package it.olegna.schoolmgmt.web.controller;

import it.olegna.schoolmgmt.service.UtenteSvc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/public")
@Slf4j
public class LoginController {
    @Autowired
    private UtenteSvc utenteSvc;

    @GetMapping("/accedi")
    public String loginPage() {
        if (utenteSvc.intiDb()) {
            log.info("DB vuoto. Inzializzazione DB necessaria");
            return "redirect:/public/init-db";
        }
        return "pagine/login";
    }

    @GetMapping(value = "/init-db")
    public String initDbController() {
        return "pagine/init-db";
    }
}