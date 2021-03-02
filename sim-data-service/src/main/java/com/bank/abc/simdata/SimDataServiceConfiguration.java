package com.bank.abc.simdata;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;

@Configuration
public class SimDataServiceConfiguration {
    @Value("classpath:redisson-config.yaml")
    private Resource redissonConfig;

    @Bean
    public RedissonClient redissonClient() throws IOException {
        return Redisson.create(Config.fromYAML(redissonConfig.getInputStream()));
    }
}
