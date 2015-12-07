package view.stageBuilder;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import util.Constant;
import view.event.DayScheduleListController;

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

    public Stage newDayScheduleList(Date selectedDate) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(Constant.DayScheduleListView));
        Parent root = fxmlLoader.load();
        DayScheduleListController dayScheduleListController = fxmlLoader.getController();
        try {
            dayScheduleListController.setDate(selectedDate);
        }catch(ClassNotFoundException cnfe){
            System.err.println("Data has corrupted in Data directory\n" +
                    "Maybe your Scheduler version doesn't match with Schedule files.");
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
