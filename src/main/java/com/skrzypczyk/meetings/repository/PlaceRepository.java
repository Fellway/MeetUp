package com.skrzypczyk.meetings.repository;

import com.skrzypczyk.meetings.model.Place;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Place, Long> {
}
