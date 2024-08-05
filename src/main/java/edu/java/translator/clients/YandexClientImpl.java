package edu.java.translator.clients;

import edu.java.translator.dtos.yandex.YandexLanguagesRequest;
import edu.java.translator.dtos.yandex.YandexLanguagesResponse;
import edu.java.translator.dtos.yandex.YandexTranslationRequest;
import edu.java.translator.dtos.yandex.YandexTranslationResponse;
import edu.java.translator.utils.YandexToken;
import org.springframework.web.client.RestTemplate;

public class YandexClientImpl implements YandexClient {

    private final YandexToken token;
    private final RestTemplate restTemplate;

    private static final String TRANSLATION_URN = "/translate";
    private static final String LANGUAGES_URN = "/languages";

    public YandexClientImpl(YandexToken token, RestTemplate restTemplate) {
        this.token = token;
        this.restTemplate = restTemplate;
    }

    @Override
    public YandexTranslationResponse translate(YandexTranslationRequest request) {
        request.setFolderId(token.folderId());
        return restTemplate.
                postForObject(TRANSLATION_URN, request, YandexTranslationResponse.class);
    }

    @Override
    public YandexLanguagesResponse getLanguages(YandexLanguagesRequest request) {
        request.setFolderId(token.folderId());
        return restTemplate
                .postForObject(LANGUAGES_URN, request, YandexLanguagesResponse.class);
    }
}
