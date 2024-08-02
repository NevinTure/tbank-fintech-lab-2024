package edu.java.translator.clients;

import edu.java.translator.dtos.yandex.YandexTranslationRequest;
import edu.java.translator.dtos.yandex.YandexTranslationResponse;
import edu.java.translator.utils.YandexToken;
import org.springframework.web.client.RestTemplate;

public class YandexClientImpl implements YandexClient {

    private final YandexToken token;
    private final RestTemplate restTemplate;

    public YandexClientImpl(YandexToken token, RestTemplate restTemplate) {
        this.token = token;
        this.restTemplate = restTemplate;
    }

    @Override
    public YandexTranslationResponse translate(YandexTranslationRequest request) {

        return null;
    }
}
