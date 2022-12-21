package security.springoauth2social.converters;

import lombok.extern.slf4j.Slf4j;
import security.springoauth2social.enums.OAuth2Config;
import security.springoauth2social.model.ProviderUser;
import security.springoauth2social.model.social.GoogleUser;
import security.springoauth2social.util.OAuth2Utils;

@Slf4j
public class OAuth2GoogleProviderUserConverter implements ProviderUserConverter<ProviderUserRequest, ProviderUser> {
    @Override
    public ProviderUser converter(ProviderUserRequest providerUserRequest) {
        String registrationId = providerUserRequest.clientRegistration().getRegistrationId();
        String socialName = OAuth2Config.SocialType.GOOGLE.getSocialName();
        if (!registrationId.equals(socialName)) return null;

        return new GoogleUser(
            OAuth2Utils.getMainAttributes(providerUserRequest.oAuth2User()),
            providerUserRequest.oAuth2User(),
            providerUserRequest.clientRegistration()
        );
    }
}
