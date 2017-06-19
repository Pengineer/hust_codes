package edu.whut.xml;

import java.io.FileOutputStream;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * 利用DOM4j开发包来解析XML文档，结合了DOM解析和SAX解析功能
 */

public class Demo4 {

	public static void main(String[] args) throws Exception {
	//	read();
	//	readAttr();
	//	add1();
	//	add2();
	//	delete();
		update();
	}
	
	/*读取指定标签*/
	public static void read() throws Exception{		
		//1，得到SAX读取器
		SAXReader reader = new SAXReader();
		//2，得到文档的Document对象
		Document document = reader.read("src/edu/whut/xml/book.xml");
		//3，通过根节点获取其他节点（比JAXP复杂一点）
		Element root = document.getRootElement();
		Element book = root.elements("书").get(1);
		String name = book.element("书名").getText();
		System.out.println(name);
		
	}
	
	/*读取指定标签的属性值*/
	public static void readAttr() throws Exception{
		SAXReader reader = new SAXReader();
		Document document = reader.read("src/edu/whut/xml/book.xml");
		Element root = document.getRootElement();
		Element book = root.elements("书").get(1);
		Attribute attr = book.element("书名").attribute("name");
		String name = attr.getValue();
		//或则这样写：String name1 = book.element("书名").attributeValue("name");
		System.out.println(name);
	}
	
	/*为指定标签添加子标签： <新售价>30</新售价>*/
	public static void add1() throws Exception{
		SAXReader reader = new SAXReader();
		Document document = reader.read("src/edu/whut/xml/book.xml");
		Element root = document.getRootElement();
		Element book = root.elements("书").get(1);
		book.addElement("新售价").setText("30.00RMB");
		
		//将修改后的document写到文件中，其中涉及到编码格式的问题。不管XML文档是什么编码，document
		//在内存中的编码存在形式都是UTF-8，FileWriter数据流默认编码是GB2312（系统内存中）。
		//因此，XMLWriter writer = new XMLWriter(new FileWriter());得到的肯定是乱码。
		
		//----第一种解决方式：传入可以指定编码方式的Writer子类：OutputStreamWriter
		//XMLWriter writer = new XMLWriter(new OutputStreamWriter(new FileOutputStream("src/edu/whut/xml/book1.xml"),"UTF-8"));
		
		//----第二种解决方法：如果XML文档就是GB2312的编码形式，这时就要为XMLWriter指定一个编码格式，
		//                   将GB2312的数据流以GB2312的编码方式写入到XML文档中。
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("GB2312");	
		XMLWriter writer = new XMLWriter(new FileOutputStream("src/edu/whut/xml/book1.xml"),format);
		//或XMLWriter writer = new XMLWriter(new OutputStreamWriter(new FileOutputStream("src/edu/whut/xml/book1.xml"),"gb2312"),format);
		writer.write(document);
		writer.close();
	}
	
	/*向指定标签的指定位置处添加子标签，默认是在最后面添加（上面add1方法就是）*/
	public static void add2() throws Exception{
		SAXReader reader = new SAXReader();
		Document document = reader.read("src/edu/whut/xml/book.xml");
		
		Element book = document.getRootElement().element("书");//得到第一本书
		List<Element> list = book.elements(); //得到第一本书的所有子节点
		
		Element price = DocumentHelper.createElement("新售价");//通过DocumentHelper创建标签
		price.setText("60.00RMB");
		list.add(2, price);
		
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("GB2312");	
		XMLWriter writer = new XMLWriter(new FileOutputStream("src/edu/whut/xml/book1.xml"),format);
		writer.write(document);
		writer.close();
	}
	
	/*删除指定位置标签*/
	public static void delete() throws Exception{
		SAXReader reader = new SAXReader();
		Document document = reader.read("src/edu/whut/xml/book1.xml");
		
		Element book = document.getRootElement().elements("书").get(0);//得到第一本书
		List<Element> list = book.elements();
		
		list.remove(2);
		
		/*或则用下面的方法：
		 * Element price = document.getRootElement().element("书").element("售价");
		 * price.getParent().remove(price);
		 */
		
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("GB2312");
		
		XMLWriter writer = new XMLWriter(new FileOutputStream("src/edu/whut/xml/book1.xml"),format);
		writer.write(document);
		writer.close();
	}
	
	/*更新标签内容*/
	public static void update() throws Exception{
		SAXReader reader = new SAXReader();
		Document document = reader.read("src/edu/whut/xml/book1.xml");
		
		Element book = document.getRootElement().element("书");
		List<Element> list = book.elements();
		Element price = list.get(2);
		price.setText("65.00RMB");
		
		OutputFormat format = new OutputFormat();
		format.setEncoding("GB2312");
		
		XMLWriter writer = new XMLWriter(new FileOutputStream("src/edu/whut/xml/book1.xml"));
		writer.write(document);
		writer.close();	
	}
}
