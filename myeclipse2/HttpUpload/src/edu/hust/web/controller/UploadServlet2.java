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

/*实际的文件上传比UploadServlet1要复杂的多，比如添加监听器，设置缓冲区大小，指定临时文件目录，如何防止直接访问，如何防止所有放在一个目录下（算法分散），如何防止覆盖，如何防止中文名乱码等等
 * 
 * 为了保证上传之后的文件不能直接访问（防止上传恶意文件后直接访问该恶意代码，恶意代码可以使用Runtime对象，Runtime甚至可以使用格式化磁盘命令），
 * 我们可以将保存的路径文件夹放在WEB-INF文件夹下，WEB-INF目录下的文件是受保护不能被外界直接访问的。
 */

public class UploadServlet2 extends HttpServlet {

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
				//4.2.1，得到的是普通入项
				if(item.isFormField()){
					String name = item.getFieldName();
					String value = item.getString();
					System.out.println(name+"="+value);
				}else{
				//4.2.2，得到的是上传输入项（form表单里面type=file）
					String filename = item.getName();  //得到上传文件完整路径名（不同浏览器不一样，故有下面一句处理）
					filename = filename.substring((filename.lastIndexOf("\\")+1));
					
					String savepath = this.getServletContext().getRealPath("/WEB-INF/upload");//上传文件在服务器中的保存路径
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
