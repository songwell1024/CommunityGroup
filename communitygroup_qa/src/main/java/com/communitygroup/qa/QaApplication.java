package com.communitygroup.qa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import util.IdWorker;
import util.JwtUtil;

/**
 * @program:
 * @description:
 * @author: Song
 * @create: Created in 2019-02-20 18:09
 * @Modified by:
 **/
@SpringBootApplication
public class QaApplication {
    public static void main(String[] args){
        SpringApplication.run(QaApplication.class,args);
    }

    @Bean
    public IdWorker idWorker(){
        return new IdWorker();
    }

    @Bean
    public JwtUtil jwtUtil(){
        return new JwtUtil();
    }
}
