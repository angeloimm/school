package it.olegna.schoolmgmt.service.impl;

import it.olegna.schoolmgmt.dto.FileUploadResponseDto;
import it.olegna.schoolmgmt.dto.UtenteDto;
import it.olegna.schoolmgmt.dto.UtenteTableDto;
import it.olegna.schoolmgmt.dto.UtenteWithAttachDto;
import it.olegna.schoolmgmt.enums.TipoUtenteEnum;
import it.olegna.schoolmgmt.mapper.UtenteMapper;
import it.olegna.schoolmgmt.persistence.model.Allegato;
import it.olegna.schoolmgmt.persistence.model.Utente;
import it.olegna.schoolmgmt.persistence.model.Utente_;
import it.olegna.schoolmgmt.persistence.repository.AllegatoRepository;
import it.olegna.schoolmgmt.persistence.repository.UtenteRepository;
import it.olegna.schoolmgmt.service.UtenteSvc;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class UtenteSvcImpl implements UtenteSvc {
    @Autowired
    private UtenteRepository repository;
    @Autowired
    private AllegatoRepository allegatoRepository;
    @Autowired
    private UtenteMapper mapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = false)
    public UtenteDto createModificaUtente(UtenteDto utente) {
        log.trace("Creazione utente {}", utente);
        String originalPwd = utente.getPassword();
        utente.setPassword(passwordEncoder.encode(originalPwd));
        return mapper.toDto(repository.save(mapper.toEntity(utente)));
    }

    @Override
    @Transactional(readOnly = false)
    public UtenteDto createUtenteWithAttach(UtenteWithAttachDto utente) {
        String originalPwd = utente.getPassword();
        utente.setPassword(passwordEncoder.encode(originalPwd));
        Utente saved = repository.save(mapper.toEntityFromAttached(utente));
        if( !utente.getAllegati().isEmpty() ) {
            try {

                List<Allegato> allegati = new ArrayList<>(utente.getAllegati().size());
                for (UUID idAllegato : utente.getAllegati()) {
                    Allegato reference = allegatoRepository.getReferenceById(idAllegato);
                    reference.setUtente(saved);
                    allegati.add(reference);
                }
                allegatoRepository.saveAll(allegati);
            }catch (Exception e){
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UtenteDto> findUtenteById(UUID idUtente) {
        log.info("Ricerco utente con ID {}", idUtente);
        Optional<Utente> result = this.repository.findById(idUtente);
        return result.isEmpty() ? Optional.empty() : Optional.of(mapper.toDto(result.get()));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Utente> findUtenteByUsername(String username) {
        return this.repository.findByUsername(username);
    }

    @Override
    @Transactional(readOnly = false)
    public void cancellaUtente(UUID idUtente) {
        log.info("Cancellazione utente con ID {}", idUtente);
        repository.deleteById(idUtente);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UtenteDto> recuperaUtenti() {
        List<Sort.Order> ordinamento = new ArrayList<>();
        ordinamento.add(Sort.Order.asc(Utente_.NOME));
        ordinamento.add(Sort.Order.asc(Utente_.COGNOME));
        return mapper.toDtos(repository.findAll(Sort.by(ordinamento)));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<UtenteTableDto>> findByTipoUtente(TipoUtenteEnum tipoUtenteEnum) {
        List<Sort.Order> ordinamento = new ArrayList<>();
        ordinamento.add(Sort.Order.asc(Utente_.NOME));
        ordinamento.add(Sort.Order.asc(Utente_.COGNOME));
        List<UtenteTableDto> utentePage = this.repository.findByTipoUtente(tipoUtenteEnum, Sort.by(ordinamento));
        if( utentePage.isEmpty() ) {
            return Optional.empty();
        }
        return Optional.of(utentePage);
    }
    @Override
    @Transactional(readOnly = true)
    public Page<UtenteTableDto> findByTipoUtente(TipoUtenteEnum tipoUtenteEnum, Pageable pageable) {
        return this.repository.findByTipoUtente(tipoUtenteEnum, pageable);
    }
    @Override
    @Transactional(readOnly = true)
    public boolean intiDb() {
        return this.repository.count() <= 0;
    }
}