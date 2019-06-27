package com.skrzypczyk.meetings.service.event;

import com.skrzypczyk.meetings.model.Category;
import com.skrzypczyk.meetings.model.Event;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EventService {
    void save(Event event);

    Page<Event> findNewestPosts();

    Event findEventByIdentity(String identity);

    List<Event> findAllEvents();

    List<Event> findAllByCategory(Category category);
}
