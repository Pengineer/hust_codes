/*
��ȡ����¼�룺
System.out:��Ӧ���Ǳ�׼����豸--->����̨��
System.in :��Ӧ���Ǳ�׼�����豸--->���̡�

����
ͨ������¼�����ݡ�
��¼���һ�����ݺ󣬾ͽ��������ݽ��д�ӡ��
���¼���������over,��ôֹͣ¼�롣
*/

import java.io.*;

class ReadIn 
{
	public static void main(String[] args) throws IOException
	{
		InputStream is = System.in;  //��ȡ����¼�����

		StringBuilder buf = new StringBuilder();

		int by = 0 ;

		while(true)
		{
			int ch = is.read();

			if(ch=='\r')    //��Ϊ��windowsϵͳ�£��س�����"\r\n",��˵��س�ʱ��������\r������\r��������ֱ��ͨ��\n���жϻس�
				continue;
			if(ch=='\n')    //�س���ʾ��һ�еĽ�����Ӧ��buf�е�����ת���ַ������
			{
				String s = buf.toString();
				if("over".equals(s))             //��û��continue���˴�ӦΪif("over\r".equals(s))          ��������ַ�����over���ͽ������롣
					break; 
				sop(s.toUpperCase());
				buf.delete(0,buf.length());
			}
			else
				buf.append((char)ch);
		}
		
		is.close();
	}

	public static void sop(Object obj)
	{
		System.out.println(obj);
	}
}
