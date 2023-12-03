package com.oxygen.wechat.config;

import com.oxygen.wechat.interceptor.JWTInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JWTInterceptor())
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/v1/**")
                .excludePathPatterns("/api/v2/**")
                .excludePathPatterns("/weChat/**");
    }



}