package edu.java.translator;

import edu.java.translator.clients.YandexClient;
import edu.java.translator.dtos.Language;
import edu.java.translator.dtos.TranslationRequest;
import edu.java.translator.dtos.TranslationResponse;
import edu.java.translator.dtos.yandex.*;
import edu.java.translator.services.TranslationHandler;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("yandex")
public class YandexTranslationHandlerTest extends IntegrationEnvironment {

    @MockBean
    private YandexClient client;
    @Autowired
    private TranslationHandler handler;

    @Test
    public void testTranslate() {
        //given
        YandexTranslationRequest yaRequest1 = createRequest("Ты");
        YandexTranslationRequest yaRequest2 = createRequest("где,");
        YandexTranslationRequest yaRequest3 = createRequest("друг?");
        YandexTranslationResponse yaResponse1 = createResponse("You");
        YandexTranslationResponse yaResponse2 = createResponse("where,");
        YandexTranslationResponse yaResponse3 = createResponse("friend?");
        TranslationRequest request = new TranslationRequest(
                "Ты где, друг?",
                "ru",
                "en"
        );

        //when
        Mockito.when(client.translate(yaRequest1)).thenReturn(yaResponse1);
        Mockito.when(client.translate(yaRequest2)).thenReturn(yaResponse2);
        Mockito.when(client.translate(yaRequest3)).thenReturn(yaResponse3);
        TranslationResponse result = handler.translate(request);

        //then
        TranslationResponse expectedResult = new TranslationResponse(
                "You where, friend?"
        );
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testGetSupportedLanguages() {
        //given
        YandexLanguagesRequest yaRequest = new YandexLanguagesRequest();
        YandexLanguagesResponse yaResponse = new YandexLanguagesResponse(
                List.of(new YandexLanguageEntry("en", "English"),
                        new YandexLanguageEntry("ru", "Русский"),
                        new YandexLanguageEntry("az", "azərbaycan")
                ));

        //when
        Mockito.when(client.getLanguages(yaRequest)).thenReturn(yaResponse);
        List<Language> result = handler.getSupportedLanguages();

        //then
        List<Language> expectedResult = List.of(
                new Language("en"),
                new Language("ru"),
                new Language("az")
        );
        assertThat(result).containsExactlyElementsOf(expectedResult);
    }

    private YandexTranslationRequest createRequest(String word) {
        return new YandexTranslationRequest(
                null,
                List.of(word),
                "ru",
                "en"
        );
    }

    private YandexTranslationResponse createResponse(String word) {
        return new YandexTranslationResponse(
                List.of(new YandexTranslationEntry(word))
        );
    }
}
