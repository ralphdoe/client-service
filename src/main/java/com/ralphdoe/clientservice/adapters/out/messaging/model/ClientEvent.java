package com.ralphdoe.clientservice.adapters.out.messaging.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
public class ClientEvent implements Serializable {
    private String eventType;       // e.g. CLIENT_CREATED
    private String firstName;
    private String lastName;
    private Integer age;
    private LocalDate birthDate;
}

