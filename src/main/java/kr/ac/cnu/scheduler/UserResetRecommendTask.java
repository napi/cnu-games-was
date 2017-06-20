package kr.ac.cnu.scheduler;

import kr.ac.cnu.domain.CnuUser;

import java.util.TimerTask;

/**
 * Created by dingue on 2017-06-20.
 */
public class UserResetRecommendTask extends TimerTask {
    CnuUser cnuUser;

    public UserResetRecommendTask(CnuUser cnuUser) {
        this.cnuUser = cnuUser;
    }

    public void run() {
        cnuUser.setOneDayGoodAndBadCount(0);
    }
}
