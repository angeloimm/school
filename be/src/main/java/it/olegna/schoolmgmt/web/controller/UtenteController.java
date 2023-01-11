package it.olegna.schoolmgmt.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import it.olegna.schoolmgmt.dto.UtenteDto;
import it.olegna.schoolmgmt.dto.UtenteTableDto;
import it.olegna.schoolmgmt.dto.UtenteWithAttachDto;
import it.olegna.schoolmgmt.dto.api.ApiResponse;
import it.olegna.schoolmgmt.dto.api.PagedApiResponse;
import it.olegna.schoolmgmt.dto.ricerca.utente.RicercaTabelleUtils;
import it.olegna.schoolmgmt.enums.TipoUtenteEnum;
import it.olegna.schoolmgmt.service.UtenteSvc;
import jakarta.servlet.http.HttpServletRequest;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/protected/utenti")
@Slf4j
public class UtenteController {
    @Autowired
    private UtenteSvc utenteSvc;
    @Autowired
    private MappingJackson2HttpMessageConverter springMvcJacksonConverter;
    @Autowired
    private HttpServletRequest req;

    @GetMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<PagedApiResponse<List<UtenteTableDto>>> utenti(@RequestParam(name = "q", required = true) String q, @RequestParam(name = "nome-cognome", required = false) String nomeCognome) throws JsonProcessingException {
        String decoded = new String(Base64Utils.decodeFromString(q));
        RicercaTabelleUtils ruu = this.springMvcJacksonConverter.getObjectMapper().readValue(decoded, RicercaTabelleUtils.class);
        log.info("Ricerco gli utenti per tipo utente {}", ruu.getTipoUtente());
        TipoUtenteEnum tipoUtenteEnum = null;
        switch (ruu.getTipoUtente()) {
            case "S":
                tipoUtenteEnum = TipoUtenteEnum.STUDENTE;
                break;
            case "D":
                tipoUtenteEnum = TipoUtenteEnum.DOCENTE;
                break;
            case "A":
                tipoUtenteEnum = TipoUtenteEnum.AMMINISTRATORE;
                break;
            default:
                throw new IllegalArgumentException("Tipo utente non riconosciuto " + ruu.getTipoUtente() + ". Valori ammessi: S, D e A");
        }
        log.trace("Recupero gli utenti con questa query {} e questo oggetto {}; b64 query {}", decoded, ruu, q);
        PageRequest pageRequest = null;
        if( ruu.getEvent() != null ) {
            Sort sort = null;
            if (StringUtils.hasText(ruu.getEvent().getSortField())) {
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

            if (sort != null) {
                pageRequest = PageRequest.of(ruu.getEvent().getFirst(), ruu.getEvent().getRows(), sort);
            } else {
                pageRequest = PageRequest.of(ruu.getEvent().getFirst(), ruu.getEvent().getRows());
            }
        }else{
            pageRequest = PageRequest.of(0, Integer.MAX_VALUE, Sort.by(Sort.Order.asc("nome"),Sort.Order.asc("cognome")));
        }
        Page<UtenteTableDto> utenti = this.utenteSvc.findByTipoUtente(tipoUtenteEnum, pageRequest, nomeCognome);
        return ResponseEntity.ok(PagedApiResponse.<List<UtenteTableDto>>builder()
                        .totalRecords(utenti.getTotalElements())
                        .payload(utenti.getContent()).build());
    }

    @GetMapping(value = {"{idUtente}"}, consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<PagedApiResponse<List<UtenteDto>>> utente(@PathVariable(required = true, name = "idUtente") UUID id) {
        log.info("Recupero i dettagli dell'utente con ID {}", id);
        return ResponseEntity.ok(PagedApiResponse.<List<UtenteDto>>builder().error(false).payload(Collections.singletonList(this.utenteSvc.findUtenteById(id).orElseThrow())).build());
    }

    @DeleteMapping(value = {"{idUtente}"}, consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<ApiResponse<Void>> deleteUtente(@PathVariable(required = true, name = "idUtente") UUID id) {
        log.info("Cancello l'utente con ID {}", id);
        this.utenteSvc.cancellaUtente(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<ApiResponse<UtenteDto>> creaUtente(@RequestParam(name = "allegati", required = false) MultipartFile mf,
                                                                     @RequestParam(name = "datiUtente", required = true)String jsonUtente) throws JsonProcessingException {
        UtenteDto utente = springMvcJacksonConverter.getObjectMapper().readValue(jsonUtente, UtenteDto.class);
        return ResponseEntity.ok(ApiResponse.<UtenteDto>builder().payload(utenteSvc.createModificaUtente(utente, Collections.singletonList(mf))).build());
    }

    @PostMapping(value = {"save-utente"}, consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<ApiResponse<UtenteDto>> uploadAllegato(@RequestBody UtenteWithAttachDto utente) {
        utente.setAttivo(Boolean.TRUE);
        return ResponseEntity.ok(ApiResponse.<UtenteDto>builder().payload(this.utenteSvc.createUtenteWithAttach(utente)).build());
    }

    @PutMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<ApiResponse<UtenteDto>> modificaUtente(@RequestParam(name = "allegati", required = false) MultipartFile mf,
                                                             @RequestParam(name = "datiUtente", required = true)String jsonUtente) throws JsonProcessingException {
        UtenteDto utente = springMvcJacksonConverter.getObjectMapper().readValue(jsonUtente, UtenteDto.class);
        return ResponseEntity.ok(ApiResponse.<UtenteDto>builder().payload(utenteSvc.createModificaUtente(utente, Collections.singletonList(mf))).build());
    }
}
