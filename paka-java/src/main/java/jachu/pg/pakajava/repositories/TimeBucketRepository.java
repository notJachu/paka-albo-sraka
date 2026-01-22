package jachu.pg.pakajava.repositories;

import jachu.pg.pakajava.entities.TimeBucket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.UUID;

public interface TimeBucketRepository extends JpaRepository<TimeBucket, UUID> {


    TimeBucket findByStatDateBetween(LocalDate statDateAfter, LocalDate statDateBefore);

    @Query("select sum(m.votes_paka) from TimeBucket m")
    int getVotesPaka();

    @Query("select sum(m.votes_sraka) from TimeBucket m")
    int getVotesSraka();
}
