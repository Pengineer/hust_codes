package edu.hust.utils;

import java.io.File;
import java.io.FileOutputStream;

import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;


public class XmlUtils {
	private static String filepath;
	//得到user.xml文件经类加载器加载后的路径名。
	static{
		filepath = XmlUtils.class.getClassLoader().getResource("user.xml").getPath();
		filepath=filepath.replace("%20", " ");
	}
	
	public static Document getDocument() throws Exception{
		SAXReader reader = new SAXReader();
		Document document = reader.read(new File(filepath));
		return document;
	}
	
	public static void write2XML(Document document) throws Exception{
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");	
		XMLWriter writer = new XMLWriter(new FileOutputStream(filepath),format);
		writer.write(document);
		writer.close();
	}
}
