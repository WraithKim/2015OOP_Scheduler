package schedule;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Donghwan on 11/29/2015.
 *
 * 스케쥴러 프로그램
 */
public class Scheduler {

    private MonthSchedule monthSchedule;

    public Scheduler(){
        Calendar cur = GregorianCalendar.getInstance();
        loadMonthSchedule(cur.get(Calendar.YEAR), cur.get(Calendar.MONTH));
    }

    public void loadMonthSchedule(int year, int month){
        // 여기서 해당 년, 해당 월의 일정리스트를 불러와서 monthSchedule에 대입해야 함.
    }

    public MonthSchedule getMonthSchedule(){
        return monthSchedule;
    }

    public void saveMonthSchedule(){
        // 현재 Scheduler가 가지고 있는 월의 일정리스트를 파일로 저장

        // 용도
        // 변경되었든 변경되지 않았든 다른 월의 일정리스트를 보기 전에
        // 이 메소드를 호출해서 현재 일정리스트를 전부 파일로 백업해야함.
    }
}
