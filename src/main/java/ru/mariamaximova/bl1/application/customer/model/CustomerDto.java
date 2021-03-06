package ru.mariamaximova.bl1.application.customer.model;

import lombok.Data;

@Data
public class CustomerDto {
    private Long id;

    private String name;

    private String lastName;

    private String email;
    private boolean is_moderator;

    public CustomerDto() {
    }
}
