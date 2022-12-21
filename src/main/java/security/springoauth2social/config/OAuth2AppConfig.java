package security.springoauth2social.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import security.springoauth2social.common.authority.CustomAuthorityMapper;

@Configuration
public class OAuth2AppConfig {

    /**
     * 구글은 권한 값이 이상하게 넘어와서 그것을 매핑하기 위한 Custom
     */
    @Bean
    public GrantedAuthoritiesMapper customAuthorityMapper() {
        return new CustomAuthorityMapper();
    }

}
