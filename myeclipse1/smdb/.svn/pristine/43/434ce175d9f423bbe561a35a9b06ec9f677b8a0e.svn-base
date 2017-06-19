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

import csdc.bean.GeneralApplication;
import csdc.bean.ProjectApplication;
import csdc.tool.execution.finder.GeneralProjectFinder;
import csdc.tool.execution.finder.InstpProjectFinder;
import csdc.tool.reader.ExcelReader;

/**
 * 上传2010年申报书文件
 * @author pengliang
 *
 */
@Component
@Scope("prototype")
public class Year2010ApplyAttachmentUpload extends Importer {
	
	ExcelReader reader1 = new ExcelReader("E:\\work\\2.sks\\1.smdb\\0.参考资料\\数据资料\\社科项目\\一般项目\\2010\\2010年一般项目申报数据-初审合格23196项_修正导入.xls");
	
	List<ProjectApplication> projectList;
	
	@Autowired
	GeneralProjectFinder generalProjectFinder;
	
	@Autowired
	InstpProjectFinder instpProjectFinder;
	

	@Override
	protected void work() throws Throwable {
//		FillFileFieldByExcel();//2010年度所有项目的file字段均为空
		UploadGeneralProjectApply();
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
			if(Q.equals("踪训国（踪凡）")) {
				Q = "踪训国";
			} else if(Q.equals("吴毅笔名南野")) {
				Q = "吴毅";
			} else if(Q.equals("熊贤汉<笔名熊飞>")) {
				Q = "熊贤汉";
			} else if(Q.equals("马永生（马知遥）")) {
				Q = "马永生";
			} else if(Q.equals("张哲(曾用名张银犬)")) {
				Q = "张哲";
			} else if(Q.equals("郝兴义（老戥）")) {
				Q = "郝兴义";
			} else if(Q.equals("万润保（晴川）")) {
				Q = "万润保";
			} else if(Q.equals("TianliLiu")) {
				Q = "刘天俐";
			} else if(Q.equals("袁承蔚（原名袁楠）")) {
				Q = "袁承蔚";
			} else if(G.equals("实施藏汉英三种教育促进跨文化理解与中华民族认同研究——以四川藏区为例")) {
				G = "实施藏汉英三语教育促进跨文化理解与中华民族认同研究——以四川藏区为例";
			}
			GeneralApplication generalApplication = generalProjectFinder.findApplication(G, Q, 2010);
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
			smdbFileName = "general_app_2010_" + application.getUniversity().getCode() + "_" + sdf.format(new Date(srcFile.lastModified())) + ".doc";
			destFile = new File(smdbGeneralRealPath, smdbFileName);
			for(int i=1; destFile.exists(); i++) {
				smdbFileName = "general_app_2010_" + application.getUniversity().getCode() + "_" + sdf.format(new Date(srcFile.lastModified() + i)) + ".doc";
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
			application.setFile("upload/project/general/app/2010/" + smdbFileName);
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
	 * 初始化2010年需处理而还未处理的一般项目申报数据
	 */
	@SuppressWarnings("unchecked")
	public void initGeneralProject() {
		Long begin = System.currentTimeMillis();
		projectList = dao.query("select app from ProjectApplication app where app.type='general' and app.year=2010 and app.file is not null and instr(app.file,'_app_2010_')=0");
		System.out.println("init GeneralProject complete! use time " + (System.currentTimeMillis() - begin) + "ms");
	}
	
	Year2010ApplyAttachmentUpload(){}

}
