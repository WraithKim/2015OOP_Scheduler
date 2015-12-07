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
import schedule.Homework;
import util.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

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

    private boolean sync(){
        // TODO 나중에 디버그 코드 지워야 함
        PortalXmlParser portalParser = new PortalXmlParser();
        FileManager fileManager = FileManager.getInstance();
        AlarmQueue alarmQueue = AlarmQueue.getInstance();
        String savedStudentID = null;
        try {
            savedStudentID = fileManager.readStudentNumber();
            System.out.println("Sync :: loaded Student ID : " + savedStudentID);
        }catch(IOException ioe) {
            System.err.println("Could't get student ID, Please save your ID at Setting");
        }catch(ClassNotFoundException cnfe) {
            System.err.println("Data has corrupted in Data directory\n" +
                    "Maybe your Scheduler version doesn't match with Schedule files.");
        }
        if(homeworkTableView.getItems() != null) { //기존에 테이블에 저장된 리스트를 알람 큐에서 지우기
            ObservableList<Homework> preHomeworkList = homeworkTableView.getItems();
            System.out.println("Before Homework List Length : " + preHomeworkList.size());
            System.out.println("Before AlarmQueue Length : " + AlarmQueue.getInstance().size());
            preHomeworkList.forEach(alarmQueue::remove);
        }
        System.out.println("After Homework List Length : " + homeworkTableView.getItems().size());
        System.out.println("After AlarmQueue Length : " + AlarmQueue.getInstance().size());

        try { //포탈에서 과제 정보를 받아오기
            String lectureIDXmlInfo = PortalHttpRequest.getHomeworkLectureIDList(savedStudentID);
            System.out.println("Sync :: lectureIDList : " + lectureIDXmlInfo);
            Set<Integer> lectureIDSet = portalParser.parseHomeworkLectureIDList(lectureIDXmlInfo);
            ArrayList<Homework> newHomeworkList = new ArrayList<>();
            for (Integer lectureID : lectureIDSet) {
                String homeworkXmlInfo = PortalHttpRequest.getHomeworkList(savedStudentID, lectureID);
                System.out.println("Sync :: homeworkXmlInfo : " + homeworkXmlInfo);
                List<Homework> remoteHomeworkList = portalParser.parseHomeworkList(homeworkXmlInfo);

                for (Homework homework : remoteHomeworkList) {
                	newHomeworkList.add(homework);
                    AlarmQueue.getInstance().add(homework);
                }
            }
            homeworkTableView.setItems(FXCollections.observableArrayList(newHomeworkList));
            try{ //포탈에서 받아온 과제를 저장하기
                fileManager.writeHomeworkFile(newHomeworkList);
            }catch (IOException ioe){
                System.err.println("Couldn't save your homework, Please try again after deleting existing homework list");
                return false;
            }
            return true;
        }catch(IOException e) { // 포탈에서 과제를 받는 데 발생한 예외
            System.err.println("Couldn't get homework list. Please check you are connected to internet");
            System.out.println("Try to load homework list in local storage");
            try { // 로컬에 저장된 파일을 찾아서 리스트를 만듬
                ArrayList<Homework> localHomeworkList = new ArrayList<>();
                for (Homework homework : fileManager.readHomeworkFile()) {
                    localHomeworkList.add(homework);
                    AlarmQueue.getInstance().add(homework);
                }
                homeworkTableView.setItems(FXCollections.observableArrayList(localHomeworkList));
                return true;
            }catch(IOException ioe){ // 로컬에도 저장된 리스트가 없을 때
                System.err.println("Could't get homework list saved in local storage");
                return false;
            }catch(ClassNotFoundException cnfe){ // 직렬화 문제
                System.err.println("Data has corrupted in Data directory\n" +
                        "Maybe your Scheduler version doesn't match with Schedule files.");
                return false;
            }
        } finally{
            System.out.println("Finally Homework List Length : " + homeworkTableView.getItems().size());
            System.out.println("Finally AlarmQueue Length : " + AlarmQueue.getInstance().size());
        }
    }

    @FXML
    protected void handleSyncButtonAction(@SuppressWarnings("UnusedParameters") ActionEvent event){
        syncButton.setDisable(true);
        sync();
        syncButton.setDisable(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sync();
    }
}
