package edu.hust.mapreduce.test;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.apache.hadoop.mrunit.mapreduce.MapReduceDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.hust.mapreduce.MapperClass;
import edu.hust.mapreduce.ReducerClass;

@SuppressWarnings("all")
public class MapReduceFunctionTest {
	private Mapper mapper;
	private Reducer reducer;
	private MapReduceDriver driver;
	
	@Before
	public void init() {
		mapper = new MapperClass();
		reducer = new ReducerClass();
		driver = new MapReduceDriver(mapper, reducer);
	}
	
	@Test
	public void test() {
		String line = "This is a test for MapReduce";
		driver.withInput(null, new Text(line))
			  .withOutput(new Text("MapReduce"), new IntWritable(1))  //�����Ҫ����
			  .withOutput(new Text("This"), new IntWritable(1))
			  .withOutput(new Text("a"), new IntWritable(1))
			  .withOutput(new Text("for"), new IntWritable(1))
			  .withOutput(new Text("is"), new IntWritable(1))
			  .withOutput(new Text("test"), new IntWritable(1)).runTest();
	}
	
	@After
	public void destory() {
		mapper = null;
		reducer = null;
		driver = null;
	}
}
