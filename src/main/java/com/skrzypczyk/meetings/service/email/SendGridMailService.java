package com.skrzypczyk.meetings.service.email;


import com.sendgrid.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
public class SendGridMailService implements EmailService {

    private SendGrid sendGrid;
    private static Logger logger = LoggerFactory.getLogger(LoggerFactory.class);
    @Autowired
    public SendGridMailService(SendGrid sendGrid) {
        this.sendGrid = sendGrid;
    }

    @Async
    @Override
    public void sendActivationEmail(String toSome, String token) {
        Email from = new Email("sender@meetings.com");
        String subject = "Please activate your account ";
        Email to = new Email(toSome);
        Content content = new Content("text/plain", "Click here to activate your account: http://app-meetings.herokuapp.com/activation?token=" + token);
        Mail mail = new Mail(from, subject, to, content);

        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            sendGrid.api(request);
        } catch (IOException ex) {
            logger.error("Cannot send email!");
        }
    }

    @Async
    @Override
    public void sendEmailWithResetToken(String toSome, String token) {
        Email from = new Email("sender@meetings.com");
        String subject = "Please activate your account ";
        Email to = new Email(toSome);
        Content content = new Content("text/plain", "Click here to reset your password: http://app-meetings.herokuapp.com/change-password?token=" + token);
        Mail mail = new Mail(from, subject, to, content);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            sendGrid.api(request);
        } catch (IOException ex) {
            logger.error("Cannot send email!");
        }
    }
}
