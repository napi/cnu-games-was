package kr.ac.cnu.controller;

import kr.ac.cnu.configuration.UserContext;
import kr.ac.cnu.configuration.UserOperator;
import kr.ac.cnu.domain.CnuUser;
import kr.ac.cnu.domain.facebook.FacebookUser;
import kr.ac.cnu.exception.BadRequestException;
import kr.ac.cnu.restclient.FacebookClient;
import kr.ac.cnu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * Created by rokim on 2017. 7. 5..
 */
@RestController
@RequestMapping("/api/login")
public class LoginController {
    @Autowired private FacebookClient facebookClient;
    @Autowired private UserService userService;

    /**
     * TODO Token 만 있으면 쉽게 로그인 정보를 탈취할 수 있다
     * @param session
     * @param token
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public CnuUser login(HttpSession session, @RequestParam String token) {
        FacebookUser facebookUser = facebookClient.callFacebookProfile(token);
        CnuUser cnuUser = userService.findAndCreateCnuUser(facebookUser);

        if (cnuUser == null) {
            throw new BadRequestException();
        }
        session.setAttribute("user", cnuUser);
        return cnuUser;
    }
}
