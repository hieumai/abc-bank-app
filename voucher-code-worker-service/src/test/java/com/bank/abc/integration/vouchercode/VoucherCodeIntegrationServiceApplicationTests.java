package com.bank.abc.integration.vouchercode;

import com.bank.abc.worker.vouchercode.VoucherCodeWorkerServiceApplication;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import redis.embedded.RedisServer;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = VoucherCodeWorkerServiceApplication.class)
class VoucherCodeIntegrationServiceApplicationTests {
    public static final int REDIS_PORT = 32000;
    private static final RedisServer mockRedis = new RedisServer(REDIS_PORT);

    @Value("${redisson.executor.name}")
    private String executorName;

    @Autowired
    private RedissonClient redissonClient;

    @BeforeAll
    public static void beforeAll() {
        mockRedis.start();
    }

    @AfterAll
    public static void afterAll() {
        mockRedis.stop();
    }

    @Test
    public void contextLoads() {
        assertThat(redissonClient).isNotNull();
    }


    @Test
    public void testRedissonClientInitSuccessfully() {
        assertThat(redissonClient.getConfig()).isNotNull();
        assertThat(redissonClient.getExecutorService(executorName)).isNotNull();
    }
}
