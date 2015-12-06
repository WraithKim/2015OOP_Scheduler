package view.stageBuilder;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import util.SharedPreference;
import view.event.DayScheduleListController;

/**
 * Created by Donghwan on 12/5/2015.
 *
 * 하루 일정 창을 생성함
 */
public class DayScheduleListStageBuilder {
    private static DayScheduleListStageBuilder ourInstance = new DayScheduleListStageBuilder();

    private FXMLLoader fxmlLoader;

    public static DayScheduleListStageBuilder getInstance(){
        return ourInstance;
    }

    public Stage newDayScheduleList() throws Exception{
        fxmlLoader = new FXMLLoader(getClass().getResource(SharedPreference.DayScheduleListView));
        Parent root = fxmlLoader.load();
        DayScheduleListController dayScheduleListController = fxmlLoader.getController();
        Scene scene = new Scene(root);
        Stage dayScheduleListView = new Stage();
        dayScheduleListView.setScene(scene);
        dayScheduleListView.setTitle("Schedule List");
        dayScheduleListView.setResizable(false);
        dayScheduleListView.setOnCloseRequest((WindowEvent event)->{
            dayScheduleListController.saveList();
        });
        return dayScheduleListView;
    }

    private DayScheduleListStageBuilder() {}
}
