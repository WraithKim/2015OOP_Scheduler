package schedule;

import java.util.ArrayList;

/**
 * Created by Donghwan on 11/29/2015.
 *
 * 하루 일정을 모두 모아놓은 클래스
 */
public class DaySchedule extends ArrayList<Schedule>{
    private int day;

    public DaySchedule(int day) throws InvalidTimeException{
        setDay(day);
    }

    private void setDay(int day) throws InvalidTimeException{
        if(day < 1 || day > 31) throw new InvalidTimeException();
        else this.day = day;
    }

    public int getDay() {
        return day;
    }
}
