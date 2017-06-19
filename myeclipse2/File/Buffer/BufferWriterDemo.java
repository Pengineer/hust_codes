/*
缓冲区是为了提高流的操作效率而出现的。

所以在创建缓冲区之前，来先有流对象。

该缓冲区中提供了一个跨平台的换行符:newLine().

*/

import java.io.*;

class  BufferWriterDemo
{
	public static void main(String[] args) throws Exception 
	{
		FileWriter fw = new FileWriter("d.txt");         //创建一个字符写入流对象。同时创建了一个文件d.txt

		BufferedWriter bufw = new BufferedWriter(fw);    //将需要被提高效率的流对象作为参数传递给缓冲区的构造函数即可(关联)。   其实缓冲区内部是封装了数组的，将流中的数据写到这个缓冲数组中，最后刷到目的地

		for(int x=0 ; x<5 ; x++)
		{
			bufw.write("abcd"+x);  //将数据写到流的缓冲区中
			bufw.newLine();
			bufw.flush();                               //********************记住，只要用到缓冲区，既要记得刷新。**********************
		}//因为缓冲区数组是在内存中的，当突然掉电时，内存就会释放，因此要经常刷缓冲区，将流的缓冲区中的数据刷到指定目录中

		bufw.close();                                   //其实，关闭缓冲区，就是关闭缓冲区中的流对象。
	}
}
