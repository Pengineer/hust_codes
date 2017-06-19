package csdc.tool.execution.importer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import csdc.bean.GeneralApplication;
import csdc.bean.InstpApplication;
import csdc.bean.SinossProjectApplication;
import csdc.tool.ApplicationContainer;

@Component
public class Year2014ApplyAttachmentUpload extends Importer {

	private List<GeneralApplication> generalProjectsList;
	private List<InstpApplication> instpProjectsList;
	private Map<String, String> docIdToSinossId;
	private Map<String, InstpApplication> docIdToProject;
	
	/**
	 * 文件名ID -> 文件修改时间
	 */
	Map<String, Long> sinossFiles;
	
	@Override
	protected void work() throws Throwable {
//		UploadGeneralProjectYear2014(); 
		UploadInstpProjectYear2014();
	}
	
	public void UploadGeneralProjectYear2014() throws Exception{
		initGeneralProject();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("/tmp/testLog")));
		String storageFileName = "";
		String smdbFileName = "";
		String extendName = "";
		
		String smdbFilePath = "upload/project/general/app/2014/";
		String smdbRealPath = ApplicationContainer.sc.getRealPath(smdbFilePath);
		
		String sinossFilePath = "upload/sinoss/gener/app/2014/";
		String sinossRealPath = ApplicationContainer.sc.getRealPath(sinossFilePath);
		
		Long tempFileModifiedTime = null ;
		
		//sinossFileName:龚郑勇(c364b0a0-3dc7-41e9-920f-11609d3f8a36).doc
		for(GeneralApplication generalApplication : generalProjectsList) {
			storageFileName = generalApplication.getFile();
			extendName = storageFileName.substring(storageFileName.lastIndexOf("."));

			tempFileModifiedTime = getSinossFileModifiedTime(sinossRealPath, storageFileName.replaceAll("^.*?\\((.*?)\\)\\.doc$","$1").replaceAll("-", ""));
			
			if(tempFileModifiedTime == null) {
				bw.write("源文件不存在：" + storageFileName);
				bw.newLine();
				continue;
			}
			
			smdbFileName = "general_app_2014_" + generalApplication.getUniversity().getCode() + "_" + sdf.format(new Date(tempFileModifiedTime)) + extendName;
			File destFile = new File(smdbRealPath, smdbFileName);
			File srcFile = new File(sinossRealPath, storageFileName);
			for(int i=1; destFile.exists(); i++) {
				smdbFileName = "general_app_2014_" + generalApplication.getUniversity().getCode() + "_" + sdf.format(new Date(tempFileModifiedTime + i)) + extendName;
				destFile = new File(smdbRealPath, smdbFileName);
			}
			
			try {
				FileUtils.copyFile(srcFile, destFile);
			} catch (IOException e) {
				bw.write(e.getMessage() + ":" + e.getStackTrace());
				bw.newLine();
				if(destFile.exists()) {		//删除拷贝不完整的文件
					destFile.delete();
				}
				continue;
			}
			
			generalApplication.setFile(smdbFilePath + smdbFileName);
			dao.modify(generalApplication);
		}
		bw.write("copy generalFiles over !");
		bw.close();
	}
	
	public void UploadInstpProjectYear2014() throws Exception{
		initInstpProject();
		initSinossInstpProject();
		
		int total = instpProjectsList.size();
		int current=0;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		Long tempFileModifiedTime = null ;
		File dir = new File("D:\\base\\2014");
		File[] listFile = dir.listFiles();
		String smdbFileName;
		String sinossId;
		
		for(InstpApplication instpApplication : instpProjectsList) {
			System.out.println((++current) + "/" + total);
			sinossId = docIdToSinossId.get(instpApplication.getFile());
			for(File file : listFile) {
				if(file.getName().equals(sinossId)) {
					String[] fileNames =  new File(file.getPath()).list();
					String srcFileName = "";
					for(String fileName :fileNames) {
						if(fileName.startsWith("sq_")){
							srcFileName = fileName;
							break;
						}
					}
					
					File srcFile = new File(file.getPath(), srcFileName);
					tempFileModifiedTime = srcFile.lastModified();
					smdbFileName = "instp_app_2014_" + instpApplication.getUniversity().getCode() + "_" + sdf.format(new Date(tempFileModifiedTime)) + ".doc";
					File destFile = new File("D:\\instp\\2014", smdbFileName);
					for(int i=1; destFile.exists(); i++) {
						smdbFileName = "instp_app_2014_" + instpApplication.getUniversity().getCode() + "_" + sdf.format(new Date(tempFileModifiedTime + i)) + ".doc";
						destFile = new File("D:\\instp\\2014", smdbFileName);
					}
					
					FileUtils.copyFile(srcFile, destFile);
					instpApplication.setFile("upload/project/instp/app/2014/" + smdbFileName);
					dao.modify(instpApplication);
				}
			}
		}
	}
	
	/**
	 * 初始化2014年需处理而还未处理的一般项目申请数据
	 */
	@SuppressWarnings("unchecked")
	public void initGeneralProject() {
		Long begin = System.currentTimeMillis();
		generalProjectsList = dao.query("select app from ProjectApplication app where app.type='general' and app.year=2014 and app.file is not null and instr(app.file,'general_app_2014_')=0");
		System.out.println("init GeneralProject complete! use time " + (System.currentTimeMillis() - begin) + "ms");
	}
	
	/**
	 * 初始化2014年需处理而还未处理的基地项目申请数据
	 */
	@SuppressWarnings("unchecked")
	public void initInstpProject() {
		Long begin = System.currentTimeMillis();
		if(docIdToProject == null) {
			docIdToProject = new HashMap<String, InstpApplication>();
		}
		instpProjectsList = dao.query("select app from ProjectApplication app where app.type='instp' and app.year=2014 and app.file is not null and instr(app.file,'instp_app_2014_')=0");
		System.out.println("init GeneralProject complete! use time " + (System.currentTimeMillis() - begin) + "ms");
	}
	
	/**
	 * 初始化中间表中2014年基地项目申请数据
	 */
	@SuppressWarnings("unchecked")
	public void initSinossInstpProject() {
		Long begin = System.currentTimeMillis();
		if (docIdToSinossId == null) {
			docIdToSinossId = new HashMap<String, String>();
		}
		List<Object[]> sinossInstpProjectsList = dao.query("select app.sinossId, app.applyDocName from SinossProjectApplication app where app.typeCode='base' and app.year='2014' and app.applyDocName is not null");
		for(Object[] project : sinossInstpProjectsList) {
			String sinossId = (String)project[0];
			String applyDocName = (String)project[1];
			docIdToSinossId.put(applyDocName, sinossId);
		}
		
		System.out.println("init SinossInstpProjectsList complete! use time " + (System.currentTimeMillis() - begin) + "ms");
	}
	
	public Long getSinossFileModifiedTime(String sinossRealPath, String storageFileName) {
		if(sinossFiles == null) {
			initSinossFiles(sinossRealPath);
		}
		return sinossFiles.get(storageFileName);
	}

	public void initSinossFiles(String sinossRealPath) {
		if(sinossFiles == null) {
			sinossFiles = new HashMap<String, Long>();
		}
		File dir = new File(sinossRealPath);
		File[] files = dir.listFiles();
		for(File file : files) {
			sinossFiles.put(file.getName().replaceAll("^.*?\\((.*?)\\)\\.doc$","$1").replaceAll("-", ""), file.lastModified());
		}
	}
	
	public static void main(String[] args) throws IOException {
		File dir = new File("D:\\base\\2014");
		File[] listFile = dir.listFiles();
		for(File file : listFile) {
			if(file.isDirectory()) {
				String[] fileNames =  new File(file.getPath()).list();
				String srcFileName = "";
				for(String fileName :fileNames) {
					if(fileName.startsWith("sq_")){
						srcFileName = fileName;
						break;
					}
				}
				System.out.println(file.getName() + ".." + file.getPath());
				File srcFile = new File(file.getPath(),srcFileName);
				File destFile = new File("D:\\base","test.doc");
				FileUtils.copyFile(srcFile, destFile);
			}
			
			return;
		}
	}

}
