package util;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import schedule.Homework;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Lumin on 2015-11-30.
 * 포탈과 관련된 정보를 Parse 해주는 기능을 제공하는 클래스입니다.
 */
public class PortalXmlParser {

    private PortalXmlParser() {

    }

    /**
     * 주어진 과제 리스트 Xml 내용으로부터 과제가 담겨있는 과목의 ID들을 가져옵니다.
     *
     * @param homeworkXmlContent 과제 리스트를 담고 있는 Xml 내용을 가리킵니다.
     * @return 과목 ID들의 Set을 반환합니다.
     */
    public static Map<Integer, String> parseHomeworkLectureIDList(String homeworkXmlContent) {
        Map<Integer, String> homeworkLectureMap = new HashMap<>();

        try {
            InputSource inputSource = new InputSource(new StringReader(homeworkXmlContent));
            Document homeworkDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputSource);
            XPath xmlPath = XPathFactory.newInstance().newXPath();

            // vector id = plan을 담는 노드를 탐색
            NodeList studentPlanList = (NodeList) xmlPath.compile("map/vector[@id='plan']/map[@id]").evaluate(homeworkDocument, XPathConstants.NODESET);

            for (int i = 0; i < studentPlanList.getLength(); i++) {
                NodeList detailPlanList = studentPlanList.item(i).getChildNodes();

                // detailPlanList.item(1) => sumgubun 노드를 불러움
                if (detailPlanList.item(1).getAttributes().item(0).getTextContent().equals("과제방")) {
                    String homeworkEndPeriod = detailPlanList.item(2).getAttributes().item(0).getTextContent();
                    homeworkEndPeriod = homeworkEndPeriod.substring(homeworkEndPeriod.lastIndexOf(" ") + 1);
                    String lectureName = detailPlanList.item(3).getAttributes().item(0).getTextContent();
                    int lectureNumber = Integer.parseInt(detailPlanList.item(4).getAttributes().item(0).getTextContent());

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                    Calendar homeworkEndCalendar = Calendar.getInstance();
                    homeworkEndCalendar.setTime(dateFormat.parse(homeworkEndPeriod));

                    homeworkLectureMap.put(lectureNumber, lectureName);
                }
            }

        } catch (ParserConfigurationException | ParseException e) {
            e.printStackTrace();
            System.out.println("PortalXmlParser::parseHomeworkLectureIDList - Parser를 올바르게 설정할 수 없습니다.");
        } catch (IOException | SAXException e) {
            e.printStackTrace();
            System.out.println("PortalXmlParser::parseHomeworkLectureIDList - Document 인스턴스 생성 중 문제가 발생하였습니다.");
        } catch (XPathExpressionException e) {
            e.printStackTrace();
            System.out.println("PortalXmlParser::parseHomeworkLectureIDList - XPath에서 실행하는 표현식이 올바르지 않습니다.");
        }

        return homeworkLectureMap;
    }

    /**
     * 주어진 과제방 Xml 내용으로부터 과제의 상세한 내역을 가져옵니다.
     *
     * @param homeworkXmlContent 과제방에 요청한 결과가 담겨있는 Xml을 가리킵니다.
     * @return Homework형 List를 반환합니다.
     */
    public static List<Homework> parseHomeworkList(String homeworkXmlContent) {
        List<Homework> homeworks = new ArrayList<>();

        try {
            InputSource inputSource = new InputSource(new StringReader(homeworkXmlContent));
            Document homeworkDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputSource);
            XPath xmlPath = XPathFactory.newInstance().newXPath();

            // vector id = plan을 담는 노드를 탐색
            NodeList totalHomeworkList = (NodeList) xmlPath.compile("map/vector[@id='tasklist']/map[@id]").evaluate(homeworkDocument, XPathConstants.NODESET);

            for (int i = 0; i < totalHomeworkList.getLength(); i++) {
                NodeList detailHomeworkList = totalHomeworkList.item(i).getChildNodes();
                int pumpIndex = 0;

                if (detailHomeworkList.item(7).getNodeName().equals("taskopenyn")) {
                    pumpIndex = 1;
                }

                String currentHomeworkStatus = detailHomeworkList.item(5 + pumpIndex).getAttributes().item(0).getTextContent();
                String homeworkName = detailHomeworkList.item(2).getAttributes().item(0).getTextContent();
                String homeworkEndTime = detailHomeworkList.item(4).getAttributes().item(0).getTextContent();

                // detailHomeworkList.item(5) => taskendyn 노드를 불러움
                if (currentHomeworkStatus.equals("ING")) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
                    Calendar homeworkEndCalendar = Calendar.getInstance();

                    homeworkEndCalendar.setTime(dateFormat.parse(homeworkEndTime));


                    Homework homeworkInst = new Homework(homeworkName, homeworkEndCalendar);
                    homeworks.add(homeworkInst);

                }
            }

        } catch (ParserConfigurationException | ParseException e) {
            e.printStackTrace();
            System.err.println("PortalXmlParser::parseHomeworkList - Parser를 올바르게 설정할 수 없습니다.");
        } catch (IOException | SAXException e) {
            e.printStackTrace();
            System.err.println("PortalXmlParser::parseHomeworkList - Document 인스턴스 생성 중 문제가 발생하였습니다.");
        } catch (XPathExpressionException e) {
            e.printStackTrace();
            System.err.println("PortalXmlParser::parseHomeworkList - XPath에서 실행하는 표현식이 올바르지 않습니다.");
        }

        return homeworks;
    }

    public boolean parseLoginResult(String loginXmlContent) {
        try {
            InputSource inputSource = new InputSource(new StringReader(loginXmlContent));
            Document homeworkDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputSource);
            XPath xmlPath = XPathFactory.newInstance().newXPath();

            // vector id = plan을 담는 노드를 탐색
            NodeList loginResultNode = (NodeList) xmlPath.compile("html/body/div/form/input[@name='callsrc']").evaluate(homeworkDocument, XPathConstants.NODESET);

            for (int i = 0; i < loginResultNode.getLength(); i++) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
