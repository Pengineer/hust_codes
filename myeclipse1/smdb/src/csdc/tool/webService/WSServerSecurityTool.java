package csdc.tool.webService;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
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
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyAgreement;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.hp.hpl.sparta.xpath.ThisNodeTest;

import csdc.tool.ApplicationContainer;


/**
 * 静态工具类
 * @author zhangnan
 * 2014-7-14
 */
public class WSServerSecurityTool {
	/**
	 * 字符串常量定义
	 */
	private static final String localNameSpace = "http://server.webService.service.csdc/";
	private static String securityWSPath = ApplicationContainer.sc.getRealPath("/keypair");
	//蜜月库路径密码相关配置
	private static final String serverTrustStorePath = securityWSPath +"/CSDC.truststore";
	private static final String serverTrustStorePass = "MOECSDC";
	private static final String serverCertificatePath = securityWSPath +"/CSDC.cer";
	private static final String serverKeyStorePath = securityWSPath +"/CSDC.keystore";
	private static final String serverKeyStorePass = "MOECSDC";
	private static final String serverKeyAlias = "CSDC";// 密钥别名
	private static final String serverKeyPass = "MOECSDC";// 密码
	
	private static final String KEY_STORE = "JKS";//Java Key Store
	private static final String X509 = "X.509";
	private static final String KEY_ALGORITHM = "RSA";//密钥算法名字（保留）
	private static final String SIGNATURE_ALGORITHM = "MD5withRSA";//数字签名\验算法
 
	/**********************************************************************************
	 * 解密解密相关
	 *********************************************************************************/
	public static HashMap<String, VisitorInfoBean> visitorBeansMap = null;
	private static Map algorithmNames = new HashMap<String, Integer>();
	static {//按照算法强度，依次增强
		algorithmNames.put("DES", 1);//8B蜜月长度64bit(默认)实际用56bit
		algorithmNames.put("AES", 2);//16B
		algorithmNames.put("DESede", 3);//24B
	}
	/**
	 * 握手
	 * 成功 返回结果String类型的协商信息
	 * 失败 返回null
	 * @throws IOException 
	 */
    public static String  doHandsShake(BigInteger ctP, BigInteger ctG, int ctL, byte[] ctPK, String userMark ){
    	
		KeyPairGenerator kpg;
		String end = null;
		byte[] srPK = null;
		try {
			kpg = KeyPairGenerator.getInstance("DH");
			DHParameterSpec dhSpec = new DHParameterSpec(ctP, ctG, ctL);
			kpg.initialize(dhSpec);
			KeyPair kp = kpg.generateKeyPair();
			srPK = kp.getPublic().getEncoded();
//			srSK = kp.getPrivate().getEncoded();（保留）
			KeyAgreement ka = KeyAgreement.getInstance("DH");
			ka.init(kp.getPrivate());
			KeyFactory kf = KeyFactory.getInstance("DH");
			X509EncodedKeySpec x509Spec = new X509EncodedKeySpec(ctPK);
			PublicKey pk = kf.generatePublic(x509Spec);
			ka.doPhase(pk, true);
			byte[] secretKey = ka.generateSecret();
			//根据userMark 将生成的密钥放入其bean对象中
			VisitorInfoBean beanSelectBean = (VisitorInfoBean) visitorBeansMap.get(userMark);
			beanSelectBean.setVisitorSecretKey(secretKey);
			end = byteArray2HexStr(srPK);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
		return end;
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
	/**********************************************************************************
	 * 密钥库管理
	 *********************************************************************************/
	/**
	 * 验证C身份
	 * @param date
	 * @param certClient
	 * @return
	 * @throws IOException 
	 */
	public static boolean verifyClientCert(Date date, Certificate certClient){
		X509Certificate x509CertClient = (X509Certificate)certClient;
		String ClientDN = x509CertClient.getSubjectDN().getName();
		KeyStore trustStore = null;
		boolean value_b = false;
		try {
			trustStore = getKeyStore(serverTrustStorePath, serverTrustStorePass);
			 Enumeration<String> e=trustStore.aliases();
			 while (e.hasMoreElements()) {
			  String aliasString = (String)e.nextElement();
			  Certificate certTrusted = trustStore.getCertificate(aliasString);
			  X509Certificate x509CertTrusted = (X509Certificate)certTrusted;
			  if(x509CertTrusted.getSubjectDN().getName() == ClientDN ){//信任裤中存在此证书
				  if(verifyCertificate(date,certClient,certTrusted)){//用此信任证书作为CA证书验证对应客户端证书
					  value_b = true;
					  break;
				  }
			  }
		    }
		} catch (Exception e) {
			System.out.println("\n客户端证书验证--->信任库加载失败！");
			value_b = false;
			e.printStackTrace();
		}
		return value_b;
	}
	// 服务端调用客户端证书的ca证书对客户端证书验证（扩展待用）
	private static boolean verifyCertificate(Date date, Certificate certificate,String caPath ) {
		return verifyCertificate(date, certificate, getExportedCertFromFile(caPath));
	}
	/**
	 * 用证书的CA证书，以及当前时间进行验证，对证书验证
	 */
	private static boolean verifyCertificate(Date date, Certificate certificate,Certificate caCert ) {
		boolean status = true;
		X509Certificate x509Certificate = (X509Certificate)certificate;
		try {
			x509Certificate.checkValidity(date);
		} catch (Exception e) {
			status = false;
		}
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
	
	/**
	 * 获取导出的ServiceCertificate证书
	 * @param path
	 * @return
	 * 成功返回证书;
	 * 失败返回null
	 * @throws IOException 
	 */
	public static Certificate getExportedCertFromFile(String path){
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
	
	public static Certificate getServerCertificate(){
		return getExportedCertFromFile(serverCertificatePath);
	}
	
	public static String getServerCertificateAsString(){
		byte[] cer_byte = null;
		try {
			 Certificate serverCer = getExportedCertFromFile(serverCertificatePath);
			 cer_byte = serverCer.getEncoded();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  cer_byte==null?null: byteArray2HexStr(cer_byte);
	}
	
	/**
	 *利用密钥库中多个别名alias的私钥 对text进行数字签名,（目前服务端只有一个）公私密钥对
	 */
	private static String signText(String text, String alias, String aliasPassword ){
		byte[] sign = null;
		try {
			PrivateKey privateKey = getPrivateKey(alias, aliasPassword);
			Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);//实例化签名算法
			signature.initSign(privateKey);
			signature.update(text.getBytes());//执行签名
			sign = signature.sign();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sign==null?null:byteArray2HexStr(sign);
	}
	
	//服务端只用一对公/私密钥对，指定别名
	public static String signText(String text ){
		return signText(text, serverKeyAlias, serverKeyPass);
	}
	
	/**
	 * 对签名进行验证
	 * @param text 签名实体
	 * @param signStr 签名值
	 * @param certificate 缓存的证书
	 * @return  验证结果
	 * @throws IOException 
	 */
	public static boolean verifySign(String text, String signStr ,Certificate certificate){
		boolean value = false;
		try {
		 PublicKey publicKey = certificate.getPublicKey();
		 Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);//实例化签名对象
		 signature.initVerify(publicKey);
		 signature.update(text.getBytes());
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
	
	private static PrivateKey getPrivateKey( String alias, String aliasPassword)throws Exception {
		KeyStore ks = getKeyStore(serverKeyStorePath,serverKeyStorePass);
		PrivateKey key = (PrivateKey) ks.getKey(alias,aliasPassword.toCharArray());
		return key;
	}
	
	private static KeyStore getKeyStore(String keyStorePath,String keyStorePassword )throws Exception {
		FileInputStream is = new FileInputStream(keyStorePath);//
		KeyStore ks = KeyStore.getInstance(KEY_STORE);
		ks.load(is, keyStorePassword.toCharArray());
		is.close();
		return ks;
	}
	
	/**
	 * 转换证书
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
	
	/**********************************************************************************
	 * 通用其他
	 *********************************************************************************/
	 public static String getServletContextPath(){
		 ClassLoader classLoader = Thread.currentThread().getContextClassLoader();  
         if (classLoader == null) {  
             classLoader = ClassLoader.getSystemClassLoader();  
         }  
         java.net.URL url = classLoader.getResource("");  
         String ROOT_CLASS_PATH = url.getPath()+"/";  
         File rootFile = new File(ROOT_CLASS_PATH);
         String WEB_INFO_DIRECTORY_PATH = rootFile.getParent()+"/";  
         File webInfoDir = new File(WEB_INFO_DIRECTORY_PATH);  
         String SERVLET_CONTEXT_PATH = webInfoDir.getParent();  
         return SERVLET_CONTEXT_PATH;
	 }
	 
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

	public static String getLocalnamespace() {
		return localNameSpace;
	}

	public static void setVisitorBeansMap(
			HashMap<String, VisitorInfoBean> visitorBeansMap) {
		WSServerSecurityTool.visitorBeansMap = visitorBeansMap;
	}

	public static HashMap<String, VisitorInfoBean> getVisitorBeansMap() {
		if(visitorBeansMap == null){
			visitorBeansMap = new HashMap<String, VisitorInfoBean>();
		}
		return visitorBeansMap;
	}

}
