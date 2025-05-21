package com.ralphdoe.clientservice.adapters.out.persistence;

import com.ralphdoe.clientservice.domain.model.Client;
import com.ralphdoe.clientservice.domain.ports.out.ClientRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ClientPersistenceAdapter implements ClientRepositoryPort {

    private final JpaClientRepository jpaClientRepository;

    public ClientPersistenceAdapter(JpaClientRepository jpaClientRepository) {
        this.jpaClientRepository = jpaClientRepository;
    }

    @Override
    public Client save(Client client) {
        ClientEntity entity = ClientEntity.builder()
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .age(client.getAge())
                .birthDate(client.getBirthDate())
                .build();

        ClientEntity saved = jpaClientRepository.save(entity);

        return toDomain(saved);
    }

    @Override
    public List<Client> findAll() {
        return jpaClientRepository.findAll()
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    private Client toDomain(ClientEntity entity) {
        return Client.builder()
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .age(entity.getAge())
                .birthDate(entity.getBirthDate())
                .build();
    }
}
