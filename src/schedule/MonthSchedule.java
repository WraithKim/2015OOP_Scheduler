package schedule;

import java.util.ArrayList;

/**
 * Created by Donghwan on 11/29/2015.
 *
 * 한 달 일정을 관리하는 클래스
 */
public class MonthSchedule extends ArrayList<DaySchedule> {
    private int year;
    private Month month;

    private static final int MIN_YEAR = 1970;

    public MonthSchedule(int year, Month month) throws InvalidTimeException{
        setYear(year);
        this.month = month;
    }

    private void setYear(int year) throws InvalidTimeException{
        if(year < MIN_YEAR) throw new InvalidTimeException();
        else this.year = year;
    }

    public int getYear() {
        return year;
    }

    public Month getMonth() {
        return month;
    }
}

enum Month{
    JAN, FEB, MAR, APR, MAY, JUN, JUL, AUG, SEP, OCT, NOV, DEC
}