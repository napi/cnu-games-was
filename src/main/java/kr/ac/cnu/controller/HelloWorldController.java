package kr.ac.cnu.controller;

import kr.ac.cnu.domain.facebook.FacebookAccessToken;
import kr.ac.cnu.restclient.FacebookClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by rokim on 2017. 5. 18..
 */
@RestController
@RequestMapping("")
@Slf4j
public class HelloWorldController {

    @Autowired
    private FacebookClient facebookClient;

    @RequestMapping("/hello")
    public String hello() {
        String s = "Hello, World!!";
        log.info(s);
        return s;
    }

    @RequestMapping("/user")
    public String user(String code) {
        if (code == null || code.equals("")) {
            throw new RuntimeException(
                    "ERROR: Didn't get code parameter in callback.");
        }

        FacebookAccessToken facebookAccessToken = facebookClient.callFacebookAccessToken(code);
        String profileJson = facebookClient.callFacebookProfile(facebookAccessToken.getAccessToken());

        System.out.println(profileJson);
        return profileJson;
    }
}
