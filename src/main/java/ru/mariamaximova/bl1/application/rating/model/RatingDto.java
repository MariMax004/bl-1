package ru.mariamaximova.bl1.application.rating.model;

import lombok.Data;

@Data
public class RatingDto {
    private Long id;
    private boolean is_active;
    private Long rating;

    public RatingDto() {
    }

    public RatingDto(Long id, boolean is_active, Long rating) {
        this.id = id;
        this.is_active = is_active;
        this.rating = rating;
    }
}
