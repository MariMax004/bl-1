package ru.mariamaximova.bl1.application.auth.service;

import org.springframework.http.ResponseEntity;
import ru.mariamaximova.bl1.application.auth.domain.User;

public interface AuthService {
    ResponseEntity<String> signin(User user);

//    public boolean saveUser(User user);
//    TODO: add registration
}
