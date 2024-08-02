package edu.java.translator.clients;

import edu.java.translator.dtos.yandex.YandexLanguagesRequest;
import edu.java.translator.dtos.yandex.YandexLanguagesResponse;
import edu.java.translator.dtos.yandex.YandexTranslationRequest;
import edu.java.translator.dtos.yandex.YandexTranslationResponse;
import edu.java.translator.utils.YandexToken;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

public class YandexClientImpl implements YandexClient {

    private final YandexToken token;
    private final RestTemplate restTemplate;
    private final URI baseUrl;

    private static final String TRANSLATION_URN = "translate";
    private static final String LANGUAGES_URN = "languages";

    public YandexClientImpl(YandexToken token, RestTemplate restTemplate, URI baseUrl) {
        this.token = token;
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
    }

    @Override
    public YandexTranslationResponse translate(YandexTranslationRequest request) {
        request.setFolderId(token.folderId());
        RequestEntity<YandexTranslationRequest> requestEntity
                = new RequestEntity<>(request, HttpMethod.POST, baseUrl.resolve(TRANSLATION_URN));
        ResponseEntity<YandexTranslationResponse> response = restTemplate
                .exchange(requestEntity, YandexTranslationResponse.class);
        return response.getBody();
    }

    @Override
    public YandexLanguagesResponse getLanguages(YandexLanguagesRequest request) {
        request.setFolderId(token.folderId());
        RequestEntity<YandexLanguagesRequest> requestEntity
                = new RequestEntity<>(request, HttpMethod.POST, baseUrl.resolve(LANGUAGES_URN));
        ResponseEntity<YandexLanguagesResponse> response = restTemplate
                .exchange(requestEntity, YandexLanguagesResponse.class);
        return response.getBody();
    }
}
