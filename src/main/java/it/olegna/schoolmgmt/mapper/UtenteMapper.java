package it.olegna.schoolmgmt.mapper;

import it.olegna.schoolmgmt.dto.UtenteDto;
import it.olegna.schoolmgmt.persistence.model.Utente;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UtenteMapper {
    UtenteDto toDto(Utente utente);

    Utente toEntity(UtenteDto dto);

    List<UtenteDto> toDtos(List<Utente> entities);

    List<Utente> toEntities(List<UtenteDto> dtos);
}
