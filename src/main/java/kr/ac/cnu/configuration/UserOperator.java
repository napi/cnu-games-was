package kr.ac.cnu.configuration;

import kr.ac.cnu.domain.CnuUser;

/**
 * Created by rokim on 2017. 5. 31..
 */
public interface UserOperator {
    CnuUser getCnuUserFromAccessToken(String accessToken);
}
