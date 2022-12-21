package security.springoauth2social.enums;

public class OAuth2Config {

    public enum SocialType {

        GOOGLE("google"),
        NAVER("naver");

        private final String socialName;

        SocialType(String socialName) {
            this.socialName = socialName;
        }

        public String getSocialName() {
            return socialName;
        }

    }


}
