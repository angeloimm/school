package it.olegna.schoolmgmt.persistence.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
@Embeddable
public class StudenteCorsoId {
    @Column(name = "ID_STUDENTE", nullable = false)
    private UUID idStudente;
    @Column(name = "ID_CORSO", nullable = false)
    private UUID idCorso;
}
