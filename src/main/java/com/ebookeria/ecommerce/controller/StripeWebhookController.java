package com.ebookeria.ecommerce.controller;

import com.ebookeria.ecommerce.service.webhook.StripeWebhookService;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.net.Webhook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class StripeWebhookController {
    private static final Logger log = LoggerFactory.getLogger(StripeWebhookController.class);
    @Value("${webhook.key.secret}")
    private String webhookSecretKey;
    private final StripeWebhookService stripeWebhookService;

    public StripeWebhookController(StripeWebhookService stripeWebhookService) {

        this.stripeWebhookService = stripeWebhookService;
    }


    @PostMapping(path = "/webhook")
    public ResponseEntity<String> handleWebhook(
            @RequestBody String payload,
            @RequestHeader("Stripe-Signature") String sigHeader) {
        Event event;

        try {
            event = Webhook.constructEvent(payload, sigHeader, webhookSecretKey);
        } catch (SignatureVerificationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid signature");
        }

        log.info(event.getType());

        switch (event.getType()) {
            case "payment_intent.succeeded":
                stripeWebhookService.handlePaymentIntentSucceeded(event);
                break;
            case "payment_intent.payment_canceled":
                stripeWebhookService.handlePaymentIntentCanceled(event);
                break;
        }
        return ResponseEntity.ok("Webhook processed");


    }
}
