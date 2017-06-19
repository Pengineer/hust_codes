package csdc.tool.execution.importer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import csdc.bean.GeneralApplication;
import csdc.bean.SpecialApplication;
import csdc.tool.ApplicationContainer;

/**
 * 1，按照统一的命名规则将中间表中的文件复制到正式库指定目录
 * 2，将复制后的文件名设置到对应项目的file字段
 * @author pengliang
 *
 */

@Component
public class AttachmentUpload extends Importer {
	
	int year = 2015;
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	
	List<GeneralApplication> generalProjectsList;
	
	List<SpecialApplication> specialProjectsList;

	@Override
	protected void work() throws Throwable {
		CopySpecialAttachment();
		CopyGeneralAttachment();
	}
	
	public void CopyGeneralAttachment() throws Exception {
		initGeneralProject();
		
		
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("/tmp/testLog")));
		
		String storageFileName = "";
		String smdbFileName = "";
		String extendName = "";
		
		String smdbFilePath = "upload/project/general/app/" + year + "/";
		String smdbRealPath = ApplicationContainer.sc.getRealPath(smdbFilePath);
		
		String sinossFilePath = "upload/sinoss/gener/app/" + year + "/";
		String sinossRealPath = ApplicationContainer.sc.getRealPath(sinossFilePath);
		
		//sinossFileName:龚郑勇(c364b0a0-3dc7-41e9-920f-11609d3f8a36).doc
		for(GeneralApplication generalApplication : generalProjectsList) {
			storageFileName = generalApplication.getFile();
			
			//本地测试.....
			storageFileName = storageFileName.replaceAll("\\W","");
			File dir = new File(sinossRealPath);
			File[] files = dir.listFiles();
			for(File file : files){
				if(file.getName().replaceAll("\\W","").contains(storageFileName)){
					storageFileName = file.getName();
					break;
				}
			}
			
			extendName = storageFileName.substring(storageFileName.lastIndexOf("."));
			smdbFileName = "general_app_2015_" + generalApplication.getUniversity().getCode() + "_" + sdf.format(new Date()) + extendName;
			File destFile = new File(smdbRealPath, smdbFileName);
			File srcFile = new File(sinossRealPath, storageFileName);
			if(destFile.exists()){
				throw new RuntimeException("程序跑的太快，创建了同名文件");
			} else {
				try {
					FileUtils.copyFile(srcFile, destFile);
					bw.write("srcFile:" + sinossRealPath + "/" + storageFileName);
					bw.newLine();
				} catch (IOException e) {
					bw.write(e.getMessage() + ":" + e.getStackTrace());
					bw.close();
					throw new RuntimeException("文件复制失败");
				}
			}
			
			generalApplication.setFile(smdbFilePath + smdbFileName);
			dao.modify(generalApplication);
		}
		bw.write("copy generalFiles over !");
		bw.close();
	}
	
	public void CopySpecialAttachment() {
		initSpecialProject();
//		int current=0;
//		int total=specialProjectsList.size();
		
		String storageFileName = "";
		String smdbFileName = "";
		String extendName = "";
		
		String smdbFilePath = "upload/project/special/app/" + year + "/";
		String smdbRealPath = ApplicationContainer.sc.getRealPath(smdbFilePath);
		
		String sinossFilePath = "upload/sinoss/special/app/" + year + "/";
		String sinossRealPath = ApplicationContainer.sc.getRealPath(sinossFilePath);
		
		//sinossFileName:龚郑勇(c364b0a0-3dc7-41e9-920f-11609d3f8a36).doc
		for(SpecialApplication specialApplication : specialProjectsList) {
//			System.out.println((++current) + "/" + total);
			storageFileName = specialApplication.getFile();
			
			//本地测试.....
			storageFileName = storageFileName.replaceAll("\\W","");
			File dir = new File(sinossRealPath);
			File[] files = dir.listFiles();
			for(File file : files){
				if(file.getName().replaceAll("\\W","").contains(storageFileName)){
					storageFileName = file.getName();
					break;
				}
			}
			
			extendName = storageFileName.substring(storageFileName.lastIndexOf("."));
			smdbFileName = "special_app_2015_" + specialApplication.getUniversity().getCode() + "_" + sdf.format(new Date()) + extendName;
			File destFile = new File(smdbRealPath, smdbFileName);
			File srcFile = new File(sinossRealPath, storageFileName);
			if(destFile.exists()){
				throw new RuntimeException("程序跑的太快，创建了同名文件");
			} else {
				try {
					FileUtils.copyFile(srcFile, destFile);
				} catch (IOException e) {
					throw new RuntimeException("文件复制失败");
				}
			}
			specialApplication.setFile(smdbFilePath + smdbFileName);
			dao.modify(specialApplication);
		}
	}
	
	@SuppressWarnings("unchecked")
	public void initGeneralProject() {
		Long begin = System.currentTimeMillis();
		generalProjectsList = dao.query("select app from ProjectApplication app where app.type='general' and app.year=? and app.file is not null and app.name<>'媒介融合趋势下我国传媒产业发展问题及对策研究'", year);
		System.out.println("init GeneralProject complete! use time " + (System.currentTimeMillis() - begin) + "ms");
	}
	
	@SuppressWarnings("unchecked")
	public void initSpecialProject() {
		Long begin = System.currentTimeMillis();
		specialProjectsList = dao.query("select app from ProjectApplication app where app.type='special' and app.year=? and app.file is not null and app.name<>'媒介融合趋势下我国传媒产业发展问题及对策研究'", year);
		System.out.println("init SpecialProject complete! use time " + (System.currentTimeMillis() - begin) + "ms");
	}

}
