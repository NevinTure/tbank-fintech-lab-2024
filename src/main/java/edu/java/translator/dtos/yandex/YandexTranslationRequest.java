package edu.java.translator.dtos.yandex;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class YandexTranslationRequest {

    private String folderId;
    List<String> texts;
    @JsonProperty("sourceLanguageCode")
    private String sourceLang;
    @JsonProperty("targetLanguageCode")
    private String targetLang;
}
