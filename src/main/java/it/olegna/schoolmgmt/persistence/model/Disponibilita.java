package it.olegna.schoolmgmt.persistence.model;

import it.olegna.schoolmgmt.enums.DisponibilitaEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "DisponibilitaEntity")
@Table(name = "DISPONIBILITA")
public class Disponibilita {
    @ToString.Include
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private UUID id;
    @ManyToOne(targetEntity = Utente.class)
    @JoinColumn(name = "ID_DOCENTE")
    private Utente docente;
    @ToString.Include
    @EqualsAndHashCode.Include
    @Column(name = "DATA_DISPONIBILITA", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataDisponibilita;
    @ToString.Include
    @EqualsAndHashCode.Include
    @Column(name = "ARCO_TEMPORALE_DISP", nullable = false, length = 100)
    private DisponibilitaEnum arcoTemporaleDisponibilita;
}