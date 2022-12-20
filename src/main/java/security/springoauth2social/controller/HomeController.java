package security.springoauth2social.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class HomeController {

    @GetMapping("/api/user")
    public String user(Authentication authentication, Model model, @AuthenticationPrincipal OAuth2User oAuth2User) {
        log.info("authentication", authentication);
        log.info("oAuth2User", oAuth2User);

        model.addAttribute("authentication", authentication);
        model.addAttribute("oAuth2User", oAuth2User);

        return "authentication";
    }

    @GetMapping("/api/oidc")
    public String oidc(Authentication authentication, Model model, @AuthenticationPrincipal OidcUser oidcUser) {
        log.info("authentication", authentication);
        log.info("oidcUser", oidcUser);

        model.addAttribute("authentication", authentication);
        model.addAttribute("oidcUser", oidcUser);

        return "authentication";
    }


}
