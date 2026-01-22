package jachu.pg.pakajava.components;

import jachu.pg.pakajava.entities.Event;
import jachu.pg.pakajava.entities.TimeBucket;
import jachu.pg.pakajava.repositories.TimeBucketRepository;
import jachu.pg.pakajava.services.EventService;
import jachu.pg.pakajava.services.TimeBucketService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class TestDataInitialiser {
    private final EventService eventService;
    private final TimeBucketService timeBucketService;
    private final TimeBucketRepository timeBucketRepository;

    public TestDataInitialiser(EventService eventService, TimeBucketService timeBucketService, TimeBucketRepository timeBucketRepository) {
        this.eventService = eventService;
        this.timeBucketService = timeBucketService;
        this.timeBucketRepository = timeBucketRepository;
    }

    @PostConstruct
    public void init(){
        LocalDateTime start_time = LocalDateTime.now().plusHours(2);
        LocalDateTime end_time = start_time.plusHours(6);
        Event event = eventService.createEvent("Sample Event", "This is a sample event for testing purposes.", start_time, end_time);
        System.out.println("Created event with id " + event.getUuid());

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
