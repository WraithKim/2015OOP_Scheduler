package util;

import schedule.AlarmThread;
import schedule.Schedule;

import java.util.concurrent.PriorityBlockingQueue;

/**
 * Created by Donghwan on 12/4/2015.
 *
 * 알람 쓰레드에서 가장 앞의 알람을 먼저 보여주는 우선순위 동기화 큐
 */
public class AlarmQueue extends PriorityBlockingQueue<Schedule>{
    private static final AlarmQueue ourInstance = new AlarmQueue();

    public static AlarmQueue getInstance() {
        return ourInstance;
    }

    private AlarmQueue() {
        super(10, (o1, o2)->{
            int ret = Long.compare(o1.getAlarmTime(), o2.getAlarmTime());
            if(ret == 0) return -1;
            else return ret;
        });
    }

    // 동기화 큐의 세가지 추가 함수를 오버라이딩 했지만, 우선순위 동기화 큐의 API 문서에 의하면 add를 사용하기를 강조함.
    // 반드시 불러온 기간의 -1 이내의 일정을 갱신해야만 안전하다.
    @Override
    public boolean add(Schedule schedule) {
        return  schedule.getAlarmTime() > System.currentTimeMillis() &&
                schedule.getAlarmTime() <= AlarmThread.getInstance().getNextDay().getTimeInMillis() + Constant.loadTermForQueue &&
                super.add(schedule);
    }

    @Override
    public void put(Schedule schedule) {
        if(schedule.getAlarmTime() > System.currentTimeMillis() &&
                schedule.getAlarmTime() <= AlarmThread.getInstance().getNextDay().getTimeInMillis() + Constant.loadTermForQueue) super.put(schedule);
    }

    @Override
    public boolean offer(Schedule schedule) {
        return schedule.getAlarmTime() > System.currentTimeMillis() &&
                schedule.getAlarmTime() <= AlarmThread.getInstance().getNextDay().getTimeInMillis() + Constant.loadTermForQueue &&
                super.offer(schedule);
    }
}
