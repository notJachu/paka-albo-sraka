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

    public void deleteEvent(UUID id){
        eventRepository.deleteById(id);
    }

    public void deleteAllEvents() {
        eventRepository.deleteAll();
    }
}
