package com.riyaz.ecom.paymentservice.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("redirectUrl")
public class RedirectPageController {
    Logger logger = LoggerFactory.getLogger(RedirectPageController.class);
    @GetMapping
    public String redirectPage() {
        logger.info("Received Redirect Page");
        return "Thank you for purchase on Riyaz Ecom";
    }
}
