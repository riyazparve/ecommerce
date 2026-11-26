package com.riyaz.ecom.paymentservice.services;

import com.riyaz.ecom.paymentservice.paymentgateways.IPaymentGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService implements IPaymentService {

    @Autowired
    private IPaymentGateway paymentGateway;

    public String getPaymentLink(String orderId, Long amount, String customerPhone, String customerName, String customerEmail) {
        return paymentGateway.getPaymentLink(orderId, amount, customerPhone, customerName, customerEmail);
    }
}
