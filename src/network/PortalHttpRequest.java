package network;


import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Created by Lumin on 2015-11-30.
 * 포탈에 관련된 HttpRequest를 요청하는 정적 클래스입니다.
 */
public class PortalHttpRequest {

    /**
     * PortalHttpRequest 인스턴스를 초기화합니다. PortalHttpRequest 클래스는 정적 클래스이므로 인스턴스화 할 수 없습니다.
     */
    private PortalHttpRequest() {

    }

    /**
     * 학번을 입력받아, 그에 해당하는 과제 리스트를 요청하는 Request를 보내 그 결과를 받아냅니다.
     * @param studentID 요청하고자 하는 학번을 가리킵니다.
     * @return Request 요청의 결과입니다. XML 형식의 문자열이 반환됩니다.
     */
    public static String getHomeworkLectureIDList(String studentID) throws IOException {
        PortalPayloadFactory payloadFactory = new PortalPayloadFactory();
        String payLoad = payloadFactory.getHomeworkRequestPayload(studentID);
        return HttpRequest.sendHttpPostRequest(RequestList.HomeworkLectureIDList.getMappedUrl(), payLoad, StandardCharsets.ISO_8859_1.toString());
    }

    /**
     * 학번과 과목 ID를 입력받아, 상세한 과제 정보를 요청하는 Request를 보내 그 결과를 받아냅니다.
     * @param studentID 요청하고자 하는 학번을 가리킵니다.
     * @param lectureID 요청하고자 하는 과목의 ID를 가리킵니다.
     * @return Request 요청의 결과입니다. XML 형식의 문자열이 반환됩니다.
     */
    public static String getHomeworkList(String studentID, int lectureID) throws IOException {
        PortalPayloadFactory payloadFactory = new PortalPayloadFactory();
        String payLoad = payloadFactory.getHomeworkDetailTimePayload(studentID, lectureID);

        return HttpRequest.sendHttpPostRequest(RequestList.HomeworkList.getMappedUrl(), payLoad, StandardCharsets.ISO_8859_1.toString());
    }


    /**
     * 요청할 수 있는 Request의 목록을 나열하는 열거형입니다.
     */
    public enum RequestList {
        /**
         * 과제 과목 ID 리스트를 불러오는 Request입니다.
         */
        HomeworkLectureIDList("http://cautis.cau.ac.kr/LMS/LMS/prof/myp/pLmsMyp040/getStudLectureCourse.do"),

        /**
         * 과제의 상세 정보를 불러오는 Request입니다.
         */
        HomeworkList("http://cautis.cau.ac.kr/LMS/LMS/std/lec/sLmsLec070/selectTaskList.do");

        final String mappedUrl;

        RequestList(String mappedUrl) {
            this.mappedUrl = mappedUrl;
        }

        /**
         * 해당 오브젝트에 Mapping 되어있는 Request URL을 가져옵니다.
         * @return 해당 오브젝트에 맞는 Request URL입니다.
         */
        public String getMappedUrl() {
            return this.mappedUrl;
        }
    }
}