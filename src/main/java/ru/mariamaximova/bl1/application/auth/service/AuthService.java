package ru.mariamaximova.bl1.application.auth.service;

import org.springframework.web.bind.annotation.RequestBody;
import ru.mariamaximova.bl1.application.auth.model.AuthDto;
import ru.mariamaximova.bl1.application.auth.model.RegistrationDto;
import ru.mariamaximova.bl1.application.auth.model.TokenDto;

public interface AuthService {
    TokenDto registration(RegistrationDto registrationDto);

    TokenDto login(AuthDto authDto);
}
