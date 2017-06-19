package edu.whut.utils;
/**
 * 此包作为本次工程的工具包，是针对XML文档的工具包，提供了两个方法：
 * 
 * 1，public static Document getDocument()：获取XML文档的Document对象；
 * 2，public static void write2XML(Document document)：将操作更新到XML文档；
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
