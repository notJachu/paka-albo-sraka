package jachu.pg.pakajava.services;

import jachu.pg.pakajava.entities.TimeBucket;
import jachu.pg.pakajava.repositories.TimeBucketRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class TimeBucketService {

    private final TimeBucketRepository timeBucketRepository;

    public TimeBucketService(TimeBucketRepository timeBucketRepository) {this.timeBucketRepository = timeBucketRepository;}

//    TODO: revise this code to make it cleaner/safer
//    currently vote is represented as an integer, where 1 = paka, -1 = sraka
//    should probably change for something better
    public void recordVote(int vote){
        LocalDate localDate = LocalDate.now();

        int rowsUpdated = 0;
        if(vote == 1){
            rowsUpdated = timeBucketRepository.incrementVotesPaka(localDate);
        } else if (vote == -1) {
            rowsUpdated = timeBucketRepository.incrementVotesSraka(localDate);
        }
        if(rowsUpdated == 0){
            // no existing TimeBucket for today, create one
            TimeBucket timeBucket = TimeBucket.builder()
                    .id(UUID.randomUUID())
                    .statDate(localDate)
                    .votes_paka(vote == 1 ? 1 : 0)
                    .votes_sraka(vote == -1 ? 1 : 0)
                    .build();
            timeBucketRepository.save(timeBucket);
        }
    }

    public TimeBucket findByDate(LocalDate date) {
        return timeBucketRepository.findByStatDate(date);
    }

    public List<TimeBucket> findBetween(LocalDate startDate, LocalDate endDate) {
        return timeBucketRepository.findByStatDateBetween(startDate, endDate);
    }
}
