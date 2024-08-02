package edu.java.translator.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
public class Translation {

    private Long id;
    private String userAddr;
    private String sourceLang;
    private String targetLang;
    private String originText;
    private String translatedText;
    private OffsetDateTime createdAt;

    public Translation() {
        this.createdAt = OffsetDateTime.now().withNano(0);
    }
}
