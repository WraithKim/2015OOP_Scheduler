package view.stageBuilder;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import main.Scheduler;
import org.controlsfx.control.NotificationPane;
import org.controlsfx.dialog.ExceptionDialog;
import util.Constant;
import view.event.DayScheduleListController;

import java.io.IOException;
import java.util.Date;

/**
 * Created by Donghwan on 12/5/2015.
 *
 * 하루 일정 창을 생성함
 */
public class DayScheduleListStageBuilder {
    private static final DayScheduleListStageBuilder ourInstance = new DayScheduleListStageBuilder();

    public static DayScheduleListStageBuilder getInstance(){
        return ourInstance;
    }

    public Stage newDayScheduleList(Date selectedDate) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(Constant.ViewPath.DayScheduleListView.pathInfomation));

        Parent root = fxmlLoader.load();
        DayScheduleListController dayScheduleListController = fxmlLoader.getController();

        root = NotificationPaneUpgrader.getInstance().upgrade(root);
        dayScheduleListController.setNotificationPane((NotificationPane)root);

        try {
            dayScheduleListController.loadScheduleList(selectedDate);
        }catch(ClassNotFoundException cnfe){
            ExceptionDialog exceptionDialog = new ExceptionDialog(cnfe);
            exceptionDialog.show();
            return null;
        }
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Scheduler.class.getResource("/view/Scheduler.css").toExternalForm());
        Stage dayScheduleListView = new Stage();
        dayScheduleListView.setScene(scene);
        dayScheduleListView.setTitle("Schedule List");
        dayScheduleListView.setResizable(false);
        dayScheduleListView.setOnCloseRequest((WindowEvent event)-> dayScheduleListController.saveList());
        return dayScheduleListView;
    }

    private DayScheduleListStageBuilder() {}
}
