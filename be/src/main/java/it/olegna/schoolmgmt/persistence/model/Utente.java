package it.olegna.schoolmgmt.persistence.model;

import it.olegna.schoolmgmt.enums.TipoUtenteEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Utente {
    @MongoId(FieldType.OBJECT_ID)
    private String id;
    private String nome;
    private String cognome;
    private String sesso;
    private String indirizzo;
    private String codiceFiscale;
    private TipoUtenteEnum tipoUtente;
    private Date dataNascita;
    @DBRef(lazy = true)
    private List<Materia> materie;
    @Indexed(unique = true, name = "username-utente")
    private String username;
    @ToString.Exclude
    private String password;
    private Boolean attivo;
}