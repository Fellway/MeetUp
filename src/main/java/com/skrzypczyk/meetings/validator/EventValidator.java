package com.skrzypczyk.meetings.validator;

import com.skrzypczyk.meetings.model.Event;
import com.skrzypczyk.meetings.service.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.time.LocalDateTime;

@Component
public class EventValidator implements Validator {
    @Autowired
    CategoryService categoryService;

    @Override
    public boolean supports(Class<?> aClass) {
        return Event.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Event event = (Event) o;

        ValidationUtils.rejectIfEmpty(errors, "title", "NotEmpty", "Title cannot be empty");
        if (!event.getTitle().matches("^[\\W\\D]{5,100}$")) {
            errors.rejectValue("title", null, "Title must be at least 5 and up to 100 characters long.");
        }
        if (!event.getDescription().matches("[\\W\\D]{5,1000}$")) {
            errors.rejectValue("description", null, "Description must be at least 50 and up to 500 characters long.");
        }
        if (event.getCategory() == null) {
            errors.rejectValue("category", null, "Please choose category!");
        } else if (!categoryService.findCategoryById(event.getCategory().getId()).isPresent()) {
            errors.rejectValue("category", null, "Invalid category!");
        }

        if (event.getDate() == null) {
            errors.rejectValue("date", null, "Invalid date of event!");
        } else if (event.getDate().atTime(event.getTime()).isBefore(LocalDateTime.now())) {
            errors.rejectValue("date", null, "Invalid date of event!");
        }
        if (event.getPlaceOfMeeting().getX() == null || event.getPlaceOfMeeting().getY() == null) {
            errors.rejectValue("placeOfMeeting", null, "Please choose place of meeting!");
        }
        if (event.getSeats() < 5) {
            errors.rejectValue("seats", null, "Please enter correct number of seats");
        }
    }
}
