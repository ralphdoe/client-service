package com.ralphdoe.clientservice.adapters.in.rest;

import com.ralphdoe.clientservice.adapters.in.rest.dto.ClientRequest;
import com.ralphdoe.clientservice.adapters.in.rest.dto.ClientResponse;
import com.ralphdoe.clientservice.domain.model.Client;
import com.ralphdoe.clientservice.domain.ports.in.ClientUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
@Slf4j
public class ClientController {

    private final ClientUseCase clientUseCase;

    @PostMapping
    public ResponseEntity<ClientResponse> createClient(@RequestBody @Valid ClientRequest request) {
        log.info("Received request to create client: {}", request);
        Client created = clientUseCase.createClient(
                Client.builder()
                        .firstName(request.getFirstName())
                        .lastName(request.getLastName())
                        .age(request.getAge())
                        .birthDate(request.getBirthDate())
                        .build()
        );
        log.info("Client created successfully: {}", created);
        return ResponseEntity.ok(toResponse(created));
    }

    @GetMapping
    public ResponseEntity<List<ClientResponse>> getAllClients() {
        log.info("Fetching all clients");
        List<ClientResponse> responses = clientUseCase.getAllClients()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        log.info("Total clients fetched: {}", responses.size());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/metrics")
    public ResponseEntity<Map<String, Double>> getMetrics() {
        log.info("Calculating client age metrics");
        Map<String, Double> metrics = clientUseCase.getClientAgeMetrics();
        log.info("Client age metrics: {}", metrics);
        return ResponseEntity.ok(metrics);
    }

    private ClientResponse toResponse(Client client) {
        LocalDate estimatedDeathDate = client.getBirthDate().plusYears(80);

        return ClientResponse.builder()
                .id(client.getId())
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .age(client.getAge())
                .birthDate(client.getBirthDate())
                .estimatedDeathDate(estimatedDeathDate)
                .build();
    }

}
