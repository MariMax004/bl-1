package ru.mariamaximova.bl1.application.rating.service;

import ru.mariamaximova.bl1.application.rating.model.RatingDto;
import ru.mariamaximova.bl1.application.rating.model.ResponseRatingDto;

import javax.transaction.UserTransaction;
import java.util.List;

public interface RatingService {
    List<ResponseRatingDto> getRatings(Long filmId);

    void saveRating(Long filmId, Long customerId, RatingDto commentDto);
    void deleteRating(Long id);
    void updateStatusRating(Long id, Boolean flag);


}
