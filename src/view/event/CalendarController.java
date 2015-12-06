package view.event;

import extfx.scene.control.CalendarView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import network.PortalHttpRequest;
import schedule.Schedule;
import util.*;
import view.stageBuilder.DayScheduleListStageBuilder;
import view.stageBuilder.HomeworkListStageBuilder;
import view.stageBuilder.SettingStageBuilder;

import java.io.IOException;
import java.util.*;

/**
 * Created by CAUCSE on 2015-12-03.
 *
 * 달력 창에 달린 이벤트 리스너
 */
public class CalendarController {
    @FXML
    private CalendarView calendarView;

    @FXML
    private Button settingButton;

    @FXML
    private Button detailButton;

    @FXML
    private Button homeworkButton;

    @FXML
    protected void handleDetailButton(ActionEvent event) throws Exception{
        detailButton.setDisable(true);
        if((calendarView.getSelectedDate()) != null){
            // 창 생성
            Stage stage = DayScheduleListStageBuilder.getInstance().newDayScheduleList(calendarView.getSelectedDate());
            stage.show();
        }
        detailButton.setDisable(false);
    }

    @FXML
    protected void handleSettingButtonAction(ActionEvent event) throws Exception{
        Stage settingView = SettingStageBuilder.getInstance().defaultSettingViewStage();
        if(settingView != null) settingView.show();
    }

    @FXML
    protected void handleHomeworkButton(ActionEvent event) throws Exception{

        homeworkButton.setDisable(true);
        // 창 생성
        Stage stage = HomeworkListStageBuilder.getInstance().newHomeworkListViewStage();
        if(stage != null) stage.show();
        homeworkButton.setDisable(false);
    }
}
