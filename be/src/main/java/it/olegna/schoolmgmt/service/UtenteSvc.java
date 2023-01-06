package it.olegna.schoolmgmt.service;

import it.olegna.schoolmgmt.dto.UtenteDto;
import it.olegna.schoolmgmt.dto.UtenteTableDto;
import it.olegna.schoolmgmt.dto.UtenteWithAttachDto;
import it.olegna.schoolmgmt.enums.TipoUtenteEnum;
import it.olegna.schoolmgmt.persistence.model.Utente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UtenteSvc {
    UtenteDto createModificaUtente(UtenteDto utente, List<MultipartFile> attaches);

    UtenteDto createUtenteWithAttach(UtenteWithAttachDto utente);

    Optional<UtenteDto> findUtenteById(UUID idUtente);

    Optional<Utente> findUtenteByUsername(String username);

    void cancellaUtente(UUID idUtente);

    boolean intiDb();

    List<UtenteDto> recuperaUtenti();

    Optional<List<UtenteTableDto>> findByTipoUtente(TipoUtenteEnum tipoUtenteEnum);

    Page<UtenteTableDto> findByTipoUtente(TipoUtenteEnum tipoUtenteEnum, Pageable pageable, String nomeCognome);

    Boolean usernameValid(String value);
}