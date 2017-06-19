/*
字符读取流缓冲区：
该缓冲区提供了一个一次读一行的方法readLine，方便与对文本数据的获取。
当返回null时，表示读到文件末尾。

readLine()只返回结束符之前的有效数据，并不返回回车符。


读的是缓冲区中流的数据，由于缓冲区中是有数组的，当缓冲区与文件关联后，系统底层会自动地将文件中的数据以流的形式存到缓冲区中，

当我们调用缓冲区的readLine()方法时，就可以读取缓冲区中的数据(此数据也是以流的形式存在--new BufferedReader)
*/

import java.io.*;

class BufferReaderDemo 
{
	public static void main(String[] args)  throws Exception
	{
		FileReader fd = new FileReader("d.txt");

		BufferedReader bufr = new BufferedReader(fd);//创建一个字符读取流缓冲区

		String str = null;

		while((str=bufr.readLine())!=null)  
		{
			System.out.println(str);
		}

		bufr.close();
	}
}
