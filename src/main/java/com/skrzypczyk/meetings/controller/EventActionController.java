package com.skrzypczyk.meetings.controller;

import com.skrzypczyk.meetings.model.Event;
import com.skrzypczyk.meetings.model.User;
import com.skrzypczyk.meetings.security.SecurityUtils;
import com.skrzypczyk.meetings.service.event.EventService;
import com.skrzypczyk.meetings.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
public class EventActionController {

    private final UserService userService;

    private final EventService eventService;

    @Autowired
    public EventActionController(UserService userService, EventService eventService) {
        this.userService = userService;
        this.eventService = eventService;
    }

    @PostMapping("/event-registration/{identity}")
    public String signUpToEvent(HttpServletRequest request, @PathVariable String identity,
                                final RedirectAttributes redirectAttributes,
                                @RequestParam(value = "signOff", required = false) String signOff,
                                @RequestParam(value = "signUp", required = false) String signUp) {
        User user = userService.findByUsername(SecurityUtils.getLoggedUserNameFromRequest(request));
        Event event = eventService.findEventByIdentity(identity);
        if (signUp != null) {
            if (event.getParticipants().contains(user)) {
                redirectAttributes.addFlashAttribute("info", "You are already registered!");
            } else {
                event.getParticipants().add(user);
                redirectAttributes.addFlashAttribute("info", "You have been successfully registered to this event!");
                eventService.save(event);
            }
        } else if (signOff != null) {
            if (event.getParticipants().contains(user)) {
                event.getParticipants().remove(user);
                eventService.save(event);
                redirectAttributes.addFlashAttribute("info", "You have been successfully unsubscribed event!");
            } else {
                redirectAttributes.addFlashAttribute("info", "You are not registered to this event!");
            }
        }
        return "redirect:/events/" + identity;
    }
}
