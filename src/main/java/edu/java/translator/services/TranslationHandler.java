package edu.java.translator.services;

import edu.java.translator.dtos.Language;
import edu.java.translator.dtos.TranslationRequest;
import edu.java.translator.dtos.TranslationResponse;
import java.util.List;

public interface TranslationHandler {

    TranslationResponse translate(TranslationRequest request);

    List<Language> getSupportedLanguages();
}
