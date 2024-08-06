package edu.java.translator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Translation {

    private Long id;
    private String userAddr;
    private String sourceLang;
    private String targetLang;
    private String originText;
    private String translatedText;

    public Translation(String userAddr, String sourceLang, String targetLang, String originText, String translatedText) {
        this.userAddr = userAddr;
        this.sourceLang = sourceLang;
        this.targetLang = targetLang;
        this.originText = originText;
        this.translatedText = translatedText;
    }
}
