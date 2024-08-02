package edu.java.translator.configuration;

import edu.java.translator.clients.YandexClient;
import edu.java.translator.clients.YandexClientImpl;
import edu.java.translator.utils.TokenParser;
import edu.java.translator.utils.YandexToken;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@ConditionalOnProperty(prefix = "app", name = "provider.name", havingValue = "yandex")
@Configuration
public class YandexClientConfig {

    private String baseUrlStr = "https://translate.api.cloud.yandex.net/translate/v2";
    private final YandexToken token;
    private final RestTemplateBuilder builder;
    private final ResponseErrorHandler errorHandler;

    public YandexClientConfig(
            ApplicationConfig appConfig,
            RestTemplateBuilder builder,
            ResponseErrorHandler errorHandler) {
        this.builder = builder;
        this.errorHandler = errorHandler;
        this.baseUrlStr = appConfig.provider().url() == null ? baseUrlStr : appConfig.provider().url();
        this.token = TokenParser.parseYandexToken(appConfig.provider().token());
    }

    @Bean
    public RestTemplate restTemplate() {
        return builder
                .defaultHeader("Content-Type", "application/json")
                .defaultHeader("Authorization", "Api-Key ".concat(token.apiKey()))
                .errorHandler(errorHandler)
                .build();
    }

    @Bean
    public YandexClient yandexClient() {
        return new YandexClientImpl(
                token,
                restTemplate(),
                URI.create(baseUrlStr)
        );
    }
}
