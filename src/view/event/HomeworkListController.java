package view.event;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import schedule.Homework;
import util.*;
import java.net.URL;
import java.util.*;

/**
 * Created by Donghwan on 12/6/2015.
 *
 * 과제 리스트 컨트롤러
 */
public class HomeworkListController extends AbstactNotificationController implements Initializable{
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

    @FXML
    protected void handleSyncButtonAction(@SuppressWarnings("UnusedParameters") ActionEvent event){
        syncButton.setDisable(true);
        HomeworkSyncManager.homeworkSyncManager.sync(this);
        syncButton.setDisable(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        HomeworkSyncManager.homeworkSyncManager.sync(this);
        homeworkTableView.setItems(HomeworkSyncManager.homeworkSyncManager.getHomeworkList());
    }
}
