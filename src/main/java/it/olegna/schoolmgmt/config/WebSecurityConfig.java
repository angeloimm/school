package it.olegna.schoolmgmt.config;

import it.olegna.schoolmgmt.service.impl.AuthServerCustomAuthProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;

@Configuration
public class WebSecurityConfig {
    public static final String LOGIN_URL = "/public/accedi";
    @Autowired
    private AuthServerCustomAuthProvider authServerCustomAuthProvider;
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {

        return http
                .authorizeHttpRequests(exchanges ->
                        exchanges
                                .requestMatchers("/public/**",
                                        "/h2-console",
                                        "/webjars/**",
                                        "/js/**",
                                        "/css/**",
                                        "/fonts/**",
                                        "/images/**",
                                        "/actuator/**",
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
                .formLogin()
                .loginPage(LOGIN_URL)
                .loginProcessingUrl("/login")
                .permitAll()
                .usernameParameter("username")
                .passwordParameter("password")
                .and()
                .logout()
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .and()
                .build();
    }
    @Autowired
    public void customAuthProvider(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authServerCustomAuthProvider);
    }

    /*
    @Bean
    public UserDetailsService userDetailsService(@Autowired UtenteRepository utenteRepository){
        return new DiomedeeUserDetailManager(utenteRepository);
    }*/
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
