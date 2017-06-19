package edu.hust.mapreduce;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class MapperClass1 extends Mapper<Object, Text, Text, Text>{//定义输入输出参数类型

	//定义输出参数
	//Text是Hadoop封装String后的类型，IntWritable是Hadoop封装Integer后的类型，这些类能够被串行化
	public Text keyText = new Text();
	public Text t = new Text();
	public IntWritable intValue = new IntWritable(1);//因为是要统计单词的个数，因此value为1，最后reduce把相同key的value相加就实现单词的统计

	@Override
	protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
		//Object和Text类型由Mapper的前两个参数决定
		String str = value.toString();// value是一行数据："hello Hadoop"
		
		StringTokenizer stringTokenizer = new StringTokenizer(str);//切割str，默认按空格切割（等效于split）
		while (stringTokenizer.hasMoreElements()) {
			System.out.println(key + "");
			keyText.set(stringTokenizer.nextToken());//等价于String keyText = stringTokenizer.nextToken();
			//write为写出，其参数与Mapper的后两个参数类型一致
			t.set("|");
			context.write(keyText, t);//等价于：context.write("XXXX",1);
		}
		
		
	}
	
	/*
	 * 文件内容：
	 * create a new file
	 * writeBytes
	 * writeUTF
	 * writeUTF goon
	 * welcome guy
	 * welcome hust
	 * writeUTF good
	   System.out.println(key + "")输出的map的key值：
		0
		0
		0
		0
		18
		29
		38
		38
		52
		52
		64
		64
		77
		77
		可以看出，key指的是每一行的开头相对该文件其实位置的偏移量，其可得知该map由2个Mapper完成。
	 */

}
