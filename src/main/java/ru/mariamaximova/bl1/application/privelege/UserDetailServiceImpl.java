package ru.mariamaximova.bl1.application.privelege;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.mariamaximova.bl1.application.auth.domain.TokenRepository;
import ru.mariamaximova.bl1.application.auth.model.UserXmlDto;
import ru.mariamaximova.bl1.application.auth.xml.UserXmlRepository;
import ru.mariamaximova.bl1.application.customer.domain.CustomerRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service("userDetailsService")
@Transactional
@AllArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService{

    @Autowired
    protected final TokenRepository tokenRepository;

    @Autowired
    protected final CustomerRepository customerRepository;


    @Override
    public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
        UserXmlRepository userXmlRepository = new UserXmlRepository(tokenRepository);
        final UserXmlDto user = userXmlRepository.getCustomerByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(email);
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (user.getIsModerator()) authorities.add(new SimpleGrantedAuthority("ADMIN"));
        else authorities.add(new SimpleGrantedAuthority("CUSTOMER"));

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }
}
