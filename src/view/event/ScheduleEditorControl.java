package view.event;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 * Created by CAUCSE on 2015-12-03.
 *
 * 일정 편집 창에 달린 이벤트 리스너
 */
public class ScheduleEditorControl {

    @FXML
    private TextField titleTextField;

    @FXML
    private ToggleGroup priorityToggleGroup;
    // 우선순위를 나타내는 라디오버튼을 묶어놓은 그룹

    @FXML
    private ComboBox<String> hourComboBox;

    @FXML
    private ComboBox<String> minuteComboBox;

    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private Button scheduleSaveButton;

    @FXML
    protected void handleSaveButtonAction(ActionEvent event){

    }
}
