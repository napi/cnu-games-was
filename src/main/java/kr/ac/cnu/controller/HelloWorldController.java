package kr.ac.cnu.controller;

import kr.ac.cnu.domain.CnuUser;
import kr.ac.cnu.domain.facebook.FacebookAccessToken;
import kr.ac.cnu.repository.UserRepository;
import kr.ac.cnu.restclient.FacebookClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.PermitAll;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by rokim on 2017. 5. 18..
 */
@RestController
@RequestMapping("")
@Slf4j
public class HelloWorldController {

    @Autowired
    private FacebookClient facebookClient;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/insert")
    public CnuUser insert() {
        CnuUser cnuUser = new CnuUser();
        cnuUser.setUserId("ASDF");
        return userRepository.save(cnuUser);
    }

    @RequestMapping("/cnu")
    public Map<String, CnuUser> cnu(CnuUser cnuUser) {
        Map<String, CnuUser> map = new HashMap<>();

        map.put("Message", cnuUser);

        return map;
    }

    @RequestMapping("/hello")
    public Map<String, String> hello() {
        String s = "Hello, World!!";
        log.info(s);
        Map<String, String> map = new HashMap<>();

        map.put("Message", s);

        return map;
    }

    @RequestMapping("/helloUser")
    public CnuUser helloUser() {
        CnuUser cnuUser = new CnuUser();
        cnuUser.setIdx(1);
        cnuUser.setUserId("rokim");

        return cnuUser;
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
