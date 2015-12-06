package view.event;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import schedule.Schedule;
import util.AlarmQueue;
import util.FileManager;
import util.SharedPreference;
import util.Homework;
import view.stageBuilder.ScheduleEditorStageBuilder;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Created by CAUCSE on 2015-12-03.
 *
 * 하루 일정 창에 달린 이벤트 리스너
 */
public class DayScheduleListController implements Initializable{
    @FXML
    private Label dateLabel;

    @FXML
    private TableView<Schedule> scheduleTableView;

    @FXML
    private TableColumn timeColumn;

    @FXML
    private Button addButton;

    @FXML
    private Button editButton;

    @FXML
    private Button deleteButton;

    private Date curDate;
    private ArrayList<Schedule> scheduleList;

    // 현재 리스트를 일정에 저장함
    // 지금은 창 종료 이벤트가 호출함
    @SuppressWarnings("unchecked")
    public boolean saveList(){
        try {
            boolean ret = FileManager.getInstance().writeScheduleFile(scheduleList);
            if(!ret) System.err.println("something wrong during saving current list");
            return ret;
        }catch(IOException ioe){
            System.err.println("Something wrong during saving your schedule list");
            return false;
        }
    }

    @FXML
    protected void handleAddButtonAction(ActionEvent event) throws Exception{
        addButton.setDisable(true);
        SharedPreference.editMode = false;
        SharedPreference.editingDate = curDate;
        SharedPreference.editingScheduleList = scheduleTableView.getItems();
        Stage stage = ScheduleEditorStageBuilder.getInstance().newScheduleEditor();
        stage.show();
        addButton.setDisable(false);
    }

    @FXML
    protected void handleEditButtonAction(ActionEvent event) throws Exception{
        editButton.setDisable(true);
        Schedule focusedItem;
        if((focusedItem = scheduleTableView.getFocusModel().getFocusedItem()) != null &&
                !(focusedItem instanceof Homework)){
            SharedPreference.editMode = true;
            SharedPreference.editingDate = curDate;
            SharedPreference.editingSchedule = focusedItem;
            SharedPreference.editingScheduleList = scheduleTableView.getItems();
            Stage stage = ScheduleEditorStageBuilder.getInstance().newScheduleEditor();
            stage.show();
        }
        editButton.setDisable(false);
    }

    @FXML
    protected void handleDeleteButtonAction(ActionEvent event){
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

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd");
        curDate = SharedPreference.curDate;
        dateLabel.setText(dateFormat.format(curDate));
        scheduleList = SharedPreference.curScheduleList;
        scheduleTableView.setItems(FXCollections.observableArrayList(scheduleList));
    }
}
