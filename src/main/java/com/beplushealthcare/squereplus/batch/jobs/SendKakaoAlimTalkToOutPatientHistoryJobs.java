package com.beplushealthcare.squereplus.batch.jobs;

import com.beplushealthcare.squereplus.batch.enums.BuminType;
import com.beplushealthcare.squereplus.batch.feignclient.dto.*;
import com.beplushealthcare.squereplus.batch.properties.EMRProperties;
import com.beplushealthcare.squereplus.batch.properties.SquereplusProperties;
import com.beplushealthcare.squereplus.batch.service.EdgeService;
import com.beplushealthcare.squereplus.batch.service.RedisService;
import com.beplushealthcare.squereplus.batch.service.SquerePlusService;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Call;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class SendKakaoAlimTalkToOutPatientHistoryJobs {
    private final EdgeService edgeService;
    private final SquerePlusService squerePlusService;
    private final EMRProperties emrProperties;
    private final RedisService redisService;
    private final String JOB_NAME = "SEND-KAKAO-ALIM-TALK-OUT-PATIENT-HISTORY";
    private final String EDGE_NEXT_TOKEN_KEY = "edgeNext:token:hospitalId:";
    private static final String FIRST_PATIENT_CODE = "1"; // 초진환자 code
    private static final String NEW_PATIENT_CODE = "3"; // 신규환자 code
    private static final String ENDOSCOPIC_ENCOUNTER_CODE = "09"; // 내시경환자 code

    @Bean
    public Job sendKakaoAlimTalkToOutPatientHistoryJob(
            JobRepository jobRepository,
            Step edgeLoginStep,
            Step edgeRetrieveOutPatientTreatmentHistory) {
        return new JobBuilder("sendKakaoAlimTalkToOutPatientHistoryJob", jobRepository)
//            .start(edgeLoginStep) // sendKakaoAlimTalkToOutPatientHistoryStep 스텝을 시작 스텝으로 설정
            .start(edgeRetrieveOutPatientTreatmentHistory)
//            .next(sendAlimTalkToVisitPatient)
            .build();
    }

    @Bean
    public Step edgeLoginStep(JobRepository jobRepository,
                             Tasklet edgeLoginTasklet,
                             PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("edgeLoginStep", jobRepository)
                .allowStartIfComplete(true)
                .tasklet(edgeLoginTasklet, platformTransactionManager)
                .build();
    }

    @Bean
    @JobScope
    public Tasklet edgeLoginTasklet(@Value("#{jobParameters[buminType]}") BuminType buminType) {
        return ((contribution, chunkContext) -> {
            EdgeLoginRequest request = EdgeLoginRequest.builder()
                    .clientId(buminType.getClientId())
                    .clientSecret(buminType.getClientSecret())
                    .grantType(buminType.getGrantType())
                    .scope(buminType.getScope())
                    .hospitalId(buminType.getHospitalId())
                    .build();
            EmrLoginResponse emrLoginResponse = edgeService.getAccessToken(request);

            contribution.getStepExecution()
                    .getJobExecution()
                    .getExecutionContext()
                    .put("accessKey", emrLoginResponse.getAccessToken());
            return RepeatStatus.FINISHED;
        });
    }

    @Bean
    public Step edgeRetrieveOutPatientTreatmentHistory(JobRepository jobRepository,
                                                       Tasklet edgeRetrieveOutPatientTreatmentHistoryTasklet,
                                                       PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("edgeRetrieveOutPatientTreatmentHistoryStep", jobRepository)
                .allowStartIfComplete(true)
                .tasklet(edgeRetrieveOutPatientTreatmentHistoryTasklet, platformTransactionManager)
                .build();
    }


    @Bean
    @JobScope
    public Tasklet edgeRetrieveOutPatientTreatmentHistoryTasklet(@Value("#{jobParameters[buminType]}") BuminType buminType,
                                                                 @Value("#{jobParameters[startDate]}") String from,
                                                                 @Value("#{jobParameters[endDate]}") String to) {
        return ((contribution, chunkContext) -> {
            List<HospitalAccountResponse> result = squerePlusService.getHospitalAccounts(buminType);
            System.out.println(result);
//            String accessKey = (String)contribution.getStepExecution()
//                    .getJobExecution()
//                    .getExecutionContext()
//                    .get("accessKey");
//            EdgeRetrieveOutPatientTreatmentHistoryRequest retrieveRequest = EdgeRetrieveOutPatientTreatmentHistoryRequest.builder()
//                    .searchStartDate(from)
//                    .searchEndDate(to)
//                    .includeCancellation(false)
//                    .accountClassCode("O")
//                    .build();
//            try {
//                List<EdgeRetrieveOutPatientTreatmentHistoryResponse> outPatients = edgeService.retrieveOutPatientHistory(
//                        buminType.getTenantId(),
//                        buminType.getHospitalId(),
//                        accessKey,
//                        retrieveRequest
//                );
//
////                TODO - 걸러야 되기 위해 API 호출 ??? DB 에 직접 접근, API Call EventLog 적재
//                contribution.getStepExecution()
//                        .getJobExecution()
//                        .getExecutionContext()
//                        .put("outPatients", outPatients);
//            } catch (Exception e) {
////                redisService.deleteRedis(EDGE_NEXT_TOKEN_KEY + buminType.getHospitalId());
//            }
            return RepeatStatus.FINISHED;
        });
    }

    @Bean
    public Step sendAlimTalkToVisitPatientStep(JobRepository jobRepository,
                                               Tasklet edgeRetrieveOutPatientTreatmentHistoryTasklet,
                                               PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("sendAlimTalkToVisitPatientStep", jobRepository)
                .allowStartIfComplete(true)
                .tasklet(edgeRetrieveOutPatientTreatmentHistoryTasklet, platformTransactionManager)
                .build();
    }

    @Bean
    @JobScope
    public Tasklet sendAlimTalkToVisitPatientTasklet() {
        return ((contribution, chunkContext) -> {
            List<EdgeRetrieveOutPatientTreatmentHistoryResponse> outPatients = (List<EdgeRetrieveOutPatientTreatmentHistoryResponse>)contribution.getStepExecution()
                    .getJobExecution()
                    .getExecutionContext()
                    .get("outPatients");

            System.out.println(outPatients);
            return RepeatStatus.FINISHED;
        });
    }

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
