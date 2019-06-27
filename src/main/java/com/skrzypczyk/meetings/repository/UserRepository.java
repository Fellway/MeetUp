package com.skrzypczyk.meetings.repository;


import com.skrzypczyk.meetings.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findByActivationToken(String activationToken);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE User u set u.enabled = true where u.activationToken = :activationToken")
    void enableUser(@Param("activationToken") String activationToken);
}
