package com.ynoyaner.openpayd.integrationtests;

import com.ynoyaner.openpayd.payload.ApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ExchangeControllerTests {

    @LocalServerPort
    private int port;

    private URL baseUrl;

    @Autowired
    private TestRestTemplate template;

    @BeforeEach()
    public void setup() throws MalformedURLException {
        this.baseUrl=new URL("http://localhost:" + port + "/api/v1/rates/fxrate");
    }

    @Test
    public void convert_Succes_When_ValidParamsSend() throws Exception {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl.toString())
                .queryParam("sourceCurrency", "EUR")
                .queryParam("targetCurrency", "TRY");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity request = new HttpEntity(headers);

        ResponseEntity<ApiResponse> response = template.exchange(
                builder.build().encode().toUri(),
                HttpMethod.GET,
                request,
                ApiResponse.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK,response.getStatusCode());
        ApiResponse apiResponse=response.getBody();
        assertNotNull(apiResponse);
        assertEquals(Boolean.TRUE,apiResponse.getSuccess());
    }

    @Test
    public void convert_Fail_When_OneParamMissing() throws Exception {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl.toString())
                .queryParam("sourceCurrency", "EUR");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity request = new HttpEntity(headers);

        ResponseEntity<ApiResponse> response = template.exchange(
                builder.build().encode().toUri(),
                HttpMethod.GET,
                request,
                ApiResponse.class);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
    }
}
