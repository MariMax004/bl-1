package ru.mariamaximova.bl1.error;

import lombok.Getter;
import ru.mariamaximova.bl1.error.model.ApplicationErrorDto;

@Getter
public class ApplicationException extends RuntimeException {
    private final ApplicationErrorDto error;

    public ApplicationException(ApplicationErrorDto error) {
        super(error.getMessage());
        this.error = error;
    }

    public ApplicationErrorDto getError() {
        return this.error;
    }
}
