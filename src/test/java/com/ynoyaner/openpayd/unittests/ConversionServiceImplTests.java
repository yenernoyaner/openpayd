package com.ynoyaner.openpayd.unittests;

import com.ynoyaner.openpayd.exception.FixerIOApiException;
import com.ynoyaner.openpayd.payload.ConvertApiRequest;
import com.ynoyaner.openpayd.payload.ConvertApiResponse;
import com.ynoyaner.openpayd.repository.ConversionRepository;
import com.ynoyaner.openpayd.service.impl.ConversionServiceImpl;
import com.ynoyaner.openpayd.service.impl.FxRateServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ConversionServiceImplTests {

    @InjectMocks
    private ConversionServiceImpl conversionService;

    @Mock
    private  FxRateServiceImpl fxRateService;

    @Test
    void should_ThrowException_WhenBaseNotEUR() {
        ConvertApiRequest request = new ConvertApiRequest(BigDecimal.valueOf(100),"USD","TRY");

        when(this.fxRateService.getRate(request.getSourceCurrency(),request.getTargetCurrency()))
                .thenThrow(FixerIOApiException.class);
        Executable executable=()->conversionService.convert(request);
        assertThrows(FixerIOApiException.class,executable);
    }
}
