package edu.whut.xml;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

/*
 * function:获取XML文档指定内容（标签内容为例）
 */

public class Demo3_2 {

	/**
	 * @param args
	 * @throws Exception 
	 * @throws ParserConfigurationException 
	 */
	public static void main(String[] args) throws ParserConfigurationException, Exception {
		//1，创建解析工厂
		SAXParserFactory factory = SAXParserFactory.newInstance();
		
		//2，得到解析器
		SAXParser sp = factory.newSAXParser();
		
		//3，由解析器得到读取器,再用读取器去读XML文档(dom解析是直接用解析器去解析XML文档)
		XMLReader reader = sp.getXMLReader();
		
		//4，设置内容处理器，HandlerList相当于事件监听器，没读取到一处，就会执行相应的方法。
		reader.setContentHandler(new TagValueHandler());
		
		//5，读取XML文档内容，开始解析
		reader.parse("src/edu/whut/xml/book.xml");
	}
}

/**
 * function：获取指定标签的内容
 * 
 *实际开发一般不会像Demo3_1中那样去直接实现ContentHandler接口，这样的话要覆写的方法太多，因此
 *我们一般继承已实现ContentHandler接口的子类，然后覆写其中我们需要的方法即可。
 */
class TagValueHandler extends DefaultHandler{
	private String name;             //解析到的当前标签名
	private int expectNumber = 2;    //期望解析到的第几个作者
	private int currentNumber = 0;   //当前解析到的第几个作者
	
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		name = qName;
		if(name.equals("作者")){
			currentNumber++;
		}
	}
	
	@Override
	public void characters(char[] ch, int start, int length)//"作者".equals(name)
			throws SAXException {		
		//注意不能写成name.equals("作者")，因为</书名>，</作者>，</售价>之后的空格会被当成
		//内容的空的字符串，此时name=null，也就没有equals方法了
		if("作者".equals(name) && currentNumber == expectNumber){
			System.out.println(new String(ch,start,length));
		}
		
		/* 或则写成：
		 if(name != null && name.equals("作者") && currentNumber == expectNumber){
			System.out.println(new String(ch,start,length)); 
		 }*/
		 
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		name = null;
	}
	
}
