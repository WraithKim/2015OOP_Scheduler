package schedule;

import javafx.beans.property.SimpleStringProperty;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Lumin on 2015-12-03.
 *
 * 과제 클래스
 */

public class Homework extends Schedule implements Serializable {
    private static final long serialVersionUID = 20151207L;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd");
    private transient SimpleStringProperty dateProperty;

    public Homework(String name, Calendar dueDate) {
        super(name, dueDate, Priority.NOTICED);
        dateProperty = new SimpleStringProperty();
        dateProperty.set(dateFormat.format(getDueDate().getTime()));
    }

    //테이블 뷰가 호출하는 메소드이기 때문에 절대로 지워서는 안됨
    @SuppressWarnings("unused")
    public SimpleStringProperty datePropertyProperty() {
        if(dateProperty == null){
            dateProperty = new SimpleStringProperty();
            dateProperty.set(dateFormat.format(getDueDate().getTime()));
        }
        return dateProperty;
    }
}
