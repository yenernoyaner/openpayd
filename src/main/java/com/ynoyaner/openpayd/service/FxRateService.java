package com.ynoyaner.openpayd.service;

import com.ynoyaner.openpayd.payload.fixerio.FixerIOApiRateResponse;

public interface FxRateService {
    FixerIOApiRateResponse getRate(String sourceCurrency, String targetCurrency);

}
