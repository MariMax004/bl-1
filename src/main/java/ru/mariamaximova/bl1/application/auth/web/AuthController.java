package ru.mariamaximova.bl1.application.auth.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.mariamaximova.bl1.application.auth.domain.User;
import ru.mariamaximova.bl1.application.auth.service.impl.AuthServiceImpl;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthServiceImpl authService;

    @PostMapping(value = "/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        return authService.signin(user);
    }

}
