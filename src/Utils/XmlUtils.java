/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;
import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
/**
 *
 * @author simplesolution.co
 */
public class XmlUtils {

    public static String getValueOfNodeByKey(String path,String node,String key) {
        String result = "";
        try {
            File inputFile = new File(path);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName(node);
            result =nList.item(0).getAttributes().getNamedItem(key).getNodeValue();
        } catch (Exception e) {
        }
        return result;

    }
     public static int getPortLeapDroid(String path) {
        int result = -1;
        try {
            File inputFile = new File(path);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("GuestProperty");
            for (int i = 0; i < nList.getLength(); i++) {
                if(nList.item(i).getAttributes().getNamedItem("name").toString().contains("adb_port")){
                      return Integer.parseInt(nList.item(i).getAttributes().getNamedItem("value").getNodeValue()) -1;
                }
            }
            //result =nList.item(0).getAttributes().getNamedItem(key).getNodeValue();
        } catch (Exception e) {
        }
        return result;

    }
}
