package com.ralphdoe.clientservice.domain.ports.in;

import com.ralphdoe.clientservice.domain.model.Client;

import java.util.List;
import java.util.Map;

public interface ClientUseCase {
    Client createClient(Client client);
    List<Client> getAllClients();
    Map<String, Double> getClientAgeMetrics();
}
