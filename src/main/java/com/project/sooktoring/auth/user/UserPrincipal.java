package com.project.sooktoring.auth.user;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Builder
@Getter
public class UserPrincipal implements UserDetails {

    private String providerId;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public static UserPrincipal create(String providerId, String password, Collection<? extends GrantedAuthority> authorities) {
        return UserPrincipal.builder()
                .providerId(providerId)
                .password(password)
                .authorities(authorities)
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return providerId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
