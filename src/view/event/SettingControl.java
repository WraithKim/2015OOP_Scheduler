package view.event;

import extfx.scene.control.RestrictiveTextField;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import util.FileManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import util.SharedPreference;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Donghwan on 2015-12-03.
 * <p>
 * 설정 창에 달린 이벤트 리스너
 */
public class SettingControl implements Initializable{

    @FXML
    private RestrictiveTextField settingSIDInputForm;

    @FXML
    private Button settingSaveButton;

    @FXML
    protected void handleChangedIDInputForm(KeyEvent event){
        int length = settingSIDInputForm.getLength();
        if((length == 7 && event.getCharacter().matches("[0-9]"))||
                (length == 8 && !(event.getCode().equals(KeyCode.BACK_SPACE))))
            settingSaveButton.setDisable(false);
        else settingSaveButton.setDisable(true);
    }

    @FXML
    protected void handleSaveAction(ActionEvent event){
        try {
            String newStudentID = this.settingSIDInputForm.getText();
            if(newStudentID.length() != 8) return;
            SharedPreference.savedStudentID = newStudentID;
            FileManager.getInstance().writeStudentNumber(SharedPreference.savedStudentID);

            System.out.println("Save Button Clicked :: Saved Content : " + SharedPreference.savedStudentID);
        } catch (IOException e) {
            System.out.println("Save Button Clicked :: IOException");
            SharedPreference.savedStudentID = this.settingSIDInputForm.getText();
            System.out.println("Save Button Clicked :: Saved Content : " + SharedPreference.savedStudentID);
            try {
                FileManager.getInstance().writeStudentNumber(SharedPreference.savedStudentID);
            }catch(IOException e2){
                System.err.println("Something wrong during save StudentID, Please try save again");
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources){
        try {
            String preStudentID = FileManager.getInstance().readStudentNumber();
            System.out.println("Loaded Content : " + preStudentID);
            settingSIDInputForm.setText(preStudentID);
        } catch (IOException | ClassNotFoundException e){
            System.err.println("can't find StudentID");
        }
    }
}
