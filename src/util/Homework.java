package util;

import schedule.Priority;
import schedule.Schedule;

import java.util.Calendar;

/**
 * Created by Lumin on 2015-12-03.
 */
public class Homework extends Schedule {
    private int totalRelatedStudent;
    private int totalSummitStudent;
    private Calendar homeworkStartCalendar;

    public Homework(String name) {
        super(name);
        super.setPriority(Priority.NOTICED);
        this.totalRelatedStudent = 0;
        this.totalSummitStudent = 0;
    }

    public Calendar getHomeworkStartCalendar() {
        return this.homeworkStartCalendar;
    }

    public void setHomeworkStartCalendar(Calendar homeworkStartCalendar) {
        this.homeworkStartCalendar = homeworkStartCalendar;
    }

    public int getTotalRelatedStudent() {
        return this.totalRelatedStudent;
    }

    public void setTotalRelatedStudent(int totalRelatedStudent) {
        this.totalRelatedStudent = totalRelatedStudent;
    }

    public int getTotalSummitStudent() {
        return this.totalSummitStudent;
    }

    public void setTotalSummitStudent(int totalSummitStudent) {
        this.totalSummitStudent = totalSummitStudent;
    }
}
