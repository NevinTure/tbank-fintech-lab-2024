package edu.java.translator.dtos.yandex;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class YandexTranslationResponse {

    @JsonProperty("translations")
    private List<YandexTranslationEntry> translations;
}
