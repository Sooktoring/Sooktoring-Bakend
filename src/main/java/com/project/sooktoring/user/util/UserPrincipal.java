package com.project.sooktoring.user.util;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Builder
@Getter
public class UserPrincipal implements UserDetails {

    private Long userId;
    private String providerId;
    private Collection<? extends GrantedAuthority> authorities;

    public static UserPrincipal create(Long userId, String providerId, Collection<? extends GrantedAuthority> authorities) {
        return UserPrincipal.builder()
                .userId(userId)
                .providerId(providerId)
                .authorities(authorities)
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return "";
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
