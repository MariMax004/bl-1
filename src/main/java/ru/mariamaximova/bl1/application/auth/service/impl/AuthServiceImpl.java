package ru.mariamaximova.bl1.application.auth.service.impl;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
//    TODO: setup AuthenticationManager
    final AuthenticationManager authenticationManager;
//    private final BCryptPasswordEncoder bCryptPasswordEncoder; // need if we will save users to encrypt password


    @Override
    public ResponseEntity<String> signin(User user) {
        try {
            Gson gson = new Gson();
            User local_user = userRepository.findByUsername(user.getUsername());
            String pass = local_user.getPassword();
            if (user.getPassword().equals(pass)) {
                Long userId = local_user.getId();
                LoginResponse loginResponse = new LoginResponse(getUserToken(user), userId);
                return new ResponseEntity<>(gson.toJson(loginResponse), HttpStatus.OK);
            }
            else {
                log.error("Wrong password for user: {}", user);
                return new ResponseEntity<>("Неверные учетные данные пользователя", HttpStatus.BAD_REQUEST);
            }
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
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        return jwtUtils.generateJwtToken(authentication);
    }
}
