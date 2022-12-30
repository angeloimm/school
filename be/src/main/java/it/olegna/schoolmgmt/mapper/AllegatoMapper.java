package it.olegna.schoolmgmt.mapper;

import it.olegna.schoolmgmt.dto.AllegatoDto;
import it.olegna.schoolmgmt.persistence.model.Allegato;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AllegatoMapper {
    @Mapping(target = "idUtente", source = "utente.id")
    @Mapping(target = "multipartFile", ignore = true)
    AllegatoDto toDto(Allegato allegato);

    @Mapping(target = "utente", ignore = true)
    Allegato toEntity(AllegatoDto dto);

    List<AllegatoDto> toDtos(List<Allegato> entities);

    List<Allegato> toEntities(List<AllegatoDto> dtos);
}
