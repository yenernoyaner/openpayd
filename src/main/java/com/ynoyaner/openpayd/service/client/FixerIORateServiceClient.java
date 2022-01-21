package com.ynoyaner.openpayd.service.client;

import com.ynoyaner.openpayd.config.RestTemplateErrorHandler;
import com.ynoyaner.openpayd.constants.FixerIOApiConstants;
import com.ynoyaner.openpayd.exception.FixerIOApiException;
import com.ynoyaner.openpayd.payload.fixerio.FixerIOApiRateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Service
public class FixerIORateServiceClient {

    private final RestTemplate restTemplate;
    private final String fixerApiBaseUrl;


    @Autowired
    public FixerIORateServiceClient(RestTemplateBuilder restTemplateBuilder, @Value("${fixer.io.base.url}") String fixerApiBaseUrl) {
        this.restTemplate = restTemplateBuilder.setConnectTimeout(Duration.ofSeconds(FixerIOApiConstants.CONNECT_TIMEOUT))
                                                .setReadTimeout(Duration.ofSeconds(FixerIOApiConstants.READ_TIMEOUT))
                                                .errorHandler(new RestTemplateErrorHandler())
                                                .build();
        this.fixerApiBaseUrl = fixerApiBaseUrl;
    }


    public FixerIOApiRateResponse getExchangeRateResponse(String sourceCurrency, String targetCurrency) {

        String requestURL = createFixerIOApiLatestRatesRequest(sourceCurrency, targetCurrency);
        ResponseEntity<FixerIOApiRateResponse> response = restTemplate.getForEntity(requestURL, FixerIOApiRateResponse.class);
        FixerIOApiRateResponse fixerIOApiRateResponse = response.getBody();
        if (fixerIOApiRateResponse == null) {
            throw new FixerIOApiException(FixerIOApiConstants.ERROR_CODES.UNEXPECTED_EXCEPTION, FixerIOApiConstants.ERROR_CODES.UNEXPECTED_EXCEPTION_MESSAGE);
        }
        if (fixerIOApiRateResponse.isSuccess()) {
            return fixerIOApiRateResponse;
        } else {
            String errorMessage = fixerIOApiRateResponse.getError().getInfo() != null ? fixerIOApiRateResponse.getError().getInfo() : fixerIOApiRateResponse.getError().getType().replaceAll("_", " ");
            throw new FixerIOApiException(fixerIOApiRateResponse.getError().getCode(), errorMessage);
        }

   }

    private String createFixerIOApiLatestRatesRequest(String sourceCurrency,String targetCurrency) {
        return this.fixerApiBaseUrl.concat(FixerIOApiConstants.LATEST).concat("?")
                .concat("access_key=").concat(FixerIOApiConstants.ACCESS_KEY)
                .concat("&base=").concat(sourceCurrency)
                .concat("&symbols=").concat(targetCurrency);
    }
}
