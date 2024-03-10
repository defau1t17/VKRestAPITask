package org.vktask.vkrestapitask.filter;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.vktask.vkrestapitask.service.UserService;

import java.util.List;

//@Configuration
//@RequiredArgsConstructor
public class FilterConfiguration {

//    private final UserService userService;

//    @Bean
//    public FilterRegistrationBean<RestApiFilter> createRestApiFilter() {
//        FilterRegistrationBean<RestApiFilter> restApiFilterFilterRegistrationBean = new FilterRegistrationBean<>();
//        restApiFilterFilterRegistrationBean.setFilter(new RestApiFilter(userService));
//        restApiFilterFilterRegistrationBean.addUrlPatterns("/api/v1/posts/*", "/api/v1/users/*", "/api/v1/albums/*");
//        restApiFilterFilterRegistrationBean.setOrder(1);
//        return restApiFilterFilterRegistrationBean;
//    }
}
