package it.olegna.schoolmgmt.persistence.repository;

import it.olegna.schoolmgmt.persistence.model.Materia;
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
public interface MateriaRepository extends JpaRepository<Materia, UUID>, JpaSpecificationExecutor<Materia> {
    Page findByNomeMateriaStartsWithIgnoreCase(@Param("nomeMateria") String nome, Pageable pageable);

    Optional<List<Materia>> findByNomeMateriaStartsWithIgnoreCase(@Param("nomeMateria") String nome, Sort s);
}
