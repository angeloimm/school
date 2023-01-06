package it.olegna.schoolmgmt.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import it.olegna.schoolmgmt.dto.MateriaDto;
import it.olegna.schoolmgmt.dto.MateriaTableDto;
import it.olegna.schoolmgmt.dto.UtenteTableDto;
import it.olegna.schoolmgmt.dto.api.ApiResponse;
import it.olegna.schoolmgmt.dto.api.PagedApiResponse;
import it.olegna.schoolmgmt.dto.ricerca.utente.RicercaTabelleUtils;
import it.olegna.schoolmgmt.service.MateriaSvc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;
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
import java.util.UUID;

@RestController
@RequestMapping("/protected/materie")
@Slf4j
public class MateriaController {
    @Autowired
    private MateriaSvc materiaSvc;
    @Autowired
    private MappingJackson2HttpMessageConverter springMvcJacksonConverter;

    @GetMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<PagedApiResponse<List<MateriaTableDto>>> materie(@RequestParam(name = "q", required = false) String q, @RequestParam(name = "nomeMateria", required = false)String materia) throws JsonProcessingException {
        String decoded = new String(Base64Utils.decodeFromString(q));
        RicercaTabelleUtils ruu = this.springMvcJacksonConverter.getObjectMapper().readValue(decoded, RicercaTabelleUtils.class);
        Sort sort = null;
        if(StringUtils.hasText(ruu.getEvent().getSortField())) {
            String sortField = ruu.getEvent().getSortField();
            switch (ruu.getEvent().getSortOrder()) {
                case 1: {

                    sort = Sort.by(Sort.Order.asc(sortField));
                    break;
                }
                case -1: {

                    sort = Sort.by(Sort.Order.desc(sortField));
                    break;
                }
                default:
                    throw new IllegalArgumentException("Valore ordinamento inaspettato: " + ruu.getEvent().getSortOrder());
            }
        }
        PageRequest pageRequest = null;
        if (sort != null) {
            pageRequest = PageRequest.of(ruu.getEvent().getFirst(), ruu.getEvent().getRows(), sort);
        } else {
            pageRequest = PageRequest.of(ruu.getEvent().getFirst(), ruu.getEvent().getRows());
        }
        Page<MateriaTableDto> materie = this.materiaSvc.recuperaMaterie(materia, pageRequest);
        return ResponseEntity.ok(PagedApiResponse.<List<MateriaTableDto>>builder()
                .totalRecords(materie.getTotalElements())
                .payload(materie.getContent()).build());
    }

    @GetMapping(value = {"{idMateria}"}, consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<ApiResponse<MateriaDto>> materia(@PathVariable(required = true, name = "idMateria") UUID id) {
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
