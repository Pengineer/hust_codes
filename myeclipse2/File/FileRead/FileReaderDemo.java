
import java.io.*;

class FileReaderDemo 
{
	public static void main(String[] args)  throws IOException   //为方便演示，直接将异常抛出去
	{
		FileReader fd = new FileReader("E:\\programming exercises\\Java\\File\\FileWrite\\demo.txt");   //创建一个文件流读取对象，和指定文件的名称相关联。

		for(int x=0 ; (x=fd.read())!=-1 ; )   //read()方法一次只能读取一个字符，而且会自动往下读。
		{
			System.out.println((char)x);
		}

		fd.close();
	}
}
