/*
���󣺸���ָ���ļ���

��д���������໥�����ģ�����ͨ��buffer������������й��ȹ�����

��д�����󣬶�Ҫ�ر�����Դ��

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
				fw.write(buf,0,len);  //��������������ͨ��buf���鴫��д����������Ȼ��д��������д���½����ļ���
			}
		}
		catch (Exception e)
		{
			throw new RuntimeException("��дʧ��") ;
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
				throw new RuntimeException("��ʧ��") ;
			}

			if(fw!=null)
			try
			{
				fw.close();
			}
			catch (Exception e)
			{
				throw new RuntimeException("дʧ��") ;
			}
		}
	}
}
