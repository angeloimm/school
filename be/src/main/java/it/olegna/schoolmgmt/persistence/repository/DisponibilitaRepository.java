package it.olegna.schoolmgmt.persistence.repository;

import it.olegna.schoolmgmt.persistence.model.Disponibilita;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DisponibilitaRepository extends MongoRepository<Disponibilita, String> {
    Optional<List<Disponibilita>> findByUsernameDocente(String usernameDocente);
}
