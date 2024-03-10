package org.vktask.vkrestapitask.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import org.vktask.vkrestapitask.entity.Role;
import org.vktask.vkrestapitask.entity.User;
import org.vktask.vkrestapitask.exception.TokenNotFoundException;
import org.vktask.vkrestapitask.service.UserService;

import java.io.IOException;
import java.util.Optional;

//@Component
public class RestApiFilter extends GenericFilterBean {

    private final UserService userService;

    public RestApiFilter(UserService userService) {
        this.userService = userService;
    }


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        String token = httpServletRequest.getHeader("TOKEN");

        System.out.println(token);

        if (token == null) {
            HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        Optional<User> optionalUser = userService.findUserByToken(token);
        System.out.println(optionalUser.isPresent());
        if (optionalUser.isPresent() && allowAccess(httpServletRequest.getRequestURI(), optionalUser.get().getRole())) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } else {
            HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
            httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
    }

    private boolean allowAccess(String resource, Role role) {
        return resource.substring(8)
                .toUpperCase()
                .equals(role.name()
                        .substring(5)) || role == Role.ROLE_ADMIN;
    }


}
