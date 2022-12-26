package it.olegna.schoolmgmt.web.controller;

import it.olegna.schoolmgmt.dto.CorsoDto;
import it.olegna.schoolmgmt.dto.api.ApiResponse;
import it.olegna.schoolmgmt.service.AulaSvc;
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
@RequestMapping("/public/aule")
@Slf4j
public class AulaController {
    @Autowired
    private AulaSvc aulaSvc;

    @GetMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<ApiResponse<List<CorsoDto>>> aule() {
        log.trace("Recupero tutti gli utenti");
        return ResponseEntity.ok(ApiResponse.<List<CorsoDto>>builder().error(false).payload(this.aulaSvc.recuperaAule()).build());
    }

    @GetMapping(value = {"{idAula}"}, consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<ApiResponse<CorsoDto>> aula(@PathVariable(required = true, name = "idAula") String id) {
        log.info("Recupero i dettagli dell'aula con ID {}", id);
        return ResponseEntity.ok(ApiResponse.<CorsoDto>builder().error(false).payload(this.aulaSvc.findAulaById(id).orElseThrow()).build());
    }

    @DeleteMapping(value = {"{idAula}"}, consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<ApiResponse<Void>> deleteAula(@PathVariable(required = true, name = "idAula") String id) {
        log.info("Cancello l'Aula con ID {}", id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<ApiResponse<CorsoDto>> creaAula(@RequestBody CorsoDto aula) {
        return ResponseEntity.ok(ApiResponse.<CorsoDto>builder().payload(aulaSvc.createModificaAula(aula)).build());
    }

    @PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<ApiResponse<CorsoDto>> modificaAula(@RequestBody CorsoDto aula) {
        return ResponseEntity.ok(ApiResponse.<CorsoDto>builder().payload(aulaSvc.createModificaAula(aula)).build());
    }
}
