package com.ralphdoe.clientservice.domain.ports.out;

import com.ralphdoe.clientservice.domain.model.Client;

import java.util.List;

public interface ClientRepositoryPort {
    Client save(Client client);
    List<Client> findAll();
}
