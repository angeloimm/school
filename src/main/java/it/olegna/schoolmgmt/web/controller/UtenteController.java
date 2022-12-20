package it.olegna.schoolmgmt.web.controller;

import it.olegna.schoolmgmt.dto.api.ApiResponse;
import it.olegna.schoolmgmt.dto.UtenteDto;
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
@RequestMapping("/public/utenti")
@Slf4j
public class UtenteController {
    @Autowired
    private UtenteSvc utenteSvc;

    @GetMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<ApiResponse<List<UtenteDto>>> utenti() {
        log.trace("Recupero tutti gli utenti");
        return ResponseEntity.ok(ApiResponse.<List<UtenteDto>>builder().error(false).payload(this.utenteSvc.recuperaUtenti()).build());
    }

    @GetMapping(value = {"{idUtente}"}, consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<ApiResponse<UtenteDto>> utente(@PathVariable(required = true, name = "idUtente") String id) {
        log.info("Recupero i dettagli dell'utente con ID {}", id);
        return ResponseEntity.ok(ApiResponse.<UtenteDto>builder().error(false).payload(this.utenteSvc.findUtenteById(id).orElseThrow()).build());
    }

    @DeleteMapping(value = {"{idUtente}"}, consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<ApiResponse<Void>> deleteUtente(@PathVariable(required = true, name = "idUtente") String id) {
        log.info("Cancello l'utente con ID {}", id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<ApiResponse<UtenteDto>> creaUtente(@RequestBody UtenteDto utente) {
        return ResponseEntity.ok(ApiResponse.<UtenteDto>builder().payload(utenteSvc.createModificaUtente(utente)).build());
    }

    @PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<ApiResponse<UtenteDto>> modificaUtente(@RequestBody UtenteDto utente) {
        return ResponseEntity.ok(ApiResponse.<UtenteDto>builder().payload(utenteSvc.createModificaUtente(utente)).build());
    }
}
