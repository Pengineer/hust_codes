package csdc.tool.webService.smdb.client;

import java.io.IOException;
import java.security.cert.Certificate;
import java.util.Date;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.w3c.dom.Element;




public class ClientVSignHandler implements SOAPHandler<SOAPMessageContext> {
	private final boolean see_b = false;
	public boolean handleMessage(SOAPMessageContext ctx) {
		Boolean out_b = (Boolean) ctx.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
		SOAPMessage msg = ctx.getMessage();
		SOAPEnvelope env;
		if(!out_b){
			try {
				
				if(see_b) {
					try {
						System.out.println("\nSOAP显示：client收到服务证书的的soap消息：");
						msg.writeTo(System.out);
					} catch (IOException e) {
						e.printStackTrace();
					}
				} 
				
				env = msg.getSOAPPart().getEnvelope();
				SOAPHeader hdr = env.getHeader();
				SOAPBody  body = env.getBody();
				if (hdr == null)
					return SOAPEnvTool.changeContent(body, "S缺少header标签！");
				//server返回的SOAP都有visitorTag籽标签
				Element serverInforsElement = (Element) hdr.getElementsByTagName("ServerInformationsTag").item(0);
				if(null == serverInforsElement) {
					return SOAPEnvTool.changeContent(body, "S缺少ServerInformationsTag标签！");
				}
				String visitorTag = serverInforsElement.getElementsByTagName("visitorTag").item(0).getTextContent();
				int reconnStateInt = Integer.parseInt(visitorTag);
				switch (reconnStateInt) {
				case 0:
					//取出serverSignValue，验证签名，并对返回内容并解密
					WSClientSecurityTool.reConn_tag = 0;//一般安全操作，设置安全状态
					String genSignValue = null;
					String genContentCode = null;
					genSignValue = serverInforsElement.getElementsByTagName("serverSignValue").item(0).getTextContent();
					genContentCode = SOAPEnvTool.getRequestContent(body);
					if(!WSClientSecurityTool.verifySign(genContentCode, genSignValue, WSClientSecurityTool.serverCerReceive)){
						return SOAPEnvTool.changeContent(body, "S提供服务内容签名验证失败！");
					}
					//解密还原
					String genContentHex = WSClientSecurityTool.doDecryProcess(genContentCode, WSClientSecurityTool.secretKey, WSClientSecurityTool.algorithmType);
					SOAPEnvTool.setSOAPBodyContent(body, genContentHex);
					break;
                case 1:
                	//连接建立过程：获取标签serverCertificate,serverSignValue
                	String serverCerString = null;
                	String serverSignValue = null;
                	serverCerString = serverInforsElement.getElementsByTagName("serverCertificate").item(0).getTextContent();
                	serverSignValue = serverInforsElement.getElementsByTagName("serverSignValue").item(0).getTextContent();
					//验证证书
                	Certificate serverCerObj = WSClientSecurityTool.getCertificateFromStr(serverCerString);
					Date TimeNow = new Date();
					if(!WSClientSecurityTool.verifyCertificate(TimeNow, serverCerObj, WSClientSecurityTool.serverCAPath)){
						return SOAPEnvTool.changeContent(body, "S证书验证失败！");
					}
					WSClientSecurityTool.serverCerReceive = serverCerObj;
					//验证签名
					String connBackContent = SOAPEnvTool.getRequestContent(body);
					if(!WSClientSecurityTool.verifySign(connBackContent, serverSignValue, WSClientSecurityTool.serverCerReceive)){
						return SOAPEnvTool.changeContent(body, "S服务签名验证失败！");
					}
					break;
                case 2://连接断开，密文安全传输
                	//取出serverSignValue，验证签名，并对返回内容并解密
					WSClientSecurityTool.reConn_tag = 0;//一般安全操作，设置安全状态
					String disconnSignValue = null;
					String disconnContentCode = null;
					disconnSignValue = serverInforsElement.getElementsByTagName("serverSignValue").item(0).getTextContent();
					disconnContentCode = SOAPEnvTool.getRequestContent(body);
					if(!WSClientSecurityTool.verifySign(disconnContentCode, disconnSignValue, WSClientSecurityTool.serverCerReceive)){
						return SOAPEnvTool.changeContent(body, "S提供服务内容签名验证失败！");
					}
					//解密还原
					String disconnContentHex = WSClientSecurityTool.doDecryProcess(disconnContentCode, WSClientSecurityTool.secretKey, WSClientSecurityTool.algorithmType);
					SOAPEnvTool.setSOAPBodyContent(body, disconnContentHex);
                	//断开操作结束，设置为协商状态（默认）
					WSClientSecurityTool.reConn_tag = 1;
                	break;
                case 3:
                	//服务端错误信息返回，不进行任何处理，只传递错误消息
                	//并设置蜜月协商状态（默认）
                	WSClientSecurityTool.reConn_tag = 1;
                	break;
                case 4:
                	//直接服务返回，不做任何处理
                	break;
				default:
					break;
				}
				
			} catch (SOAPException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	
	public boolean handleFault(SOAPMessageContext ctx) {
        return true;  
	}
	
	public void close(MessageContext context) {
	}

	public Set<QName> getHeaders() {
		return null;
	}

}
