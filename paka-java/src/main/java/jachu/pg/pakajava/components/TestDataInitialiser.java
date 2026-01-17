package jachu.pg.pakajava.components;

import jachu.pg.pakajava.services.EventService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class TestDataInitialiser {
    private final EventService eventService;
    public TestDataInitialiser(EventService eventService) {
        this.eventService = eventService;
    }

    @PostConstruct
    public void init(){
        LocalDateTime start_time = LocalDateTime.now().plusHours(2);
        LocalDateTime end_time = start_time.plusHours(6);
        UUID id = eventService.createEvent("Sample Event", "This is a sample event for testing purposes.", start_time, end_time);
        System.out.println("Created event with id " + id);
    }

    @PreDestroy
    public void destroy(){
        eventService.deleteAllEvents();
    }
}
