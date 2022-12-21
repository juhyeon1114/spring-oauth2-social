package security.springoauth2social.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import security.springoauth2social.common.converters.ProviderUserRequest;
import security.springoauth2social.model.PrincipalUser;
import security.springoauth2social.model.ProviderUser;
import security.springoauth2social.model.users.User;
import security.springoauth2social.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService extends AbstractOAuth2UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            user = User.builder()
                    .id("1")
                    .username("user1")
                    .password("{noop}1234")
                    .authorities(AuthorityUtils.createAuthorityList("ROLE_USER"))
                    .email("user@a.com")
                    .build();
        }

        ProviderUserRequest providerUserRequest = new ProviderUserRequest(user);
        ProviderUser providerUser = providerUser(providerUserRequest);

        return new PrincipalUser(providerUser);
    }

}
