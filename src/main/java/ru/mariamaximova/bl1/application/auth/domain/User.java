package ru.mariamaximova.bl1.application.auth.domain;


import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Table(name = "users")
@ToString(of = "id")
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;
}

