package com.ynoyaner.openpayd.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ynoyaner.openpayd.exception.FixerIOApiException;
import com.ynoyaner.openpayd.payload.fixerio.FixerIOApiRateResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

@Component
public class RestTemplateErrorHandler implements ResponseErrorHandler {

    private static ObjectMapper objectMapper;

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        HttpStatus status = response.getStatusCode();
        return status.is4xxClientError() || status.is5xxServerError();
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(response.getBody()))) {
            String httpBodyResponse = reader.lines().collect(Collectors.joining(""));

            FixerIOApiRateResponse errorResponse = objectMapper.readValue(httpBodyResponse, FixerIOApiRateResponse.class);
            if(errorResponse!=null && !errorResponse.isSuccess()){
                String errorMessage= errorResponse.getError().getInfo()!=null ? errorResponse.getError().getInfo():errorResponse.getError().getType().replaceAll("_"," ");
                throw new FixerIOApiException(errorResponse.getError().getCode(),errorMessage);
            }
        }
    }

    @PostConstruct
    public void setObjectMapper() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }
}
