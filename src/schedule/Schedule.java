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
    private static final long serialVersionUID = 20151204L;
    private static final SimpleDateFormat dateForm;

    private static final long A_DAY_AS_TIME = 86400L;
    private static final long THREE_DAY_AS_TIME = 259200L;
    private static final long A_WEEK_AS_TIME = 604800L;

    //for TableView
    private transient final SimpleStringProperty nameProperty;
    private transient final SimpleStringProperty timeProperty;
    private transient final SimpleStringProperty priorityProperty;

    //real attributes
    private final String name;
    private String description;
    private Priority priority;
    private Calendar dueDate;

    static{
        dateForm = new SimpleDateFormat("HH:mm");
    }

    {
        nameProperty = new SimpleStringProperty();
        timeProperty = new SimpleStringProperty();
        priorityProperty = new SimpleStringProperty();

        this.dueDate = new GregorianCalendar();
    }

    public Schedule(String name, Calendar dueDate, Priority priority){
        this.name = name;
        nameProperty.set(name);
        setDueDate(dueDate);
        dateForm.setCalendar(dueDate);
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

    public void setPriority(Priority priority) {
        this.priority = priority;
        priorityProperty.set(priority.toString());
    }

    public Calendar getDueDate() {
        return dueDate;
    }

    public void setDueDate(Calendar dueDate) {
        this.dueDate.setTime(dueDate.getTime());
        this.timeProperty.set(dateForm.format(this.dueDate.getTime()));
    }

    public String getNameProperty() {
        return nameProperty.get();
    }

    public SimpleStringProperty namePropertyProperty() {
        return nameProperty;
    }

    public String getTimeProperty() {
        return timeProperty.get();
    }

    public SimpleStringProperty timePropertyProperty() {
        return timeProperty;
    }

    public String getPriorityProperty() {
        return priorityProperty.get();
    }

    public SimpleStringProperty priorityPropertyProperty() {
        return priorityProperty;
    }

    public long getAlarmTime(){
        switch(priority){
            case NONE: return dueDate.getTimeInMillis() - A_DAY_AS_TIME;
            case NOTICED: return dueDate.getTimeInMillis() - THREE_DAY_AS_TIME;
            case URGENT: return dueDate.getTimeInMillis() - A_WEEK_AS_TIME;
            default: return 0L;
        }
    }
}
