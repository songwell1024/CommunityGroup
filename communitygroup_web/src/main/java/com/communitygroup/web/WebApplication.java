package com.communitygroup.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @program:
 * @description:
 * @author: Song
 * @create: Created in 2019-03-01 16:40
 * @Modified by:
 **/
@EnableZuulProxy
@SpringBootApplication
@EnableEurekaClient
public class WebApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }
}
