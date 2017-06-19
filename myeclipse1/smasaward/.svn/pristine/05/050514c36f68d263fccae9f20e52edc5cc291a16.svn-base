package csdc.tool.webService.smdb.client;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * 2014-8-4  修改 添加多种加密算法选择功能； 添加“非安全”传输功能
 */
/**
 * 
 * @author zhangn
 * 2014-12-11
 * 客户端调用组件
 */
public class Invoker {
		
	private WebServicesService clientService = new WebServicesService();
	{
    	clientService.setHandlerResolver(new ClientHandlerResolver());
	}

	/**
	 * 安全访问---建立连接阶段
	 * @param algorithmName 加密算法选择：目前系统支持“DES”、“AES”、“DESede”三种算法类型
	 * @param argument 标准请求参数
	 * @return
	 * @throws DocumentException
	 * @throws UnsupportedEncodingException
	 */
	public String connect(String algorithmName, String argument) throws DocumentException, UnsupportedEncodingException {
		String shakeHandResult;
		Document document = DocumentHelper.parseText(argument);
		if(document.selectNodes("//serverCAPath").isEmpty()) {
			throw new DocumentException("serverCAPath节点不存在");
		} else if(document.selectNodes("//clientCAPath").isEmpty()) {
			throw new DocumentException("clientCAPath节点不存在");
		} else if(document.selectNodes("//clientKeyStorePath").isEmpty()) {
			throw new DocumentException("clientKeyStorePath节点不存在");
		}else if(document.selectNodes("//clientKeyStorePassword").isEmpty()) {
			throw new DocumentException("clientKeyStorePassword节点不存在");
		}else if(document.selectNodes("//clientKeyAlias").isEmpty()) {
			throw new DocumentException("clientKeyAlias节点不存在");
		}else if(document.selectNodes("//clientKeyPass").isEmpty()) {
			throw new DocumentException("clientKeyPass节点不存在");
		}else if(document.selectNodes("//visitorPassport").isEmpty()) {
			throw new DocumentException("visitorPassport节点不存在");
		}else if(document.selectNodes("//visitorPassword").isEmpty()) {
			throw new DocumentException("visitorPassword节点不存在");
		}
		//信息初始化
		WSClientSecurityTool.serverCAPath = ((Element) document.selectNodes("//serverCAPath").get(0)).getText();
		WSClientSecurityTool.clientCAPath = ((Element) document.selectNodes("//clientCAPath").get(0)).getText();
		WSClientSecurityTool.clientKeyStorePath = ((Element) document.selectNodes("//clientKeyStorePath").get(0)).getText();
		WSClientSecurityTool.clientKeyStorePassword = ((Element) document.selectNodes("//clientKeyStorePassword").get(0)).getText();
		WSClientSecurityTool.clientKeyAlias = ((Element) document.selectNodes("//clientKeyAlias").get(0)).getText();
		WSClientSecurityTool.clientKeyPass = ((Element) document.selectNodes("//clientKeyPass").get(0)).getText();
		WSClientSecurityTool.visit_name = ((Element) document.selectNodes("//visitorPassport").get(0)).getText();
		WSClientSecurityTool.visit_password = ((Element) document.selectNodes("//visitorPassword").get(0)).getText();

		//连接状态参数
		WSClientSecurityTool.reConn_tag = 1;//顺序
		int type = WSClientSecurityTool.getAlogrithmType(algorithmName);
		if(0 == type){
			shakeHandResult = SOAPEnvTool.toPraseContent("notice", "请使用以下密钥协商算法：DES，AES，DESede！");
			return new String(WSClientSecurityTool.hexStr2ByteArray(shakeHandResult), "utf-8");
		}
		WSClientSecurityTool.algorithmType = type;
		
		WSClientSecurityTool.keyAgreeInit();
		String content = clientService.getWebServicesPort().operate(WSClientSecurityTool.genShakeReqXmlStr());//
		//解析
		Map<String, Object> requestInfo = SOAPEnvTool.parseResponse(content);
		if(requestInfo.get("type") != null && requestInfo.get("type").equals("error")) {
			return new String(WSClientSecurityTool.hexStr2ByteArray(content), "utf-8");//错误XML信息向上递交
		}
		if(requestInfo.get("type") != null && requestInfo.get("type").equals("shakeEnds")) {
			String endString = (String) requestInfo.get("content");
			WSClientSecurityTool.servicePK = WSClientSecurityTool.hexStr2ByteArray(endString);
			if(WSClientSecurityTool.genSecretKey() != null){
				shakeHandResult = SOAPEnvTool.toPraseContent("notice", "握手成功");
				//修改状态信息
				WSClientSecurityTool.reConn_tag = 0;
				return new String(WSClientSecurityTool.hexStr2ByteArray(shakeHandResult), "utf-8");
			}else{
				WSClientSecurityTool.reConn_tag = 1;
			}
		}
		shakeHandResult = SOAPEnvTool.toPraseContent("notice", "握手失败");
		return new String(WSClientSecurityTool.hexStr2ByteArray(shakeHandResult), "utf-8");
	}
	/*
	 * 服务调用
	 * 注意:
	 * 上层与对底层的调用（交互）：正常字符串;<br>中间层对底层的调用（交互）：正常字符串<--->utf-8码文(hex转化)
	 * （operate操作都是argument转码过的十六进制）
	 */
	/**
	 * 安全访问---服务请求
	 * @param passport 用户名
	 * @param password 密码
	 * @param argument 标准格式的请求参数
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String operate(String passport, String password, String argument) throws UnsupportedEncodingException {
		String content = null;
		if(WSClientSecurityTool.reConn_tag == 1){
			content = SOAPEnvTool.toPraseContent("notice", "连接未建立，服务访问失败，请先进行连接操作！");
			return new String(WSClientSecurityTool.hexStr2ByteArray(content), "utf-8");//还原代码
		}else{// reConn_tag = 0 ,2情况
			if(passport == null || password == null) {
				content = SOAPEnvTool.toPraseContent("notice", "用户名或密码为空！");
				return new String(WSClientSecurityTool.hexStr2ByteArray(content), "utf-8");//还原代码
	    	}
			WSClientSecurityTool.visit_name = passport;
			WSClientSecurityTool.visit_password = password;
			
			content = clientService.getWebServicesPort().operate(WSClientSecurityTool.byteArray2HexStr(argument.getBytes("utf-8")));
			byte[] bytesContent = WSClientSecurityTool.hexStr2ByteArray(content);
			content = new String(bytesContent, "utf-8");
		}
		return content;
	}
	
	/**
	 * 安全访问---连接断开阶段
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String closeConnection() throws UnsupportedEncodingException{
		if(WSClientSecurityTool.reConn_tag == 1){//连接已经关闭
			String contentError = SOAPEnvTool.toPraseContent("notice", "连接已经处于断开状态！");
			return new String(WSClientSecurityTool.hexStr2ByteArray(contentError), "utf-8");//还原代码
		}//
		//关闭接口 ，继续请求
		WSClientSecurityTool.reConn_tag = 2;//
		String giveUpString = clientService.getWebServicesPort().operate(SOAPEnvTool.toParseGeneralMethod("notused", "notused"));//已经转码
		
		Map<String, Object> requestGiveUp = SOAPEnvTool.parseResponse(giveUpString);
		if(requestGiveUp.get("type") != null && requestGiveUp.get("type").equals("data")){
			//在ClientVSighHandler中清除reConn_tag标志
			WSClientSecurityTool.secretKey = null;//Handler 需要用来解密在此处清除
			WSClientSecurityTool.algorithmType = 2;//设置加密算法2为默认值
			return new String(WSClientSecurityTool.hexStr2ByteArray(giveUpString), "utf-8");//正常XML信息向上递交
		}else if(requestGiveUp.get("type") != null && requestGiveUp.get("type").equals("error")){
			return new String(WSClientSecurityTool.hexStr2ByteArray(giveUpString), "utf-8");//错误XML信息向上递交
		}else{
			String contentError = SOAPEnvTool.toPraseContent("notice", "断开操作失败，请重试！");
			return new String(WSClientSecurityTool.hexStr2ByteArray(contentError), "utf-8");//还原代码
		}
	}
	
	/**
	 * 直接服务调用,不需要进行连接相关操作
	 * @param passport 用户名
	 * @param password 密码
	 * @param argument 标准请求参数
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String operateDirect(String passport, String password, String argument) throws UnsupportedEncodingException {
		String content = null;
		if(passport == null || password == null) {
			content = SOAPEnvTool.toPraseContent("notice", "用户名或密码为空！");
			return new String(WSClientSecurityTool.hexStr2ByteArray(content), "utf-8");//还原代码
    	}
		WSClientSecurityTool.visit_name = passport;
		WSClientSecurityTool.visit_password = password;
		WSClientSecurityTool.reConn_tag = 4;//直接服务调用标志
		String contentHex = clientService.getWebServicesPort().operate(WSClientSecurityTool.byteArray2HexStr(argument.getBytes("utf-8")));
		//将十六进制转化为正常字符并解析
		Map<String, Object> requestDirect = SOAPEnvTool.parseResponse(contentHex);
		if(requestDirect.get("type") != null && requestDirect.get("type").equals("data")){
			//还原为正常字符并向上递交
			return new String(WSClientSecurityTool.hexStr2ByteArray(contentHex), "utf-8");//data正常XML信息向上递交(解码后的结果)，并有上一层解析结果
		} else if(requestDirect.get("type") != null && requestDirect.get("type").equals("notice")){
			return new String(WSClientSecurityTool.hexStr2ByteArray(contentHex), "utf-8");//notice信息向上递交
		} else if(requestDirect.get("type") != null && requestDirect.get("type").equals("error")){
			return new String(WSClientSecurityTool.hexStr2ByteArray(contentHex), "utf-8");//error错误XML信息向上递交
		} else {
			String contentError = SOAPEnvTool.toPraseContent("notice", "直接服务调用失败，请重试！");
			return new String(WSClientSecurityTool.hexStr2ByteArray(contentError), "utf-8");//还原代码
		}
	}
}
