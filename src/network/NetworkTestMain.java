package network;

import util.PortalXmlParser;

/**
 * Created by Lumin on 2015-11-30.
 */
public class NetworkTestMain {
    public static void main(String[] args) throws Exception{
        PortalXmlParser parser = new PortalXmlParser();
        String content = PortalHttpRequest.getHomeworkList("20146824");
        String content_2 = PortalHttpRequest.getHomeworkDetailTime("20146824", 97325);

        parser.parseHomeworkTime(content_2);
        parser.parseHomeworkList(content);
        System.out.println();
    }
}
