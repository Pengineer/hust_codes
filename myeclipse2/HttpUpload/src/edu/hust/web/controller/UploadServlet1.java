package edu.hust.web.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/*function ：使用apache提供的文件上传jar包来实现文件的上传
 * 
 * DiskFileItemFactory 是创建 FileItem 对象的工厂，这个工厂类常用方法：
   1，public void setSizeThreshold(int sizeThreshold) 
	   设置内存缓冲区的大小，默认值为10K。当上传文件大于缓冲区大小时， fileupload组件将使用临时文件缓存上传文件。
   2，public void setRepository(java.io.File repository) 
	    指定临时文件目录，默认值为System.getProperty("java.io.tmpdir").
   3，public DiskFileItemFactory(int sizeThreshold, java.io.File repository) 
	    构造函数。
   
   ServletFileUpload 负责处理上传的文件数据，并将表单中每个输入项封装成一个 FileItem 对象中。常用方法有：
   1，boolean isMultipartContent(HttpServletRequest request) 
                判断上传表单是否为multipart/form-data类型
   2，List parseRequest(HttpServletRequest request)
                解析request对象，并把表单中的每一个输入项包装成一个FileItem 对象，并返回一个保存了所有FileItem的list集合。 
   3，setFileSizeMax(long fileSizeMax) 
                设置上传文件的最大值
   4，setSizeMax(long sizeMax) 
                设置上传文件总量的最大值
   5，setHeaderEncoding(java.lang.String encoding) 
                设置编码格式
   6，setProgressListener(ProgressListener pListener)
                 设置监听器，文件下载进度条就是监听器做的 
 */

public class UploadServlet1 extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try{
			//1，得到解析器工厂（接下来设置缓冲区大小和临时路径，这里为了简单就都默认了）
			DiskFileItemFactory factory = new DiskFileItemFactory();
			
			//2，得到解析器
			ServletFileUpload upload = new ServletFileUpload(factory);
			
			//3，判断上传数据的类型
			boolean contentType = upload.isMultipartContent(request);
			
			//4.1，如果不是multipart(没有enctype属性),是普通form表单信息,按照传统方式获取表单
			if(!contentType){
				return;
			}
			//4.2，如果是multipart表单，则调用解析器解析上传数据
			List<FileItem> list = upload.parseRequest(request);
			for(FileItem item : list){	
				//4.2.1，得到的是普通输入项
				if(item.isFormField()){
					String name = item.getFieldName();
					String value = item.getString();
					System.out.println(name+"="+value);
				}else{
				//4.2.2，得到的是上传输入项（form表单里面type=file）
					String filename = item.getName();  //得到上传文件完整路径名（不同浏览器不一样，故有下面一句处理）
					filename = filename.substring((filename.lastIndexOf("\\")+1));
					
					String savepath = this.getServletContext().getRealPath("/upload");//上传文件在服务器中的保存路径
					InputStream in = item.getInputStream();
					FileOutputStream out = new FileOutputStream(savepath+ "\\" + filename);
					byte[] buf = new byte[1024];
					int len = 0;
					while((len=in.read(buf))!=-1){
						out.write(buf, 0, len);
					}
					in.close();
					out.close();
				}				
			}
			
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().print("上传成功");
			
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
