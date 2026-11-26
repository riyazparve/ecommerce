package com.riyaz.ecom.paymentservice.controllers;

import com.riyaz.ecom.paymentservice.dtos.InitiatePaymentRequestDto;
import com.riyaz.ecom.paymentservice.services.IPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private IPaymentService paymentService;

    @PostMapping
    public ResponseEntity<String> initiatePayment(@RequestBody InitiatePaymentRequestDto requestDto){
        String paymentLink = paymentService.getPaymentLink(requestDto.getOrderId(), requestDto.getAmount(), requestDto.getCustomerPhone(), requestDto.getCustomerName(), requestDto.getCustomerEmail());
        return new ResponseEntity<>(paymentLink, HttpStatus.OK);
    }
}
