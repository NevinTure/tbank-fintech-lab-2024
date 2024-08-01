package edu.java.translator.configuration;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app", ignoreInvalidFields = false)
public record ApplicationConfig(
    @NotEmpty
    TranslateProvider provider,
    @NotEmpty
    Datasource datasource
) {

    public record TranslateProvider(ProviderName name, String token, String url) {}

    public enum ProviderName {
        YANDEX, GOOGLE, DEEPL
    }

    public record Datasource(String url, String username, String password) {}
}
