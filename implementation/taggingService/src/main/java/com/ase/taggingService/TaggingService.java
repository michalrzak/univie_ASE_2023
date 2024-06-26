package com.ase.taggingService;

import com.ase.common.taggingEvent.ETags;
import com.ase.taggingService.repository.ITaggingRepository;
import jakarta.transaction.Transactional;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaggingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(
            MethodHandles.lookup().lookupClass());

    private final ITaggingRepository repository;
    private final Publisher publisher;


    @Autowired
    public TaggingService(ITaggingRepository repository, Publisher publisher) {
        this.repository = repository;
        this.publisher = publisher;
    }


    public TaggingEvent getEventById(String eventId, String attendeeId) {
        LOGGER.debug("get TaggingEvent by eventId and attendeeId");
        return repository.getTaggingEventByEventIdAndUserId(eventId, attendeeId);
    }


    /**
     * method is called to add a New TaggingEvent anr returns new TaggingEvent
     *
     * @param userId, eventId, etags
     * @return TaggingEvent
     */
    public TaggingEvent addNewTaggingEvent(String userId, String eventId, List<ETags> eTags) {
        LOGGER.debug("add New TaggingEvent");
        if (repository.existsByEventIdAndUserId(eventId,
                userId)) { //in case Event already exists in database
            LOGGER.debug("TaggingEvent changed");
            TaggingEvent taggingEvent = repository.getTaggingEventByEventIdAndUserId(eventId,
                    userId);
            repository.deleteByEventIdAndUserId(eventId, userId);
            TaggingEvent newTaggingEvent = new TaggingEvent(taggingEvent);
            newTaggingEvent.addEventTag(eTags);
            repository.save(newTaggingEvent);
            List<ETags> tags = new ArrayList<>();
            tags.addAll(eTags);
            try {
                LOGGER.debug("TaggingEvent published");
                publisher.updateTaggingEvent(eventId, userId, tags);
            } catch (Exception e){
                LOGGER.error("Failed to connect with publisher");
                return newTaggingEvent;
            }
            return newTaggingEvent;
        } else {     //in case new TaggingEvent must be created in DB
            LOGGER.debug("Create new TaggingEvent");
            TaggingEvent newTaggingEvent = new TaggingEvent(userId, eventId, eTags);
            repository.save(newTaggingEvent);
            List<ETags> tags = new ArrayList<>();
            tags.addAll(eTags);
            try {
                publisher.newTaggingEvent(eventId, userId, tags);
            }catch (Exception e){
                LOGGER.error("Failed to connect with publisher");
                return newTaggingEvent;
            }
            return newTaggingEvent;
        }
    }

    /**
     * method is called to delete a Tag and changes the TaggingEvent, returns TaggingEvent
     *
     * @param userId, eventId,  eTags
     * @return TaggingEvent
     */
    @Transactional
    public TaggingEvent removeTag(String userId, String eventId, List<ETags> eTags) {
        LOGGER.debug("Remove Tag from TaggingEvent");
        TaggingEvent changedTaggingEvent = repository.getTaggingEventByEventIdAndUserId(eventId,
                userId);
        if (changedTaggingEvent.getEventTags().size() == 1) {  //removes DB entry not longer needed
            LOGGER.debug("TaggingEvent deleted from Database");
            repository.deleteAllByEventIdAndUserId(eventId, userId);
            return null;
        } else {
            ETags tag = eTags.get(0);
            changedTaggingEvent.removeEventTag(tag);
            repository.save(changedTaggingEvent);
            try {
                publisher.updateTaggingEvent(changedTaggingEvent.getEventId(),
                        changedTaggingEvent.getUserId(), changedTaggingEvent.getEventTags());
            } catch (Exception e){
                LOGGER.error("Failed to connect with publisher");
                return changedTaggingEvent;
            }
            return changedTaggingEvent;
        }
    }

    /**
     * removes TaggingEvent from Database
     *
     * @param userId, eventId
     * @return void
     */
    @Transactional
    public void removeTaggingEvent(String userId, String eventId) {
        LOGGER.debug("Delete TaggingEvent");
        repository.deleteAllByEventIdAndUserId(eventId, userId);
    }
}