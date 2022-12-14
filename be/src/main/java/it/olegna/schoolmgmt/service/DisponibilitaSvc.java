package it.olegna.schoolmgmt.service;

import it.olegna.schoolmgmt.dto.DisponibilitaDto;

import java.util.List;
import java.util.Optional;

public interface DisponibilitaSvc {
    DisponibilitaDto createModificaDisponibilita(DisponibilitaDto disponibilita);

    void cancellaDisponibilita(String idDisponibilita);

    Optional<DisponibilitaDto> findDisponibilitaById(String id);

    Optional<List<DisponibilitaDto>> findDisponibilitaByUsernameDocente(String username);

    List<DisponibilitaDto> recuperaDisponibilita();

}
