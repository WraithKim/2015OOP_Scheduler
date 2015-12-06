package util;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import schedule.Schedule;

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

    /**
     * PortaXmlParser 인스턴스를 초기화합니다.
     */
    public PortalXmlParser() {

    }

    /**
     * 주어진 과제 리스트 Xml 내용으로부터 과제가 담겨있는 과목의 ID들을 가져옵니다.
     *
     * @param homeworkXmlContent 과제 리스트를 담고 있는 Xml 내용을 가리킵니다.
     * @return 과목 ID들의 Set을 반환합니다.
     */
    public Set<Integer> parseHomeworkLectureIDList(String homeworkXmlContent) {
        Set<Integer> homeworkLectureIDSet = new TreeSet<>();

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
                    //String lectureName = detailPlanList.item(3).getAttributes().item(0).getTextContent();
                    int lectureNumber = Integer.parseInt(detailPlanList.item(4).getAttributes().item(0).getTextContent());
                    //String homeworkName = detailPlanList.item(5).getAttributes().item(0).getTextContent();

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                    Calendar homeworkEndCalendar = Calendar.getInstance();
                    homeworkEndCalendar.setTime(dateFormat.parse(homeworkEndPeriod));

                    homeworkLectureIDSet.add(lectureNumber);

                    //TODO 디버그 코드 지워야 함

                    //System.out.println("------------ Homework -----------------------");
                    //System.out.println("Homework Period : " + homeworkEndPeriod);
                    //System.out.println("Lecture Name : " + lectureName);
                    //System.out.println("Lecture Number : " + lectureNumber);
                    //System.out.println("Homework Name : " + homeworkName);
                    //System.out.println("------------ Homework End -----------------------");
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

        return homeworkLectureIDSet;
    }

    /**
     * 주어진 과제방 Xml 내용으로부터 과제의 상세한 내역을 가져옵니다.
     *
     * @param homeworkXmlContent 과제방에 요청한 결과가 담겨있는 Xml을 가리킵니다.
     * @return Homework형 List를 반환합니다.
     */
    public List<Homework> parseHomeworkList(String homeworkXmlContent) {
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
                //String homeworkStartTime = detailHomeworkList.item(3).getAttributes().item(0).getTextContent();
                String homeworkEndTime = detailHomeworkList.item(4).getAttributes().item(0).getTextContent();
                //int homeworkSubmitStudentNum = Integer.parseInt(detailHomeworkList.item(7 + pumpIndex).getAttributes().item(0).getTextContent());
                //int homeworkTotalStudentNum = Integer.parseInt(detailHomeworkList.item(8 + pumpIndex).getAttributes().item(0).getTextContent());

                // detailHomeworkList.item(5) => taskendyn 노드를 불러움
                if (currentHomeworkStatus.equals("ING")) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
                    Calendar homeworkEndCalendar = Calendar.getInstance();
                    //Calendar homeworkStartCalendar = Calendar.getInstance();

                    homeworkEndCalendar.setTime(dateFormat.parse(homeworkEndTime));
                    //homeworkStartCalendar.setTime(dateFormat.parse(homeworkStartTime));


                    Homework homeworkInst = new Homework(homeworkName, homeworkEndCalendar);
                    homeworks.add(homeworkInst);

                    //TODO 디버그 코드 지우기
                    //System.out.println("------------ Homework -----------------------");
                    //System.out.println("Homework StartTime : " + homeworkStartTime);
                    //System.out.println("Homework EndTime : " + homeworkEndTime);
                    //System.out.println("Homework TotalStudent : " + homeworkTotalStudentNum);
                    //System.out.println("Homework SubmitStudent : " + homeworkSubmitStudentNum);
                    //System.out.println("Homework Name : " + homeworkName);
                    //System.out.println("------------ Homework End -----------------------");
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

}
