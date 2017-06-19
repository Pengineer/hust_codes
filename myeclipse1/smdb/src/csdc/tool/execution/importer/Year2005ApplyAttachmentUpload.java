package csdc.tool.execution.importer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hwpf.extractor.WordExtractor;

import csdc.bean.GeneralApplication;
import csdc.bean.ProjectApplication;
import csdc.tool.StringTool;
import csdc.tool.reader.ExcelReader;

public class Year2005ApplyAttachmentUpload extends Importer {

	private ExcelReader reader;
	/**
	 * 高校代码+记录编号 -> 项目名
	 */
	private Map<String, String> recordCodeToProject;
	/**
	 * 项目名 -> 高校代码+记录编号
	 */
	private Map<String, String> projectToRecordCode;
	
	/**
	 * 高校代码+记录编号 -> 文件
	 */
	private Map<String, File> recordCodeToFile;
	private Map<String, String> recordCodeToFileDir;
	
	/**
	 * 记录未处理文件或处理错误的文件
	 */
	private List<String> errorMsgsList;
	private List<ProjectApplication> projectApplicationsList;
	
	@Override
	protected void work() throws Throwable {
		//recordCodeToProject = new HashMap<String, String>();
		recordCodeToFile = new HashMap<String, File>();
		recordCodeToFileDir = new HashMap<String, String>();
		errorMsgsList = new ArrayList<String>();
		
		File srcFile = null;
		File destFile = null;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String smdbFileName = "";
		String smdbGeneralRealPath = "F:\\work\\2.sks\\1.smdb\\0.参考资料\\数据资料\\社科项目\\一般项目\\项目电子文档\\项目申请评审书\\test";
		String smdbUploadFilePath = "upload/project/general/app/2005/";
		
		getExcelData();
		getAllFiles(new File("F:\\work\\2.sks\\1.smdb\\0.参考资料\\数据资料\\社科项目\\一般项目\\项目电子文档\\项目申请评审书\\2005"), recordCodeToFile);
		initGeneralProject();
		
		int size = projectApplicationsList.size();
		int current=0;
		File file = null;
		for(ProjectApplication application : projectApplicationsList){
			System.out.println((++current) + "/" + size + "--" + application.getId());
			if(null == application.getName()){
				continue;
			}
			String recordCode = projectToRecordCode.get(application.getName());
			if(null != recordCode){
				file = recordCodeToFile.get(recordCode);
				if(null != file){
					srcFile =new File(file.getAbsolutePath());
					recordCodeToFileDir.remove(recordCode);
				}else{
					continue;
				}
			}else{
				continue;
			}
			
			smdbFileName = "general_app_2005_" + application.getUniversity().getCode() + "_" + sdf.format(new Date(srcFile.lastModified())) + ".pdf";
			destFile = new File(smdbGeneralRealPath, smdbFileName);
			for(int i=1; destFile.exists(); i++) {
				smdbFileName = "general_app_2005_" + application.getUniversity().getCode() + "_" + sdf.format(new Date(srcFile.lastModified() + i)) + ".pdf";
				destFile = new File(smdbGeneralRealPath, smdbFileName);
			}
			
			try {
				FileUtils.copyFile(srcFile, destFile);
				FileUtils.forceDelete(srcFile);
			} catch (Exception e) {
				errorMsgsList.add(application.getName() + ":" + application.getFile());
				if(destFile.exists()) {//删除拷贝不完整的文件
					destFile.delete();
				}
				continue;
			}
			application.setFile(smdbUploadFilePath + smdbFileName);
			dao.modify(application);
		}
		
		System.out.println("----recordCodeToFileDir----" + recordCodeToFileDir.size());
		if(0 < recordCodeToFileDir.size()){
			FileUtils.writeStringToFile(new File("F:\\work\\2.sks\\1.smdb\\0.参考资料\\数据资料\\社科项目\\一般项目\\项目电子文档\\项目申请评审书\\2005test1.txt"), recordCodeToFileDir.toString().replaceAll(",\\s+", "\n"));
		}
	}
	
	private void getAllFiles(File dir, Map<String, File> recordToFile) 
			throws FileNotFoundException{
		File[] files = dir.listFiles();
		for (File file : files) {
			if (file.isDirectory())
				getAllFiles(file, recordToFile);
			else{
				String fileName = file.getName();
				String codeRecord = fileName.substring(0, fileName.lastIndexOf("_"));
				if(null != codeRecord && !"".equals(codeRecord)){
					recordToFile.put(codeRecord, file);
					recordCodeToFileDir.put(codeRecord, file.getAbsolutePath());
				}else {
					errorMsgsList.add("获取文件codeRecord错误: " + file.getAbsolutePath());
				}
			}
		}
	}
	
	public void initGeneralProject() {
		Long begin = System.currentTimeMillis();
		projectApplicationsList = dao.query("select app from ProjectApplication app where app.year=2005 and app.file is null");
		System.out.println("init GeneralProject complete! use time " + (System.currentTimeMillis() - begin) + "ms");
	}
	
	/**
	 * 获取Excel数据作为文件绑定项目的依据
	 * @throws Exception 
	 * @author 2014-8-30 
	 */
	public void getExcelData() throws Exception{
		Date begin = new Date();
		
		reader.readSheet(0);		
		recordCodeToProject = new HashMap<String,String>();
		projectToRecordCode = new HashMap<String,String>();
		while (next(reader)) {
			System.out.println(D);
			recordCodeToProject.put(G + "_" + A, D);
			projectToRecordCode.put(D, G + "_" + A);
		}
		
		System.out.println("initTitle complete! Used time: " + (new Date().getTime() - begin.getTime()) + "ms");
	}
	
	public static void main(String[] args) throws IOException {
		File file = new File("F:\\work\\2.sks\\1.smdb\\0.参考资料\\数据资料\\社科项目\\一般项目\\项目电子文档\\项目申请评审书\\2005\\10001_1190_60827.pdf");
		System.out.println(file.getName().substring(0, file.getName().lastIndexOf("_")));
	}

	public Year2005ApplyAttachmentUpload(){
	}
	
	public Year2005ApplyAttachmentUpload(String file) {
		reader = new ExcelReader(file);
	}
}
