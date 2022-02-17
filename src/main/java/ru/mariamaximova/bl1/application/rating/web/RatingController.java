package ru.mariamaximova.bl1.application.rating.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.mariamaximova.bl1.application.rating.model.RatingDto;
import ru.mariamaximova.bl1.application.rating.model.ResponseRatingDto;
import ru.mariamaximova.bl1.application.rating.service.RatingService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RatingController {

    private final RatingService ratingService;


    @GetMapping(value = "/film/{filmId}/ratings")
    public List<ResponseRatingDto> getRatings(@PathVariable Long filmId) {
        return ratingService.getRatings(filmId);
    }

    @PostMapping(value = "/film/{filmId}/customer/{customerId}/rating/save")
    public void saveRating(@PathVariable Long filmId,
                           @PathVariable Long customerId,
                           @RequestBody RatingDto commentDto) {
        ratingService.saveRating(filmId, customerId, commentDto);
    }
}
