--Tabella utente
CREATE TABLE PUBLIC.UTENTE (
	ID UUID NOT NULL,
	NOME CHARACTER VARYING(100) NOT NULL,
	COGNOME CHARACTER VARYING(100) NOT NULL,
	SESSO CHARACTER VARYING(5) NOT NULL,
	INDIRIZZO CHARACTER VARYING(1000) NOT NULL,
	CODICE_FISCALE CHARACTER VARYING(16) NOT NULL,
	TIPO_UTENTE CHARACTER VARYING(50) NOT NULL,
	DATA_NASCITA DATE NOT NULL,
	USERNAME CHARACTER VARYING(100) NOT NULL,
	PASSWORD CHARACTER VARYING(100) NOT NULL,
	ATTIVO BOOLEAN NOT NULL,
	CONSTRAINT UTENTE_PK PRIMARY KEY (ID)
);
CREATE UNIQUE INDEX PRIMARY_KEY_9 ON PUBLIC.UTENTE (ID);
CREATE INDEX UTENTE_CODICE_FISCALE_IDX ON PUBLIC.UTENTE (CODICE_FISCALE);
CREATE INDEX UTENTE_COGNOME_IDX ON PUBLIC.UTENTE (COGNOME);
CREATE INDEX UTENTE_NOME_IDX ON PUBLIC.UTENTE (NOME);
CREATE INDEX UTENTE_SESSO_IDX ON PUBLIC.UTENTE (SESSO);
CREATE INDEX UTENTE_TIPO_UTENTE_IDX ON PUBLIC.UTENTE (TIPO_UTENTE);
CREATE INDEX UTENTE_USERNAME_IDX ON PUBLIC.UTENTE (USERNAME);

--tabella allegato
CREATE TABLE PUBLIC.ALLEGATO (
	ID UUID NOT NULL,
	NOME_FILE CHARACTER VARYING(100) NOT NULL,
	CONTENT_TYPE_FILE CHARACTER VARYING(100) NOT NULL,
	DIMENSIONE_FILE INTEGER NOT NULL,
	CONTENUTO_FILE BINARY LARGE OBJECT NOT NULL,
	ID_UTENTE UUID NOT NULL,
	CONSTRAINT ALLEGATO_PK PRIMARY KEY (ID),
	CONSTRAINT ALLEGATO_UTENTE_FK FOREIGN KEY (ID_UTENTE) REFERENCES PUBLIC.UTENTE(ID)
);
CREATE INDEX ALLEGATO_ID_UTENTE_IDX ON PUBLIC.ALLEGATO (ID_UTENTE);

--tabella materia
CREATE TABLE PUBLIC.MATERIA (
	ID UUID NOT NULL,
	NOME_MATERIA CHARACTER VARYING(100) NOT NULL,
	CONSTRAINT MATERIA_PK PRIMARY KEY (ID)
);
CREATE INDEX MATERIA_NOME_MATERIA_IDX ON PUBLIC.MATERIA (NOME_MATERIA);

--tabella materia_utente
CREATE TABLE PUBLIC.UTENTE_MATERIA (
	ID_UTENTE UUID NOT NULL,
	ID_MATERIA UUID NOT NULL,
	CONSTRAINT UTENTE_MATERIA_PK PRIMARY KEY (ID_UTENTE,ID_MATERIA),
	CONSTRAINT UTENTE_MATERIA_FK FOREIGN KEY (ID_UTENTE) REFERENCES PUBLIC.UTENTE(ID),
	CONSTRAINT UTENTE_MATERIA_FK_1 FOREIGN KEY (ID_MATERIA) REFERENCES PUBLIC.MATERIA(ID)
);
CREATE INDEX UTENTE_MATERIA_ID_UTENTE_IDX ON PUBLIC.UTENTE_MATERIA (ID_UTENTE);
CREATE INDEX UTENTE_MATERIA_ID_MATERIA_IDX ON PUBLIC.UTENTE_MATERIA (ID_MATERIA);

--tabella disponibilita
CREATE TABLE PUBLIC.DISPONIBILITA (
	ID UUID NOT NULL,
	ID_UTENTE UUID NOT NULL,
	DATA_DISPONIBILITA DATE NOT NULL,
  ARCO_TEMPORALE_DISP CHARACTER VARYING(100) NOT NULL,
	CONSTRAINT DISPONIBILITA_PK PRIMARY KEY (ID),
	CONSTRAINT DISPONIBILITA_FK FOREIGN KEY (ID_UTENTE) REFERENCES PUBLIC.UTENTE(ID)
);
CREATE INDEX DISPONIBILITA_ID_UTENTE_IDX ON PUBLIC.DISPONIBILITA (ID_UTENTE);

--tabella corso
CREATE TABLE PUBLIC.CORSO (
	ID UUID NOT NULL,
	ID_DISPONIBILITA UUID NOT NULL,
	CONSTRAINT CORSO_PK PRIMARY KEY (ID),
	CONSTRAINT CORSO_FK FOREIGN KEY (ID_DISPONIBILITA) REFERENCES PUBLIC.DISPONIBILITA(ID)
);
CREATE INDEX CORSO_ID_DISPONIBILITA_IDX ON PUBLIC.CORSO (ID_DISPONIBILITA);