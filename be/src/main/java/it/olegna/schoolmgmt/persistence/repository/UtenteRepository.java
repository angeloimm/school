package it.olegna.schoolmgmt.persistence.repository;

import it.olegna.schoolmgmt.dto.UtenteTableDto;
import it.olegna.schoolmgmt.enums.TipoUtenteEnum;
import it.olegna.schoolmgmt.persistence.model.Utente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, UUID>, JpaSpecificationExecutor<Utente> {
    Optional<Utente> findByUsername(@Param("username") String na);

    long countByUsername(@Param("username") String uName);

    long countByUsernameStartsWith(@Param("username") String uName);

    List<UtenteTableDto> findByTipoUtente(@Param("tipoUtente") TipoUtenteEnum tipoUtente, Sort sort);

    Page<UtenteTableDto> findByTipoUtente(@Param("tipoUtente") TipoUtenteEnum tipoUtente, Pageable pageable);
}
