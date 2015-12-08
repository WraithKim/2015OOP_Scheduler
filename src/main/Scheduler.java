package main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import util.AlarmThread;
import util.AlarmQueue;
import util.Constant;
import util.FileManager;
import util.ResourceLoader;
import view.stageBuilder.SettingStageBuilder;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Paint;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.sun.prism.paint.Color;


/**
 * Created by Donghwan on 11/29/2015.
 *
 * 스케쥴러 프로그램을 실행하는 클래스입니다.
 */
public class Scheduler extends Application{
	
	private static final Delta dragDelta = new Delta();
	private static JFrame frame;
	
    public static void main(String[] args) {
    	
    	SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                initAndShowGUI();
                _setIconTray();
            }
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
        AlarmQueue alarmQueue = AlarmQueue.getInstance();
        FileManager fileManager = FileManager.getInstance();
        for(int i = 0; i < Constant.loadTerm; i++){
            try{
                fileManager.readScheduleFile(cur).forEach(alarmQueue::add);
            }catch(IOException ioe) {
                // nothing to do
            }catch(ClassNotFoundException cnfe){
                System.err.println("Data has corrupted in Data directory\n" +
                        "Maybe your Scheduler version doesn't match with Schedule files.");
                System.exit(1);
                return;
            }
            cur.add(Calendar.DATE, 1);
        }
    }

    private static void initStudentId() throws Exception{
        // 학생의 학번을 불러옴
        try {
            FileManager.getInstance().readStudentNumber();
        }catch(FileNotFoundException fnfe){
            // 설정 창을 열어서 id를 입력받게 해야함.
            Stage settingView = SettingStageBuilder.getInstance().newUserSettingViewStage();
            if(settingView != null) settingView.show();
        }
    }

    private static void initAndShowGUI(){
    	
    	frame = new JFrame("OOP Schedule");
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	
        JFXPanel fxPanel = new JFXPanel();
        fxPanel.setBackground(new java.awt.Color(0,0,0,0));
        
        frame.add(fxPanel); frame.setUndecorated(true);
        frame.setBackground(new java.awt.Color(0,0,0,0));
        
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                initFX(fxPanel);
                frame.pack();
                frame.setAlwaysOnTop(false);
                frame.toBack();
            }
        });
        
        frame.setFocusableWindowState(false);
        frame.setVisible(true);
    }
    
    private static void initFX(JFXPanel fxPanel) {
        Scene scene = createScene();
        _setDraggable(fxPanel, scene);
        fxPanel.setScene(scene);
    }
    
    private static Scene createScene() {
    	
    	// 달력 뷰를 생성
        FXMLLoader fxmlLoader = new FXMLLoader(Scheduler.class.getResource(Constant.CalendarView));
        Parent root = null;
		try {
			root = fxmlLoader.load();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
        // 안티앨리어싱
        Scene scene = new Scene(root, 600, 500, false, SceneAntialiasing.BALANCED);
        scene.setFill(javafx.scene.paint.Color.TRANSPARENT);
        
        // 스케쥴러 관리 프로그램을 생성하고
        try {
			initStudentId();
		} catch (Exception e) {
			e.printStackTrace();
		}
        initAlarmThread();
        
        // CSS
        scene.getStylesheets().add(Scheduler.class.getResource("/view/Scheduler.css").toExternalForm());
        
        return scene;
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        // 달력 뷰를 생성
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(Constant.CalendarView));
        Parent root = fxmlLoader.load();
        
        // 안티앨리어싱
        Scene scene = new Scene(root, 600, 500, false, SceneAntialiasing.BALANCED);
        //this._setDraggable(primaryStage, scene);
        
        
        // 스케쥴러 관리 프로그램을 생성하고
        initStudentId();
        initAlarmThread();

        primaryStage.setTitle("Calendar");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest((WindowEvent event)->{
            // TODO 그냥 끄기 전에 전부 제대로 저장했는지 확인이라도 시켜줘야 할 듯...
            System.exit(0);
        });
        
        // CSS
        //this.setUserAgentStylesheet("Scheduler.css");
        scene.getStylesheets().add(Scheduler.class.getResource("/view/Scheduler.css").toExternalForm());

        // Utility 설정
        primaryStage.initStyle(StageStyle.UTILITY);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(null);
        
        primaryStage.show();
    }
    
    private static void _setDraggable(JFXPanel f, Scene se){
    	
    	se.setOnMousePressed(new EventHandler<javafx.scene.input.MouseEvent>() {
			@Override
			public void handle(javafx.scene.input.MouseEvent mouseEvent) {
				dragDelta.x = mouseEvent.getSceneX();
    		    dragDelta.y = mouseEvent.getSceneY();
			}
    	});
    	
    	se.setOnMouseDragged(new EventHandler<javafx.scene.input.MouseEvent>() {
    		  @Override 
    		  public void handle(javafx.scene.input.MouseEvent mouseEvent) {
  				frame.setLocation((int)(mouseEvent.getScreenX() - dragDelta.x), 
  						(int)(mouseEvent.getScreenY() - dragDelta.y));
    		  }
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
            
            java.awt.Font defaultFont = java.awt.Font.decode(null);
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
