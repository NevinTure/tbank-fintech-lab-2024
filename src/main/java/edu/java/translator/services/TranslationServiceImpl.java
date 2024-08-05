package edu.java.translator.services;

import edu.java.translator.dtos.Language;
import edu.java.translator.dtos.TranslationRequest;
import edu.java.translator.dtos.TranslationResponse;
import edu.java.translator.model.Translation;
import edu.java.translator.repositories.TranslationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class TranslationServiceImpl implements TranslationService {

    private final TranslationRepository repository;
    private final TranslationHandler handler;
    private final ModelMapper mapper;

    public TranslationServiceImpl(
            TranslationRepository repository,
            TranslationHandler handler,
            ModelMapper mapper) {
        this.repository = repository;
        this.handler = handler;
        this.mapper = mapper;
    }

    @Override
    public TranslationResponse translate(TranslationRequest request) {
        formatRequestLanguages(request);
        Translation translation = mapper.map(request, Translation.class);
        TranslationResponse response = handler.translate(request);
        translation.setTranslatedText(response.getTranslatedText());
        save(translation);
        return response;
    }

    @Override
    public List<Language> getSupportedLanguages() {
        return handler
                .getSupportedLanguages()
                .stream()
                .peek(v -> v.setCode(StringUtils.capitalize(v.getCode())))
                .toList();
    }

    @Override
    public void save(Translation translation) {
        repository.save(translation);
    }

    private void formatRequestLanguages(TranslationRequest request) {
        request.setSourceLang(request.getSourceLang().toLowerCase());
        request.setTargetLang(request.getTargetLang().toLowerCase());
    }
}
