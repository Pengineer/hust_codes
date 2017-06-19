package csdc.tool.execution.importer;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import csdc.bean.ProjectApplication;
import csdc.tool.execution.finder.GeneralProjectFinder;
import csdc.tool.execution.finder.InstpProjectFinder;

@Component
public class Year2011ApplyAttachmentUpload extends Importer{

	List<ProjectApplication> projectList;
	
	@Autowired
	GeneralProjectFinder generalProjectFinder;
	
	@Autowired
	InstpProjectFinder instpProjectFinder;
	
	@Override
	protected void work() throws Throwable {
		UploadGeneralProjectApply();
	}

	public void UploadGeneralProjectApply() {
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
			smdbFileName = "general_app_2011_" + application.getUniversity().getCode() + "_" + sdf.format(new Date(srcFile.lastModified())) + ".doc";
			destFile = new File(smdbGeneralRealPath, smdbFileName);
			for(int i=1; destFile.exists(); i++) {
				smdbFileName = "general_app_2011_" + application.getUniversity().getCode() + "_" + sdf.format(new Date(srcFile.lastModified() + i)) + ".doc";
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
			application.setFile("upload/project/general/app/2011/" + smdbFileName);
			dao.modify(application);
		}
		
		if(msg.size()>0) {
			System.out.println(msg.toString().replaceAll(",\\s+", "\n"));
		}
	}

	public void initGeneralProject() {
		Long begin = System.currentTimeMillis();
		projectList = dao.query("select app from ProjectApplication app where app.type='general' and app.year=2011 and app.file is not null and instr(app.file,'_app_2011_')=0");
		System.out.println("init GeneralProject complete! use time " + (System.currentTimeMillis() - begin) + "ms");
	}

}
