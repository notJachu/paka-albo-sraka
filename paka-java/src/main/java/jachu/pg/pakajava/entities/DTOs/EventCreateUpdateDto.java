package jachu.pg.pakajava.entities.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventCreateUpdateDto {
    String eventName;
    String eventDescription;
    String timeStart;
    String timeEnd;
}
