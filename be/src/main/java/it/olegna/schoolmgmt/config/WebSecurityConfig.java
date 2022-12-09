package it.olegna.schoolmgmt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;

@Configuration
public class WebSecurityConfig {
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {

        return http
                .authorizeHttpRequests(exchanges ->
                        exchanges
                                .requestMatchers("/public/**",
                                        "/v3/api-docs/**",
                                        "/swagger-ui/**",
                                        "/swagger-ui.html", "/public/**", "/websocket/**")
                                .permitAll()
                                .requestMatchers("/protected/**")
                                .authenticated())
                .csrf(csrf -> {
                    csrf.csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler());
                    csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
                })
                .build();
    }
}
