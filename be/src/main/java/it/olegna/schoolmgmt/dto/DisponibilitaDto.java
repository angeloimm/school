package it.olegna.schoolmgmt.dto;

import it.olegna.schoolmgmt.enums.DisponibilitaEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
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
    private UUID id;
    private UtenteDto docente;
    private String usernameDocente;
    private Date dataDisponibilita;
    private DisponibilitaEnum arcoTemporaleDisponibilita;
}
