package view.event;

import extfx.scene.control.RestrictiveTextField;
import fileManage.FileManager;
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
        FileManager fileManager = new FileManager();
        try {
            System.out.println("Save Button Clicked :: Inputform Content : " + this.settingSIDInputForm.getText());
            System.out.println("Save Button Clicked :: Loaded Content : " + fileManager.readStudentNumber());

            // Try to invoke exception.
            Long.parseLong(this.settingSIDInputForm.getText());
            Constant.savedStudentID = this.settingSIDInputForm.getText();
            fileManager.writeStudentNumber(Constant.savedStudentID);

            System.out.println("Save Button Clicked :: Saved Content : " + Constant.savedStudentID);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Save Button Clicked :: IOException");
        }
    }
}
