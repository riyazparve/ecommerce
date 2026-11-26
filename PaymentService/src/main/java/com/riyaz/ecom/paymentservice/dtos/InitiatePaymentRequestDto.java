package com.riyaz.ecom.paymentservice.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class InitiatePaymentRequestDto {
    String orderId;
    Long amount;
    String customerPhone;
    String customerName;
    String customerEmail;
}
