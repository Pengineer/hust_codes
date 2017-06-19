import java.io.*;

class TransStreamDemo1 
{
	public static void main(String[] args) throws IOException
	{
		OutputStream os = System.out;   //写出

		OutputStreamWriter osw = new OutputStreamWriter(os);   //字符流转字节流

		BufferedWriter bufw = new BufferedWriter(osw);

		bufw.write("liangjian".toUpperCase());  //写出到屏幕上

		bufw.flush();

		bufw.close();		
	}
}
