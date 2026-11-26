package com.riyaz.ecom.paymentservice.paymentgateways;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.HexFormat;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RazorpayPaymentGatewayTests {

    private static final String SECRET = "test-secret";
    private static final String PAYMENT_ID = "pay_test";
    private static final String PAYMENT_LINK_ID = "plink_test";
    private static final String REFERENCE_ID = "order_test";
    private static final String STATUS = "paid";

    private RazorpayPaymentGateway gateway;

    @BeforeEach
    void setUp() {
        gateway = new RazorpayPaymentGateway();
        ReflectionTestUtils.setField(gateway, "razorpaySecret", SECRET);
    }

    @Test
    void verifiesValidPaymentLinkCallbackSignature() throws Exception {
        assertTrue(gateway.verifyPaymentLinkCallback(
                PAYMENT_ID, PAYMENT_LINK_ID, REFERENCE_ID, STATUS, signature()
        ));
    }

    @Test
    void rejectsTamperedPaymentLinkCallback() throws Exception {
        assertFalse(gateway.verifyPaymentLinkCallback(
                PAYMENT_ID, PAYMENT_LINK_ID, REFERENCE_ID, "created", signature()
        ));
    }

    private String signature() throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(SECRET.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
        String message = PAYMENT_LINK_ID + "|" + REFERENCE_ID + "|" + STATUS + "|" + PAYMENT_ID;
        return HexFormat.of().formatHex(mac.doFinal(message.getBytes(StandardCharsets.UTF_8)));
    }
}
