package view.event;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import network.PortalHttpRequest;
import schedule.Schedule;
import util.AlarmQueue;
import util.Homework;
import util.PortalXmlParser;
import util.SharedPreference;

import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * Created by Donghwan on 12/6/2015.
 */
public class HomeworkListController {
    @FXML
    private Button syncButton;

    @FXML
    protected void handleSyncButtonAction(ActionEvent event){
        PortalXmlParser portalParser = new PortalXmlParser();
        System.out.println("Sync Button :: loaded Student ID : " + SharedPreference.savedStudentID);
        if (SharedPreference.savedStudentID.isEmpty()) {
            System.err.println("먼저 학번 설정을 해주세요!");
            return;
        }

        syncButton.setDisable(true);
        try {
            String lectureIDXmlInfo = PortalHttpRequest.getHomeworkLectureIDList(SharedPreference.savedStudentID);
            Set<Integer> lectureIDSet = portalParser.parseHomeworkLectureIDList(lectureIDXmlInfo);

            System.out.println("Sync Button :: lectureIDList : " + lectureIDXmlInfo);
            System.out.println("Before Homework List Length : " + SharedPreference.homeworkList.size());
            System.out.println("Before AlarmQueue Length : " + AlarmQueue.getInstance().size());

            SharedPreference.homeworkList.stream().filter(homework -> AlarmQueue.getInstance().contains(homework)).forEach(homework -> AlarmQueue.getInstance().remove(homework));
            SharedPreference.homeworkList.clear();

            System.out.println("After Homework List Length : " + SharedPreference.homeworkList.size());
            System.out.println("After AlarmQueue Length : " + AlarmQueue.getInstance().size());

            for (Integer lectureID : lectureIDSet) {
                String homeworkXmlInfo = PortalHttpRequest.getHomeworkList(SharedPreference.savedStudentID, lectureID);
                System.out.println("Sync Button :: homeworkXmlInfo : " + homeworkXmlInfo);
                List<Schedule> entityHomeworkList = portalParser.parseHomeworkList(homeworkXmlInfo);

                for (Schedule schedule : entityHomeworkList) {
                    SharedPreference.homeworkList.add((Homework) schedule);
                    AlarmQueue.getInstance().add(schedule);
                }
            }
            // TODO : 1. 과제리스트 객체에도 저장해야 하고, 알람 큐에도 등록해야함
            //        2. 기존에 과제가 있었다면 과제리스트는 비운 다음 저장해야 하고, 알람 큐에도 기존 과제를 지워야 함

        } catch (IOException e) {
            System.err.println("Couldn't get homework list. Please check your internet connection or something else");
        } finally {
            System.out.println("Finally Homework List Length : " + SharedPreference.homeworkList.size());
            System.out.println("Finally AlarmQueue Length : " + AlarmQueue.getInstance().size());

            syncButton.setDisable(false);
        }
    }
}
