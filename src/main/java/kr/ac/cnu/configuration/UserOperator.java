package kr.ac.cnu.configuration;

import kr.ac.cnu.domain.facebook.FacebookUser;

/**
 * Created by rokim on 2017. 5. 31..
 */
public interface UserOperator {
    public FacebookUser getCnuUserFromAccessToken(String accessToken);
}
