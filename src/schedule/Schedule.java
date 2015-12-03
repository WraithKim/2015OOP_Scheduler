package schedule;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Donghwan on 11/28/2015.
 *
 * 스케쥴 하나에 관한 클래스
 */
public class Schedule implements Serializable{
    public class Alarm implements Serializable, Comparable<Alarm>{
        private static final long serialVersionUID = 20151202L;
        private static final long A_DAY_AS_TIME = 86400L;
        private static final long THREE_DAY_AS_TIME = 259200L;
        private static final long A_WEEK_AS_TIME = 604800L;

        private boolean enabled;

        private Alarm(){
            this.enabled = false;
        }

        @Override
        public int compareTo(Alarm o) {
            return Long.compare(this.getAlarmTime(), o.getAlarmTime());
        }

        public void setEnabled(){
            this.enabled = true;
        }

        public void setDisabled(){
            this.enabled = false;
        }

        public boolean isEnabled(){
            return this.enabled;
        }

        public long getAlarmTime(){
            if(priority == Priority.NONE) return dueDate.getTimeInMillis() - A_DAY_AS_TIME;
            if(priority == Priority.NOTICED) return dueDate.getTimeInMillis() - THREE_DAY_AS_TIME;
            if(priority == Priority.URGENT) return dueDate.getTimeInMillis() - A_WEEK_AS_TIME;
            else return 0L;
        }
    }

    private static final long serialVersionUID = 20151201L;

    private String name;
    private String description;
    private Priority priority;
    private Calendar dueDate;
    private Alarm alarm;

    {
        priority = Priority.NONE;
        alarm = new Alarm();
    }

    public Schedule(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Alarm getAlarm() {
        return alarm;
    }

    public Calendar getDueDate() {
        return dueDate;
    }

    public void setDueDate(Calendar dueDate) {
        this.dueDate = dueDate;
    }
}
