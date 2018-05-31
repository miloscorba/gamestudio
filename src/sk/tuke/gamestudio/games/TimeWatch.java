package sk.tuke.gamestudio.games;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

public class TimeWatch implements Serializable {
    private long starts;

    public static TimeWatch start() {
        return new TimeWatch();
    }

    private TimeWatch() {
        reset();
    }

    public void reset() {
        starts = System.currentTimeMillis();
    }

    public long getTime() {
        long ends = System.currentTimeMillis();
        return ends - starts;
    }

    public long getTime(TimeUnit unit) {
        return unit.convert(getTime(), TimeUnit.MILLISECONDS);
    }
}