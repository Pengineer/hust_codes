package csdc.tool.execution.importer;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.GeneralApplication;
import csdc.bean.InstpApplication;
import csdc.bean.ProjectApplication;
import csdc.tool.ApplicationContainer;
import csdc.tool.execution.finder.GeneralProjectFinder;
import csdc.tool.execution.finder.InstpProjectFinder;
import csdc.tool.reader.ExcelReader;

/**
 * 上传2013年申请书文件(此代码不提交SVN)
 * @author pengliang
 *
 * 备注：1，有40个项目填写了申请书，但是没有找对对应的文件
 *     2，部分学校的基地项目离线上报的，需单独处理
 */

public class Year2013ApplyAttachmentUpload extends Importer {
	
	ExcelReader reader1;
	
	ExcelReader reader2;
	
	List<ProjectApplication> projectList;
	
	@Autowired
	GeneralProjectFinder generalProjectFinder;
	
	@Autowired
	InstpProjectFinder instpProjectFinder;
	

	@Override
	protected void work() throws Throwable {
//		FillFileFieldByExcel();//2013年度所有项目的file字段均为空 
//		DivideGeneralAndInstpApply(); 
		UploadGeneralAndInstpProjectApply();
	}
	

	
	/**
	 * 根excel表按照原始格式设置c_file字段
	 * @throws Exception
	 */
	public void FillFileFieldByExcel() throws Exception{
		reader1.readSheet(0);
		int total = reader1.getRowNumber();
		generalProjectFinder.reset();
		while(next(reader1)){
			System.out.println(reader1.getCurrentRowIndex() + "/" + total + ":" + F);
			if("张明浩（曾用名：张明皓）".equals(O)) {
				O = "张明浩";
			} else if("陈红梅（陈一梅）".equals(O)){
				O = "陈红梅";
			} else if("王桂彩（思竹）".equals(O)){
				O = "王桂彩";
			} else if("吴头文（吴投文）".equals(O)){
				O = "吴投文";
			} else if("杨再伟（第一）   肖波（第二）".equals(O)){
				O = "杨再伟; 肖波";
			}
			GeneralApplication generalApplication = generalProjectFinder.findApplication(F, O, 2013);
			generalApplication.setFile(C);
			dao.modify(generalApplication);
		}
		
		reader2.readSheet(0);
		total = reader2.getRowNumber();
		while(next(reader2)){
			if(C == null || "".equals(C)){
				return;
			}
			System.out.println(reader2.getCurrentRowIndex() + "/" + total + ":" + G);
			if("跨境跨界民族与边疆社会建设和公共事务治理研究".equals(G)){
				G = "跨境民族与边疆公共事务治理";
			} 
			if(Q.trim().contains(" ")){
				Q = Q.substring(0, Q.indexOf(" "));
			}
			InstpApplication instpApplication = instpProjectFinder.findApplication(G, Q, 2013);
			instpApplication.setFile(C);
			dao.modify(instpApplication);
		}
	}
	
	/**
	 * 在本地将基地文件和一般项目文件从同一目录中分开，上传服务器
	 * @throws Exception 
	 */
	public void DivideGeneralAndInstpApply() {
		initProject();
		int size = projectList.size();
		int current=0;
		Set<String> msg = new HashSet<String>();
		
		File srcFile =null;
		File destFile =null;
		
//		String sinossTogetherFilePath = "upload/sinoss/gener_base/";
//		String sinossTogetherRealPath = ApplicationContainer.sc.getRealPath(sinossTogetherFilePath);
		String sinossTogetherRealPath = "D:\\gener_base";
		
//		String sinossGenerFilePath = "upload/sinoss/gener/app/2013/";
//		String sinossGenerRealPath = ApplicationContainer.sc.getRealPath(sinossGenerFilePath);
		String sinossGenerRealPath = "D:\\gener";
		
//		String sinossBaseFilePath = "upload/sinoss/base/app/2013/";
//		String sinossBaseRealPath = ApplicationContainer.sc.getRealPath(sinossBaseFilePath);
		String sinossBaseRealPath = "D:\\base";
		
		for(ProjectApplication application : projectList) {
			System.out.println((++current) + "/" + size);
			srcFile = new File(sinossTogetherRealPath, application.getFile());
			if(application.getType().equals("general")) {
				destFile = new File(sinossGenerRealPath, application.getFile());
			} else {
				destFile = new File(sinossBaseRealPath, application.getFile());
			}
			
			try {
				FileUtils.copyFile(srcFile, destFile);
				FileUtils.forceDelete(srcFile);
			} catch (Exception e) {
				msg.add(application.getName());
				if(destFile.exists()) {		//删除拷贝不完整的文件
					destFile.delete();
				}
				continue;
			}
		}
		
		if(msg.size()>0) {
			System.out.println(msg.toString().replaceAll(",\\s+", "\n"));
		}
	}
	
	public void UploadGeneralAndInstpProjectApply() throws Exception {
		initProject();
		int size = projectList.size();
		int current=0;
		Set<String> msg = new HashSet<String>();
		
		File srcFile =null;
		File destFile =null;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		
		String smdbFileName = "";
		String smdbFilePath = "";
		
//		String smdbGeneralFilePath = "upload/project/general/app/2013/";
//		String smdbInstpFilePath = "upload/project/instp/app/2013/";
//		String smdbGeneralRealPath = ApplicationContainer.sc.getRealPath(smdbGeneralFilePath);
//		String smdbInstpRealPath = ApplicationContainer.sc.getRealPath(smdbInstpFilePath);
		String smdbGeneralFilePath = "D:\\general";
		String smdbInstpFilePath = "D:\\instp";
		String smdbGeneralRealPath = "D:\\general";
		String smdbInstpRealPath = "D:\\instp";
		
//		String sinossGenerFilePath = "upload/sinoss/gener/app/2013/";
//		String sinossInstpFilePath = "upload/sinoss/base/app/2013/";
//		String sinossGenerRealPath = ApplicationContainer.sc.getRealPath(sinossGenerFilePath);
//		String sinossInstpRealPath = ApplicationContainer.sc.getRealPath(sinossInstpFilePath);
		String sinossGenerRealPath = "D:\\gener";
		String sinossInstpRealPath = "D:\\base";
		
		
		
		for(ProjectApplication application : projectList) {
			System.out.println((++current) + "/" + size);
			if(application.getType().equals("general")) {
				smdbFilePath = smdbGeneralFilePath;
				srcFile = new File(sinossGenerRealPath, application.getFile());
				smdbFileName = "general_app_2013_" + application.getUniversity().getCode() + "_" + sdf.format(new Date(srcFile.lastModified())) + ".doc";
				destFile = new File(smdbGeneralRealPath, smdbFileName);
				for(int i=1; destFile.exists(); i++) {
					smdbFileName = "general_app_2013_" + application.getUniversity().getCode() + "_" + sdf.format(new Date(srcFile.lastModified() + i)) + ".doc";
					destFile = new File(smdbGeneralRealPath, smdbFileName);
				}
			} else {
				smdbFilePath = smdbInstpFilePath;
				srcFile = new File(sinossInstpRealPath, application.getFile());
				smdbFileName = "instp_app_2013_" + application.getUniversity().getCode() + "_" + sdf.format(new Date(srcFile.lastModified())) + ".doc";
				destFile = new File(smdbInstpRealPath, smdbFileName);
				for(int i=1; destFile.exists(); i++) {
					smdbFileName = "instp_app_2013_" + application.getUniversity().getCode() + "_" + sdf.format(new Date(srcFile.lastModified() + i)) + ".doc";
					destFile = new File(smdbInstpRealPath, smdbFileName);
				}
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
			application.setFile(smdbFilePath + smdbFileName);
			dao.modify(application);
		}
		
		if(msg.size()>0) {
			System.out.println(msg.toString().replaceAll(",\\s+", "\n"));
		}
	}
	
	/**
	 * 初始化2013年需处理而还未处理的一般项目申请数据
	 */
	@SuppressWarnings("unchecked")
	public void initProject() {
		Long begin = System.currentTimeMillis();
		projectList = dao.query("select app from ProjectApplication app where (app.type='general' or app.type='instp') and app.year=2013 and app.file is not null and instr(app.file,'_app_2013_')=0");
		System.out.println("init Project complete! use time " + (System.currentTimeMillis() - begin) + "ms");
	}
	
	public Year2013ApplyAttachmentUpload(String fileName1, String fileName2) {
		reader1 = new ExcelReader(fileName1);
		reader2 = new ExcelReader(fileName2);
	}
	
	public Year2013ApplyAttachmentUpload(){}
}
