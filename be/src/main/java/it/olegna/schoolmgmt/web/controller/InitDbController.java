package it.olegna.schoolmgmt.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import it.olegna.schoolmgmt.dto.UtenteDto;
import it.olegna.schoolmgmt.dto.api.ApiResponse;
import it.olegna.schoolmgmt.enums.TipoUtenteEnum;
import it.olegna.schoolmgmt.service.UtenteSvc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Collections;

@RestController
@RequestMapping("/public/init")
@Slf4j
public class InitDbController {
    @Autowired
    private UtenteSvc utenteSvc;
    @Autowired
    private MappingJackson2HttpMessageConverter springMvcJacksonConverter;
    @GetMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
                produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<ApiResponse<Boolean>> initDb(){
        return ResponseEntity.ok(ApiResponse.<Boolean>builder().payload(utenteSvc.intiDb()).build());
    }
    @PostMapping(value = {"admin"}, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<ApiResponse<UtenteDto>> creaAmministratore(@RequestParam(name = "allegati", required = false)MultipartFile[] mf,
                                                                     @RequestParam(name = "datiUtente", required = true)String jsonUtente) throws JsonProcessingException {
        UtenteDto utente = springMvcJacksonConverter.getObjectMapper().readValue(jsonUtente, UtenteDto.class);
        return ResponseEntity.ok(ApiResponse.<UtenteDto>builder().payload(utenteSvc.createModificaUtente(utente, Arrays.asList(mf))).build());
    }
}
