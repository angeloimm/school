package it.olegna.schoolmgmt.dto;


import it.olegna.schoolmgmt.persistence.model.Disponibilita;
import it.olegna.schoolmgmt.persistence.model.StudenteCorso;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CorsoDto {
    @ToString.Include
    @EqualsAndHashCode.Include
    private UUID id;
    private DisponibilitaDto disponibilita;
    private List<UtenteDto> studenti;
}
