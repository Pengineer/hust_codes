package csdc.tool.webService.smdb.client;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.soap.SOAPBody;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * soap报文处理工具
 * @author zhangnan
 * @version v1.0
 * 2014-4-2
 */
public class SOAPEnvTool {
	//定义Soap报文中的节点
	//请求
	public static final String REQUEST = "request";
	public static final String REQUEST_SERVICE = "service";
	public static final String REQUEST_METHOD = "method";
	public static final String REQUEST_ARGUMENTS = "arguments";
	//请求_握手
	public static final String REQUEST_SERVICE_CONTROL = "ControlService";
	public static final String REQUEST_METHOD_HANDSSHAKE = "requestHandsShake";
	public static final String REQUEST_ARGUMENTS_SHAKEMATERIAL = "shakeMaterial";//单个参数名
	//请求_一般服务
	public static final String REQUEST_ARGUMENTS_PROJECTTYPE = "projectType";//单个参数名	
	//应答
	public static final String RESPONSE = "response";
	public static final String RESPONSE_TIME = "time";
	public static final String RESPONSE_TYPE = "type";
	public static final String RESPONSE_CONTENT = "content";	
	//应答_返回
	public static final String RESPONSE_TYPE_ERROR = "error";//异常类型
	public static final String RESPONSE_TYPE_SHAKEENDS = "shakeEnds";//握手结果
	public static final String RESPONSE_TYPE_DATA = "data";//一般服务返	
	public static final String RESPONSE_TYPE_NOTICE = "notice";// 由中间层向上"返回"，	
	public static final String RESPONSE_NODEVALUE_EMPTY = "notused";//无用的空置信息
	
	/**
	 * 本服务只提供唯一入口
	 * 返回请求内容
	 * @param bdy
	 * @return
	 * 有则返回content
	 * 无则返回null
	 */
	public static String getRequestContent(SOAPBody bdy){
		String content = bdy.getChildNodes().item(0).getChildNodes().item(0).getTextContent();
		if(content == null){
			return null;
		}
		return content;
	}
	/**
	 * 重置SOAPBody中的内容
	 * @param bdy
	 * @param newContent
	 */
	public static void setSOAPBodyContent(SOAPBody bdy, String newContent ){
		bdy.getChildNodes().item(0).getChildNodes().item(0).setTextContent(newContent);
	}

	/**
	 * 解析应答内容（将底层返回结果还原编码，并解析并返回）
	 * 包含(1)对方反馈(2)安检异常
	 * @param content
	 * @return
	 * 成功：获取time，type，content节点的内容,
	 * 其中type类型有如下四种：error:异常类型,notice:提示类型, shakeEnds:握手结果, data:正常的服务请求返回内容.
	 * 注意：
	 * 如果type 是data 则content对应的值是数据 Element类型
	 * 如果type 非data类型，则content对应的是文本信息；
	 * 返回Map 
	 */
	public static Map<String, Object> parseResponse(String content) {
		HashMap<String, Object> responseMap = new HashMap<String, Object>();
		try {
			byte[] bytesContent = WSClientSecurityTool.hexStr2ByteArray(content);
			content = new String(bytesContent, "utf-8");
			Document doc = DocumentHelper.parseText(content);
			Element timeElement = (Element) doc.selectNodes("//time").get(0);
			responseMap.put("time", timeElement.getText());
			Element typElement = (Element) doc.selectNodes("//type").get(0);
			responseMap.put("type", typElement.getText());
			
			Element contentElement = (Element) doc.selectNodes("//content").get(0);
			if (!typElement.getText().equals("data")) {
				//如果为type不适data类型，则返回map中content的是对应的文本信息
				responseMap.put("content", contentElement.getText());
			} else {
				//如果为type是data类型，则返回map中content的是对应的Element集合
				responseMap.put("content", contentElement);
			}
		} catch (Exception e) {
			e.printStackTrace();
			responseMap.put("errorInfo", "服务返回解析内容出错！");
			System.out.println("服务内容，解析错误！");
		}
		return responseMap;
	}
	/**
	 * 解析应答内容（上层调用，正常编码，直接解析并返回）
	 * 注意：
	 * 如果type 是data 则content对应的值是数据 Element类型
	 * 如果type 非data类型，则content对应的是文本信息；
	 * @param normal
	 * @return
	 */
	public static Map<String, Object> parseNormalResponse(String normal) {
		HashMap<String, Object> responseMap = new HashMap<String, Object>();
		try {
			Document doc = DocumentHelper.parseText(normal);
			Element timeElement = (Element) doc.selectNodes("//time").get(0);
			responseMap.put("time", timeElement.getText());
			Element typElement = (Element) doc.selectNodes("//type").get(0);
			responseMap.put("type", typElement.getText());
			//如果为type不适data类型，则返回map中content的是对应的消息内容
			Element contentElement = (Element) doc.selectNodes("//content").get(0);
			if (!typElement.getText().equals("data")) {
				responseMap.put("content", contentElement.getText());
			} else {
				//取出contnet中的内容，
				responseMap.put("content", contentElement);
			}
		} catch (Exception e) {
			e.printStackTrace();
			responseMap.put("errorInfo", "服务返回解析内容出错！");
			System.out.println("服务内容，解析错误！");
		}
		return responseMap;
	}
	
	/**
	 * C检测S端调用，将检测结果以标准规范形式反馈给“服务调用者”
	 * （1）C端检测简化
	 * （2）避免放入request中交互与系统耦合
	 * （3）这样处理明显比之前合理
	 * 区别：C需要了解S身份检测结果，
	 * 		S只需将检测结果回送给C。
	 */
	public static boolean changeContent(SOAPBody bdy,String reason) {
		//若已经包含异常，不进行并更
		String ctentString = getRequestContent(bdy);
		Map<String, Object> fedbckInfo = parseResponse(ctentString);
		if(fedbckInfo.get("type") != null && fedbckInfo.get("type").equals("error")){
			return true;
		}else {
			String contString = toPraseContent("error",reason);
			bdy.getChildNodes().item(0).getChildNodes().item(0).setTextContent(contString);//getrequest, arg
			return true;
		}
	}
	
	/**
	 * 客户端调用，“形成”服务端返回的某种类型信息，用于客户端进行提示返回
	 * 统一返回内容格式
	 * @param answertype 响应内容类型
	 * error:异常类型
	 * warning:警告
	 * notice:通知
	 * data:正常的服务请求返回内容
	 * @param answerCtent
	 * 统一规格的消息响应
	 * @return 如果type:非data类型，则content是的值是string类型
	 */
	public static String toPraseContent(String type ,String content ) {
		Document document = DocumentHelper.createDocument();
		Element response = document.addElement("response");
		Element submitTime = response.addElement("time");//提交时间
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		submitTime.setText(df.format(new Date()));
		Element typeElem = response.addElement("type");
		typeElem.setText(type);
		Element conElem = response.addElement("content");
		conElem.setText(content);
		String xmlStr = document.asXML();
		String hexcontent = null;
		try {
			hexcontent = WSClientSecurityTool.byteArray2HexStr(xmlStr.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return hexcontent;
	}

	/**
	 * 通用
	 * 请求参数标准XML格式生成函数
	 * @param serviceName
	 * @param methodName
	 * @param argumentsMap key作为argument节点下的子节点，value作为子节点下的text值
	 * @return
	 */
	public static String toParseGeneralMethod(String serviceName,String methodName,Map argumentsMap) {
		String request = null;
		Document document = DocumentHelper.createDocument();
		Element requestElement = document.addElement("request");
		requestElement.addElement("service").setText(serviceName);//服务名
		requestElement.addElement("method").setText(methodName);//方法名
		
		if(!argumentsMap.isEmpty()){
			Element argumentElement = requestElement.addElement("arguments");//参数
			Iterator argNames = argumentsMap.keySet().iterator();
			while(argNames.hasNext()){
				String argNameString = (String) argNames.next();
				argumentElement.addElement(argNameString).setText((String) argumentsMap.get(argNameString));
			}
			try {
				request = WSClientSecurityTool.byteArray2HexStr(document.asXML().getBytes("utf-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return request;
	}
	
	//无参请求生成
	public static String toParseGeneralMethod(String serviceName,String methodName) {
		Map argsMap = new HashMap<String, String>();
	    argsMap.put("notused", "notused");
	    return toParseGeneralMethod(serviceName, methodName, argsMap);
	}
	
	//仅限于生成握手请求参数
	public static String toParseShakeHandMethod(String serviceName,String methodName,String argument) {
		String request = null;
		Document document = DocumentHelper.createDocument();
		Element requestElement = document.addElement("request");
		requestElement.addElement("service").setText(serviceName);//服务名
		requestElement.addElement("method").setText(methodName);//方法名
		Element argElement = requestElement.addElement("arguments");//参数
		argElement.addElement("shakeMaterial").setText(argument);
		try {
			request = WSClientSecurityTool.byteArray2HexStr(document.asXML().getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return request;
	}
}
