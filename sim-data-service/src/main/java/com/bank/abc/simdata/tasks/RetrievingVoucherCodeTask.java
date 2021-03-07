package com.bank.abc.simdata.tasks;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

public class RetrievingVoucherCodeTask implements Callable<String> {
    private static final Logger logger = LoggerFactory.getLogger(RetrievingVoucherCodeTask.class);;

    private final String taskId;
    private String voucherCodeServiceUrl;

    public RetrievingVoucherCodeTask() {
        // empty constructor for org.redisson.codec.JsonJacksonCodec to work
        taskId = UUID.randomUUID().toString();
    }

    public RetrievingVoucherCodeTask(String voucherCodeServiceUrl) {
        this();
        this.voucherCodeServiceUrl = voucherCodeServiceUrl;
    }

    @Override
    public String call() {
        logger.info("Start getting voucher code on task " + taskId);
        try {
            HttpGet request = new HttpGet(voucherCodeServiceUrl);
            HttpResponse response = getHttpClient().execute(request);
            try (BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))) {
                String voucherCode = rd.lines().collect(Collectors.joining("\n"));
                logger.info("Done getting voucher code on task " + taskId);
                return voucherCode;
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public HttpClient getHttpClient() {
        return HttpClients.createDefault();
    }
}
