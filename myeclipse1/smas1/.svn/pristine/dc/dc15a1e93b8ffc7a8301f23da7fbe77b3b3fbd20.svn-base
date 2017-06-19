package csdc.tool.webService.smdb.client;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyAgreement;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class WSClientSecurityTool {
	public static String visit_name;
	public static String visit_password;
	
	public static int reConn_tag;
	/**
	 * 双方安全交互标志位
	 * 0，进行安全的数据交互
	 * 1，首次连接进行密钥协商
	 * 2，断开连接进行
	 * 3，传递错误信息
	 * 4, 接口直接调用，不进行安全处理：Handler类不进行安全处理，用户名密码明文传输
	 * 说明，reConn_tag初始化为1；并在密钥生成之后重置为0；在断开链接操作前重置为2，在返回的Soap报文处理中，重置为1.
	 */
	public static byte secretKey[];//genSecretKey()获得
	
	
	public static String serverCAPath;//服务端ca证书
	public static String clientCAPath;
	public static String clientKeyStorePath;
	public static String clientKeyStorePassword;//----不同的客户端需要设置不同的密码
	public static String clientKeyAlias;//密钥别名
	public static String clientKeyPass;//密码
	
	private static String shakeMaterial;
	private static final String localName = "http://server.webService.service.csdc/";
	private static BigInteger clientP, clientG;
	private static int clientL;//客户端长度
	private static byte clientPK[];//客户端公钥
	private static byte clientSK[];//客户端私钥
    public  static byte servicePK[];//reConnKeyAgree服务获得
	private static KeyPair keyPair;
	
    public static final String KEY_STORE = "JKS";//Java Key Store
    public static final String X509 = "X.509";//证书格式类型定义
    public static final String KEY_ALGORITHM = "RSA";//密钥算法
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";//数字签名\验算法
    public static Certificate serverCerReceive;
    
	public static int algorithmType = 2;//默认，算法类型变化范围：0，1，2，3，当为0时表示无此种加密算法类型
	private static Map algorithmNames = new HashMap<String, Integer>();//可选加密算法种类对应强度
	static {//按照算法强度排序
		algorithmNames.put("DES", 1);//8B蜜月长度64bit(默认)
		algorithmNames.put("AES", 2);//16B
		algorithmNames.put("DESede", 3);//24B
	}
	public static int getAlogrithmType(String algorithmName){
		if(!algorithmNames.containsKey(algorithmName)){
			return 0;
		}
		return (Integer) algorithmNames.get(algorithmName);
	}
	/*************************************************************************************************
	 * 	蜜月协商安全
	 *************************************************************************************************/
	/**
	 * 初始化参数 CTP,CTG,CTLN,CTPK; 
	 * 和CTSK；KeyPair；
	 */
	public static void keyAgreeInit() {
		try {
			reConn_tag = 1 ;
			KeyPairGenerator kpg;
			kpg = KeyPairGenerator.getInstance("DH");
			kpg.initialize(1024);//最终生成的会话密钥长度为1024bit
			KeyPair kp = kpg.generateKeyPair(); 
			DHParameterSpec dhSpec = ((DHPublicKey) kp.getPublic()).getParams();
			clientG = dhSpec.getG();
			clientP = dhSpec.getP();
			clientL = dhSpec.getL();
			clientPK = kp.getPublic().getEncoded();
			keyPair = kp;
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		}
	}
	/**
	 * 加密算法处理
	 * @param clear
	 * @param secretKey
	 * @param algorithmType 算法类型1，2，3
	 * @return
	 */
	public static String doEncryProcess(String clear, byte[] secretKey, int algorithmType){
		if(algorithmType == 0){
			return null;
		}
		String codeString = null;
		switch (algorithmType) {
		case 1://"DES"
			codeString = DESEncry(clear, secretKey);
			break;
		case 2://"AES"
			codeString = AESEncry(clear, secretKey);
			break;
		case 3://"DESede"
			codeString = DESedeEncry(clear, secretKey);
			break;
		default:
			return null;
		}	
		return codeString;
	}
	/**
	 * 解密算法处理
	 * @param code
	 * @param secretKey
	 * @param algorithmType 算法类型 1，2，3
	 * @return
	 */
	public static String doDecryProcess(String code, byte[] secretKey, int algorithmType){
		if(algorithmType == 0){
			return null;
		}
		String clearsString = null;
		switch (algorithmType) {
		case 1://"DES"
			clearsString = DESDecry(code, secretKey);
			break;
		case 2://"AES"
			clearsString = AESDecry(code, secretKey);
			break;
		case 3://"DESede"
			clearsString = DESedeDecry(code, secretKey);
			break;
		default:
			return null;
		}	
		return clearsString;
	}
	private static String DESEncry(String content, byte[] secretKey){
		String code_str = null;
		try {
			SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
			DESKeySpec desSpec = new DESKeySpec(secretKey);
			SecretKey secret_key = skf.generateSecret(desSpec);
			Cipher c;
			c = Cipher.getInstance("DES/ECB/PKCS5Padding");
			c.init(Cipher.ENCRYPT_MODE, secret_key);
			byte[] code = c.doFinal(content.getBytes());
			code_str = byteArray2HexStr(code);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} 
		return code_str==null?null:code_str;
	}
	private static String DESDecry(String code, byte[] secretKey){
		String clear_str = null;
		try {
			//用秘密密钥生成DESkey，采用对称加密数据并返回 ,java6密钥默认长度56位
			SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
			DESKeySpec desSpec;
			desSpec = new DESKeySpec(secretKey);
			SecretKey key = skf.generateSecret(desSpec);
			Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] clear_byte = cipher.doFinal(hexStr2ByteArray(code));			
			clear_str = new String(clear_byte);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return clear_str==null?null:clear_str;
	}
	private static String AESEncry(String content, byte[] secretKey){
		String code_str = null;
		try {
			SecretKeySpec aesSpec = new SecretKeySpec(secretKey, 0, 16, "AES");
			SecretKey secret_key = (SecretKey)aesSpec;
			Cipher c;
			c = Cipher.getInstance("AES/ECB/PKCS5Padding");
			c.init(Cipher.ENCRYPT_MODE, secret_key);
			byte[] code = c.doFinal(content.getBytes());
			code_str = byteArray2HexStr(code);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} 
		return code_str == null ? null : code_str;
	}
	private static String AESDecry(String code, byte[] secretKey){
		String clear_str = null;
		try {
			SecretKeySpec aesSpec = new SecretKeySpec(secretKey, 0, 16, "AES");
			SecretKey key = (SecretKey)aesSpec;
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] clear_byte = cipher.doFinal(hexStr2ByteArray(code));			
			clear_str = new String(clear_byte);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return clear_str==null?null:clear_str;
	}
	private static String DESedeEncry(String content, byte[] secretKey){
		String code_str = null;
		try {
			SecretKeyFactory skf = SecretKeyFactory.getInstance("DESede");
			DESedeKeySpec desEdeSpec = new DESedeKeySpec(secretKey);
			SecretKey secret_key = skf.generateSecret(desEdeSpec);
			Cipher c;
			c = Cipher.getInstance("DESede/ECB/PKCS5Padding");
			c.init(Cipher.ENCRYPT_MODE, secret_key);
			byte[] code = c.doFinal(content.getBytes());
			code_str = byteArray2HexStr(code);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} 
		return code_str==null?null:code_str;
	}
	private static String DESedeDecry(String code, byte[] secretKey){
		String clear_str = null;
		try {
			SecretKeyFactory skf = SecretKeyFactory.getInstance("DESede");
			DESedeKeySpec desEdeSpec;
			desEdeSpec = new DESedeKeySpec(secretKey);
			SecretKey key = skf.generateSecret(desEdeSpec);
			Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] clear_byte = cipher.doFinal(hexStr2ByteArray(code));			
			clear_str = new String(clear_byte);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return clear_str==null?null:clear_str;
	}


    /**
     * client生成secretKey
     * @param servicePK
     * @param keyPair
     * @return secretKey
     */
	public static byte[] genSecretKey(){
		byte[] secKey = null;
		try {
			KeyAgreement ka = KeyAgreement.getInstance("DH");
			ka.init(keyPair.getPrivate());
			KeyFactory kf = KeyFactory.getInstance("DH");
			X509EncodedKeySpec x509Spec = new X509EncodedKeySpec(servicePK);
			PublicKey sPK = kf.generatePublic(x509Spec);
			ka.doPhase(sPK, true);
			secretKey = ka.generateSecret();
			String secretString = byteArray2HexStr(secretKey);
			secKey = secretKey;
//			System.out.println("*************生成会话密钥为：" + secretString);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return secKey==null?null:secKey;
	}
	/**
	 * 获取xml格式的content
	 * 此方法在此定义比较合适
	 * @return
	 */
	public static String genShakeReqXmlStr() { 
		shakeMaterial = getClientPStr() + ";" + getClientGStr() + ";" + getClientLStr() + ";" + getClientPKStr();
		return SOAPEnvTool.toParseShakeHandMethod("ControlService", "requestHandsShake", shakeMaterial);//统一进行了转码
	}
	
	
	/***************************************************************************************************
	 * Client 证书库管理
	 **************************************************************************************************/

	/**
	 * 获取已经导出的的证书
	 * @param path
	 * @return
	 * 成功返回证书;
	 * 失败返回null
	 */
	public static Certificate getExportedCertificate(String path){
		//ca证书，或者客户端证书
		Certificate certificate = null;
		CertificateFactory certificateFactory;
		try {
			certificateFactory = CertificateFactory.getInstance(X509);
			FileInputStream in = new FileInputStream(path);
			 certificate = certificateFactory.generateCertificate(in);
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return certificate;
	}
	public static String getExportedCertificateAsString(String path){
		byte[] cer_byte = null;
		try {
			//直接从密钥库中获取，也可以从导出的文件中读取证书。
			 Certificate clientCer =  getExportedCertificate(path);
			 cer_byte = clientCer.getEncoded();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return byteArray2HexStr(cer_byte);
	}
	/**
	 *利用密钥库中别名alias的私钥 对text进行数字签名
	 * @param text
	 * @param alias
	 * @param aliasPassword
	 * @return
	 * 成功返回签名值，失败返回null
	 */
	public static String signText(String text , String alias, String aliasPassword ){
		byte[] sign = null;
		try {
			PrivateKey privateKey = getPrivateKey(alias, aliasPassword);
			Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);//实例化签名算法
			signature.initSign(privateKey);
			signature.update(text.getBytes());//执行签名
			sign = signature.sign();//返回签名，字节数组类型
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sign==null?null:byteArray2HexStr(sign);//节后后还原byte[]
	}
	/**
	 * 签名验证
	 * @param text 签名实体
	 * @param signStr 签名值
	 * @param certificate 服务端的证书
	 * @return  验证结果
	 */
	public static boolean verifySign(String text, String signStr ,Certificate certificate){
		boolean value = false;
		try {
		 PublicKey publicKey = certificate.getPublicKey();
		 Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);//实例化签名对象
		 signature.initVerify(publicKey);//初始化签名对象
		 signature.update(text.getBytes());//执行签名验证
		 value = signature.verify(hexStr2ByteArray(signStr));
		} catch (SignatureException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}
		return value;
	}
	
	/**
	 * 转换证书
	 * @param cerStr
	 * @return
	 * 成功 返回证书
	 * 失败 返回null
	 */
	public static Certificate getCertificateFromStr(String cerStr){
		Certificate cerCge = null ;
		byte[] cerbyte = hexStr2ByteArray(cerStr);
		 ByteArrayInputStream stream = new ByteArrayInputStream(cerbyte);
		 InputStream iptStream = (InputStream)stream;
		try {
			CertificateFactory certificateFactory = CertificateFactory.getInstance(X509);
			cerCge = certificateFactory.generateCertificate(iptStream);
		} catch (CertificateException e) {
			e.printStackTrace();
		}
		return cerCge;
	}
	private static PrivateKey getPrivateKey( String alias, String aliasPassword)
			throws Exception {
		KeyStore ks = getKeyStore();
		PrivateKey key = (PrivateKey) ks.getKey(alias,aliasPassword.toCharArray());
		return key;
	}
	private static KeyStore getKeyStore()throws Exception {
		FileInputStream is = new FileInputStream(clientKeyStorePath);
		KeyStore ks = KeyStore.getInstance(KEY_STORE);
		String password = clientKeyStorePassword;
		ks.load(is, password.toCharArray());
		is.close();
		return ks;
	}
	public static boolean verifyCertificate(Date date, Certificate certificate,String caPath ) {
		return verifyCertificate(date, certificate, getExportedCertificate(caPath));
	}
	public static boolean verifyCertificate(Date date, Certificate certificate,Certificate caCert ) {
		boolean status = true;
		//检测时间是否在有效期
		X509Certificate x509Certificate = (X509Certificate)certificate;
		try {
			x509Certificate.checkValidity(date);
		} catch (Exception e) {
			status = false;
		}
		//检测证书的合法性（签发单位合法签名）
		if(status == true){
		    PublicKey caPK = caCert.getPublicKey();
			try {
				certificate.verify(caPK);
				status = true;
			} catch (Exception e) {
				e.printStackTrace();
				status = false;
			}
		}
		return status;
	}
    
    
	
	/***************************************************************************************************
	 * 通用工具
	 **************************************************************************************************/

	public static byte[] hexStr2ByteArray(String hexStr) {
		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2),
					16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}
	public static String byteArray2HexStr(byte[] byteArray) {
		char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'A', 'B', 'C', 'D', 'E', 'F' };
		char[] resultCharArray = new char[byteArray.length * 2];
		int index = 0;
		for (byte b : byteArray) {
			resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
			resultCharArray[index++] = hexDigits[b & 0xf];
		}
		return new String(resultCharArray);
	}
	
	/***************************************************************************************************
	 * 其他转换工具
	 **************************************************************************************************/

	//获取字符串形式
	public static String getClientPKStr(){
		String string = byteArray2HexStr(clientPK);
		return string;
	}
	//获取字符串形式
	public static String getClientPStr() {
		String string = clientP.toString();;
		return string;
	}
	//获取字符串形式
	public static String getClientGStr() {
		String string = clientG.toString();;
		return string;
	}
	//获取字符串形式
	public static String getClientLStr() {
		String string =  Integer.toString(clientL);
		return string;
	}
	
	public static String getShakeMaterial() {
		return shakeMaterial;
	}
	public static String getLocalname() {
		return localName;
	}
	
}
