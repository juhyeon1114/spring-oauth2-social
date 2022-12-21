package security.springoauth2social.common.util;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import security.springoauth2social.common.enums.OAuth2Config;
import security.springoauth2social.model.Attributes;
import security.springoauth2social.model.PrincipalUser;

import java.util.Map;

public class OAuth2Utils {

    public static Attributes getMainAttributes(OAuth2User oAuth2User) {
        return Attributes.builder()
                .mainAttributes(oAuth2User.getAttributes())
                .build();
    }

    public static Attributes getSubAttributes(OAuth2User oAuth2User, String subAttributesKey) {
        Map<String, Object> subAttributes = (Map<String, Object>) oAuth2User.getAttributes().get(subAttributesKey);
        return Attributes.builder()
                .subAttributes(subAttributes)
                .build();
    }

    public static Attributes getOtherAttributes(OAuth2User oAuth2User, String subAttributesKey, String otherAttributesKey) {
        Map<String, Object> subAttributes = (Map<String, Object>) oAuth2User.getAttributes().get(subAttributesKey);
        Map<String, Object> otherAttributes = (Map<String, Object>) subAttributes.get(otherAttributesKey);

        return Attributes.builder()
                .subAttributes(subAttributes)
                .otherAttributes(otherAttributes)
                .build();
    }

    public static String oAuth2UserName(OAuth2AuthenticationToken authenticationToken, PrincipalUser principalUser) {

        String username;
        String registrationId = authenticationToken.getAuthorizedClientRegistrationId();
        OAuth2User oAuth2User = principalUser.providerUser().getOAuth2User();

        // Google
        Attributes attributes = OAuth2Utils.getMainAttributes(oAuth2User);
        username = (String) attributes.getMainAttributes().get("name");

        // Naver
        if (registrationId.equals(OAuth2Config.SocialType.NAVER.getSocialName())) {
            attributes = OAuth2Utils.getSubAttributes(oAuth2User, "response");
            username = (String) attributes.getSubAttributes().get("name");
        }

        return username;

    }

}
