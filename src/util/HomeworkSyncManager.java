package util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import network.PortalHttpRequest;
import schedule.Homework;
import view.event.AbstactNotificationController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by Donghwan on 12/8/2015.
 *
 * 과제 리스트의 동기화를 관리
 */
public class HomeworkSyncManager {
    private static HomeworkSyncManager ourInstance = new HomeworkSyncManager();
    private ObservableList<Homework> homeworkList = FXCollections.observableArrayList();

    public static HomeworkSyncManager getInstance(){ return ourInstance; }

    private HomeworkSyncManager(){}

    public ObservableList<Homework> getHomeworkList(){
        return homeworkList;
    }

    public boolean sync(AbstactNotificationController controller){
        PortalXmlParser portalParser = new PortalXmlParser();
        FileManager fileManager = FileManager.getInstance();
        AlarmQueue alarmQueue = AlarmQueue.getInstance();
        String savedStudentID;
        try {
            savedStudentID = fileManager.readStudentNumber();
        }catch(IOException ioe) {
            controller.printNotificationPane("Could't get student ID, Please save your ID at Setting");
            return false;
        }catch(ClassNotFoundException cnfe) {
            controller.printNotificationPane("Data has corrupted in Data directory\n" +
                    "Maybe your Scheduler version doesn't match with Schedule files.");
            return false;
        }
        //기존에 테이블에 저장된 리스트를 알람 큐에서 지우기
        homeworkList.forEach(alarmQueue::remove);
        homeworkList.clear();

        try { //포탈에서 과제 정보를 받아오기
            String lectureIDXmlInfo = PortalHttpRequest.getHomeworkLectureIDList(savedStudentID);
            Map<Integer, String> lectureMap = portalParser.parseHomeworkLectureIDList(lectureIDXmlInfo);
            for (Integer lectureID : lectureMap.keySet()) {
                String homeworkXmlInfo = PortalHttpRequest.getHomeworkList(savedStudentID, lectureID);
                List<Homework> remoteHomeworkList = portalParser.parseHomeworkList(homeworkXmlInfo);

                for (Homework homework : remoteHomeworkList) {
                    Homework renamedHomework = new Homework(lectureMap.get(lectureID) + "-" + homework.getName(), homework.getDueDate());
                    homeworkList.add(renamedHomework);
                    AlarmQueue.getInstance().add(renamedHomework);
                }
            }
            try{ //포탈에서 받아온 과제를 저장하기t
                fileManager.writeHomeworkFile(homeworkList);
            }catch (IOException ioe){
                controller.printNotificationPane("Couldn't save your homework, Please try again after deleting existing homework list");
                return false;
            }
            controller.printNotificationPane("Successfully load homework list");
            return true;
        }catch(IOException e) { // 포탈에서 과제를 받는 데 발생한 예외
            controller.printNotificationPane("Couldn't get homework list. Please check you are connected to internet\n" +
                    "Try to load homework list in local storage");
            try { // 로컬에 저장된 파일을 찾아서 리스트를 만듬\
                for (Homework homework : fileManager.readHomeworkFile()) {
                    homeworkList.add(homework);
                    AlarmQueue.getInstance().add(homework);
                }
                controller.printNotificationPane("Successfully load homework list");
                return true;
            }catch(IOException ioe){ // 로컬에도 저장된 리스트가 없을 때
                controller.printNotificationPane("Could't get homework list saved in local storage");

                return false;
            }catch(ClassNotFoundException cnfe){ // 직렬화 문제
                controller.printNotificationPane("Data has corrupted in Data directory\n" +
                        "Maybe your Scheduler version doesn't match with Schedule files.");
                return false;
            }
        }
    }
}
