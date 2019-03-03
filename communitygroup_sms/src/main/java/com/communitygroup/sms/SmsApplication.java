package com.communitygroup.sms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @program:
 * @description:
 * @author: Song
 * @create: Created in 2019-02-26 14:20
 * @Modified by:
 **/
@SpringBootApplication
@EnableEurekaClient
public class SmsApplication {

    public static void main(String[] args){
        SpringApplication.run(SmsApplication.class, args);
    }
}
