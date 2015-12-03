package view.event;

import extfx.scene.control.CalendarView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by CAUCSE on 2015-12-03.
 *
 * 달력 창에 달린 이벤트 리스너
 */
public class CalendarControl {
    @FXML
    private CalendarView calendarView;

    @FXML
    private Button settingButton;

    @FXML
    private Button detailButton;

    @FXML
    private Button syncButton;

    @FXML
    protected void handleDetailButton(ActionEvent event){
        Date selectedDate = null;
        if((selectedDate = calendarView.getSelectedDate()) != null){
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(calendarView.getSelectedDate());
            //selectedDate: 현재 선택한 날짜
            //System.out.println("date"+calendar.get(Calendar.YEAR)+calendar.get(Calendar.MONTH)+calendar.get(Calendar.DAY_OF_MONTH));
            //여기에 해당 버튼이 해야 될 일
        }
    }

    @FXML
    protected void handleSettingButtonAction(ActionEvent event){

    }

    @FXML
    protected void handleSyncButtonAction(ActionEvent event){

    }
}
