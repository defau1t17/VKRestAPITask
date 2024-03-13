package org.vktask.vkrestapitask.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.vktask.vkrestapitask.filter.RestApiFilter;
import org.vktask.vkrestapitask.service.AuthenticationService;
import org.vktask.vkrestapitask.service.UserService;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final UserService userService;

    private final AuthenticationService authenticationService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
                        authorizationManagerRequestMatcherRegistry
                                .requestMatchers("/api/v1/reg", "/api/v1/auth/*").permitAll()
                                .requestMatchers("/api/v1/users*").hasAnyRole("ADMIN", "USERS")
                                .requestMatchers("/api/v1/albums*").hasAnyRole("ADMIN", "ALBUMS")
                                .requestMatchers("/api/v1/posts*").hasAnyRole("ADMIN", "POSTS")
                                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**").permitAll()
                                .anyRequest().authenticated())
                .securityMatcher("/api/v1/**")
                .addFilterBefore(new RestApiFilter(userService, authenticationService), BasicAuthenticationFilter.class)
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }


}
