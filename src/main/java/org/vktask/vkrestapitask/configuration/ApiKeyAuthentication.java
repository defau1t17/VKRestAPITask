package org.vktask.vkrestapitask.configuration;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class ApiKeyAuthentication extends AbstractAuthenticationToken {

    private String token;

    public ApiKeyAuthentication(String key, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.token = key;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }
}
