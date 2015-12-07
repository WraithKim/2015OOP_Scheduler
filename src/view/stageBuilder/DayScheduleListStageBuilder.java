package view.stageBuilder;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
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

    public Stage newDayScheduleList(Date selectedDate){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(Constant.DayScheduleListView));

        Parent root;
        try{
            root = fxmlLoader.load();
        }catch(IOException ioe){
            System.err.println("Couldn't load fxml file");
            return null;
        }
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
        Stage dayScheduleListView = new Stage();
        dayScheduleListView.setScene(scene);
        dayScheduleListView.setTitle("Schedule List");
        dayScheduleListView.setResizable(false);
        dayScheduleListView.setOnCloseRequest((WindowEvent event)-> dayScheduleListController.saveList());
        return dayScheduleListView;
    }

    private DayScheduleListStageBuilder() {}
}
