package security.springoauth2social.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import security.springoauth2social.common.util.OAuth2Utils;
import security.springoauth2social.model.PrincipalUser;

import java.util.Map;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index(Authentication authentication, Model model, @AuthenticationPrincipal PrincipalUser principalUser) {
        if (authentication != null) {

            String username;

            if (authentication instanceof OAuth2AuthenticationToken) {
                username = OAuth2Utils.oAuth2UserName((OAuth2AuthenticationToken) authentication, principalUser);
            } else {
                username = principalUser.providerUser().getUsername();
            }

//            Map<String, Object> attributes = principalUser.getAttributes();
//            String name = (String) attributes.get("name");
//
//            if (authenticationToken.getAuthorizedClientRegistrationId().equals("naver")) {
//                Map<String, Object> response = (Map<String, Object>) attributes.get("response");
//                name = (String) response.get("name");
//            }

            model.addAttribute("user", username);
            model.addAttribute("provider", principalUser.providerUser().getProvider());
        }

        return "index";
    }

}
