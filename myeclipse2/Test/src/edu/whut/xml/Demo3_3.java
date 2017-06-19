package edu.whut.xml;

/**
 * 在实际开发中，经常要将待解析的数据封装到对象中，本Demo就是将书封装成对象，然后add到list集合中
 * 
 * SAX解析很容易出现空指针异常，因为标签后的空格会被读取，经常要进行断点调试。
 */

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

public class Demo3_3 {
	
	public static void main(String[] args) throws Exception{
	
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser sp = factory.newSAXParser();
		XMLReader reader = sp.getXMLReader();
		reader.setContentHandler(new BeanListHandler());//为读取器注册一个监听器
		reader.parse("src/edu/whut/xml/book.xml");
	}
}

class BeanListHandler extends DefaultHandler{
	private String currentTag;
	private String name;
	private String author;
	private String price;
	Demo3_3_Book book;
	List<Demo3_3_Book> list = new ArrayList<Demo3_3_Book>();
	
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		currentTag = qName;
		if("书".equals(currentTag)){
			book = new Demo3_3_Book();
		}
	}
	
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		if("书名".equals(currentTag)){
			name = new String(ch,start,length);
			book.setName(name);
		}
		if("作者".equals(currentTag)){
			author = new String(ch,start,length);
			book.setAuthor(author);
		}
		if("售价".equals(currentTag)){
			price = new String(ch,start,length);
			book.setPrice(price);
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if("书".equals(qName)){
			list.add(book);
			book = null;
		}
		currentTag = null;   //没有这一句就会报出空指针异常,在书的结束标签之后，book=null， 但currentTag还是等于"售价"，读到</book>之后的空格时，执行character方法，与第三个if匹配，可是book=null，因此空指针异常
	}

	@Override
	public void endDocument() throws SAXException {
		for(Demo3_3_Book bk : list){
			System.out.println(bk.getName()+","+bk.getAuthor()+","+bk.getPrice());
		}
	}
	
}
