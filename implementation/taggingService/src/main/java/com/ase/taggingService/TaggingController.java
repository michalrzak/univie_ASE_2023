package com.ase.taggingService;

import com.ase.common.taggingEvent.ETags;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/tag")
public class TaggingController {

    private static final Logger LOGGER = LoggerFactory.getLogger(
            MethodHandles.lookup().lookupClass());

    private final TaggingService taggingService;


    @Autowired
    public TaggingController(TaggingService taggingService) {
        this.taggingService = taggingService;
    }

    @PostMapping("/add/{eventId}/{userId}/{addEventTag}")  //add new eventTag to event for user
    public TaggingEvent addEventTag(@PathVariable String eventId, @PathVariable String userId,
            @PathVariable ETags addEventTag) {
        LOGGER.info("POST api/v1/tag/add/{}/{}/{}", eventId, userId, addEventTag);
        List<ETags>tag = new ArrayList<>();
        tag.add(addEventTag);
        return taggingService.addNewTaggingEvent(userId, eventId, tag);
    }

    @GetMapping("/event/{eventId}/{userId}") //returns event with tags
    public TaggingEvent getEvent(@PathVariable String eventId, @PathVariable String userId) {
        LOGGER.info("GET api/v1/tag/event/{}/{}", eventId, userId);
        return taggingService.getEventById(eventId, userId);
    }

    @PutMapping("/removeTag/{eventId}/{userId}/{eventTag}")
    public TaggingEvent removeEventTag(@PathVariable String eventId, @PathVariable String userId,
            @PathVariable ETags eventTag) {
        LOGGER.info("DELETE api/v1/tag/removeTag/{}/{}/{}", eventId, userId, eventTag);
        List<ETags>eTags = new ArrayList<>();
        eTags.add(eventTag);
        return taggingService.removeTag(userId, eventId, eTags);
    }

    @DeleteMapping("/removeEvent/{eventId}/{userId}")
    public void removeTaggingEvent(@PathVariable String eventId, @PathVariable String userId) {
        LOGGER.info("DELETE api/v1/tag/removeEvent/{}/{}", eventId, userId);
        taggingService.removeTaggingEvent(userId, eventId);
    }

    @GetMapping("/healthCheck")
    public ResponseEntity<String> healthCheck(){
        return ResponseEntity.status(HttpStatus.OK).body("Success");
    }
}
