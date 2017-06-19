/*

ReadIn.java文件中，输入一个字符就存一次，比较麻烦，效率低下,而且它实现的就是读一行的原理，也就是readLine的方法。

能不能直接使用readLine()方法来完成键盘录入的一行数据的读取呢？

readLine方法就是字符流BufferedReader类中的方法，

而键盘录入的read方法是字节流InputStream的方法。

那么能不能将字节流转成字符流，再使用字符流缓冲区的readLine的方法呢？


*/
import java.io.*;
class  TransStreamDemo
{
	public static void main(String[] args) throws IOException
	{
		//获取键盘录入对象
		InputStream is = System.in;

		//将字节流对象转成字符流对象，使用转换流，InputStreamReader()
		InputStreamReader isr = new InputStreamReader(is);

		//为了提高效率，将字符串进行缓冲区技术高效操作，使用BufferReader()
		BufferedReader br = new BufferedReader(isr);

//		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));   //综合上面的三句就得到这一句，这一句是键盘最常见的操作，以后开发就用它。

		String line = null;

		while((line=br.readLine())!=null)
		{
			if("over".equals(line))
				break;
			System.out.println(line.toString());
		}

		br.close();
	}
}
