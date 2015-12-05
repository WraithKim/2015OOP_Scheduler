package view.event;

import extfx.scene.control.CalendarView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import network.PortalHttpRequest;
import schedule.Schedule;
import util.Constant;
import util.PortalXmlParser;
import view.stageBuilder.SettingStageBuilder;

import java.io.IOException;
import java.util.*;

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

    private PortalXmlParser portalParser = new PortalXmlParser();

    @FXML
    protected void handleDetailButton(ActionEvent event) throws Exception{
        Date selectedDate = null;
        if((selectedDate = calendarView.getSelectedDate()) != null){
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(calendarView.getSelectedDate());
            //selectedDate: 현재 선택한 날짜
            //System.out.println("date"+calendar.get(Calendar.YEAR)+calendar.get(Calendar.MONTH)+calendar.get(Calendar.DAY_OF_MONTH));

            //TODO 새 스케쥴리스트 창을 위한 날짜 문자열과 스케쥴리스트 객체를 만들어서 새 창에 넘겨줘야 함

            Parent root = FXMLLoader.load(getClass().getResource(Constant.DayScheduleListView));
            Scene scene = new Scene(root);
            Stage settingView = new Stage();
            settingView.setTitle("Schedule List");
            settingView.setScene(scene);
            settingView.setResizable(false);
            settingView.show();
        }
    }

    @FXML
    protected void handleSettingButtonAction(ActionEvent event) throws Exception{
        Stage settingView = SettingStageBuilder.getInstance().defaultSettingViewStage();
        if(settingView != null) settingView.show();
    }

    @FXML
    protected void handleSyncButtonAction(ActionEvent event){
        System.out.println("Sync Button :: loaded Student ID : " + Constant.savedStudentID);
        if (Constant.savedStudentID.isEmpty()) {
            System.err.println("먼저 학번 설정을 해주세요!");
            return;
        }

        try {
            String lectureIDXmlInfo = PortalHttpRequest.getHomeworkLectureIDList(Constant.savedStudentID);
            System.out.println("Sync Button :: lectureIDList : " + lectureIDXmlInfo);
            Set<Integer> lectureIDSet = portalParser.parseHomeworkLectureIDList(lectureIDXmlInfo);
            List<List<Schedule>> totalHomeworkList = new ArrayList<>();

            for (Integer lectureID : lectureIDSet) {
                String homeworkXmlInfo = PortalHttpRequest.getHomeworkList(Constant.savedStudentID, lectureID);
                System.out.println("Sync Button :: homeworkXmlInfo : " + homeworkXmlInfo);
                List<Schedule> entityHomeworkList = portalParser.parseHomeworkList(homeworkXmlInfo);
                totalHomeworkList.add(entityHomeworkList);
            }
            // TODO : Save in
        } catch (IOException e) {
            // TODO : How Exception control?
        }
    }
}
