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
    private static final long serialVersionUID = 20151206L;

    public Homework(String name, Calendar dueDate) {
        super(name, dueDate, Priority.NOTICED);
    }
}
