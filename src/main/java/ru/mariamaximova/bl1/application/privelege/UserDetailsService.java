package ru.mariamaximova.bl1.application.privelege;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserDetailsService {
    UserDetails loadUserByEmail(String email) throws UsernameNotFoundException;
}
