package com.beplushealthcare.munjin.batch.jobs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
public class HelloWorldJobs {

    @Bean
    public Job helloJob(JobRepository jobRepository, Step helloStep1) {
        return new JobBuilder("helloJob", jobRepository)
                .start(helloStep1) // helloStep1 스텝을 시작 스텝으로 설정
                .build();
    }

    @Bean
    public Step helloStep1(JobRepository jobRepository, Tasklet helloStep1Tasklet1, PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("helloStep1Tasklet1", jobRepository)
                .tasklet(helloStep1Tasklet1, platformTransactionManager)
                .build();
    }

    @Bean
    public Tasklet helloStep1Tasklet1() {
        return ((contribution, chunkContext) -> {
            log.info("Hello World");
            System.out.println("Hello World");
            return RepeatStatus.FINISHED;
        });
    }
}
