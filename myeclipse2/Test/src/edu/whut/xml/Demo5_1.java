package edu.whut.xml;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;

/**
 *当XML文档的层次结构较复杂时，对XML文档的读取就会显得很费劲，我们一般采用XPath API进行XML文档
 *的读取操作。
 */

public class Demo5_1 {	
	public static void main(String[] args) throws Exception {
		SAXReader reader = new SAXReader();
		Document document = reader.read("src/edu/whut/xml/book.xml");
		
		//selectSingleNode：返回第一个匹配节点
		String author = document.selectSingleNode("//作者").getText();
		System.out.println(author);
	}

}
