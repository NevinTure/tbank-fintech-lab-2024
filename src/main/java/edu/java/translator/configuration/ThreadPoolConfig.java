package edu.java.translator.configuration;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ThreadPoolConfig {

    private static final int CORE_POOL_SIZE = 10;


    @Bean
    public ThreadPoolExecutor threadPool() {
        return (ThreadPoolExecutor) Executors.newFixedThreadPool(CORE_POOL_SIZE);
    }
}
