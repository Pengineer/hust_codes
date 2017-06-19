package org.csdc.tool;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


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

	@SuppressWarnings("deprecation")
	public static String saveUpload(File file, String fileName, String savePath, String signID) throws Exception {
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		String realPath = requestAttributes.getRequest().getRealPath(savePath),realName;
		// 获得绝对路径
//		String realPath = request.getSession().getServletContext().getRealPath(savePath),realName;
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
		mkdir(realPath);
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
	@SuppressWarnings("deprecation")
	public static String saveUploadFile(File file, String savePath, String fileName, String oFileName) throws Exception {
//		String realPath = request.getSession().getServletContext().getRealPath(savePath);// 获得硬盘路径
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		String realPath = requestAttributes.getRequest().getRealPath(savePath);
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
	@SuppressWarnings("deprecation")//不检测过期方法
	public static void fileDelete(String fileName) {	
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		fileName = requestAttributes.getRequest().getRealPath(fileName);
//在控制器中获取fileName;
//		fileName = ServletActionContext.getServletContext().getRealPath(fileName);
		File file = new File(fileName);
		if (file.exists()) {
			file.delete();
		}
	}
	
	public static void cp(String sourceFile, String targetFile) throws IOException {
        BufferedInputStream inBuff = null;
        BufferedOutputStream outBuff = null;
        try {
            // 新建文件输入流并对它进行缓冲
            inBuff = new BufferedInputStream(new FileInputStream(sourceFile));

            // 新建文件输出流并对它进行缓冲
            outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));

            // 缓冲数组
            byte[] b = new byte[1024 * 5];
            int len;
            while ((len = inBuff.read(b)) != -1) {
                outBuff.write(b, 0, len);
            }
            // 刷新此缓冲的输出流
            outBuff.flush();
        } finally {
            // 关闭流
            if (inBuff != null)
                inBuff.close();
            if (outBuff != null)
                outBuff.close();
        }
    }
	
	/**
	 * 新建目录
	 * @param path 完整路径
	 * @return 是否成功新建目录(目录已存在，不创建目录，返回false；目录不存在，创建目录，返回true)
	 * @author leida 2011-12-23
	 */
	public static boolean mkdir(String path) {
		java.io.File file = new java.io.File(path);
		if(!file.exists()) {
			return file.mkdirs();
		}
		return false;
	}
	
	/**
	 * 递归删除目录及目录下所有文件、子目录
	 * @param folderPath 目录路径或文件名
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
	 * 如: "example.tar.gz" 返回 "gz"
	 * @param file 文件
	 * @return
	 * @author xuhan 2012-9-19
	 */
	public static String getExtension(File file) {
		if (file == null) {
			return "";
		}
		int dotIndex = file.getName().lastIndexOf('.');
		return dotIndex >= 0 ? file.getName().substring(dotIndex+1) : "";
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
	 * @return
	 * @author xuhan 2012-9-19
	 */
	public static String getTrueFilename(String dir, File file) {
		return dir + "/" + file.getName();
	}
	
	public static byte[] getFileByByte(String filename){
		BufferedInputStream in = null;
		ByteArrayOutputStream out = null;
		try {
			in = new BufferedInputStream(new FileInputStream(filename));        
			out = new ByteArrayOutputStream(1024);              
			byte[] temp = new byte[1024];        
			int size = 0;        
			while ((size = in.read(temp)) != -1) {        
			    out.write(temp, 0, size);        
			}        
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}             
        return  out.toByteArray();      
	}
	
	public static  void writeFileByByte(String filename,byte [] content){
		ByteArrayInputStream in = new ByteArrayInputStream(content);
		BufferedOutputStream out = null;
		try {
			out = new BufferedOutputStream(new FileOutputStream(filename));
			byte[] temp = new byte[1024];        
			int size = 0;        
			while ((size = in.read(temp)) != -1) {        
			    out.write(temp, 0, size);        
			}  
			in.close();
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public static StringBuffer readFileContent(String filename){
		String charset = "UTF-8";
		try {
			charset = charsetString(filename);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		StringBuffer buffer = new StringBuffer();
		String line = null;
        BufferedReader reader=null;
        try {  
        	InputStream is = new FileInputStream(filename);  
        	reader = new BufferedReader(new InputStreamReader(is,charset
                ));  
            line = reader.readLine();  
        } catch (IOException e) {
            e.printStackTrace();  
        }
        while (line != null) {
            buffer.append(line);
            buffer.append("\r\n");
            try {  
                line = reader.readLine();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }
        }
		return buffer;
	}
	
	
	/**
	 * 获取文件名称[不含后缀名]
	 *@param fileName 文件的的路径
	 *@return String
	 */
	public static String getFilePrefix(String fileName){
		int splitIndex = fileName.lastIndexOf(".");
		if(splitIndex==-1)
			return fileName;
        return fileName.substring(0, splitIndex);
	}
	
	/**
	 * 获取文件所在的目录
	 * @param fullPath
	 * @return
	 */
	public static String getDirectory(String fullPath){
		int splitIndex = fullPath.lastIndexOf(File.separator);
		if(splitIndex==-1)
			return "";
		return fullPath.substring(0, splitIndex);
	}
	
	public static String charsetString(String fileName) throws Exception{
        BufferedInputStream bin = new BufferedInputStream(
        new FileInputStream(fileName));
        int p = (bin.read() << 8) + bin.read();
        String code = null;
         
        switch (p) {
            case 0xefbb:
                code = "UTF-8";
                break;
            case 0xfffe:
                code = "Unicode";
                break;
            case 0xfeff:
                code = "UTF-16BE";
                break;
            default:
                code = "GBK";
        }
         
        return code;
    }
	/**
	 * 把文本写入文件中
	 * @param filename
	 * @param fileContent
	 * @return
	 */
	public static void writeFileContent(String filename,String fileContent){
		try {  
            File f = new File(filename);  
            if (!f.getParentFile().exists()) {  
                f.getParentFile().mkdirs();  
            }  
            FileOutputStream out = new FileOutputStream(filename);  
            byte[] bytes = fileContent.getBytes("UTF-8");  
            out.write(bytes);  
            out.flush();  
            out.close();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }
	}
	
	/**
	 * 遍历一个目录下所有文件及文件夹
	 * @param file
	 * @param fileList
	 */
	public static void listFile(File file,List<File> fileList){
		fileList.add(file);
		File[] files = file.listFiles();
		if (files != null) { 
			for(File f :files){
				listFile(f, fileList);
			}
		}
	}
	
	/**
	 * 
	 * @param src 要移动的文件
	 * @param destDir 目的文件夹 (后面不加"\\")
	 */
	public static void move(String src,String destDir){
		File oldFile = new File(src); 
		//文件新（目标）地址 
		//new一个新文件夹 
		File fnewpath = new File(destDir); 
		//判断文件夹是否存在 
		if(!fnewpath.exists()) 
		fnewpath.mkdirs(); 
		//将文件移到新文件里 
		File fnew = new File(destDir +File.separator+oldFile.getName()); 
		oldFile.renameTo(fnew); 
	}
	public static void main(String[] args) throws Exception {
		Date d1 = new Date();
		byte [] content = getFileByByte("E:\\汽车_989.txt");
		System.out.println(new String(content));
	}
}
