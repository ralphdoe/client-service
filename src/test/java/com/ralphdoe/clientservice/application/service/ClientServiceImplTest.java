package com.ralphdoe.clientservice.application.service;

import com.ralphdoe.clientservice.adapters.out.messaging.ClientEventPublisher;
import com.ralphdoe.clientservice.adapters.out.messaging.model.ClientEvent;
import com.ralphdoe.clientservice.domain.model.Client;
import com.ralphdoe.clientservice.domain.ports.out.ClientRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClientServiceImplTest {

    @Mock
    private ClientRepositoryPort clientRepositoryPort;

    @Mock
    private ClientEventPublisher clientEventPublisher;

    @InjectMocks
    private ClientServiceImpl clientService;

    private Client sampleClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        sampleClient = Client.builder()
                .firstName("Ana")
                .lastName("Gomez")
                .age(30)
                .birthDate(LocalDate.of(1995, 5, 20))
                .build();
    }

    @Test
    void shouldCreateClientAndPublishEvent() {
        when(clientRepositoryPort.save(any(Client.class))).thenReturn(sampleClient);

        Client saved = clientService.createClient(sampleClient);

        assertNotNull(saved);
        assertEquals("Ana", saved.getFirstName());
        verify(clientRepositoryPort, times(1)).save(any(Client.class));
        verify(clientEventPublisher, times(1)).publishClientCreatedEvent(any(ClientEvent.class));
    }

    @Test
    void shouldReturnAllClients() {
        List<Client> clients = List.of(sampleClient);
        when(clientRepositoryPort.findAll()).thenReturn(clients);

        List<Client> result = clientService.getAllClients();

        assertEquals(1, result.size());
        assertEquals("Gomez", result.get(0).getLastName());
    }

    @Test
    void shouldCalculateAgeMetricsCorrectly() {
        Client client1 = Client.builder().age(30).build();
        Client client2 = Client.builder().age(40).build();
        Client client3 = Client.builder().age(50).build();

        when(clientRepositoryPort.findAll()).thenReturn(List.of(client1, client2, client3));

        Map<String, Double> metrics = clientService.getClientAgeMetrics();

        assertEquals(40.0, metrics.get("averageAge"));
        assertEquals(8.16, metrics.get("standardDeviation"), 0.01); // delta permitido
    }
}
