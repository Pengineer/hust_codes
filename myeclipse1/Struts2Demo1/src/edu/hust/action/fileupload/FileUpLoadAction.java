package edu.hust.action.fileupload;

import java.io.File;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

public class FileUpLoadAction {
	private File image;  //image变量名一定要和JSP的form中的name值一致（JSP匹配到Action后会在底层调用本Action的setter给具有相同属性名的属性注入值）
	private String imageFileName;  //Struts2提供了获取上传文件文件名的特殊方式：只需要我们定义变量的时候遵循一定的写法即可，获取文件名的变量只能是：文件FileName（imageFileName），还要提供相应的setter方法
	
	public File getImage() {
		return image;
	}

	public void setImage(File image) {
		this.image = image;
	}

	public String getImageFileName() {
		return imageFileName;
	}

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}

	public String execute() throws Exception{
		if(image!=null){
			String saveRealPath = ServletActionContext.getServletContext().getRealPath("/images");//通过相对路径来获取文件实际保存路径
			File saverealpathf = new File(saveRealPath);//将文件保存路径封装成File对象（因为要调用File的判断方法）
			if(!saverealpathf.exists()){
				saverealpathf.mkdirs();
			}
			System.out.println(saveRealPath);
		
			File savefile = new File(saveRealPath,imageFileName);
			if(savefile.exists()){
				ServletActionContext.getRequest().setAttribute("info", "上传文件已存在");
				return "message";
			}
			System.out.println(savefile);
			FileUtils.copyFile(image, savefile);
			ServletActionContext.getRequest().setAttribute("info", "上传成功");
			return "message";
		}
		ServletActionContext.getRequest().setAttribute("info", "请选择上传文件");
		return "message";
	}
}
