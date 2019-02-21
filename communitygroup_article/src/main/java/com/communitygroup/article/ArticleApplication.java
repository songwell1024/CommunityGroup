package com.communitygroup.article;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import util.IdWorker;

/**
 * @program:
 * @description:
 * @author: Song
 * @create: Created in 2019-02-21 11:04
 * @Modified by:
 **/
@SpringBootApplication
public class ArticleApplication {
    public static void main(String[] args){
        SpringApplication.run(ArticleApplication.class, args);
    }

    @Bean
    public IdWorker idWorker(){
        return new IdWorker();
    }

}
