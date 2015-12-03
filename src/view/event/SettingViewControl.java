package view.event;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * Created by CAUCSE on 2015-12-03.
 *
 * 설정 창에 달린 이벤트 리스너
 */
public class SettingViewControl {
    @FXML
    private Button settingSaveButton;

    @FXML
    protected void handleSaveButtonAction(ActionEvent event){
        System.out.println("Saved!");
    }
}
