package org.csdc.tool;

import java.io.File;
import java.io.FileOutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javassist.expr.NewArray;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Expand;

import de.innosystec.unrar.Archive;
import de.innosystec.unrar.rarfile.FileHeader;
/**
 * 文件解压缩工具类
 * @author jintf
 * @date 2014-6-16
 */
public class DeCompressUtil {
	/**
	 * 解压zip格式压缩包
	 * 对应的是ant.jar
	 */
	private static void unzip(String sourceZip,String destDir) throws Exception{		
		try{
			destDir = destDir+ sourceZip.substring(sourceZip.lastIndexOf(File.separatorChar),sourceZip.indexOf("."))+File.separator;
			Project p = new Project();
			Expand e = new Expand();
			e.setProject(p);
			e.setSrc(new File(sourceZip));
			e.setOverwrite(false);
			e.setDest(new File(destDir));
			/*
			ant下的zip工具默认压缩编码为UTF-8编码，
			而winRAR软件压缩是用的windows默认的GBK或者GB2312编码
			所以解压缩时要制定编码格式
			*/
			e.setEncoding("gbk");
			e.execute();
		}catch(Exception e){
			throw e;
		}
	}
	/**
	 * 解压rar格式压缩包。
	 * 对应的是java-unrar-0.3.jar，但是java-unrar-0.3.jar又会用到commons-logging-1.1.1.jar
	 */
	private static void unrar(String sourceRar,String destDir) throws Exception{
		Archive a = null;
		FileOutputStream fos = null;
		
		try{
			destDir = destDir+ sourceRar.substring(sourceRar.lastIndexOf(File.separatorChar),sourceRar.indexOf("."))+File.separator;
			a = new Archive(new File(sourceRar));
			FileHeader fh = a.nextFileHeader();
			while(fh!=null){
				if(!fh.isDirectory()){
					//1 根据不同的操作系统拿到相应的 destDirName 和 destFileName
					//String compressFileName = fh.getFileNameString().trim();
					String compressFileName =  fh.getFileNameW().trim();  
	                if(!existZH(compressFileName)){  
	                	compressFileName = fh.getFileNameString().trim();  
	                }  
					String destFileName = "";
					String destDirName = "";
					//非windows系统
					if(File.separator.equals("/")){
						destFileName = destDir + compressFileName.replaceAll("\\\\", "/");
						destDirName = destFileName.substring(0, destFileName.lastIndexOf("/"));
					//windows系统	
					}else{
						destFileName = destDir + compressFileName.replaceAll("/", "\\\\");
						destDirName = destFileName.substring(0, destFileName.lastIndexOf("\\"));
					}
					//2创建文件夹				
					File dir = new File(destDirName);
					if(!dir.exists()||!dir.isDirectory()){
						dir.mkdirs();
					}
					//3解压缩文件
					fos = new FileOutputStream(new File(destFileName));
					a.extractFile(fh, fos);
					fos.close();
					fos = null;
				}
				fh = a.nextFileHeader();
			}
			a.close();
			a = null;
		}catch(Exception e){
			throw e;
		}finally{
			if(fos!=null){
				try{fos.close();fos=null;}catch(Exception e){e.printStackTrace();}
			}
			if(a!=null){
				try{a.close();a=null;}catch(Exception e){e.printStackTrace();}
			}
		}
	}
	/**
	 * 解压缩
	 */
	public static void deCompress(String sourceFile,String destDir) throws Exception{		
		//根据类型，进行相应的解压缩
		String type = sourceFile.substring(sourceFile.lastIndexOf(".")+1);
		if(type.equals("zip")){
			DeCompressUtil.unzip(sourceFile, destDir);
		}else if(type.equals("rar")){
			DeCompressUtil.unrar(sourceFile, destDir);
		}else{
			throw new Exception("只支持zip和rar格式的压缩包！");
		}
	}
	
	public static boolean existZH(String str) {
		String regEx = "[\\u4e00-\\u9fa5]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		while (m.find()) {
			return true;
		}
		return false;
	}
	
}

