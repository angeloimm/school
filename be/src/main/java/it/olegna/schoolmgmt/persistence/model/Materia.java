package it.olegna.schoolmgmt.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Materia {
    @MongoId(FieldType.OBJECT_ID)
    private String id;
    @Indexed(unique = true, name = "nome-materia")
    private String materia;
}
