package edu.java.translator;

import edu.java.translator.dtos.TranslationRequest;
import edu.java.translator.dtos.TranslationResponse;
import edu.java.translator.exceptions.ProviderInternalServerErrorException;
import edu.java.translator.services.TranslationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest("app.provider.url=http://localhost:8080")
@AutoConfigureMockMvc
public class TranslationControllerTest extends IntegrationEnvironment {

    @MockBean
    private TranslationService service;
    @Autowired
    private MockMvc mvc;

    @Test
    public void testShowWhen200Ok() throws Exception {
        //then
        mvc.perform(get("/translator"))
                .andExpect(status().isOk());
    }

    @Test
    public void testShowWhen400ProviderError() throws Exception {
        //when
        Mockito.when(service.getSupportedLanguages())
                .thenThrow(new ProviderInternalServerErrorException(
                        "provider error",
                        "ProviderInternalServerErrorException",
                        500
                ));

        //then
        mvc.perform(get("/translator"))
                .andExpect(status().is4xxClientError())
                .andExpect(content().string(containsString("http 500 provider error")));
    }

    @Test
    public void testTranslateWhen200Ok() throws Exception {
        //given
        TranslationRequest request = new TranslationRequest(
                "text",
                "en",
                "ru"
        );
        TranslationResponse response = new TranslationResponse(
                "текст"
        );

        //when
        Mockito.when(service.translate(request)).thenReturn(response);

        //then
        mvc.perform(post("/translator")
                        .flashAttr("request", request))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("текст")));
    }

    @Test
    public void testTranslateWhen400BadRequest() throws Exception {
        //given
        TranslationRequest request = new TranslationRequest(
                null,
                "en",
                "ru"
        );

        //then
        mvc.perform(post("/translator")
                        .flashAttr("request", request))
                .andExpect(status().is4xxClientError())
                .andExpect(content()
                        .string(containsString("http 400 Invalid request params: sourceText")));
    }
}
