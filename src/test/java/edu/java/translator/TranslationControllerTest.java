package edu.java.translator;

import edu.java.translator.services.TranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest("app.provider.url=http://localhost:8080")
@AutoConfigureMockMvc
public class TranslationControllerTest extends IntegrationEnvironment {

    @MockBean
    private TranslationService service;
    @Autowired
    private MockMvc mvc;
}
