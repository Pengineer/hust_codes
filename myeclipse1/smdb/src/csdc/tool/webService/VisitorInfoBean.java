package csdc.tool.webService;

import java.security.cert.Certificate;

public class VisitorInfoBean {
	/**
	 * 用户标志信息是由用户名的MD5值
	 * （唯一性依据：系统用户标识唯一）
	 */
	private String visitorMark ;
	/**
	 * 此访问对象对应的证书
	 * 且证书验证通过
	 */
	private Certificate  visitorCertificate;
	/**
	 * 动态变化，每一次SOAP访问，都会根据recontag更新此属性
	 * 含义说明：
	 * 0:进行安全的通信；
	 * 1:握手连接过程；
	 * 2:安全的断开过程；
	 * 3:逻辑反馈过程；
	 * 4:是直接进行服务访问
	 * 
	 */
	private int visitorTag;//用户的标志信息,取值 0， 1， 2，3, 4
	/**
	 * 双方产生的会话密钥
	 */
	private byte[] visitorSecretKey; 
	/**
	 * 用户选择的加密算法类型
	 * 1:DES加密算法类型；
	 * 2：AES加密算法类型；
	 * 3：DESede加密算法类型
	 * 安全强度主键DESede最高，DES最低
	 */
	private int visitorAlgType;
	
	
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
