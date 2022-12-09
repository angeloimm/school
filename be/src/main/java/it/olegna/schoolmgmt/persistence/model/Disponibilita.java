package it.olegna.schoolmgmt.persistence.model;

import it.olegna.schoolmgmt.enums.DisponibilitaEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Disponibilita {
    @MongoId(FieldType.OBJECT_ID)
    private String id;
    @DBRef(lazy = false)
    private Utente docente;
    private String usernameDocente;
    private Date dataDisponibilita;
    private DisponibilitaEnum arcoTemporaleDisponibilita;
}
