/*
�ַ���ȡ����������
�û������ṩ��һ��һ�ζ�һ�еķ���readLine����������ı����ݵĻ�ȡ��
������nullʱ����ʾ�����ļ�ĩβ��

readLine()ֻ���ؽ�����֮ǰ����Ч���ݣ��������ػس�����


�����ǻ��������������ݣ����ڻ���������������ģ������������ļ�������ϵͳ�ײ���Զ��ؽ��ļ��е�������������ʽ�浽�������У�

�����ǵ��û�������readLine()����ʱ���Ϳ��Զ�ȡ�������е�����(������Ҳ����������ʽ����--new BufferedReader)
*/

import java.io.*;

class BufferReaderDemo 
{
	public static void main(String[] args)  throws Exception
	{
		FileReader fd = new FileReader("d.txt");

		BufferedReader bufr = new BufferedReader(fd);//����һ���ַ���ȡ��������

		String str = null;

		while((str=bufr.readLine())!=null)  
		{
			System.out.println(str);
		}

		bufr.close();
	}
}
