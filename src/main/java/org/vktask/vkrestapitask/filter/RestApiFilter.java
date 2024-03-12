package org.vktask.vkrestapitask.filter;


import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.vktask.vkrestapitask.service.AuthenticationService;
import org.vktask.vkrestapitask.service.UserService;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

public class RestApiFilter implements Filter {

    private final UserService userService;

    public RestApiFilter(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        try {
            Authentication authentication = new AuthenticationService(userService).getAuthentication(httpServletRequest);
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (BadCredentialsException exception) {
            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
            PrintWriter writer = httpServletResponse.getWriter();
            writer.print(exception.getMessage());
            writer.flush();
            writer.close();
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

}


