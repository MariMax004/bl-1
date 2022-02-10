package ru.mariamaximova.bl1.application.auth.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Token getByToken(String token);
}
