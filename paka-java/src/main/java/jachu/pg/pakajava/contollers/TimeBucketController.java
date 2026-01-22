package jachu.pg.pakajava.contollers;

import jachu.pg.pakajava.entities.DTOs.TimeBucketAggregateDto;
import jachu.pg.pakajava.entities.DTOs.TimeBucketCollectionDto;
import jachu.pg.pakajava.services.TimeBucketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/time-buckets")
public class TimeBucketController {

    private final TimeBucketService timeBucketService;

    public TimeBucketController(TimeBucketService timeBucketService) {
        this.timeBucketService = timeBucketService;
    }

    // BUCKETS
    @GetMapping("")
    public List<TimeBucketCollectionDto> getTimeBuckets(@RequestParam(required = false, value = "from") String from,
                                                        @RequestParam(required = false, value = "to") String to,
                                                        @RequestParam(required = false, value = "date") String date) {

        List<TimeBucketCollectionDto> buckets;

        // giving list of len = 1 to keep return type consistent
        if (date != null){

            try {
                java.time.LocalDate.parse(date);
            } catch (Exception e){
                return List.of();
            }
            var bucket = timeBucketService.findByDate(java.time.LocalDate.parse(date));

            if (bucket == null){
                return List.of();
            }

            buckets = List.of(new TimeBucketCollectionDto(
                    bucket.getStatDate().toString(),
                    bucket.getVotes_paka(),
                    bucket.getVotes_sraka()
            ));
        }
        else if (from != null && to != null){
            try {
                java.time.LocalDate.parse(from);
                java.time.LocalDate.parse(to);
            } catch (Exception e){
                return List.of();
            }
            buckets = timeBucketService.findBetween(
                    java.time.LocalDate.parse(from),
                    java.time.LocalDate.parse(to)
            ).stream().map(
                    bucket -> new TimeBucketCollectionDto(
                            bucket.getStatDate().toString(),
                            bucket.getVotes_paka(),
                            bucket.getVotes_sraka()
                    )
            ).toList();
        }
        else {
            // list all buckets
            buckets = timeBucketService.findAll().stream().map(
                    bucket -> new TimeBucketCollectionDto(
                            bucket.getStatDate().toString(),
                            bucket.getVotes_paka(),
                            bucket.getVotes_sraka()
                    )
            ).toList();
        }
        return buckets;
    }

    @GetMapping("/current")
    public ResponseEntity<TimeBucketCollectionDto> getCurrentDayBucket() {
        var bucket = timeBucketService.findByDate(java.time.LocalDate.now());

        if (bucket == null){
            return ResponseEntity.notFound().build();
        }

        var dto = new TimeBucketCollectionDto(
                bucket.getStatDate().toString(),
                bucket.getVotes_paka(),
                bucket.getVotes_sraka()
        );

        return ResponseEntity.ok(dto);
    }

    // AGGREGATES
    @GetMapping("/aggregates")
    public ResponseEntity<TimeBucketAggregateDto> getAggregates(@RequestParam(required = false, value = "from") String from,
                                                                @RequestParam(required = false, value = "to") String to) {

        TimeBucketAggregateDto result;
        if (from != null && to != null){
            try {
                java.time.LocalDate.parse(from);
                java.time.LocalDate.parse(to);
            } catch (Exception e){
                return ResponseEntity.badRequest().build();
            }
            var totals = timeBucketService.getTotalVotesBetween(
                    java.time.LocalDate.parse(from),
                    java.time.LocalDate.parse(to)
            );
            // TODO: change for something that makes sense
            result = new TimeBucketAggregateDto(totals._1(), totals._2());

        } else {
            var totals = timeBucketService.getTotalVotes();
            result = new TimeBucketAggregateDto(totals._1(), totals._2());
        }
        return ResponseEntity.ok(result);
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