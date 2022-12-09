package it.olegna.schoolmgmt.service.impl;

import it.olegna.schoolmgmt.persistence.model.Materia;
import it.olegna.schoolmgmt.persistence.repository.MateriaRepository;
import it.olegna.schoolmgmt.service.MateriaSvc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class MateriaSvcImpl implements MateriaSvc {
    @Autowired
    private MateriaRepository repository;
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Materia createModificaMateria(Materia materia) {
        log.trace("Salvo materia {}", materia);
        return repository.save(materia);
    }

    @Override
    public Optional<Materia> findMateriaById(String id) {
        log.info("Ricrco materia con ID {}", id);
        return this.repository.findById(id);
    }

    @Override
    public void cancellaMateria(String idMateria) {
        log.info("Cancellazione materia con ID {}", idMateria);
        repository.deleteById(idMateria);
    }

    @Override
    public List<Materia> recuperaMaterie(String nome) {
        if (!StringUtils.hasText(nome)) {
            return repository.findAll(Sort.by(Sort.Order.asc("materia")));
        }
        return repository.findByMateriaStartsWith(nome, Sort.by(Sort.Order.asc("materia")));
    }
}
