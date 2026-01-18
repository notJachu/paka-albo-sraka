package jachu.pg.pakajava.contollers;


import jachu.pg.pakajava.entities.DTOs.EventCollectionDto;
import jachu.pg.pakajava.entities.DTOs.EventCreateUpdateDto;
import jachu.pg.pakajava.entities.DTOs.EventReadDto;
import jachu.pg.pakajava.entities.Event;
import jachu.pg.pakajava.repositories.EventRepository;
import jachu.pg.pakajava.services.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/events")
public class EventController {
    private final EventRepository eventRepository;
    private final EventService eventService;

    public EventController(EventRepository eventRepository, EventService eventService) {
        this.eventRepository = eventRepository;
        this.eventService = eventService;
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


    @PostMapping("")
    public ResponseEntity<EventCollectionDto> createEvent(@RequestBody EventCreateUpdateDto eventDto) {
        try {
            LocalDateTime.parse(eventDto.getTimeStart());
            LocalDateTime.parse(eventDto.getTimeEnd());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        Event event = eventService.createEvent(
                eventDto.getEventName(),
                eventDto.getEventDescription(),
                LocalDateTime.parse(eventDto.getTimeStart()),
                LocalDateTime.parse(eventDto.getTimeEnd())
        );
        EventCollectionDto createdEventDto = new EventCollectionDto(
                event.getUuid(),
                event.getName(),
                event.getTime_start().toString(),
                event.getTime_end().toString()
        );
        return ResponseEntity.ok(createdEventDto);
    }

    // Handle voting form submission
    @PostMapping("/{id}/vote")
    public ResponseEntity<Void> voteForEvent(@PathVariable UUID id, @RequestParam String vote) {
        Event event = eventRepository.findById(id).orElse(null);
        if (event == null) {
            return ResponseEntity.notFound().build();
        }

        if (vote.equals("paka")) {
            event.setVotes_paka(event.getVotes_paka() + 1);
        } else if (vote.equals("sraka")) {
            event.setVotes_sraka(event.getVotes_sraka() + 1);
        } else {
            return ResponseEntity.badRequest().build();
        }

        eventRepository.save(event);
        return ResponseEntity.ok().build();
    }

}
