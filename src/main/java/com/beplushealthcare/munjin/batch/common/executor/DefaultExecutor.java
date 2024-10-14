package com.beplushealthcare.munjin.batch.common.executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class DefaultExecutor {

    @Primary
    @Bean("thread.pool-size.1")
    public TaskExecutor taskExecutor1() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(1);
        executor.setMaxPoolSize(1);
        executor.setThreadNamePrefix("multi-thread-");
        executor.initialize();
        return executor;
    }

    @Bean("thread.pool-size.3")
    public TaskExecutor taskExecutor3() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(3);
        executor.setMaxPoolSize(3);
        executor.setThreadNamePrefix("multi-thread-");
        executor.initialize();
        return executor;
    }

    @Bean("thread.pool-size.5")
    public TaskExecutor taskExecutor5() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(5);
        executor.setThreadNamePrefix("multi-thread-");
        executor.initialize();
        return executor;
    }
}
