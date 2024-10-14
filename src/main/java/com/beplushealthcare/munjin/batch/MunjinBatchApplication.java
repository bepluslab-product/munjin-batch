package com.beplushealthcare.munjin.batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MunjinBatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(MunjinBatchApplication.class, args);
    }

}
