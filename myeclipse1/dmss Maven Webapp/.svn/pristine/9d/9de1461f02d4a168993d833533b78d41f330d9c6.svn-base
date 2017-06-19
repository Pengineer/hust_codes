package org.csdc.hadoop;

import java.io.IOException;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Hdfs;
import org.apache.hadoop.fs.Path;

import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.MapFile;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.SequenceFile.Reader;
import org.apache.hadoop.io.SequenceFile.Writer;
import org.apache.hadoop.io.Text;
import org.csdc.model.Document;
import org.csdc.storage.HdfsDAO;
import org.csdc.tool.FileTool;

public class MergeFile {
	
	public static void main(String[] args) throws IOException {
		System.setProperty("HADOOP_USER_NAME", "root");
		Configuration conf = new Configuration();
	    String uri = "hdfs://master:9000/dmss/201402/10";
	    FileSystem fs = FileSystem.get(URI.create(uri), new Configuration());
		MapFile.Reader reader = null;
		Text key = new Text("0");
		Text value = new Text();
		try{
			reader = new MapFile.Reader(fs, uri, conf);
			reader.get(key, value);
			System.out.println(value.toString());
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			IOUtils.closeStream(reader);
		}	
	}
	
	public  void write() throws IOException {
		System.setProperty("HADOOP_USER_NAME", "root");
		Configuration conf = new Configuration();
	    String uri = "hdfs://master:9000/dmss/test02";
	    FileSystem fs = FileSystem.get(URI.create(uri), new Configuration());
		MapFile.Writer writer = null;
		Text key = new Text();
		Text value = new Text();
		
		try{
			writer = new MapFile.Writer(new Configuration(), fs, uri, key.getClass(), value.getClass());			
			key.set("1");
			value.set(FileTool.readFileContent("F://test/2M.zip").toString());
			writer.append(key, value);
			//
			key.set("2");
			value.set("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
			writer.append(key, value);
			//
			key.set("3");
			value.set("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
			writer.append(key, value);
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			IOUtils.closeStream(writer);
		}	
	}
	
}
