package edu.java.translator.dtos.yandex;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class YandexTranslationRequest {

    private String folderId;
    List<String> texts;
    @JsonProperty("targetLanguageCode")
    private String targetLang;
    @JsonProperty("sourceLanguageCode")
    private String sourceLang;
}
