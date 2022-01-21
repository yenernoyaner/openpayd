package com.ynoyaner.openpayd.payload;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class ConvertApiRequest implements Serializable {

    @NotNull
    @PositiveOrZero(message = "numbers accepted only!")
    @ApiModelProperty(required = true,example = "3558.76")
    private BigDecimal sourceAmount;

    @NotBlank
    @Size(min = 2,max = 3)
    @ApiModelProperty(required = true,example = "EUR")
    private String sourceCurrency;

    @NotBlank
    @Size(min = 2,max = 3)
    @ApiModelProperty(required = true,example = "TRY")
    private String targetCurrency;

    public ConvertApiRequest(BigDecimal sourceAmount, String sourceCurrency, String targetCurrency) {
        this.sourceAmount = sourceAmount;
        this.sourceCurrency = sourceCurrency;
        this.targetCurrency = targetCurrency;
    }

    public BigDecimal getSourceAmount() {
        return sourceAmount;
    }

    public void setSourceAmount(BigDecimal sourceAmount) {
        this.sourceAmount = sourceAmount;
    }

    public String getSourceCurrency() {
        return sourceCurrency;
    }

    public void setSourceCurrency(String sourceCurrency) {
        this.sourceCurrency = sourceCurrency;
    }

    public String getTargetCurrency() {
        return targetCurrency;
    }

    public void setTargetCurrency(String targetCurrency) {
        this.targetCurrency = targetCurrency;
    }

    @Override
    public int hashCode() {

        return Objects.hash(sourceAmount, sourceCurrency, targetCurrency);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConvertApiRequest that = (ConvertApiRequest) o;
        return Objects.equals(sourceAmount, that.sourceAmount) &&
                Objects.equals(sourceCurrency, that.sourceCurrency) &&
                Objects.equals(targetCurrency, that.targetCurrency);
    }

    @Override
    public String toString() {
        return "ConvertApiRequest{" +
                "sourceAmount=" + sourceAmount +
                ", sourceCurrency='" + sourceCurrency + '\'' +
                ", targetCurrency='" + targetCurrency + '\'' +
                '}';
    }
}
