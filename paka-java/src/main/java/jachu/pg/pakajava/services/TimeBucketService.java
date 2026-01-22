package jachu.pg.pakajava.services;

import jachu.pg.pakajava.entities.TimeBucket;
import jachu.pg.pakajava.repositories.TimeBucketRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class TimeBucketService {

    private final TimeBucketRepository timeBucketRepository;

    public TimeBucketService(TimeBucketRepository timeBucketRepository) {this.timeBucketRepository = timeBucketRepository;}

//    TODO: add recording the vote to the state

    public TimeBucket findByDate(LocalDate date) {
        return timeBucketRepository.findByStatDate(date);
    }

    public List<TimeBucket> findBetween(LocalDate startDate, LocalDate endDate) {
        return timeBucketRepository.findByStatDateBetween(startDate, endDate);
    }
}
