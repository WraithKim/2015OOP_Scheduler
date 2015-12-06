package util;

import schedule.Priority;
import schedule.Schedule;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by Lumin on 2015-12-03.
 *
 * 과제 클래스
 */

public class Homework extends Schedule implements Serializable {
    // TODO 과제는 수정 불가능
    private static final long serialVersionUID = 20151206L;


    private int totalRelatedStudent;
    private int totalSummitStudent;
    private Calendar homeworkStartCalendar;

    public Homework(String name, Calendar dueDate) {
        super(name, dueDate, Priority.NOTICED);
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
