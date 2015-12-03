package view.event;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import schedule.Schedule;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by CAUCSE on 2015-12-03.
 *
 * 하루 일정 창에 달린 이벤트 리스너
 */
public class DayScheduleListControl implements Initializable{

    @FXML
    ListView<Schedule> scheduleList;

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    protected void handleAddButtonAction(ActionEvent event){

    }

    @FXML
    protected void handleDeleteButtonAction(ActionEvent event){

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // GUI 창을 띄우기 직전에 해야할 작업(일종의 GUI 창 생성자)
        // 예시는 스케쥴 편집 창에서 시간 콤보박스를 만드는 코드를 보세요

    }
}
