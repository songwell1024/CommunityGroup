package com.communitygroup.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @program:
 * @description:
 * @author: Song
 * @create: Created in 2019-03-01 15:52
 * @Modified by:
 **/
@EnableZuulProxy
@SpringBootApplication
@EnableEurekaClient
public class ManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManagerApplication.class, args);
    }
}
