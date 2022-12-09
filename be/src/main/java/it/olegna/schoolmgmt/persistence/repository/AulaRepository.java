package it.olegna.schoolmgmt.persistence.repository;

import it.olegna.schoolmgmt.persistence.model.Aula;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AulaRepository extends MongoRepository<Aula, String> {
}
