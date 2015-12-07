package view.stageBuilder;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.controlsfx.control.NotificationPane;
import util.Constant;
import util.FileManager;
import view.event.SettingController;

/**
 * Created by Donghwan on 12/5/2015.
 *
 * 설정 창을 생성하고 창에 대한 설정을 함
 */
public class SettingStageBuilder {
    private static final SettingStageBuilder ourInstance = new SettingStageBuilder();

    private boolean isCreated = false;

    public static SettingStageBuilder getInstance(){
        return ourInstance;
    }

    private Stage newSettingViewStage() throws Exception {
        if(isCreated) return null;
        isCreated = true;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(Constant.SettingView));
        Parent root = fxmlLoader.load();
        SettingController settingController = fxmlLoader.getController();

        root = NotificationPaneUpgrader.getInstance().upgrade(root);
        settingController.setNotificationPane((NotificationPane)root);
        Scene scene = new Scene(root);
        Stage settingView = new Stage();
        settingView.setScene(scene);
        settingView.setAlwaysOnTop(true);
        settingView.setResizable(false);
        return settingView;
    }

    public Stage newUserSettingViewStage() throws Exception{
        Stage settingView = newSettingViewStage();
        if(settingView != null) {
            settingView.setTitle("New Student ID");
            settingView.setOnCloseRequest((WindowEvent event) -> {
                try {
                    FileManager.getInstance().readStudentNumber();
                } catch (Exception e) {

                    System.exit(1);
                }
                isCreated = false;
                settingView.close();
            });
        }
        return settingView;
    }

    public Stage defaultSettingViewStage() throws Exception{
        Stage settingView = newSettingViewStage();
        if(settingView != null){
            settingView.setTitle("Setting");
            settingView.setOnCloseRequest((WindowEvent event)-> {
                isCreated = false;
                settingView.close();
            });
        }
        return settingView;
    }

    private SettingStageBuilder(){}
}
