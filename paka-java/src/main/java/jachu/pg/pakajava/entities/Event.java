package jachu.pg.pakajava.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "events")
public class Event {

    @Id
    private UUID uuid;
    @Column(nullable = false, name = "event_name")
    private String name;
    @Column(length = 1000, name = "event_description")
    private String description;

    @Column(nullable = false, name = "time_created")
    private LocalDateTime time_create;
    @Column(nullable = false, name = "time_start")
    private LocalDateTime time_start;
    @Column(nullable = false, name = "time_end")
    private LocalDateTime time_end;

    @Column(nullable = false, name = "votes_paka")
    private int votes_paka = 0;
    @Column(nullable = false, name = "votes_sraka")
    private int votes_sraka = 0;

    public void incrementVotes_paka() {
        this.votes_paka++;
    }

    public void incrementVotes_sraka() {
        this.votes_sraka++;
    }
}
