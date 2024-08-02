package edu.java.translator.dtos.yandex;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class YandexLanguagesResponse {

    private List<YandexLanguageEntry> languages;
}
