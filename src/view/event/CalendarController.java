package view.event;

import java.net.URL;
import java.util.ResourceBundle;

import extfx.scene.control.CalendarView;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import util.HomeworkSyncManager;
import view.stageBuilder.DayScheduleListStageBuilder;
import view.stageBuilder.HomeworkListStageBuilder;
import view.stageBuilder.SettingStageBuilder;



/**
 * Created by Donghwan on 2015-12-03.
 *
 * 달력 창에 달린 이벤트 리스너
 */
public class CalendarController extends AbstactNotificationController implements Initializable {
	
	@FXML
    private CalendarView calendarView;

    @SuppressWarnings("unused")
    @FXML
    private Button settingButton;

    @FXML
    private Button homeworkButton;

    @FXML
    protected void handleSettingButtonAction(@SuppressWarnings("UnusedParameters") ActionEvent event) throws Exception{
        Stage settingView = SettingStageBuilder.getInstance().defaultSettingViewStage();
        if(settingView != null) settingView.show();
    }

    @FXML
    protected void handleHomeworkButton(@SuppressWarnings("UnusedParameters") ActionEvent event) throws Exception{
        homeworkButton.setDisable(true);
        HomeworkSyncManager.homeworkSyncManager.sync(this);
        // 창 생성
        Stage stage = HomeworkListStageBuilder.getInstance().newHomeworkListViewStage();
        if(stage != null) stage.show();
        homeworkButton.setDisable(false);
    }

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		
		this.calendarView.selectedDateProperty().addListener((Observable observable)->{
		        if((calendarView.getSelectedDate()) != null){
		            // 창 생성
		            Stage stage = null;
                    stage = DayScheduleListStageBuilder.getInstance().newDayScheduleList(calendarView.getSelectedDate());
		            if(stage != null) stage.show();
		        }
		});
        
	}
	
}
