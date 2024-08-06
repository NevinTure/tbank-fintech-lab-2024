package edu.java.translator.dtos.yandex;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class YandexLanguagesResponse {

    private List<YandexLanguageEntry> languages;
}
