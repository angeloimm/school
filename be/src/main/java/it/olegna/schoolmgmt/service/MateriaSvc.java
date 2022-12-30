package it.olegna.schoolmgmt.service;

import it.olegna.schoolmgmt.dto.MateriaDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MateriaSvc {
    MateriaDto createModificaMateria(MateriaDto materia);

    Optional<MateriaDto> findMateriaById(UUID id);

    void cancellaMateria(UUID idMateria);

    List<MateriaDto> recuperaMaterie(String nome);
}