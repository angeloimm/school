package it.olegna.schoolmgmt.persistence.model;

import it.olegna.schoolmgmt.enums.TipoUtenteEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "UtenteEntity")
@Table(name = "UTENTE")
public class Utente {
    @ToString.Include
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private UUID id;
    @ToString.Include
    @EqualsAndHashCode.Include
    @Column(name = "NOME", nullable = false, length = 100)
    private String nome;
    @ToString.Include
    @EqualsAndHashCode.Include
    @Column(name = "COGNOME", nullable = false, length = 100)
    private String cognome;
    @ToString.Include
    @EqualsAndHashCode.Include
    @Column(name = "SESSO", nullable = false, length = 5)
    private String sesso;
    @ToString.Include
    @EqualsAndHashCode.Include
    @Column(name = "INDIRIZZO", nullable = false, length = 1000)
    private String indirizzo;
    @ToString.Include
    @EqualsAndHashCode.Include
    @Column(name = "CODICE_FISCALE", nullable = false, length = 16)
    private String codiceFiscale;
    @ToString.Include
    @EqualsAndHashCode.Include
    @Column(name = "TIPO_UTENTE", nullable = false, length = 50)
    private TipoUtenteEnum tipoUtente;
    @ToString.Include
    @EqualsAndHashCode.Include
    @Column(name = "DATA_NASCITA", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataNascita;
    @ToString.Include
    @EqualsAndHashCode.Include
    @Column(name = "TIPO_UTENTE", nullable = false, length = 100)
    private String username;
    @Column(name = "PASSWORD", nullable = false, length = 100)
    private String password;
    @ToString.Include
    @EqualsAndHashCode.Include
    @Column(name = "ATTIVO", nullable = false)
    private Boolean attivo;
    @OneToMany(mappedBy = "utente", fetch = FetchType.LAZY)
    private List<UtenteMateria> materie;
    @OneToMany(mappedBy = "utente", fetch = FetchType.LAZY)
    private List<Allegato> allegati;
}