package view.event;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import schedule.Schedule;
import util.AlarmQueue;
import util.Constant;
import util.Homework;
import view.stageBuilder.ScheduleEditorStageBuilder;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

/**
 * Created by CAUCSE on 2015-12-03.
 *
 * 하루 일정 창에 달린 이벤트 리스너
 */
public class DayScheduleListControl implements Initializable{
    @FXML
    private Label dateLabel;

    @FXML
    private TableView<Schedule> scheduleTableView;

    @FXML
    private TableColumn timeColumn;

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    protected void handleAddButtonAction(ActionEvent event) throws Exception{
        addButton.setDisable(true);
        Constant.editMode = true;
        Stage stage = ScheduleEditorStageBuilder.getInstance().newScheduleEditor();
        stage.show();
        addButton.setDisable(false);
    }

    @FXML
    protected void handleEditButtonAction(ActionEvent event) throws Exception{
        //editButton.setDisable(true);
        Schedule focusedItem;
        if((focusedItem = scheduleTableView.getFocusModel().getFocusedItem()) != null &&
                !(focusedItem instanceof Homework)){
            Constant.editingSchedule = focusedItem;
        }
        Stage stage = ScheduleEditorStageBuilder.getInstance().newScheduleEditor();
        stage.show();
        //editButton.setDisable(false);
    }

    @FXML
    protected void handleDeleteButtonAction(ActionEvent event){
        deleteButton.setDisable(true);
        Schedule focusedItem;
        if((focusedItem = scheduleTableView.getFocusModel().getFocusedItem()) != null){
            scheduleTableView.getItems().remove(focusedItem);
        }
        AlarmQueue.getInstance().remove(focusedItem);
        deleteButton.setDisable(false);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void initialize(URL location, ResourceBundle resources) {
        // GUI 창을 띄우기 직전에 해야할 작업(일종의 GUI 창 생성자)
        scheduleTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd");
        dateLabel.setText(dateFormat.format(Constant.curDate));
        scheduleTableView.setItems(FXCollections.observableArrayList(Constant.curScheduleList));
    }
}
