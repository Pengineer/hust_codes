/*
需求：复制指定文件。

读写数据流是相互独立的，可以通过buffer数组或容器进行过度关联。

读写结束后，都要关闭流资源。

*/


import java.io.*;

class FileCopyDemo 
{
	public static void main(String[] args) 
	{
		copyMethod();
	}

	public static void copyMethod()
	{
		FileWriter fw = null ;
		FileReader fd = null ;
		try
		{
			fw = new FileWriter("copyFile.txt");
			fd = new FileReader("E:\\programming exercises\\Java\\File\\FileWrite\\FileWriterContinueDemo.java");

			char[] buf = new char[1024];

			int len = 0 ;
			
			while((len=fd.read(buf))!=-1)
			{
				fw.write(buf,0,len);  //将读出的数据流通过buf数组传到写入数据流，然后将写入数据流写到新建的文件中
			}
		}
		catch (Exception e)
		{
			throw new RuntimeException("读写失败") ;
		}
		finally
		{
			if(fd!=null)
			try
			{
				fd.close();
			}
			catch (Exception e)
			{
				throw new RuntimeException("读失败") ;
			}

			if(fw!=null)
			try
			{
				fw.close();
			}
			catch (Exception e)
			{
				throw new RuntimeException("写失败") ;
			}
		}
	}
}
