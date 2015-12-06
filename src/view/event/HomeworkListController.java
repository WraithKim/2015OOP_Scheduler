package view.event;

import javafx.collections.FXCollections;
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
    private TableView<Schedule> homeworkTableView;

    @FXML
    private TableColumn dateColume;

    @FXML
    private TableColumn timeColumn;

    @FXML
    private Button syncButton;

    public boolean sync(){
        // TODO 나중에 디버그 코드 지워야 함
        PortalXmlParser portalParser = new PortalXmlParser();
        FileManager fileManager = FileManager.getInstance();
        System.out.println("Sync :: loaded Student ID : " + SharedPreference.savedStudentID);
        if (SharedPreference.savedStudentID.isEmpty()) {
            System.err.println("먼저 학번 설정을 해주세요!");
            return false;
        }
        try {
            String lectureIDXmlInfo = PortalHttpRequest.getHomeworkLectureIDList(SharedPreference.savedStudentID);
            Set<Integer> lectureIDSet = portalParser.parseHomeworkLectureIDList(lectureIDXmlInfo);

            System.out.println("Sync :: lectureIDList : " + lectureIDXmlInfo);
            System.out.println("Before Homework List Length : " + SharedPreference.homeworkList.size());
            System.out.println("Before AlarmQueue Length : " + AlarmQueue.getInstance().size());

            SharedPreference.homeworkList.stream().filter(homework -> AlarmQueue.getInstance().contains(homework)).forEach(homework -> AlarmQueue.getInstance().remove(homework));
            SharedPreference.homeworkList.clear();

            System.out.println("After Homework List Length : " + SharedPreference.homeworkList.size());
            System.out.println("After AlarmQueue Length : " + AlarmQueue.getInstance().size());

            for (Integer lectureID : lectureIDSet) {
                String homeworkXmlInfo = PortalHttpRequest.getHomeworkList(SharedPreference.savedStudentID, lectureID);
                System.out.println("Sync :: homeworkXmlInfo : " + homeworkXmlInfo);
                List<Schedule> entityHomeworkList = portalParser.parseHomeworkList(homeworkXmlInfo);

                for (Schedule schedule : entityHomeworkList) {
                    SharedPreference.homeworkList.add((Homework) schedule);
                    AlarmQueue.getInstance().add(schedule);
                }
            }
            fileManager.writeHomeworkFile(SharedPreference.homeworkList);
        } catch (IOException e) {
            System.err.println("Couldn't get homework list. Please check your internet connection or something else");
            try {
                SharedPreference.homeworkList.stream().filter(homework -> AlarmQueue.getInstance().contains(homework)).forEach(homework -> AlarmQueue.getInstance().remove(homework));
                SharedPreference.homeworkList.clear();
                for (Schedule schedule : fileManager.readHomeworkFile()) {
                    SharedPreference.homeworkList.add((Homework) schedule);
                    AlarmQueue.getInstance().add(schedule);
                }
            }catch(IOException ioe){
                System.err.println("Could't get homework list saved in local storage");
                return false;
            }catch(ClassNotFoundException cnfe){
                System.err.println("Data has corrupted in Data directory");
                return false;
            }
        }finally{
            System.out.println("Finally Homework List Length : " + SharedPreference.homeworkList.size());
            System.out.println("Finally AlarmQueue Length : " + AlarmQueue.getInstance().size());
        }
        homeworkTableView.refresh();
        return true;
    }

    @FXML
    protected void handleSyncButtonAction(ActionEvent event){
        syncButton.setDisable(true);
        sync();
        syncButton.setDisable(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sync();
        homeworkTableView.setItems(FXCollections.observableArrayList(SharedPreference.homeworkList));
    }
}
