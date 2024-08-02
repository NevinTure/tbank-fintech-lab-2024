package edu.java.translator;

import edu.java.translator.dtos.TranslationRequest;
import edu.java.translator.dtos.TranslationResponse;
import edu.java.translator.services.TranslationHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class IntegrationTest {

    private final TranslationHandler translationHandler;

    @Autowired
    public IntegrationTest(TranslationHandler translationHandler) {
        this.translationHandler = translationHandler;
    }


    @Test
    void contextLoads() {
    }

    @Test
    public void test() {
        TranslationRequest request = new TranslationRequest(
                "Hello world",
                "en",
                "ru"
        );
        TranslationResponse response = translationHandler.translate(request);
        System.out.println(response.getTranslatedText());
    }
}
