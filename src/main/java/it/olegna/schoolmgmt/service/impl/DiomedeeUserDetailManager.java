package it.olegna.schoolmgmt.service.impl;

import it.olegna.schoolmgmt.domain.DiomedeeUserDetail;
import it.olegna.schoolmgmt.persistence.model.Utente;
import it.olegna.schoolmgmt.service.UtenteSvc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;

import java.util.Collections;

@Slf4j
public class DiomedeeUserDetailManager implements UserDetailsManager {
    private Authentication authentication;
    private UtenteSvc utenteSvc;

    public DiomedeeUserDetailManager(UtenteSvc utenteSvc) {
        this.utenteSvc = utenteSvc;
    }

    @Override
    public void createUser(UserDetails user) {
        log.warn("Not implemented");
    }

    @Override
    public void updateUser(UserDetails user) {
        log.warn("Not implemented");
    }

    @Override
    public void deleteUser(String username) {
        log.warn("Not implemented");
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        log.warn("Not implemented");
    }

    @Override
    public boolean userExists(String username) {
        return utenteSvc.findUtenteByUsername(username).isPresent();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.trace("Ricerco utente con username {}", username);
        Utente user = utenteSvc.findUtenteByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Nessun utente trovato con username ".concat(username)));
        return DiomedeeUserDetail.builder()

                .cognome(user.getCognome())
                .nome(user.getNome())
                .idUtente(user.getId())
                .username(user.getUsername())

                .password(user.getPassword())
                .dataNascita(user.getDataNascita())
                .codiceFiscale(user.getCodiceFiscale())
                .sesso(user.getSesso())
                .tipoUtente(Collections.singletonList(user.getTipoUtente()))
                .build();
    }

    public void setAuthentication(Authentication authentication) {
        this.authentication = authentication;
    }
}
