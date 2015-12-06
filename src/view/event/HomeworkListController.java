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
import schedule.Schedule;
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
        //System.out.println("Sync :: loaded Student ID : " + Constant.savedStudentID);
        try {
            savedStudentID = fileManager.readStudentNumber();
        }catch(IOException ioe) {
            System.err.println("Could't get student ID, Please save your ID at Setting");
        }catch(ClassNotFoundException cnfe) {
            System.err.println("Data has corrupted in Data directory\n" +
                    "Maybe your Scheduler version doesn't match with Schedule files.");
        }
        if(homeworkTableView.getItems() != null) {
            //System.out.println("Sync :: lectureIDList : " + lectureIDXmlInfo);
            //System.out.println("Before Homework List Length : " + Constant.homeworkList.size());
            //System.out.println("Before AlarmQueue Length : " + AlarmQueue.getInstance().size());
            ObservableList<Homework> preHomeworkList = homeworkTableView.getItems();
            preHomeworkList.forEach(alarmQueue::remove);
        }
        //System.out.println("After Homework List Length : " + Constant.homeworkList.size());
        //System.out.println("After AlarmQueue Length : " + AlarmQueue.getInstance().size());

        try {
            String lectureIDXmlInfo = PortalHttpRequest.getHomeworkLectureIDList(savedStudentID);
            Set<Integer> lectureIDSet = portalParser.parseHomeworkLectureIDList(lectureIDXmlInfo);
            ArrayList<Homework> newHomeworkList = new ArrayList<>();
            for (Integer lectureID : lectureIDSet) {
                String homeworkXmlInfo = PortalHttpRequest.getHomeworkList(savedStudentID, lectureID);
                //System.out.println("Sync :: homeworkXmlInfo : " + homeworkXmlInfo);
                List<Homework> entityHomeworkList = portalParser.parseHomeworkList(homeworkXmlInfo);

                for (Homework homework : entityHomeworkList) {
                    newHomeworkList.add(homework);
                    AlarmQueue.getInstance().add(homework);
                }
            }
            homeworkTableView.setItems(FXCollections.observableArrayList(newHomeworkList));
            try{
                fileManager.writeHomeworkFile(newHomeworkList);
            }catch (IOException ioe){
                System.err.println("Couldn't save your homework, Please try again after deleting existing homework list");
                return false;
            }
            return true;
        }catch(IOException e) {
            System.err.println("Couldn't get homework list. Please check you are connected to internet");
            System.out.println("Try to load homework list in local storage");
            try {
                ArrayList<Homework> localHomeworkList = new ArrayList<>();
                for (Homework homework : fileManager.readHomeworkFile()) {
                    localHomeworkList.add(homework);
                    AlarmQueue.getInstance().add(homework);
                }
                homeworkTableView.setItems(FXCollections.observableArrayList(localHomeworkList));
                return true;
            }catch(IOException ioe){
                System.err.println("Could't get homework list saved in local storage");
                return false;
            }catch(ClassNotFoundException cnfe){
                System.err.println("Data has corrupted in Data directory\n" +
                        "Maybe your Scheduler version doesn't match with Schedule files.");
                return false;
            }
        }
        finally{
            //System.out.println("Finally Homework List Length : " + Constant.homeworkList.size());
            //System.out.println("Finally AlarmQueue Length : " + AlarmQueue.getInstance().size());
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
