package com.flowforgr.FlowForgr.shared.security.config;

import com.flowforgr.FlowForgr.auth.AppUserRepository;
import com.flowforgr.FlowForgr.auth.entity.AppUser;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
@AllArgsConstructor
public class AppUserDetailsService implements UserDetailsService {

    private final AppUserRepository appUserRepository;

    @Override
    @NonNull
    public UserDetails loadUserByUsername(@NonNull String email) throws UsernameNotFoundException {
        AppUser user = appUserRepository.findByEmail(email);

        if(ObjectUtils.isEmpty(user)) {
            throw new UsernameNotFoundException(email);
        }

        return new AppUserDetails(user);
    }
}
