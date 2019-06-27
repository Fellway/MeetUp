package com.skrzypczyk.meetings.service.place;

import com.skrzypczyk.meetings.model.Place;
import com.skrzypczyk.meetings.repository.PlaceRepository;
import org.springframework.stereotype.Service;

@Service
public class PlaceServiceImpl implements PlaceService {
    private final PlaceRepository placeRepository;

    public PlaceServiceImpl(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    @Override
    public void save(Place place) {
        placeRepository.save(place);
    }
}
