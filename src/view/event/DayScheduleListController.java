package view.event;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.controlsfx.control.NotificationPane;
import schedule.Schedule;
import util.AlarmQueue;
import util.FileManager;
import view.stageBuilder.ScheduleEditorStageBuilder;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Donghwan on 2015-12-03.
 *
 * 하루 일정 창에 달린 이벤트 리스너
 */
public class DayScheduleListController implements Initializable{
    @FXML
    private Label dateLabel;

    @FXML
    private TableView<Schedule> scheduleTableView;

    @SuppressWarnings("unused")
    @FXML
    private TableColumn timeColumn;

    @FXML
    private Button addButton;

    @FXML
    private Button editButton;

    @FXML
    private Button deleteButton;

    private Date currentDate;

    private NotificationPane notificationPane;

    public void setNotificationPane(NotificationPane notificationPane) {
        this.notificationPane = notificationPane;
    }

    // 현재 리스트를 일정에 저장함
    // 지금은 창 종료 이벤트가 호출함
    @SuppressWarnings("unchecked")
    public boolean saveList(){
        try {
            return FileManager.getInstance().writeScheduleFile(scheduleTableView.getItems().subList(0, scheduleTableView.getItems().size()));
        }catch(IOException ioe){
            System.err.println("Something wrong during saving your schedule list");
            return false;
        }
    }

    public void loadScheduleList(Date currentDate) throws ClassNotFoundException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd");
        this.currentDate = currentDate;
        dateLabel.setText(dateFormat.format(this.currentDate));
        Calendar dateToLoad = new GregorianCalendar();
        dateToLoad.setTime(this.currentDate);
        try {
            for (Schedule schedule : FileManager.getInstance().readScheduleFile(dateToLoad)){
                scheduleTableView.getItems().add(schedule);
            }
        }catch(IOException ioe){
            System.err.println("Couldn't load schedule files");
        }
    }

    @FXML
    protected void handleAddButtonAction(@SuppressWarnings("UnusedParameters") ActionEvent event) throws Exception{
        addButton.setDisable(true);
        Stage stage = ScheduleEditorStageBuilder.getInstance().newAddingScheduleEditor(currentDate, scheduleTableView.getItems());
        stage.show();
        addButton.setDisable(false);
    }

    @FXML
    protected void handleEditButtonAction(@SuppressWarnings("UnusedParameters") ActionEvent event) throws Exception{
        editButton.setDisable(true);
        Schedule focusedItem;
        if((focusedItem = scheduleTableView.getFocusModel().getFocusedItem()) != null){
            Stage stage = ScheduleEditorStageBuilder.getInstance().newEditingScheduleEditor(focusedItem);
            stage.show();
        }
        editButton.setDisable(false);
    }

    @FXML
    protected void handleDeleteButtonAction(@SuppressWarnings("UnusedParameters") ActionEvent event){
        deleteButton.setDisable(true);
        Schedule focusedItem;
        if((focusedItem = scheduleTableView.getFocusModel().getFocusedItem()) != null){
            scheduleTableView.getItems().remove(focusedItem);
            AlarmQueue.getInstance().remove(focusedItem);
        }
        deleteButton.setDisable(false);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void initialize(URL location, ResourceBundle resources) {
        // GUI 창을 띄우기 직전에 해야할 작업(일종의 GUI 창 생성자)
        scheduleTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        scheduleTableView.setItems(FXCollections.observableArrayList(new ArrayList<>()));
    }
}
