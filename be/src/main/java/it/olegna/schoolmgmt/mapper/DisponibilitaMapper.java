package it.olegna.schoolmgmt.mapper;

import it.olegna.schoolmgmt.dto.DisponibilitaDto;
import it.olegna.schoolmgmt.persistence.model.Disponibilita;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DisponibilitaMapper {
    DisponibilitaDto toDto(Disponibilita disponibilita);

    Disponibilita toEntity(DisponibilitaDto dto);

    List<DisponibilitaDto> toDtos(List<Disponibilita> entities);

    List<Disponibilita> toEntities(List<DisponibilitaDto> dtos);
}
