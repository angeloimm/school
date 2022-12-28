package it.olegna.schoolmgmt.service;

import it.olegna.schoolmgmt.dto.DisponibilitaDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DisponibilitaSvc {
    DisponibilitaDto createModificaDisponibilita(DisponibilitaDto disponibilita);

    void cancellaDisponibilita(UUID idDisponibilita);

    Optional<DisponibilitaDto> findDisponibilitaById(UUID id);

    Optional<List<DisponibilitaDto>> findDisponibilitaByUsernameDocente(String username);

    List<DisponibilitaDto> recuperaDisponibilita();

}
