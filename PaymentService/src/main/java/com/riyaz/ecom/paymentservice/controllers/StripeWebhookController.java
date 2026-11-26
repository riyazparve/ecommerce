package com.riyaz.ecom.paymentservice.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stripeWebhook")
public class StripeWebhookController {
    Logger logger = LoggerFactory.getLogger(StripeWebhookController.class);
    @PostMapping
    public void respondToEvents(@RequestBody String event){
        logger.info("webhook event received: " + event);
    }
}
