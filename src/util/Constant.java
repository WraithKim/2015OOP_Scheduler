package util;

/**
 * 프로젝트 진행 시 공통적으로 필요한 상수들을 정의해 놓은 클래스입니다. (정적 클래스)
 */
public interface Constant {
    /**
     * 빈 문자열입니다.
     */
    String EMPTY_STRING = "";

    /**
     * fxml 파일을 불러올 경로
     */
    String viewPackage = "/view/";
    String DayScheduleListView = Constant.viewPackage + "DayScheduleListView.fxml";
    String CalendarView = Constant.viewPackage + "CalendarView.fxml";
    String ScheduleEditorView = Constant.viewPackage + "ScheduleEditorView.fxml";
    String SettingView = Constant.viewPackage + "SettingView.fxml";
    String HomeworkListView = Constant.viewPackage + "HomeworkListView.fxml";
}
