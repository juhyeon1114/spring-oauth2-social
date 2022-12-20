package security.springoauth2social.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import security.springoauth2social.model.*;
import security.springoauth2social.repository.UserRepository;

@Service
@Getter
@RequiredArgsConstructor
@Slf4j
public abstract class AbstractOAuth2UserService {

    private final UserService userService;
    private final UserRepository userRepository;

    protected void register(ProviderUser providerUser, OAuth2UserRequest userRequest) {

        User user = userRepository.findByUsername(providerUser.getUsername());

        if (user == null) {
            String registrationId = userRequest.getClientRegistration().getRegistrationId();
            userService.register(registrationId, providerUser);
        } else {
            log.warn("Already existed : ", user);
        }

    }

    protected ProviderUser providerUser(ClientRegistration clientRegistration, OAuth2User oAuth2User) {
        String registrationId = clientRegistration.getRegistrationId();

        switch (registrationId) {
            case "keycloak" -> {
                return new KeycloakUser(oAuth2User, clientRegistration);
            }
            case "google" -> {
                return new GoogleUser(oAuth2User, clientRegistration);
            }
            case "naver" -> {
                return new NaverUser(oAuth2User, clientRegistration);
            }
            default -> {
                return null;
            }
        }

    }

}
