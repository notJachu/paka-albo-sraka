package jachu.pg.pakajava.contollers;


import jachu.pg.pakajava.entities.DTOs.EventCollectionDto;
import jachu.pg.pakajava.entities.DTOs.EventCreateUpdateDto;
import jachu.pg.pakajava.entities.DTOs.EventReadDto;
import jachu.pg.pakajava.entities.DTOs.VoteRequest;
import jachu.pg.pakajava.entities.Event;
import jachu.pg.pakajava.repositories.EventRepository;
import jachu.pg.pakajava.repositories.TimeBucketRepository;
import jachu.pg.pakajava.services.CaptchaService;
import jachu.pg.pakajava.services.EventService;
import jachu.pg.pakajava.services.TimeBucketService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.beans.factory.annotation.Value;
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
    private final TimeBucketService timeBucketService;
    private final CaptchaService captchaService;
    private final UUID defaultEventID;

    public EventController(EventRepository eventRepository,
                           EventService eventService,
                           TimeBucketService timeBucketService,
                           CaptchaService captchaService,
                           @Value("${default-event-uuid}") UUID defaultEventID) {
        this.eventRepository = eventRepository;
        this.eventService = eventService;
        this.timeBucketService = timeBucketService;
        this.captchaService = captchaService;
        this.defaultEventID = defaultEventID;
    }

    @GetMapping("")
    public Page<EventCollectionDto> findAll(@PageableDefault(size = 10, sort = "name",
            direction = Sort.Direction.ASC) Pageable pageable) {
        Page<Event> eventsPage = eventService.findAllPaged(pageable);
        Page<EventCollectionDto> eventDtos = eventsPage.map(event -> new EventCollectionDto(
                event.getUuid(),
                event.getName(),
                event.getTimeStart().toString(),
                event.getTimeEnd().toString()
        ));
        return eventDtos;
    }

//    TODO: implement serving default event to be featured on the landing page
    @GetMapping("/default")
    public ResponseEntity<EventReadDto> findDefault() {
        return findById(defaultEventID);
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
                event.getTimeStart().toString(),
                event.getTimeEnd().toString(),
                event.getVotePaka(),
                event.getVotesSraka()
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
                event.getTimeStart().toString(),
                event.getTimeEnd().toString()
        );
        return ResponseEntity.ok(createdEventDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventCollectionDto> updateEvent(@PathVariable UUID id,
                                                          @RequestBody EventCreateUpdateDto eventDto) {
        Event event = eventRepository.findById(id).orElse(null);
        if (event == null) {
            return ResponseEntity.notFound().build();
        }
        try {
            LocalDateTime.parse(eventDto.getTimeStart());
            LocalDateTime.parse(eventDto.getTimeEnd());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

        Event updatedEvent = eventService.updateEvent(
                id,
                eventDto.getEventName(),
                eventDto.getEventDescription(),
                LocalDateTime.parse(eventDto.getTimeStart()),
                LocalDateTime.parse(eventDto.getTimeEnd())
        );
        EventCollectionDto updatedEventDto = new EventCollectionDto(
                updatedEvent.getUuid(),
                updatedEvent.getName(),
                updatedEvent.getTimeStart().toString(),
                updatedEvent.getTimeEnd().toString()
        );
        return ResponseEntity.ok(updatedEventDto);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteEvent(@PathVariable UUID id) {
        Event event = eventRepository.findById(id).orElse(null);
        if (event == null) {
            return ResponseEntity.notFound().build();
        }

        // implement return type for deletion
        // handle the possibility of deletion fail
        deleteEvent(id);
        return ResponseEntity.ok().build();
    }

    // Handle voting form submission
//    TODO: move voting logic to service and only handle request/response in controller
    @PostMapping("/{id}/vote")
    public ResponseEntity<Void> voteForEvent(@PathVariable UUID id,
                                             @RequestParam String vote,
                                             @CookieValue(value = "has_voted", required = false)
                                                 String hasVoted,
                                             @RequestBody VoteRequest voteData,
                                             HttpServletResponse response) {

//        String captchaToken = request.getParameter("g-recaptcha-response");
        System.out.println(voteData.toString());
        if (voteData.captchaToken == null || !captchaService.verifyRecaptcha("", voteData.captchaToken)) {
            return ResponseEntity.status(403).build();
        }
        // should probably move to service
        Event event = eventRepository.findById(id).orElse(null);


        if (event == null) {
            return ResponseEntity.notFound().build();
        }

        if (hasVoted != null && hasVoted.equals("true")) {
            return ResponseEntity.status(403).build();
        }

        // Consider moving this into event service
        // calling repository in controller kinda iffy
        if (vote.equals("paka")) {
            event.incrementVotes_paka();
            timeBucketService.recordVote(1);
        } else if (vote.equals("sraka")) {
            event.incrementVotes_sraka();
            timeBucketService.recordVote(-1);
        } else {
            return ResponseEntity.badRequest().build();
        }

        eventRepository.save(event);

        Cookie cookie = new Cookie("has_voted", "true");
        cookie.setPath("/");
        cookie.setMaxAge(3600);
        cookie.setHttpOnly(false);
//        for now allow frontend to read cookie to disable voting form,
//        but should consider making it httpOnly and only use it for backend validation
        response.addCookie(cookie);

        return ResponseEntity.ok().build();
    }
}
