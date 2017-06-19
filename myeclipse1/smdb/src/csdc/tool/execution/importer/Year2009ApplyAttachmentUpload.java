package csdc.tool.execution.importer;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import csdc.bean.ProjectApplication;
import csdc.tool.execution.finder.GeneralProjectFinder;
import csdc.tool.execution.finder.InstpProjectFinder;

/**
 * 上传2009年申报书文件
 * @author pengliang
 *
 */
@Component
@Scope("prototype")
public class Year2009ApplyAttachmentUpload extends Importer {
	
	List<ProjectApplication> projectList;
	
	@Autowired
	GeneralProjectFinder generalProjectFinder;
	
	@Autowired
	InstpProjectFinder instpProjectFinder;
	

	@Override
	protected void work() throws Throwable {
		UploadGeneralProjectApply();
	}
	
	/**
	 * 重命名一般项目申请书文件名
	 * @throws Exception
	 */
	public void UploadGeneralProjectApply() throws Exception {
		initGeneralProject();
		int size = projectList.size();
		int current=0;
		Set<String> msg = new HashSet<String>();
		
		File srcFile =null;
		File destFile =null;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String smdbFileName = "";
		String smdbGeneralRealPath = "D:\\general";
		String sinossGenerRealPath = "D:\\gener";
		
		for(ProjectApplication application : projectList) {
			System.out.println((++current) + "/" + size);
			srcFile = new File(sinossGenerRealPath, application.getFile());
			smdbFileName = "general_app_2009_" + application.getUniversity().getCode() + "_" + sdf.format(new Date(srcFile.lastModified())) + ".doc";
			destFile = new File(smdbGeneralRealPath, smdbFileName);
			for(int i=1; destFile.exists(); i++) {
				smdbFileName = "general_app_2009_" + application.getUniversity().getCode() + "_" + sdf.format(new Date(srcFile.lastModified() + i)) + ".doc";
				destFile = new File(smdbGeneralRealPath, smdbFileName);
			}
			
			try {
				FileUtils.copyFile(srcFile, destFile);
				FileUtils.forceDelete(srcFile);
			} catch (Exception e) {
				msg.add(application.getName() + ":" + application.getFile());
				if(destFile.exists()) {		//删除拷贝不完整的文件
					destFile.delete();
				}
				continue;
			}
			application.setFile("upload/project/general/app/2009/" + smdbFileName);
			dao.modify(application);
		}
		
		if(msg.size()>0) {
			System.out.println(msg.toString().replaceAll(",\\s+", "\n"));
		}
	}
	
	/**
	 * 判断文件名中是否包含某一申请人姓名
	 * @param fileName
	 * @param applicantName
	 * @return
	 */
	public boolean fileNameContainsApplicant(String fileName, String applicantName) {
		if (applicantName.contains("; ")) {
			String[] names = applicantName.split("; ");
			for (String name : names) {
				if (fileName.contains(name)) {
					return true;
				}
			}
			return false;
		} else {
			return fileName.contains(applicantName);
		}
	}
	
	/**
	 * 初始化2009年需处理而还未处理的一般项目申报数据
	 */
	@SuppressWarnings("unchecked")
	public void initGeneralProject() {
		Long begin = System.currentTimeMillis();
		projectList = dao.query("select app from ProjectApplication app where app.type='general' and app.year=2009 and app.file is not null and instr(app.file,'_app_2009_')=0");
		System.out.println("init GeneralProject complete! use time " + (System.currentTimeMillis() - begin) + "ms");
	}
	
	Year2009ApplyAttachmentUpload(){}

}
