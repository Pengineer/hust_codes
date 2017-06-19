package edu.hust.mapreduce;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class ReducerClass1 extends Reducer<Text, Text, Text, Text>{

	public Text keyText = new Text();
	
	//�������Ĳ������磺key:hello values:[1,1,1]  ����hello���ʳ��ֹ�3��
	@Override
	protected void reduce(Text key, Iterable<Text> values, Context context)
			throws IOException, InterruptedException {
		String str = "";
		for (Text val : values) {
			System.out.println(context.getCurrentKey() + "/" + context.getCurrentValue());
			str = str + val.toString();
		}
		keyText.set(str);
		context.write(key, keyText);
	}
	
}
