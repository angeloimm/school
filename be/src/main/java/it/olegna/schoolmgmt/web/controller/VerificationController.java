package it.olegna.schoolmgmt.web.controller;

import it.olegna.schoolmgmt.dto.api.ApiResponse;
import it.olegna.schoolmgmt.service.UtenteSvc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public/validation")
@Slf4j
public class VerificationController {
    @Autowired
    private UtenteSvc utenteSvc;

    @GetMapping(value = "/username/{username}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<ApiResponse<Boolean>> validUsername(@PathVariable(name = "username", required = true) String u) {
        return ResponseEntity.ok(ApiResponse.<Boolean>builder().payload(utenteSvc.usernameValid(u)).build());
    }
}
