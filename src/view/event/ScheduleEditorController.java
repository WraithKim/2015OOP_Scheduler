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

import java.net.URL;
import java.util.*;

/**
 * Created by CAUCSE on 2015-12-03.
 *
 * 일정 편집 창에 달린 이벤트 리스너
 */
public class ScheduleEditorController implements Initializable{

    private boolean editMode;
    private Date currentDate;
    private Schedule editingSchedule;
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

    public void setEditableView(Schedule editingSchedule) {
        this.editMode = true;
        this.editingSchedule = editingSchedule;
        AlarmQueue.getInstance().remove(editingSchedule);
        titleTextField.setDisable(true);
        titleTextField.setText(editingSchedule.getName());
        switch(editingSchedule.getPriority()){
            case NONE: priorityNone.setSelected(true); break;
            case NOTICED: priorityNoticed.setSelected(true); break;
            case URGENT: priorityUrgent.setSelected(true); break;
        }
        hourComboBox.getSelectionModel().select(editingSchedule.getDueDate().get(Calendar.HOUR_OF_DAY));
        minuteComboBox.getSelectionModel().select(editingSchedule.getDueDate().get(Calendar.MINUTE));
        descriptionTextArea.setText(editingSchedule.getDescription());
    }

    public void setAddableView(Date currentDate, ObservableList<Schedule> originDaySchedule){
        this.editMode = false;
        this.currentDate = currentDate;
        this.originDaySchedule = originDaySchedule;
    }

    @FXML
    protected void handleSaveButtonAction(@SuppressWarnings("UnusedParameters") ActionEvent event){
        if(titleTextField.getLength() == 0) return;
        scheduleSaveButton.setDisable(true);
        AlarmQueue alarmQueue = AlarmQueue.getInstance();
        Priority priority = Priority.NONE;
        if(priorityUrgent.isSelected()) priority = Priority.URGENT;
        else if(priorityNoticed.isSelected()) priority = Priority.NOTICED;

        if(editMode){
            alarmQueue.remove(editingSchedule);
            editingSchedule.setPriority(priority);
            Calendar newDueDate = editingSchedule.getDueDate();
            newDueDate.set(Calendar.HOUR_OF_DAY, hourComboBox.getSelectionModel().getSelectedIndex());
            newDueDate.set(Calendar.MINUTE, minuteComboBox.getSelectionModel().getSelectedIndex());
            editingSchedule.setDueDate(newDueDate);
            editingSchedule.setDescription(descriptionTextArea.getText());
            //System.out.println("new Schedule alarm time: "+editingSchedule.getAlarmTime());

        }else{
            Calendar dueDate = new GregorianCalendar();
            dueDate.setTime(currentDate);
            dueDate.set(Calendar.HOUR_OF_DAY, hourComboBox.getSelectionModel().getSelectedIndex());
            dueDate.set(Calendar.MINUTE, minuteComboBox.getSelectionModel().getSelectedIndex());
            editingSchedule = new Schedule(titleTextField.getText(), dueDate, priority);
            editingSchedule.setDescription(descriptionTextArea.getText());
            //System.out.println("new Schedule alarm time: "+editingSchedule.getAlarmTime());
            originDaySchedule.add(editingSchedule);

            editMode = true;
        }
        alarmQueue.add(editingSchedule);
        scheduleSaveButton.setDisable(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<Integer> hourList = new ArrayList<>(24);
        for(int i = 0; i < 24; i++){
            hourList.add(i);
        }
        hourComboBox.setItems(FXCollections.observableArrayList(hourList));
        hourComboBox.getSelectionModel().select(0);
        ArrayList<Integer> minuteList = new ArrayList<>(60);
        for(int i = 0; i < 60; i++){
            minuteList.add(i);
        }
        minuteComboBox.setItems((FXCollections.observableArrayList(minuteList)));
        minuteComboBox.getSelectionModel().select(0);
    }

}
