package kr.ac.cnu.configuration;

import kr.ac.cnu.domain.CnuUser;

/**
 * Created by rokim on 2017. 5. 31..
 */
public class UserContext {
    private static final ThreadLocal<CnuUser> USER_INFO = new ThreadLocal<>();

    public static final void setUser(CnuUser cnuUser) {
        USER_INFO.set(cnuUser);
    }

    public static final CnuUser getUser() {
        return USER_INFO.get();
    }

}
