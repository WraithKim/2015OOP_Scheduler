package view.stageBuilder;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import util.Constant;

/**
 * Created by Donghwan on 12/5/2015.
 *
 * 하루 일정 창을 생성함
 */
public class DayScheduleListStageBuilder {
    private static DayScheduleListStageBuilder ourInstance = new DayScheduleListStageBuilder();
    public static DayScheduleListStageBuilder getInstance(){
        return ourInstance;
    }

    public Stage newDayScheduleList() throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource(Constant.DayScheduleListView));
        Scene scene = new Scene(root);
        Stage dayScheduleListView = new Stage();
        dayScheduleListView.setScene(scene);
        dayScheduleListView.setResizable(false);

        return dayScheduleListView;
    }

    private DayScheduleListStageBuilder() {}
}
