package edu.hust.mapreduce.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.hust.mapreduce.ReducerClass;

/** 
 * 在HADOOP中使用MRUNIT进行单元测试
 *
 * 版本问题，此处无法测试
 */

@SuppressWarnings("all")
public class ReduceFunctionTest {
	
	private Reducer reducer;
	private ReduceDriver reduceDriver;
	
	@Before
	public void initReduce() {
		reducer = new ReducerClass();
		reduceDriver = new ReduceDriver(reducer);
	}
	
	@Test
	public void test() {
		String key = "is";
		List values = new ArrayList();
		values.add(new IntWritable(5));
		
		reduceDriver.withInput(new Text("is"), values)
					.withOutput(new Text("is"), new IntWritable(5)).runTest();
	}
	
	@After
	public void destoryReduce() {
		reducer = null;
		reduceDriver = null;
	}

}
