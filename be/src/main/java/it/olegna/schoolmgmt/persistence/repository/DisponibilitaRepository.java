package it.olegna.schoolmgmt.persistence.repository;

import it.olegna.schoolmgmt.persistence.model.Disponibilita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DisponibilitaRepository extends JpaRepository<Disponibilita, String> {
    Optional<List<Disponibilita>> findByUsernameDocente(String usernameDocente);
}
