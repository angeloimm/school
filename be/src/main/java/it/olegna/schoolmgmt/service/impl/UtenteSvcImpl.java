package it.olegna.schoolmgmt.service.impl;

import it.olegna.schoolmgmt.persistence.model.Utente;
import it.olegna.schoolmgmt.persistence.repository.UtenteRepository;
import it.olegna.schoolmgmt.service.UtenteSvc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UtenteSvcImpl implements UtenteSvc {
    @Autowired
    private UtenteRepository repository;

    @Override
    public Utente createModificaUtente(Utente utente) {
        log.trace("Creazione utente {}", utente);
        return repository.save(utente);
    }

    @Override
    public Optional<Utente> findUtenteById(String idUtente) {
        log.info("Ricerco utente con ID {}", idUtente);
        return this.repository.findById(idUtente);
    }

    @Override
    public void cancellaUtente(String idUtente) {
        log.info("Cancellazione utente con ID {}", idUtente);
        repository.deleteById(idUtente);
    }

    @Override
    public List<Utente> recuperaUtenti() {
        List<Sort.Order> ordinamento = new ArrayList<>();
        ordinamento.add(Sort.Order.asc("nome"));
        ordinamento.add(Sort.Order.asc("cognome"));
        return repository.findAll(Sort.by(ordinamento));
    }
}
