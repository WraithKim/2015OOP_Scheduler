package schedule;

import java.util.Date;

/**
 * Created by Donghwan on 11/28/2015.
 *
 * 알람에 대한 클래스
 */
public class Alarm {
    private boolean enabled;
    private Date dueDate;

    public Alarm(Date dueDate){
        this.dueDate = dueDate;
        this.enabled = true;
    }

    public static Alarm NormalAlarm(Date timeToBegin){
        return new Alarm(new Date(timeToBegin.getTime() - 3600));
    }

    public static Alarm ImportantAlarm(Date timeToBegin){
        return new Alarm(new Date(timeToBegin.getTime() - 7200));
    }

    public static Alarm UrgentAlarm(Date timeToBegin){
        return new Alarm(new Date(timeToBegin.getTime() - 10800));
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

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
}
