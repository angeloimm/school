package it.olegna.schoolmgmt.service;

import it.olegna.schoolmgmt.dto.MateriaDto;

import java.util.List;
import java.util.Optional;

public interface MateriaSvc {
    MateriaDto createModificaMateria(MateriaDto materia);

    Optional<MateriaDto> findMateriaById(String id);

    void cancellaMateria(String idMateria);

    List<MateriaDto> recuperaMaterie(String nome);
}