package it.olegna.schoolmgmt.service.impl;

import it.olegna.schoolmgmt.domain.SchoolUserDetail;
import it.olegna.schoolmgmt.persistence.model.Utente;
import it.olegna.schoolmgmt.persistence.repository.UtenteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@Slf4j
public class SchoolUserDetailManager implements UserDetailsManager {
    @Autowired
    private UtenteRepository utenteRepository;

    @Override
    public void createUser(UserDetails user) {
        log.warn("Not implemented");
    }

    @Override
    public void updateUser(UserDetails user) {
        log.warn("Not implemented");
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteUser(String username) {
        log.warn("Not implemented");
    }

    @Override
    @Transactional(readOnly = false)
    public void changePassword(String oldPassword, String newPassword) {
        log.warn("Not implemented");
    }

    @Override
    @Transactional(readOnly = true)
    public boolean userExists(String username) {
        return utenteRepository.countByUsername(username) > 0;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.trace("Ricerco utente con username {}", username);
        Utente user = utenteRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Nessun utente trovato con username ".concat(username)));
        return SchoolUserDetail.builder()
                .cognome(user.getCognome())
                .nome(user.getNome())
                .idUtente(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .dataNascita(user.getDataNascita())
                .codiceFiscale(user.getCodiceFiscale())
                .sesso(user.getSesso())
                .enabled(user.getAttivo())
                .tipoUtente(Collections.singletonList(user.getTipoUtente()))

                .build();
    }
}