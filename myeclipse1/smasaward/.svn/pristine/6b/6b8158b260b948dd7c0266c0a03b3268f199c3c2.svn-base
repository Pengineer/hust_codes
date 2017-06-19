package csdc.tool.webService.smdb.client;

import java.io.IOException;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;


/**
 * 
 * 2014-7-18
 * 添加用户标识信息
 */
public class AuthClientHandler implements SOAPHandler<SOAPMessageContext> {
	private final boolean see_b = false;
	public boolean handleMessage(SOAPMessageContext context) {
		String localNameSpace = WSClientSecurityTool.getLocalname();
		Boolean out_b = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
		SOAPMessage msg = context.getMessage();
		if (out_b) {
			try {
				SOAPEnvelope env = msg.getSOAPPart().getEnvelope();
				SOAPHeader hdr = env.getHeader();
				SOAPBody  body = env.getBody();
				int reconnStatueInt = WSClientSecurityTool.reConn_tag;
				if (hdr == null)
					hdr = env.addHeader();
				//添加访问者信息标签，SOAP请求中必然有visitorMark,visitorTag
				QName visitorInfosQName = new QName(localNameSpace, "VisitorInformationsTag");
				SOAPHeaderElement visitorInfosHElemt = hdr.addHeaderElement(visitorInfosQName);
				visitorInfosHElemt.addChildElement("visitorMark").addTextNode(MD5.getMD5(WSClientSecurityTool.visit_name));
				visitorInfosHElemt.addChildElement("visitorTag").addTextNode(Integer.toString(reconnStatueInt));
				//分状态讨论
				switch (reconnStatueInt) {
				case 0:
					//添加visitorTag, visitorName, password, visitorSignValue
					//并对visitorName, password加密处理，先对请求内容加密，然后进行签名
					String codeName = null;
					String codePass = null;
					//加密
					codeName =  WSClientSecurityTool.doEncryProcess(WSClientSecurityTool.visit_name, WSClientSecurityTool.secretKey, WSClientSecurityTool.algorithmType);
					codePass =  WSClientSecurityTool.doEncryProcess(WSClientSecurityTool.visit_password, WSClientSecurityTool.secretKey, WSClientSecurityTool.algorithmType);
					visitorInfosHElemt.addChildElement("visitorName").addTextNode(codeName);
					visitorInfosHElemt.addChildElement("password").addTextNode(codePass);
					//加密请求内容
					String genealRequestCode = WSClientSecurityTool.doEncryProcess(SOAPEnvTool.getRequestContent(body),WSClientSecurityTool.secretKey, WSClientSecurityTool.algorithmType);
					SOAPEnvTool.setSOAPBodyContent(body, genealRequestCode);
					//签名加密结果
					String genderalReqCode = SOAPEnvTool.getRequestContent(body);
					String signValueGenReq = WSClientSecurityTool.signText(genderalReqCode,WSClientSecurityTool.clientKeyAlias,WSClientSecurityTool.clientKeyPass);
					visitorInfosHElemt.addChildElement("visitorSignValue").addTextNode(signValueGenReq);
					break;
				case 1://握手协商
					// 只在用户连接建立时，添加用户标志
					visitorInfosHElemt.addChildElement("visitorAlgorithmType").addTextNode(Integer.toString(WSClientSecurityTool.algorithmType));
					//添加证书
					String cerStr = WSClientSecurityTool.getExportedCertificateAsString(WSClientSecurityTool.clientCAPath);
					visitorInfosHElemt.addChildElement("visitorCertificate").addTextNode(cerStr);
					// 添加签名，针对Body中内容进行签名
					String encodedContent = SOAPEnvTool.getRequestContent(body);
					String signvalue = WSClientSecurityTool.signText(
							encodedContent,
							WSClientSecurityTool.clientKeyAlias,
							WSClientSecurityTool.clientKeyPass);
					visitorInfosHElemt.addChildElement("visitorSignValue").addTextNode(signvalue);
					break;
				case 2://断开连接
					//添加visitorTag, visitorName, password, visitorSignValue
					//并对visitorName, password加密处理，先对请求内容加密，然后进行签名
					String codeNameDisConn = null;
					String codePassDisConn = null;
					//加密
					codeNameDisConn =  WSClientSecurityTool.doEncryProcess(WSClientSecurityTool.visit_name, WSClientSecurityTool.secretKey, WSClientSecurityTool.algorithmType);
					codePassDisConn =  WSClientSecurityTool.doEncryProcess(WSClientSecurityTool.visit_password, WSClientSecurityTool.secretKey, WSClientSecurityTool.algorithmType);
					visitorInfosHElemt.addChildElement("visitorName").addTextNode(codeNameDisConn);
					visitorInfosHElemt.addChildElement("password").addTextNode(codePassDisConn);
					//加密请求内容
					String disConnRequestCode = WSClientSecurityTool.doEncryProcess(SOAPEnvTool.getRequestContent(body),WSClientSecurityTool.secretKey, WSClientSecurityTool.algorithmType);
					SOAPEnvTool.setSOAPBodyContent(body, disConnRequestCode);
					//签名加密结果
					String disConnReqCode = SOAPEnvTool.getRequestContent(body);
					String disConnSignValue = WSClientSecurityTool.signText(disConnReqCode,WSClientSecurityTool.clientKeyAlias,WSClientSecurityTool.clientKeyPass);
					visitorInfosHElemt.addChildElement("visitorSignValue").addTextNode(disConnSignValue);
					break;
				case 3:
					//服务端错误提示返回，此处做处理
					break;
				case 4:
					//直接服务调用，visitorName, password 处于明文状态，其他内容不做处理
					visitorInfosHElemt.addChildElement("visitorName").addTextNode(WSClientSecurityTool.visit_name);
					visitorInfosHElemt.addChildElement("password").addTextNode(WSClientSecurityTool.visit_password);
					break;
				default:
					break;
				}
				msg.saveChanges();
				if (see_b) {
					try {
						System.out.println("\nSOAP显示：client处理完毕发送到Server的SOAP内容");
						msg.writeTo(System.out);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				return true;
			} catch (SOAPException e) {
				e.printStackTrace();
			}
		}
		else {//服务端返回的
		}
		return true;
	}
	
	public boolean handleFault(SOAPMessageContext context) {
		SOAPMessage msg = context.getMessage();
		SOAPFault soapFault;
		try {
			soapFault = msg.getSOAPPart().getEnvelope().getBody().getFault();
			String messageString = soapFault.getFaultString();
			System.out.println("\n服务端用户名验证反馈:" + messageString);
		} catch (SOAPException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void close(MessageContext context) {
	}
	
	public Set<QName> getHeaders() {
		return null;
	}

}
