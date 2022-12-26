package it.olegna.schoolmgmt.web.controller;

import it.olegna.schoolmgmt.service.UtenteSvc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/protected")
@Slf4j
public class ProtectedPagesController {
    @Autowired
    private UtenteSvc utenteSvc;

    @GetMapping("/hp")
    public String loginPage() {

        return "pagine/hp";
    }
}