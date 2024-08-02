package edu.java.translator.dtos.yandex;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class YandexTranslationResponse {

    @JsonProperty("translations")
    private List<YandexTranslationEntry> translations;
}
