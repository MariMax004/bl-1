package ru.mariamaximova.bl1.application.auth.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
public class TokenDto {
    private String token;
}
