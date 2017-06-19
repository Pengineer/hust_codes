package csdc.tool.webService;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.xml.soap.SOAPBody;

/**
 * soap报文处理工具
 * @author zhangnan
 * @version v1.0
 * 2014-4-3
 * 2014-7-28 多人访问调整
 */
public class SOAPEnvTool {
	//定义Soap报文中的节点，双方引用规范
	//请求
	public static final String REQUEST = "request";
	public static final String REQUEST_SERVICE = "service";
	public static final String REQUEST_METHOD = "method";
	public static final String REQUEST_ARGUMENTS = "arguments";
	//请求_握手
	public static final String REQUEST_SERVICE_CONTROL = "ControlService";
	public static final String REQUEST_METHOD_HANDSSHAKE = "requestHandsShake";
	public static final String REQUEST_ARGUMENTS_SHAKEMATERIAL = "shakeMaterial";//单个参数名
	//应答
	public static final String RESPONSE = "response";
	public static final String RESPONSE_TIME = "time";
	public static final String RESPONSE_TYPE = "type";
	public static final String RESPONSE_CONTENT = "content";
	//应答_返回
	public static final String RESPONSE_TYPE_ERROR = "error";//异常类型
	public static final String RESPONSE_TYPE_SHAKEENDS = "shakeEnds";//握手结果
	public static final String RESPONSE_TYPE_DATA = "data";//一般服务返
	public static final String RESPONSE_TYPE_NOTICE = "notice";// 由中间层向上"返回"
	public static final String RESPONSE_NODEVALUE_EMPTY = "notused";//无用的空置信息
	
	/**
	 * 将指定错误原因放入session传递,为C提供反馈
	 * @param session
	 * @param soapInfo
	 * @param reason
	 * @return
	 */
	public static boolean genErrorSOAP(HttpSession session,Map<String, String> Map,String reason){
		if(Map.get("error") != null){
			return true;
		}else{
			Map.put("error", reason);
			session.setAttribute("soapInfo", Map);
			return true;//down chain
		}
	}
	
	/**
	 * 获取session的soapInfo
	 * 有则获取，无则重建
	 */
	public static Map<String, String> getSoapInfo(HttpSession session){
		Map<String, String> map = null;
		if(null == session.getAttribute("soapInfo")){
			map = new HashMap<String, String>();
			session.setAttribute("soapInfo", map);
		}
		return map;
	}
	
	/**
	 * 本服务只提供唯一入口
	 * 返回请求内容，便于后续处理如签名等
	 */
	public static String getRequestContent(SOAPBody bdy){
		String content = bdy.getChildNodes().item(0).getChildNodes().item(0).getTextContent();
		if(content == null){
			return null;
		}
		return content;
	}

}
