/*

文件操作有标准化的异常处理代码格式

*/

import java.io.*;

class FileWriterExceptionDemo 
{
	public static void main(String[] args) 
	{
		FileWriter fw = null;   //因为try catch finally是三段独立的代码，因此文件引用的级去哪里必须在外面，如果在try里面声明的，那么finally中就不能用。
		try
		{
			fw = new FileWriter("Demo2.txt");
			fw.write("liangjian1");
		}
		catch (IOException e)  //新建文件或写异常
		{
			System.out.println("抓到new or write异常了："+e.toString());
		}
		finally   //关闭流是必须要执行的动作，因此必须放在finally中，否则，如果放在try中，当发生new异常时，就不恩呢执行流资源的关闭了
		{
			try
			{
				if(fw!=null)     //当文件新建异常时，fw就没有实例对象，就不能执行关闭动作，因此要先判断一下
					fw.close();
			}
			catch (IOException e)
			{
				System.out.println("抓到close异常了："+e.toString());
			}
		}
	}
}
