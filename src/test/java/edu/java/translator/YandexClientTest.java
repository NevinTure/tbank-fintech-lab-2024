package edu.java.translator;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import edu.java.translator.clients.YandexClient;
import edu.java.translator.dtos.yandex.*;
import edu.java.translator.exceptions.ClientBadRequestException;
import edu.java.translator.exceptions.ProviderException;
import edu.java.translator.exceptions.ProviderInternalServerErrorException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest("app.provider.url=http://localhost:8080")
@WireMockTest(httpPort = 8080)
public class YandexClientTest extends IntegrationEnvironment {

    private final YandexClient client;

    @Autowired
    public YandexClientTest(YandexClient client) {
        this.client = client;
    }

    @Test
    public void testTranslateWhen200Ok() {
        //given
        YandexTranslationRequest request = new YandexTranslationRequest(
                "test",
                List.of("Hi"),
                "ru",
                "en"
        );

        //when
        stubFor(post("/translate").willReturn(jsonResponse("""
                {
                    "translations": [
                        {
                            "text": "Привет"
                        }
                    ]
                }
                """, 200)));
        YandexTranslationResponse result = client.translate(request);

        //then
        YandexTranslationResponse expectedResult = new YandexTranslationResponse(
                List.of(new YandexTranslationEntry("Привет"))
        );
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testTranslateWhen400BadRequest() {
        //given
        YandexTranslationRequest request = new YandexTranslationRequest(
                "folderId",
                List.of("Hi"),
                "ru",
                "en"
        );

        //when
        stubFor(post("/translate").willReturn(jsonResponse("""
                {
                    "code": 3,
                    "message": "error"
                }
                """, 400)));

        //then
        assertThatExceptionOfType(ClientBadRequestException.class)
                .isThrownBy(() -> client.translate(request));
    }

    @Test
    public void testTranslateWhen500InternalServerError() {
        //given
        YandexTranslationRequest request = new YandexTranslationRequest(
                "test",
                List.of("Hi"),
                "ru",
                "en"
        );

        //when
        stubFor(post("/translate").willReturn(jsonResponse("""
                {
                    "message": "Internal server error"
                }
                """, 500)));

        //then
        assertThatExceptionOfType(ProviderException.class)
                .isThrownBy(() -> client.translate(request));
    }

    @Test
    public void testGetLanguagesWhen200Ok() {
        //given
        YandexLanguagesRequest request = new YandexLanguagesRequest(
                "test"
        );

        //when
        stubFor(post("/languages").willReturn(jsonResponse("""
                {
                    "languages": [
                        {
                        "code": "az",
                        "name": "azərbaycan"
                        },
                        {
                        "code": "sq",
                        "name": "shqip"
                        }
                    ]
                }
                """, 200)));
        YandexLanguagesResponse result = client.getLanguages(request);

        //then
        YandexLanguagesResponse expectedResult = new YandexLanguagesResponse(
                List.of(
                        new YandexLanguageEntry("az", "azərbaycan"),
                        new YandexLanguageEntry("sq", "shqip"))
        );
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testGetLanguagesWhen400BadRequest() {
        //given
        YandexLanguagesRequest request = new YandexLanguagesRequest(
                "test"
        );

        //when
        stubFor(post("/languages").willReturn(jsonResponse("""
                {
                    "code": 3,
                    "message": "error"
                }
                """, 400)));

        //then
        assertThatExceptionOfType(ClientBadRequestException.class)
                .isThrownBy(() -> client.getLanguages(request));
    }

    @Test
    public void testGetLanguagesWhen500InternalServerError() {
        //given
        YandexLanguagesRequest request = new YandexLanguagesRequest(
                "test"
        );

        //when
        stubFor(post("/languages").willReturn(jsonResponse("""
                {
                    "message": "Internal server error"
                }
                """, 500)));

        //then
        assertThatExceptionOfType(ProviderInternalServerErrorException.class)
                .isThrownBy(() -> client.getLanguages(request));
    }
}
