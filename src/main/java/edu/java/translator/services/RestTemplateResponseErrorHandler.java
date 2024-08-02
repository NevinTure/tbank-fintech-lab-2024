package edu.java.translator.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.java.translator.exceptions.ApiClientErrorException;
import edu.java.translator.exceptions.ApiServerErrorException;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;
import java.util.Map;

@Component
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {

    private final ObjectMapper objMapper;

    public RestTemplateResponseErrorHandler(ObjectMapper objMapper) {
        this.objMapper = objMapper;
    }

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return response.getStatusCode().is5xxServerError() ||
                response.getStatusCode().is4xxClientError();
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        Map<String, Object> map = objMapper.readValue(response.getBody(), Map.class);
        if (response.getStatusCode().is5xxServerError()) {
            throw new ApiServerErrorException(
                    "Internal Server Error",
                    "ApiServerErrorException",
                    response.getStatusCode().value()
            );
        } else if (response.getStatusCode().is4xxClientError()) {
            String desc = (String) map.get("message");
            throw new ApiClientErrorException(
                    desc,
                    "ApiClientErrorException",
                    response.getStatusCode().value()
            );
        }
    }
}
