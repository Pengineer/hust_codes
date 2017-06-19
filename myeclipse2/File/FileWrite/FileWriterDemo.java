/*
字符流和字节流：

字节流两个基类： InPutStream    OutPutStream   

字符流两个基类： Reader   Writer

先学习字符流的特点：
既然IO流是用于操作数据的，那么数据的最常见体现形式是文件，因此先操作文件。

需求：在硬盘上创建一个文件，并向文件中写入文字数据。

在API文档中找到专门用操作文件的Writer子类对象：FileWriter。后缀名是父类名，前缀名是该流对象的功能。

*/


import java.io.*;

class  FileWriterDemo
{
	public static void main(String[] args)  throws IOException
	{
		//创建一个FileWriter对象该对象一被初始化就需要明确被操作的文件，而且该文件会被创建到指定的目录下，如果该文件已有同名文件，将被覆盖。
		FileWriter fw = new FileWriter("demo.txt");

		//调用Write方法，将字符串写入到流中。  write()是FileWriter的父类OutputStreamWriter中的方法
		fw.write("liangjian");

		//刷新流对象中缓冲区的内容，将对象刷到目的文件中。
		fw.flush();

		//关闭流资源，但在关闭之前会先刷新流对象缓冲区中的数据，将数据刷到目的地，
		//和flush的区别：flush刷新后，流可以继续使用，close刷新后，流将会被关闭。
		fw.close();
	}
}
