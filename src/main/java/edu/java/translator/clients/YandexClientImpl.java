package edu.java.translator.clients;

import edu.java.translator.dtos.yandex.YandexTranslationRequest;
import edu.java.translator.dtos.yandex.YandexTranslationResponse;
import edu.java.translator.utils.YandexToken;

public class YandexClientImpl implements YandexClient {

    private final YandexToken token;

    public YandexClientImpl(YandexToken token) {
        this.token = token;
    }

    @Override
    public YandexTranslationResponse translate(YandexTranslationRequest request) {
        return null;
    }
}
