package it.olegna.schoolmgmt.dto;

import it.olegna.schoolmgmt.enums.TipoUtenteEnum;
import lombok.ToString;
import lombok.Value;

import java.util.Date;
import java.util.UUID;

@Value
@ToString
public class UtenteTableDto {
    private UUID id;
    private String nome;
    private String cognome;
    private String sesso;
    private String indirizzo;
    private String codiceFiscale;
    private Date dataNascita;
    private TipoUtenteEnum tipoUtente;
}