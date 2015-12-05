package view.stageBuilder;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import util.SharedPreference;

/**
 * Created by Donghwan on 12/5/2015.
 */
public class ScheduleEditorStageBuilder {
    private static ScheduleEditorStageBuilder ourInstance = new ScheduleEditorStageBuilder();

    public static ScheduleEditorStageBuilder getInstance(){
        return ourInstance;
    }

    public Stage newScheduleEditor() throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource(SharedPreference.ScheduleEditorView));
        Scene scene = new Scene(root);
        Stage settingView = new Stage();
        settingView.setScene(scene);
        settingView.setResizable(false);
        return settingView;
    }

    private ScheduleEditorStageBuilder(){

    }
}
