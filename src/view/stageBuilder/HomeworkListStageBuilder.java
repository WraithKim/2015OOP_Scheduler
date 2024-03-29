package view.stageBuilder;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import main.Scheduler;
import org.controlsfx.control.NotificationPane;
import util.Constant;
import view.event.HomeworkListController;

import java.io.IOException;

/**
 * Created by Donghwan on 12/6/2015.
 *
 * 과제 리스트 창을 생성하는 클래스
 */
public class HomeworkListStageBuilder {
    private static final HomeworkListStageBuilder ourInstance = new HomeworkListStageBuilder();

    public static HomeworkListStageBuilder getInstance() {
        return ourInstance;
    }

    private boolean isCreated = false;

    public Stage newHomeworkListViewStage() throws IOException {
        if(isCreated) return null;
        isCreated = true;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(Constant.ViewPath.HomeworkListView.pathInfomation));
        Parent root = fxmlLoader.load();
        HomeworkListController homeworkListController = fxmlLoader.getController();

        root = NotificationPaneUpgrader.getInstance().upgrade(root);
        homeworkListController.setNotificationPane((NotificationPane)root);

        Scene scene = new Scene(root);
        scene.getStylesheets().add(Scheduler.class.getResource("/view/Scheduler.css").toExternalForm());
        Stage HomeworkListView = new Stage();
        HomeworkListView.setScene(scene);
        HomeworkListView.setResizable(false);
        HomeworkListView.setTitle("Homework List");
        HomeworkListView.setOnCloseRequest((WindowEvent event)-> isCreated = false);
        return HomeworkListView;
    }

    private HomeworkListStageBuilder() {
    }
}
