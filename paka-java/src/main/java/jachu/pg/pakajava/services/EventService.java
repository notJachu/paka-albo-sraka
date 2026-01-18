package jachu.pg.pakajava.services;


import jachu.pg.pakajava.entities.Event;
import jachu.pg.pakajava.repositories.EventRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
public class EventService {

    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Event createEvent(String name, String description, LocalDateTime timeStart, LocalDateTime timeEnd) {
        Event event =  Event.builder()
                .uuid(UUID.randomUUID())
                .name(name)
                .description(description)
                .time_create(LocalDateTime.now())
                .time_start(timeStart)
                .time_end(timeEnd)
                .build();
        eventRepository.save(event);
        return event;
    }

    public Event updateEvent(UUID id, String name, String description, LocalDateTime timeStart, LocalDateTime timeEnd) {

        Event event = eventRepository.findById(id).orElseThrow(() -> new RuntimeException("Event not found"));

        event.setName(name);
        event.setDescription(description);
        event.setTime_start(timeStart);
        event.setTime_end(timeEnd);
        eventRepository.save(event); // may be redundant due to transactional context
        return event;
    }

    public void deleteAllEvents() {
        eventRepository.deleteAll();
    }
}
