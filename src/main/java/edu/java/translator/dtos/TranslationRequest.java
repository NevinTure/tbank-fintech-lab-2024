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

    public TranslationRequest(String sourceText, String sourceLang, String targetLang) {
        this.sourceText = sourceText;
        this.sourceLang = sourceLang;
        this.targetLang = targetLang;
    }
}
