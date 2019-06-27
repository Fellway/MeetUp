package com.skrzypczyk.meetings.validator;

import com.skrzypczyk.meetings.model.User;
import com.skrzypczyk.meetings.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {
    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
        if (userService.findByUsername(user.getUsername()) != null) {
            errors.rejectValue("username", null, "Someone already has that username.");
        }
        if (!user.getUsername().matches("^[a-zA-Z0-9]{5,20}$")) {
            errors.rejectValue("username", null, "Invalid character in login");
        }
        if (user.getUsername().length() > 20 || user.getUsername().length() < 5) {
            errors.rejectValue("username", null, "Login must be at least 5 and up to 20 characters long.");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (!user.getPassword().matches("(?=^.{6,15}$)(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\\s).*$")) {
            errors.rejectValue("password", null, "Password must have at least 1 small-case letter, 1 capital letter and 1 digit.");
        }
        if (!user.getPasswordConfirm().equals(user.getPassword())) {
            errors.rejectValue("passwordConfirm", null, "These passwords don't match.");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty");
        if (userService.findByEmail(user.getEmail()).isPresent()) {
            errors.rejectValue("email", null, "Someone already has that email.");
        }
        if (!user.getEmail().matches("^([0-9a-zA-Z]([-.\\w]*[0-9a-zA-Z])*@([0-9a-zA-Z][-\\w]*[0-9a-zA-Z]\\.)+[a-zA-Z]{2,9})$")) {
            errors.rejectValue("email", null, "Email is invalid\"");
        }
    }
}
