package com.ynoyaner.openpayd.service.impl;

import com.ynoyaner.openpayd.payload.fixerio.FixerIOApiRateResponse;
import com.ynoyaner.openpayd.service.FxRateService;
import com.ynoyaner.openpayd.service.client.FixerIORateServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FxRateServiceImpl implements FxRateService {

    private final FixerIORateServiceClient fixerIORateServiceClient;

    @Autowired
    public FxRateServiceImpl(FixerIORateServiceClient fixerIORateServiceClient) {
        this.fixerIORateServiceClient=fixerIORateServiceClient;
    }

    @Override
    public FixerIOApiRateResponse getRate(String sourceCurrency, String targetCurrency) {
       return fixerIORateServiceClient.getExchangeRateResponse(sourceCurrency,targetCurrency);
    }
}
