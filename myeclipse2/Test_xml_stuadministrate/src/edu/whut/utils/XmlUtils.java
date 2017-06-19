package edu.whut.utils;
/**
 * �˰���Ϊ���ι��̵Ĺ��߰��������XML�ĵ��Ĺ��߰����ṩ������������
 * 
 * 1��public static Document getDocument()����ȡXML�ĵ���Document����
 * 2��public static void write2XML(Document document)�����������µ�XML�ĵ���
 */
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class XmlUtils {
	public static String path = "src/students.xml"; 
	 
	public static Document getDocument() throws ParserConfigurationException, SAXException, IOException{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		return builder.parse(path);
	}
	
	public static void write2XML(Document document) throws FileNotFoundException, TransformerException{
		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer trans = factory.newTransformer();
		trans.transform(new DOMSource(document), new StreamResult(new FileOutputStream(
				path)));
	}
}
