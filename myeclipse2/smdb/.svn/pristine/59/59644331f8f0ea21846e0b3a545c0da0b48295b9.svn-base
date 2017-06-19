package csdc.tool;

import java.util.HashMap;
import java.util.Map;

public class ResponseStatusCodes {
	/*
	 * 消息（1字头）
	 * ▪ 100 Continue▪ 101 Switching Protocols▪ 102 Processing
	 * 成功（2字头）
	 * ▪ 200 OK▪ 201 Created▪ 202 Accepted▪ 204 No Content▪ 205 Reset Content
	 * ▪ 206 Partial Content▪ 207 Multi-Status
	 * 重定向（3字头）
	 * ▪ 300 Multiple Choices▪ 301 Moved Permanently▪ 302 Found▪ 303 See Other▪ 304 Not Modified
	 * ▪ 305 Use Proxy▪ 306 Switch Proxy▪ 307 Temporary Redirect
	 * 请求错误（4字头）
	 * ▪ 400 Bad Request▪ 401 Unauthorized▪ 402 Payment Required▪ 403 Forbidden▪ 404 Not Found
	 * ▪ 405 Method Not Allowed▪ 406 Not Acceptable▪ 408 Request Timeout▪ 409 Conflict▪ 410 Gone
	 * ▪ 411 Length Required▪ 412 Precondition Failed▪ 414 Request-URI Too Long▪ 417 Expectation Failed▪ 421
	 * ▪ 422 Unprocessable Entity▪ 423 Locked▪ 424 Failed Dependency▪ 425 Unordered Collection▪ 426 Upgrade Required
	 * ▪ 449 Retry With
	 * 服务器错误（5字头）
	 * ▪ 500 Internal Server Error▪ 501 Not Implemented▪ 502 Bad Gateway▪ 503 Service Unavailable▪ 504 Gateway Timeout
	 * ▪ 507 Insufficient Storage▪ 508 Loop Detected▪ 510 Not Extended*/
	public static final String CONTINUE = "100";
	public static final String SWITCHINGPROTOCOLS = "101";
	public static final String PROCESSING = "102";
	public static final String OK = "200";
	public static final String CREATED = "201";
	public static final String ACCEPTED = "202";
	public static final String NONAUTHORITATIVE = "203";	
	public static final String NOCONTENT = "204";
	public static final String RESETCONTENT = "205";
	public static final String PARTIALCONTENT = "206";	
	public static final String MULTISTATUS = "207";	
	public static final String MULTIPLECHOICES = "300";	
	public static final String MOVEDPERMANENTLY = "301";	
	public static final String FOUND = "302";	
	public static final String SEEOTHER = "303";	
	public static final String NOTMODIFIED = "304";	
	public static final String USEPROXY = "305";	
	public static final String SWITCHPROXY = "306";	
	public static final String TEMPORARYREDIRECT = "307";
	public static final String BADREQUEST = "400";
	public static final String UNAUTHORIZED = "401";
	public static final String PAYMENTREQUIRED = "402";
	public static final String FORBIDDEN = "403";
	public static final String NOTFOUND = "404";
	public static final String METHODNOTALLOWED = "405";
	public static final String NOTACCEPTABLE = "406";
	public static final String REQUESTTIIMEOUT = "408";
	public static final String CONFLICT = "409";
	public static final String GONE = "410";
	public static final String LENGTHREQUIRED = "411";
	public static final String PRECONDITIONFAILED = "412";
	public static final String REQUESTURITOOLONG = "414";
	public static final String EXPECTIATIONFAILED = "417";
	public static final String UNPROCESSABLEENTITY = "422";
	public static final String LOCK = "423";
	public static final String FAILEDDEPENDENCY = "424";
	public static final String UNORDEREDCOLLECTION = "425";
	public static final String UPGRADEREQUIRED = "426";
	public static final String RETRYWITH = "449";
	
	
	public static final String INTERNALSERVERERROR = "500";	
	public static final String NOTIMPLEMENTED = "501";	
	public static final String BADGATEWAY = "502";	
	public static final String SERVICEUNAVAILABLE = "503";	
	public static final String GATEWAYTIMEOUT = "504";	
	public static final String INSUFFICIENT = "507";	
	public static final String LOOPDETECTED = "508";	
	public static final String NOTEXTENDED = "510";	

	private static final String[][] Response_Status_Codes = {
		//成功（1字头）
		{CONTINUE, "Continue", "继续"},
		{SWITCHINGPROTOCOLS, "Switching Protocols", "交换协议"},
		{PROCESSING, "Processing", "Processing"},
		{OK, "Ok", "成功"},
		{ACCEPTED, "Accepted", "接收"},
		{NOCONTENT, "No Content", "无内容"},
		{NONAUTHORITATIVE, "Non-Authoritative Information", "非认证信息"},
		{RESETCONTENT, "Reset Content", "重置内容"},
		{PARTIALCONTENT, "Partial Content", "部分内容"},
		{MULTISTATUS, "Multi-Status", "Multi-Status"},
		{MULTIPLECHOICES, "Multiple Choices", "多种选择"},
		{MOVEDPERMANENTLY, "Moved Permanently", "永久移动"},
		{FOUND, "Found", "暂时转移"},
		{SEEOTHER, "See Other", "参见其他"},
		{NOTMODIFIED, "Not Modified", "未修改"},
		{USEPROXY, "Use Proxy", "使用代理"},
		{SWITCHPROXY, "Switch Proxy", "切换代理"},
		{TEMPORARYREDIRECT, "Temporary Redirect", "临时重定向"},
		{INTERNALSERVERERROR, "Internal Server Error", "服务器内部错误"},
		{NOTIMPLEMENTED, "Not Implemented", "尚未实施"},
		{BADGATEWAY, "Bad Gateway", "错误网关"},
		{SERVICEUNAVAILABLE, "Service Unavailable", "服务不可用"},
		{GATEWAYTIMEOUT, "Gateway Timeout", "网关超时"},
		{INSUFFICIENT, "Insufficient Storage", "Insufficient Storage"},
		{LOOPDETECTED, "Loop Detected", "Loop Detected"},
		{NOTEXTENDED, "Not Extended", "Not Extended"},
		{BADREQUEST, "Bad Request", ""},
		{UNAUTHORIZED, "Unauthorized", ""},
		{PAYMENTREQUIRED, "Payment Required", ""},
		{FORBIDDEN, "Forbidden", ""},
		{NOTFOUND, "Not Found", ""},
		{METHODNOTALLOWED, "Method Not Allowed", ""},
		{NOTACCEPTABLE, "Not Acceptable", ""},
		{REQUESTTIIMEOUT, "Request Timeout", ""},
		{NOTACCEPTABLE, "Not Acceptable", ""},
		{CONFLICT, "Conflict", ""},
		{GONE, "Gone", ""},
		{LENGTHREQUIRED, "Length Required", ""},
		{PRECONDITIONFAILED, "Precondition Failed", ""},
		{REQUESTURITOOLONG, "Request-URI Too Long", ""},
		{EXPECTIATIONFAILED, "Expectation Failed", ""},
		{UNPROCESSABLEENTITY, "Unprocessable Entity", ""},
		{LOCK, "Locked", ""},
		{FAILEDDEPENDENCY, "Failed Dependency", ""},
		{UNORDEREDCOLLECTION, "Unordered Collection", ""},
		{UPGRADEREQUIRED, "Upgrade Required", ""},
		{RETRYWITH, "Retry With", ""},
	};
	public static final Map<String, String[]> Response_Status_Codes_MAP;
	static {
		Response_Status_Codes_MAP = new HashMap<String, String[]>();
		String[] value;
		for (String[] tmp : Response_Status_Codes) {// 遍历上述对象，将其封装为map对象
			value = new String[2];
			value[0] = tmp[1];
			value[1] = tmp[2];
			Response_Status_Codes_MAP.put(tmp[0], value);// 以URL为key，其代码及描述为value
		}
	}

}
