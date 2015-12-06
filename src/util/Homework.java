package util;

import javafx.beans.property.SimpleStringProperty;
import schedule.Priority;
import schedule.Schedule;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Lumin on 2015-12-03.
 *
 * 과제 클래스
 */

public class Homework extends Schedule implements Serializable {
    private static final long serialVersionUID = 20151206L;
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd");

    private int totalRelatedStudent;
    private int totalSummitStudent;
    private Calendar homeworkStartCalendar;
    private transient SimpleStringProperty dateProperty;

    public Homework(String name, Calendar dueDate) {
        super(name, dueDate, Priority.NOTICED);
        this.totalRelatedStudent = 0;
        this.totalSummitStudent = 0;
        dateProperty = new SimpleStringProperty();
        dateProperty.set(dateFormat.format(getDueDate().getTime()));
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


    public SimpleStringProperty datePropertyProperty() {
        if(dateProperty == null){
            dateProperty = new SimpleStringProperty();
            dateProperty.set(dateFormat.format(getDueDate().getTime()));
        }
        return dateProperty;
    }
}
