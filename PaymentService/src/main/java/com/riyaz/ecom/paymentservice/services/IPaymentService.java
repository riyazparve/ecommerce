package com.riyaz.ecom.paymentservice.services;

public interface IPaymentService {
    String getPaymentLink(String orderId, Long amount, String customerPhone, String customerName, String customerEmail);
}
