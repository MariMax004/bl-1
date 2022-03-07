package ru.mariamaximova.bl1.error;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.mariamaximova.bl1.error.model.ApplicationErrorDto;

import javax.persistence.NonUniqueResultException;

@RequiredArgsConstructor
@ControllerAdvice
public class ExceptionHandlerUtil extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    protected ResponseEntity<ApplicationErrorDto> handleThereIsApplicationException(ApplicationException ex) {
        return new ResponseEntity<>(ApplicationErrorDto.of(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({DataIntegrityViolationException.class, NonUniqueResultException.class})
    protected ResponseEntity<ApplicationErrorDto> handleThereIsNoSuchException() {
        return new ResponseEntity<>(ApplicationErrorDto.of("Ошибка сохранения"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

//    @ExceptionHandler(Exception.class)
//    protected ResponseEntity<ApplicationErrorDto> handleException() {
//        return new ResponseEntity<>(ApplicationErrorDto.of("Ошибка приложения"), HttpStatus.INTERNAL_SERVER_ERROR);
//    }
}
