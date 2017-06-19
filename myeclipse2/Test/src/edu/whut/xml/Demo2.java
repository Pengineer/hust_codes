package edu.whut.xml;

import java.io.FileOutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * JAXP:
 *    1，JAXP开发包是J2SE的一部分，因此开发时不用额外导包，它由javax.xml、org.w3c.dom、org.xml.sax包及其子包组成；
 *    2，在javax.xml.parsers包中，定义了几个工厂类，程序员调用这些工厂类，可以得到xml文档的
 *       DOM或者SAX解析器，从而实现对xml文档的解析。
 */

/**
 * 使用JAXP进行DOm解析： 
 * 	  javax.xml.parsers包中的DocumentBuilderFactory用于创建DOM模式的解析器对象， DocumentBuilderFactory
 *    是一个抽象工厂类，它不能直接实例化，但该类提供了一个newInstance方法，这个方法会根据本地平台
 *    默认安装的解析器，自动创建一个工厂的对象并返回，然后通过该工厂的newDocumentBuilder方法返回
 *    一个DOM解析器DocumentBuilder。
 *    
 *    dom解析下，xml文档的每一个组成部分都会用一个对象表示，例如标签用Element、属性用Attr，但不
 *    管什么对象，都是Node的子类，所以在开发中，可以把获取到任意节点都当做Node对待。
 */

public class Demo2 {

	public static void main(String[] args) throws Exception {
		//1，创建DOM解析器工厂 
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		
		//2，得到DOM解析器
		DocumentBuilder builder = factory.newDocumentBuilder();

		//3，解析xml文档，得到代表文档的Document对象
		Document document = builder.parse("src/edu/whut/xml/book.xml");
		
		
		//---------------对文档的具体操作------------------------------		
	//	read1(document);
	//	System.out.println("-----------------------------");
	//	read2(document);
	//	System.out.println("-----------------------------");
	//	read3(document);
	//	System.out.println("-----------------------------");
	//	add1(document);
	//	System.out.println("-----------------------------");
	//	add2(document);
	//	System.out.println("-----------------------------");
	//	addAttr(document);
	//	System.out.println("-----------------------------");
	//	delete1(document);
	//	System.out.println("-----------------------------");
	//	delete2(document);
	//	System.out.println("-----------------------------");
		updata(document);
		System.out.println("-----------------------------");
	}

	/*获取指定标签内容*/
	private static void read1(Document document) {
		NodeList list = document.getElementsByTagName("书名");
		Node node = list.item(1);
		System.out.println(node.getTextContent());
	}
	
	/*打印所有标签：通过根节点遍历*/
	private static void read2(Document document) {
		Node root = document.getElementsByTagName("书架").item(0);
		list(root);
	}
	private static void list(Node node) {
		if(node instanceof Element){
			System.out.println(node.getNodeName());
		}
		NodeList childs = node.getChildNodes();
		for(int i=0 ; i<childs.getLength() ; i++){
			Node child = childs.item(i);
			list(child);			
		}		
	}
	
	/*获取标签的属性值*/
	private static void read3(Document document) {
		//为了使用更多的方法，将Node节点强转成子类对象
		Element element = (Element)document.getElementsByTagName("书名").item(0);
		String value = element.getAttribute("name");
		System.out.println(value); 
	}
	
	/*向xml文档中添加节点*/
	private static void add1(Document document) throws Exception {
		Element price = document.createElement("新售价");
		price.setTextContent("69.00RMB");
		
		Element book = (Element)document.getElementsByTagName("书").item(0);
		book.appendChild(price);//到此步，只是向内存中的book.xml文件添加了节点
		
		//下面就是通过内存中的book.xml来更新硬盘上的book.xml文件,工厂设计模式（API文档）
		TransformerFactory transfactory = TransformerFactory.newInstance();
		Transformer trans = transfactory.newTransformer();
		trans.transform(new DOMSource(document), new StreamResult(new FileOutputStream(
				"src/edu/whut/xml/book.xml")));
		
		System.out.println("add success");
	}
	
	/*向xml文档的指定位置处添加节点*/
	private static void add2(Document document) throws Exception {
		Element price = document.createElement("新售价");
		price.setTextContent("69.00RMB");
		
		//获取参考节点位置
		Element referencenode = (Element)document.getElementsByTagName("售价").item(0);
		
		Element book = (Element)document.getElementsByTagName("书").item(0);
		book.insertBefore(price, referencenode);
		
		//下面就是通过内存中的book.xml来更新硬盘上的book.xml文件
		TransformerFactory transfactory = TransformerFactory.newInstance();
		Transformer trans = transfactory.newTransformer();
		trans.transform(new DOMSource(document), new StreamResult(new FileOutputStream(
				"src/edu/whut/xml/book.xml")));

		System.out.println("add success");
	}

	/*未指定标签添加属性*/
	private static void addAttr(Document document) throws Exception {
		Element bookname = (Element)document.getElementsByTagName("书名").item(1);
		bookname.setAttribute("name", "cba");
		
		TransformerFactory transfactory = TransformerFactory.newInstance();
		Transformer trans = transfactory.newTransformer();
		trans.transform(new DOMSource(document), new StreamResult(new FileOutputStream(
				"src/edu/whut/xml/book.xml")));
	}
	
	/*删除节点*/
	private static void delete1(Document document) throws Exception{
		//得到要删除的节点
		Element oldprice = (Element)document.getElementsByTagName("售价").item(0);
		//得到要删除节点的父节点
		Element oldpricefather = (Element)document.getElementsByTagName("书").item(0);
		
		oldpricefather.removeChild(oldprice);
		
		TransformerFactory transfactory = TransformerFactory.newInstance();
		Transformer trans = transfactory.newTransformer();
		trans.transform(new DOMSource(document), new StreamResult(new FileOutputStream(
				"src/edu/whut/xml/book.xml")));
		System.out.println("delete success");
	}
	/*删除节点的第二种方法,该方法更常用，比如，它可以删除售价是XX的所有书*/
	private static void delete2(Document document) throws Exception{
		//得到要删除的节点
		Element oldprice = (Element)document.getElementsByTagName("售价").item(0);
		oldprice.getParentNode().removeChild(oldprice);
	
		//比如：oldprice.getParentNode().getParentNode().removeChild(oldprice.getParentNode());
		
		TransformerFactory transfactory = TransformerFactory.newInstance();
		Transformer trans = transfactory.newTransformer();
		trans.transform(new DOMSource(document), new StreamResult(new FileOutputStream(
				"src/edu/whut/xml/book.xml")));
		System.out.println("delete success");
	}
	
	/*更新节点内容*/
	private static void updata(Document document) throws Exception {
		Element oldprice = (Element)document.getElementsByTagName("售价").item(0);
		oldprice.setTextContent("69.00RMB");
		
		TransformerFactory transfactory = TransformerFactory.newInstance();
		Transformer trans = transfactory.newTransformer();
		trans.transform(new DOMSource(document), new StreamResult(new FileOutputStream(
				"src/edu/whut/xml/book.xml")));
		System.out.println("update success");
	}
}
