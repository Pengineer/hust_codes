/*
����ͨ������������ָ���ļ���
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
			bufr = new BufferedReader(new FileReader("BufferReaderDemo.java"));  //��BufferReaderDemo.java�е����ݷŵ�������bufr�С�

			String line = null ;
			
			while((line=bufr.readLine())!=null)   //��ȡbufr������������
			{
				bufw.write(line);   //��line�е�����д��bufw������
				bufw.newLine();     //д��һ���зָ���
				bufw.flush();       //��bufw�������е�����ˢ��ָ����Ŀ�ĵ�   
			}
		}
		catch (Exception e)
		{
			throw new RuntimeException("��дʧ��") ;
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
				throw new RuntimeException("���ر�ʧ��") ;
			}

			if(bufw!=null)
			try
			{
				bufw.close();   //colse��ͬʱ��bufw�е�����copy��BufferCopyFile.txt�С�
			}
			catch (Exception e)
			{
				throw new RuntimeException("д�ر�ʧ��") ;
			}
		}
	}
}
