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
public class UtenteMateriaId {
    @Column(name = "ID_UTENTE", nullable = false)
    private UUID idUtente;
    @Column(name = "ID_MATERIA", nullable = false)
    private UUID idMateria;
}
