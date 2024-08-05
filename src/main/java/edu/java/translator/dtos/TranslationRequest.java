package edu.java.translator.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TranslationRequest {

    private String userAddr;
    @NotEmpty
    private String sourceText;
    private String sourceLang;
    private String targetLang;

    public TranslationRequest(String text, String sourceLang, String targetLang) {
        this.sourceText = text;
        this.sourceLang = sourceLang;
        this.targetLang = targetLang;
    }
}
