package edu.java.translator.services;

import edu.java.translator.clients.YandexClient;
import edu.java.translator.dtos.Language;
import edu.java.translator.dtos.TranslationRequest;
import edu.java.translator.dtos.TranslationResponse;
import edu.java.translator.dtos.yandex.YandexLanguagesRequest;
import edu.java.translator.dtos.yandex.YandexLanguagesResponse;
import edu.java.translator.dtos.yandex.YandexTranslationRequest;
import edu.java.translator.dtos.yandex.YandexTranslationResponse;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

public class YandexTranslationHandler implements TranslationHandler {

    private final YandexClient client;
    private final ThreadPoolExecutor threadPool;

    public YandexTranslationHandler(YandexClient client, ThreadPoolExecutor threadPool) {
        this.client = client;
        this.threadPool = threadPool;
    }

    @SneakyThrows
    @Override
    public TranslationResponse translate(TranslationRequest request) {
        List<YandexTranslationRequest> requests = convertToRequests(request);
        List<Future<YandexTranslationResponse>> futures = threadPool
                .invokeAll(
                        requests
                                .stream()
                                .<Callable<YandexTranslationResponse>>map(v -> () -> client.translate(v))
                                .toList());
        List<String> translatedWords = new ArrayList<>();
        for (Future<YandexTranslationResponse> future : futures) {
            translatedWords.add(future.get().getTranslations().getFirst().getText());
        }
        return new TranslationResponse(
                joinWords(translatedWords),
                request.getTargetLang()
        );
    }

    @Override
    public List<Language> getSupportedLanguages() {
        YandexLanguagesRequest request = new YandexLanguagesRequest();
        YandexLanguagesResponse response = client.getLanguages(request);
        return response
                .getLanguages()
                .stream()
                .map(v -> new Language(String.format("%s - %s", v.getName(), v.getCode())))
                .toList();
    }

    private List<YandexTranslationRequest> convertToRequests(TranslationRequest request) {
        String text = request.getText().strip();
        return getWords(text).stream().map(v -> {
            YandexTranslationRequest yaReq = new YandexTranslationRequest();
            yaReq.setTexts(List.of(v));
            yaReq.setSourceLang(request.getSourceLang());
            yaReq.setTargetLang(request.getTargetLang());
            return yaReq;
        }).toList();
    }

    private String joinWords(List<String> translated) {
        return String.join(" ", translated);
    }

    private List<String> getWords(String text) {
        return Arrays.stream(text.split("\\s+")).toList();
    }
}
