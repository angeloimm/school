package it.olegna.schoolmgmt.persistence.repository;

import it.olegna.schoolmgmt.persistence.model.Disponibilita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DisponibilitaRepository extends JpaRepository<Disponibilita, UUID>, JpaSpecificationExecutor<Disponibilita> {
    //Optional<List<Disponibilita>> findByUsernameDocente(String usernameDocente);
}
