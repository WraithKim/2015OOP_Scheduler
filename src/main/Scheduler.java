package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import schedule.AlarmThread;
import util.AlarmQueue;
import util.Constant;
import util.FileManager;
import view.stageBuilder.SettingStageBuilder;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

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

    public void initAlarmQueue(){
        // 현재 시간에서 8일 후까지의 일정을 불러온다.
        // 알람큐에 등록
        Calendar cur = GregorianCalendar.getInstance();
        AlarmQueue alarmQueue = AlarmQueue.getInstance();
        for(int i = 0; i < 8; i++){
            try{
                alarmQueue.addAll(FileManager.getInstance().readScheduleFile(cur.get(Calendar.YEAR), cur.get(Calendar.MONTH) + 1, cur.get(Calendar.DAY_OF_MONTH) + 1));
            }catch(IOException ioe) {
                // nothing to do
            }catch(ClassNotFoundException cnfe){
                System.err.println("Something wrong in Data directory");
                System.exit(1);
                return;
            }
        }
        while(!(alarmQueue.isEmpty())&&(alarmQueue.peek().getAlarmTime() < cur.getTimeInMillis())){
            alarmQueue.poll();
        }

        AlarmThread.getInstance().start();
    }

    private void initStudentId() throws Exception{
        // 학생의 학번을 불러옴
        try {
            Constant.savedStudentID = FileManager.getInstance().readStudentNumber();
        }catch(FileNotFoundException fnfe){
            // 설정 창을 열어서 id를 입력받게 해야함.
            Stage settingView = SettingStageBuilder.getInstance().newUserSettingViewStage();
            if(settingView != null) settingView.show();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // 스케쥴러 관리 프로그램을 생성하고
        initStudentId();
        initAlarmQueue();
        //AlarmThread.getInstance().start();

        // 달력 뷰를 생성
        Parent root = FXMLLoader.load(getClass().getResource(Constant.CalendarView));
        Scene scene = new Scene(root);

        primaryStage.setTitle("Calendar");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest((WindowEvent event)->{
            // TODO 끄기 전에 저장을 할거면 여기에 저장하는 코드를 넣어야 함
            System.exit(0);
        });

        primaryStage.show();
    }
}
