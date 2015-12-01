package util;

import jdk.internal.util.xml.impl.Input;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.StringReader;

/**
 * Created by Lumin on 2015-11-30.
 */
public class PortalXmlParser {
    public void parseHomeworkList(String homeworkXmlContent) {
        try {
            InputSource inputSource = new InputSource(new StringReader(homeworkXmlContent));
            Document homeworkDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputSource);
            XPath xmlPath = XPathFactory.newInstance().newXPath();

            // vector id = plan을 담는 노드를 탐색
            NodeList studentPlanList = (NodeList) xmlPath.compile("map/vector[@id='plan']/map[@id]").evaluate(homeworkDocument, XPathConstants.NODESET);

            System.out.println(studentPlanList.getLength());
            for (int i = 0; i < studentPlanList.getLength(); i++) {
                NodeList detailPlanList = studentPlanList.item(i).getChildNodes();
                // detailPlanList.item(1) => sumgubun 노드를 불러움
                System.out.println(detailPlanList.item(1).getAttributes().item(0).getTextContent());
                if (detailPlanList.item(1).getAttributes().item(0).getTextContent().equals("과제방")) {
                    int lectureNumber = 0;
                    
                }
            }

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            System.out.println("PortalXmlParser::parseHomeworkList - Parser를 올바르게 설정할 수 없습니다.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("PortalXmlParser::parseHomeworkList - Document 인스턴스 생성 중 문제가 발생하였습니다.");
        } catch (SAXException e) {
            e.printStackTrace();
            System.out.println("PortalXmlParser::parseHomeworkList - Document 인스턴스 생성 중 문제가 발생하였습니다.");
        } catch (XPathExpressionException e) {
            e.printStackTrace();
            System.out.println("PortalXmlParser::parseHomeworkList - XPath에서 실행하는 표현식이 올바르지 않습니다.");
        }
    }
}
