package ru.mariamaximova.bl1.application.comment.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mariamaximova.bl1.application.customer.domain.Customer;
import ru.mariamaximova.bl1.application.fiml.domain.Film;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByFilmId(Film filmId);

    Comment getByFilmIdAndCustomerId(Film filmId, Customer customerId);
    void deleteById(Long id);
    Comment getCommentById(Long id);
}
