package network;

import util.PortalXmlParser;
import util.Restourant;

import java.util.Date;

/**
 * Created by Lumin on 2015-11-30.
 */
public class NetworkTestMain {
    public static void main(String[] args) throws Exception{
        PortalXmlParser parser = new PortalXmlParser();
        String content = PortalHttpRequest.getHomeworkList("20146824");
        String content_2 = PortalHttpRequest.getHomeworkDetailTime("20146824", 97157);
        String content_3 = PortalHttpRequest.getMealList(new Date(System.currentTimeMillis()), Restourant.Dormitory);

        parser.parseMealList(content_3);
        parser.parseHomeworkTime(content_2);
        parser.parseHomeworkList(content);
        System.out.println();
    }
}