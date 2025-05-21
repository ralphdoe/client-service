package com.ralphdoe.clientservice.adapters.in.rest.dto;

import lombok.Data;

@Data
public class AuthRequest {
    private String username;
    private String password;
}
