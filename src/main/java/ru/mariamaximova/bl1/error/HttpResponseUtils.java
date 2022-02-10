package ru.mariamaximova.bl1.error;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.mariamaximova.bl1.error.model.ApplicationErrorDto;

import javax.annotation.PostConstruct;
import javax.persistence.NonUniqueResultException;
import javax.servlet.http.HttpServletResponse;

/**
 * Обработчик ошибки.
 *
 * @author Iuri Babalin.
 */
@Component
@RequiredArgsConstructor
public class HttpResponseUtils extends ResponseEntityExceptionHandler {

    private static ObjectMapper staticObjectMapper;
    private final ObjectMapper objectMapper;

    @SneakyThrows
    public static void writeError(HttpServletResponse response, ApplicationErrorDto error, int status) {
        response.setContentType("application/json; charset=utf-8");
        response.setStatus(status);
        staticObjectMapper.writeValue(response.getOutputStream(), error);
    }

    @PostConstruct
    public void postConstruct() {
        staticObjectMapper = this.objectMapper;
    }

}

