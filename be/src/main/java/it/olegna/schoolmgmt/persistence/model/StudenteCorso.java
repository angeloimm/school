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
@Entity(name = "StudenteCorso")
@Table(name = "STUDENTE_CORSO")
public class StudenteCorso {
    @ToString.Include
    @EqualsAndHashCode.Include
    @EmbeddedId
    private StudenteCorsoId id;
    @ManyToOne
    @MapsId("ID_STUDENTE")
    @JoinColumn(name = "ID_STUDENTE")
    private Utente studente;
    @ManyToOne
    @MapsId("ID_CORSO")
    @JoinColumn(name = "ID_CORSO")
    private Corso corso;
}