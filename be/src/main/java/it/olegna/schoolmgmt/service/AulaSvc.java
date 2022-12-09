package it.olegna.schoolmgmt.service;

import it.olegna.schoolmgmt.persistence.model.Aula;

import java.util.List;
import java.util.Optional;

public interface AulaSvc {
    Aula createModificaAula(Aula aula);

    Optional<Aula> findAulaById(String id);

    void cancellaAula(String idAula);

    List<Aula> recuperaAule();
}
