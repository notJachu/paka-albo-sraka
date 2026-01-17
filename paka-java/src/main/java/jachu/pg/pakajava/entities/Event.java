package jachu.pg.pakajava.entities;

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
public class Event {
    private UUID uuid;

    private String name;
    private String description;

    private LocalDateTime time_create;
    private LocalDateTime time_start;
    private LocalDateTime time_end;

    private int votes_paka;
    private int votes_sraka;
}
