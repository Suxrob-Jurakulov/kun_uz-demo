package com.company.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecuredFilterConfig {
    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public FilterRegistrationBean<JwtFilter> filterRegistrationBeanRegion() {
        FilterRegistrationBean<JwtFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(jwtFilter);

        bean.addUrlPatterns("/profile/adm/*");
        bean.addUrlPatterns("/article/adm/*");
        bean.addUrlPatterns("/article_like/*");
        bean.addUrlPatterns("/category/adm/*");
        bean.addUrlPatterns("/comment/adm/*");
        bean.addUrlPatterns("/region/adm/*");
        bean.addUrlPatterns("/types/adm/*");
        bean.addUrlPatterns("/comment_like/*");
        return bean;
    }
}
