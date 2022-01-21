package com.ynoyaner.openpayd.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Conversion {

    @Id
    @GeneratedValue
    private Long id;
    private BigDecimal sourceAmount;
    private String sourceCurrency;
    private String targetCurrency;
    private BigDecimal amountInTargetCurrency;
    private LocalDate conversionDate;

    public Conversion() {
    }

    public Conversion(BigDecimal sourceAmount, String sourceCurrency, String targetCurrency, BigDecimal amountInTargetCurrency,LocalDate date) {
        this.sourceAmount = sourceAmount;
        this.sourceCurrency = sourceCurrency;
        this.targetCurrency = targetCurrency;
        this.amountInTargetCurrency = amountInTargetCurrency;
        this.conversionDate =date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public BigDecimal getAmountInTargetCurrency() {
        return amountInTargetCurrency;
    }

    public void setAmountInTargetCurrency(BigDecimal amountInTargetCurrency) {
        this.amountInTargetCurrency = amountInTargetCurrency;
    }

    public LocalDate getConversionDate() {
        return conversionDate;
    }

    public void setConversionDate(LocalDate conversionDate) {
        this.conversionDate = conversionDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Conversion that = (Conversion) o;
        return id.equals(that.id) && sourceAmount.equals(that.sourceAmount) && sourceCurrency.equals(that.sourceCurrency) && targetCurrency.equals(that.targetCurrency) && amountInTargetCurrency.equals(that.amountInTargetCurrency) && conversionDate.equals(that.conversionDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sourceAmount, sourceCurrency, targetCurrency, amountInTargetCurrency, conversionDate);
    }

    @Override
    public String toString() {
        return "Conversion{" +
                "sourceAmount=" + sourceAmount +
                ", sourceCurrency='" + sourceCurrency + '\'' +
                ", targetCurrency='" + targetCurrency + '\'' +
                ", amountInTargetCurrency=" + amountInTargetCurrency +
                ", conversionDate=" + conversionDate +
                '}';
    }
}
