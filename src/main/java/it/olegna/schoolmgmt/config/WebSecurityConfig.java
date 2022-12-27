package it.olegna.schoolmgmt.config;

import it.olegna.schoolmgmt.service.impl.SchoolUserDetailManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;

import java.io.IOException;

@Configuration
public class WebSecurityConfig {
    public static final String LOGIN_URL = "/public/accedi";
    public static final String LOGOUT_URL = "/public/logout";
    public static final String SUCCESS_URL = "/protected/hp";
    @Autowired
    private SchoolUserDetailManager schoolUserDetailManager;

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
                                        "/bootstrap-icons/**",
                                        "/swagger-ui.html",
                                        "/public/**",
                                        "/websocket/**")
                                .permitAll()
                                .requestMatchers("/protected/**")
                                .authenticated())
                .formLogin()
                    .loginPage(LOGIN_URL)
                    .loginProcessingUrl("/login")
                    .permitAll()
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .successHandler(new AuthenticationSuccessHandler() {
                        @Override
                        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                            response.sendRedirect("/protected/hp");
                        }
                    })
                .and()
                .logout()
                    .logoutUrl(LOGOUT_URL)
                    .permitAll()
                    .clearAuthentication(true)
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
                .and()
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public DaoAuthenticationProvider schoolAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(schoolUserDetailManager);
        return provider;
    }
}