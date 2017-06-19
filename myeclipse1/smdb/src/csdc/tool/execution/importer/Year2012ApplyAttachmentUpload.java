package csdc.tool.execution.importer;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.GeneralApplication;
import csdc.bean.ProjectApplication;
import csdc.tool.execution.finder.GeneralProjectFinder;
import csdc.tool.execution.finder.InstpProjectFinder;
import csdc.tool.reader.ExcelReader;

/**
 * 上传2012年申报书文件
 * @author pengliang
 *
 */

public class Year2012ApplyAttachmentUpload extends Importer {
	
	ExcelReader reader1;
	
	List<ProjectApplication> projectList;
	
	@Autowired
	GeneralProjectFinder generalProjectFinder;
	
	@Autowired
	InstpProjectFinder instpProjectFinder;
	

	@Override
	protected void work() throws Throwable {
//		FillFileFieldByExcel();//2012年度所有项目的file字段均为空
//		UploadGeneralProjectApply();
		
		UploadInstpProjectApply();
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
			System.out.println(reader1.getCurrentRowIndex() + "/" + total + ":" + G);
			if(Q.equals("常林Chang Lin")) {
				Q = "常林";
			} else if(Q.equals("顾力行(S.J.Kulich)")) {
				Q = "顾力行";
			} else if(Q.equals("Qilian Chen 陈其莲")) {
				Q = "陈其莲";
			} else if(Q.equals("钱健（笔名羽离子)")) {
				Q = "钱健";
			} else if(Q.equals("张连顺（顺真）")) {
				Q = "张连顺";
			} else if(Q.equals("张平（张恨无）")) {
				Q = "张平";
			} else if(Q.equals("王中平（曾用名：王忠萍）")) {
				Q = "王中平";
			} else if(Q.equals("WEIMIN TANG（唐蔚明）")) {
				Q = "唐蔚明";
			}
			GeneralApplication generalApplication = generalProjectFinder.findApplication(G, Q, 2012);
			generalApplication.setFile(D);
			dao.modify(generalApplication);
		}
		System.out.println("over");
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
			smdbFileName = "general_app_2012_" + application.getUniversity().getCode() + "_" + sdf.format(new Date(srcFile.lastModified())) + ".doc";
			destFile = new File(smdbGeneralRealPath, smdbFileName);
			for(int i=1; destFile.exists(); i++) {
				smdbFileName = "general_app_2012_" + application.getUniversity().getCode() + "_" + sdf.format(new Date(srcFile.lastModified() + i)) + ".doc";
				destFile = new File(smdbGeneralRealPath, smdbFileName);
			}
			
			try {
				FileUtils.copyFile(srcFile, destFile);
//				FileUtils.forceDelete(srcFile);
			} catch (Exception e) {
				msg.add(application.getName() + ":" + application.getFile());
				if(destFile.exists()) {		//删除拷贝不完整的文件
					destFile.delete();
				}
				continue;
			}
			application.setFile("upload/project/general/app/2012/" + smdbFileName);
			dao.modify(application);
		}
		
		if(msg.size()>0) {
			System.out.println(msg.toString().replaceAll(",\\s+", "\n"));
		}
	}
	

	/**
	 * 重命名基地项目申请书文件名
	 * @throws Exception
	 */
	public void UploadInstpProjectApply() throws Exception {
		initInstpProject();
		int size = projectList.size();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		int current=0;
		Set<String> msg = new HashSet<String>();
		ArrayList<File> files = new ArrayList<File>();
		
		File srcFile =null;//原始文件
		File destFile =null;//重命名后的文件
		
		String smdbFileName = "";//重命名后的文件名
		String smdbInstpRealPath = "D:\\instp\\";//重命名后的文件路径
		String sinossInstpRealPath = "D:\\base\\2012\\报送材料-按高校\\";//源文件路径
		
		for(ProjectApplication application : projectList) {
			System.out.println((++current) + "/" + size);
			File dir = new File(sinossInstpRealPath + application.getAgencyName());
			files = getAllFiles(dir, files);
			for (File file : files) {
				if (fileNameContainsApplicant(file.getName(), application.getApplicantName())) {
					srcFile = file;
					break;
				}
			}
			if(srcFile == null) { //项目申请书不存在
				msg.add(application.getName());
				continue;
			}
			
			smdbFileName = "instp_app_2012_" + application.getUniversity().getCode() + "_" + sdf.format(new Date(srcFile.lastModified())) + ".doc";
			destFile = new File(smdbInstpRealPath, smdbFileName);
			for(int i=1; destFile.exists(); i++) {
				smdbFileName = "instp_app_2012_" + application.getUniversity().getCode() + "_" + sdf.format(new Date(srcFile.lastModified() + i)) + ".doc";
				destFile = new File(smdbInstpRealPath, smdbFileName);
			}
			
			try {
				FileUtils.copyFile(srcFile, destFile);
//				FileUtils.forceDelete(srcFile);
			} catch (Exception e) {
				msg.add(application.getName());
				if(destFile.exists()) {		//删除拷贝不完整的文件
					destFile.delete();
				}
				continue;
			}
			application.setFile("upload/project/instp/app/2012/" + smdbFileName);
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
	 * 获取指定路径下面的所有文件，包括子文件夹下的文件
	 * @param dir
	 * @param list
	 * @return
	 */
	public ArrayList<File> getAllFiles(File dir, ArrayList<File> list) {
		File[] files = dir.listFiles();
		for (File file : files) {
			if (file.isDirectory())
				getAllFiles(file, list);
			else
				list.add(file);
		}
		
		return list;
	}
	
	/**
	 * 初始化2012年需处理而还未处理的一般项目申报数据
	 */
	@SuppressWarnings("unchecked")
	public void initGeneralProject() {
		Long begin = System.currentTimeMillis();
		projectList = dao.query("select app from ProjectApplication app where app.type='general' and app.year=2012 and app.file is not null and instr(app.file,'_app_2012_')=0");
		System.out.println("init GeneralProject complete! use time " + (System.currentTimeMillis() - begin) + "ms");
	}
	
	/**
	 * 初始化2012年需处理而还未处理的基地项目申报数据
	 */
	@SuppressWarnings("unchecked")
	public void initInstpProject() {
		Long begin = System.currentTimeMillis();
		projectList = dao.query("select app from ProjectApplication app where app.type='instp' and app.year=2012 and app.file is null");
		System.out.println("init InstpProject complete! use time " + (System.currentTimeMillis() - begin) + "ms");
	}
	
	Year2012ApplyAttachmentUpload(String fileName1) {
		reader1 = new ExcelReader(fileName1);
	}
	
	Year2012ApplyAttachmentUpload(){}
	
	public static void main(String[] args) {
//		ArrayList list = new ArrayList();
//		list = showDir(new File("D:\\base\\2012\\报送材料-按高校"), list);
//		System.out.println(list.toString().replaceAll(", ", "\r"));
		
		File file = new File("D:\\base\\2012\\报送材料-按高校\\中国人民大学\\项目申请-26份\\中国特色社会主义理论研究中心-黄继峰.doc");
		System.out.println(file.getAbsoluteFile());
	}
	
//	public static ArrayList showDir(File dir, ArrayList list) {
//		File[] files = dir.listFiles();
//		for (File file : files) {
//			if (file.isDirectory())
//				showDir(file, list);
//			else
//				list.add(file);
//		}
//		
//		return list;
//	}
}
