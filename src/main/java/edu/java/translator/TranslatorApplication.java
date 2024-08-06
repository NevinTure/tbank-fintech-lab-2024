package edu.java.translator;

import edu.java.translator.configuration.ApplicationConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationConfig.class)
public class TranslatorApplication {
    public static void main(String[] args) {
        SpringApplication.run(TranslatorApplication.class, args);
    }
}
