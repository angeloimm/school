package it.olegna.schoolmgmt.service;

import it.olegna.schoolmgmt.persistence.model.Disponibilita;

import java.util.List;
import java.util.Optional;

public interface DisponibilitaSvc {
    Disponibilita createModificaDisponibilita(Disponibilita disponibilita);

    void cancellaDisponibilita(String idDisponibilita);

    Optional<Disponibilita> findDisponibilitaById(String id);

    Optional<List<Disponibilita>> findDisponibilitaByUsernameDocente(String username);

    List<Disponibilita> recuperaDisponibilita();

}
