package view.event;

import java.awt.Event;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import com.sun.corba.se.spi.orbutil.threadpool.Work;
import com.sun.javafx.scene.control.behavior.DateCellBehavior;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;

import extfx.scene.control.CalendarView;
import extfx.scene.control.DateCell;
import extfx.util.ClickRepeater;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import util.Constant;
import view.stageBuilder.DayScheduleListStageBuilder;
import view.stageBuilder.HomeworkListStageBuilder;
import view.stageBuilder.SettingStageBuilder;


/**
 * Created by Donghwan on 2015-12-03.
 *
 * 달력 창에 달린 이벤트 리스너
 */
public class CalendarController implements Initializable {
    
	private int test = 0;
	
	@FXML
    private CalendarView calendarView;

    @SuppressWarnings("unused")
    @FXML
    private Button settingButton;

    @FXML
    private Button detailButton;

    @FXML
    private Button homeworkButton;

    @FXML
    protected void handleDetailButton(@SuppressWarnings("UnusedParameters") ActionEvent event) throws Exception{
        detailButton.setDisable(true);
        if((calendarView.getSelectedDate()) != null){
            // 창 생성
            Stage stage = DayScheduleListStageBuilder.getInstance().newDayScheduleList(calendarView.getSelectedDate());
            if(stage != null) stage.show();
        }
        detailButton.setDisable(false);
        
    }

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
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		this.calendarView.selectedDateProperty().addListener(new InvalidationListener() {
			
			@Override
			public void invalidated(Observable arg0) {
				
				detailButton.setDisable(true);
				
		        if((calendarView.getSelectedDate()) != null){
		            // 창 생성
		            Stage stage = null;
		            
					try {
						stage = DayScheduleListStageBuilder.getInstance().newDayScheduleList(calendarView.getSelectedDate());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
		            if(stage != null) stage.show();
		        }
		        detailButton.setDisable(false);
				
			}
		});
        
	}
	
}
