package com.bank.abc.worker.vouchercode;

import org.redisson.Redisson;
import org.redisson.api.RScheduledExecutorService;
import org.redisson.api.RedissonClient;
import org.redisson.api.WorkerOptions;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Configuration
public class VoucherCodeWorkerConfiguration {
    @Value("${redisson.executor.name}")
    private String executorName;

    @Value("${redisson.executor.workersCount}")
    private int workersCount;

    @Value("${redisson.executor.taskTimeoutInSeconds}")
    private int taskTimeoutInSeconds;

    @Value("classpath:redisson-config.yaml")
    private Resource redissonConfig;

    @Bean
    public RedissonClient redissonClient() throws IOException {
        RedissonClient redissonClient = Redisson.create(Config.fromYAML(redissonConfig.getInputStream()));
        RScheduledExecutorService executorService = redissonClient.getExecutorService(executorName);
        WorkerOptions options = WorkerOptions.defaults().workers(workersCount).taskTimeout(taskTimeoutInSeconds, TimeUnit.SECONDS);
        executorService.registerWorkers(options);
        return redissonClient;
    }
}
