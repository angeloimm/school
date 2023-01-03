package it.olegna.schoolmgmt.config;

import it.olegna.schoolmgmt.domain.SchoolUserDetail;
import it.olegna.schoolmgmt.dto.UtenteDto;
import it.olegna.schoolmgmt.dto.api.ApiError;
import it.olegna.schoolmgmt.dto.api.ApiResponse;
import it.olegna.schoolmgmt.dto.api.ErrorDetail;
import it.olegna.schoolmgmt.service.impl.SchoolUserDetailManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.csrf.CsrfTokenRequestHandler;

import java.io.IOException;
import java.util.Collections;

@Configuration
@Slf4j
public class WebSecurityConfig {
    public static final String LOGIN_URL = "/public/accedi";
    public static final String LOGOUT_URL = "/public/logout";
    public static final String SUCCESS_URL = "/protected/hp";
    @Autowired
    private MappingJackson2HttpMessageConverter springMvcJacksonConverter;
    @Autowired
    private SchoolUserDetailManager schoolUserDetailManager;

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {

        return http
                /*.sessionManagement(sessione ->{
                    sessione.sessionCreationPolicy(SessionCreationPolicy.I);
                })*/
                .authorizeHttpRequests(exchanges ->
                        exchanges
                                .requestMatchers("/public/**",
                                        "/favicon.ico",
                                        //Erogazione angular da static
                                        "/index.html",
                                        "/*.js**",
                                        "/*.css*",
                                        "/*.scss*",
                                        "/*.png*",
                                        "/*.svg*",
                                        "/*.ttf*",
                                        "/*.woff*",
                                        "/*.woff2*",
                                        "/*.eot*",
                                        "/*.txt*",
                                        "/assets/**",
                                        //Fine erogazione angular da static
                                        "/v3/api-docs/**",
                                        "/swagger-ui/**",
                                        "/swagger-ui.html",
                                        "/public/**",
                                        "/actuator/**",
                                        "/actuators/**",
                                        "/websocket/**")
                                .permitAll()
                                .requestMatchers("/protected/**")
                                .authenticated())
                .formLogin()
                //.loginPage(LOGIN_URL)
                .loginProcessingUrl("/login")
                .permitAll()
                .usernameParameter("username")
                .passwordParameter("password")
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                        //Recupero utente corrente
                        if (authentication instanceof UsernamePasswordAuthenticationToken) {
                            SchoolUserDetail currentUser = (SchoolUserDetail) authentication.getPrincipal();
                            ApiResponse<UtenteDto> userDetail = ApiResponse.<UtenteDto>builder().payload(UtenteDto.builder()
                                                                                                            .nome(currentUser.getNome())
                                                                                                            .cognome(currentUser.getCognome())
                                                                                                            .sesso(currentUser.getSesso())
                                                                                                            .dataNascita(currentUser.getDataNascita())
                                                                                                            .tipoUtente(currentUser.getTipoUtente().get(0))
                                                                                                            .username(currentUser.getUsername())
                                                                                                            .id(currentUser.getIdUtente())
                                                                                                            .build())
                                                                .build();
                            response.getWriter().append(springMvcJacksonConverter.getObjectMapper().writeValueAsString(userDetail));
                            response.setStatus(200);
                        } else {
                            ApiResponse<String> resp = ApiResponse.<String>builder().payload("Errore nel processo di autenticazione. Auth non di tipo UsernamePasswordAuthenticationToken").build();
                            response.getWriter().append(springMvcJacksonConverter.getObjectMapper().writeValueAsString(resp));
                            response.setStatus(401);
                        }
                    }
                })
                .failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                        log.error("AuthenticationException ", exception);
                        ApiResponse<String> resp = ApiResponse.<String>builder()
                                .payload("Errore nel processo di autenticazione. Eccezione " + exception.getMessage())
                                .error(true)
                                .errori(ApiError.builder()
                                        .path(request.getRequestURI())
                                        .details(Collections.singletonList(ErrorDetail.builder()
                                                                            .code("BC")
                                                                            .build()))
                                        .build())
                                .build();
                        response.getWriter().append(springMvcJacksonConverter.getObjectMapper().writeValueAsString(resp));
                        response.setStatus(496);
                    }
                })
                .and()
                .csrf(csrf -> {
                    csrf.csrfTokenRequestHandler(this.csrfTokenRequestHandler());
                    csrf.csrfTokenRepository(csrfTokenRepository());
                    //csrf.ignoringRequestMatchers("/h2-console/**");
                })
                .logout()
                .logoutSuccessHandler(new LogoutSuccessHandler() {
                    @Override
                    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                        ApiResponse<Boolean> userDetail = ApiResponse.<Boolean>builder().payload(Boolean.TRUE)
                                        .build();
                        response.getWriter().append(springMvcJacksonConverter.getObjectMapper().writeValueAsString(userDetail));
                        response.setStatus(200);
                    }
                })
                .logoutUrl(LOGOUT_URL)
                .permitAll()
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .and()
                .build();
    }
    CsrfTokenRequestHandler csrfTokenRequestHandler(){
        CsrfTokenRequestAttributeHandler result = new CsrfTokenRequestAttributeHandler();
        return result;
    }
    CsrfTokenRepository csrfTokenRepository(){
        CookieCsrfTokenRepository repository = CookieCsrfTokenRepository.withHttpOnlyFalse();
        repository.setSecure(true);
        repository.setCookiePath("/");
        return  repository;
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