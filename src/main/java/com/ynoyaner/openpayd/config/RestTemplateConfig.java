package com.ynoyaner.openpayd.config;

import com.ynoyaner.openpayd.constants.FixerIOApiConstants;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class RestTemplateConfig {

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplateBuilder()
                .setConnectTimeout(Duration.ofSeconds(FixerIOApiConstants.CONNECT_TIMEOUT))
                .setReadTimeout(Duration.ofMillis(FixerIOApiConstants.READ_TIMEOUT))
                .build();
    }
}
