package it.olegna.schoolmgmt.service.impl;

import it.olegna.schoolmgmt.domain.DiomedeeUserDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
public class AuthServerCustomAuthProvider implements AuthenticationProvider {
    @Autowired
    private AuthServerUserDetails authServerUserDetails;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        DiomedeeUserDetail user = (DiomedeeUserDetail) this.authServerUserDetails.loadUserByUsername(username);
        return checkPassword(user, password);
    }

    private Authentication checkPassword(DiomedeeUserDetail user, String password) {
        if (Objects.equals(password, user.getPassword())) {
            UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities());
            result.setDetails(user);
            return result;
        } else {
            throw new BadCredentialsException("Bad credentials");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}