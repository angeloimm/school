package it.olegna.schoolmgmt.persistence.model;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

public class Aula {
    @MongoId(FieldType.OBJECT_ID)
    private String id;
    @DBRef(lazy = false)
    private List<Utente> studenti;
    @DBRef(lazy = false)
    private Materia materia;
}
