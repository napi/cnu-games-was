package kr.ac.cnu.controller;

import io.swagger.annotations.ApiImplicitParam;
import kr.ac.cnu.annotation.CnuLogin;
import kr.ac.cnu.configuration.UserContext;
import kr.ac.cnu.domain.CnuUser;
import kr.ac.cnu.domain.facebook.FacebookUser;
import kr.ac.cnu.repository.UserRepository;
import kr.ac.cnu.restclient.FacebookClient;
import kr.ac.cnu.service.ExampleService;
import kr.ac.cnu.study.model.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

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

    @Autowired
    private ExampleService exampleService;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        // resouces/templates/{RETURN}.html 를 찾는다
        return "index";
    }

    @RequestMapping(value = "/board", method = RequestMethod.GET)
    public String board(Model model) {
        // {RETURN}.html 에 매핑할 attribute 정보를 넣는다.
        model.addAttribute("boardList", exampleService.getDummyBoardList());

        // resouces/templates/{RETURN}.html 를 찾는다
        return "board";
    }

    @RequestMapping(value = "/facebook", method = RequestMethod.GET)
    public String board() {
        return "facebook";
    }


    @RequestMapping(value = "/helloWorld", method = RequestMethod.GET)
    // Response 에 직접 value 를 set 한다.
    @ResponseBody
    public String helloWorld() {
        return "Hello, World";
    }

    @RequestMapping(value = "/helloParam", method = RequestMethod.GET)
    @ResponseBody
    // URI 의 쿼리 파라미터를 넣는다.
    public String helloParam(@RequestParam String param) {
        return param;
    }

    @RequestMapping(value = "/helloPath/{path}", method = RequestMethod.GET)
    @ResponseBody
    // URI 의 path 를 파라미터로 사용한다.
    public String helloPath(@PathVariable String path) {
        // HttpMessageConverter 가 String 을 HTTP body 에 직접 value 를 넣어준다.
        return path;
    }

    @RequestMapping(value = "/helloBody", method = RequestMethod.POST)
    @ResponseBody
    // POST 의 json 으로 된 request body 를 매핑하여 파라미터로 전달한다.
    public Student helloBody(@RequestBody Student student) {
        // Response 에 직접 value 를 set 한다.
        // 이 경우 MappingJackson2HttpMessageConverter 가 Object 를 json 포맷으로 변경한다.

        // TODO insert Student

        return student;
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/hello200", method = RequestMethod.GET)
    public void hello200() {
        log.info("Hello 200!");
    }

    @ResponseBody
    @RequestMapping(value = "/helloMap", method = RequestMethod.GET)
    public Map<String, String> helloMap() {
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

    // WebConfig 의 Interceptor 부분을 참고
    @CnuLogin
    @ApiImplicitParam(name = "token", value = "Facebook client access token", required = true, dataType = "string", paramType = "header", defaultValue = "")
    @ResponseBody
    @RequestMapping(value = "/helloLogin", method = RequestMethod.GET)
    public CnuUser helloLogin() {
        return UserContext.getUser();
    }

    @RequestMapping(value = "/facebook/{path}", method = RequestMethod.GET)
    @ResponseBody
    public FacebookUser getCnuUser(@PathVariable String path) {
        return facebookClient.callFacebookProfile(path);
    }

//    Don't need to study
//
//    @ResponseBody
//    @RequestMapping(value = "/user", method = RequestMethod.GET)
//    public FacebookUser user(String code) {
//        if (code == null || code.equals("")) {
//            throw new RuntimeException(
//                    "ERROR: Didn't get code parameter in callback.");
//        }
//
//        FacebookAccessToken facebookAccessToken = facebookClient.callFacebookAccessToken(code);
//        return facebookClient.callFacebookProfile(facebookAccessToken.getAccessToken());
//
//    }
}
