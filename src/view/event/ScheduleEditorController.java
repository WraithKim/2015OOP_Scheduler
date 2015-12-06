package view.event;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import schedule.Priority;
import schedule.Schedule;
import util.AlarmQueue;
import util.SharedPreference;

import java.net.URL;
import java.util.*;

/**
 * Created by CAUCSE on 2015-12-03.
 *
 * 일정 편집 창에 달린 이벤트 리스너
 */
public class ScheduleEditorController implements Initializable{

    private boolean editMode;
    private Date curDate;
    private Schedule schedule;
    private ObservableList<Schedule> originDaySchedule;

    @FXML
    private TextField titleTextField;

    @FXML
    private ToggleGroup priorityToggleGroup;
    // 우선순위를 나타내는 라디오버튼을 묶어놓은 그룹

    @FXML
    private RadioButton priorityNone;

    @FXML
    private RadioButton priorityNoticed;

    @FXML
    private RadioButton priorityUrgent;

    @FXML
    private ComboBox<Integer> hourComboBox;

    @FXML
    private ComboBox<Integer> minuteComboBox;

    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private Button scheduleSaveButton;



    @FXML
    protected void handleSaveButtonAction(ActionEvent event){
        if(titleTextField.getLength() == 0) return;

        scheduleSaveButton.setDisable(true);
        if(!editMode){
            Priority priority = Priority.NONE;
            if(priorityUrgent.isSelected()) priority = Priority.URGENT;
            else if(priorityNoticed.isSelected()) priority = Priority.NOTICED;
            Calendar dueDate = new GregorianCalendar();
            dueDate.setTime(curDate);
            dueDate.set(Calendar.HOUR_OF_DAY, hourComboBox.getSelectionModel().getSelectedIndex());
            dueDate.set(Calendar.MINUTE, minuteComboBox.getSelectionModel().getSelectedIndex());
            schedule = new Schedule(titleTextField.getText(), dueDate, priority);
            //System.out.println("new Schedule alarm time: "+schedule.getAlarmTime());
            originDaySchedule.add(schedule);
            if(schedule.getAlarmTime() > System.currentTimeMillis()) AlarmQueue.getInstance().put(schedule);
            editMode = true;
        }else{
            AlarmQueue.getInstance().remove(schedule);
            Priority priority = Priority.NONE;
            if(priorityUrgent.isSelected()) priority = Priority.URGENT;
            else if(priorityNoticed.isSelected()) priority = Priority.NOTICED;
            schedule.setPriority(priority);
            Calendar newDueDate = schedule.getDueDate();
            newDueDate.set(Calendar.HOUR_OF_DAY, hourComboBox.getVisibleRowCount());
            newDueDate.set(Calendar.MINUTE, minuteComboBox.getVisibleRowCount());
            schedule.setDescription(descriptionTextArea.getText());
            //System.out.println("new Schedule alarm time: "+schedule.getAlarmTime());
            if(schedule.getAlarmTime() > System.currentTimeMillis()) AlarmQueue.getInstance().put(schedule);
        }
        scheduleSaveButton.setDisable(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LinkedList<Integer> hourList = new LinkedList<>();
        for(int i = 0; i < 24; i++){
            hourList.add(i);
        }
        hourComboBox.setItems(FXCollections.observableList(hourList));
        hourComboBox.getSelectionModel().select(0);
        LinkedList<Integer> minuteList = new LinkedList<>();
        for(int i = 0; i < 60; i++){
            minuteList.add(i);
        }
        minuteComboBox.setItems((FXCollections.observableArrayList(minuteList)));
        minuteComboBox.getSelectionModel().select(0);

        curDate = SharedPreference.editingDate;
        editMode = SharedPreference.editMode;
        schedule = SharedPreference.editingSchedule;
        originDaySchedule = SharedPreference.editingScheduleList;
        if(editMode) {
            titleTextField.setEditable(false);
            titleTextField.setText(schedule.getName());
            switch(schedule.getPriority()){
                case NONE: priorityNone.setSelected(true); break;
                case NOTICED: priorityNoticed.setSelected(true); break;
                case URGENT: priorityUrgent.setSelected(true); break;
            }
            hourComboBox.getSelectionModel().select(schedule.getDueDate().get(Calendar.HOUR_OF_DAY));
            minuteComboBox.getSelectionModel().select(schedule.getDueDate().get(Calendar.MINUTE));
            descriptionTextArea.setText(schedule.getDescription());
        }
    }

}
