package it.olegna.schoolmgmt.service.impl;

import it.olegna.schoolmgmt.dto.CorsoDto;
import it.olegna.schoolmgmt.mapper.CorsoMapper;
import it.olegna.schoolmgmt.persistence.model.Corso;
import it.olegna.schoolmgmt.persistence.repository.CorsoRepository;
import it.olegna.schoolmgmt.service.AulaSvc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class AulaSvcImpl implements AulaSvc {
    @Autowired
    private CorsoRepository repository;
    @Autowired
    private CorsoMapper mapper;

    @Override
    @Transactional
    public CorsoDto createModificaAula(CorsoDto aula) {
        log.trace("Salvo aula {}", aula);
        return mapper.toDto(repository.save(mapper.toEntity(aula)));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CorsoDto> findAulaById(UUID id) {
        log.trace("Ricerco aula con ID {}", id);
        Optional<Corso> result = repository.findById(id);
        return result.isPresent() ? Optional.of(mapper.toDto(result.get())) : Optional.empty();
    }

    @Override
    @Transactional
    public void cancellaAula(UUID idAula) {
        log.info("Cancello aula con ID {}", idAula);
        repository.deleteById(idAula);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CorsoDto> recuperaAule() {
        log.trace("Recupero tutte le aule");
        return mapper.toDtos(repository.findAll());
    }
}