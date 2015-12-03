package util;

import schedule.Priority;
import schedule.Schedule;

import java.util.Calendar;

/**
 * Created by Lumin on 2015-12-03.
 */
public class HomeworkFrame extends Schedule {
    private int homeworkLectureID;

    public HomeworkFrame(String name, int homeworkLectureID) {
        super(name);
        super.setPriority(Priority.NOTICED);
        this.homeworkLectureID = homeworkLectureID;
    }

    public void setHomeworkLectureID(int homeworkLectureID) {
        this.homeworkLectureID = homeworkLectureID;
    }

    public int getHomeworkLectureID() {
        return this.homeworkLectureID;
    }
}
