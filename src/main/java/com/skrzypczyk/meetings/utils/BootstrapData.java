package com.skrzypczyk.meetings.utils;

import com.skrzypczyk.meetings.model.*;
import com.skrzypczyk.meetings.service.category.CategoryServiceImpl;
import com.skrzypczyk.meetings.service.event.EventServiceImpl;
import com.skrzypczyk.meetings.service.place.PlaceServiceImpl;
import com.skrzypczyk.meetings.service.role.RoleServiceImpl;
import com.skrzypczyk.meetings.service.user.UserServiceImpl;
import net.bytebuddy.utility.RandomString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

@Service
public class BootstrapData implements InitializingBean {
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private RoleServiceImpl roleService;

    @Autowired
    private CategoryServiceImpl categoryService;

    @Autowired
    private EventServiceImpl eventService;

    @Autowired
    private PlaceServiceImpl placeService;

    private final Logger LOG = LoggerFactory.getLogger(BootstrapData.class);

    @Value("#{'${data.bootstrap.categories}'.split(',')}")
    private List<String> categories;

    public void initData() {
        Role role = new Role("ADMIN");
        Set<Role> roles = new HashSet<>();
        roles.add(role);

        User administrator = new User("Administrator", "Admin1", "mateusz.skrzypczyk1@hotmail.com", "mateusz.skrzypczyk1@hotmail.com", true, true, true, true, roles);
        roleService.save(role);
        userService.save(administrator);

        categories.sort(Comparator.naturalOrder());
        for (String category : categories) {
            Category categoryObject = new Category();
            categoryObject.setName(category);
            categoryService.save(categoryObject);
        }

        NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
        DecimalFormat df = (DecimalFormat) nf;
        df.applyPattern("#.###");
        Random r = new Random();
        for (int i = 0; i < 20; i++) {
            Place place = new Place();
            place.setName(RandomString.make());
            place.setX(Double.valueOf(df.format(-85 + (85 + 85) * r.nextDouble())));
            place.setY(Double.valueOf(df.format(-180 + (180 + 180) * r.nextDouble())));
            placeService.save(place);

            Set<User> participants = new HashSet<>();
            if (i % 8 == 0) {
                participants.add(administrator);
            }
            Event event = new Event();
            event.setOrganizer(administrator);
            event.setParticipants(participants);
            event.setCategory(categoryService.findCategoryById((long) r.nextInt(10) + 1).get());
            event.setDescription("Przykładowy skrócony opis wydarzenia. Ciekawe ile może mieć maksymalnie znaków. Ciekawe ile może mieć maksymalnie znaków. Ciekawe ile może mieć maksymalnie znaków. Ciekawe ile może mieć maksymalnie znaków. Ciekawe ile może mieć maksymalnie znaków. Ciekawe ile może mieć maksymalnie znaków. Ciekawe ile może mieć maksymalnie znaków");
            event.setPlaceOfMeeting(place);
            event.setSeats(r.nextInt(200));
            event.setTitle("Wydarzenie numer " + i);
            eventService.save(event);
        }
    }

    @Override
    public void afterPropertiesSet() {
        LOG.info("Bootstrapping data...");
        initData();
        LOG.info("...Bootstrapping completed");
    }
}
