package com.ebookeria.ecommerce.controller;

import com.ebookeria.ecommerce.entity.Transaction;
import com.ebookeria.ecommerce.enums.TransactionStatus;
import com.ebookeria.ecommerce.exception.ResourceNotFoundException;
import com.ebookeria.ecommerce.repository.TransactionRepository;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.PaymentIntent;
import com.stripe.model.StripeObject;
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
    private final TransactionRepository transactionRepository;

    public StripeWebhookController(TransactionRepository transactionRepository) {

        this.transactionRepository = transactionRepository;
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

                handlePaymentIntentSucceeded(event);
                break;
            case "payment_intent.payment_failed":
                log.info("failed");
                break;

        }

        return ResponseEntity.ok("Webhook processed");


    }

    private void handlePaymentIntentSucceeded(Event event) {
        EventDataObjectDeserializer deserializer = event.getDataObjectDeserializer();
        StripeObject stripeObject = deserializer.getObject().orElse(null);
        if (stripeObject instanceof PaymentIntent paymentIntent) {
            String transactionIdStr = paymentIntent.getMetadata().get("transaction_id");
            if (transactionIdStr != null) {
                int transactionId = Integer.parseInt(transactionIdStr);
                log.info("✅ Payment succeeded for Transaction ID: {}", transactionId);
                Transaction transaction = transactionRepository.findById(transactionId).orElseThrow(() -> new ResourceNotFoundException("Transaction with id: " + transactionId + " not found"));
                transaction.setStatus(TransactionStatus.COMPLETED);
                transactionRepository.save(transaction);
            } else {
                log.warn("⚠️ Transaction ID not found in PaymentIntent metadata.");

            }
        }


    }
}
