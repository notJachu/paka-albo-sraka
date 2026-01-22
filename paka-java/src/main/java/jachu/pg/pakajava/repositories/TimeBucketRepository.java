package jachu.pg.pakajava.repositories;

import jachu.pg.pakajava.entities.TimeBucket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface TimeBucketRepository extends JpaRepository<TimeBucket, UUID> {


    List<TimeBucket> findByStatDateBetween(LocalDate statDateAfter, LocalDate statDateBefore);

    @Query("select sum(m.votes_paka) from TimeBucket m")
    int getVotesPaka();

    @Query("select sum(m.votes_sraka) from TimeBucket m")
    int getVotesSraka();

    @Modifying
    @Query("update TimeBucket m set m.votes_paka = m.votes_paka + 1 where m.statDate = :statDate")
    int incrementVotesPaka(@Param("statDate") LocalDate statDate);

    @Modifying
    @Query("update TimeBucket m set m.votes_sraka = m.votes_sraka + 1 where m.statDate = :statDate")
    int incrementVotesSraka(@Param("statDate") LocalDate statDate);

    TimeBucket findByStatDate(LocalDate statDate);
}
