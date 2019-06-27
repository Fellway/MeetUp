package com.skrzypczyk.meetings.repository;

import com.skrzypczyk.meetings.model.ResetPassword;
import com.skrzypczyk.meetings.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResetPasswordRepository extends JpaRepository<ResetPassword, Long> {
    Optional<ResetPassword> findAllByUser(User user);

    Optional<ResetPassword> findAllByToken(String token);
}
