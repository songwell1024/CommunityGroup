package com.communitygroup.user.config;

import com.communitygroup.user.interceptor.JwtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @program:
 * @description: 拦截器的配置类
 * @author: Song
 * @create: Created in 2019-02-28 14:22
 * @Modified by:
 **/
@Configuration
public class JwtInterceptorConfig extends WebMvcConfigurationSupport {

    @Autowired
    JwtInterceptor jwtInterceptor;

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/**/login");
    }
}
