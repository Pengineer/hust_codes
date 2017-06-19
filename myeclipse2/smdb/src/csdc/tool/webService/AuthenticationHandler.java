package csdc.tool.webService;

import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;



public class AuthenticationHandler implements SOAPHandler<SOAPMessageContext>{
	
	public boolean handleMessage(SOAPMessageContext ctx) {
		HttpServletRequest request = (HttpServletRequest)ctx.get(SOAPMessageContext.SERVLET_REQUEST); 
		HttpSession session = request.getSession();
		Map<String, String> soapInfo = SOAPEnvTool.getSoapInfo(session);
		Boolean out_b= (Boolean) ctx.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
		if (!out_b) {
			try {
				SOAPMessage msg = ctx.getMessage();
				SOAPHeader hdr = msg.getSOAPHeader();
				SOAPBody bdy = msg.getSOAPBody();
				if (hdr == null){
					return SOAPEnvTool.genErrorSOAP(session, soapInfo, "缺少header头信息,协议有误！");					
				}
				//取出用户标识，任何请求中都有用户标识
				String userMark = null;
            	if(hdr.getElementsByTagName("UserIdentificationCode").item(0) != null){
            		userMark = hdr.getElementsByTagName("UserIdentificationCode").item(0).getFirstChild().getTextContent();
            	}else{
            		return SOAPEnvTool.genErrorSOAP(session, soapInfo, "缺少用户标识！");
            	}
            	request.setAttribute("userMark", userMark);
            	VisitorInfoBean beanSelect = null;
				//检测握手标志
				String reconTag = null;
				if(hdr.getElementsByTagName("ReconTag").item(0) != null){
					reconTag = hdr.getElementsByTagName("ReconTag").item(0).getFirstChild().getTextContent();
				}
				else{
					return SOAPEnvTool.genErrorSOAP(session, soapInfo, "协议有误！");
				}
				int reConn_i = Integer.parseInt(reconTag);
				int giveUp_i = Integer.parseInt(reconTag);
				if(reConn_i == 2){
					reConn_i = 0;//断开连接利用0通道
				}else{
					giveUp_i = 0;
				}
				switch (reConn_i) {//当reCon_i为0，giveUp_i为2的情况是“断开连接”处理
				case 0:
					//区分是否是断开标志，giveUp_i或是0或是2
					beanSelect = (VisitorInfoBean) WSServerSecurityTool.visitorBeansMap.get(userMark);
					beanSelect.setVisitorTag(giveUp_i);//修改用户标记下的 recon状态标记,并使用其“密码属性”
					//
					SOAPElement authElement = null;
					if(hdr.getElementsByTagName("AuthClientPortPass").item(0) != null){
						authElement = (SOAPElement) hdr.getElementsByTagName("AuthClientPortPass").item(0);
					}
					else return SOAPEnvTool.genErrorSOAP(session, soapInfo, "缺少AuthClientPortPass元素，协议有误，匹配失败！");
					String username = null;
					String password = null;
					if(authElement.getElementsByTagName("Passport").item(0) != null){
						username = authElement.getElementsByTagName("Passport").item(0).getTextContent();
					}else return SOAPEnvTool.genErrorSOAP(session, soapInfo, "未检测到用户名/密码信息元素信息，匹配失败！");
					if(authElement.getElementsByTagName("Password").item(0) != null){
						password = authElement.getElementsByTagName("Password").item(0).getTextContent();
					}else return SOAPEnvTool.genErrorSOAP(session, soapInfo, "未检测到用户名/密码信息元素信息，匹配失败！");
					if( username == null || username.isEmpty() || password == null || password.isEmpty()) 
						return SOAPEnvTool.genErrorSOAP(session, soapInfo, "用户名或密码(空)信息无效，匹配失败！");
					String username2 = WSServerSecurityTool.DESDecry(username, beanSelect.getVisitorSecretKey());
					String password2= WSServerSecurityTool.DESDecry(password, beanSelect.getVisitorSecretKey());
					if(username2 != null && password2 != null){
						soapInfo.put("username", username2);
						soapInfo.put("password", password2);
					}else{
						System.out.println("解密失败！");
					}
					//对一般服务请求body下的内容进行签名验证
					String signGenReq = null;
					if(hdr.getElementsByTagName("ClientDS").item(0) != null){
						signGenReq = hdr.getElementsByTagName("ClientDS").item(0).getFirstChild().getTextContent();
					}else return SOAPEnvTool.genErrorSOAP(session, soapInfo, "一般服务请求报文：缺少ClientDS元素，协议有误，匹配失败！");
					String textCodeGenReq = SOAPEnvTool.getRequestContent(bdy);
					if(textCodeGenReq == null){
						return SOAPEnvTool.genErrorSOAP(session, soapInfo, "缺少相关内容！");
					}
					if(!WSServerSecurityTool.verifySign(textCodeGenReq, signGenReq,  beanSelect.getVisitorCertificate())){
						return SOAPEnvTool.genErrorSOAP(session, soapInfo, "C提供信息传输中发生变化，拒绝提供服务！");
					}
					//对一般服务请求body下的内容进行解密替换
					String textClearGenReq = WSServerSecurityTool.DESDecry(textCodeGenReq, beanSelect.getVisitorSecretKey());
					bdy.removeChild(bdy.getChildNodes().item(0));
					QName operate = new QName("http://server.webService.service.csdc/", "operate");
					SOAPElement operateElement = bdy.addChildElement(operate);
					QName arg0 = new QName("http://csdc.info/", "arg0");
					operateElement.addChildElement(arg0).addTextNode(textClearGenReq);
					msg.saveChanges();
					break;
                case 1:
                	//握手，蜜月交互，先C身份验证，完整性验证
                	String cerstr = null;
                	if(hdr.getElementsByTagName("ClientCer").item(0) != null){
                		cerstr = hdr.getElementsByTagName("ClientCer").item(0).getFirstChild().getTextContent();
                	}else return SOAPEnvTool.genErrorSOAP(session, soapInfo, "缺少ClientCer元素，协议有误，匹配失败！");
					if(cerstr == null){
						return SOAPEnvTool.genErrorSOAP(session, soapInfo, "客户端证书为空，匹配失败！");
					}
					Certificate certificateClient = WSServerSecurityTool.getCertificateFromStr(cerstr);
					Date TimeNow=new Date();
					if(!WSServerSecurityTool.verifyClientCert(TimeNow, certificateClient)){
						return SOAPEnvTool.genErrorSOAP(session, soapInfo, "此证书不被信任，匹配失败！");
					}
					//完整性验证
					//通过验证的证书放在visitorInforBean对象中
					String sign = null;
					if(hdr.getElementsByTagName("ClientDS").item(0) != null){
						sign = hdr.getElementsByTagName("ClientDS").item(0).getFirstChild().getTextContent();
                	}else return SOAPEnvTool.genErrorSOAP(session, soapInfo, "握手服务请求报文：缺少ClientDS元素，协议有误，匹配失败！");					
					String textString = SOAPEnvTool.getRequestContent(bdy);
					if(textString == null){
						return SOAPEnvTool.genErrorSOAP(session, soapInfo, "缺少相关内容！");
					}
					if(!WSServerSecurityTool.verifySign(textString, sign, certificateClient)){
						return SOAPEnvTool.genErrorSOAP(session, soapInfo, "C提供信息传输中发生变化，拒绝提供服务！");
					}
				    //对一般服务请求body下的内容进行解密替换
					bdy.removeChild(bdy.getChildNodes().item(0));
					QName operate2 = new QName("http://server.webService.service.csdc/", "operate");
					SOAPElement operateElement2 = bdy.addChildElement(operate2);
					QName arg02 = new QName("http://csdc.info/", "arg0");
					operateElement2.addChildElement(arg02).addTextNode(textString);
					msg.saveChanges();
					//首次访问
					//通过验证---添加VisitorInforBean对象,并放入全局变量中的visitorMap中。
					//在Map中用subjetName，用在索引主键。
					VisitorInfoBean visitorBean = new VisitorInfoBean(userMark,1);
					visitorBean.setVisitorCertificate((X509Certificate)certificateClient);
					Map visitorsMap = WSServerSecurityTool.visitorBeansMap;
					if(visitorsMap == null){
						visitorsMap = new HashMap<String, VisitorInfoBean>();
					}
					visitorsMap.put(userMark, visitorBean);
					WSServerSecurityTool.setVisitorBeansMap((HashMap<String, VisitorInfoBean>) visitorsMap);
					break;
                case 2:
                	//用户断开标志，整合到通道0中，代码简洁
                	//断开连接的通信信息也是通过密文安全传输，则必须在ServerSignHandler中case 0，完成用户访问对象的清除工作             
                	
                	break;
                case 3://完全裸奔状态 ， 请求
                	break;
                	
				default:
					break;
				}
				session.setAttribute("soapInfo", soapInfo);//传递
			} catch (SOAPException e) {
				e.printStackTrace();
			} 
		} 
		return true; 
	}
	
	public void close(MessageContext messageContext) {
	
	}

	public boolean handleFault(SOAPMessageContext ctx) {
        return true;  
	}

	public Set<QName> getHeaders() {
		return null;
	}
   
}
