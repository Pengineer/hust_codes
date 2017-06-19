package csdc.tool.webService;

import java.security.cert.Certificate;

public class VisitorInfoBean {
	private String visitorMark ;//用户标志信息，passport的MD5
	private Certificate  visitorCertificate;//保存访问这的证书
	private int visitorTag;//用户的标志信息,取值 0， 1， 2，3, 4
	//含义说明：0，进行安全的通信；1，握手连接过程；2，安全的断开过程；3，完全的裸奔状态， 错误异常信息专用通道。
	//4是直接进行服务访问
	//动态变化，每一次SOAP访问，都会根据recontag更新此属性
	private byte[] visitorSecretKey; // 保存密钥
	private int visitorAlgType;//用户选择的加密算法类型，取值0，1，2，3 分别对应：无此类型；DES,AES,DESede
	//非动态变化，旨在用户建立连接时指定类型，本次连接之后的SOAP交互不再进行变化
	
	public VisitorInfoBean() {
		
	}
	
	public VisitorInfoBean(String visitorMark, int visitorTag) {
		this.visitorMark = visitorMark;
		this.visitorTag = visitorTag;
	}

	public String getVisitorMark() {
		return visitorMark;
	}

	public void setVisitorMark(String visitorMark) {
		this.visitorMark = visitorMark;
	}

	public Certificate getVisitorCertificate() {
		return visitorCertificate;
	}

	public void setVisitorCertificate(Certificate visitorCertificate) {
		this.visitorCertificate = visitorCertificate;
	}

	public int getVisitorTag() {
		return visitorTag;
	}

	public void setVisitorTag(int visitorTag) {
		this.visitorTag = visitorTag;
	}

	public byte[] getVisitorSecretKey() {
		return visitorSecretKey;
	}

	public void setVisitorSecretKey(byte[] visitorSecretKey) {
		this.visitorSecretKey = visitorSecretKey;
	}
	
	public int getVisitorAlgType() {
		return visitorAlgType;
	}

	public void setVisitorAlgType(int visitorAlgType) {
		this.visitorAlgType = visitorAlgType;
	}

}
