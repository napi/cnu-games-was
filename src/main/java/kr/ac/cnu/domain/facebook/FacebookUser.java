package kr.ac.cnu.domain.facebook;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Created by rokim on 2017. 5. 31..
 */
@Data
public class FacebookUser {
    @JsonProperty("userID")
    private String userId;
    private String email;
}
