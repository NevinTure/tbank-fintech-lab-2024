package edu.java.translator.services;

import edu.java.translator.clients.YandexClient;
import edu.java.translator.dtos.TranslationRequest;
import edu.java.translator.dtos.yandex.YandexTranslationRequest;
import edu.java.translator.dtos.yandex.YandexTranslationResponse;
import lombok.SneakyThrows;
import org.springframework.boot.web.client.RestTemplateBuilder;

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
    public String translate(TranslationRequest request) {
        List<YandexTranslationRequest> requests = convertToRequests(request);
        List<Future<YandexTranslationResponse>> futures = threadPool
                .invokeAll(
                        requests
                                .stream()
                                .<Callable<YandexTranslationResponse>>map(v -> () -> client.translate(v)).toList());
        List<YandexTranslationResponse> responses = new ArrayList<>();
        for (Future<YandexTranslationResponse> future : futures) {
            responses.add(future.get());
        }
        return null;
    }

    private List<YandexTranslationRequest> convertToRequests(TranslationRequest request) {
        String text = request.getText().strip();
        return Arrays.stream(getWords(text)).map(v -> {
            YandexTranslationRequest yaReq = new YandexTranslationRequest();
            yaReq.setTexts(List.of(v));
            yaReq.setSourceLang(request.getSourceLang());
            yaReq.setTargetLang(request.getTargetLang());
            return yaReq;
        }).toList();
    }

    private String[] getWords(String text) {
        return text.split("\\s+");
    }

    private String[] getSpaces(String text) {
        return text.split("\\S+");
    }
}
