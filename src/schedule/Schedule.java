package schedule;

import java.io.Serializable;

/**
 * Created by Donghwan on 11/28/2015.
 *
 * 스케쥴 하나에 관한 클래스
 */
enum Priority{
    NONE, NOTICED, URGENT; //오른쪽으로 갈 수록 중요도가 높아짐
}
public class Schedule implements Serializable{
    private static final long serialVersionUID = 20151201L;

    private String name;
    private String description;
    private Priority priority;
    private Alarm alarm;

    {
        priority = Priority.NONE;
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

    public void setAlarm(Alarm alarm) {
        this.alarm = alarm;
    }
}
