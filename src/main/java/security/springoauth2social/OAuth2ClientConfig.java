package security.springoauth2social;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.web.SecurityFilterChain;
import security.springoauth2social.service.CustomOAuth2UserService;
import security.springoauth2social.service.CustomOidcUserService;

@Configuration
@RequiredArgsConstructor
public class OAuth2ClientConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomOidcUserService customOidcUserService;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers(
                "/static/js/**",
                "/static/images/**",
                "/static/css/**",
                "/static/scss/**",
                "/static/icommon/**"
        );
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests()
                .requestMatchers("/").permitAll()
                .requestMatchers("/api/user").hasAnyRole("SCOPE_profile", "SCOPE_email")
                .requestMatchers("/api/oidc").hasAnyRole("SCOPE_openid")
                .anyRequest().authenticated();

        http.oauth2Login(httpSecurityOAuth2LoginConfigurer -> {
            httpSecurityOAuth2LoginConfigurer.userInfoEndpoint(userInfoEndpointConfig -> {
                userInfoEndpointConfig
                        .userService(customOAuth2UserService)
                        .oidcUserService(customOidcUserService);
            });
        });

        http.logout().logoutSuccessUrl("/");

        return http.build();

    }

    /**
     * 구글은 권한 값이 이상하게 넘어와서 그것을 매핑하기 위한 Custom
     */
    @Bean
    public GrantedAuthoritiesMapper customAuthorityMapper() {
        return new CustomAuthorityMapper();
    }


}
