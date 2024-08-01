package edu.java.translator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Translation {

    private Integer id;
    private String userAddr;
    private String sourceLang;
    private String targetLang;
    private String originText;
    private String translatedText;
}
