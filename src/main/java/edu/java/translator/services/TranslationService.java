package edu.java.translator.services;

import edu.java.translator.dtos.Language;
import edu.java.translator.dtos.TranslationRequest;
import edu.java.translator.dtos.TranslationResponse;
import edu.java.translator.model.Translation;

import java.util.List;

public interface TranslationService {

    TranslationResponse translate(TranslationRequest request);
    List<Language> getSupportedLanguages();
    void save(Translation translation);
}
