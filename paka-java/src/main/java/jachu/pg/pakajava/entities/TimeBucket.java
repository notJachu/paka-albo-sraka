package jachu.pg.pakajava.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "daily_state")
public class TimeBucket {

    @Id
    private UUID id;

    @Builder.Default
    private LocalDate statDate =  LocalDate.now();

    @Column(nullable = false, name = "date")
    private LocalDateTime statTime = LocalDateTime.now();

    @Column(nullable = false, name = "votes_paka")
    private int votes_paka = 0;
    @Column(nullable = false, name = "votes_sraka")
    private int votes_sraka = 0;
}
