package it.olegna.schoolmgmt.dto;

import it.olegna.schoolmgmt.enums.DisponibilitaEnum;
import it.olegna.schoolmgmt.persistence.model.Utente;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
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
