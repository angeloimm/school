package it.olegna.schoolmgmt.web.controller;

import it.olegna.schoolmgmt.dto.UtenteDto;
import it.olegna.schoolmgmt.dto.UtenteTableDto;
import it.olegna.schoolmgmt.dto.UtenteWithAttachDto;
import it.olegna.schoolmgmt.dto.api.ApiResponse;
import it.olegna.schoolmgmt.dto.api.DataTableResponse;
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
@RequestMapping("/protected/utenti")
@Slf4j
public class UtenteController {
    @Autowired
    private UtenteSvc utenteSvc;
    @Autowired
    private HttpServletRequest req;
    @GetMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<DataTableResponse<List<UtenteTableDto>>> utenti(@RequestParam(name = "tipoUtente", required = true) String tipoUtente,
                                                                     @RequestParam(name = "draw", required = true) Long draw,
                                                                     @RequestParam(name = "start", required = true) Integer start,
                                                                     @RequestParam(name = "length", required = true) Integer length,
                                                                     @RequestParam(name = "order[0][column]", required = false) Integer indiceColonnaOrdinamento,
                                                                     @RequestParam(name = "order[0][dir]", required = false) String versoOrdinamento) {
        log.trace("Recupero tutti gli utenti");
        TipoUtenteEnum tipoUtenteEnum = null;
        switch (tipoUtente){
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
                throw new IllegalArgumentException("Tipo utente non riconosciuto "+tipoUtente+". Valori ammessi: S, D e A");
        }
        log.info("Ricerco gli utenti per tipo utente {}", tipoUtente);
        String nomeProprietaOrdinamento = req.getParameter("columns["+indiceColonnaOrdinamento+"][data]");
        Sort sort = null;
        switch (versoOrdinamento.toLowerCase()) {
            case "asc": {

                sort = Sort.by(Sort.Order.asc(nomeProprietaOrdinamento));
                break;
            }
            case "desc":{

                sort = Sort.by(Sort.Order.desc(nomeProprietaOrdinamento));
                break;
            }
            default:
                throw new IllegalArgumentException("Unexpected value: " + versoOrdinamento.toLowerCase());
        }

        PageRequest pageRequest = null;
        if(sort != null) {
            pageRequest = PageRequest.of(start, length, sort);
        }else {
            pageRequest = PageRequest.of(start, length);
        }
        Page<UtenteTableDto> utenti = this.utenteSvc.findByTipoUtente(tipoUtenteEnum, pageRequest);
        return ResponseEntity.ok(DataTableResponse.<List<UtenteTableDto>>builder()
                                .recordsTotal(utenti.getTotalElements())
                                .draw(draw)
                                .recordsFiltered(utenti.getTotalElements())
                                .payload(utenti.getContent()).build());
    }

    @GetMapping(value = {"{idUtente}"}, consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<ApiResponse<UtenteDto>> utente(@PathVariable(required = true, name = "idUtente") UUID id) {
        log.info("Recupero i dettagli dell'utente con ID {}", id);
        return ResponseEntity.ok(ApiResponse.<UtenteDto>builder().error(false).payload(this.utenteSvc.findUtenteById(id).orElseThrow()).build());
    }

    @DeleteMapping(value = {"{idUtente}"}, consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<ApiResponse<Void>> deleteUtente(@PathVariable(required = true, name = "idUtente") String id) {
        log.info("Cancello l'utente con ID {}", id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<ApiResponse<UtenteDto>> creaUtente(UtenteDto utente) {
        return ResponseEntity.ok(ApiResponse.<UtenteDto>builder().payload(utenteSvc.createModificaUtente(utente)).build());
    }
    @PostMapping( value ={"save-utente"} ,consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<ApiResponse<UtenteDto>> uploadAllegato(@RequestBody UtenteWithAttachDto utente ) {
        utente.setAttivo(Boolean.TRUE);
        return ResponseEntity.ok(ApiResponse.<UtenteDto>builder().payload(this.utenteSvc.createUtenteWithAttach(utente)).build());
    }

    @PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<ApiResponse<UtenteDto>> modificaUtente(@RequestBody UtenteDto utente) {
        return ResponseEntity.ok(ApiResponse.<UtenteDto>builder().payload(utenteSvc.createModificaUtente(utente)).build());
    }
}
