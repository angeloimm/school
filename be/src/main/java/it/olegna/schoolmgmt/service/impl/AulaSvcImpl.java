package it.olegna.schoolmgmt.service.impl;

import it.olegna.schoolmgmt.persistence.model.Aula;
import it.olegna.schoolmgmt.persistence.repository.AulaRepository;
import it.olegna.schoolmgmt.service.AulaSvc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class AulaSvcImpl implements AulaSvc {
    @Autowired
    private AulaRepository repository;

    @Override
    public Aula createModificaAula(Aula aula) {
        log.trace("Salvo aula {}", aula);
        return repository.save(aula);
    }

    @Override
    public Optional<Aula> findAulaById(String id) {
        log.trace("Ricerco aula con ID {}", id);
        return repository.findById(id);
    }

    @Override
    public void cancellaAula(String idAula) {
        log.info("Cancello aula con ID {}", idAula);
        repository.deleteById(idAula);
    }

    @Override
    public List<Aula> recuperaAule() {
        log.trace("Recupero tutte le aule");
        return repository.findAll(Sort.by(Sort.Order.asc("materia")));
    }
}
