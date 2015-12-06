package util;

/**
 * 프로젝트 진행 시 공통적으로 필요한 상수들을 정의해 놓은 클래스입니다. (정적 클래스)
 */
public interface SharedPreference {
    /**
     * 빈 문자열입니다.
     */
    String EMPTY_STRING = "";

    /**
     * fxml 파일을 불러올 경로
     */
    String viewPackage = "/view/";
    String DayScheduleListView = SharedPreference.viewPackage + "DayScheduleListView.fxml";
    String CalendarView = SharedPreference.viewPackage + "CalendarView.fxml";
    String ScheduleEditorView = SharedPreference.viewPackage + "ScheduleEditorView.fxml";
    String SettingView = SharedPreference.viewPackage + "SettingView.fxml";
    String HomeworkListView = SharedPreference.viewPackage + "HomeworkListView.fxml";
}
