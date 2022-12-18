package it.olegna.schoolmgmt.dto;

import it.olegna.schoolmgmt.enums.TipoUtenteEnum;
import it.olegna.schoolmgmt.persistence.model.Allegato;
import it.olegna.schoolmgmt.persistence.model.Disponibilita;
import it.olegna.schoolmgmt.persistence.model.StudenteCorso;
import it.olegna.schoolmgmt.persistence.model.UtenteMateria;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UtenteDto {
    @ToString.Include
    @EqualsAndHashCode.Include
    private UUID id;
    @ToString.Include
    @EqualsAndHashCode.Include
    private String nome;
    @ToString.Include
    @EqualsAndHashCode.Include
    private String cognome;
    @ToString.Include
    @EqualsAndHashCode.Include
    private String sesso;
    @ToString.Include
    @EqualsAndHashCode.Include
    private String indirizzo;
    @ToString.Include
    @EqualsAndHashCode.Include
    private String codiceFiscale;
    @ToString.Include
    @EqualsAndHashCode.Include
    private TipoUtenteEnum tipoUtente;
    @ToString.Include
    @EqualsAndHashCode.Include
    private Date dataNascita;
    @ToString.Include
    @EqualsAndHashCode.Include
    private String username;
    private String password;
    @ToString.Include
    @EqualsAndHashCode.Include
    private Boolean attivo;
    private List<UtenteMateria> materie;
    private List<Allegato> allegati;
    private List<Disponibilita> disponibilita;
    private List<StudenteCorso> corsi;
}