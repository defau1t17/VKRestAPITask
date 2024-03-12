package org.vktask.vkrestapitask.filter;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.vktask.vkrestapitask.service.AuditService;
import org.vktask.vkrestapitask.service.UserService;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class FilterConfiguration {

    private final UserService userService;
    private final AuditService auditService;

//    @Bean
//    public FilterRegistrationBean<RestApiFilter> createRestApiFilter() {
//        RestApiFilter restApiFilter = new RestApiFilter(userService, auditService);
//        FilterRegistrationBean<RestApiFilter> restApiFilterFilterRegistrationBean = new FilterRegistrationBean<>();
//        restApiFilterFilterRegistrationBean.setFilter(restApiFilter);
//        restApiFilterFilterRegistrationBean.addUrlPatterns("/api/v1/posts/*", "/api/v1/albums/*", "/api/v1/users/*");
//        return restApiFilterFilterRegistrationBean;
//    }
}
