package com.ralphdoe.clientservice.application.service;

import com.ralphdoe.clientservice.adapters.out.messaging.ClientEventPublisher;
import com.ralphdoe.clientservice.adapters.out.messaging.model.ClientEvent;
import com.ralphdoe.clientservice.domain.model.Client;
import com.ralphdoe.clientservice.domain.ports.in.ClientUseCase;
import com.ralphdoe.clientservice.domain.ports.out.ClientRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientServiceImpl implements ClientUseCase {

    private final ClientRepositoryPort clientRepositoryPort;
    private final ClientEventPublisher eventPublisher;

    @Override
    public Client createClient(Client client) {
        log.info("Creating client: {} {}", client.getFirstName(), client.getLastName());
        Client saved = clientRepositoryPort.save(client);
        log.info("Client saved with ID: {}", saved.getId());

        // Send asynchronous event
        ClientEvent event = ClientEvent.builder()
                .eventType("CLIENT_CREATED")
                .firstName(saved.getFirstName())
                .lastName(saved.getLastName())
                .age(saved.getAge())
                .birthDate(saved.getBirthDate())
                .build();

        eventPublisher.publishClientCreatedEvent(event);
        log.info("Published CLIENT_CREATED event for client ID: {}", saved.getId());

        return saved;
    }

    @Override
    public List<Client> getAllClients() {
        log.info("Retrieving all clients");
        return clientRepositoryPort.findAll();
    }

    @Override
    public Map<String, Double> getClientAgeMetrics() {
        log.info("Calculating client age metrics");
        List<Client> clients = clientRepositoryPort.findAll();

        DoubleSummaryStatistics stats = clients.stream()
                .collect(Collectors.summarizingDouble(Client::getAge));

        double average = stats.getAverage();
        double variance = clients.stream()
                .mapToDouble(c -> Math.pow(c.getAge() - average, 2))
                .average()
                .orElse(0.0);
        double stdDev = Math.sqrt(variance);

        log.info("Average age: {}, Standard deviation: {}", average, stdDev);

        return Map.of(
                "averageAge", average,
                "standardDeviation", stdDev
        );
    }
}
