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

    @Override
    public TranslationResponse translate(TranslationRequest request) {
        List<YandexTranslationRequest> requests = convertToRequests(request);
        List<YandexTranslationResponse> responses = sendRequests(requests);
        return new TranslationResponse(
                extractTextFromResponses(responses)
        );
    }

    @Override
    public List<Language> getSupportedLanguages() {
        YandexLanguagesRequest request = new YandexLanguagesRequest();
        YandexLanguagesResponse response = client.getLanguages(request);
        return response
                .getLanguages()
                .stream()
                .map(v -> new Language(v.getCode()))
                .toList();
    }

    private List<YandexTranslationRequest> convertToRequests(TranslationRequest request) {
        String text = request.getSourceText().strip();
        return getWords(text).stream().map(v -> {
            YandexTranslationRequest yaReq = new YandexTranslationRequest();
            yaReq.setTexts(List.of(v));
            yaReq.setSourceLang(request.getSourceLang());
            yaReq.setTargetLang(request.getTargetLang());
            return yaReq;
        }).toList();
    }

    private List<String> getWords(String text) {
        return Arrays.stream(text.split("\\s+")).toList();
    }

    @SneakyThrows
    private List<YandexTranslationResponse> sendRequests(List<YandexTranslationRequest> requests) {
        List<Future<YandexTranslationResponse>> futures = threadPool
                .invokeAll(
                        requests
                                .stream()
                                .<Callable<YandexTranslationResponse>>map(v -> () -> client.translate(v))
                                .toList());
        List<YandexTranslationResponse> responses = new ArrayList<>(futures.size());
        for (Future<YandexTranslationResponse> future : futures) {
            responses.add(future.get());
        }
        return responses;
    }

    private String extractTextFromResponses(List<YandexTranslationResponse> responses) {
        return joinWords(
                responses
                        .stream()
                        .map(v -> v.getTranslations()
                                .getFirst()
                                .getText())
                        .toList()
        );
    }

    private String joinWords(List<String> translated) {
        return String.join(" ", translated);
    }
}
