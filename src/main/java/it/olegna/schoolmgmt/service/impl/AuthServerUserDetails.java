package it.olegna.schoolmgmt.service.impl;

import it.olegna.schoolmgmt.domain.DiomedeeUserDetail;
import it.olegna.schoolmgmt.persistence.model.Utente;
import it.olegna.schoolmgmt.service.UtenteSvc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Slf4j
@Service
public class AuthServerUserDetails implements UserDetailsService {
    @Autowired
    private UtenteSvc utenteSvc;

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
}
