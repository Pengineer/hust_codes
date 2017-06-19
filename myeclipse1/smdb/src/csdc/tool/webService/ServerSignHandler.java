package csdc.tool.webService;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.apache.tools.ant.types.resources.selectors.Date;


public class ServerSignHandler implements SOAPHandler<SOAPMessageContext> {
	String localNameSpace = "http://server.webService.service.csdc/";
	@Override
	public boolean handleMessage(SOAPMessageContext ctx) {
		Boolean out_b = (Boolean) ctx.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
		HttpServletRequest request = (HttpServletRequest)ctx.get(SOAPMessageContext.SERVLET_REQUEST);
		SOAPMessage msg = ctx.getMessage();
		System.out.println("******ServerSignHandler*****"+new Date());
		if(out_b) {
			try {
				SOAPEnvelope env;
				env = msg.getSOAPPart().getEnvelope();
				SOAPHeader hdr = env.getHeader();
				SOAPBody  bdy = env.getBody();
				//获取visitorInfoBean
				String userMark = (String) request.getAttribute("userMark");				
				if(!WSServerSecurityTool.visitorBeansMap.containsKey(userMark)) {
				    //用户标识不再记录中，则新建一个用户bean，并把reconTag设置为3
				    VisitorInfoBean errorBean = new VisitorInfoBean(userMark,3);
				    WSServerSecurityTool.visitorBeansMap.put(userMark, errorBean);
				}
				VisitorInfoBean beanSelect = (VisitorInfoBean) WSServerSecurityTool.visitorBeansMap.get(userMark);
				int reconnStateInt = beanSelect.getVisitorTag();
				if (hdr == null) 
					hdr = env.addHeader();
				//返回服务端的状态信息， ServerInforsTag 
				//每次SOAP返回必有的子标签visitorTag
				QName serverInfosQName = new QName(localNameSpace, "ServerInformationsTag");
				SOAPHeaderElement serverInfosHElemt = hdr.addHeaderElement(serverInfosQName);
				serverInfosHElemt.addChildElement("visitorTag").addTextNode(Integer.toString(reconnStateInt));
				switch (reconnStateInt) {
				case 0:
					//对返回内容加密，并签名处理，添加serverSignValue
					String genContentHex = SOAPEnvTool.getRequestContent(bdy);
					//返回的服务内容先加密，后签名 ，加密算用用户SOAP请求时的算法类型
					String genContentCode = WSServerSecurityTool.doEncryProcess(genContentHex, beanSelect.getVisitorSecretKey(), beanSelect.getVisitorAlgType());
					bdy.removeChild(bdy.getChildNodes().item(0));
					QName operate = new QName("http://server.webService.service.csdc/", "operateResponse", "ns2");
					SOAPElement operateElement = bdy.addChildElement(operate);
					QName returnName = new QName("return");
					operateElement.addChildElement(returnName).addTextNode(genContentCode);	
					//签名
					String genSignValue = WSServerSecurityTool.signText(genContentCode);
					serverInfosHElemt.addChildElement("serverSignValue").addTextNode(genSignValue);
					break;
				case 1:
					//返回协serverCertificate,serverSignValue
					String certificateString = WSServerSecurityTool.getServerCertificateAsString();
					serverInfosHElemt.addChildElement("serverCertificate").addTextNode(certificateString);
					//添加签名
					String serviceContentHex = SOAPEnvTool.getRequestContent(bdy);
					String signvalueValue = WSServerSecurityTool.signText(serviceContentHex);
					serverInfosHElemt.addChildElement("serverSignValue").addTextNode(signvalueValue);
					break;
				case 2://断开连接处理
					//对返回内容加密，并签名处理，添加serverSignValue
					String disconnContentHex = SOAPEnvTool.getRequestContent(bdy);
					//返回的服务内容先加密，后签名 ，加密算用用户SOAP请求时的算法类型
					String disconnContentCode = WSServerSecurityTool.doEncryProcess(disconnContentHex, beanSelect.getVisitorSecretKey(), beanSelect.getVisitorAlgType());
					bdy.removeChild(bdy.getChildNodes().item(0));
					QName operateDisconn = new QName("http://server.webService.service.csdc/", "operateResponse", "ns2");
					SOAPElement operateElementDisconn = bdy.addChildElement(operateDisconn);
					QName returnNameDisconn = new QName("return");
					operateElementDisconn.addChildElement(returnNameDisconn).addTextNode(disconnContentCode);	
					//签名
					String disconnSignValue = WSServerSecurityTool.signText(disconnContentCode);
					serverInfosHElemt.addChildElement("serverSignValue").addTextNode(disconnSignValue);
					//断开操作后的清理工作
					WSServerSecurityTool.visitorBeansMap.remove(beanSelect.getVisitorMark());// 全局变量中visitorInfoBean对象清除
					if (WSServerSecurityTool.visitorBeansMap.isEmpty()) {
						WSServerSecurityTool.visitorBeansMap = null;// 全局变量清除工作
					}
					break;
				case 3:
					// 错误提示：不添加服务端证书，不对返回内容签名，（默认返回服务编码格式仍是utf-8）
					//因为是错误提示，所以这个Bean是为了传递错误消息临时创建的，必须清除
					WSServerSecurityTool.visitorBeansMap.remove(userMark);//清理全局变量中visitorInfoBean对象
					if(WSServerSecurityTool.visitorBeansMap.isEmpty()) {
						WSServerSecurityTool.visitorBeansMap = null;//全局变量清除工作
					}
	                break;
				case 4:
					//直接服务返回,并删除直接访问对象的visitorInfoBean
					WSServerSecurityTool.visitorBeansMap.remove(userMark);//清理全局变量中visitorInfoBean对象
					if(WSServerSecurityTool.visitorBeansMap.isEmpty()) {
						WSServerSecurityTool.visitorBeansMap = null;//全局变量清除工作
					}
					break;
				default:
					break;
				}
				msg.saveChanges();
				return true;
			} catch (SOAPException e) {
				e.printStackTrace();
			}
		}else{
			System.out.println("<------none-----");
		}
		return true;
	}

	@Override
	public boolean handleFault(SOAPMessageContext context) {
		return false;
	}

	@Override
	public void close(MessageContext context) {
	}

	@Override
	public Set<QName> getHeaders() {
		return null;
	}

}
