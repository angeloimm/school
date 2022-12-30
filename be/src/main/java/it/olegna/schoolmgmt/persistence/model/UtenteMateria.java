package it.olegna.schoolmgmt.persistence.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "UtenteMateria")
@Table(name = "UTENTE_MATERIA")
public class UtenteMateria {
    @ToString.Include
    @EqualsAndHashCode.Include
    @EmbeddedId
    private UtenteMateriaId id;
    @ManyToOne
    @MapsId("ID_UTENTE")
    @JoinColumn(name = "ID_UTENTE")
    private Utente utente;
    @ManyToOne
    @MapsId("ID_MATERIA")
    @JoinColumn(name = "ID_MATERIA")
    private Materia materia;
}