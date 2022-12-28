package it.olegna.schoolmgmt.service;

import it.olegna.schoolmgmt.dto.CorsoDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AulaSvc {
    CorsoDto createModificaAula(CorsoDto aula);

    Optional<CorsoDto> findAulaById(UUID id);

    void cancellaAula(UUID idAula);

    List<CorsoDto> recuperaAule();
}
