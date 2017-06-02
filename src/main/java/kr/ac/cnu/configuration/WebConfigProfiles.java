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
                System.out.println("LOCAL!!");
                FacebookUser facebookUser = new FacebookUser();
                facebookUser.setUserId(accessToken);
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
                System.out.println("DEV!!");
                FacebookUser facebookUser = new FacebookUser();
                facebookUser.setUserId(accessToken);
                return facebookUser;
            }
        };
    }
}
