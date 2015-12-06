package util;

import schedule.Schedule;

import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Created by Donghwan on 12/4/2015.
 *
 * for AlarmThread
 */
public class AlarmQueue extends PriorityBlockingQueue<Schedule>{
    private static AlarmQueue ourInstance = new AlarmQueue();

    public static AlarmQueue getInstance() {
        return ourInstance;
    }

    private AlarmQueue() {
        super(10, (o1, o2)->{
            return Long.compare(o1.getAlarmTime(), o2.getAlarmTime());
        });
    }
}
