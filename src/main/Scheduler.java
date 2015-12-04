package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import util.Constant;
import util.FileManager;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by Donghwan on 11/29/2015.
 *
 * 스케쥴러 프로그램
 */
public class Scheduler extends Application{

    public static void main(String[] args) {
        launch(args);
    }

    public Scheduler() throws FileNotFoundException{
    }

    public void loadAlarmQueue(){
        // 현재 시간에서 8일 후까지의 일정을 불러온다.
        // 알람큐에 등록
    }

    public void loadStudentId() throws ClassNotFoundException, IOException{
        // 학생의 학번을 불러옴
        try {
            Constant.savedStudentID = FileManager.getInstance().readStudentNumber();
        }catch(FileNotFoundException fnfe){
            // 설정 창을 열어서 id를 입력받게 해야함.
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // 스케쥴러 관리 프로그램을 생성하고
        loadStudentId();
        loadAlarmQueue();


        // 달력 뷰를 생성
        Parent root = FXMLLoader.load(getClass().getResource(Constant.viewPackage+Constant.SettingView));
        Scene scene = new Scene(root);

        primaryStage.setTitle("Calendar");
        primaryStage.setScene(scene);
        primaryStage.setAlwaysOnTop(true);
        primaryStage.setResizable(false);

        primaryStage.show();
    }
}
