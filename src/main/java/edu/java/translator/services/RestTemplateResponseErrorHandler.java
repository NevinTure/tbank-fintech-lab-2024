package edu.java.translator.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.java.translator.exceptions.ClientBadRequestException;
import edu.java.translator.exceptions.ProviderInternalServerErrorException;
import java.io.IOException;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

@Component
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {

    private final ObjectMapper objMapper;

    public RestTemplateResponseErrorHandler(ObjectMapper objMapper) {
        this.objMapper = objMapper;
    }

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return response.getStatusCode().is5xxServerError()
                || response.getStatusCode().is4xxClientError();
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        Map<String, Object> map = objMapper.readValue(response.getBody(), Map.class);
        if (response.getStatusCode().is5xxServerError()) {
            switch (response.getStatusCode()) {
                case HttpStatus.INTERNAL_SERVER_ERROR -> throw new ProviderInternalServerErrorException(
                        "Internal Server Error",
                        "ProviderInternalServerErrorException",
                        response.getStatusCode().value()
                );
                default -> throw new IllegalStateException();
            }
        } else if (response.getStatusCode().is4xxClientError()) {
            String desc = (String) map.get("message");
            switch (response.getStatusCode()) {
                case HttpStatus.BAD_REQUEST -> throw new ClientBadRequestException(
                        desc,
                        "ClientBadRequestException",
                        response.getStatusCode().value()
                );
                default -> throw new IllegalStateException();
            }
        }
    }
}
