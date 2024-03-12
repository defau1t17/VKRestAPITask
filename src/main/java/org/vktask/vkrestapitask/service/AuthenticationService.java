package org.vktask.vkrestapitask.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.vktask.vkrestapitask.configuration.ApiKeyAuthentication;
import org.vktask.vkrestapitask.entity.Role;
import org.vktask.vkrestapitask.entity.User;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserService userService;
    private static final String AUTH_TOKEN_HEADER_NAME = "AUTH-TOKEN";

    public Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(AUTH_TOKEN_HEADER_NAME);
        if (token == null || token.isEmpty()) {
            throw new BadCredentialsException("Can't find %s".formatted(AUTH_TOKEN_HEADER_NAME));
        } else {
            Optional<User> optionalUser = userService.findUserByToken(token);

            if (optionalUser.isPresent()) {
                return new ApiKeyAuthentication(token, authorities(optionalUser.get().getRole()));
            } else {
                throw new BadCredentialsException("Token doesn't match %s".formatted(AUTH_TOKEN_HEADER_NAME));
            }
        }
    }

    private Set<GrantedAuthority> authorities(Role role) {
        return Set.of(new SimpleGrantedAuthority(role.name()));
    }
}
