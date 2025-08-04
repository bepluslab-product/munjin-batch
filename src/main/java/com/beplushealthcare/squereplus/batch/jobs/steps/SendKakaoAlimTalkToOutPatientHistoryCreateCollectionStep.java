package com.beplushealthcare.squereplus.batch.jobs.steps;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Component
public class SendKakaoAlimTalkToOutPatientHistoryCreateCollectionStep {


    @JobScope
    @Bean("c.b.a.l.createCollection.Step")
    public Step collectBuildingAnnexedLand_createCollection_Step(
            JobRepository jobRepository,
//            @Qualifier("c.b.a.l.o.m.Reader") ItemReader reader,
            PlatformTransactionManager transactionManager
    ) {
        return new StepBuilder("myFirstStop", jobRepository)
                .tasklet(testTasklet(), transactionManager) // testTasklet을 tasklet으로 설정하고 트랜잭션 매니저를 사용한다.
                .build();
    }

    public Tasklet testTasklet() {
        // Tasklet을 정의하고 비즈니스 로직을 작성
        // Tasklet을 생성합니다.
        return ((contribution, chunkContext) -> {
            System.out.println("***** 10초마다 'Hello batch' 출력!! *****"); // 콘솔에 출력합니다.
            // 원하는 비지니스 로직 작성
            return RepeatStatus.FINISHED; // 작업이 완료되었음을 나타낸다.
        });
    }
}
