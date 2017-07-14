package kr.ac.cnu.configuration;

import kr.ac.cnu.domain.CnuUser;
import kr.ac.cnu.domain.facebook.FacebookUser;
import kr.ac.cnu.restclient.FacebookClient;
import kr.ac.cnu.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by rokim on 2017. 5. 31..
 */
@Configuration
@Slf4j
public class WebConfigProfiles {
    @Autowired private FacebookClient facebookClient;
    @Autowired private UserService userService;

    @Bean
    @Profile("!dev")
    public UserOperator facebookUserOperatorLocal() {
        return new UserOperator() {
            @Override
            public CnuUser getCnuUserFromAccessToken(String accessToken) {
                log.info("LOCAL!!");
                FacebookUser facebookUser = createFacebookUser(accessToken);
                if (facebookUser == null) {
                    facebookUser = new FacebookUser();
                    facebookUser.setUserId(accessToken);
                    facebookUser.setName(accessToken);
                    facebookUser.setGender("남성");
                    facebookUser.setEmail(accessToken + "@naver.com");
                    facebookUser.setPicture(null);
                }

                return userService.findAndCreateCnuUser(facebookUser);
            }
        };
    }

    @Bean
    @Profile("dev")
    public UserOperator facebookUserOperatorDev() {
        return new UserOperator() {
            @Override
            public CnuUser getCnuUserFromAccessToken(String accessToken) {
                log.info("DEV!! : {}", accessToken);
                FacebookUser facebookUser = createFacebookUser(accessToken);
                if (facebookUser == null) {
                    return null;
                }

                return userService.findCnuUser(facebookUser.getUserId());
            }
        };
    }

    private FacebookUser createFacebookUser(String accessToken) {
        FacebookUser facebookUser = null;
        try {
            facebookUser = facebookClient.callFacebookProfile(accessToken);
        } catch (Exception e) {
            log.error("Facebook Login fail [{}] : {}", accessToken, e.toString());
        }
        return facebookUser;
    }
}
