import java.io.*;

class FileReaderDemo2 
{
	public static void main(String[] args)  throws IOException
	{
		FileReader fd = new FileReader("demo.txt");

		char[] buf = new char[1024];   //一般取1024的整数倍。

		int num = 0 ;
		while((num=fd.read(buf))!=-1)  //将文件中的数据1次读1024个字符到buf数组缓存区中(此处由于文档中数据只有9个，因此读第一次时，返回num=9，执行一次循环体，第二次读时，就返回-1了)
		{
			System.out.println(new String(buf,0,num)); //将数组变成字符串打印  （StringDemo中有详细介绍）
		} 

		fd.close();

		/*

		char[]  chr = {'a','b','c','d','e','f','g'};

		String str = String.valueOf(chr);   //copyValueOf()也可以将数组转换成字符串

		System.out.println(str);

		*/
	}
}
