import java.io.*;

class TransStreamDemo1 
{
	public static void main(String[] args) throws IOException
	{
		OutputStream os = System.out;   //д��

		OutputStreamWriter osw = new OutputStreamWriter(os);   //�ַ���ת�ֽ���

		BufferedWriter bufw = new BufferedWriter(osw);

		bufw.write("liangjian".toUpperCase());  //д������Ļ��

		bufw.flush();

		bufw.close();		
	}
}
