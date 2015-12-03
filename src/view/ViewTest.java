package view;
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

import java.io.IOException;
import java.util.Locale;

public class ViewTest extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("CalendarView.fxml"));
            CalendarView calendarView = (CalendarView)(((BorderPane)root).getCenter());
            calendarView.setLocale(Locale.KOREAN);
            Scene scene = new Scene(root);

            primaryStage.setTitle("Calendar");
            primaryStage.setScene(scene);
            primaryStage.show();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }

    }
}
