package com.riyaz.ecom.paymentservice.paymentgateways;

public interface IPaymentGateway {
    String getPaymentLink(String orderId, Long amount, String customerPhone, String customerName, String customerEmail);
}
