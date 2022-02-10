package ru.mariamaximova.bl1.application.auth.model;

import lombok.Data;

@Data
public class RegistrationDto {
    private String email;

    private String password;

    private String name;

    private String lastName;
}
