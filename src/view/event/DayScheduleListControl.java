package view.event;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import schedule.DaySchedule;
import schedule.InvalidTimeException;
import schedule.Priority;
import schedule.Schedule;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by CAUCSE on 2015-12-03.
 *
 * 하루 일정 창에 달린 이벤트 리스너
 */
public class DayScheduleListControl implements Initializable{
    @FXML
    private Label dateLable;

    @FXML
    private TableView<Schedule> scheduleTableView;

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;



    @FXML
    protected void handleAddButtonAction(ActionEvent event){
        //예제 코드 - 선택된 스케쥴 복붙하기
        Schedule focusedItem;
        if((focusedItem = scheduleTableView.getFocusModel().getFocusedItem()) != null){
            scheduleTableView.getItems().add(new Schedule("test"+System.currentTimeMillis(), GregorianCalendar.getInstance(), Priority.NONE));
        }
        //===========================================================================
    }

    @FXML
    protected void handleDeleteButtonAction(ActionEvent event){

    }

    @Override
    @SuppressWarnings("unchecked")
    public void initialize(URL location, ResourceBundle resources) {
        // GUI 창을 띄우기 직전에 해야할 작업(일종의 GUI 창 생성자)
        scheduleTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd");
        dateLable.setText(dateFormat.format(resources.getObject("date")));
        scheduleTableView.setItems(FXCollections.observableArrayList((List<Schedule>)resources.getObject("scheduleList")));
    }
}
