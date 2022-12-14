package it.olegna.schoolmgmt.dto;

import it.olegna.schoolmgmt.enums.TipoUtenteEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
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
    private UUID id;
    private String nome;
    private String cognome;
    private String sesso;
    private String indirizzo;
    private String codiceFiscale;
    private TipoUtenteEnum tipoUtente;
    private Date dataNascita;
    private List<MateriaDto> materie;
    private String username;
    @ToString.Exclude
    private String password;
    private Boolean attivo;
}