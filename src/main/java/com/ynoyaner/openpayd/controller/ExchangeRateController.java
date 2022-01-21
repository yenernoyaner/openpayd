package com.ynoyaner.openpayd.controller;

import com.ynoyaner.openpayd.payload.ApiResponse;
import com.ynoyaner.openpayd.payload.fixerio.FixerIOApiRateResponse;
import com.ynoyaner.openpayd.service.FxRateService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/rates")
@Api(value = "Exchange Rate Operations")
public class ExchangeRateController {

    private final FxRateService fxRateService;

    @Autowired
    public ExchangeRateController(FxRateService fxRateService) {
        this.fxRateService = fxRateService;
    }

    @GetMapping("/fxrate")
    public ResponseEntity<?> getFxRate(@RequestParam(defaultValue = "EUR",required = true) String sourceCurrency, @RequestParam(required = true) String targetCurrency) {
        FixerIOApiRateResponse response=this.fxRateService.getRate(sourceCurrency,targetCurrency);
        return new ResponseEntity<>(new ApiResponse(true,response.getRates().get(targetCurrency)), HttpStatus.OK);
    }

}
