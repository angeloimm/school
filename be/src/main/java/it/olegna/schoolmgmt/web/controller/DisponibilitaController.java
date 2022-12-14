package it.olegna.schoolmgmt.web.controller;

import it.olegna.schoolmgmt.dto.ApiResponse;
import it.olegna.schoolmgmt.dto.DisponibilitaDto;
import it.olegna.schoolmgmt.service.DisponibilitaSvc;
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
@RequestMapping("/public/disponibilita")
@Slf4j
public class DisponibilitaController {
    @Autowired
    private DisponibilitaSvc disponibilitaSvc;

    @GetMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<ApiResponse<List<DisponibilitaDto>>> disponibilita() {
        log.trace("Recupero tutti gli utenti");
        return ResponseEntity.ok(ApiResponse.<List<DisponibilitaDto>>builder().error(false).payload(this.disponibilitaSvc.recuperaDisponibilita()).build());
    }

    @GetMapping(value = {"{usernameDocente}"}, consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<ApiResponse<List<DisponibilitaDto>>> disponibilitaDocente(@PathVariable(name = "usernameDocente", required = true) String usernameDocente) {
        log.trace("Recupero tutti gli utenti");
        return ResponseEntity.ok(ApiResponse.<List<DisponibilitaDto>>builder().error(false).payload(this.disponibilitaSvc.findDisponibilitaByUsernameDocente(usernameDocente).orElseThrow()).build());
    }

    @GetMapping(value = {"{idDisponibilita}"}, consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<ApiResponse<DisponibilitaDto>> Disponibilita(@PathVariable(required = true, name = "idDisponibilita") String id) {
        log.info("Recupero i dettagli dell'Disponibilita con ID {}", id);
        return ResponseEntity.ok(ApiResponse.<DisponibilitaDto>builder().error(false).payload(this.disponibilitaSvc.findDisponibilitaById(id).orElseThrow()).build());
    }

    @DeleteMapping(value = {"{idDisponibilita}"}, consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<ApiResponse<Void>> deleteDisponibilita(@PathVariable(required = true, name = "idDisponibilita") String id) {
        log.info("Cancello l'Disponibilita con ID {}", id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<ApiResponse<DisponibilitaDto>> creaDisponibilita(@RequestBody DisponibilitaDto disponibilita) {
        return ResponseEntity.ok(ApiResponse.<DisponibilitaDto>builder().payload(disponibilitaSvc.createModificaDisponibilita(disponibilita)).build());
    }

    @PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<ApiResponse<DisponibilitaDto>> modificaDisponibilita(@RequestBody DisponibilitaDto disponibilita) {
        return ResponseEntity.ok(ApiResponse.<DisponibilitaDto>builder().payload(disponibilitaSvc.createModificaDisponibilita(disponibilita)).build());
    }
}
