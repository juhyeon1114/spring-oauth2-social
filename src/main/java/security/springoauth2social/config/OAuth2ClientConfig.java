package security.springoauth2social.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
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

        /**
         * UserService: 사용자 정보를 가져오는 역할을 한다.
         */
        http.oauth2Login(httpSecurityOAuth2LoginConfigurer -> {
            httpSecurityOAuth2LoginConfigurer.userInfoEndpoint(userInfoEndpointConfig -> {
                userInfoEndpointConfig
                        .userService(customOAuth2UserService)
                        .oidcUserService(customOidcUserService);
            });
        });

        /**
         * Form 로그인으로 로그인할 수 있도록
         */
        http.formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/loginProc")
                .defaultSuccessUrl("/")
                .permitAll();

        http.exceptionHandling().authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"));

        http.logout().logoutSuccessUrl("/");

        return http.build();

    }


}
