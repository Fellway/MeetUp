package com.skrzypczyk.meetings.service.user;

import com.skrzypczyk.meetings.model.User;

import java.util.Optional;

public interface UserService {
    User save(User user);

    Optional<User> findUserById(Long id);

    User findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> activatingUser(String token);

    void changePassword(User user, String password);
}
