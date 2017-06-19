package edu.hust.hdfs;


import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;


/**
 * 使用IOUtile工具类操作HDFS
 * @author hadoop
 * 
 * 注意：导入的包是hadoop封装好的，而不是common下的同名包
 *
 */
public class HDFSFileTestTool {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		readFile();
	}
	
	public static void readFile() throws IOException {
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(conf);
		
		Path path = new Path("/user/hadoop/JavaTest/createFile.txt");
		
		FSDataInputStream inputStream = fs.open(path);
		
		IOUtils.copyBytes(inputStream, System.out, 4096, true);//打印到控制台
		
		IOUtils.closeStream(inputStream);
	}

}
