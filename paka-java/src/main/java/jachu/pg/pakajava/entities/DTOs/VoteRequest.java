package jachu.pg.pakajava.entities.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;

@ToString
public class VoteRequest {

    public String vote;

    @JsonProperty("captcha")
    public String captchaToken;

}
