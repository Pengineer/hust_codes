package edu.hust.mapreduce.test;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.hust.mapreduce.MapperClass;

/** 
 * 在HADOOP中使用MRUNIT进行单元测试
 *
 */

@SuppressWarnings("all")
public class MapFunctionTest {
	private Mapper mapper;
	private MapDriver driver;
	
	@Before
	public void init() {
		mapper = new MapperClass();
		driver = new MapDriver(mapper);
	}
	
	@Test
	public void test() {
		String line = "This is a test for MapReduce";//Mapper的输入参数value
		driver.withInput(null, new Text(line))
			  .withOutput(new Text("This"), new IntWritable(1))
			  .withOutput(new Text("is"), new IntWritable(1))
			  .withOutput(new Text("a"), new IntWritable(1))
			  .withOutput(new Text("test"), new IntWritable(1))
			  .withOutput(new Text("for"), new IntWritable(1))
			  .withOutput(new Text("MapReduce"), new IntWritable(1))
			  .runTest();
	}
	
	@After
	public void destory() {
		mapper = null;
		driver = null;
	}
	
}
