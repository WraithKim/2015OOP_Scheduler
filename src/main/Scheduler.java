package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.controlsfx.control.NotificationPane;
import org.controlsfx.dialog.ExceptionDialog;
import util.*;
import view.event.CalendarController;
import view.stageBuilder.NotificationPaneUpgrader;
import view.stageBuilder.SettingStageBuilder;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;


/**
 * Created by Donghwan on 11/29/2015.
 *
 * 스케쥴러 프로그램을 실행하는 클래스입니다.
 */
public class Scheduler extends Application{

    public static void main(String[] args) {
        launch(args);
    }

    public Scheduler() throws FileNotFoundException{
    }

    private void initAlarmThread(){
        AlarmThread alarmThread = AlarmThread.getInstance();
        alarmThread.start();

        // 현재 시간에서 특정 기간까지의 일정을 불러온다.
        // 불러오는 기간이 바뀌면 AlarmQueue의 값도 바뀌어야 함
        Calendar cur = GregorianCalendar.getInstance();
        AlarmQueue alarmQueue = AlarmQueue.getInstance();
        FileManager fileManager = FileManager.getInstance();
        for(int i = 0; i < Constant.loadTerm; i++){
            try{
                fileManager.readScheduleFile(cur).forEach(alarmQueue::add);
            }catch(IOException ioe) {
                // nothing to do
            }catch(ClassNotFoundException cnfe){
                ExceptionDialog exceptionDialog = new ExceptionDialog(cnfe);
                exceptionDialog.setOnCloseRequest(event->{
                    System.exit(1);
                });
                exceptionDialog.show();
            }
            cur.add(Calendar.DATE, 1);
        }
    }

    private void initStudentId() throws Exception{
        // 학생의 학번을 불러옴
        try {
            FileManager.getInstance().readStudentNumber();
        }catch(FileNotFoundException fnfe){
            // 설정 창을 열어서 id를 입력받게 해야함.
            Stage settingView = SettingStageBuilder.getInstance().newUserSettingViewStage();
            if(settingView != null) settingView.show();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // 달력 뷰를 생성
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(Constant.CalendarView));
        Parent root = fxmlLoader.load();
        CalendarController calendarController = fxmlLoader.getController();
        // 스케쥴러 관리 프로그램을 생성하고
        initStudentId();
        initAlarmThread();
        root = NotificationPaneUpgrader.getInstance().upgrade(root);
        calendarController.setNotificationPane((NotificationPane)root);
        Scene scene = new Scene(root);
        primaryStage.setTitle("Calendar");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest((WindowEvent event)->{
            System.exit(0);
        });
        primaryStage.show();
        HomeworkSyncManager.getInstance().sync(calendarController);
    }
    
}
