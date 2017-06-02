package kr.ac.cnu.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import kr.ac.cnu.annotation.CnuLogin;
import kr.ac.cnu.configuration.UserContext;
import kr.ac.cnu.configuration.WebConfig;
import kr.ac.cnu.domain.CnuUser;
import kr.ac.cnu.domain.facebook.FacebookAccessToken;
import kr.ac.cnu.domain.facebook.FacebookUser;
import kr.ac.cnu.repository.UserRepository;
import kr.ac.cnu.restclient.FacebookClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rokim on 2017. 5. 18..
 */
@Controller
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

    @ApiImplicitParam(name = "token", value = "Facebook client access token", required = true, dataType = "string", paramType = "header", defaultValue = "")
    @RequestMapping(value = "/post", method = RequestMethod.POST)
    public Map<String, Object> post(@ApiIgnore CnuUser cnuUser) {
        System.out.println(cnuUser);
        System.out.println(UserContext.getUser());
        Map<String, Object> map = new HashMap<>();
        map.put("cnu", cnuUser);
        return map;
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/hello200", method = RequestMethod.GET)
    public void hello200() {
        log.info("Hello 200!");
    }

    @ResponseBody
    @RequestMapping(value = "/helloMap", method = RequestMethod.GET)
    public Map<String, String> hello() {
        Map<String, String> map = new HashMap<>();
        String s = "Hello, World!!";
        map.put("Message", s);
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/helloUser", method = RequestMethod.GET)
    public CnuUser helloUser() {
        CnuUser cnuUser = new CnuUser();
        cnuUser.setIdx(1);
        cnuUser.setUserId("userID");
        cnuUser.setEmail("rokim@riotgames.com");
        return cnuUser;
    }

    @CnuLogin
    @ApiImplicitParam(name = "token", value = "Facebook client access token", required = true, dataType = "string", paramType = "header", defaultValue = "")
    @ResponseBody
    @RequestMapping(value = "/helloLogin", method = RequestMethod.GET)
    public CnuUser helloLogin() {
        return UserContext.getUser();
    }

    @ResponseBody
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public FacebookUser user(String code) {
        if (code == null || code.equals("")) {
            throw new RuntimeException(
                    "ERROR: Didn't get code parameter in callback.");
        }

        FacebookAccessToken facebookAccessToken = facebookClient.callFacebookAccessToken(code);
        return facebookClient.callFacebookProfile(facebookAccessToken.getAccessToken());

    }
}
