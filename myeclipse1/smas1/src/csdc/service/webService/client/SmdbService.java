package csdc.service.webService.client;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import csdc.tool.ApplicationContainer;
import csdc.tool.webService.smdb.client.Invoker;

/**
 * smas远程服务调用，面向smdb服务接口
 * @author zhangn
 * 2014-12-11
 */
public class SmdbService {
	
	Invoker invoker = null;
	
	public SmdbService() {
		invoker = new Invoker();
	}
	/**
	 * 生成初始化连接参数
	 * @return
	 */
	private String creatConnectionArg(String passport, String password) {
		//测试
		String securityWSPath = ApplicationContainer.sc.getRealPath("/keypair");
		String serverCAPath = securityWSPath + "/CSDCCA.cer";
		String clientCAPath = securityWSPath + "/SMAS.cer";
		String clientKeyStorePath = securityWSPath + "/SMAS.keystore";
		String clientKeyStorePassword = "CSDCSMAS";//----不同的客户端需要设置不同的密码
		String clientKeyAlias = "SMAS";//密钥别名
		String clientKeyPass = "CSDCSMAS";//密码
		String visitorPassport = passport;
		String visitorPassword = password;
		Document document = DocumentHelper.createDocument();
		Element argumentElement = document.addElement("argument");
		argumentElement.addElement("serverCAPath").setText(serverCAPath);
		argumentElement.addElement("clientCAPath").setText(clientCAPath);
		argumentElement.addElement("clientKeyStorePath").setText(clientKeyStorePath);
		argumentElement.addElement("clientKeyStorePassword").setText(clientKeyStorePassword);
		argumentElement.addElement("clientKeyAlias").setText(clientKeyAlias);
		argumentElement.addElement("clientKeyPass").setText(clientKeyPass);
		argumentElement.addElement("visitorPassport").setText(visitorPassport);
		argumentElement.addElement("visitorPassword").setText(visitorPassword);
		return document.asXML();
	}
	
	/**
	 * XML形式的请求字符串
	 * @param serviceName 请求放服务名称
	 * @param method 请求服务的方法
	 * @param argsMap 服务请求参数 Map<String,String>类型
	 * @return
	 */
	private String createOperateArg(String serviceName, String method, 
			Map<String,String> argsMap) {
			Document document = DocumentHelper.createDocument();
			Element response = document.addElement("request");
			response.addElement("service").setText(serviceName);// 服务名
			response.addElement("method").setText(method);// 方法名
			if (!argsMap.isEmpty()) {
				Element arguments = response.addElement("arguments");// 参数
				Iterator<String> names = argsMap.keySet().iterator();
				while (names.hasNext()) {
					String name = names.next();
					arguments.addElement(name).setText(argsMap.get(name).toString());
				}
			}
			return document.asXML();
	}
	/**
	 * 不带参数的服务请求请求字符串
	 * @param serviceName
	 * @param method
	 * @return
	 */
	private String createOperateArg(String serviceName, String method) {
		Map argsMap = new HashMap();
		return createOperateArg(serviceName, method, argsMap);
	}
	//封装安全请求连接关闭操作
	public String closeSecurityConnection() throws UnsupportedEncodingException {
		if (invoker == null) {
			invoker = new Invoker();
		}
		return invoker.closeConnection();
	}
	//封装安全请求连接
	public String OpenSecurityConnection(String passport, String password, String algorithmName) throws UnsupportedEncodingException, DocumentException {
		if (invoker == null) {
			invoker = new Invoker();
		}
		String argument = creatConnectionArg(passport, password);
		return invoker.connect(algorithmName, argument);
	}
	//封装安全请求(有参数)
	public String invokeSecurityService(String passport, String password, String serviceName, 
			String method, Map<String,String> argsMap ) throws UnsupportedEncodingException {
		if (invoker == null) {
			invoker = new Invoker();
		}
		//形成参数
		String argument = createOperateArg(serviceName, method, argsMap);
		return invoker.operate(passport, password, argument);
	}
	
	//封装安全请求(无参数)
	public String invokeSecurityService(String passport, String password, String serviceName, 
				String method ) throws UnsupportedEncodingException {
		if (invoker == null) {
			invoker = new Invoker();
		}
		//形成参数
		String argument = createOperateArg(serviceName, method);
		return invoker.operate(passport, password, argument);
	}
	//封装直接调用（有参数）
	public String invokeDirect(String passport, String password, String serviceName, 
			String method, Map<String,String> argsMap) throws UnsupportedEncodingException {
		if (invoker == null) {
			invoker = new Invoker();
		}
		//形成参数
		String argument = createOperateArg(serviceName, method, argsMap);
		return invoker.operateDirect(passport, password, argument);
	}
	//封装直接调用（无参数）
	public String invokeDirect(String passport, String password, String serviceName, 
			String method ) throws UnsupportedEncodingException {
		if (invoker == null) {
			invoker = new Invoker();
		}
		//形成参数
		String argument = createOperateArg(serviceName, method);
		return invoker.operateDirect(passport, password, argument);
	}
}
