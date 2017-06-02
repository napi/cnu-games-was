package kr.ac.cnu.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import kr.ac.cnu.annotation.CnuLogin;
import kr.ac.cnu.configuration.UserContext;
import kr.ac.cnu.configuration.WebConfig;
import kr.ac.cnu.domain.CnuUser;
import kr.ac.cnu.domain.facebook.FacebookAccessToken;
import kr.ac.cnu.repository.UserRepository;
import kr.ac.cnu.restclient.FacebookClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rokim on 2017. 5. 18..
 */
@RestController
@RequestMapping("/example")
@Slf4j
public class ExampleController {

    @Autowired
    private FacebookClient facebookClient;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/insert", method = RequestMethod.GET)
    public CnuUser insert() {
        CnuUser cnuUser = new CnuUser();
        cnuUser.setUserId("ASDF");
        return userRepository.save(cnuUser);
    }

    @ApiImplicitParams(value = {
        @ApiImplicitParam(name = "token", value = "Facebook client access token", required = true, dataType = "string", paramType = "header", defaultValue = "")
    })
    @RequestMapping(value = "/post", method = RequestMethod.POST)
    public Map<String, Object> post(@ApiIgnore CnuUser cnuUser) {
        System.out.println(cnuUser);
        System.out.println(UserContext.getUser());
        Map<String, Object> map = new HashMap<>();
        map.put("cnu", cnuUser);
        return map;
    }

    @RequestMapping(value = "/cnu", method = RequestMethod.POST)
    public Map<String, CnuUser> cnu(CnuUser cnuUser) {
        Map<String, CnuUser> map = new HashMap<>();

        map.put("Message", cnuUser);

        return map;
    }


    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    @CnuLogin
    public Map<String, String> hello() {
        String s = "Hello, World!!";
        log.info(s);
        System.out.println(UserContext.getUser());
        Map<String, String> map = new HashMap<>();

        map.put("Message", s);

        return map;
    }

    @RequestMapping(value = "/helloUser", method = RequestMethod.GET)
    public CnuUser helloUser(@ApiIgnore CnuUser cnuUser) {

        System.out.println("HelloUser");
        return cnuUser;
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
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
