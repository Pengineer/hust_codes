package org.cdsc.storage;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.hadoop.conf.Configuration;
import org.csdc.storage.HdfsDAO;


public class HDFSTest {
	
	private HdfsDAO hdfsDao;
	
	public HDFSTest(){
		hdfsDao = new HdfsDAO("hdfs://192.168.88.151:9000",new Configuration());
	}
	
	public void testUpload() throws IOException{
		String filePath = "E:\\研究生论文\\实验\\hdfs\\test42.iso";
		Date start = new Date();
		hdfsDao.copyFile(filePath,"/tmp");
		Long cost = (new Date().getTime()-start.getTime())/1000;		
		long fileSize = new File(filePath).length();
		System.out.println("消耗时间:"+ cost+"s" );
		System.out.println("文件大小"+fileSize +"字节");
		System.out.println("上传速度"+fileSize/cost/1024 +"KB/s");
	}
	
	public void testDownload() throws IOException{
		String filePath = "F:\\test\\test.iso";
		Date start = new Date();
		hdfsDao.fetch("/tmp/test.iso", filePath);
		Long cost = (new Date().getTime()-start.getTime());		
		long fileSize = new File(filePath).length();
		System.out.println("消耗时间:"+ cost/1000+"s" );
		System.out.println("文件大小"+fileSize +"字节");
		System.out.println("下载速度"+fileSize*1000/cost/1024 +"KB/s");
	}
	
	public static void main(String[] args) throws IOException {
		HDFSTest test = new HDFSTest();
		test.testUpload();
		test.testDownload();
	}
}
