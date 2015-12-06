package util;

import javafx.collections.ObservableList;
import schedule.Schedule;

import java.util.ArrayList;
import java.util.Date;

/**
 * 프로젝트 진행 시 공통적으로 필요한 상수들을 정의해 놓은 클래스입니다. (정적 클래스)
 */
public class SharedPreference {
    /**
     * 빈 문자열입니다.
     */
    public static final String EMPTY_STRING = "";

    /**
     * 더미 학번입니다.
     */
    public static final String DUMMY_STUDENT_ID = "20000000";

    /**
     * 공유 객체들
     */

    public static String savedStudentID = EMPTY_STRING;
    public static final ArrayList<Homework> homeworkList = new ArrayList<>();


    /**
     * View를 위한 상수
     */

    /**
     * fxml 파일을 불러올 경로를 저장
     */
    public static final String viewPackage = "/view/";
    public static final String DayScheduleListView = SharedPreference.viewPackage+"DayScheduleListView.fxml";
    public static final String CalendarView = SharedPreference.viewPackage+"CalendarView.fxml";
    public static final String ScheduleEditorView = SharedPreference.viewPackage+"ScheduleEditorView.fxml";
    public static final String SettingView = SharedPreference.viewPackage+"SettingView.fxml";

    /**
     * DateScheduleView를 불러오기 위해 쓸 공유 객체
     */
    public static Date curDate = null;
    public static ArrayList<Schedule> curScheduleList = null;

    /**
     * ScheduleEditorView를 불러오기 위해 쓸 공유 객체
     */
    public static Date editingDate = null;
    public static boolean editMode = false;
    public static Schedule editingSchedule = null;
    public static ObservableList<Schedule> editingScheduleList = null;

    /**
     * SharedPreference 클래스의 인스턴스를 초기화합니다. SharedPreference 클래스는 정적 클래스이므로 외부에서 인스턴스화 할 수 없습니다.
     */
    private SharedPreference() {

    }
}
