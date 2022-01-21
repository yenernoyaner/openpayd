package com.ynoyaner.openpayd.integrationtests;

import com.ynoyaner.openpayd.entity.Conversion;
import com.ynoyaner.openpayd.payload.ApiResponse;
import com.ynoyaner.openpayd.payload.ConvertApiRequest;
import com.ynoyaner.openpayd.payload.ConvertApiResponse;
import com.ynoyaner.openpayd.repository.ConversionRepository;
import com.ynoyaner.openpayd.service.client.FixerIORateServiceClient;
import com.ynoyaner.openpayd.service.impl.ConversionServiceImpl;
import com.ynoyaner.openpayd.service.impl.FxRateServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/loaddata.sql", executionPhase = BEFORE_TEST_METHOD)
@Sql(scripts = "/cleandata.sql", executionPhase = AFTER_TEST_METHOD)
public class ConversionControllerTests {

    @LocalServerPort
    private int port;

    private URL baseUrl;

    @Autowired
    private TestRestTemplate template;

    @InjectMocks
    private ConversionServiceImpl conversionService;

    @BeforeEach()
    public void setup() throws MalformedURLException {
        this.baseUrl=new URL("http://localhost:" + port + "/api/v1/conversions");
    }

    @Test
    public void convert_Succes_When_ValidParamsSend() throws Exception {
        ConvertApiRequest request=new ConvertApiRequest(BigDecimal.valueOf(100),"EUR","TRY");

        ResponseEntity<ApiResponse> responseEntity=template.postForEntity(baseUrl.toString().concat("/convert"),request,ApiResponse.class);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        ApiResponse response=responseEntity.getBody();
        assertNotNull(response);
        assertEquals(Boolean.TRUE,response.getSuccess());
    }

    @Test
    public void convert_Fail_When_InvalidParamsSend() {
        ConvertApiRequest request=new ConvertApiRequest(BigDecimal.valueOf(100),"EUR","AAA");

        ResponseEntity<ApiResponse> responseEntity=template.postForEntity(baseUrl.toString().concat("/convert"),request,ApiResponse.class);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        ApiResponse response=responseEntity.getBody();
        assertNotNull(response);
        assertEquals(Boolean.FALSE,response.getSuccess());
    }

    @Test
    public void convert_Fail_AfterTimeOut() {

        assertTimeoutPreemptively(Duration.ofSeconds(7),()->{
            ConvertApiRequest request=new ConvertApiRequest(BigDecimal.valueOf(100),"EUR","TRY");
            ResponseEntity<ApiResponse> responseEntity=template.postForEntity(baseUrl.toString().concat("/convert"),request,ApiResponse.class);
            assertNotNull(responseEntity);
            assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
            ApiResponse response=responseEntity.getBody();
            assertNotNull(response);
            assertEquals(Boolean.TRUE,response.getSuccess());
        });
    }

    @Test
    public void convertList_Succes_WhenOneorMoreParamsSend() {

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl.toString().concat("/conversionList"))
                .queryParam("conversionDate", "2022-01-20")
                .queryParam("transactionId", 111);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity request = new HttpEntity(headers);
        ResponseEntity<ApiResponse>  responseEntity = template.exchange(
                builder.build().encode().toUri(),
                HttpMethod.GET,
                request,
                ApiResponse.class);

        assertNotNull(responseEntity);
        ApiResponse response= (ApiResponse) responseEntity.getBody();
        assertNotNull(response);
        assertNotNull(response.getData());
        assertEquals(Boolean.TRUE,response.getSuccess());
    }

    @Test
    public void convertList_Fail_WhenNoParamsSend() {

        Map<String,Object> requestMap=new HashMap<>();
        requestMap.put("transactionId",null);
        requestMap.put("conversionDate", null);

        ResponseEntity<?> responseEntity=template.getForEntity(baseUrl.toString().concat("/conversionList"),ApiResponse.class,requestMap);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.BAD_REQUEST,responseEntity.getStatusCode());
        ApiResponse response= (ApiResponse) responseEntity.getBody();
        assertNotNull(response);
        assertEquals(Boolean.FALSE,response.getSuccess());
        assertEquals("1002",response.getErrorCode());
    }

}
