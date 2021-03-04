package com.bank.abc.simdata.services;

import com.bank.abc.simdata.models.ExecutorResult;
import com.bank.abc.simdata.tasks.RetrievingVoucherCodeTask;
import org.redisson.api.RExecutorFuture;
import org.redisson.api.RScheduledExecutorService;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
public class SimDataService {
    @Autowired
    private RedissonClient redissonClient;

    @Value("${redisson.executor.name}")
    private String executorName;

    @Value("${voucher-code-service.url}")
    private String voucherCodeServiceUrl;

    @Autowired
    private VoucherService voucherService;

    @Autowired
    private SmsService smsService;

    public String buyData(String phoneNumber) throws ExecutionException, InterruptedException {
        RScheduledExecutorService executorService = redissonClient.getExecutorService(executorName);
        RExecutorFuture<String> executorFuture = executorService.submitAsync(
                new RetrievingVoucherCodeTask(voucherCodeServiceUrl));
        ExecutorResult executorResult = new ExecutorResult(phoneNumber);
        try {
            return executorFuture.get(3, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            executorResult.setTimeout(true);
            return "Please wait. The request is being processed within 30 seconds";
        } finally {
            executorFuture.onComplete((resultCode, error) -> {
                if (executorResult.isTimeout()) {
                    smsService.sendVoucherCodeToPhoneNumber(phoneNumber, resultCode);
                }
                voucherService.saveVoucherByUserPhoneNumber(executorResult.getPhoneNumber(), resultCode);
            });
        }
    }
}
