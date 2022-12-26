package it.olegna.schoolmgmt.dto;


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
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AllegatoDto {
    @ToString.Include
    @EqualsAndHashCode.Include
    private UUID id;
    private String nomeFile;
    private String contentTypeFile;
    private Integer dimensioneFile;
    private UtenteDto utente;
}
