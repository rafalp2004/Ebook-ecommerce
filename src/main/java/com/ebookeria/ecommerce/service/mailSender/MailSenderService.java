package com.ebookeria.ecommerce.service.mailSender;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;

import javax.mail.MessagingException;

import java.io.*;
import java.security.GeneralSecurityException;


public interface MailSenderService {

    Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT, GsonFactory jsonFactory)
            throws IOException;

    void sendMail(String subject, String message) throws GeneralSecurityException, IOException, MessagingException;
}
