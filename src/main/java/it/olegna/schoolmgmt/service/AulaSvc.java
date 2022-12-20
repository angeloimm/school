package it.olegna.schoolmgmt.service;

import it.olegna.schoolmgmt.dto.CorsoDto;

import java.util.List;
import java.util.Optional;

public interface AulaSvc {
    CorsoDto createModificaAula(CorsoDto aula);

    Optional<CorsoDto> findAulaById(String id);

    void cancellaAula(String idAula);

    List<CorsoDto> recuperaAule();
}
