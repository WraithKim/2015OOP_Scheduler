package view.event;

import extfx.scene.control.RestrictiveTextField;
import util.FileManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import util.Constant;

import java.io.IOException;

/**
 * Created by Donghwan on 2015-12-03.
 * <p>
 * 설정 창에 달린 이벤트 리스너
 */
public class SettingViewControl {
    @FXML
    private RestrictiveTextField settingSIDInputForm;

    @FXML
    private Button settingSaveButton;

    @FXML
    protected void handleSaveButtonAction(ActionEvent event) throws ClassNotFoundException {
        try {
            System.out.println("Save Button Clicked :: Inputform Content : " + this.settingSIDInputForm.getText());
            System.out.println("Save Button Clicked :: Loaded Content : " + FileManager.getInstance().readStudentNumber());

            // Try to invoke exception.
            Long.parseLong(this.settingSIDInputForm.getText());
            Constant.savedStudentID = this.settingSIDInputForm.getText();
            FileManager.getInstance().writeStudentNumber(Constant.savedStudentID);

            System.out.println("Save Button Clicked :: Saved Content : " + Constant.savedStudentID);
        } catch (IOException e) {
            System.out.println("Save Button Clicked :: IOException");
            Constant.savedStudentID = this.settingSIDInputForm.getText();
            try {
                FileManager.getInstance().writeStudentNumber(Constant.savedStudentID);
            }catch(IOException e2){
                e2.printStackTrace();
            }
        }
    }
}
