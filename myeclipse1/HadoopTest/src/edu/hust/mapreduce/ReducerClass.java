package edu.hust.mapreduce;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class ReducerClass extends Reducer<Text, IntWritable, Text, IntWritable>{

	public IntWritable intValue = new IntWritable();
	
	//传进来的参数例如：key:hello values:[1,1,1]  表明hello单词出现过3次
	@Override
	protected void reduce(Text key, Iterable<IntWritable> values, Context context)
			throws IOException, InterruptedException {
		int sum =0;
		for (IntWritable val : values) {
			sum += val.get();
		}
		intValue.set(sum);
		context.write(key, intValue);
	}
	
}
