package com.bank.abc.simdata;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import redis.embedded.RedisServer;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = SimDataServiceApplication.class)
public class SimDataServiceApplicationTests {
    public static final int REDIS_PORT = 32000;
    private static final RedisServer mockRedis = new RedisServer(REDIS_PORT);

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
}
