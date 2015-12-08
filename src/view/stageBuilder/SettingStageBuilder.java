package view.stageBuilder;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import main.Scheduler;
import org.controlsfx.control.NotificationPane;
import util.Constant;
import util.FileManager;
import view.event.SettingController;

import java.io.IOException;

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

    private Stage newSettingViewStage() throws IOException {
        if(isCreated) return null;
        isCreated = true;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(Constant.ViewPath.SettingView.pathInfomation));
        Parent root = fxmlLoader.load();
        SettingController settingController = fxmlLoader.getController();

        root = NotificationPaneUpgrader.getInstance().upgrade(root);
        settingController.setNotificationPane((NotificationPane)root);
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Scheduler.class.getResource("/view/Scheduler.css").toExternalForm());
        Stage settingView = new Stage();
        settingView.setScene(scene);
        settingView.setAlwaysOnTop(true);
        settingView.setResizable(false);
        return settingView;
    }

    public Stage newUserSettingViewStage() throws IOException{
        Stage settingView = newSettingViewStage();
        if(settingView != null) {
            settingView.setTitle("New Student ID");
            settingView.setOnCloseRequest((WindowEvent event) -> {
                try {
                    FileManager.readStudentNumber();
                } catch (Exception e) {

                    System.exit(1);
                }
                isCreated = false;
                settingView.close();
            });
        }
        return settingView;
    }

    public Stage defaultSettingViewStage() throws IOException{
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
