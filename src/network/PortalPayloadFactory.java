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
    public String getHomeworkRequestPayload(int studentID) {
        return "<map><userId value=\" "+ studentID + "\"/><groupCode value=\"cau\"/></map>";
    }


    public String getMealRequestPayload() {
        return Constant.EMPTY_STRING    ;
    }
}
