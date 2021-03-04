package com.bank.abc.simdata.tasks;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;
import java.util.concurrent.Callable;

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
        HttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet(voucherCodeServiceUrl);
        try {
            HttpResponse response = client.execute(request);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder voucherCodeBuilder = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                voucherCodeBuilder.append(line);
            }
            logger.info("Done getting voucher code on task " + taskId);
            return voucherCodeBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
