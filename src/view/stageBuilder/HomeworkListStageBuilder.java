package view.stageBuilder;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import util.SharedPreference;

/**
 * Created by Donghwan on 12/6/2015.
 *
 * 과제 리스트 창을 생성하는 클래스
 */
public class HomeworkListStageBuilder {
    private static HomeworkListStageBuilder ourInstance = new HomeworkListStageBuilder();

    public static HomeworkListStageBuilder getInstance() {
        return ourInstance;
    }

    private FXMLLoader fxmlLoader;
    private boolean isCreated = false;

    public Stage newHomeworkListViewStage() throws Exception {
        if(isCreated) return null;
        isCreated = true;
        fxmlLoader = new FXMLLoader(getClass().getResource(SharedPreference.HomeworkListView));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
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
