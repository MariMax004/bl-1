package ru.mariamaximova.bl1.application.fiml.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmRepository extends JpaRepository<Film, Long> {
}
