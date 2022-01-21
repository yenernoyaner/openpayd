package com.ynoyaner.openpayd.controller;

import com.ynoyaner.openpayd.constants.ApplicationConstants;
import com.ynoyaner.openpayd.entity.Conversion;
import com.ynoyaner.openpayd.exception.MissingParameterException;
import com.ynoyaner.openpayd.payload.ApiResponse;
import com.ynoyaner.openpayd.payload.ConvertApiRequest;
import com.ynoyaner.openpayd.payload.ConvertApiResponse;
import com.ynoyaner.openpayd.service.ConversionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/conversions")
@Api(value = "Conversion Operations")
@Validated
public class ConversionController {

    private final ConversionService conversionService;

    @Autowired
    public ConversionController(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @PostMapping("/convert")
    @ApiOperation(value = "Converts given amount from source currency to target currency, returns amount in target currency and a transaction id", response = ConvertApiResponse.class)
    public ResponseEntity<?> convert(@Valid @RequestBody ConvertApiRequest convertApiRequest) {

        ConvertApiResponse response=conversionService.convert(convertApiRequest);
        return new ResponseEntity<>(new ApiResponse(true,response),HttpStatus.OK);
    }

    @Validated
    @GetMapping("/conversionList")
    @ApiOperation(value = "Lists all conversions with the given transaction id or transaction date, at least on parameter should be provided, conversion date should be sent in  yyyy-MM-dd format")
    public ResponseEntity<?> getConversions(@RequestParam(name = "transactionId",required = false) Long transactionId,
                                           @RequestParam(name = "conversionDate",required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate conversionDate,
                                           @RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "5") Integer pageSize){
        if(transactionId==null && conversionDate==null)
            throw new MissingParameterException(ApplicationConstants.ErrorCodes.MISSING_PARAMETER,"At least one of the parameters (transactionId, conversionDate) should be provided!");
        List<Conversion> conversionList = conversionService.getAllConversionsWithGivenParametes(conversionDate,transactionId,pageNo,pageSize);
        return new ResponseEntity<>(new ApiResponse(true,conversionList),HttpStatus.OK);
    }


}
