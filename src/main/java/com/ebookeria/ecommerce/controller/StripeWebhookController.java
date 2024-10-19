package com.ebookeria.ecommerce.controller;

import com.ebookeria.ecommerce.entity.Transaction;
import com.ebookeria.ecommerce.enums.TransactionStatus;
import com.ebookeria.ecommerce.exception.ResourceNotFoundException;
import com.ebookeria.ecommerce.repository.TransactionRepository;
import com.ebookeria.ecommerce.service.transaction.TransactionService;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;
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
            @RequestHeader("Stripe-Signature") String sigHeader)
    {
        Event event;
        log.info("Received payload: {}", payload);


        try {
            event = Webhook.constructEvent(payload, sigHeader, webhookSecretKey);
        } catch (SignatureVerificationException e) {
            log.error("⚠️  Weryfikacja sygnatury nie powiodła się.", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid signature");
        }

        log.info(event.getType());

        switch (event.getType()) {
            case "payment_intent.succeeded":
                log.info("AAAAAAAAAAAAAAAAAAAAAAAAA");

                handlePaymentIntentSucceeded(event);
                break;
            case "payment_intent.payment_failed":
                log.info("failed");
                break;

        }

        return ResponseEntity.ok("Webhook processed");


    }
    private void handlePaymentIntentSucceeded(Event event) {
        //TODO Resolve this problem. Transaction status has to change to PAID or use Success url and do it without webhooks.
        EventDataObjectDeserializer deserializer = event.getDataObjectDeserializer();
        log.info("bbbbb");
        Session session = (Session) deserializer.getObject().orElse(null);
        log.info("sdasda");

        if (session != null) {
            int transactionId = Integer.parseInt(session.getClientReferenceId());
            log.info("✅ Payment succeeded for Transaction ID: {}", session.getClientReferenceId());
            Transaction transaction = transactionRepository.findById(transactionId).orElseThrow(()->new ResourceNotFoundException("Transaction with id: " + transactionId + " not found"));
            transaction.setStatus(TransactionStatus.COMPLETED);
            transactionRepository.save(transaction);

        }
    }
}





// else {
//            int transactionId = Integer.parseInt(session.getClientReferenceId());
//            log.info("✅ Payment failed for Transaction ID: {}", session.getClientReferenceId());
//
//            Transaction transaction = transactionRepository.findById(transactionId).orElseThrow(()->new ResourceNotFoundException("Transaction with id: " + transactionId + " not found"))
//            transaction.setStatus(TransactionStatus.CANCELLED);
//            transactionRepository.save(transaction);
//        }