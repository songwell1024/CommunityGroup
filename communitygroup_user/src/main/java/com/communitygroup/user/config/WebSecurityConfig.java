package com.communitygroup.user.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

/**
 * @program:
 * @description:  @Configuration 配置类注解
 * @EnableWebSecurity  访问安全
 * @author: Song
 * @create: Created in 2019-02-27 10:10
 * @Modified by:
 **/
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //authorizeRequests是security全注解配置实现的开端，表示说明所需要的全部权限
        //需要的权限分为两部分，一部分是拦截的地址，一部分是访问这些地址所需要的权限
        //antMatchers表示拦截的路径,permitAll表示允许全部路径都访问
        //anyRequest表示任何请求，authenticated表示这些请求认证后访问
        //.and().csrf().disable();  固定写法，表示使得csrf拦截失效
        http
                .authorizeRequests()
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated()
                .and().csrf().disable();
    }
}
