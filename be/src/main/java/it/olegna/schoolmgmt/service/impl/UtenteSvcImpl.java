package it.olegna.schoolmgmt.service.impl;

import it.olegna.schoolmgmt.dto.UtenteDto;
import it.olegna.schoolmgmt.mapper.UtenteMapper;
import it.olegna.schoolmgmt.persistence.model.Utente;
import it.olegna.schoolmgmt.persistence.model.Utente_;
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
    @Autowired
    private UtenteMapper mapper;

    @Override
    public UtenteDto createModificaUtente(UtenteDto utente) {
        log.trace("Creazione utente {}", utente);
        return mapper.toDto(repository.save(mapper.toEntity(utente)));
    }

    @Override
    public Optional<UtenteDto> findUtenteById(String idUtente) {
        log.info("Ricerco utente con ID {}", idUtente);
        Optional<Utente> result = this.repository.findById(idUtente);
        return result.isEmpty() ? Optional.empty() : Optional.of(mapper.toDto(result.get()));
    }

    @Override
    public void cancellaUtente(String idUtente) {
        log.info("Cancellazione utente con ID {}", idUtente);
        repository.deleteById(idUtente);
    }

    @Override
    public List<UtenteDto> recuperaUtenti() {
        List<Sort.Order> ordinamento = new ArrayList<>();
        ordinamento.add(Sort.Order.asc(Utente_.NOME));
        ordinamento.add(Sort.Order.asc(Utente_.COGNOME));
        return mapper.toDtos(repository.findAll(Sort.by(ordinamento)));
    }
}