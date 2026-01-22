package jachu.pg.pakajava.components;

import jachu.pg.pakajava.entities.Event;
import jachu.pg.pakajava.entities.TimeBucket;
import jachu.pg.pakajava.services.EventService;
import jachu.pg.pakajava.services.TimeBucketService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class TestDataInitialiser {
    private final EventService eventService;
    private final TimeBucketService timeBucketService;

    public TestDataInitialiser(EventService eventService, TimeBucketService timeBucketService) {
        this.eventService = eventService;
        this.timeBucketService = timeBucketService;
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
    }

    @PreDestroy
    public void destroy(){
        eventService.deleteAllEvents();
    }
}
