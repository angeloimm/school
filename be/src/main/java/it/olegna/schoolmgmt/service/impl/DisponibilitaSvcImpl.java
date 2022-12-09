package it.olegna.schoolmgmt.service.impl;

import it.olegna.schoolmgmt.persistence.model.Disponibilita;
import it.olegna.schoolmgmt.persistence.repository.DisponibilitaRepository;
import it.olegna.schoolmgmt.service.DisponibilitaSvc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class DisponibilitaSvcImpl implements DisponibilitaSvc {
    @Autowired
    private DisponibilitaRepository repository;

    @Override
    public Disponibilita createModificaDisponibilita(Disponibilita disponibilita) {
        log.trace("Salvo disponibilita {}", disponibilita);
        return repository.save(disponibilita);
    }

    @Override
    public void cancellaDisponibilita(String idDisponibilita) {
        log.info("Cancello disponibilita con ID {}", idDisponibilita);
        repository.deleteById(idDisponibilita);
    }

    @Override
    public Optional<Disponibilita> findDisponibilitaById(String id) {
        log.info("Ricerco disponibilita con ID {}", id);
        return repository.findById(id);
    }

    @Override
    public Optional<List<Disponibilita>> findDisponibilitaByUsernameDocente(String username) {
        log.info("Ricerco tutte le disponibilita di {}", username);
        return repository.findByUsernameDocente(username);
    }

    @Override
    public List<Disponibilita> recuperaDisponibilita() {
        return repository.findAll(Sort.by(Sort.Order.asc("data")));
    }
}
