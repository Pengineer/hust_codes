package edu.whut.xml;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

/**
 *使用JAXP进行sax解析：
 *sax采用事件处理的方式解析XML文件，利用sax解析XML文档，设计两个部分：解析器和事件处理器：
 *1，解析器可以使用jaxp的API创建，创建出SAX解析器后，即可以指定解析器去解析某个XML文档。
 *2，解析器采用sax方式在解析某个XML文档时，它只要解析到XML文档的一个组成部分，都会去调用事件处理器
 *   的一个方法，解析器在调用事件处理器的方法时，会把当前解析到的XML文档内容作为方法的参数传递给
 *   事件处理器。
 *3，事件处理器由程序员编写，程序员通过事件处理器中的方法的参数，就可以很轻松地得到sax解析器解析到
 *   的数据，从而可以决定如何对数据进行处理。
 *   
 *   （所谓事件驱动，是指一种基于回调（CALLBACK）机制的程序运行方法。）
 */
public class Demo3_1 {
	
	public static void main(String[] arg) throws Exception{
		//1，创建解析工厂
		SAXParserFactory factory = SAXParserFactory.newInstance();
		
		//2，得到解析器
		SAXParser sp = factory.newSAXParser();
		
		//3，由解析器得到读取器,再用读取器去读XML文档(dom解析是直接用解析器去解析XML文档)
		XMLReader reader = sp.getXMLReader();
		
		//4，设置内容处理器，HandlerList相当于事件监听器，没读取到一处，就会执行相应的方法。
		reader.setContentHandler(new HandlerList());
		
		//5，读取XML文档内容，开始解析
		reader.parse("src/edu/whut/xml/book.xml");
	}	
}

/**定义一个handler类来获取文档的所有内容（实际中一般继承DefaultHandler，覆写里面的需要的方法，而不会实现ContentHandler接口）*/
class HandlerList implements ContentHandler{
	
	//文档开始时，执行该方法
	@Override
	public void startDocument() throws SAXException {
		
	}
	
	/**当读到XML文档的一个标签时，执行该方法，处理标签出现时该做的动作。
	 * 参数：uri：名称空间 URI，如果元素没有名称空间 URI，或者未执行名称空间处理，则为空字符串
	 * 		 localName：本地名称（不带前缀），如果未执行名称空间处理，则为空字符串；
	 * 	     qName：限定名（带有前缀），如果限定名不可用，则为空字符串；
	 *       atts是标签的所有属性；
	 */
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes atts) throws SAXException {
		System.out.println("<"+qName+">");
		
		for(int i=0 ;atts!=null && i<atts.getLength() ; i++){
			String name = atts.getQName(i);
			String value =atts.getValue(name);
			System.out.println(name+"="+value);
		}
	}
	
    /**
     * 当读完一个标签对的起始标签后，也就是执行完startElement后，就会执行该方法，用于读取标签
     * 里面的字符串内容，有的有，有的没有，一般父类标签没有（无空格的）。
     * 参数：ch：读取到的XML文档中的所有字符(包括空格)，可以通过new String来输出字符数组的指定长度字符。
     *       start：本次读取到的字符在ch数组中的起始位置；
     *       length：本次读取到的字符的长度；
     */
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		String content = new String(ch,start,length);//将本次读取到的字符封装成字符串对象
		System.out.println(content);
	}

	//当读到XML文档的某一个标签的结尾时，就会执行该方法
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
	
	}

	@Override
	public void endPrefixMapping(String prefix) throws SAXException {
	
	}

	@Override
	public void ignorableWhitespace(char[] ch, int start, int length)
			throws SAXException {
	
	}

	@Override
	public void processingInstruction(String target, String data)
			throws SAXException {

	}

	@Override
	public void setDocumentLocator(Locator locator) {
	
	}

	@Override
	public void skippedEntity(String name) throws SAXException {

	}

	@Override
	public void startPrefixMapping(String prefix, String uri)
			throws SAXException {
	
	}
	@Override
	public void endDocument() throws SAXException {

	}
	
}
