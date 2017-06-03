package kr.ac.cnu.configuration;

import kr.ac.cnu.domain.facebook.FacebookUser;
import kr.ac.cnu.restclient.FacebookClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Created by rokim on 2017. 5. 31..
 */
@Configuration
@Slf4j
public class WebConfigProfiles {
    @Autowired private FacebookClient facebookClient;

    @Bean
    @Profile("!dev")
    public UserOperator facebookUserOperatorLocal() {
        return new UserOperator() {
            @Override
            public FacebookUser getCnuUserFromAccessToken(String accessToken) {
                log.info("LOCAL!!");
                FacebookUser facebookUser = new FacebookUser();
                facebookUser.setUserId(accessToken);
                facebookUser.setName(accessToken);
                facebookUser.setGender("남성");
                facebookUser.setEmail(accessToken + "@naver.com");
                facebookUser.setPicture(null);
                return facebookUser;
            }
        };
    }

    @Bean
    @Profile("dev")
    public UserOperator facebookUserOperatorDev() {
        return new UserOperator() {
            @Override
            public FacebookUser getCnuUserFromAccessToken(String accessToken) {
                log.info("DEV!! : {}", accessToken);
                FacebookUser facebookUser = facebookClient.callFacebookProfile(accessToken);
                return facebookUser;
            }
        };
    }
}
