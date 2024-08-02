package edu.java.translator.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TranslationRequest {

    private String userAddr;
    private String text;
    private String sourceLang;
    private String targetLang;
}
