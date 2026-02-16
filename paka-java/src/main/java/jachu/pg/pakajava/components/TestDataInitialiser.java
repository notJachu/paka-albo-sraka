package jachu.pg.pakajava.components;

import jachu.pg.pakajava.entities.Event;
import jachu.pg.pakajava.entities.TimeBucket;
import jachu.pg.pakajava.repositories.EventRepository;
import jachu.pg.pakajava.repositories.TimeBucketRepository;
import jachu.pg.pakajava.services.EventService;
import jachu.pg.pakajava.services.TimeBucketService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;

@Component
public class TestDataInitialiser {
    private final EventService eventService;
    private final EventRepository eventRepository;
    private final TimeBucketService timeBucketService;
    private final TimeBucketRepository timeBucketRepository;
    private final UUID defaultID;

    public TestDataInitialiser(EventService eventService, TimeBucketService timeBucketService, TimeBucketRepository timeBucketRepository, EventRepository eventRepository,
                               @Value("${default-event-uuid}")UUID uuid) {
        this.eventService = eventService;
        this.eventRepository = eventRepository;
        this.timeBucketService = timeBucketService;
        this.timeBucketRepository = timeBucketRepository;
        this.defaultID = uuid;
    }

    @PostConstruct
    public void init(){
        LocalDateTime start_time = LocalDateTime.now().plusHours(2);
        LocalDateTime end_time = start_time.plusHours(6);
        Event event = eventService.createEvent("Sample Event", "This is a sample event for testing purposes.", start_time, end_time);
        System.out.println("Created event with id " + event.getUuid());

//        Event defaultEvent = eventService.createEvent("default event","default desc",LocalDateTime.now(),LocalDateTime.now());
        Event eventDefault =  Event.builder()
                .uuid(defaultID)
                .name("defaultName")
                .description("defaultDesc")
                .timeCreate(LocalDateTime.now())
                .timeStart(LocalDateTime.now().plusDays(1))
                .timeEnd(LocalDateTime.now())
                .build();
        eventRepository.save(eventDefault);
        timeBucketService.recordVote(1);
        timeBucketService.recordVote(-1);

        TimeBucket todayBucket = timeBucketService.findByDate(java.time.LocalDate.now());
        System.out.println("Today's TimeBucket - Paka votes: " + todayBucket.getVotes_paka() + ", Sraka votes: " + todayBucket.getVotes_sraka());

//        test code - should be removed
        TimeBucket timeBucket = TimeBucket.builder()
                .id(UUID.randomUUID())
                .statDate(LocalDate.now().minusDays(2))
                .votes_paka(1)
                .votes_sraka(0)
                .build();
        timeBucketRepository.save(timeBucket);

        TimeBucket timeBucket2 = TimeBucket.builder()
                .id(UUID.randomUUID())
                .statDate(LocalDate.now().minusDays(5))
                .votes_paka(1)
                .votes_sraka(100)
                .build();
        timeBucketRepository.save(timeBucket2);
    }

    @PreDestroy
    public void destroy(){
        eventService.deleteAllEvents();
    }
}
