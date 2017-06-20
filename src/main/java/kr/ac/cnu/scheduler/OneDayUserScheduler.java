package kr.ac.cnu.scheduler;

import kr.ac.cnu.domain.CnuUser;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

/**
 * Created by dingue on 2017-06-20.
 */
public class OneDayUserScheduler {
    public OneDayUserScheduler(CnuUser cnuUser) {
        Timer timer = new Timer(false);

        Date now = new Date(System.currentTimeMillis());
        Calendar date = Calendar.getInstance();
        date.set(Calendar.HOUR, 0);
        date.set(Calendar.DAY_OF_WEEK, now.getDay());
        date.set(Calendar.MINUTE, now.getMinutes());
        date.set(Calendar.SECOND, now.getSeconds());

        timer.schedule(new UserResetRecommendTask(cnuUser), date.getTime(), 24*60*60*1000);
    }
}
