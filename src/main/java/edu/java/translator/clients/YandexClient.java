package edu.java.translator.clients;

import edu.java.translator.dtos.yandex.YandexTranslationRequest;
import edu.java.translator.dtos.yandex.YandexTranslationResponse;

public interface YandexClient {

    YandexTranslationResponse translate(YandexTranslationRequest request);
}
