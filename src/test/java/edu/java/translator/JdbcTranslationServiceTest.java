package edu.java.translator;

import edu.java.translator.dtos.Language;
import edu.java.translator.dtos.TranslationRequest;
import edu.java.translator.dtos.TranslationResponse;
import edu.java.translator.model.Translation;
import edu.java.translator.repositories.TranslationRepository;
import edu.java.translator.services.TranslationHandler;
import edu.java.translator.services.TranslationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class JdbcTranslationServiceTest extends IntegrationEnvironment {

    @MockBean
    private TranslationRepository repo;
    @MockBean
    private TranslationHandler handler;
    @Autowired
    private TranslationService service;

    @Test
    public void testTranslate() {
        //given
        TranslationRequest request = new TranslationRequest(
                "127.0.0.1",
                "Dog",
                "En",
                "Ru"
        );
        TranslationResponse response = new TranslationResponse(
                "Собака"
        );

        //when
        Mockito.when(handler.translate(request)).thenReturn(response);
        service.translate(request);

        //then
        Translation expected = new Translation(
                "127.0.0.1",
                "en",
                "ru",
                "Dog",
                "Собака"
        );
        Mockito.verify(repo).save(expected);
    }

    @Test
    public void testGetSupportedLanguages() {
        //given
        List<Language> languages = List.of(
                new Language("en"),
                new Language("ru"),
                new Language("kz")
        );

        //when
        Mockito.when(handler.getSupportedLanguages()).thenReturn(languages);
        List<Language> result = service.getSupportedLanguages();

        //then
        List<Language> expectedResult = List.of(
                new Language("En"),
                new Language("Ru"),
                new Language("Kz")
        );
        assertThat(result).containsExactlyElementsOf(expectedResult);
    }
}
