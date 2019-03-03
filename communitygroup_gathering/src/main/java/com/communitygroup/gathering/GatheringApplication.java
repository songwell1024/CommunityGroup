package com.communitygroup.gathering;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import util.IdWorker;

/**
 * @program:
 * @description:
 * @author: Song
 * @create: Created in 2019-02-21 11:12
 * @Modified by:
 **/
@SpringBootApplication
@EnableCaching
@EnableEurekaClient
public class GatheringApplication {

    public static void main(String[] args){
        SpringApplication.run(GatheringApplication.class, args);
    }

    @Bean
    public IdWorker idWorker(){
        return new IdWorker();
    }
}
