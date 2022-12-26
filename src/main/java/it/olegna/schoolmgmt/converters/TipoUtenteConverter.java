package it.olegna.schoolmgmt.converters;

import it.olegna.schoolmgmt.enums.TipoUtenteEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.extern.slf4j.Slf4j;

@Converter
@Slf4j
public class TipoUtenteConverter implements AttributeConverter<TipoUtenteEnum, String> {

    @Override
    public String convertToDatabaseColumn(TipoUtenteEnum attribute) {
        switch (attribute) {
            case AMMINISTRATORE:
                return "A";
            case DOCENTE:
                return "D";
            case STUDENTE:
                return "S";
            default:
                throw new IllegalArgumentException("Tipo utente non riconosciuto. Valore attributo " + attribute);
        }
    }

    @Override
    public TipoUtenteEnum convertToEntityAttribute(String dbData) {
        switch (dbData) {
            case "A":
                return TipoUtenteEnum.AMMINISTRATORE;
            case "D":
                return TipoUtenteEnum.DOCENTE;
            case "S":
                return TipoUtenteEnum.STUDENTE;
            default:
                throw new IllegalArgumentException("Tipo utente non riconosciuto. Valore DB " + dbData);
        }
    }
}
