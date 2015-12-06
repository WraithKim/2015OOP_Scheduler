package view.stageBuilder;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import util.FileManager;
import util.SharedPreference;

/**
 * Created by Donghwan on 12/6/2015.
 */
public class HomeworkListStageBuilder {
    private static HomeworkListStageBuilder ourInstance = new HomeworkListStageBuilder();

    public static HomeworkListStageBuilder getInstance() {
        return ourInstance;
    }

    private FXMLLoader fxmlLoader;
    private boolean isCreated = false;

    private Stage newHomeworkListViewStage() throws Exception {
        if(isCreated) return null;
        isCreated = true;
        fxmlLoader = new FXMLLoader(getClass().getResource(SharedPreference.SettingView));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage settingView = new Stage();
        settingView.setScene(scene);
        settingView.setAlwaysOnTop(true);
        settingView.setResizable(false);
        return settingView;
    }

    private HomeworkListStageBuilder() {
    }
}
