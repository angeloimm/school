package it.olegna.schoolmgmt.web.controller;

import it.olegna.schoolmgmt.dto.MateriaDto;
import it.olegna.schoolmgmt.dto.api.ApiResponse;
import it.olegna.schoolmgmt.service.MateriaSvc;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/public/materie")
@Slf4j
public class MateriaController {
    @Autowired
    private MateriaSvc materiaSvc;

    @GetMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<ApiResponse<List<MateriaDto>>> materie(@RequestParam(name = "q", required = false) String nome) {
        log.trace("Recupero tutti gli utenti");
        return ResponseEntity.ok(ApiResponse.<List<MateriaDto>>builder().error(false).payload(this.materiaSvc.recuperaMaterie(nome)).build());
    }

    @GetMapping(value = {"{idMateria}"}, consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<ApiResponse<MateriaDto>> materia(@PathVariable(required = true, name = "idMateria") String id) {
        log.info("Recupero i dettagli dell'Materia con ID {}", id);
        return ResponseEntity.ok(ApiResponse.<MateriaDto>builder().error(false).payload(this.materiaSvc.findMateriaById(id).orElseThrow()).build());
    }

    @DeleteMapping(value = {"{idMateria}"}, consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<ApiResponse<Void>> deleteMateria(@PathVariable(required = true, name = "idMateria") String id) {
        log.info("Cancello l'Materia con ID {}", id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<ApiResponse<MateriaDto>> creaMateria(@RequestBody MateriaDto materia) {
        return ResponseEntity.ok(ApiResponse.<MateriaDto>builder().payload(materiaSvc.createModificaMateria(materia)).build());
    }

    @PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<ApiResponse<MateriaDto>> modificaMateria(@RequestBody MateriaDto materia) {
        return ResponseEntity.ok(ApiResponse.<MateriaDto>builder().payload(materiaSvc.createModificaMateria(materia)).build());
    }
}
