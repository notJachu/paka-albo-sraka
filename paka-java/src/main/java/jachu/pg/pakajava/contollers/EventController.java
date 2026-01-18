package jachu.pg.pakajava.contollers;


import jachu.pg.pakajava.entities.DTOs.EventCollectionDto;
import jachu.pg.pakajava.entities.DTOs.EventReadDto;
import jachu.pg.pakajava.entities.Event;
import jachu.pg.pakajava.repositories.EventRepository;
import org.springframework.http.ResponseEntity;
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
    public List<EventCollectionDto> findAll() {
        List<Event> events = eventRepository.findAll();
        List<EventCollectionDto> eventDtos = events.stream()
                .map(event -> new EventCollectionDto(
                        event.getUuid(),
                        event.getName(),
                        event.getTime_start().toString(),
                        event.getTime_end().toString()
                ))
                .toList();
        return eventDtos;
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventReadDto> findById(@PathVariable UUID id) {
        Event event = eventRepository.findById(id).orElse(null);
        if (event == null) {
            return ResponseEntity.notFound().build();
        }
        EventReadDto eventDto = new EventReadDto(
                event.getUuid(),
                event.getName(),
                event.getDescription(),
                event.getTime_start().toString(),
                event.getTime_end().toString(),
                event.getVotes_paka(),
                event.getVotes_sraka()
        );
        return ResponseEntity.ok(eventDto);
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
