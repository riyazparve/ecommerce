package com.riyaz.ecom.paymentservice.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/stripeWebhook")
public class StripeWebhookController {
    @PostMapping
    public void respondToEvents(@RequestBody String event){
        log.info("webhook event received: " + event);
    }

//    Sample webhook
//    webhook event received: {
//        "id": "evt_1UftdtRpwdMns1gzQIkHxWmL",
//                "object": "event",
//                "api_version": "2026-11-18.endive",
//                "created": 1795664801,
//                "data": {
//            "object": {
//                "id": "plink_1UftdtRpwdMns1gzkJcA0wtW",
//                        "object": "payment_link",
//                        "active": true,
//                        "after_completion": {
//                    "redirect": {
//                        "url": "https://unworried-dancing-proponent.ngrok-free.dev/redirectUrl/stripe?session_id={CHECKOUT_SESSION_ID}"
//                    },
//                    "type": "redirect"
//                },
//                "allow_promotion_codes": false,
//                        "application": null,
//                        "application_fee_amount": null,
//                        "application_fee_percent": null,
//                        "automatic_tax": {
//                    "enabled": false,
//                            "liability": null
//                },
//                "billing_address_collection": "auto",
//                        "consent_collection": null,
//                        "currency": "inr",
//                        "custom_fields": [],
//                "custom_text": {
//                    "after_submit": null,
//                            "shipping_address": null,
//                            "submit": null,
//                            "terms_of_service_acceptance": null
//                },
//                "customer_creation": "if_required",
//                        "inactive_message": null,
//                        "invoice_creation": {
//                    "enabled": false,
//                            "invoice_data": {
//                        "account_tax_ids": null,
//                                "custom_fields": null,
//                                "description": null,
//                                "footer": null,
//                                "issuer": null,
//                                "metadata": {},
//                        "rendering_options": null
//                    }
//                },
//                "livemode": false,
//                        "managed_payments": {
//                    "enabled": false
//                },
//                "metadata": {},
//                "on_behalf_of": null,
//                        "payment_intent_data": null,
//                        "payment_method_collection": "if_required",
//                        "payment_method_options": null,
//                        "payment_method_types": null,
//                        "phone_number_collection": {
//                    "enabled": false
//                },
//                "restrictions": null,
//                        "shipping_address_collection": null,
//                        "shipping_options": [],
//                "submit_type": "auto",
//                        "subscription_data": null,
//                        "tax_id_collection": {
//                    "enabled": false,
//                            "required": "never"
//                },
//                "transfer_data": null,
//                        "url": "https://buy.stripe.com/test_28E14pgI0esE9zme2HejK02"
//            }
//        },
//        "livemode": false,
//                "pending_webhooks": 1,
//                "request": {
//            "id": "req_gBKoyBCbAHpaK1",
//                    "idempotency_key": "efbb2627-5a8a-4838-9268-4699f272ab32"
//        },
//        "type": "payment_link.created"
//    }
//2026-11-26T09:17:33.427+05:30  INFO 20740 --- [PaymentService] [nio-8080-exec-3] c.r.e.p.c.StripeWebhookController        : webhook event received: {
//        "id": "evt_1UfteiRpwdMns1gzF9fFfCRJ",
//                "object": "event",
//                "api_version": "2026-11-18.endive",
//                "created": 1795664852,
//                "data": {
//            "object": {
//                "id": "cs_test_a0jojWhn3MP9g2ue3HeAsBZ4ei9lhoU1S7pmfhfA0D59cqpacYrkZA9YFk",
//                        "object": "checkout.session",
//                        "adaptive_pricing": {
//                    "enabled": true
//                },
//                "after_expiration": null,
//                        "allow_promotion_codes": false,
//                        "allowed_payment_method_types": null,
//                        "amount_subtotal": 15000,
//                        "amount_total": 15000,
//                        "automatic_tax": {
//                    "address_collection_precision": null,
//                            "enabled": false,
//                            "liability": null,
//                            "provider": null,
//                            "status": null
//                },
//                "billing_address_collection": "auto",
//                        "cancel_url": "https://stripe.com",
//                        "client_reference_id": null,
//                        "client_secret": null,
//                        "collected_information": null,
//                        "consent": null,
//                        "consent_collection": null,
//                        "created": 1795664804,
//                        "currency": "inr",
//                        "currency_conversion": null,
//                        "custom_fields": [],
//                "custom_text": {
//                    "after_submit": null,
//                            "shipping_address": null,
//                            "submit": null,
//                            "terms_of_service_acceptance": null
//                },
//                "customer": null,
//                        "customer_account": null,
//                        "customer_creation": "if_required",
//                        "customer_details": {
//                    "address": {
//                        "city": null,
//                                "country": "IN",
//                                "line1": null,
//                                "line2": null,
//                                "postal_code": null,
//                                "state": null
//                    },
//                    "business_name": null,
//                            "email": "sabaparve95@gmail.com",
//                            "individual_name": null,
//                            "name": "Saba Parve",
//                            "phone": null,
//                            "tax_exempt": "none",
//                            "tax_ids": []
//                },
//                "customer_email": null,
//                        "discounts": [],
//                "expires_at": 1795751204,
//                        "integration_identifier": null,
//                        "invoice": null,
//                        "invoice_creation": {
//                    "enabled": false,
//                            "invoice_data": {
//                        "account_tax_ids": null,
//                                "custom_fields": null,
//                                "description": null,
//                                "footer": null,
//                                "issuer": null,
//                                "metadata": {},
//                        "rendering_options": null
//                    }
//                },
//                "livemode": false,
//                        "locale": "auto",
//                        "managed_payments": {
//                    "enabled": false
//                },
//                "marketing_attributions": null,
//                        "metadata": {},
//                "mode": "payment",
//                        "origin_context": null,
//                        "payment_intent": "pi_3UftegRpwdMns1gz0VXsTm2Y",
//                        "payment_link": "plink_1UftdtRpwdMns1gzkJcA0wtW",
//                        "payment_method_collection": "if_required",
//                        "payment_method_configuration_details": {
//                    "id": "pmc_1UevW2RpwdMns1gzrPRERO0U",
//                            "parent": null
//                },
//                "payment_method_options": {
//                    "card": {
//                        "request_three_d_secure": "automatic"
//                    }
//                },
//                "payment_method_types": [
//                "card",
//                        "link"
//      ],
//                "payment_status": "paid",
//                        "permissions": null,
//                        "phone_number_collection": {
//                    "enabled": false
//                },
//                "recovered_from": null,
//                        "saved_payment_method_options": null,
//                        "setup_intent": null,
//                        "shipping_address_collection": null,
//                        "shipping_cost": null,
//                        "shipping_options": [],
//                "status": "complete",
//                        "submit_type": "auto",
//                        "subscription": null,
//                        "success_url": "https://unworried-dancing-proponent.ngrok-free.dev/redirectUrl/stripe?session_id={CHECKOUT_SESSION_ID}",
//                        "total_details": {
//                    "amount_discount": 0,
//                            "amount_shipping": 0,
//                            "amount_tax": 0
//                },
//                "ui_mode": "hosted_page",
//                        "url": null,
//                        "wallet_options": null
//            }
//        },
//        "livemode": false,
//                "pending_webhooks": 1,
//                "request": {
//            "id": null,
//                    "idempotency_key": null
//        },
//        "type": "checkout.session.completed"
//    }
}
