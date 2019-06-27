package com.skrzypczyk.meetings.controller;

import com.skrzypczyk.meetings.config.YAMLConfig;
import com.skrzypczyk.meetings.model.Event;
import com.skrzypczyk.meetings.service.category.CategoryService;
import com.skrzypczyk.meetings.service.event.EventService;
import com.skrzypczyk.meetings.service.image.ImageService;
import com.skrzypczyk.meetings.service.user.UserService;
import com.skrzypczyk.meetings.validator.EventValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class EventController {

    private final EventService eventService;
    private final UserService userService;
    private final EventValidator eventValidator;
    private final CategoryService categoryService;
    private final ImageService imageService;
    private final YAMLConfig yamlConfig;

    @Autowired
    public EventController(EventService eventService, UserService userService, EventValidator eventValidator, CategoryService categoryService, ImageService imageService, YAMLConfig yamlConfig) {
        this.eventService = eventService;
        this.userService = userService;
        this.eventValidator = eventValidator;
        this.categoryService = categoryService;
        this.imageService = imageService;
        this.yamlConfig = yamlConfig;
    }

    @GetMapping("/events/{identity}")
    public String singleEventView(@PathVariable String identity, Model model) {
        Event event = eventService.findEventByIdentity(identity);
        model.addAttribute("event", event);
        model.addAttribute("mapApiKey", yamlConfig.getApiKey());
        model.addAttribute("eventPhoto", imageService.getEventImage(event.getIdentity()));
        return "event";
    }

    @GetMapping("/new-event")
    public String newEvent(Model model) {
        model.addAttribute("mapApiKey", yamlConfig.getApiKey());
        model.addAttribute("categories", categoryService.findAll());
        if (model.asMap().get("eventForm") == null) {
            model.addAttribute("eventForm", new Event());
        }
        return "add-event";
    }

    @PostMapping("/new-event")
    public String createNewPost(@Valid @ModelAttribute("eventForm") final Event eventForm, @RequestParam("file") MultipartFile file,
                                final BindingResult bindingResult,
                                final RedirectAttributes redirectAttributes,
                                Principal principal) {
        eventValidator.validate(eventForm, bindingResult);
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.eventForm", bindingResult);
            redirectAttributes.addFlashAttribute("eventForm", eventForm);
            return "redirect:/new-event";
        }
        eventForm.setOrganizer(userService.findByUsername(principal.getName()));
        eventService.save(eventForm);
        imageService.uploadEventImage(file, eventForm.getIdentity());
        return "redirect:/events/" + eventForm.getIdentity();
    }
}
