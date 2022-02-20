package ru.mariamaximova.bl1.application.rating.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mariamaximova.bl1.application.customer.domain.Customer;
import ru.mariamaximova.bl1.application.fiml.domain.Film;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    List<Rating> findAllByFilmId(Film filmId);

    Rating getByFilmIdAndCustomerId(Film filmId, Customer customerId);
    void deleteById(Long id);
    Rating getById(Long id);
}
