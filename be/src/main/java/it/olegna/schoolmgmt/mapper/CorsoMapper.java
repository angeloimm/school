package it.olegna.schoolmgmt.mapper;

import it.olegna.schoolmgmt.dto.CorsoDto;
import it.olegna.schoolmgmt.persistence.model.Corso;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CorsoMapper {
    CorsoDto toDto(Corso corso);

    Corso toEntity(CorsoDto dto);

    List<CorsoDto> toDtos(List<Corso> entities);

    List<Corso> toEntities(List<CorsoDto> dtos);
}
