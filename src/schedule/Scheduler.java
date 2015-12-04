package schedule;

import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Created by Donghwan on 11/29/2015.
 *
 * 스케쥴러 프로그램
 */
public class Scheduler {

    private AlarmThread alarmThread;
    private PriorityBlockingQueue<Schedule> scheduleAlarmQueue;
    private int studentId;

    public Scheduler() throws FileNotFoundException{
        scheduleAlarmQueue = new PriorityBlockingQueue<>();
        alarmThread = new AlarmThread(scheduleAlarmQueue);
        Calendar cur = GregorianCalendar.getInstance();
    }

    public void loadAlarmQueue(){
        //현재 시간에서 8일 후까지의 일정을 불러온다.
        //알람큐에 등록
    }
}
