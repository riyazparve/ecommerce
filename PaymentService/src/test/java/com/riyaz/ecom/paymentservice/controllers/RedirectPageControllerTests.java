package com.riyaz.ecom.paymentservice.controllers;

import com.riyaz.ecom.paymentservice.paymentgateways.RazorpayPaymentGateway;
import com.riyaz.ecom.paymentservice.paymentgateways.StripePaymentGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RedirectPageControllerTests {

    private RazorpayPaymentGateway razorpayPaymentGateway;
    private StripePaymentGateway stripePaymentGateway;
    private RedirectPageController controller;

    @BeforeEach
    void setUp() {
        razorpayPaymentGateway = mock(RazorpayPaymentGateway.class);
        stripePaymentGateway = mock(StripePaymentGateway.class);
        controller = new RedirectPageController(razorpayPaymentGateway, stripePaymentGateway);
    }

    @Test
    void confirmsVerifiedPaidRazorpayCallback() {
        Map<String, String> parameters = Map.of(
                "razorpay_payment_id", "pay_test",
                "razorpay_payment_link_id", "plink_test",
                "razorpay_payment_link_reference_id", "order_test",
                "razorpay_payment_link_status", "paid",
                "razorpay_signature", "signature"
        );
        when(razorpayPaymentGateway.verifyPaymentLinkCallback(
                "pay_test", "plink_test", "order_test", "paid", "signature"
        )).thenReturn(true);

        var response = controller.razorpayRedirect(parameters);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Thank you for your purchase on Riyaz Ecom.", response.getBody());
    }

    @Test
    void rejectsInvalidRazorpayCallback() {
        Map<String, String> parameters = Map.of(
                "razorpay_payment_id", "pay_test",
                "razorpay_payment_link_id", "plink_test",
                "razorpay_payment_link_reference_id", "order_test",
                "razorpay_payment_link_status", "paid",
                "razorpay_signature", "invalid"
        );

        var response = controller.razorpayRedirect(parameters);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(razorpayPaymentGateway).verifyPaymentLinkCallback(
                "pay_test", "plink_test", "order_test", "paid", "invalid"
        );
    }

    @Test
    void confirmsStripeOnlyAfterRetrievingPaidSession() {
        when(stripePaymentGateway.getCheckoutSessionPaymentStatus("cs_test")).thenReturn("paid");

        var response = controller.stripeRedirect("cs_test");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Thank you for your purchase on Riyaz Ecom.", response.getBody());
        verify(stripePaymentGateway).getCheckoutSessionPaymentStatus("cs_test");
    }
}
