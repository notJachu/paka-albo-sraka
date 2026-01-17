package jachu.pg.pakajava.contollers;


import jachu.pg.pakajava.entities.Event;
import jachu.pg.pakajava.repositories.EventRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/events")
public class EventController {
    private final EventRepository eventRepository;

    public EventController(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @GetMapping("")
    public List<Event> findAll() {
        return null;
    }

    @GetMapping("/{id}")
    public Event findById(@PathVariable UUID id) {
        return null;
    }


    // TODO: change Event to EventCreateUpdateDto
    @PostMapping("")
    public Event createEvent(@RequestBody Event event) {
        return null;
    }

    // Handle voting form submission
    @PostMapping("/{id}/vote")
    public Event voteForEvent(@PathVariable UUID id, @RequestParam String vote) {
        return null;
    }

}
