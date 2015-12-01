package network;

import util.Constant;

/**
 * Created by Lumin on 2015-11-30.
 * 포탈에 관련된 Request를 요청할 때, Payload를 간편하게 구성해주는 클래스입니다.
 */
public class PortalPayloadFactory {

    /**
     * PortalPayloadFactory 인스턴스를 초기화합니다.
     */
    public PortalPayloadFactory() {

    }

    /**
     * 과제방 목록을 불러오는 Request Payload를 불러옵니다.
     * @param studentID Request Payload를 구성하기 위한 학번입니다.
     * @return 과제방 목록을 불러오기 위한 Request Payload입니다. 입력한 학번에 해당되는 Payload가 반환됩니다.
     */
    public String getHomeworkRequestPayload(String studentID) {
        return "<map><userId value=\""+ studentID + "\"/><groupCode value=\"cau\"/></map>";
    }

    /**
     * 주어진 과목의 과제방에 접근할 수 있는 Request Payload를 불러옵니다.
     * @param studentID 접근할 학번입니다.
     * @param lectureID 과제방에 접근할 과목의 ID를 가리킵니다.
     * @return 과목 과제방에 접근할 수 있는 Request Payload입니다. 입력한 학번에 해당되는 Payload가 반환됩니다.
     */
    public String getHomeworkDetailTimePayload(String studentID, int lectureID) {
        return "<map><lectureNo value=\"" + lectureID + "\"/><userId value=\"" + studentID + "\"/></map>";
    }
}
