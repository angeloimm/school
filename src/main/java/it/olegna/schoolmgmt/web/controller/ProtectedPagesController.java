package it.olegna.schoolmgmt.web.controller;

import it.olegna.schoolmgmt.dto.UtenteDto;
import it.olegna.schoolmgmt.dto.UtenteTableDto;
import it.olegna.schoolmgmt.enums.TipoUtenteEnum;
import it.olegna.schoolmgmt.service.UtenteSvc;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

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
    @GetMapping("/user")
    public String userPage(@RequestParam(name = "tipoUtente") String tipoUtente, Model model) {
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
                throw new IllegalArgumentException("Tipo utente non noto "+tipoUtente);
        }
        model.addAttribute("tipoUtenteEnum", tipoUtenteEnum.name());
        return "pagine/utenti";
    }
}