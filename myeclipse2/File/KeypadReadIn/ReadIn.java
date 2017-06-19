/*
读取键盘录入：
System.out:对应的是标准输出设备--->控制台。
System.in :对应的是标准输入设备--->键盘。

需求：
通过键盘录入数据。
当录入第一行数据后，就将该行数据进行打印。
如果录入的数据是over,那么停止录入。
*/

import java.io.*;

class ReadIn 
{
	public static void main(String[] args) throws IOException
	{
		InputStream is = System.in;  //获取键盘录入对象

		StringBuilder buf = new StringBuilder();

		int by = 0 ;

		while(true)
		{
			int ch = is.read();

			if(ch=='\r')    //因为在windows系统下，回车符是"\r\n",因此当回车时，先输入\r，若将\r丢掉，则直接通过\n来判断回车
				continue;
			if(ch=='\n')    //回车表示第一行的结束，应将buf中的数组转成字符串输出
			{
				String s = buf.toString();
				if("over".equals(s))             //若没有continue；此处应为if("over\r".equals(s))          若输入的字符串是over，就结束输入。
					break; 
				sop(s.toUpperCase());
				buf.delete(0,buf.length());
			}
			else
				buf.append((char)ch);
		}
		
		is.close();
	}

	public static void sop(Object obj)
	{
		System.out.println(obj);
	}
}
