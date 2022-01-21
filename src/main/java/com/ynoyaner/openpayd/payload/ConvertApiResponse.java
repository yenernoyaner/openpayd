package com.ynoyaner.openpayd.payload;

import java.io.Serializable;
import java.math.BigDecimal;

public class ConvertApiResponse implements Serializable {

   private BigDecimal amountInTargetCurrency;
   private Long transactionId;

    public BigDecimal getAmountInTargetCurrency() {
        return amountInTargetCurrency;
    }

    public void setAmountInTargetCurrency(BigDecimal amountInTargetCurrency) {
        this.amountInTargetCurrency = amountInTargetCurrency;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }
}
