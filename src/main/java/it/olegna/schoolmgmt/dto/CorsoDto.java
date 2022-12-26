package it.olegna.schoolmgmt.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
    @ToString.Include
    @EqualsAndHashCode.Include
    private UUID id;
    private DisponibilitaDto disponibilita;
    private List<UtenteDto> studenti;
}
