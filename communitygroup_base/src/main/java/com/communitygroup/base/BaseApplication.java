package com.communitygroup.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import util.IdWorker;

/**
 * @program: BaseApplication
 * @description: 启动类
 * @author: Song
 * @create: Created in 2019-02-18 20:01
 * @Modified by:
 **/
@SpringBootApplication
public class BaseApplication {

    public static void main(String[] args){
        SpringApplication.run(BaseApplication.class, args);
    }

    /**
     * 将公共模块的ID生成器类加入容器中
     * @return
     */
    @Bean
    public IdWorker idWorker(){
        return new IdWorker();
    }

}
