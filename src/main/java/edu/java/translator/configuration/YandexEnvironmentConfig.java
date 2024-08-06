package edu.java.translator.configuration;

import edu.java.translator.clients.YandexClient;
import edu.java.translator.services.TranslationHandler;
import edu.java.translator.services.YandexTranslationHandler;
import java.util.concurrent.ThreadPoolExecutor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@ConditionalOnProperty(prefix = "app", name = "provider.name", havingValue = "yandex")
@Configuration
public class YandexEnvironmentConfig {

    @Bean
    public TranslationHandler translationHandler(YandexClient yandexClient, ThreadPoolExecutor threadPool) {
        return new YandexTranslationHandler(yandexClient, threadPool);
    }
}
