package view.event;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import schedule.Schedule;
import util.Constant;

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
    protected void handleAddButtonAction(ActionEvent event){
        //예제 코드 - 선택된 스케쥴 복붙하기
        Schedule focusedItem;
        if((focusedItem = scheduleTableView.getFocusModel().getFocusedItem()) != null){

        }
        // TODO 스케쥴 편집창을 생성하면서 해당 스케쥴을 편집창에 넘겨줌
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
        dateLabel.setText(dateFormat.format(Constant.curDate));
        scheduleTableView.setItems(FXCollections.observableArrayList(Constant.curScheduleList));
    }
}
