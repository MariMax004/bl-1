package ru.mariamaximova.bl1.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.mariamaximova.bl1.application.auth.domain.TokenRepository;
import ru.mariamaximova.bl1.application.auth.model.UserXmlDto;
import ru.mariamaximova.bl1.application.auth.xml.UserXmlRepository;
import ru.mariamaximova.bl1.application.privelege.UserDetailsService;
import ru.mariamaximova.bl1.utils.JwtUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.util.StringUtils.hasText;


@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    public static final String AUTHORIZATION = "Authorization";

    private final JwtUtils jwtUtils;

    private final TokenRepository tokenRepository;

    private final UserDetailsService userDetailsService;

    @Override
    public void doFilterInternal(HttpServletRequest request,
                                 HttpServletResponse response,
                                 FilterChain filterChain) throws IOException, ServletException {
        String token = getTokenFromRequest(request);
        if (token != null && jwtUtils.validateToken(token) && isDataBase(token)) {
            String email = jwtUtils.getWordForToken(token);
            UserXmlRepository userXmlRepository = new UserXmlRepository(tokenRepository);
            final UserXmlDto user = userXmlRepository.getCustomerByEmail(email);
            UserDetails userDetails = userDetailsService.loadUserByEmail(email);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user,
                    null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private boolean isDataBase(String token) {
        return !ObjectUtils.isEmpty(tokenRepository.getByToken(token));
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearer = request.getHeader(AUTHORIZATION);
        if (hasText(bearer) && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }
}