package ru.mariamaximova.bl1.application.auth.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import ru.mariamaximova.bl1.application.auth.domain.Token;
import ru.mariamaximova.bl1.application.auth.domain.TokenRepository;
import ru.mariamaximova.bl1.application.auth.model.AuthDto;
import ru.mariamaximova.bl1.application.auth.model.RegistrationDto;
import ru.mariamaximova.bl1.application.auth.model.TokenDto;
import ru.mariamaximova.bl1.application.auth.model.UserXmlDto;
import ru.mariamaximova.bl1.application.auth.service.AuthService;
import ru.mariamaximova.bl1.application.auth.xml.UserXmlRepository;
import ru.mariamaximova.bl1.application.customer.domain.Customer;
import ru.mariamaximova.bl1.error.ErrorDescription;
import ru.mariamaximova.bl1.utils.JwtUtils;

import java.util.Arrays;

@Service
@Slf4j
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    protected final TokenRepository tokenRepository;

    private final JwtUtils jwtUtils;

    @Override

    public TokenDto registration(RegistrationDto registrationDto) {
        UserXmlRepository userXmlRepository = new UserXmlRepository(tokenRepository);
        UserXmlDto user = userXmlRepository.getCustomerByEmail(registrationDto.getEmail());
        ErrorDescription.REGISTRATION_ERROR_USER_IS_PRESENT.throwIfTrue(!ObjectUtils.isEmpty(user));
        Customer customer = convertToCustomer(registrationDto);
        userXmlRepository.save(customer);
        return TokenDto.of(customer.getTokens().get(0).getToken());
    }

    @Override
    public TokenDto login(AuthDto authDto) {
        UserXmlRepository userXmlRepository = new UserXmlRepository(tokenRepository);
        UserXmlDto user = userXmlRepository.getCustomerByEmail(authDto.getLogin());
        ErrorDescription.AUTH_LOGIN_ERROR.throwIfFalse(!ObjectUtils.isEmpty(user));
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        ErrorDescription.AUTH_PASSWORD_ERROR.throwIfFalse(passwordEncoder.matches(authDto.getPassword(),
                user.getPassword()));
        Token token = new Token();
        token.setToken(jwtUtils.generateToken(authDto.getLogin()));
        return TokenDto.of(token.getToken());
    }

    private Customer convertToCustomer(RegistrationDto registrationDto) {
        Customer customer = new Customer();
        Token token = new Token();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        customer.setEmail(registrationDto.getEmail());
        customer.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        customer.setFirstName(registrationDto.getName());
        customer.setLastName(registrationDto.getLastName());
        token.setCustomerId(customer);
        token.setToken(jwtUtils.generateToken(registrationDto.getEmail()));
        customer.setTokens(Arrays.asList(token));
        System.out.println(customer.getTokens().get(0).getToken());
        return customer;
    }
}
