package kr.ac.cnu.restclient;

import kr.ac.cnu.domain.facebook.FacebookAccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;

import java.net.URI;

/**
 * Created by rokim on 2017. 5. 21..
 */
@Component
public class FacebookClient {
    @Autowired
    private RestTemplate restTemplate;

    public FacebookAccessToken callFacebookAccessToken(String code) {
        UriTemplate uriTemplate = new UriTemplate("https://graph.facebook.com/v2.9/oauth/access_token?" +
                "client_id={app-id}" +
                "&redirect_uri={redirect-uri}" +
                "&client_secret={app-secret}" +
                "&code={code-parameter}");

        URI uri = uriTemplate.expand("188579878330277", "http://localhost:8000/user", "5181d5706d38aa15be3bec6fa98b8c8d", code);
        System.out.println(uri);
        ResponseEntity<FacebookAccessToken> responseEntity = restTemplate.getForEntity(uri, FacebookAccessToken.class);

        return responseEntity.getBody();
    }

    public String callFacebookProfile(String accessToken) {
        UriTemplate uriTemplate = new UriTemplate("https://graph.facebook.com/me?" +
                "access_token={access-token}" +
                "&fields=id,name,picture,email" +
                "&locale=ko_KR");

        URI uri = uriTemplate.expand(accessToken);
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(uri, String.class);

        return responseEntity.getBody();
    }
}
