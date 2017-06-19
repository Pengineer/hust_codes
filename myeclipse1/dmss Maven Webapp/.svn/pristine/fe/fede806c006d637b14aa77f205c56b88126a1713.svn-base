package org.csdc.hadoop;

import java.io.IOException;

import javassist.expr.NewArray;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.csdc.storage.HdfsDAO;
import org.junit.Test;

public class HdfsDAOTest {
	
	public void testAdd() throws IOException{
		Configuration conf = new Configuration();
	    HdfsDAO hdfs = new HdfsDAO();
	    hdfs.copyFile("F:\\h.txt", "/user/fan");
	    hdfs.ls("/");
	}
	
	public void testMkdir() throws IOException{
		Configuration conf = new Configuration();
	    HdfsDAO hdfs = new HdfsDAO();
	    hdfs.mkdirs("/user/test1");
	}
	
	public void testDeldir() throws IOException{
		Configuration conf = new Configuration();
	    HdfsDAO hdfs = new HdfsDAO();
	    hdfs.rmr("/user/test1");
	}
	
	@Test
	public void testCreateFile() throws IOException{
		Configuration conf = new Configuration();
	    HdfsDAO hdfs = new HdfsDAO("192.168.88.151:9000",conf);
	    hdfs.createFile("/tmp/new.txt", "aaaaaaaaaabbbbbbbbbbcccccccccc");
	}
	
	public static void main(String[] args) throws IOException {
		Configuration conf = new Configuration();
	    HdfsDAO hdfs = new HdfsDAO("hdfs://192.168.88.151:9000",conf);
	    hdfs.createFile("/tmp/new.txt", "aaaaaaaaaabbbbbbbbbbcccccccccc");
	}
	
}
