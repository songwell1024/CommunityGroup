package com.communitygroup.spit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import util.IdWorker;

/**
 * @program:
 * @description:
 * @author: Song
 * @create: Created in 2019-02-22 21:40
 * @Modified by:
 **/
@SpringBootApplication
@EnableEurekaClient
public class SpitApplication {
    public static void main(String[] args){
        SpringApplication.run(SpitApplication.class, args);
    }

    @Bean
    public IdWorker idWorker(){
        return new IdWorker();
    }
}
