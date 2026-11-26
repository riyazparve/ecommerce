package com.riyaz.ecom.paymentservice.controllers;

import com.riyaz.ecom.paymentservice.paymentgateways.RazorpayPaymentGateway;
import com.riyaz.ecom.paymentservice.paymentgateways.StripePaymentGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("redirectUrl")
public class RedirectPageController {

    public static final String THANK_YOU_MESSAGE = "Thank you for your purchase on Riyaz Ecommerce.";
    private final RazorpayPaymentGateway razorpayPaymentGateway;
    private final StripePaymentGateway stripePaymentGateway;

    public RedirectPageController(
            RazorpayPaymentGateway razorpayPaymentGateway,
            StripePaymentGateway stripePaymentGateway
    ) {
        this.razorpayPaymentGateway = razorpayPaymentGateway;
        this.stripePaymentGateway = stripePaymentGateway;
    }

    @GetMapping("/razorpay")
    public ResponseEntity<String> razorpayRedirect(@RequestParam Map<String, String> parameters) {
        String paymentId = parameters.get("razorpay_payment_id");
        String paymentLinkId = parameters.get("razorpay_payment_link_id");
        String referenceId = parameters.get("razorpay_payment_link_reference_id");
        String status = parameters.get("razorpay_payment_link_status");
        String signature = parameters.get("razorpay_signature");

        if (isBlank(paymentId) || isBlank(paymentLinkId) || isBlank(referenceId)
                || isBlank(status) || isBlank(signature)) {
            return ResponseEntity.badRequest().body("Incomplete Razorpay payment callback.");
        }

        if (!razorpayPaymentGateway.verifyPaymentLinkCallback(
                paymentId, paymentLinkId, referenceId, status, signature)) {
            log.warn("Razorpay payment callback signature verification failed");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Payment verification failed.");
        }

        if (!"paid".equalsIgnoreCase(status)) {
            return ResponseEntity.ok("Payment was not completed. Status: " + status);
        }
        log.info("Razorpay payment callback verified");
        return ResponseEntity.ok(THANK_YOU_MESSAGE);
    }

    @GetMapping("/stripe")
    public ResponseEntity<String> stripeRedirect(@RequestParam(name = "session_id") String stripeSessionId) {
        try {
            String paymentStatus = stripePaymentGateway.getCheckoutSessionPaymentStatus(stripeSessionId);
            if (!"paid".equalsIgnoreCase(paymentStatus)) {
                return ResponseEntity.ok("Payment was not completed. Status: " + paymentStatus);
            }
            log.info("Stripe checkout session payment verified");
            return ResponseEntity.ok(THANK_YOU_MESSAGE);
        } catch (IllegalStateException e) {
            log.error("Unable to verify Stripe checkout session", e);
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body("Unable to verify payment at this time.");
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
