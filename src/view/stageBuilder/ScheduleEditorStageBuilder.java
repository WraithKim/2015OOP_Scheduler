package view.stageBuilder;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import util.SharedPreference;

/**
 * Created by Donghwan on 12/5/2015.
 *
 * 스케쥴 편집창을 생성
 */
public class ScheduleEditorStageBuilder {
    private static ScheduleEditorStageBuilder ourInstance = new ScheduleEditorStageBuilder();

    private FXMLLoader fxmlLoader;

    public static ScheduleEditorStageBuilder getInstance(){
        return ourInstance;
    }

    public Stage newScheduleEditor() throws Exception{
        fxmlLoader = new FXMLLoader(getClass().getResource(SharedPreference.ScheduleEditorView));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage scheduleEditorView = new Stage();
        scheduleEditorView.setScene(scene);
        scheduleEditorView.setTitle("Schedule Editor");
        scheduleEditorView.setResizable(false);
        return scheduleEditorView;
    }

    private ScheduleEditorStageBuilder(){

    }
}
