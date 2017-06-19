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


public class ServerSignHandler implements SOAPHandler<SOAPMessageContext> {
	String localNameSpace = "http://server.webService.service.csdc/";
	@Override
	public boolean handleMessage(SOAPMessageContext ctx) {
		Boolean out_b = (Boolean) ctx.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
		HttpServletRequest request = (HttpServletRequest)ctx.get(SOAPMessageContext.SERVLET_REQUEST);
		SOAPMessage msg = ctx.getMessage();
		if(out_b){
			try {
				SOAPEnvelope env;
				env = msg.getSOAPPart().getEnvelope();
				SOAPHeader hdr = env.getHeader();
				SOAPBody  bdy = env.getBody();
				if (hdr == null) 
					hdr = env.addHeader();
				String userMark = (String) request.getAttribute("userMark");				
				if(!WSServerSecurityTool.visitorBeansMap.containsKey(userMark)){//判断
				   VisitorInfoBean errorBean = new VisitorInfoBean(userMark,3);
				   WSServerSecurityTool.visitorBeansMap.put(userMark, errorBean);
				}
				VisitorInfoBean beanSelect = (VisitorInfoBean) WSServerSecurityTool.visitorBeansMap.get(userMark);
				int tag = beanSelect.getVisitorTag();
				int giveUP_i = tag;
				if(tag == 2){
					tag = 0;
				}else{
					giveUP_i = 0;
				}
				switch (tag) {
				case 0:
					QName qname = new QName(localNameSpace, "ReconTag");
					SOAPHeaderElement helemt = hdr.addHeaderElement(qname);
					//断开标志giveUP_i为0 一般安全通信，为2则进行断开连接收尾工作
					helemt.addChildElement("recon_tag").addTextNode(Integer.toString(giveUP_i));
					if(giveUP_i == 2){//清理工作
						WSServerSecurityTool.visitorBeansMap.remove(beanSelect.getVisitorMark());
					}
					String text = SOAPEnvTool.getRequestContent(bdy);
					//返回的服务内容先加密，后签名 
					String codeString = WSServerSecurityTool.DESEncry(text, beanSelect.getVisitorSecretKey());
					bdy.removeChild(bdy.getChildNodes().item(0));
					QName operate = new QName("http://server.webService.service.csdc/", "operateResponse", "ns2");
					SOAPElement operateElement = bdy.addChildElement(operate);
					QName returnName = new QName("return");
					operateElement.addChildElement(returnName).addTextNode(codeString);	
					//对密文签名
					String signvalue = WSServerSecurityTool.signText(codeString);
					QName qname1 = new QName(localNameSpace, "ServerDS");
					SOAPHeaderElement helemt1 = hdr.addHeaderElement(qname1);
					helemt1.addChildElement("signvalue").addTextNode(signvalue);
					msg.saveChanges();
					break;
				case 1:
					//返回协商状态消息---此时密钥已经生成，
					//可以根据次userMark，判断是否是自己的标识 获取，生成 绘画蜜月
					QName qname1_1 = new QName(localNameSpace, "ReconTag");
					SOAPHeaderElement helemt1_1 = hdr.addHeaderElement(qname1_1);
					helemt1_1.addChildElement("recon_tag").addTextNode(Integer.toString(beanSelect.getVisitorTag()));
					QName qname1_2 = new QName(localNameSpace, "ServerCer");
					SOAPHeaderElement helemt1_2 = hdr.addHeaderElement(qname1_2);
					String cer_str = WSServerSecurityTool.getServerCertificateAsString();
					helemt1_2.addChildElement("servervalue").addTextNode(cer_str);
					String text1_3 = SOAPEnvTool.getRequestContent(bdy);
					String signvalue1_3 = WSServerSecurityTool.signText(text1_3);
					QName qname1_3 = new QName(localNameSpace, "ServerDS");
					SOAPHeaderElement helemt1_3 = hdr.addHeaderElement(qname1_3);
					helemt1_3.addChildElement("signvalue").addTextNode(signvalue1_3);
					break;
				case 2:
					//执行情况和0一样，用giveUp_i 做区分标志，代码简洁处理
					break;
				case 3://完全裸奔状态 ， 错误提示：不添加服务端证书，不对返回内容签名，（默认返回服务编码格式仍是utf-8）
					 //因为是错误提示，所以只做清理工作
					 QName qname3_1 = new QName(localNameSpace, "ReconTag");
					SOAPHeaderElement helemt3_1 = hdr.addHeaderElement(qname3_1);
					//断开标志giveUP_i为0 一般安全通信，为2则进行断开连接收尾工作
					helemt3_1.addChildElement("recon_tag").addTextNode(Integer.toString(beanSelect.getVisitorTag()));
					WSServerSecurityTool.visitorBeansMap.remove(userMark);//清理
	                break;
				default:
					break;
				}
				msg.saveChanges();
				return true;
			} catch (SOAPException e) {
				e.printStackTrace();
			}
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
