package it.olegna.schoolmgmt.service;

import it.olegna.schoolmgmt.dto.MateriaDto;
import it.olegna.schoolmgmt.dto.MateriaTableDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MateriaSvc {
    MateriaDto createModificaMateria(MateriaDto materia);

    Optional<MateriaDto> findMateriaById(UUID id);

    void cancellaMateria(UUID idMateria);

    Page<MateriaTableDto> recuperaMaterie(String nome, Pageable pageable);
}