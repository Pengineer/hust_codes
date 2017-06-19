package csdc.tool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

/**
 * 文件处理公共类
 * @author 徐涵
 * @author 龚凡	2010.12.09	更新文件保存方法，添加邮件附件文件名公共部分的生成
 * @author leida 2011.12.23 添加新建文件夹、递归删除文件夹、清除文件夹下所有文件的方法
 */
public class FileTool {

	/**
	 * 保存上传文件
	 * @param file 文件对象
	 * @param fileName 原始文件名
	 * @param savePath 保存路径
	 * @param signID 随机字符串
	 * @return 新文件名
	 * @throws Exception
	 */
	public static String saveUpload(File file, String fileName, String savePath, String signID) throws Exception {
		// 获得绝对路径
		String realPath = ServletActionContext.getServletContext().getRealPath(savePath), realName;
		// 获取源文件后缀名
		String extendName = fileName.substring(fileName.lastIndexOf("."));

		File existingFile;
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		do {
			// 获取系统时间并转成字符串
			Date date = new Date();
			String dateformat = format.format(date);
			// 生成新的文件名
			realName = dateformat + extendName;
			realPath = realPath.replace('\\','/');
			existingFile = new File(realPath + "/" + realName);
		} while (existingFile.exists());
		mkdir_p(realPath);
		boolean success = file.renameTo(new File(realPath, realName));
		if (!success){
			FileUtils.copyFile(file, new File(realPath, realName));
		}
		return realName;
	}

	/**
	 * 保存文件
	 * @param file 文件对象
	 * @param savePath 保存路径，如"/upload/mail"
	 * @param fileName 新文件名，不包括后缀名，如"attr_sessionId_yyyyMMdd_1"
	 * @param oFileName 原始文件名
	 * @return 返回用于存于数据库的相对文件路径
	 * @throws Exception
	 */
	public static String saveUploadFile(File file, String savePath, String fileName, String oFileName) throws Exception {
		String realPath = ServletActionContext.getServletContext().getRealPath(savePath);// 获得硬盘路径
		String relativePath = fileName + oFileName.substring(oFileName.lastIndexOf("."));// 完整相对路径
		realPath = realPath.replace('\\','/');
		FileOutputStream fos = new FileOutputStream(realPath + "/" + relativePath);// 创建文件输出流
		FileInputStream fis = new FileInputStream(file);// 以上传文件建立一个输入流
		// 保存文件
		byte[] b = new byte[1024];
		int len = 0;
		while ((len = fis.read(b)) != -1) {
			fos.write(b, 0, len);
		}
		fis.close();
		fos.close();
		return savePath + "/" + relativePath;
	}

	/**
	 * 删除文件
	 * @param fileName 待删除的文件名
	 */
	public static void fileDelete(String fileName) {
		fileName = ServletActionContext.getServletContext().getRealPath(fileName);
		File file = new File(fileName);
		if (file.exists()) {
			file.delete();
		}
	}

	/**
	 * 递归新建目录
	 * @param path 完整路径
	 * @return 是否成功新建目录
	 * @author leida 2011-12-23
	 */
	public static boolean mkdir_p(String path) {
		java.io.File file = new java.io.File(path);
		if(!file.exists()) {
			return file.mkdirs();
		}
		else
			return false;
	}
	
	/**
	 * 递归删除目录及目录下所有文件、子目录
	 * @param folderPath 目录路径
	 * @author leida 2011-12-23
	 */
	public static void rm_fr(String folderPath) {
		try {
			delAllFile(folderPath); // 删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); // 删除空文件夹
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除目录下所有文件及子目录
	 * @param path 目录路径
	 * @return 是否成功删除
	 * @author leida 2011-12-23
	 */
	public static boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
				rm_fr(path + "/" + tempList[i]);// 再删除空文件夹
				flag = true;
			}
		}
		return flag;
	}
	
	/**
	 * 获取文件扩展名。
	 * 如: "example.tar.gz" 返回 ".gz"
	 * @param file 文件
	 * @return
	 * @author xuhan 2012-9-19
	 */
	public static String getExtension(File file) {
		if (file == null) {
			return "";
		}
		int dotIndex = file.getName().lastIndexOf('.');
		return dotIndex >= 0 ? file.getName().substring(dotIndex) : "";
	}
	
	/**
	 * 按照【给定文件的扩展名】和【当前时间】返回一个【指定目录】下可用的文件路径
	 * @param dir 指定目录的工程相对路径
	 * @param file 给定的文件
	 * @return
	 * @author xuhan 2012-9-19
	 */
	public static String getAvailableFilename(String dir, File file) {
		String fileName = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SSS_").format(new Date()) + SignID.getRandomString(5) + getExtension(file);
		return dir + "/" + fileName;
	}
	

	/**
	 * 按照【给定文件的扩展名】返回一个【指定目录】下可用的文件路径
	 * @param dir 指定目录的工程相对路径
	 * @param file 给定的文件
	 * @param id
	 * @return
	 * @author xuhan 2012-9-19
	 */

	public static String getTrueFilename(String dir,String id, File file) {
		String realName = null;
		if (dir.contains("temp")) {
			realName  = "temp_"+id+"_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) +"_"+ SignID.getRandomString(4) +getExtension(file);
		} else if (dir.contains("news")) {
			realName  = "news"+id+"_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) +"_"+ SignID.getRandomString(4) +getExtension(file);
		} else if (dir.contains("notice")) {
			realName  = "notice"+id+"_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) +"_"+ SignID.getRandomString(4) +getExtension(file);
		}
		return dir + "/" + realName;
	}
	
	/**
	 * 根据文件路径名判断文件是否存在
	 * @param fileName	文件路径名
	 * @return
	 */
	public static boolean isExsits(String fileName){
		boolean flag = false;
		File file = new File(fileName);
		if (file.exists()) {
			flag = true;
		}
		return flag;
	}
}
