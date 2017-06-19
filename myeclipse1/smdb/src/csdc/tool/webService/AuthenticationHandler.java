package csdc.tool.webService;

import java.io.IOException;
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

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.hp.hpl.sparta.Node;



public class AuthenticationHandler implements SOAPHandler<SOAPMessageContext>{
	
	public boolean handleMessage(SOAPMessageContext ctx) {
		boolean see_b = true;//查看SOAP报文结构
		HttpServletRequest request = (HttpServletRequest)ctx.get(SOAPMessageContext.SERVLET_REQUEST); 
		HttpSession session = request.getSession();
		Map<String, String> soapInfo = SOAPEnvTool.getSoapInfo(session);
		Boolean out_b= (Boolean) ctx.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
		
		System.out.println("*****AuthenticationHandler******"+new Date());
		if (!out_b) {
			try {
				SOAPMessage msg = ctx.getMessage();
				if (see_b) {
					try {
						System.out.println("\nSOAP显示：client处理完毕发送到Server的SOAP内容");
						msg.writeTo(System.out);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				SOAPHeader hdr = msg.getSOAPHeader();
				SOAPBody bdy = msg.getSOAPBody();
				if (hdr == null){
					return SOAPEnvTool.genErrorSOAP(session, soapInfo, "缺少header头信息,协议有误！");					
				}
				//取出用户标识，任何请求中都有用户标识visitorMark和visitorTag
				//取出用户选用的加密算法类型，任何请求中都有此标识algorithmType
				String visitorMark = null;
				String visitorTag = null;
				VisitorInfoBean beanSelect = null;//根据visitorMark而产生或在全局变量visitorBeansMap获取
				Element visitorInforsElement = (Element) hdr.getElementsByTagName("VisitorInformationsTag").item(0);
				if(null == visitorInforsElement){
					return SOAPEnvTool.genErrorSOAP(session, soapInfo, "缺少VisitorInformationsTag标签！");
				}
				System.out.println("visitorInforsElement" + visitorInforsElement.toString());
				visitorMark = visitorInforsElement.getElementsByTagName("visitorMark").item(0).getTextContent();
//				visitorMark = visitorInforsElement.getElementsByTagName("visitorMark").item(0).getFirstChild().getNodeValue();
				
				NodeList visitorMarkNodeList = visitorInforsElement.getElementsByTagName("visitorMark");
//				System.out.println("//:" + visitorMarkNodeList.toString());
//				System.out.println("//:" + visitorMarkNodeList.item(0).getNodeName());
//				System.out.println("//:" + visitorMarkNodeList.item(0).getNodeValue());
//				System.out.println("//:" + visitorMarkNodeList.item(0).getTextContent());
//				System.out.println("//:" + visitorMarkNodeList.item(0).getFirstChild() == null ? "NULL值" : "可以获得firstChild");
//				System.out.println("//:" + visitorMarkNodeList.item(0).getFirstChild().getNodeName());
//				System.out.println("//:" + visitorMarkNodeList.item(0).getFirstChild().getNodeValue());
//				System.out.println("//:" + visitorMarkNodeList.item(0).getFirstChild().getTextContent());
//				System.out.println("visitorMark:" + visitorMark);
            	request.setAttribute("userMark", visitorMark);//
            	
            	visitorTag = visitorInforsElement.getElementsByTagName("visitorTag").item(0).getTextContent();
//            	visitorTag = visitorInforsElement.getElementsByTagName("visitorTag").item(0).getFirstChild().getNodeValue();
            	NodeList visitorTagNodeList = visitorInforsElement.getElementsByTagName("visitorTag");
//            	System.out.println("//:" + visitorTagNodeList.toString());
//            	System.out.println("//:" + visitorTagNodeList.item(0).getNodeName());
//				System.out.println("//:" + visitorTagNodeList.item(0).getNodeValue());
//				System.out.println("//:" + visitorTagNodeList.item(0).getTextContent());
//				System.out.println("//:" + visitorTagNodeList.item(0).getFirstChild() == null ? "NULL值" : "可以获得firstChild");
//				System.out.println("//:" + visitorTagNodeList.item(0).getFirstChild().getNodeName());
//				System.out.println("//:" + visitorTagNodeList.item(0).getFirstChild().getNodeValue());
//				System.out.println("//:" + visitorTagNodeList.item(0).getFirstChild().getTextContent());
//				System.out.println("visitorTag:" + visitorTag);
            	
            	int reConnStateInt = Integer.parseInt(visitorTag);
				switch (reConnStateInt) {
				case 0:
					//获取对象，获取visitorName， password， visitorSignValue；
					//并解密visitorName， password，然后验证签名
					String genNameCode = null;
					String genPasswordCode = null;
					String genSignValue = null;
					String username = null;
					String password = null;
					beanSelect = (VisitorInfoBean) WSServerSecurityTool.visitorBeansMap.get(visitorMark);
					beanSelect.setVisitorTag(reConnStateInt);//修改用户标记下的 recon状态标记
					genNameCode = visitorInforsElement.getElementsByTagName("visitorName").item(0).getTextContent();
					genPasswordCode = visitorInforsElement.getElementsByTagName("password").item(0).getTextContent();
					genSignValue = visitorInforsElement.getElementsByTagName("visitorSignValue").item(0).getTextContent();
					
					if(genNameCode == null || genNameCode.isEmpty() || genPasswordCode == null || genPasswordCode.isEmpty())
						return SOAPEnvTool.genErrorSOAP(session, soapInfo, "用户名或密码(空)信息无效，匹配失败！");
					username = WSServerSecurityTool.doDecryProcess(genNameCode, beanSelect.getVisitorSecretKey(), beanSelect.getVisitorAlgType());
					password= WSServerSecurityTool.doDecryProcess(genPasswordCode, beanSelect.getVisitorSecretKey(), beanSelect.getVisitorAlgType());
					soapInfo.put("username", username);
					soapInfo.put("password", password);
					//签名验证
					String genReqCode = SOAPEnvTool.getRequestContent(bdy);
					if(!WSServerSecurityTool.verifySign(genReqCode, genSignValue,  beanSelect.getVisitorCertificate())){
						return SOAPEnvTool.genErrorSOAP(session, soapInfo, "C签名验证失败！");
					}
					//对一般服务请求body下的内容进行解密替换
					String genReqText = WSServerSecurityTool.doDecryProcess(genReqCode, beanSelect.getVisitorSecretKey(), beanSelect.getVisitorAlgType());
					bdy.removeChild(bdy.getChildNodes().item(0));
					QName operate = new QName("http://server.webService.service.csdc/", "operate");
					SOAPElement operateElement = bdy.addChildElement(operate);
					QName arg0 = new QName("http://csdc.info/", "arg0");
					operateElement.addChildElement(arg0).addTextNode(genReqText);
					msg.saveChanges();
					break;
                case 1:
                	//握手过程：取算法类型，取证书并验证，去签名值并验证,最后保存此visitorMark对应的isitorInforBean
                	String algorithmType = null;
                	String certificateStr = null;
                	String signatureValue = null;
                	algorithmType = visitorInforsElement.getElementsByTagName("visitorAlgorithmType").item(0).getTextContent();
                	certificateStr = visitorInforsElement.getElementsByTagName("visitorCertificate").item(0).getTextContent();
                	signatureValue = visitorInforsElement.getElementsByTagName("visitorSignValue").item(0).getTextContent();
                	//验证证书
                	Certificate certificateClient = WSServerSecurityTool.getCertificateFromStr(certificateStr);
					Date TimeNow=new Date();
					if(!WSServerSecurityTool.verifyClientCert(TimeNow, certificateClient)){
						return SOAPEnvTool.genErrorSOAP(session, soapInfo, "此证书验证失败，证书不被信任或已经过期");
					}
					//验证签名
					String textString = SOAPEnvTool.getRequestContent(bdy);
					if(!WSServerSecurityTool.verifySign(textString, signatureValue, certificateClient)){
						return SOAPEnvTool.genErrorSOAP(session, soapInfo, "签名验证失败，请求内容发生变化！");
					}
				    //对一般服务请求body下的内容进行解密替换
					bdy.removeChild(bdy.getChildNodes().item(0));
					QName operate2 = new QName("http://server.webService.service.csdc/", "operate");
					SOAPElement operateElement2 = bdy.addChildElement(operate2);
					QName arg02 = new QName("http://csdc.info/", "arg0");
					operateElement2.addChildElement(arg02).addTextNode(textString);
					msg.saveChanges();
					//通过验证
					//首次访问建立对象：设定visitorMark,isitorTag,algorithmType,visitorCertificate ，并保存到全局变量
					VisitorInfoBean visitorBean = new VisitorInfoBean(visitorMark, 1);
					visitorBean.setVisitorAlgType(Integer.parseInt(algorithmType));//保存用用户所选的算法名字类型
					visitorBean.setVisitorCertificate((X509Certificate)certificateClient);
					Map visitorsMap = WSServerSecurityTool.visitorBeansMap;
					if(visitorsMap == null){
						visitorsMap = new HashMap<String, VisitorInfoBean>();
					}
					visitorsMap.put(visitorMark, visitorBean);
					WSServerSecurityTool.setVisitorBeansMap((HashMap<String, VisitorInfoBean>) visitorsMap);//首次建立全局变量保存
					break;
                case 2://连接断开，断开连接也是处在加密机制之下进行
                	//获取对象，获取visitorName， password， visitorSignValue；
					//并解密visitorName， password，然后验证签名
					String disconnNameCode = null;
					String disconnPasswordCode = null;
					String disconnSignValue = null;
					String usernameDisconn = null;
					String passwordDisconn = null;
					beanSelect = (VisitorInfoBean) WSServerSecurityTool.visitorBeansMap.get(visitorMark);
					beanSelect.setVisitorTag(reConnStateInt);//修改用户标记下的 recon状态标记
					disconnNameCode = visitorInforsElement.getElementsByTagName("visitorName").item(0).getTextContent();
					disconnPasswordCode = visitorInforsElement.getElementsByTagName("password").item(0).getTextContent();
					disconnSignValue = visitorInforsElement.getElementsByTagName("visitorSignValue").item(0).getTextContent();
					if(disconnNameCode == null || disconnNameCode.isEmpty() || disconnPasswordCode == null || disconnPasswordCode.isEmpty()) {
						return SOAPEnvTool.genErrorSOAP(session, soapInfo, "用户名或密码(空)信息无效！");
					}
					usernameDisconn = WSServerSecurityTool.doDecryProcess(disconnNameCode, beanSelect.getVisitorSecretKey(), beanSelect.getVisitorAlgType());
					passwordDisconn= WSServerSecurityTool.doDecryProcess(disconnPasswordCode, beanSelect.getVisitorSecretKey(), beanSelect.getVisitorAlgType());
					soapInfo.put("username", usernameDisconn);
					soapInfo.put("password", passwordDisconn);
					//签名验证
					String disconnReqCode = SOAPEnvTool.getRequestContent(bdy);
					if(!WSServerSecurityTool.verifySign(disconnReqCode, disconnSignValue,  beanSelect.getVisitorCertificate())){
						return SOAPEnvTool.genErrorSOAP(session, soapInfo, "C签名验证失败！");
					}
					//揭秘请求内容
					String disconnReqText = WSServerSecurityTool.doDecryProcess(disconnReqCode, beanSelect.getVisitorSecretKey(), beanSelect.getVisitorAlgType());
					bdy.removeChild(bdy.getChildNodes().item(0));
					QName operateDisconn = new QName("http://server.webService.service.csdc/", "operate");
					SOAPElement operateElementDisconn = bdy.addChildElement(operateDisconn);
					QName arg0Disconn = new QName("http://csdc.info/", "arg0");
					operateElementDisconn.addChildElement(arg0Disconn).addTextNode(disconnReqText);
					msg.saveChanges();
                	break;
                case 3:
                	//配合服务端传输错误信息而设置，此处不做任何处理
                	break;
                case 4:
                	//直接进行服务调用，每次访问都生成VisitorInforBean对象,并放入全局变量中的visitorMap中。
                	//并在SOAP返回的时候将此对象删除
					VisitorInfoBean visitorDirect = new VisitorInfoBean(visitorMark,4);//产生直接访问对象，设置状态标志4
					Map visitorsDirectMap = WSServerSecurityTool.visitorBeansMap;
					if(visitorsDirectMap == null){
						visitorsDirectMap = new HashMap<String, VisitorInfoBean>();
					}
					visitorsDirectMap.put(visitorMark, visitorDirect);
					WSServerSecurityTool.setVisitorBeansMap((HashMap<String, VisitorInfoBean>) visitorsDirectMap);//首次建立全局变量保存
					//获取用户表示信息
					String directReqName = null;
					String directReqPassword = null;
					directReqName = visitorInforsElement.getElementsByTagName("visitorName").item(0).getTextContent();
					directReqPassword = visitorInforsElement.getElementsByTagName("password").item(0).getTextContent();
					if(directReqName != null && directReqPassword != null) {
						soapInfo.put("username", directReqName);
						soapInfo.put("password", directReqPassword);
					} else {
						return SOAPEnvTool.genErrorSOAP(session, soapInfo, "用户名或密码(空)信息无效！");
					}
				default:
					break;
				}
				session.setAttribute("soapInfo", soapInfo);//传递
			} catch (SOAPException e) {
				e.printStackTrace();
			} 
		} else{
			System.out.println("--------none------>");
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
