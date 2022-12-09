package it.olegna.schoolmgmt.service;

import it.olegna.schoolmgmt.persistence.model.Materia;

import java.util.List;
import java.util.Optional;

public interface MateriaSvc {
    Materia createModificaMateria(Materia materia);

    Optional<Materia> findMateriaById(String id);

    void cancellaMateria(String idMateria);

    List<Materia> recuperaMaterie(String nome);

}
