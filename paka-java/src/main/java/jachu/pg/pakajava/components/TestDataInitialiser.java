package jachu.pg.pakajava.components;

import jachu.pg.pakajava.services.EventService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class TestDataInitialiser {
    private final EventService eventService;
    public TestDataInitialiser(EventService eventService) {
        this.eventService = eventService;
    }

    @PostConstruct
    public void init(){
        
    }
}
