package com.ase.event.dataAccess;

import com.ase.event.domain.EEventTypes;
import com.ase.event.domain.Event;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEventRepository extends JpaRepository<Event, String> {

    List<Event> findAll();

    List<Event> findAllByorganizerID(String organizerID);

    Event findEventByEventID(String id);

    List<Event> findByType(EEventTypes type);

    List<Event> findByCapacity(int capacity);

    List<Event> findByDate(Date date);

    List<Event> findByDescription(String des);

    List<Event> findByEventName(String name);


}


