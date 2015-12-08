package view.event;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import network.PortalHttpRequest;
import org.controlsfx.control.NotificationPane;
import schedule.Homework;
import util.*;

import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * Created by Donghwan on 12/6/2015.
 *
 * 과제 리스트 컨트롤러
 */
public class HomeworkListController implements Initializable{
    @FXML
    private TableView<Homework> homeworkTableView;

    @SuppressWarnings("unused")
    @FXML
    private TableColumn dateColumn;

    @SuppressWarnings("unused")
    @FXML
    private TableColumn timeColumn;

    @FXML
    private Button syncButton;

    private NotificationPane notificationPane;

    public void setNotificationPane(NotificationPane notificationPane) {
        this.notificationPane = notificationPane;
    }

    private void printNotificationPane(String string){
        if(notificationPane.isShowing()) {
            notificationPane.hide();
        }
        notificationPane.setText(string);
        notificationPane.show();
    }

    public boolean sync(){
        PortalXmlParser portalParser = new PortalXmlParser();
        FileManager fileManager = FileManager.getInstance();
        AlarmQueue alarmQueue = AlarmQueue.getInstance();
        String savedStudentID;
        try {
            savedStudentID = fileManager.readStudentNumber();
        }catch(IOException ioe) {
            printNotificationPane("Could't get student ID, Please save your ID at Setting");
            return false;
        }catch(ClassNotFoundException cnfe) {
            printNotificationPane("Data has corrupted in Data directory\n" +
                        "Maybe your Scheduler version doesn't match with Schedule files.");
            return false;
        }
        if(homeworkTableView.getItems() != null) { //기존에 테이블에 저장된 리스트를 알람 큐에서 지우기
            ObservableList<Homework> preHomeworkList = homeworkTableView.getItems();
            preHomeworkList.forEach(alarmQueue::remove);
        }

        try { //포탈에서 과제 정보를 받아오기
            String lectureIDXmlInfo = PortalHttpRequest.getHomeworkLectureIDList(savedStudentID);
            Map<Integer, String> lectureMap = portalParser.parseHomeworkLectureIDList(lectureIDXmlInfo);
            ArrayList<Homework> newHomeworkList = new ArrayList<>();
            for (Integer lectureID : lectureMap.keySet()) {
                String homeworkXmlInfo = PortalHttpRequest.getHomeworkList(savedStudentID, lectureID);
                List<Homework> remoteHomeworkList = portalParser.parseHomeworkList(homeworkXmlInfo);

                for (Homework homework : remoteHomeworkList) {
                    Homework renamedHomework = new Homework(lectureMap.get(lectureID) + "-" + homework.getName(), homework.getDueDate());
                	newHomeworkList.add(renamedHomework);
                    AlarmQueue.getInstance().add(renamedHomework);
                }
            }
            homeworkTableView.setItems(FXCollections.observableArrayList(newHomeworkList));
            try{ //포탈에서 받아온 과제를 저장하기
                fileManager.writeHomeworkFile(newHomeworkList);
            }catch (IOException ioe){
                printNotificationPane("Couldn't save your homework, Please try again after deleting existing homework list");
                return false;
            }
            printNotificationPane("Successfully load homework list");
            return true;
        }catch(IOException e) { // 포탈에서 과제를 받는 데 발생한 예외
            printNotificationPane("Couldn't get homework list. Please check you are connected to internet\n" +
                        "Try to load homework list in local storage");
            try { // 로컬에 저장된 파일을 찾아서 리스트를 만듬
                ArrayList<Homework> localHomeworkList = new ArrayList<>();
                for (Homework homework : fileManager.readHomeworkFile()) {
                    localHomeworkList.add(homework);
                    AlarmQueue.getInstance().add(homework);
                }
                homeworkTableView.setItems(FXCollections.observableArrayList(localHomeworkList));
                printNotificationPane("Successfully load homework list");
                return true;
            }catch(IOException ioe){ // 로컬에도 저장된 리스트가 없을 때
                printNotificationPane("Could't get homework list saved in local storage");

                return false;
            }catch(ClassNotFoundException cnfe){ // 직렬화 문제
                printNotificationPane("Data has corrupted in Data directory\n" +
                            "Maybe your Scheduler version doesn't match with Schedule files.");
                return false;
            }
        }
    }

    @FXML
    protected void handleSyncButtonAction(@SuppressWarnings("UnusedParameters") ActionEvent event){
        syncButton.setDisable(true);
        sync();
        syncButton.setDisable(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {}
}
