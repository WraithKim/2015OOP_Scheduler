package view.stageBuilder;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.controlsfx.control.NotificationPane;
import schedule.Schedule;
import util.Constant;
import view.event.ScheduleEditorController;

import java.util.Date;

/**
 * Created by Donghwan on 12/5/2015.
 *
 * 스케쥴 편집창을 생성
 */
public class ScheduleEditorStageBuilder {
    private static final ScheduleEditorStageBuilder ourInstance = new ScheduleEditorStageBuilder();

    private FXMLLoader fxmlLoader;

    public static ScheduleEditorStageBuilder getInstance(){
        return ourInstance;
    }

    public Stage newAddingScheduleEditor(Date currentDate, ObservableList<Schedule> originScheduleList) throws Exception{
        fxmlLoader = new FXMLLoader(getClass().getResource(Constant.ViewPath.ScheduleEditorView.pathInfomation));
        Parent root = fxmlLoader.load();
        ScheduleEditorController scheduleEditorController = fxmlLoader.getController();
        scheduleEditorController.setAddableView(currentDate, originScheduleList);

        root = NotificationPaneUpgrader.getInstance().upgrade(root);
        scheduleEditorController.setNotificationPane((NotificationPane)root);
        Scene scene = new Scene(root);
        Stage scheduleEditorView = new Stage();
        scheduleEditorView.setScene(scene);
        scheduleEditorView.setTitle("New Schedule Add");
        scheduleEditorView.setResizable(false);
        return scheduleEditorView;
    }

    public Stage newEditingScheduleEditor(Schedule editingSchedule) throws Exception{
        fxmlLoader = new FXMLLoader(getClass().getResource(Constant.ViewPath.ScheduleEditorView.pathInfomation));
        Parent root = fxmlLoader.load();
        ScheduleEditorController scheduleEditorController = fxmlLoader.getController();
        scheduleEditorController.setEditableView(editingSchedule);

        NotificationPane notificationPane = new NotificationPane();
        notificationPane.setContent(root);
        notificationPane.setShowFromTop(true);
        notificationPane.setCloseButtonVisible(true);
        scheduleEditorController.setNotificationPane(notificationPane);

        Scene scene = new Scene(notificationPane);
        Stage scheduleEditorView = new Stage();
        scheduleEditorView.setScene(scene);
        scheduleEditorView.setTitle("Existing Schedule Edit");
        scheduleEditorView.setResizable(false);
        return scheduleEditorView;
    }

    private ScheduleEditorStageBuilder(){

    }
}
