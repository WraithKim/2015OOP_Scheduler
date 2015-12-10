package view.event;

import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import extfx.scene.control.CalendarView;
import extfx.scene.control.DateCell;
import javafx.beans.Observable;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import org.controlsfx.dialog.ExceptionDialog;

import util.FileManager;
import view.stageBuilder.DayScheduleListStageBuilder;


/**
 * Created by Donghwan on 2015-12-03.
 *
 * 달력 창에 달린 이벤트 리스너
 */
public class CalendarController extends AbstactNotificationController implements Initializable {
	
	private Calendar c = Calendar.getInstance();
	private Calendar today = Calendar.getInstance();
	
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

				return new DateCell(){
					@Override
			          protected void updateItem(Date item, boolean empty) {
			             super.updateItem(item, empty);

			             c.setTime(item);
			             
			             getStyleClass().removeAll("today");
			             getStyleClass().removeAll("exist");
			             
			             if ( c.get(Calendar.YEAR) == today.get(Calendar.YEAR) && c.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR) ){
			            	 getStyleClass().addAll("today");
			             }
			             
			             if ( FileManager.containScheduleList(c) ){
			            	 getStyleClass().addAll("exist");
			             }
			             
			             if (c.get(Calendar.DAY_OF_WEEK) == 1) {
			            	 getStyleClass().addAll("sunday");
			             }

			             if (c.get(Calendar.DAY_OF_WEEK) == 7) {
			            	 getStyleClass().addAll("saturday");
			             }
			          }
				};
			}
		});
		
		rootPane.setStyle("-fx-background:transparent;");
        
	}
	
}
