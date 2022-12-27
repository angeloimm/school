package it.olegna.schoolmgmt.persistence.repository;

import it.olegna.schoolmgmt.persistence.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, String>, JpaSpecificationExecutor<Utente> {
    Optional<Utente> findByUsername(@Param("username") String na);

    long countByUsername(@Param("username") String uName);
}
