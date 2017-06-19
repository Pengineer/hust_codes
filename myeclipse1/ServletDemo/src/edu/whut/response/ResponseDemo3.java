package edu.whut.response;

//传送下载服务器上的资源图片

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ResponseDemo3 extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		test2(response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}
	
	//图片名称是非中文
	public void test1(HttpServletResponse response){
		
		String path = this.getServletContext().getRealPath("/download/1.jpg");
		String filename = path.substring(path.lastIndexOf("\\")+1);
		
		response.setHeader("content-disposition", "attachment;filename="+filename);
		
		OutputStream out = null;
		InputStream in = null;
		try{
			in = new FileInputStream(path);
			out = response.getOutputStream();
			byte[] buf = new byte[1024];
			int len = 0;
			while((len = in.read(buf))!=-1){
				out.write(buf,0,len);
			}
		}catch(Exception e){
			throw new RuntimeException(e);
		}finally{
			if(out!=null)
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if(in!=null)
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}		
	}
	
	//图片名称是中文，使用URLEncoder的encode方法将图片名称（路径）进行编码
	public void test2(HttpServletResponse response) throws IOException{
		
		String path = this.getServletContext().getRealPath("/download/小清新.jpg");
		String filename = path.substring(path.lastIndexOf("\\")+1);
		
		response.setHeader("content-disposition", "attachment;filename="+URLEncoder.encode(filename,"UTF-8"));
		
		OutputStream out = null;
		InputStream in = null;
		try{
			in = new FileInputStream(path);
			out = response.getOutputStream();
			byte[] buf = new byte[1024];
			int len = 0;
			while((len = in.read(buf))!=-1){
				out.write(buf,0,len);
			}
		}catch(Exception e){
			throw new RuntimeException(e);
		}finally{
			if(out!=null)
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if(in!=null)
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}		
	}

}
