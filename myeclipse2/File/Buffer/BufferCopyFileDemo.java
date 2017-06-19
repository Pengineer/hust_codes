/*
需求：通过缓冲区复制指定文件。
*/

import java.io.*;

class BufferCopyFileDemo 
{
	public static void main(String[] args) 
	{
		copyMethod();
	}

	public static void copyMethod()
	{
		BufferedWriter bufw = null ;
		BufferedReader bufr = null ;
		try
		{
			bufw = new BufferedWriter(new FileWriter("BufferCopyFile.txt"));
			bufr = new BufferedReader(new FileReader("BufferReaderDemo.java"));  //将BufferReaderDemo.java中的数据放到缓冲区bufr中。

			String line = null ;
			
			while((line=bufr.readLine())!=null)   //读取bufr缓冲区的数据
			{
				bufw.write(line);   //将line中的数据写到bufw缓冲区
				bufw.newLine();     //写入一个行分隔符
				bufw.flush();       //将bufw缓冲区中的数据刷到指定的目的地   
			}
		}
		catch (Exception e)
		{
			throw new RuntimeException("读写失败") ;
		}
		finally
		{
			if(bufr!=null)
			try
			{
				bufr.close();
			}
			catch (Exception e)
			{
				throw new RuntimeException("读关闭失败") ;
			}

			if(bufw!=null)
			try
			{
				bufw.close();   //colse的同时将bufw中的数据copy到BufferCopyFile.txt中。
			}
			catch (Exception e)
			{
				throw new RuntimeException("写关闭失败") ;
			}
		}
	}
}
