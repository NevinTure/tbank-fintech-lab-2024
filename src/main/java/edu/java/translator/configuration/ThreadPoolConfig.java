package edu.java.translator.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class ThreadPoolConfig {

    private static final int CORE_POOL_SIZE = 10;


    @Bean
    public ThreadPoolExecutor threadPool() {
        return (ThreadPoolExecutor) Executors.newFixedThreadPool(CORE_POOL_SIZE);
    }
}
