package ru.mariamaximova.bl1.error;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.mariamaximova.bl1.error.model.ApplicationErrorDto;

import javax.persistence.NonUniqueResultException;

/**
 * Обработчик ошибки.
 *
 * @author Iuri Babalin.
 */
@ControllerAdvice
public class HttpResponseUtils extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    protected ResponseEntity<ApplicationErrorDto> handleThereIsApplicationException(Exception ex) {
        return new ResponseEntity<>(ApplicationErrorDto.of(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({DataIntegrityViolationException.class, NonUniqueResultException.class})
    protected ResponseEntity<ApplicationErrorDto> handleThereIsNoSuchException() {
        return new ResponseEntity<>(ApplicationErrorDto.of("Ошибка сохранения"), HttpStatus.BAD_REQUEST);
    }

}
