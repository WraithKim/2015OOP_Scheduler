package view.event;

import extfx.scene.control.RestrictiveTextField;
import javafx.fxml.Initializable;
import util.FileManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import util.Constant;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Donghwan on 2015-12-03.
 * <p>
 * 설정 창에 달린 이벤트 리스너
 */
public class SettingViewControl implements Initializable{
    @FXML
    private RestrictiveTextField settingSIDInputForm;

    @FXML
    private Button settingSaveButton;

    @FXML
    protected void handleSaveButtonAction(ActionEvent event){
        try {
            String newStudentID = this.settingSIDInputForm.getText();
            if(newStudentID.length() != 8) return;
            Constant.savedStudentID = newStudentID;
            FileManager.getInstance().writeStudentNumber(Constant.savedStudentID);

            System.out.println("Save Button Clicked :: Saved Content : " + Constant.savedStudentID);
        } catch (IOException e) {
            System.out.println("Save Button Clicked :: IOException");
            Constant.savedStudentID = this.settingSIDInputForm.getText();
            System.out.println("Save Button Clicked :: Saved Content : " + Constant.savedStudentID);
            try {
                FileManager.getInstance().writeStudentNumber(Constant.savedStudentID);
            }catch(IOException e2){
                System.err.println("Something wrong during save StudentID, Please try save again");;
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
            System.err.println("Something wrong during load StudentID");
        }
    }
}
