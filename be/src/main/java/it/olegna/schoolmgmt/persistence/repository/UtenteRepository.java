package it.olegna.schoolmgmt.persistence.repository;

import it.olegna.schoolmgmt.persistence.model.Utente;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtenteRepository extends MongoRepository<Utente, String> {
}
