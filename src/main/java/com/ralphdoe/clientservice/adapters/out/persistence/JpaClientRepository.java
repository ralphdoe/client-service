package com.ralphdoe.clientservice.adapters.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaClientRepository extends JpaRepository<ClientEntity, Long> {

}
