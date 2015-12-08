package main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.stage.Stage;

import org.controlsfx.control.NotificationPane;
import org.controlsfx.dialog.ExceptionDialog;

import util.*;
import view.event.CalendarController;
import view.stageBuilder.NotificationPaneUpgrader;
import util.AlarmThread;
import util.AlarmQueue;
import util.Constant;
import util.FileManager;
import util.ResourceLoader;
import view.stageBuilder.SettingStageBuilder;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * Created by Donghwan on 11/29/2015.
 *
 * 스케쥴러 프로그램을 실행하는 클래스입니다.
 */
public class Scheduler extends Application{
    private static final Delta dragDelta = new Delta();
    private static JFrame frame;
	
    public static void main(String[] args) {
    	
    	SwingUtilities.invokeLater(()->{
                initAndShowGUI();
                _setIconTray();
        });
    }

    public Scheduler() throws FileNotFoundException{
    }

    private static void initAlarmThread(){
        AlarmThread alarmThread = AlarmThread.getInstance();
        alarmThread.start();

        // 현재 시간에서 특정 기간까지의 일정을 불러온다.
        // 불러오는 기간이 바뀌면 AlarmQueue의 값도 바뀌어야 함
        Calendar cur = GregorianCalendar.getInstance();
        for(int i = 0; i < Constant.loadTerm; i++){
            try{
                FileManager.readScheduleFile(cur).forEach(AlarmQueue.alarmQueue::add);
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

    private static void initStudentId(){
        // 학생의 학번을 불러옴
        if(!FileManager.containID()){// 설정 창을 열어서 id를 입력받게 해야함.
            FileManager.makeDateDirectory();
            try {
                Stage settingView = SettingStageBuilder.getInstance().newUserSettingViewStage();
                if (settingView != null) settingView.show();
            }catch(IOException ioe){
                ExceptionDialog exceptionDialog = new ExceptionDialog(ioe);
                exceptionDialog.setOnCloseRequest(event-> System.exit(1));
                exceptionDialog.show();
            }
        }
    }

    private static void initAndShowGUI(){
    	
    	frame = new JFrame("OOP Schedule");
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	
        JFXPanel fxPanel = new JFXPanel();
        fxPanel.setBackground(new java.awt.Color(0,0,0,0));
        
        frame.add(fxPanel); frame.setUndecorated(true);
        frame.setBackground(new java.awt.Color(0,0,0,0));
        
        Platform.runLater(() -> {
                initFX(fxPanel);
                frame.pack();
                frame.setAlwaysOnTop(false);
                frame.toBack();
        });
        
        frame.setFocusableWindowState(false);
        frame.setVisible(true);

    }
    
    private static void initFX(JFXPanel fxPanel) {
        Scene scene = createScene();
        if(scene == null) System.exit(1);
        //_setDraggable(scene);
        fxPanel.setScene(scene);
    }
    
    private static Scene createScene() {
        // 스케쥴러 관리 프로그램을 생성하고
        initStudentId();
        initAlarmThread();

        //달력 뷰를 생성
        FXMLLoader fxmlLoader = new FXMLLoader(Scheduler.class.getResource(Constant.ViewPath.CalendarView.pathInfomation));
        Parent root;
        try {
            root = fxmlLoader.load();
            CalendarController calendarController = fxmlLoader.getController();
            root = NotificationPaneUpgrader.getInstance().upgrade(root);
            calendarController.setNotificationPane((NotificationPane)root);
            
            _setDraggable(root);
            // 안티앨리어싱
            Scene scene = new Scene(root, 600, 500, false, SceneAntialiasing.BALANCED);
            scene.setFill(javafx.scene.paint.Color.TRANSPARENT);
            
            // CSS
            scene.getStylesheets().add(Scheduler.class.getResource("/view/Scheduler.css").toExternalForm());
            HomeworkSyncManager.homeworkSyncManager.sync(calendarController);
            
            return scene;
        } catch (IOException ioe) {
            ExceptionDialog exceptionDialog = new ExceptionDialog(ioe);
            exceptionDialog.setOnCloseRequest(event-> System.exit(1));
            exceptionDialog.show();
            return null;
        }
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {}

    private static void _setDraggable(Node se){
    	
    	se.setOnMousePressed(mouseEvent -> {
				dragDelta.x = mouseEvent.getSceneX();
    		    dragDelta.y = mouseEvent.getSceneY();
    	});

    	se.setOnMouseDragged(mouseEvent -> {
  				frame.setLocation((int)(mouseEvent.getScreenX() - dragDelta.x), 
  						(int)(mouseEvent.getScreenY() - dragDelta.y));
    	});
    	
    }
    
    private static void _setIconTray() {
        try {
            java.awt.Toolkit.getDefaultToolkit();

            if (!java.awt.SystemTray.isSupported()) {
                System.out.println("No system tray support, application exiting.");
                Platform.exit();
            }

            java.awt.SystemTray tray = java.awt.SystemTray.getSystemTray();
            ImageIcon image = new ImageIcon(ImageIO.read(ResourceLoader.load("icon.png")));
            java.awt.TrayIcon trayIcon = new java.awt.TrayIcon(image.getImage());
            
            //java.awt.Font defaultFont = java.awt.Font.decode(null);
            java.awt.MenuItem exitItem = new java.awt.MenuItem("Exit");
            
            exitItem.addActionListener(event -> {
                System.exit(0);
                tray.remove(trayIcon);
            });
            
            // setup the popup menu for the application.
            final java.awt.PopupMenu popup = new java.awt.PopupMenu();
            popup.add(exitItem);
            trayIcon.setPopupMenu(popup);

            // add the application tray icon to the system tray.
            tray.add(trayIcon);
            
        } catch (java.awt.AWTException | IOException e) {
            System.out.println("Unable to init system tray");
            e.printStackTrace();
        }
    }
}
class Delta { double x, y; }