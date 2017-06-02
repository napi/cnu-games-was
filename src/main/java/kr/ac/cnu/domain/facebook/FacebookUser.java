package kr.ac.cnu.domain.facebook;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

/**
 * Created by rokim on 2017. 5. 31..
 */
@Data
public class FacebookUser {
    @JsonProperty("id")
    private String userId;
    private String name;
    private String picture;
    private String email;
    private String gender;

    @JsonProperty("picture")
    public void setPicture(Map<String, Object> map) {
        Map<String, Object> data = (Map<String, Object>) map.get("data");
        if (data != null) {
            picture = (String) data.get("url");
        }
    }
}
