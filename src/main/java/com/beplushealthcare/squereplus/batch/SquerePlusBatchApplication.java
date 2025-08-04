package com.beplushealthcare.squereplus.batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
//@EnableBatchProcessing
@SpringBootApplication
public class SquerePlusBatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(SquerePlusBatchApplication.class, args);
    }

}
