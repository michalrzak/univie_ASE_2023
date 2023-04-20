package com.example.ase_project.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.lang.invoke.MethodHandles;
import java.util.Collection;


@RestController
@RequestMapping(path = "api/v1/events")
public class EventController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping()
    public Collection<Event> getAllEvents() {
        LOGGER.info("GET api/v1/events");
        return eventService.getAll();
    }

    @GetMapping(value = "/{organizerID}")
    public Collection<Event> getEventsByOrganizerID(@PathVariable String organizerID) {
        LOGGER.info("GET api/v1/events/{}", organizerID);
        return eventService.getAllEventsByorganizerID(organizerID);
    }


    @GetMapping("/{eventID}")
    public Event getEventsByID(@PathVariable String eventID) {
        LOGGER.info("GET api/v1/events/{}", eventID);
        return eventService.getEventsByID(eventID);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public Event createEvent(@RequestBody Event event) {
        LOGGER.info("POST api/v1/events: {} ", event);
        eventService.createEvent(event);
        return event;
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping()
    public Event updateEvent(@RequestBody Event event) {
        LOGGER.info("PUT api/v1/events: {} ", event);
        eventService.updateEvent(event);
        return event;
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{eventID}")
    public void deleteEvent(@PathVariable String eventID){
        LOGGER.info("DELETE api/v1/events: {} ", eventID);
        eventService.deleteEvent(eventID);
    }


}
