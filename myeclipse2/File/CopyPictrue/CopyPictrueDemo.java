/*
图片文件的复制：使用字节流，字节流一般用来处理媒体文件。

字符流只用来处理文字文件。
*/


import java.io.*;

class CopyPictrueDemo 
{
	public static void main(String[] args) 
	{
		FileOutputStream fos = null;
		FileInputStream  fis = null;
		try
		{
			fos = new FileOutputStream("2.png");
			fis = new FileInputStream("1.png");

			byte[] buf = new byte[1024];      //字节流
			int len;

			while((len = fis.read(buf))!=-1)
			{
				fos.write(buf,0,len);
			}
		}
		catch (Exception e)
		{
			throw new RuntimeException("读写失败");
		}
		finally
		{
			if(fos!=null)
			try
			{
				fos.close();
			}
			catch (Exception e)
			{
				throw new RuntimeException("关闭失败");
			}
			if(fis!=null)
			try
			{
				fis.close();
			}
			catch (Exception e)
			{
				throw new RuntimeException("关闭失败");
			}
		}
	}
}
