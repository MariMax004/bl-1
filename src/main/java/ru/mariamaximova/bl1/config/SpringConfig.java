package ru.mariamaximova.bl1.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import ru.mariamaximova.bl1.application.customer.domain.CustomerRepository;
import ru.mariamaximova.bl1.error.ErrorDescription;
import ru.mariamaximova.bl1.error.HttpResponseUtils;
import ru.mariamaximova.bl1.error.model.ApplicationErrorDto;

import javax.servlet.http.HttpServletResponse;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SpringConfig extends WebSecurityConfigurerAdapter {

    private final JwtFilter jwtFilter;

    private final CustomerRepository customerRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .configurationSource(corsConfigurationSource())
                .and()
                .csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler())
                .authenticationEntryPoint(authenticationEntryPoint())
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/login")
                .permitAll()
                .antMatchers(AUTH_WHITELIST)
                .permitAll()
                .antMatchers(HttpMethod.POST, "/registration")
                .permitAll()
                .antMatchers(HttpMethod.GET, "/film/{filmId}/comments")
                .permitAll()
                .antMatchers(HttpMethod.OPTIONS, "/**")
                .authenticated()
                .antMatchers(HttpMethod.GET, "/**")
                .permitAll()
                .antMatchers(HttpMethod.POST, "/**")
                .authenticated();
        http.headers().frameOptions().sameOrigin();
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * Конфигарация CORS.
     */
    private CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Collections.singletonList("*"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedMethods(Collections.singletonList("*"));
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.setAlwaysUseFullPath(true);
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /**
     * {@see AccessDeniedHandler}.
     */
    private AccessDeniedHandler accessDeniedHandler() {
        ErrorDescription errorDescription = ErrorDescription.ACCESS_DENIED;
        ApplicationErrorDto error = ApplicationErrorDto.of(errorDescription.getMessage());
        return (request, response, ex) -> HttpResponseUtils.writeError(response, error,
                HttpServletResponse.SC_FORBIDDEN);
    }

    /**
     * {@see AuthenticationEntryPoint}.
     */
    private AuthenticationEntryPoint authenticationEntryPoint() {
        ErrorDescription errorDescription = ErrorDescription.UNAUTHORIZED_ACCESS;
        ApplicationErrorDto error = ApplicationErrorDto.of(errorDescription.getMessage());
        return (request, response, ex) -> HttpResponseUtils.writeError(response, error,
                HttpServletResponse.SC_UNAUTHORIZED);
    }

    private static final String[] AUTH_WHITELIST = {
            "/authenticate",
            "/swagger-resources/**",
            "/swagger-ui/**",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/webjars/**"
    };
}
