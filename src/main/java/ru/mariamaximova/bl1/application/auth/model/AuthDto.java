package ru.mariamaximova.bl1.application.auth.model;

import lombok.Data;

@Data
public class AuthDto {
    private String login;

    private String password;
}
