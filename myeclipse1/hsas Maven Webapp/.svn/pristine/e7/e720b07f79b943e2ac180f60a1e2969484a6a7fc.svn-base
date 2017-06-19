package csdc.tool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class LicenseTool {
	
	/*
	 * 获取计算机IP
	 */
	public static String getWinIP(){
	    InetAddress addr;
		try {
			addr = InetAddress.getLocalHost();
			return addr.getHostAddress().toString();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        return "unknown ip";
	}
	
	/**
	 * 获取主机名
	 * @return
	 */
	public static String getHostName(){
	    InetAddress addr;
		try {
			addr = InetAddress.getLocalHost();
			return addr.getHostName().toString();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        return "unknown host";
	}
	public static String getWinCPUSerial() {
		String result = "";
		try {
			File file = new File("c:/cpu.vbs");
			file.deleteOnExit();
			FileWriter fw = new java.io.FileWriter(file);
			String vbs = "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\n"
					+ "Set colItems = objWMIService.ExecQuery _ \n"
					+ "   (\"Select * from Win32_Processor\") \n"
					+ "For Each objItem in colItems \n"
					+ "    Wscript.Echo objItem.ProcessorId \n"
					+ "    exit for  ' do the first cpu only! \n" + "Next \n";

			fw.write(vbs);
			fw.close();
			Process p = Runtime.getRuntime().exec(
					"cscript /"+"/NoLogo " + file.getPath());
			BufferedReader input = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			String line;
			while ((line = input.readLine()) != null) {
				result += line;
			}
			input.close();
			file.delete();
		} catch (Exception e) {
			e.fillInStackTrace();
		}
		if (result.trim().length() < 1 || result == null) {
			result = "unknown";
		}
		return result.trim();
	}
	
	public static String getWinHardDiskSN(String drive) {
		String result = "";
		try {
			File file = new File("d:/hd.vbs");
			file.deleteOnExit();
			FileWriter fw = new java.io.FileWriter(file);

			String vbs = "Set objFSO = CreateObject(\"Scripting.FileSystemObject\")\n"
					+ "Set colDrives = objFSO.Drives\n"
					+ "Set objDrive = colDrives.item(\""
					+ drive
					+ "\")\n"
					+ "Wscript.Echo objDrive.SerialNumber"; // see note
			fw.write(vbs);
			fw.close();
			Process p = Runtime.getRuntime().exec(
					"cscript /"+"/NoLogo " + file.getPath());
			BufferedReader input = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			String line;
			while ((line = input.readLine()) != null) {
				result += line;
			}
			input.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.trim();
	}
	
	public static String getOsName() {
		Properties prop = System.getProperties();
		String os = prop.getProperty("os.name");
		return os;
	}

	public static void checkVersion() {
		StringBuffer sb = FileTool.readFileContent(System.getProperty("user.dir") + "/key");
		if(!verifyLicense(sb.toString())) {
			System.err.println("您使用的软件为盗版软件");
			Integer.parseInt("ERROR!");
		} else {
			System.out.println("您使用的软件为版软件");
		}
	}
	
	@SuppressWarnings("unchecked")
	public static String getSysMac() {
		try {
			Enumeration interfaces = NetworkInterface.getNetworkInterfaces();
			while (interfaces.hasMoreElements()) {
				NetworkInterface face = (NetworkInterface) interfaces.nextElement();
				if (!face.getName().equals("lo")) {
					byte[] mac = face.getHardwareAddress();
					if(mac != null)
					return bytes2mac(mac);
				}
			}
		} catch (SocketException se) {
		}
		return null;
	}

	private static String bytes2mac(byte[] bytes) {
		if (bytes.length != 6) {
			return null;
		}
		StringBuffer macString = new StringBuffer();
		byte currentByte;
		for (int i = 0; i < bytes.length; i++) {
			currentByte = (byte) ((bytes[i] & 240) >> 4);
			macString.append(Integer.toHexString(currentByte));
			currentByte = (byte) ((bytes[i] & 15));
			macString.append(Integer.toHexString(currentByte));
			macString.append("-");
		}
		macString.delete(macString.length() - 1, macString.length());
		return macString.toString().toUpperCase();
	}
	
	/**
	 * 生成用户信息文件
	 * @param licenseId
	 */
	public static void createUserInfoFile(String licenseId){
		FileTool.writeFileContent(System.getProperty("user.dir") + "/key", createUserInfo(licenseId));
	}
	

	/**
	 * 两次DES生成用户信息内容
	 * @return 
	 */
	public static String createUserInfo(String licenseId){
		String osName = getOsName();
		String keyString = "";
		keyString += "OS-"+osName+"ZOF";
		keyString += "IP-"+getWinIP()+"ZOF";
		keyString += "HOST-"+getHostName()+"ZOF";
		keyString += "LICENSE-"+licenseId+"ZOF";
		if(osName.toLowerCase().startsWith("window")) {
			keyString += "CPU-" + getWinCPUSerial()+"ZOF";
			keyString += "HD-" + getWinHardDiskSN("c")+"ZOF";
			keyString += "MAC-" + getSysMac()+"ZOF";
		} else {
			keyString = "MAC-" + getSysMac()+"ZOF";
		}
		System.out.println(keyString);
		DesUtils des = null;
		try {
			des = new DesUtils();
			keyString = des.encrypt(des.encrypt(keyString));
		} catch (Exception e) {
			//e.printStackTrace();
		}
		return keyString;
	}
	
	/**
	 * 解用户信息文件
	 * @param userInfo
	 * @return Map 用户信息内容
	 */
	public static Map getUserInfoMap(String userInfo){
		Map map = new HashMap();
		DesUtils des = null;
		try {
			des = new DesUtils();
			userInfo = des.decrypt(des.decrypt(userInfo));
		} catch (Exception e) {
			e.printStackTrace();
		}
		map.put("CPU",userInfo.substring(userInfo.indexOf("CPU")+4, userInfo.indexOf("ZOF", userInfo.indexOf("CPU"))));
		map.put("MAC", userInfo.substring(userInfo.indexOf("MAC")+4, userInfo.indexOf("ZOF", userInfo.indexOf("MAC"))));
		map.put("OS", userInfo.substring(userInfo.indexOf("OS")+3, userInfo.indexOf("ZOF", userInfo.indexOf("OS"))));
		map.put("IP", userInfo.substring(userInfo.indexOf("IP")+3, userInfo.indexOf("ZOF", userInfo.indexOf("IP"))));
		map.put("HOST", userInfo.substring(userInfo.indexOf("HOST")+5, userInfo.indexOf("ZOF", userInfo.indexOf("HOST"))));
		map.put("HD", userInfo.substring(userInfo.indexOf("HD")+3, userInfo.indexOf("ZOF", userInfo.indexOf("HD"))));
		map.put("LICENSE", userInfo.substring(userInfo.indexOf("LICENSE")+8, userInfo.indexOf("ZOF", userInfo.indexOf("LICENSE"))));
		return map;
	}
	
	/**
	 * 根据用户信息文件生成授权文件
	 * @param userInfo
	 * @return license 授权文件
	 */
	public static String createLicenseContent(String userInfo){
		String license = "ERROR USERINFO ";
		DesUtils des = null;
		try {
			des = new DesUtils();
			license = des.encrypt(userInfo);
		} catch (Exception e) {
			//e.printStackTrace();
		}
		return license;
	}

	/**
	 * 解license内容
	 * @param userInfo
	 * @return Map 用户信息内容
	 */
	public static Map getLicenseMap(String license){
		Map map = new HashMap();
		DesUtils des = null;
		try {
			des = new DesUtils();
			license = des.decrypt(des.decrypt(des.decrypt(license)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		map.put("CPU",license.substring(license.indexOf("CPU")+4, license.indexOf("ZOF", license.indexOf("CPU"))));
		map.put("MAC", license.substring(license.indexOf("MAC")+4, license.indexOf("ZOF", license.indexOf("MAC"))));
		map.put("OS", license.substring(license.indexOf("OS")+3, license.indexOf("ZOF", license.indexOf("OS"))));
		map.put("IP", license.substring(license.indexOf("IP")+3, license.indexOf("ZOF", license.indexOf("IP"))));
		map.put("HOST", license.substring(license.indexOf("HOST")+5, license.indexOf("ZOF", license.indexOf("HOST"))));
		map.put("HD", license.substring(license.indexOf("HD")+3, license.indexOf("ZOF", license.indexOf("HD"))));
		map.put("LICENSE", license.substring(license.indexOf("LICENSE")+8, license.indexOf("ZOF", license.indexOf("LICENSE"))));
		return map;
	}
	/**
	 * 验证授权文件
	 * @param license
	 * @return 是否是正版license
	 */
	public static boolean verifyLicense(String license){
		Map map = getLicenseMap(license);
		if(map.get("MAC").equals(getSysMac()) && map.get("HD").equals(getWinHardDiskSN("c")) && map.get("CPU").equals(getWinCPUSerial())){
			return true;
		}
		return false;
	}	
	
	public static void main(String[] args) throws UnknownHostException {
		createUserInfoFile("00012");
		System.out.println(getLicenseMap(createLicenseContent(createUserInfo("00000007"))));
		System.out.println(verifyLicense("ef419b7a99057b880d736e6f74a616f745af113d49569623260c520fcc11d792f289044d6684fa5e7ed1e416f46ea33e27a00ca1fd520e9a3cf07f346da8159987088f28f80e54173e9a23babdbd5390b7149d88820d522ac756e6d8c2e07e1d368111d8e0269e2de9be642ec078e041ecd94fb9cc910ea920f27c4c12d9e7dac585f500374420367353585e1393f59d8624fff93df0405aa652b68449867e2ec9c7e747b94228348db65980aa0137d7b585d5f976b057590801b84392f6c7d9abe26a566c9d2d6ae3590a61c91a32311af4578ad7a937954f806524a8738390ef7b3f38dabcf3cc3622de68d21135672a5770524b0541252c564c8fb8a3a2c929146f3849cec1765760aa1ed5815e3d47fa8217b8e06de4cd0f04a45e781ccd5482590c53772c3f8eeb20845a0025e5a58c37bdecc95aa337fceb8f788ff146db166bfbf9464cbb12e8b636c8447cd2294ff1fcb2c9b8bc0d8b11fd237da44acd3716899e23b341b922309533e4ed4b7f303c6dc63a6cb890231df87166b852fab4f358196ac2fb2bf743fc7245168dbf3e10a232704cad2e0df723cacf87dce90d8d990c4b993c279a5591c06b5bc9bb6b3bed34b01c49c752f2f830c27ee59c0360c2836fbd7e8ae7103a3e5cf8cedc4044c3977c33a21a3604fba86310b355ad2da6eebbe4a7dcd96a4c77356d28367373f24ba1c6b3aca6869fff82e4a8f8899be1c481cb12a528cff9ca9cebf74cdda003899306ec04290c3a149aba75a4af20954386fd9f4d3ee365b5497927ef20407c80f0a38c7a58a7f825beace7d579b398aed03ea526580225846d67df3152ba8f13f9d9af"));
		
	}
}
