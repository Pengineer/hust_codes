package edu.hust.hdfs;


import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;


/**
 * ʹ��IOUtile���������HDFS
 * @author hadoop
 * 
 * ע�⣺����İ���hadoop��װ�õģ�������common�µ�ͬ����
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
		
		IOUtils.copyBytes(inputStream, System.out, 4096, true);//��ӡ������̨
		
		IOUtils.closeStream(inputStream);
	}

}
