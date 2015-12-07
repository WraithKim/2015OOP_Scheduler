package view.event;

import extfx.scene.control.RestrictiveTextField;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.controlsfx.control.NotificationPane;
import org.controlsfx.dialog.ExceptionDialog;
import util.FileManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Donghwan on 2015-12-03.
 * <p>
 * 설정 창에 달린 이벤트 리스너
 */
public class SettingController implements Initializable{

    @FXML
    private RestrictiveTextField settingSIDInputForm;

    @FXML
    private Button settingSaveButton;

    private NotificationPane notificationPane;

    public void setNotificationPane(NotificationPane notificationPane) {
        this.notificationPane = notificationPane;
    }

    @FXML
    protected void handleChangedIDInputForm(KeyEvent event){
        int length = settingSIDInputForm.getLength();
        if((length == 7 && event.getCharacter().matches("[0-9]"))||
                (length == 8 && !(event.getCode().equals(KeyCode.BACK_SPACE))))
            settingSaveButton.setDisable(false);
        else settingSaveButton.setDisable(true);
    }

    @FXML
    protected void handleSaveAction(@SuppressWarnings("UnusedParameters") ActionEvent event){
        String newStudentID = this.settingSIDInputForm.getText();
        if(newStudentID.length() != 8) return;
        try {
            FileManager.getInstance().writeStudentNumber(newStudentID);
        } catch (IOException ioe) {
            if(!notificationPane.isShowing()) {
                notificationPane.setText("Something wrong during save StudentID, Please try save again");
                notificationPane.show();
            }
            return;
        }

        ((Stage)settingSaveButton.getScene().getWindow()).getOnCloseRequest().handle(new WindowEvent(settingSaveButton.getScene().getWindow(), WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources){
        try {
            String preStudentID = FileManager.getInstance().readStudentNumber();
            settingSIDInputForm.setText(preStudentID);
        }catch (IOException ioe) {
            //nothing to do
        }catch(ClassNotFoundException cnfe){
            ExceptionDialog exceptionDialog = new ExceptionDialog(cnfe);
            exceptionDialog.show();
        }
    }
}
