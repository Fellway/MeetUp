package com.skrzypczyk.meetings.service.resetpassword;

import com.skrzypczyk.meetings.model.ResetPassword;

import java.util.Optional;

public interface ResetPasswordService {
    void save(String email);

    void delete(String email);

    Optional<ResetPassword> findByToken(String token);
}
