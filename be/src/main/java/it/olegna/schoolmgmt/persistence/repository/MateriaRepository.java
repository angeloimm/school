package it.olegna.schoolmgmt.persistence.repository;

import it.olegna.schoolmgmt.persistence.model.Materia;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MateriaRepository extends MongoRepository<Materia, String> {
    List<Materia> findByMateriaStartsWith(String token, Sort ordinamento);
}
