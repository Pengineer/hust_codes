/*
�ַ������ֽ�����

�ֽ����������ࣺ InPutStream    OutPutStream   

�ַ����������ࣺ Reader   Writer

��ѧϰ�ַ������ص㣺
��ȻIO�������ڲ������ݵģ���ô���ݵ����������ʽ���ļ�������Ȳ����ļ���

������Ӳ���ϴ���һ���ļ��������ļ���д���������ݡ�

��API�ĵ����ҵ�ר���ò����ļ���Writer�������FileWriter����׺���Ǹ�������ǰ׺���Ǹ�������Ĺ��ܡ�

*/


import java.io.*;

class  FileWriterDemo
{
	public static void main(String[] args)  throws IOException
	{
		//����һ��FileWriter����ö���һ����ʼ������Ҫ��ȷ���������ļ������Ҹ��ļ��ᱻ������ָ����Ŀ¼�£�������ļ�����ͬ���ļ����������ǡ�
		FileWriter fw = new FileWriter("demo.txt");

		//����Write���������ַ���д�뵽���С�  write()��FileWriter�ĸ���OutputStreamWriter�еķ���
		fw.write("liangjian");

		//ˢ���������л����������ݣ�������ˢ��Ŀ���ļ��С�
		fw.flush();

		//�ر�����Դ�����ڹر�֮ǰ����ˢ�������󻺳����е����ݣ�������ˢ��Ŀ�ĵأ�
		//��flush������flushˢ�º������Լ���ʹ�ã�closeˢ�º������ᱻ�رա�
		fw.close();
	}
}
