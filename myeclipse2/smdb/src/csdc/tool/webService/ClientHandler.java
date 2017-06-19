package csdc.tool.webService;

import java.util.Set;
import javax.xml.namespace.QName;
import javax.xml.soap.*;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

/**
 * soap头认证
 * @author suwb
 *
 */
public class ClientHandler implements SOAPHandler<SOAPMessageContext> {
	
	public boolean handleMessage(SOAPMessageContext ctx) {

	//出站，即客户端发出请求前，添加表头信息
	Boolean request_p=(Boolean)ctx.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
	if(request_p){
		try {
			SOAPMessage msg=ctx.getMessage();
			SOAPEnvelope env=msg.getSOAPPart().getEnvelope();
			SOAPHeader hdr=env.getHeader();
			if(hdr==null) hdr=env.addHeader();
			
			//添加认证信息头
			
			//QName(String namespaceURI, String localPart, String prefix)
			//QName(String namespaceURI, String localPart)
			//QName(String localPart)
			//@param namespaceURI:QName的名称空间
		    //@param localPart:QName的本地部分
			//@param prefix:QName的前缀
			QName name=new QName("http://webservice.com/", "Authentication", "tns");
			SOAPHeaderElement header = hdr.addHeaderElement(name);
			
			//addChildElement(String localName, String prefix,String uri)
			//addChildElement(String localName, String prefix)
			//addChildElement(String localName)
			//@param uri:新元素所属空间名称URI
			//@param localName:新元素的本地名称
		    //@param prefix:新元素名称的空间前缀
			//见JDK 1.6的API
//			SOAPElement userElement = header.addChildElement("Username", "wsse");
//			userElement.addTextNode("admin");
			SOAPElement passElement = header.addChildElement("spPassword", "tns");
			passElement.addTextNode("sinoss#@mNqa2013");
			
			msg.saveChanges();
	
	        //把SOAP消息输出到System.out，即控制台
//			System.out.println("验证的头消息为： ");
//			msg.writeTo(System.out);
			
			return true;	
	    } catch (Exception e) {	
	       e.printStackTrace();	
	    }
	}	
	return false;	
	}
	
	@Override
	public boolean handleFault(SOAPMessageContext context) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public void close(MessageContext context) {
		// TODO Auto-generated method stub		
	}
	
	@Override
	public Set<QName> getHeaders() {
		// TODO Auto-generated method stub
		return null;
	}

}
