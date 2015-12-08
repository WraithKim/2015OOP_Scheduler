package view.event;

import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import extfx.scene.control.CalendarView;
import extfx.scene.control.DateCell;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.controlsfx.dialog.ExceptionDialog;
import view.stageBuilder.DayScheduleListStageBuilder;
import view.stageBuilder.SettingStageBuilder;



/**
 * Created by Donghwan on 2015-12-03.
 *
 * 달력 창에 달린 이벤트 리스너
 */
public class CalendarController extends AbstactNotificationController implements Initializable {
	
	@FXML
	private BorderPane rootPane;
	
	@FXML
    private CalendarView calendarView;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		this.calendarView.selectedDateProperty().addListener((Observable observable)->{
		        if((calendarView.getSelectedDate()) != null){
		            // 창 생성
		            Stage stage = null;
					try {
						stage = DayScheduleListStageBuilder.getInstance().newDayScheduleList(calendarView.getSelectedDate());
						if (stage != null) stage.show();
					}catch(IOException ioe){
						ExceptionDialog exceptionDialog = new ExceptionDialog(ioe);
						exceptionDialog.show();
					}
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
