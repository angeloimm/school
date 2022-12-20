package it.olegna.schoolmgmt.service;

import it.olegna.schoolmgmt.dto.UtenteDto;
import it.olegna.schoolmgmt.persistence.model.Utente;

import java.util.List;
import java.util.Optional;

public interface UtenteSvc {
    UtenteDto createModificaUtente(UtenteDto utente);

    Optional<UtenteDto> findUtenteById(String idUtente);
    Optional<Utente> findUtenteByUsername(String username);
    void cancellaUtente(String idUtente);

    List<UtenteDto> recuperaUtenti();
}