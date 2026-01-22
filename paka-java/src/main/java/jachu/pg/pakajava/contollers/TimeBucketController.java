package jachu.pg.pakajava.contollers;

import jachu.pg.pakajava.entities.DTOs.TimeBucketCollectionDto;
import jachu.pg.pakajava.services.TimeBucketService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/time-buckets")
public class TimeBucketController {

    private final TimeBucketService timeBucketService;

    public TimeBucketController(TimeBucketService timeBucketService) {
        this.timeBucketService = timeBucketService;
    }

    @GetMapping("")
    public List<TimeBucketCollectionDto> getTimeBuckets() {
        List<TimeBucketCollectionDto> buckets = timeBucketService.findAll().stream().map(
                bucket -> new TimeBucketCollectionDto(
                        bucket.getStatDate().toString(),
                        bucket.getVotes_paka(),
                        bucket.getVotes_sraka()
                )
        ).toList();

        return buckets;
    }

}
/*
    BUCKETS
    - list all buckets
        GET /api/time-buckets
    - get bucket by date
        GET /api/time-buckets?date=date
    - get buckets between dates
        GET /api/time-buckets?from=date&to=date
    - get current day bucket
        GET /api/time-buckets/current

    AGGREGATES
    - get the value of votes paka and sraka (total)
        GET /api/time-buckets/aggregates
    - get the value of votes paka and sraka between dates
        GET /api/time-buckets/aggregates?from=date&to=date

 */