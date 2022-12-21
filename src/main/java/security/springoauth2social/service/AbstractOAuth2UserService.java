package security.springoauth2social.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.stereotype.Service;
import security.springoauth2social.converters.ProviderUserConverter;
import security.springoauth2social.converters.ProviderUserRequest;
import security.springoauth2social.model.*;
import security.springoauth2social.model.users.User;
import security.springoauth2social.repository.UserRepository;

@Service
@Getter
@Slf4j
public abstract class AbstractOAuth2UserService {

    @Autowired
    private UserService userService;
    @Autowired private UserRepository userRepository;
    @Autowired private ProviderUserConverter<ProviderUserRequest, ProviderUser> providerUserConverter;

    protected void register(ProviderUser providerUser, OAuth2UserRequest userRequest) {

        User user = userRepository.findByUsername(providerUser.getUsername());

        if (user == null) {
            String registrationId = userRequest.getClientRegistration().getRegistrationId();
            userService.register(registrationId, providerUser);
        } else {
            log.warn("Already existed : ", user);
        }

    }

    protected ProviderUser providerUser(ProviderUserRequest providerUserRequest) {

        return providerUserConverter.converter(providerUserRequest);

    }

}
