package view.event;

import extfx.scene.control.CalendarView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import network.PortalHttpRequest;
import schedule.Schedule;
import util.SharedPreference;
import util.FileManager;
import util.PortalXmlParser;
import view.stageBuilder.DayScheduleListStageBuilder;
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
        detailButton.setDisable(true);
        Date selectedDate = null;
        if((selectedDate = calendarView.getSelectedDate()) != null){
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(calendarView.getSelectedDate());
            //selectedDate: 현재 선택한 날짜
            // TODO 지금은 로컬에 저장된 일정리스트만 보여줌.
            // 나중에 과제 리스트 중 해당 날짜인 것도 저 curScheduleList에 추가해서 넘겨줘야 함.
            SharedPreference.curDate = calendar.getTime();
            SharedPreference.curScheduleList = FileManager.getInstance().readScheduleFile(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.DAY_OF_MONTH));
            Stage stage = DayScheduleListStageBuilder.getInstance().newDayScheduleList();
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
    protected void handleSyncButtonAction(ActionEvent event){
        System.out.println("Sync Button :: loaded Student ID : " + SharedPreference.savedStudentID);
        if (SharedPreference.savedStudentID.isEmpty()) {
            System.err.println("먼저 학번 설정을 해주세요!");
            return;
        }

        syncButton.setDisable(true);
        try {
            String lectureIDXmlInfo = PortalHttpRequest.getHomeworkLectureIDList(SharedPreference.savedStudentID);
            System.out.println("Sync Button :: lectureIDList : " + lectureIDXmlInfo);
            Set<Integer> lectureIDSet = portalParser.parseHomeworkLectureIDList(lectureIDXmlInfo);
            List<List<Schedule>> totalHomeworkList = new ArrayList<>();

            for (Integer lectureID : lectureIDSet) {
                String homeworkXmlInfo = PortalHttpRequest.getHomeworkList(SharedPreference.savedStudentID, lectureID);
                System.out.println("Sync Button :: homeworkXmlInfo : " + homeworkXmlInfo);
                List<Schedule> entityHomeworkList = portalParser.parseHomeworkList(homeworkXmlInfo);
                totalHomeworkList.add(entityHomeworkList);
            }
            // TODO : 1. 과제리스트 객체에도 저장해야 하고, 알람 큐에도 등록해야함
            //        2. 기존에 과제가 있었다면 과제리스트는 비운 다음 저장해야 하고, 알람 큐에도 기존 과제를 지워야 함

        } catch (IOException e) {
            System.err.println("Couldn't get homework list. Please check your internet connection or something else");
        }finally {
            syncButton.setDisable(false);
        }
    }
}
