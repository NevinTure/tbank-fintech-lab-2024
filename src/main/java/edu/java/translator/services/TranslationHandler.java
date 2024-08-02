package edu.java.translator.services;

import edu.java.translator.dtos.TranslationRequest;

public interface TranslationHandler {

    String translate(TranslationRequest request);
}
