package sample;
/**
 * Created by CAUCSE on 2015-12-03.
 *
 * for testing view
 */

import extfx.scene.control.CalendarView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

public class ViewTest extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public static final String viewPackage = ".."+File.separator+"view"+File.separator;
    public static final String DayScheduleListView = "DayScheduleListView.fxml";
    public static final String CalendarView = "CalendarView.fxml";
    public static final String ScheduleEditorView = "ScheduleEditorView.fxml";
    public static final String SettingView = "SettingView.fxml";

    @Override
    public void start(Stage primaryStage) {
        try{

            //Parent root = FXMLLoader.load(getClass().getResource("CalendarView.fxml"));
            //CalendarView calendarView = (CalendarView)(((BorderPane)root).getCenter());
            //calendarView.setLocale(Locale.KOREAN);
            //Scene scene = new Scene(root);

            Parent root = FXMLLoader.load(getClass().getResource(viewPackage+DayScheduleListView));
            Scene scene = new Scene(root);

            // Setting its location with always on top.
            //primaryStage.getIcons().add(new Image(""));
            primaryStage.setTitle("Calendar");
            primaryStage.setScene(scene);
            primaryStage.setAlwaysOnTop(true);
            primaryStage.setResizable(false);
            //primaryStage.initStyle(StageStyle.UNDECORATED);
            //primaryStage.setAlwaysOnTop(false);

            primaryStage.show();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }

    }
}
