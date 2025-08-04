package com.beplushealthcare.squereplus.batch.jobs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
public class HelloWorldJobs {

    @Bean
    public Job helloJob(JobRepository jobRepository, Step helloStep) {
        return new JobBuilder("helloJob", jobRepository)
                .start(helloStep) // helloStep 스텝을 시작 스텝으로 설정
                .build();
    }

    @Bean
    public Step helloStep(JobRepository jobRepository, Tasklet helloStepTasklet, PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("helloStep", jobRepository)
                .allowStartIfComplete(true) // 이미 성공한 이력이 있을 경우 해당을 Step 재실행하지 않는다.
                .tasklet(helloStepTasklet, platformTransactionManager)
                .build();
    }

    @Bean
    @JobScope
    public Tasklet helloStepTasklet() {
        return ((contribution, chunkContext) -> {
            log.info("Hello World");
            return RepeatStatus.FINISHED;
        });
    }
}
