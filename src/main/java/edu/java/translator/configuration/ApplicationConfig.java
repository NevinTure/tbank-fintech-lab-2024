package edu.java.translator.configuration;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app", ignoreInvalidFields = false)
public record ApplicationConfig(
    TranslateProvider provider,
    Datasource datasource
) {

    public record TranslateProvider(
            @NotNull
            ProviderName name,
            @NotNull
            String token,
            @NotNull
            String url) {}

    public enum ProviderName {
        YANDEX, GOOGLE, DEEPL
    }

    public record Datasource(String url, String username, String password) {}
}
