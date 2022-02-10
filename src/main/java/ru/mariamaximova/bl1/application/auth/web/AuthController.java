package ru.mariamaximova.bl1.application.auth.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.mariamaximova.bl1.application.auth.model.AuthDto;
import ru.mariamaximova.bl1.application.auth.model.RegistrationDto;
import ru.mariamaximova.bl1.application.auth.model.TokenDto;
import ru.mariamaximova.bl1.application.auth.service.impl.AuthServiceImpl;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthServiceImpl authService;

    @PostMapping(value = "/registration")
    public TokenDto registration(@RequestBody RegistrationDto registrationDto) {
        return authService.registration(registrationDto);
    }

    @PostMapping(value = "/login")
    public TokenDto login(@RequestBody AuthDto authDto) {
        return authService.login(authDto);
    }

}
