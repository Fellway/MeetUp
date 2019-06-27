package com.skrzypczyk.meetings.model;

import lombok.Getter;
import lombok.Setter;
import net.bytebuddy.utility.RandomString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "event")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String identity = RandomString.make(8);

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate date;

    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime time;

    private String title;

    @Column(length = 1000000)
    private String description;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Category category;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Place placeOfMeeting;

    private Integer seats;

    @ManyToOne(cascade = CascadeType.MERGE)
    private User organizer;

    @ManyToMany(cascade = CascadeType.MERGE)
    private Set<User> participants = new HashSet<>();

    public Event() {
    }

    public Integer getNumberOfFreeSeats() {
        return getSeats() - getParticipants().size();
    }
}
