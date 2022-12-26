package it.olegna.schoolmgmt.dto;

import it.olegna.schoolmgmt.enums.DisponibilitaEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class DisponibilitaDto {
    @ToString.Include
    @EqualsAndHashCode.Include
    private UUID id;
    private UtenteDto docente;
    @ToString.Include
    @EqualsAndHashCode.Include
    private Date dataDisponibilita;
    @ToString.Include
    @EqualsAndHashCode.Include
    private DisponibilitaEnum arcoTemporaleDisponibilita;
}
