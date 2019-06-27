package com.skrzypczyk.meetings.service.event;

import com.skrzypczyk.meetings.model.Category;
import com.skrzypczyk.meetings.model.Event;
import com.skrzypczyk.meetings.model.Place;
import com.skrzypczyk.meetings.repository.EventRepository;
import com.skrzypczyk.meetings.service.place.PlaceService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    private final PlaceService placeService;

    public EventServiceImpl(EventRepository eventRepository, PlaceService placeService) {
        this.eventRepository = eventRepository;
        this.placeService = placeService;
    }

    @Override
    public void save(Event event) {
        Place place = new Place();
        place.setName(event.getPlaceOfMeeting().getName());
        place.setX(event.getPlaceOfMeeting().getX());
        place.setY(event.getPlaceOfMeeting().getY());
        placeService.save(place);
        event.setPlaceOfMeeting(place);
        eventRepository.save(event);
    }

    @Override
    public Page<Event> findNewestPosts() {
        Pageable limit = PageRequest.of(0, 8);
        return eventRepository.findAll(limit);
    }

    @Override
    public Event findEventByIdentity(String identity) {
        return eventRepository.findByIdentity(identity);
    }

    @Override
    public List<Event> findAllEvents() {
        return eventRepository.findAll();
    }

    @Override
    public List<Event> findAllByCategory(Category category) {
        return eventRepository.findAllByCategory(category);
    }
}
