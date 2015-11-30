package network;

/**
 * Created by Lumin on 2015-11-30.
 */
public class NetworkTestMain {
    public static void main(String[] args) {
        PortalPayloadFactory factory = new PortalPayloadFactory();
        //System.out.println(sendHttpGetRequest("http://codeforces.com/problemset/problem/1/A"));
        //System.out.println(sendHttpPostRequest("http://dev.wikinote.zeropage.org/FrontPage?edit", new String[]{"data"}, new String[]{"Again"}));
        System.out.println("------------------------------------------------------------------------------------");
        System.out.println(HttpRequest.sendHttpPostRequest("http://cautis.cau.ac.kr/LMS/LMS/prof/myp/pLmsMyp040/getStudLectureCourse.do", factory.getHomeworkRequestPayload(20146824)));
    }
}
