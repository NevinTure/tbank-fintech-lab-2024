package edu.java.translator.dtos;

import lombok.Data;

@Data
public class TranslationRequest {

    private String text;
    private String sourceLang;
    private String targetLang;
}
