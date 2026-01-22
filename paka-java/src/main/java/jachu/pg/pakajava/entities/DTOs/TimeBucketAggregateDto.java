package jachu.pg.pakajava.entities.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TimeBucketAggregateDto {
    int votes_paka;
    int votes_sraka;
}
