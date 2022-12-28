package it.olegna.schoolmgmt.web.controller;

import it.olegna.schoolmgmt.dto.AllegatoDto;
import it.olegna.schoolmgmt.dto.FileUploadResponseDto;
import it.olegna.schoolmgmt.dto.UploadedFileDto;
import it.olegna.schoolmgmt.dto.UtenteDto;
import it.olegna.schoolmgmt.dto.UtenteTableDto;
import it.olegna.schoolmgmt.dto.api.ApiResponse;
import it.olegna.schoolmgmt.dto.api.DataTableResponse;
import it.olegna.schoolmgmt.enums.TipoUtenteEnum;
import it.olegna.schoolmgmt.service.AllegatoSvc;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/protected/allegati")
@Slf4j
public class AllegatoController {
    @Autowired
    private AllegatoSvc allegatoSvc;
    @Autowired
    private HttpServletRequest req;

    @DeleteMapping(value = {"{idAllegato}"}, consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<ApiResponse<Void>> deleteAllegato(@PathVariable(required = true, name = "idAllegato") UUID id) {
        log.info("Cancello l'utente con ID {}", id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping( consumes = { MediaType.MULTIPART_FORM_DATA_VALUE },
                  produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<FileUploadResponseDto> uploadAllegato(@RequestParam(required = true, value = "allegati-utente") MultipartFile mpf) {
        UploadedFileDto uploadedFileDto = allegatoSvc.saveAllegato(AllegatoDto.builder()
                                                        .dimensioneFile((int)mpf.getSize())
                                                        .multipartFile(mpf)
                                                        .contentTypeFile(mpf.getContentType())
                                                        .nomeFile(mpf.getOriginalFilename())
                                                        .build());
        return ResponseEntity.ok(FileUploadResponseDto.builder().files(Collections.singletonList(uploadedFileDto)).build());
    }

}
