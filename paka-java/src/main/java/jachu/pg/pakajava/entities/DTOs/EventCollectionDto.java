package jachu.pg.pakajava.entities.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventCollectionDto {
    UUID uuid;
    String eventName;
    String timeStart;
    String timeEnd;
}
