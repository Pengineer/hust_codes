import java.io.*;

class FileWriterContinueDemo 
{
	public static void main(String[] args) 
	{
		FileWriter fw = null;   
		try
		{
			fw = new FileWriter("Demo2.txt",true);  //传递一个true参数，表示不覆盖原来已有文件，并在已有文件处的末尾进行续写。
			fw.write("\r\nhubeisheng\r\nxiaotaoshi");
		}
		catch (IOException e)  
		{
			System.out.println("抓到new or write异常了："+e.toString());
		}
		finally   
		{
			try
			{
				if(fw!=null)    
					fw.close();
			}
			catch (IOException e)
			{
				System.out.println("抓到close异常了："+e.toString());
			}
		}
	}
}
