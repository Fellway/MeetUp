package com.skrzypczyk.meetings.service.email;

public interface EmailService {
    void sendEmailWithResetToken(String to, String token);

    void sendActivationEmail(String to, String token);
}
