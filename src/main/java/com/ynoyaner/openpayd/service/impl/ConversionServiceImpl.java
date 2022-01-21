package com.ynoyaner.openpayd.service.impl;

import com.ynoyaner.openpayd.constants.ApplicationConstants;
import com.ynoyaner.openpayd.entity.Conversion;
import com.ynoyaner.openpayd.exception.ResourceNotFoundException;
import com.ynoyaner.openpayd.payload.ConvertApiRequest;
import com.ynoyaner.openpayd.payload.ConvertApiResponse;
import com.ynoyaner.openpayd.payload.fixerio.FixerIOApiRateResponse;
import com.ynoyaner.openpayd.repository.ConversionRepository;
import com.ynoyaner.openpayd.service.ConversionService;
import com.ynoyaner.openpayd.service.FxRateService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ConversionServiceImpl implements ConversionService {

    private final FxRateService fxRateService;
    private final ConversionRepository conversionRepository;

    public ConversionServiceImpl(FxRateService fxRateService, ConversionRepository conversionRepository) {
        this.fxRateService = fxRateService;
        this.conversionRepository=conversionRepository;
    }

    @Override
    public ConvertApiResponse convert(ConvertApiRequest request) {

        FixerIOApiRateResponse fixerIOResponse = fxRateService.getRate(request.getSourceCurrency(),request.getTargetCurrency());
        BigDecimal targetCurrencyRate=fixerIOResponse.getRates().get(request.getTargetCurrency());
        BigDecimal amountInTargetCurrency=request.getSourceAmount().multiply(targetCurrencyRate);

        Conversion savedConversion=saveConversionTODB(request,amountInTargetCurrency,fixerIOResponse.getDate());

        ConvertApiResponse response=new ConvertApiResponse();
        response.setTransactionId(savedConversion.getId());
        response.setAmountInTargetCurrency(amountInTargetCurrency);
        return response;
    }

    private Conversion saveConversionTODB(ConvertApiRequest request, BigDecimal amountInTargetCurrency, LocalDate date) {
        Conversion conversion=new Conversion(request.getSourceAmount(),request.getSourceCurrency(),request.getTargetCurrency(),amountInTargetCurrency,date);
        return conversionRepository.save(conversion);
    }

    @Override
    public List<Conversion> getAllConversionsWithGivenParametes(LocalDate conversionDate, Long transactionId, Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo,pageSize,Sort.by("id").ascending());

        Page<Conversion> conversions = conversionRepository.findAll((root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (conversionDate!= null ) {
                predicates.add(builder.equal(root.get("conversionDate"), conversionDate));
            }
            if (transactionId != null) {
                predicates.add(builder.equal(root.get("id"), transactionId));
            }
            return builder.and(predicates.toArray(new Predicate[0]));
        }, pageable);

        if (conversions.isEmpty()) {
            throw new ResourceNotFoundException(ApplicationConstants.ErrorCodes.RESOURCE_NOT_FOUND,"No Conversion found with given parameters!");
        }
        return conversions.getContent();
    }
}
