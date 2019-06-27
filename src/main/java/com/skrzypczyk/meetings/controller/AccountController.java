package com.skrzypczyk.meetings.controller;

import com.skrzypczyk.meetings.model.ResetPassword;
import com.skrzypczyk.meetings.model.User;
import com.skrzypczyk.meetings.service.email.EmailService;
import com.skrzypczyk.meetings.service.resetpassword.ResetPasswordService;
import com.skrzypczyk.meetings.service.user.UserService;
import com.skrzypczyk.meetings.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Optional;

@Controller
public class AccountController {

    private final UserService userService;
    private final UserValidator userValidator;
    private final EmailService emailService;
    private final ResetPasswordService resetPasswordService;

    @Autowired
    public AccountController(UserService userService, UserValidator userValidator, EmailService emailService, ResetPasswordService resetPasswordService) {
        this.userService = userService;
        this.userValidator = userValidator;
        this.emailService = emailService;
        this.resetPasswordService = resetPasswordService;
    }

    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error, @RequestParam(value = "logout", required = false) String logout, Model model) {
        String errorMsg = "";
        if (error != null) {
            errorMsg = "Username or Password is incorrect!";
        }
        if (logout != null) {
            errorMsg = "You have been successfully logged out!";
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            return "redirect:/home";
        }
        model.addAttribute("msg", errorMsg);
        return "login";
    }

    @GetMapping(value = "/logout")
    public void logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new User());
        return "registration";
    }

    @Transactional
    @PostMapping("/registration")
    public String registration(@Valid @ModelAttribute("userForm") User userForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        userValidator.validate(userForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        User user = userService.save(userForm);
        new Thread(() -> emailService.sendActivationEmail(userForm.getEmail(), user.getActivationToken())).start();
        redirectAttributes.addFlashAttribute("info", "Activation email has been send to your email. Please confirm your account.");
        return "redirect:/home";
    }

    @GetMapping("/activation")
    public String userActivation(@RequestParam(value = "token") String token, RedirectAttributes redirectAttributes) {
        Optional<User> optionalUser = userService.activatingUser(token);
        if (optionalUser.isPresent()) {
            redirectAttributes.addFlashAttribute("info", "Your account has been successfully activated");
        } else {
            redirectAttributes.addFlashAttribute("info", "Invalid activation link");
        }
        return "redirect:/home";
    }

    @GetMapping("/reset-password")
    public String resetPassword() {
        return "reset";
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam(value = "email") String email,
                                RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("info", "We send you reset password link on your email.");
        resetPasswordService.save(email);
        return "redirect:/login";
    }

    @GetMapping("/change-password")
    public String changePasswordView(@RequestParam(value = "token") String token, RedirectAttributes redirectAttributes) {
        Optional<ResetPassword> optionalResetPassword = resetPasswordService.findByToken(token);
        if (!optionalResetPassword.isPresent()) {
            redirectAttributes.addFlashAttribute("info", "Your link is incorrect or have expired!");
            return "redirect:/login";
        }
        return "change-password";
    }

    @PostMapping("/change-password")
    public String changePassword(@RequestParam(value = "token") String token,
                                 @RequestParam(value = "password") String password,
                                 @RequestParam(value = "confirm-password") String confirmPassword,
                                 RedirectAttributes redirectAttributes) {

        Optional<ResetPassword> optionalResetPassword = resetPasswordService.findByToken(token);
        if (!password.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("info", "Passwords do not match!");
            return "redirect:/change-password?token=" + token;
        }
        if (optionalResetPassword.isPresent()) {
            User user = optionalResetPassword.get().getUser();
            userService.changePassword(user, password);
            redirectAttributes.addFlashAttribute("info", "Password successfully changed!");
            return "redirect:/login";
        }
        redirectAttributes.addFlashAttribute("Your link is incorrect or have expired!");
        return "redirect:/home";
    }
}
