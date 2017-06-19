package edu.hust.action.fileupload;

import java.io.File;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

public class FileUpLoadAction {
	private File image;  //image������һ��Ҫ��JSP��form�е�nameֵһ�£�JSPƥ�䵽Action����ڵײ���ñ�Action��setter��������ͬ������������ע��ֵ��
	private String imageFileName;  //Struts2�ṩ�˻�ȡ�ϴ��ļ��ļ��������ⷽʽ��ֻ��Ҫ���Ƕ��������ʱ����ѭһ����д�����ɣ���ȡ�ļ����ı���ֻ���ǣ��ļ�FileName��imageFileName������Ҫ�ṩ��Ӧ��setter����
	
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
			String saveRealPath = ServletActionContext.getServletContext().getRealPath("/images");//ͨ�����·������ȡ�ļ�ʵ�ʱ���·��
			File saverealpathf = new File(saveRealPath);//���ļ�����·����װ��File������ΪҪ����File���жϷ�����
			if(!saverealpathf.exists()){
				saverealpathf.mkdirs();
			}
			System.out.println(saveRealPath);
		
			File savefile = new File(saveRealPath,imageFileName);
			if(savefile.exists()){
				ServletActionContext.getRequest().setAttribute("info", "�ϴ��ļ��Ѵ���");
				return "message";
			}
			System.out.println(savefile);
			FileUtils.copyFile(image, savefile);
			ServletActionContext.getRequest().setAttribute("info", "�ϴ��ɹ�");
			return "message";
		}
		ServletActionContext.getRequest().setAttribute("info", "��ѡ���ϴ��ļ�");
		return "message";
	}
}
