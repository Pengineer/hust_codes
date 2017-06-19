package org.csdc.hadoop;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

public class TestFile {
	public static void main(String[] args) { 

		try{
			
			String localFilePath = "F:\\aa.txt";
			String dest = "hdfs://root@master:9000/user/tian/aa.txt";
			InputStream in = new BufferedInputStream(new FileInputStream(localFilePath));
		    Configuration conf = new Configuration();  	 	
		    System.setProperty("HADOOP_USER_NAME", "root");
		    FileSystem fs = FileSystem.get(URI.create(dest),conf);  
		    OutputStream out = fs.create(new Path(dest)) ;
		    IOUtils.copyBytes(in, out, 4096,true);
		    fs.close();  
	    } catch (Exception e) {  
	        // TODO Auto-generated catch block  
	        e.printStackTrace();  
	    } 
	}
}
