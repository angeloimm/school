package it.olegna.schoolmgmt.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CorsoDto {
    private UUID id;
    private List<UtenteDto> studenti;
    private MateriaDto materiaDto;
}
