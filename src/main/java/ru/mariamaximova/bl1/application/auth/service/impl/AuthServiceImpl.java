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
import ru.mariamaximova.bl1.application.auth.service.AuthService;
import ru.mariamaximova.bl1.application.customer.domain.Customer;
import ru.mariamaximova.bl1.application.customer.domain.CustomerRepository;
import ru.mariamaximova.bl1.error.ErrorDescription;
import ru.mariamaximova.bl1.utils.JwtUtils;

import java.util.Arrays;
import java.util.List;


@Service
@Slf4j
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final CustomerRepository customerRepository;

    protected final TokenRepository tokenRepository;

    private final JwtUtils jwtUtils;

    @Override
    public TokenDto registration(RegistrationDto registrationDto) {
        Customer customer = customerRepository.getCustomerByEmail(registrationDto.getEmail());
        ErrorDescription.REGISTRATION_ERROR_USER_IS_PRESENT.throwIfTrue(!ObjectUtils.isEmpty(customer));
        customer = customerRepository.save(convertToCustomer(registrationDto));
        return TokenDto.of(customer.getTokens().get(0).getToken());
    }

    @Override
    public TokenDto login(AuthDto authDto) {
        Customer customer = customerRepository.getCustomerByEmail(authDto.getLogin());
        ErrorDescription.AUTH_LOGIN_ERROR.throwIfFalse(!ObjectUtils.isEmpty(customer));
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        ErrorDescription.AUTH_PASSWORD_ERROR.throwIfFalse(passwordEncoder.matches(authDto.getPassword(),
                customer.getPassword()));
        Token token = new Token();
        token.setCustomerId(customer);
        token.setToken(jwtUtils.generateToken(authDto.getLogin()));
        tokenRepository.save(token);
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
        return customer;
    }
}
