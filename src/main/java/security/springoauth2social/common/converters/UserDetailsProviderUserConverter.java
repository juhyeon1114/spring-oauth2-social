package security.springoauth2social.common.converters;

import security.springoauth2social.model.ProviderUser;
import security.springoauth2social.model.users.FormUser;
import security.springoauth2social.model.users.User;

public class UserDetailsProviderUserConverter implements ProviderUserConverter<ProviderUserRequest, ProviderUser>{
    @Override
    public ProviderUser converter(ProviderUserRequest providerUserRequest) {

        if (providerUserRequest.user() == null) return null;

        User user = providerUserRequest.user();
        return FormUser.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .authorities(user.getAuthorities())
                .provider("none")
                .build();

    }
}
