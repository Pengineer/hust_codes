/*
注意区分了，易混淆:
FileInputStream：读文件，将数据输入到流中

FileOutputStream：写文件
*/

import java.io.*;

class InOutputStreamDemo 
{
	public static void main(String[] args) throws IOException
	{
	//	writeFile();
		readFile_1();
	//	readFile_2();
	//	readFile_3();
	}

	public static void writeFile() throws IOException
	{
		FileOutputStream fos = new FileOutputStream("demo.txt");  //数据从流中输出（out），写入到文件中

		fos.write("liangjian".getBytes());

		fos.close();
	}

	public static void readFile_1() throws IOException    //-------------读法1：读一个输出一个
	{
		FileInputStream fis = new FileInputStream("demo.txt");  //将文件中数据输入到流中（in），read是读取流中的数据，数据都是在流中传输的。

		int ch = 0;

		while((ch=fis.read())!=-1)
		{
			System.out.println((char)ch);
		}
		
		fis.close();
	}

	public static void readFile_2() throws IOException    //读法2：一次读1024个byte后在输出
	{
		FileInputStream fis = new FileInputStream("demo.txt");

		byte[] buf = new byte[1024];

		int len = 0 ;

		while((len=fis.read(buf))!=-1)
		{
			System.out.println(new String(buf,0,len));
		}

		fis.close();
	}

	public static void readFile_3() throws IOException
	{
		FileInputStream fis = new FileInputStream("demo.txt");

		byte[] buf = new byte[fis.available()];   //定义一个长度刚刚好的缓冲区。-----*****不建议这个做，还是以上面的方法为主，特别是文件较大时，容易发生内存溢出。

		fis.read(buf);
	
		System.out.println(new String(buf));
		

		fis.close();
	}
}
