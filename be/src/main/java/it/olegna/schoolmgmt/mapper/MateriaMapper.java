package it.olegna.schoolmgmt.mapper;

import it.olegna.schoolmgmt.dto.MateriaDto;
import it.olegna.schoolmgmt.persistence.model.Materia;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MateriaMapper {
    MateriaDto toDto(Materia Materia);

    Materia toEntity(MateriaDto dto);

    List<MateriaDto> toDtos(List<Materia> entities);

    List<Materia> toEntities(List<MateriaDto> dtos);
}
