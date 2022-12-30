package it.olegna.schoolmgmt.dto;

import it.olegna.schoolmgmt.enums.TipoUtenteEnum;
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
@EqualsAndHashCode
@ToString
public class UtenteWithAttachDto {
    private List<UUID> allegati;
    @ToString.Include
    @EqualsAndHashCode.Include
    private UUID id;
    @ToString.Include
    @EqualsAndHashCode.Include
    private String nome;
    @ToString.Include
    @EqualsAndHashCode.Include
    private String cognome;
    @ToString.Include
    @EqualsAndHashCode.Include
    private String sesso;
    @ToString.Include
    @EqualsAndHashCode.Include
    private String indirizzo;
    @ToString.Include
    @EqualsAndHashCode.Include
    private String codiceFiscale;
    @ToString.Include
    @EqualsAndHashCode.Include
    private TipoUtenteEnum tipoUtente;
    @ToString.Include
    @EqualsAndHashCode.Include
    private Date dataNascita;
    @ToString.Include
    @EqualsAndHashCode.Include
    private String username;
    private String password;
    @ToString.Include
    @EqualsAndHashCode.Include
    private Boolean attivo;
}
