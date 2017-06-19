package edu.hust.mapreduce;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class MapperClass1 extends Mapper<Object, Text, Text, Text>{//�������������������

	//�����������
	//Text��Hadoop��װString������ͣ�IntWritable��Hadoop��װInteger������ͣ���Щ���ܹ������л�
	public Text keyText = new Text();
	public Text t = new Text();
	public IntWritable intValue = new IntWritable(1);//��Ϊ��Ҫͳ�Ƶ��ʵĸ��������valueΪ1�����reduce����ͬkey��value��Ӿ�ʵ�ֵ��ʵ�ͳ��

	@Override
	protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
		//Object��Text������Mapper��ǰ������������
		String str = value.toString();// value��һ�����ݣ�"hello Hadoop"
		
		StringTokenizer stringTokenizer = new StringTokenizer(str);//�и�str��Ĭ�ϰ��ո��и��Ч��split��
		while (stringTokenizer.hasMoreElements()) {
			System.out.println(key + "");
			keyText.set(stringTokenizer.nextToken());//�ȼ���String keyText = stringTokenizer.nextToken();
			//writeΪд�����������Mapper�ĺ�������������һ��
			t.set("|");
			context.write(keyText, t);//�ȼ��ڣ�context.write("XXXX",1);
		}
		
		
	}
	
	/*
	 * �ļ����ݣ�
	 * create a new file
	 * writeBytes
	 * writeUTF
	 * writeUTF goon
	 * welcome guy
	 * welcome hust
	 * writeUTF good
	   System.out.println(key + "")�����map��keyֵ��
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
		���Կ�����keyָ����ÿһ�еĿ�ͷ��Ը��ļ���ʵλ�õ�ƫ��������ɵ�֪��map��2��Mapper��ɡ�
	 */

}
