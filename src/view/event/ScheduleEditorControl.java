package view.event;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import util.Constant;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

/**
 * Created by CAUCSE on 2015-12-03.
 *
 * 일정 편집 창에 달린 이벤트 리스너
 */
public class ScheduleEditorControl implements Initializable{

    @FXML
    private TextField titleTextField;

    @FXML
    private ToggleGroup priorityToggleGroup;
    // 우선순위를 나타내는 라디오버튼을 묶어놓은 그룹

    @FXML
    private ComboBox<Integer> hourComboBox;

    @FXML
    private ComboBox<Integer> minuteComboBox;

    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private Button scheduleSaveButton;

    @FXML
    protected void handleSaveButtonAction(ActionEvent event){
        System.out.println("Save");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LinkedList<Integer> hourList = new LinkedList<>();
        for(int i = 0; i < 24; i++){
            hourList.add(i);
        }
        hourComboBox.setItems(FXCollections.observableList(hourList));
        LinkedList<Integer> minuteList = new LinkedList<>();
        for(int i = 0; i < 60; i++){
            minuteList.add(i);
        }
        minuteComboBox.setItems((FXCollections.observableArrayList(minuteList)));

        // TODO 기존 일정을 편집하는 경우 기존 일정을 불러오고, 제목 입력 필드를 편집 불가로 바꿔야 함.
    }

}
