/*

ReadIn.java�ļ��У�����һ���ַ��ʹ�һ�Σ��Ƚ��鷳��Ч�ʵ���,������ʵ�ֵľ��Ƕ�һ�е�ԭ��Ҳ����readLine�ķ�����

�ܲ���ֱ��ʹ��readLine()��������ɼ���¼���һ�����ݵĶ�ȡ�أ�

readLine���������ַ���BufferedReader���еķ�����

������¼���read�������ֽ���InputStream�ķ�����

��ô�ܲ��ܽ��ֽ���ת���ַ�������ʹ���ַ�����������readLine�ķ����أ�


*/
import java.io.*;
class  TransStreamDemo
{
	public static void main(String[] args) throws IOException
	{
		//��ȡ����¼�����
		InputStream is = System.in;

		//���ֽ�������ת���ַ�������ʹ��ת������InputStreamReader()
		InputStreamReader isr = new InputStreamReader(is);

		//Ϊ�����Ч�ʣ����ַ������л�����������Ч������ʹ��BufferReader()
		BufferedReader br = new BufferedReader(isr);

//		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));   //�ۺ����������͵õ���һ�䣬��һ���Ǽ�������Ĳ������Ժ󿪷���������

		String line = null;

		while((line=br.readLine())!=null)
		{
			if("over".equals(line))
				break;
			System.out.println(line.toString());
		}

		br.close();
	}
}
