package it.olegna.schoolmgmt.persistence.repository;

import it.olegna.schoolmgmt.persistence.model.StudenteCorso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface StudenteCorsoRepository extends JpaRepository<StudenteCorso, String>, JpaSpecificationExecutor<StudenteCorso> {
}
