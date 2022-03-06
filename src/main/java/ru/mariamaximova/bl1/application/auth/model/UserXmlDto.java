package ru.mariamaximova.bl1.application.auth.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserXmlDto {
    private Long id;

    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private Boolean isModerator;
}
