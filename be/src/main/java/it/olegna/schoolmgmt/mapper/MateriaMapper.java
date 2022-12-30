package it.olegna.schoolmgmt.mapper;

import it.olegna.schoolmgmt.dto.MateriaDto;
import it.olegna.schoolmgmt.persistence.model.Materia;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MateriaMapper {
    @Mapping(target = "utenti", ignore = true)
    MateriaDto toDto(Materia Materia);

    @Mapping(target = "utenti", ignore = true)
    Materia toEntity(MateriaDto dto);

    List<MateriaDto> toDtos(List<Materia> entities);

    List<Materia> toEntities(List<MateriaDto> dtos);
}
