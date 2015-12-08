package view.event;

import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import extfx.scene.control.CalendarView;
import extfx.scene.control.DateCell;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import view.stageBuilder.DayScheduleListStageBuilder;
import view.stageBuilder.HomeworkListStageBuilder;
import view.stageBuilder.SettingStageBuilder;


/**
 * Created by Donghwan on 2015-12-03.
 *
 * 달력 창에 달린 이벤트 리스너
 */
public class CalendarController implements Initializable {

	@FXML
	private BorderPane rootPane;
	
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
        // 창 생성
        Stage stage = HomeworkListStageBuilder.getInstance().newHomeworkListViewStage();
        if(stage != null) stage.show();
        homeworkButton.setDisable(false);
    }

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		
		System.out.println(this.calendarView.getId());
		
		this.calendarView.selectedDateProperty().addListener((Observable observable)->{
		        if((calendarView.getSelectedDate()) != null){
		            // 창 생성
		            Stage stage = null;
                    stage = DayScheduleListStageBuilder.getInstance().newDayScheduleList(calendarView.getSelectedDate());
		            if(stage != null) stage.show();
		        }
		});
		
		this.calendarView.setDayCellFactory(new Callback<CalendarView, DateCell>() {
			
			@Override
			public DateCell call(CalendarView arg0) {
				
				DateCell dc = new DateCell(){
					@Override
			          protected void updateItem(Date item, boolean empty) {
			             super.updateItem(item, empty);
			             
			             Calendar c = Calendar.getInstance();
			             c.setTime(item);
			             
			             if (c.get(Calendar.DAY_OF_WEEK) == 1) {
			            	 getStyleClass().addAll("sunday");
			             }
			             
			             if (c.get(Calendar.DAY_OF_WEEK) == 7) {
			            	 getStyleClass().addAll("saturday");
			             }
			             
			          }
				};
				
				return dc;
			}
		});
		
		
		rootPane.setStyle("-fx-background:transparent;");
        
	}
	
}
