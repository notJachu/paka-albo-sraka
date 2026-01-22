package jachu.pg.pakajava.services;


import jachu.pg.pakajava.entities.Event;
import jachu.pg.pakajava.repositories.EventRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
public class EventService {

    private final EventRepository eventRepository;


    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Event createEvent(String name, String description, LocalDateTime timeStart, LocalDateTime timeEnd) {
        Event event =  Event.builder()
                .uuid(UUID.randomUUID())
                .name(name)
                .description(description)
                .timeCreate(LocalDateTime.now())
                .timeStart(timeStart)
                .timeEnd(timeEnd)
                .build();
        eventRepository.save(event);
        return event;
    }

    public Event updateEvent(UUID id, String name, String description, LocalDateTime timeStart, LocalDateTime timeEnd) {

        Event event = eventRepository.findById(id).orElseThrow(() -> new RuntimeException("Event not found"));

        event.setName(name);
        event.setDescription(description);
        event.setTimeStart(timeStart);
        event.setTimeEnd(timeEnd);
        eventRepository.save(event); // may be redundant due to transactional context
        return event;
    }

    public void deleteEvent(UUID id){
        eventRepository.deleteById(id);
    }

    public void deleteAllEvents() {
        eventRepository.deleteAll();
    }

    public Page<Event> findAllPaged(Pageable pageable) {
        return eventRepository.findAll(pageable);
    }
}
