package it.olegna.schoolmgmt.web.controller;

import it.olegna.schoolmgmt.dto.UtenteDto;
import it.olegna.schoolmgmt.dto.api.ApiResponse;
import it.olegna.schoolmgmt.enums.TipoUtenteEnum;
import it.olegna.schoolmgmt.service.UtenteSvc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/public/init")
@Slf4j
public class InitDbController {
    @Autowired
    private UtenteSvc utenteSvc;

    @PostMapping(value = {"admin"},consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<ApiResponse<UtenteDto>> creaAmministratore(@RequestBody UtenteDto utente) {
        utente.setTipoUtente(TipoUtenteEnum.AMMINISTRATORE);
        utente.setAttivo(Boolean.TRUE);
        return ResponseEntity.ok(ApiResponse.<UtenteDto>builder().payload(utenteSvc.createModificaUtente(utente)).build());
    }
}
