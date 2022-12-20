package it.olegna.schoolmgmt.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class LoginController {
    public static final String ERROR_CODE_PARAM="diomedeeErrorCode";
    public static final String CLIENT_ID_PARAM="diomedeeClientId";
    public static final String REDIRECT_URI_ID_PARAM="diomedeeRedirectUri";

    @GetMapping("/accedi")
    public String oauth2LoginPage(Model model,
                                  @CurrentSecurityContext(expression = "authentication") Authentication authentication,
                                  @Value("${spring.security.oauth2.server.login.captcha.enabled:true}") boolean enableCaptchaLogin,
                                  @RequestAttribute(name = "org.springframework.security.web.csrf.CsrfToken", required = false) CsrfToken csrfToken) {

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return "redirect:/";
        }
        if (csrfToken != null) {
            model.addAttribute("_csrfToken", csrfToken);
        }
        //SystemSettings systemSettings = new SystemSettings();
        //model.addAttribute("enableCaptchaLogin", enableCaptchaLogin);
        //model.addAttribute("systemSettings", systemSettings);
        return "pagine/login";
    }
}