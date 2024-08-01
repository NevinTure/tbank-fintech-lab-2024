package edu.java.translator.utils;

public class TokenParser {

    private TokenParser() {
    }

    public static YandexToken parseYandexToken(String tokenStr) {
        String[] data = tokenStr.split(":");
        return new YandexToken(data[0], data[1]);
    }
}
