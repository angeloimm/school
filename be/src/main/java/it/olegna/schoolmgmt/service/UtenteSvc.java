package it.olegna.schoolmgmt.service;

import it.olegna.schoolmgmt.persistence.model.Utente;

import java.util.List;
import java.util.Optional;

public interface UtenteSvc {
    Utente createModificaUtente(Utente utente);

    Optional<Utente> findUtenteById(String idUtente);

    void cancellaUtente(String idUtente);

    List<Utente> recuperaUtenti();
}
