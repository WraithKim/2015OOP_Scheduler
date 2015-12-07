package schedule;

import javafx.beans.property.SimpleStringProperty;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Donghwan on 11/28/2015.
 *
 * 스케쥴 하나에 관한 클래스
 */
public class Schedule implements Serializable{
    private static final long serialVersionUID = 20151207L;
    private static final SimpleDateFormat timeForm = new SimpleDateFormat("HH:mm");

    private static final long A_DAY_AS_TIME = 86400000L;
    private static final long THREE_DAY_AS_TIME = 259200000L;
    private static final long A_WEEK_AS_TIME = 604800000L;

    //for TableView
    private transient SimpleStringProperty nameProperty;
    private transient SimpleStringProperty timeProperty;
    private transient SimpleStringProperty priorityProperty;

    //real attributes
    private final String name;
    private String description;
    private Priority priority;
    private final Calendar dueDate;


    public Schedule(String name, Calendar dueDate, Priority priority){
        this.dueDate = new GregorianCalendar();
        this.name = name;
        nameProperty = new SimpleStringProperty();
        timeProperty = new SimpleStringProperty();
        priorityProperty = new SimpleStringProperty();
        nameProperty.set(name);
        setDueDate(dueDate);
        timeForm.setCalendar(dueDate);
        setPriority(priority);
    }

    public String getName() {
        return name;
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

    public Calendar getDueDate() {
        return dueDate;
    }

    public long getAlarmTime(){
        switch(priority){
            case NONE: return dueDate.getTimeInMillis() - A_DAY_AS_TIME;
            case NOTICED: return dueDate.getTimeInMillis() - THREE_DAY_AS_TIME;
            case URGENT: return dueDate.getTimeInMillis() - A_WEEK_AS_TIME;
            default: return 0L;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Schedule schedule = (Schedule) o;

        if (!getName().equals(schedule.getName())) return false;
        if (getDescription() != null ? !getDescription().equals(schedule.getDescription()) : schedule.getDescription() != null)
            return false;
        if (getPriority() != schedule.getPriority()) return false;
        return !(getDueDate() != null ? !getDueDate().equals(schedule.getDueDate()) : schedule.getDueDate() != null);

    }

    @Override
    public int hashCode() {
        int result = getName().hashCode();
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + (getPriority() != null ? getPriority().hashCode() : 0);
        result = 31 * result + (getDueDate() != null ? getDueDate().hashCode() : 0);
        return result;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
        if(priorityProperty == null) priorityProperty = new SimpleStringProperty();
        priorityProperty.set(priority.toString());
    }

    public void setDueDate(Calendar dueDate) {
        this.dueDate.setTime(dueDate.getTime());
        if(timeProperty == null) timeProperty = new SimpleStringProperty();
        this.timeProperty.set(timeForm.format(this.dueDate.getTime()));
    }

    @SuppressWarnings("unused")
    public SimpleStringProperty namePropertyProperty() {
        if(nameProperty == null){
            nameProperty = new SimpleStringProperty();
            nameProperty.set(name);
        }
        return nameProperty;
    }


    @SuppressWarnings("unused")
    public SimpleStringProperty timePropertyProperty() {
        if(timeProperty == null){
            timeProperty = new SimpleStringProperty();
            this.timeProperty.set(timeForm.format(this.dueDate.getTime()));
        }
        return timeProperty;
    }

    @SuppressWarnings("unused")
    public SimpleStringProperty priorityPropertyProperty() {
        if(priorityProperty == null){
            priorityProperty = new SimpleStringProperty();
            priorityProperty.set(priority.toString());
        }
        return priorityProperty;
    }
}
