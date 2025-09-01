package com.example.demo1.configuration;

import com.example.demo1.interceptor.RequestInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final RequestInterceptor requestInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(requestInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/home/**");
    }
}
