package util;

/**
 * 프로젝트 진행 시 공통적으로 필요한 상수들을 정의해 놓은 클래스입니다. (정적 클래스)
 */
public class Constant {
    /**
     * 빈 문자열입니다.
     */
    public static final String EMPTY_STRING = "";

    /**
     * 더미 학번입니다.
     */
    public static final String DUMMY_STUDENT_ID = "20000000";


    public static String savedStudentID = EMPTY_STRING;

    /**
     * Constant 클래스의 인스턴스를 초기화합니다. Constant 클래스는 정적 클래스이므로 외부에서 인스턴스화 할 수 없습니다.
     */
    private Constant() {

    }
}
