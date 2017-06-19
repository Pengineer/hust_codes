package edu.hust.action.fileupload;

import java.io.File;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

public class FilesUpLoadAction {
	private File[] image;
	private String[] imageFileName;  //Struts2�ṩ�˻�ȡ�ϴ��ļ��ļ��������ⷽʽ��ֻ��Ҫ���Ƕ��������ʱ����ѭһ����д�����ɣ���ȡ�ļ����ı���ֻ���ǣ��ļ�FileName��imageFileName������Ҫ�ṩ��Ӧ��setter����
	
	

	public File[] getImage() {
		return image;
	}

	public void setImage(File[] image) {
		this.image = image;
	}

	public String[] getImageFileName() {
		return imageFileName;
	}

	public void setImageFileName(String[] imageFileName) {
		this.imageFileName = imageFileName;
	}

	public String execute() throws Exception{
		if(image!=null){
			String saveRealPath = ServletActionContext.getServletContext().getRealPath("/images");//ͨ�����·������ȡ�ļ�ʵ�ʱ���·��
			File saverealpathf = new File(saveRealPath);//���ļ�����·����װ��File������ΪҪ����File���жϷ�����
			if(!saverealpathf.exists()){
				saverealpathf.mkdirs();
			}

			for(int i=0; i<image.length; i++){
				File savefile = new File(saveRealPath,imageFileName[i]);
				if(savefile.exists()){
					ServletActionContext.getRequest().setAttribute("info", "�ϴ��ļ��Ѵ���");
					return "message";
				}
				FileUtils.copyFile(image[i], savefile);
				ServletActionContext.getRequest().setAttribute("info", "�ϴ��ɹ�");				
			}	
			return "message";
		}
		
		ServletActionContext.getRequest().setAttribute("info", "��ѡ���ϴ��ļ�");
		return "message";
	}
}
