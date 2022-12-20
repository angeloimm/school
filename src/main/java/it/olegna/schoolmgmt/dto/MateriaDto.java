package it.olegna.schoolmgmt.dto;

import it.olegna.schoolmgmt.persistence.model.UtenteMateria;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
public class MateriaDto {
    @ToString.Include
    @EqualsAndHashCode.Include
    private UUID id;
    private String nomeMateria;
    private List<UtenteDto> utenti;
}
