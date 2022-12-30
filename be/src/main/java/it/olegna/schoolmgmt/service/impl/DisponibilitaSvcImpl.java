package it.olegna.schoolmgmt.service.impl;

import it.olegna.schoolmgmt.dto.DisponibilitaDto;
import it.olegna.schoolmgmt.mapper.DisponibilitaMapper;
import it.olegna.schoolmgmt.persistence.model.Disponibilita;
import it.olegna.schoolmgmt.persistence.model.Disponibilita_;
import it.olegna.schoolmgmt.persistence.model.Utente;
import it.olegna.schoolmgmt.persistence.repository.DisponibilitaRepository;
import it.olegna.schoolmgmt.service.DisponibilitaSvc;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class DisponibilitaSvcImpl implements DisponibilitaSvc {
    @Autowired
    private DisponibilitaRepository repository;
    @Autowired
    private DisponibilitaMapper mapper;

    @Override
    public DisponibilitaDto createModificaDisponibilita(DisponibilitaDto disponibilita) {
        log.trace("Salvo disponibilita {}", disponibilita);
        return mapper.toDto(repository.save(mapper.toEntity(disponibilita)));
    }

    @Override
    public void cancellaDisponibilita(UUID idDisponibilita) {
        log.info("Cancello disponibilita con ID {}", idDisponibilita);
        repository.deleteById(idDisponibilita);
    }

    @Override
    public Optional<DisponibilitaDto> findDisponibilitaById(UUID id) {
        log.info("Ricerco disponibilita con ID {}", id);
        Optional<Disponibilita> result = repository.findById(id);
        return result.isEmpty() ? Optional.empty() : Optional.of(mapper.toDto(result.get()));
    }

    @Override
    public Optional<List<DisponibilitaDto>> findDisponibilitaByUsernameDocente(String username) {
        log.info("Ricerco tutte le disponibilita di {}", username);
        List<Disponibilita> result = repository.findAll(this.findByUsernameDocente(username));
        return result.isEmpty() ? Optional.empty() : Optional.of(mapper.toDtos(result));
    }

    @Override
    public List<DisponibilitaDto> recuperaDisponibilita() {
        List<Disponibilita> result = repository.findAll(Sort.by(Sort.Order.asc(Disponibilita_.DATA_DISPONIBILITA)));
        return mapper.toDtos(result);
    }

    private Specification<Disponibilita> findByUsernameDocente(String usernameDocente) {
        return (root, query, criteriaBuilder) -> {
            Join<Disponibilita, Utente> joinDocente = root.join(Disponibilita_.DOCENTE);
            Predicate p = criteriaBuilder.equal(joinDocente.get("username"), usernameDocente);
            log.trace("Creato predicate {} per username docente {}", p, usernameDocente);
            return p;
        };
    }
}
