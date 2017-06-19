package csdc.service.imp;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;

import csdc.service.IMailService;
import csdc.tool.ApplicationContainer;
import csdc.tool.FileTool;
import csdc.tool.SignID;

public class MailService extends BaseService implements IMailService{

	public String renameFile(String id, File file) {
			String fileName = file.getName();
			String extendName = fileName.substring(fileName.lastIndexOf("."));
			String realPath = ApplicationContainer.sc.getRealPath("upload");
			String realName = "mail_"+id+"_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) +"_"+ SignID.getRandomString(4) +extendName;
			String filepath = "mail/"+new SimpleDateFormat("yyyyMM").format(new Date())+"/";
			realPath = realPath.replace('\\', '/');
			String path = realPath + "/" + filepath + realName;
		try {
			FileTool.mkdir_p(realPath + "/" + filepath);
			File x = new File(path);
			FileUtils.copyFile(file, x);
			filepath = "upload/" + filepath + realName;
			return filepath;
		} catch (IOException e) {
			List filenames = getFiles(filepath);
			for (int i = 0; i < filenames.size(); i++) {
				String name = (String) filenames.get(i);
				if (name.contains(id)) {
					File file2 = new File(filepath+ name);
					file2.delete();
				}
			}
			e.printStackTrace();
			return null;
		}
	}
	
	public List getFiles(String path) {
		File dir = new File(path);
		File[] files = dir.listFiles();
		List file_names = new ArrayList();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {// 判断是否是目录
				file_names.add(files[i].getName() + "=>它是一个文件夹");
			}
			if (files[i].isHidden()) {// 判断是否是隐藏文件
				file_names.add(files[i].getName() + "=>它是一个隐藏文");
			}
			if (files[i].isFile() && (!files[i].isHidden())) {// 判断是否是文件并不能是隐藏文件
				file_names.add(files[i].getName());
			}
		}
		return file_names;
	}

}
