package com.communitygroup.recruit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import util.IdWorker;

import javax.swing.*;

/**
 * @program:
 * @description:
 * @author: Song
 * @create: Created in 2019-02-19 16:58
 * @Modified by:
 **/
@SpringBootApplication
public class RecruitApplication {

    public static void main(String[] args){
        SpringApplication.run(RecruitApplication.class, args);
    }

    @Bean
    public IdWorker idWorker(){
        return new IdWorker();
    }
}
