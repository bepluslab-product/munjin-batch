package com.beplushealthcare.munjin.batch.jobs;

import com.beplushealthcare.munjin.batch.enums.BuminType;
import com.beplushealthcare.munjin.batch.feignclient.EmrFeignClient;
import com.beplushealthcare.munjin.batch.feignclient.dto.EdgeRetrieveOutPatientTreatmentHistoryResponse;
import com.beplushealthcare.munjin.batch.properties.EMRProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class SendKakaoAlimTalkToOutPatientHistoryJobs {
    private final EmrFeignClient emrFeignClient;
    private final EMRProperties emrProperties;
    private final String JOB_NAME = "SEND-KAKAO-ALIM-TALK-OUT-PATIENT-HISTORY";

    @Bean
    public Job sendKakaoAlimTalkToOutPatientHistoryJob(
            JobRepository jobRepository,
            Step edgeLoginStep) {
        return new JobBuilder("sendKakaoAlimTalkToOutPatientHistoryJob", jobRepository)
            .start(edgeLoginStep) // sendKakaoAlimTalkToOutPatientHistoryStep 스텝을 시작 스텝으로 설정
            .build();
    }

    @Bean
    public Step edgeLoginStep(JobRepository jobRepository,
                             Tasklet edgeLoginTasklet,
                             PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("sendKakaoAlimTalkToOutPatientHistoryStepTasklet", jobRepository)
                .tasklet(edgeLoginTasklet, platformTransactionManager)
                .build();
    }

    @Bean
    @JobScope
    public Tasklet edgeLoginTasklet(@Value("#{jobParameters[buminType]}") String buminType,
                                    @Value("#{jobParameters[startDate]}") String startDate) {
        return ((contribution, chunkContext) -> {
            emrFeignClient.getAccessToken(new URI(emrProperties.getEdge().getLoginPath()), null);
            return RepeatStatus.FINISHED;
        });
    }

    @Bean
    public Step sendKakaoAlimTalkToOutPatientHistoryStep(JobRepository jobRepository,
                                                         Tasklet sendKakaoAlimTalkToOutPatientHistoryStepTasklet,
                                                         PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("sendKakaoAlimTalkToOutPatientHistoryStepTasklet", jobRepository)
                .chunk(3, platformTransactionManager)
                .reader()
//                .tasklet(sendKakaoAlimTalkToOutPatientHistoryStepTasklet, platformTransactionManager)
                .build();
    }

//    @Bean
//    public Job sendKakaoAlimTalkToOutPatientHistoryJob(
//            JobRepository jobRepository,
//            Step sendKakaoAlimTalkToOutPatientHistoryStep) {
//        return new JobBuilder("sendKakaoAlimTalkToOutPatientHistoryJob", jobRepository)
//                .start(sendKakaoAlimTalkToOutPatientHistoryStep()) // sendKakaoAlimTalkToOutPatientHistoryStep 스텝을 시작 스텝으로 설정
//                .build();
//    }
//    @Bean
//    @JobScope
//    public Tasklet sendKakaoAlimTalkToOutPatientHistoryStepTasklet(@Value("#{jobParameters[buminType]}") String buminType,
//                                                                   @Value("#{jobParameters[startDate]}") String startDate) {
//        return ((contribution, chunkContext) -> {
//            System.out.println(buminType);
//            System.out.println(startDate);
//            log.info("Hello World");
//            System.out.println("Hello World");
//            return RepeatStatus.FINISHED;
//        });
//    }
}
