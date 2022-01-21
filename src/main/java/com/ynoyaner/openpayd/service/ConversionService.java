package com.ynoyaner.openpayd.service;

import com.ynoyaner.openpayd.entity.Conversion;
import com.ynoyaner.openpayd.payload.ConvertApiRequest;
import com.ynoyaner.openpayd.payload.ConvertApiResponse;

import java.time.LocalDate;
import java.util.List;

public interface ConversionService {

    ConvertApiResponse convert(ConvertApiRequest convertApiRequest);

    List<Conversion> getAllConversionsWithGivenParametes(LocalDate conversionDate, Long transactionId, Integer pageNo, Integer pageSize);
}
