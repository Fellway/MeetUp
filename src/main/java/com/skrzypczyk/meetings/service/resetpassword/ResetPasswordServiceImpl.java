package com.skrzypczyk.meetings.service.resetpassword;

import com.skrzypczyk.meetings.model.ResetPassword;
import com.skrzypczyk.meetings.model.User;
import com.skrzypczyk.meetings.repository.ResetPasswordRepository;
import com.skrzypczyk.meetings.repository.UserRepository;
import com.skrzypczyk.meetings.service.email.EmailService;
import com.skrzypczyk.meetings.utils.TokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ResetPasswordServiceImpl implements ResetPasswordService {

    private final EmailService emailService;
    private final ResetPasswordRepository resetPasswordRepository;
    private final UserRepository userRepository;

    @Autowired
    public ResetPasswordServiceImpl(EmailService emailService, ResetPasswordRepository resetPasswordRepository, UserRepository userRepository) {
        this.emailService = emailService;
        this.resetPasswordRepository = resetPasswordRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void save(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        delete(email);
        if (userOptional.isPresent()) {
            String generatedToken = TokenGenerator.generateToken();
            LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(10);
            emailService.sendEmailWithResetToken(email, generatedToken);
            ResetPassword resetPassword = new ResetPassword();
            resetPassword.setToken(generatedToken);
            resetPassword.setUser(userOptional.get());
            resetPassword.setTokenExpiryDate(expirationTime);
            resetPasswordRepository.save(resetPassword);
        }
    }

    @Override
    public void delete(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            Optional<ResetPassword> optionalResetPassword = resetPasswordRepository.findAllByUser(optionalUser.get());
            optionalResetPassword.ifPresent(resetPasswordRepository::delete);
        }
    }

    @Override
    public Optional<ResetPassword> findByToken(String token) {
        Optional<ResetPassword> optionalResetPassword = resetPasswordRepository.findAllByToken(token);
        if (optionalResetPassword.isPresent()) {
            if (optionalResetPassword.get().getTokenExpiryDate().isBefore(LocalDateTime.now())) {
                return Optional.empty();
            }
            return optionalResetPassword;
        }
        return Optional.empty();
    }
}
