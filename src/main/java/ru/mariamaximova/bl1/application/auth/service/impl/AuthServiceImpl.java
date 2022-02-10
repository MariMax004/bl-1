package ru.mariamaximova.bl1.application.auth.service.impl;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.mariamaximova.bl1.application.auth.domain.User;
import ru.mariamaximova.bl1.application.auth.domain.UserRepository;
import ru.mariamaximova.bl1.application.auth.model.LoginResponse;
import ru.mariamaximova.bl1.application.auth.service.AuthService;


@Service
@Slf4j
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    @Override
    public ResponseEntity<String> signin(User user) {
        try {
            Gson gson = new Gson();
            Long userId = userRepository.findByUsername(user.getUsername()).getId();
            LoginResponse loginResponse = new LoginResponse(getUserToken(user), userId);
            return new ResponseEntity<>(gson.toJson(loginResponse), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Unexpected error {}", e.getMessage());
            return new ResponseEntity<>("Неверные учетные данные пользователя", HttpStatus.BAD_REQUEST);
        }
    }

//    private User findUserByUsername(String username) {
//        return userRepository.findByUsername(username);
//    }

    private String getUserToken(User user){
        return "token";
    }
}
