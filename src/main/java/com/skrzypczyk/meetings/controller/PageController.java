package com.skrzypczyk.meetings.controller;

import com.skrzypczyk.meetings.model.Category;
import com.skrzypczyk.meetings.model.Event;
import com.skrzypczyk.meetings.security.SecurityUtils;
import com.skrzypczyk.meetings.service.category.CategoryService;
import com.skrzypczyk.meetings.service.event.EventService;
import com.skrzypczyk.meetings.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Controller
public class PageController {

    private final EventService eventService;

    private final CategoryService categoryService;

    private final UserService userService;

    @Autowired
    public PageController(EventService eventService, CategoryService categoryService, UserService userService) {
        this.eventService = eventService;
        this.categoryService = categoryService;
        this.userService = userService;
    }

    @GetMapping(value = {"/home", "/"})
    public String homePage(Model model) {
        List<Event> eventList = eventService.findNewestPosts().getContent();
        List<Category> categoryList = categoryService.findAll();
        model.addAttribute("events", eventList);
        model.addAttribute("categories", categoryList);
        return "index";
    }

    @GetMapping(value = "/events")
    public String allEvents(Model model, HttpServletRequest request) {
        String username = SecurityUtils.getLoggedUserNameFromRequest(request);
        model.addAttribute("subscribed", userService.findByUsername(username).getEvents());
        model.addAttribute("events", eventService.findAllEvents());
        return "all-events";
    }

    @GetMapping(value = "/categories/{categoryName}")
    public String allEventsByCategory(Model model, @PathVariable String categoryName, HttpServletRequest request) {
        Optional<Category> optionalCategory = categoryService.findByName(categoryName.replaceAll("-", " "));
        String username = SecurityUtils.getLoggedUserNameFromRequest(request);
        if (optionalCategory.isPresent()) {
            model.addAttribute("events", eventService.findAllByCategory(optionalCategory.get()));
        } else {
            model.addAttribute("events", eventService.findAllEvents());
        }
        model.addAttribute("subscribed", userService.findByUsername(username).getEvents());
        return "all-events";
    }
}
