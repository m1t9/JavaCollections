package com.javarush.task.task33.task3309;

import org.w3c.dom.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.util.regex.Pattern;

/*
Комментарий внутри xml
*/
public class Solution {
    public static String toXmlWithComment(Object obj, String tagName, String comment) throws ParserConfigurationException,
            JAXBException, TransformerException {

        JAXBContext context = JAXBContext.newInstance(obj.getClass());
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.newDocument();
        marshaller.marshal(obj, document);

        NodeList nodeList = document.getElementsByTagName("*");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            replaceNode(node, document);
            if (node.getNodeName().equals(tagName)) {
                Comment comment1 = document.createComment(comment);
                node.getParentNode().insertBefore(comment1, node);
            }
        }

//        TransformerFactory transformerFactory = TransformerFactory.newDefaultInstance();
//        Transformer transformer = transformerFactory.newTransformer();
//        StringWriter writer = new StringWriter();
//        transformer.transform(new DOMSource(document), new StreamResult(writer));
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.STANDALONE, "no");

        StringWriter writer = new StringWriter();
        transformer.transform(new DOMSource(document), new StreamResult(writer));
        return writer.toString();
    }

    // thanks to Alexander https://gitlab.resprojects.ru/mrresident
    private static void replaceNode(Node node, Document document) {
        if ((node.getNodeType() == 3) && (Pattern.compile(".*[<>&'\"].*").matcher(node.getTextContent()).find())) {
            Node newNode = document.createCDATASection(node.getNodeValue());
            node.getParentNode().replaceChild(newNode, node);
        }

        NodeList childNodeList = node.getChildNodes();
        for (int i = 0; i < childNodeList.getLength(); i++) {
            replaceNode(childNodeList.item(i), document);
        }
    }

    public static void main(String[] args) throws JAXBException, ParserConfigurationException, TransformerException {
        // thanks to Igor https://javarush.ru/users/1387481
        System.out.println(Solution.toXmlWithComment(new First(), "second", "COMMENT"));
    }
}
