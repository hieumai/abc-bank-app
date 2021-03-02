package com.bank.abc.simdata.controller;

import com.bank.abc.simdata.tasks.RetrievingVoucherCodeTask;
import org.redisson.api.RExecutorFuture;
import org.redisson.api.RScheduledExecutorService;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static com.bank.abc.simdata.SimDataServiceConstants.VOUCHER_CODE_EXECUTOR;

@RestController
@RequestMapping("/data")
public class SimDataController {
    private static final Logger logger = LoggerFactory.getLogger(SimDataController.class);

    @Autowired
    private RedissonClient redissonClient;

    @Value("${redisson.executor.name}")
    private String executorName;

    @Value("${voucher-code-service.url}")
    private String voucherCodeServiceUrl;

    @PostMapping
    public String buyData() throws ExecutionException, InterruptedException {
        RScheduledExecutorService executorService = redissonClient.getExecutorService(executorName);
        RExecutorFuture<String> executorFuture = executorService.submitAsync(
                new RetrievingVoucherCodeTask(voucherCodeServiceUrl));
        try {
            return executorFuture.get(3, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            executorFuture.onComplete((result, error) -> {
                logger.info("SMS sent for result " + result);
            });
            return "Please wait. The request is being processed within 30 seconds";
        }
    }
}