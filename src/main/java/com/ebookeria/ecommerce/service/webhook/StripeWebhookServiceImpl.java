package com.ebookeria.ecommerce.service.webhook;

import com.ebookeria.ecommerce.entity.Transaction;
import com.ebookeria.ecommerce.enums.TransactionStatus;
import com.ebookeria.ecommerce.exception.ResourceNotFoundException;
import com.ebookeria.ecommerce.repository.TransactionRepository;
import com.ebookeria.ecommerce.service.emailContentBuilder.EmailContentBuilder;
import com.ebookeria.ecommerce.service.mailSender.MailSenderService;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.PaymentIntent;
import com.stripe.model.StripeObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.GeneralSecurityException;

@Service
public class StripeWebhookServiceImpl implements StripeWebhookService {

    private static final Logger log = LoggerFactory.getLogger(StripeWebhookServiceImpl.class);
    private final TransactionRepository transactionRepository;
    private final MailSenderService mailSenderService;
    private final EmailContentBuilder emailContentBuilder;

    public StripeWebhookServiceImpl(TransactionRepository transactionRepository, MailSenderService mailSenderService, EmailContentBuilder emailContentBuilder) {
        this.transactionRepository = transactionRepository;
        this.mailSenderService = mailSenderService;
        this.emailContentBuilder = emailContentBuilder;
    }


    @Override
    public void handlePaymentIntentSucceeded(Event event) {
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
                String subject = "New transaction";
                String message = emailContentBuilder.buildTransactionEmail(transaction);
                try {
                    mailSenderService.sendMail(subject, message);
                } catch (GeneralSecurityException | IOException | MessagingException e) {
                    throw new RuntimeException(e);
                }
            } else {
                log.warn("⚠️ Transaction ID not found in PaymentIntent metadata.");
            }
        }
    }

    @Override
    public void handlePaymentIntentCanceled(Event event) {
        EventDataObjectDeserializer deserializer = event.getDataObjectDeserializer();
        StripeObject stripeObject = deserializer.getObject().orElse(null);
        if (stripeObject instanceof PaymentIntent paymentIntent) {
            String transactionIdStr = paymentIntent.getMetadata().get("transaction_id");
            if (transactionIdStr != null) {
                int transactionId = Integer.parseInt(transactionIdStr);
                log.info("Payment failed for Transaction ID: {}", transactionId);
                Transaction transaction = transactionRepository.findById(transactionId).orElseThrow(() -> new ResourceNotFoundException("Transaction with id: " + transactionId + " not found"));
                transaction.setStatus(TransactionStatus.CANCELLED);
                transactionRepository.save(transaction);
                try {
                    mailSenderService.sendMail("Ebook", "message with url to download ebook");
                    log.info("Mail sended");
                } catch (GeneralSecurityException | IOException | MessagingException e) {
                    throw new RuntimeException(e);
                }
            } else {
                log.warn("⚠️ Transaction ID not found in PaymentIntent metadata.");

            }
        }
    }
}
