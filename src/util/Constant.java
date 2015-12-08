package util;


/**
 * 프로젝트 진행 시 공통적으로 필요한 상수들을 정의해 놓은 클래스입니다. (정적 클래스)
 */
public interface Constant {
    /**
     * OS
     */
    String OS = System.getProperty("os.name").toLowerCase();

    /**
     * 알람큐가 불러오는 기간
     */
    long loadTermForQueue = 604800000L;
    long loadTerm = (loadTermForQueue / 1000 / 86400) + 1;

    /**
     * 빈 문자열입니다.
     */
    String EMPTY_STRING = "";

    /**
     * fxml 파일을 불러올 경로
     */
    String viewPackage = "/view/";
    enum ViewPath {
        DayScheduleListView(Constant.viewPackage + "DayScheduleListView.fxml"),
        CalendarView(Constant.viewPackage + "CalendarView.fxml"),
        ScheduleEditorView(Constant.viewPackage + "ScheduleEditorView.fxml"),
        SettingView(Constant.viewPackage + "SettingView.fxml"),
        HomeworkListView(Constant.viewPackage + "HomeworkListView.fxml");

        public final String pathInfomation;

        ViewPath(String pathInfomation) {
            this.pathInfomation = pathInfomation;
        }
    }
}
