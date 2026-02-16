package jachu.pg.pakajava.entities.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GoogleResponseDto {
    private boolean success;

    // Data weryfikacji
    @JsonProperty("challenge_ts")
    private String challengeTs;

    // Nazwa hosta
    private String hostname;

    // Kody błędów (opcjonalne)
    @JsonProperty("error-codes")
    private List<String> errorCodes;

}
