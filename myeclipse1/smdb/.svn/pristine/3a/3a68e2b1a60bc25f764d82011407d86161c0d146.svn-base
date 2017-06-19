package csdc.tool.execution.importer;

import java.awt.print.Pageable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.rtf.RTFEditorKit;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.pdfbox.PDFReader;
import org.pdfbox.pdfparser.PDFParser;
import org.pdfbox.pdmodel.PDDocument;
import org.pdfbox.util.PDFTextStripper;

import csdc.bean.GeneralApplication;
import csdc.bean.ProjectApplication;
import csdc.tool.StringTool;

public class Year2006ApplyAttachmentUpload extends Importer {
	
	private static List<GeneralApplication> generalProjectsList;
	private static Map<String, File> uniAndNameToFile = new HashMap<String, File>();
	private static Map<String, File> nameToFile = new HashMap<String, File>();
	private static Map<String, File> error = new HashMap<String, File>();
	private static int count;
	private static Set<String> errorMsgs;
	/**
	 * <项目名称, 项目申请对象>
	 */
	private Map<String, ProjectApplication> projectApplicationMap;
	
	
	protected void work() throws Throwable {
		UploadGeneralProjectYear2008(); 
	}
	
	public void UploadGeneralProjectYear2008() throws Exception{
		count = 0;
		initGeneralProject();
		
		int size = generalProjectsList.size();
		int current=0;
		Set<String> msg = new HashSet<String>();
		errorMsgs = new HashSet<String>();
		uniAndNameToFile = new HashMap<String, File>();
		nameToFile = new HashMap<String, File>();
		
		File srcFile = null;
		File destFile = null;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		
		String smdbFileName = "";
		String smdbGeneralRealPath = "F:\\work\\2.sks\\1.smdb\\0.参考资料\\数据资料\\社科项目\\一般项目\\项目电子文档\\项目申请评审书\\test";
		String smdbUploadFilePath = "upload/project/general/app/2006/";
		
		getAllFiles(new File("F:\\work\\2.sks\\1.smdb\\0.参考资料\\数据资料\\社科项目\\一般项目\\项目电子文档\\项目申请评审书\\2006"), uniAndNameToFile, nameToFile, error);
		
		int uniAndNameCount = uniAndNameToFile.size();
		int nameCount = nameToFile.size();
		File file = null;
		
		for(ProjectApplication application : generalProjectsList) {
			System.out.println((++current) + "/" + size + "--" + application.getId());
			//smdbFilePath = smdbGeneralFilePath;
			if(null == application.getAgencyName() || null == application.getApplicantName()){
				continue;
			}
			file = uniAndNameToFile.get(application.getAgencyName() + "-" + application.getApplicantName());
			if(null == file){
				file = nameToFile.get(application.getApplicantName());
				if(null == file){
					file = dealSpecialDoc(application.getId());
					if(null != file){
						srcFile =new File(file.getAbsolutePath());
					}else{
						continue;
					}
				}else{
					srcFile =new File(file.getAbsolutePath());
					nameToFile.remove(application.getApplicantName());
				}
			}else{
				srcFile =new File(file.getAbsolutePath());
				uniAndNameToFile.remove(application.getAgencyName() + "-" + application.getApplicantName());
			}
			
			smdbFileName = "general_app_2006_" + application.getUniversity().getCode() + "_" + sdf.format(new Date(srcFile.lastModified())) + ".doc";
			destFile = new File(smdbGeneralRealPath, smdbFileName);
			for(int i=1; destFile.exists(); i++) {
				smdbFileName = "general_app_2006_" + application.getUniversity().getCode() + "_" + sdf.format(new Date(srcFile.lastModified() + i)) + ".doc";
				destFile = new File(smdbGeneralRealPath, smdbFileName);
			}
			
			try {
				FileUtils.copyFile(srcFile, destFile);
				//FileUtils.forceDelete(srcFile);
			} catch (Exception e) {
				msg.add(application.getName() + ":" + application.getFile());
				if(destFile.exists()) {//删除拷贝不完整的文件
					destFile.delete();
				}
				continue;
			}
			application.setFile(smdbUploadFilePath + smdbFileName);
			dao.modify(application);
		}
	
		System.out.println("uniAndNameToFile");
		Map<String, File> anotherUniAndNameMap = new HashMap<String, File>();
		anotherUniAndNameMap.putAll(uniAndNameToFile);
		Iterator<Map.Entry<String, File>> it = anotherUniAndNameMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, File> entry = it.next();
			srcFile =new File(entry.getValue().getAbsolutePath());
			String projectName = getProjectName(entry.getValue());
			if(null != projectName){
				System.out.println(entry.getKey() + "--" + projectName);
				ProjectApplication app = null;
				if("韩国语汉字词与汉语词关联研究".equals(projectName)){
					app = (ProjectApplication)dao.queryUnique("select app from ProjectApplication app where app.id='4028d88a3098b5a1013098b7281b7590'");
				}else if("桂林工学院-王青山".equals(entry.getKey())){
					app = (ProjectApplication)dao.queryUnique("select app from ProjectApplication app where app.id='4028d88a3098b5a1013098b7224f1e3e'");
				}else{
					app = (ProjectApplication)dao.queryUnique("select app from ProjectApplication app where app.year=2006 and app.file is null and app.name ='" + projectName + "'");
				}
				
				if(null != app){
					smdbFileName = "general_app_2006_" + app.getUniversity().getCode() + "_" + sdf.format(new Date(srcFile.lastModified())) + ".doc";
					destFile = new File(smdbGeneralRealPath, smdbFileName);
					for(int i=1; destFile.exists(); i++) {
						smdbFileName = "general_app_2006_" + app.getUniversity().getCode() + "_" + sdf.format(new Date(srcFile.lastModified() + i)) + ".doc";
						destFile = new File(smdbGeneralRealPath, smdbFileName);
					}
					try {
						FileUtils.copyFile(srcFile, destFile);
						//FileUtils.forceDelete(srcFile);
					} catch (Exception e) {
						msg.add(app.getName() + ":" + app.getFile());
						if(destFile.exists()) {//删除拷贝不完整的文件
							destFile.delete();
						}
						continue;
					}
					uniAndNameToFile.remove(entry.getKey());
					app.setFile(smdbUploadFilePath + smdbFileName);
					dao.modify(app);
				}else{
					String objs[] = entry.getKey().split("-");
					if(2 == objs.length){
						List<ProjectApplication> pApps = dao.query("select app from ProjectApplication app where app.year=2006 and app.file is null and app.applicantName='" + objs[1] + "'");
						
						if(1 == pApps.size()){
							smdbFileName = "general_app_2006_" + pApps.get(0).getUniversity().getCode() + "_" + sdf.format(new Date(srcFile.lastModified())) + ".doc";
							destFile = new File(smdbGeneralRealPath, smdbFileName);
							for(int i=1; destFile.exists(); i++) {
								smdbFileName = "general_app_2006_" + pApps.get(0).getUniversity().getCode() + "_" + sdf.format(new Date(srcFile.lastModified() + i)) + ".doc";
								destFile = new File(smdbGeneralRealPath, smdbFileName);
							}
							try {
								FileUtils.copyFile(srcFile, destFile);
								//FileUtils.forceDelete(srcFile);
							} catch (Exception e) {
								msg.add(pApps.get(0).getName() + ":" + pApps.get(0).getFile());
								if(destFile.exists()) {//删除拷贝不完整的文件
									destFile.delete();
								}
								continue;
							}
							uniAndNameToFile.remove(entry.getKey());
							pApps.get(0).setFile(smdbUploadFilePath + smdbFileName);
							dao.modify(pApps.get(0));
						}
					}
				}
			}
			
		}
		
		System.out.println("nameToFile");
		Map<String, File> anotherNameMap2 = new HashMap<String, File>();
		anotherNameMap2.putAll(nameToFile);
		Iterator<Map.Entry<String, File>> itu = anotherNameMap2.entrySet().iterator();
		while (itu.hasNext()) {
			Map.Entry<String, File> entry = itu.next();
			srcFile =new File(entry.getValue().getAbsolutePath());
			String projectName = getProjectName(entry.getValue());
			if(null != projectName){
				System.out.println(entry.getKey() + "--" + projectName);
				//ProjectApplication app = projectApplicationMap.get(projectName);
				ProjectApplication app = (ProjectApplication)dao.queryUnique("select app from ProjectApplication app where app.year=2006 and app.file is null and app.name ='" + projectName + "'");
				if(null != app){
					smdbFileName = "general_app_2006_" + app.getUniversity().getCode() + "_" + sdf.format(new Date(srcFile.lastModified())) + ".doc";
					destFile = new File(smdbGeneralRealPath, smdbFileName);
					for(int i=1; destFile.exists(); i++) {
						smdbFileName = "general_app_2006_" + app.getUniversity().getCode() + "_" + sdf.format(new Date(srcFile.lastModified() + i)) + ".doc";
						destFile = new File(smdbGeneralRealPath, smdbFileName);
					}
					try {
						FileUtils.copyFile(srcFile, destFile);
						//FileUtils.forceDelete(srcFile);
					} catch (Exception e) {
						msg.add(app.getName() + ":" + app.getFile());
						if(destFile.exists()) {//删除拷贝不完整的文件
							destFile.delete();
						}
						continue;
					}
					nameToFile.remove(entry.getKey());
					app.setFile(smdbUploadFilePath + smdbFileName);
					dao.modify(app);
				}else{
					continue;
				}
			}
		}
		
		System.out.println("----msg----");
		if(0 < msg.size()) {
			System.out.println(msg.toString().replaceAll(",\\s+", "\n"));
		}
		System.out.println("----文件读取失败----");
		if(0 < errorMsgs.size()){
			System.out.println(errorMsgs.toString().replaceAll(",\\s+", "\n"));
		}
		System.out.println("----uniAndNameToFile----" + uniAndNameCount);
		if(0 < uniAndNameToFile.size()){
			FileUtils.writeStringToFile(new File("F:\\work\\2.sks\\1.smdb\\0.参考资料\\数据资料\\社科项目\\一般项目\\项目电子文档\\项目申请评审书\\2006test1.txt"), uniAndNameToFile.toString().replaceAll(",\\s+", "\n"));
		}
		System.out.println("----nameToFile----" + nameCount);
		if(0 < nameToFile.size()){
			FileUtils.writeStringToFile(new File("F:\\work\\2.sks\\1.smdb\\0.参考资料\\数据资料\\社科项目\\一般项目\\项目电子文档\\项目申请评审书\\2006test2.txt"), nameToFile.toString().replaceAll(",\\s+", "\n"));
		}
		System.out.println("----error----");
		if(0 < error.size()){
			System.out.println(error.toString().replaceAll(",\\s+", "\n"));
		}
	}
	
	/**
	 * 初始化2014年需处理而还未处理的一般项目申请数据
	 */
	@SuppressWarnings("unchecked")
	public void initGeneralProject() {
		Long begin = System.currentTimeMillis();
		generalProjectsList = dao.query("select app from ProjectApplication app where app.year=2006 and app.file is null");
		System.out.println("init GeneralProject complete! use time " + (System.currentTimeMillis() - begin) + "ms");
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
	
	public static void main(String[] args) throws IOException {
	}
	
	private void getAllFiles(File dir, Map<String, File> uniAndNameToFile, Map<String, File> nameToFile, Map<String, File> error) 
			throws FileNotFoundException{
		File[] files = dir.listFiles();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		List<File> uniFiles;
		for (File file : files) {
			if (file.isDirectory())
				getAllFiles(file, uniAndNameToFile, nameToFile, error);
			else{
				String path = file.getAbsolutePath();
				if(path.endsWith(".doc")){
					System.out.println(count + "--" + path);
					String name = null;
					String university = null;
					
					//特殊情况，word文档只有一个Paragraph
					if(path.contains("部属高校\\华中科技大学\\王诗才A.doc")){
						uniAndNameToFile.put("华中科技大学-王诗才", file);
						continue;
					}
					
					InputStream is = new FileInputStream(path);
					WordExtractor extractor = null;
					try {
						extractor = new WordExtractor(is);
					} catch (IOException e) {
						errorMsgs.add("IOException：" + path);
						continue;
					}catch (IllegalArgumentException e) {
						errorMsgs.add("RTF：" + path);
						continue;
					}catch(IndexOutOfBoundsException e){
						errorMsgs.add("文件损坏：" + path);
						continue;
					}catch (IllegalStateException e) {
						errorMsgs.add("文件损坏：" + path);
						continue;
					}
					
					String paraTexts[] = extractor.getParagraphText();
					for(int i = 0; i < paraTexts.length && i < 20; i++ ){
						String str = StringTool.toDBC(paraTexts[i]);
						//str = str.replaceAll("\\s", "");
						str = str.replaceAll("[^·0-9\u4e00-\u9fa5]", "");
						if (!"".equals(str)) {
							if(str.contains("项目负责人")){
								str = str.substring(str.lastIndexOf("项目负责人"));
								name = str.replaceAll("项目负责人", "");
								continue;
							}
							if(str.contains("所在学校")){
								str = str.replaceAll("盖章", "");
								str = str.replaceAll("签章", "");
								university = str.replaceAll("所在学校", "");
								continue;
							}
						}
					}
					if((!"".equals(name)) && (null != name) ){
						if((!"".equals(university)) && null != university){
							uniAndNameToFile.put(university + "-" + name, file);
							count++;
							//System.out.println(count);
						}else{
							nameToFile.put(name, file);
						}
					}else{
						error.put("error: ", file);
					}
					closeStream(is);
				}
			}
		}
	}
	
	private String getProjectName(File file) throws IOException{
		InputStream is = new FileInputStream(file);
		WordExtractor extractor = new WordExtractor(is);
		
		String projectName = null;
		String paraTexts[] = extractor.getParagraphText();
		for(int i = 0; i < paraTexts.length && i < 20; i++ ){
			if(paraTexts.length < 20){
				break;
			}
			String str = StringTool.toDBC(paraTexts[i]);
			//str = str.replaceAll("\\s", "");
			str = str.replaceAll("\\s", "");
			str = str.replaceAll("：", ":");
			str = str.replaceAll("_", "");
			if (!"".equals(str)) {
				if(str.contains("课题名称:")){
					str = str.substring(str.lastIndexOf("课题名称:"));
					projectName = str.replaceAll("课题名称:", "");
					if(projectName.contains("项目负责人")){
						projectName = projectName.substring(0, projectName.indexOf("项目负责人"));
					}
					String strNext  = StringTool.toDBC(paraTexts[i+1]);
					strNext = strNext.replaceAll("\\s", "");
					if(!strNext.contains("项目负责人")){
						projectName = projectName + strNext;
					}
				}
			}
		}
		
		return projectName;
	}
	
	/** 
    * 关闭输入流 
    * @param is 
    */  
   private static void closeStream(InputStream is) {  
      if (is != null) {  
         try {  
            is.close();  
         } catch (IOException e) {  
            e.printStackTrace();  
         }  
      }  
   }
   
   public static String getTextFromWord(String filePath){      
        String result = null;      
        File file = new File(filePath);      
        try{      
            FileInputStream fis = new FileInputStream(file);      
            WordExtractor wordExtractor = new WordExtractor(fis);      
            result = wordExtractor.getText();      
        }catch(FileNotFoundException e){      
            e.printStackTrace();      
        }catch(IOException e){      
            e.printStackTrace();      
        };      
        return result;      
    }
	
    /**
     * 处理匹配不上的文档
     * @param id
     */
	private File dealSpecialDoc(String id){
		File file = null;
		File srcFile = null;
		//学校名有差别、项目名有细微差别
		if("4028d88a3098b008013098b158ae4139".equals(id)){//1///
			file = uniAndNameToFile.get("陕西科技大学-孙红梅教授");
			srcFile =new File(file.getAbsolutePath());
			uniAndNameToFile.remove("陕西科技大学-孙红梅教授");
		}else if("4028d88a3098b008013098b155ef15c1".equals(id)){//2///
			file = uniAndNameToFile.get("广西师范学院-毛蒋兴");
			srcFile =new File(file.getAbsolutePath());
			uniAndNameToFile.remove("广西师范学院-毛蒋兴");
		}else if("4028d88a3098b008013098b159995701".equals(id)){//3///
			file = uniAndNameToFile.get("西安外国语大学-薛小惠");
			srcFile =new File(file.getAbsolutePath());
			uniAndNameToFile.remove("西安外国语大学-薛小惠");
		}else if("4028d88a3098b008013098b1587f3d1a".equals(id)){//4///
			file = uniAndNameToFile.get("曲阜师范大学-刘成新教授");
			srcFile =new File(file.getAbsolutePath());
			uniAndNameToFile.remove("曲阜师范大学-刘成新教授");
		}else if("4028d88a3098b008013098b15a4465a2".equals(id)){//5///
			file = uniAndNameToFile.get("浙江师范大学-潘涌教授");
			srcFile =new File(file.getAbsolutePath());
			uniAndNameToFile.remove("浙江师范大学-潘涌教授");
		}else if("4028d88a3098b008013098b158603b01".equals(id)){//6///
			file = uniAndNameToFile.get("宁夏大学-郭鸿雁");
			srcFile =new File(file.getAbsolutePath());
			uniAndNameToFile.remove("宁夏大学-郭鸿雁");
		}else if("4028d88a3098b008013098b156d92978".equals(id)){//7///
			file = uniAndNameToFile.get("吉林大学-黄也平笔名黄浩");
			srcFile =new File(file.getAbsolutePath());
			uniAndNameToFile.remove("吉林大学-黄也平笔名黄浩");
		}else if("4028d88a3098b008013098b1591c4ab7".equals(id)){//8///
			file = uniAndNameToFile.get("深圳大学-刘莉教授");
			srcFile =new File(file.getAbsolutePath());
			uniAndNameToFile.remove("深圳大学-刘莉教授");
		}else if("4028d88a3098b008013098b158ed4670".equals(id)){//9///
			file = uniAndNameToFile.get("上海交通大学-徐持忍美国");
			srcFile =new File(file.getAbsolutePath());
			uniAndNameToFile.remove("上海交通大学-徐持忍美国");
		}else if("4028d88a3098b008013098b159e75e01".equals(id)){//10///
			file = uniAndNameToFile.get("新疆师范大学-热合玛依·阿不都外力");
			srcFile =new File(file.getAbsolutePath());
			uniAndNameToFile.remove("新疆师范大学-热合玛依·阿不都外力");
		}else if("4028d88a3098b008013098b1573731a6".equals(id)){//11///
			file = uniAndNameToFile.get("内蒙古大学-舍·敖特根巴雅尔白敖特根");
			srcFile =new File(file.getAbsolutePath());
			uniAndNameToFile.remove("内蒙古大学-舍·敖特根巴雅尔白敖特根");
		}else if("4028d88a3098b008013098b1554309e3".equals(id)){//12///
			file = uniAndNameToFile.get("云南大理学院-赵怀仁");
			srcFile =new File(file.getAbsolutePath());
			uniAndNameToFile.remove("云南大理学院-赵怀仁");
		}else if("4028d88a3098b008013098b1596a527b".equals(id)){//13///
			file = uniAndNameToFile.get("同济大学-方耀楣");
			srcFile =new File(file.getAbsolutePath());
			uniAndNameToFile.remove("同济大学-方耀楣");
		}else if("4028d88a3098b008013098b1590c49d1".equals(id)){//14///
			file = uniAndNameToFile.get("湖南邵阳学院-陈红");
			srcFile =new File(file.getAbsolutePath());
			uniAndNameToFile.remove("湖南邵阳学院-陈红");
		}else if("4028d88a3098b008013098b15b8d72a5".equals(id)){//15///
			file = uniAndNameToFile.get("重庆工学院-周宏");
			srcFile =new File(file.getAbsolutePath());
			uniAndNameToFile.remove("重庆工学院-周宏");
		}else if("4028d88a3098b008013098b15514064e".equals(id)){//16///
			file = uniAndNameToFile.get("北京师范大学-王玉海");
			srcFile =new File(file.getAbsolutePath());
			uniAndNameToFile.remove("北京师范大学-王玉海");
		}else if("4028d88a3098b008013098b159795348".equals(id)){//17///
			file = uniAndNameToFile.get("安徽芜湖信息技术职业学院-杨俊");
			srcFile =new File(file.getAbsolutePath());
			uniAndNameToFile.remove("安徽芜湖信息技术职业学院-杨俊");
		}else if("4028d88a3098b008013098b1561e1a6b".equals(id)){//18///
			file = uniAndNameToFile.get("河南财经学院-赵凯");
			srcFile =new File(file.getAbsolutePath());
			uniAndNameToFile.remove("河南财经学院-赵凯");
		}else if("4028d88a3098b008013098b15488006e".equals(id)){//19///
			file = uniAndNameToFile.get("安徽财经大学-潘竞成");
			srcFile =new File(file.getAbsolutePath());
			uniAndNameToFile.remove("安徽财经大学-潘竞成");
		}else if("4028d88a3098b008013098b1565c1f79".equals(id)){//20///
			file = uniAndNameToFile.get("湖南师范大学-欧新明");
			srcFile =new File(file.getAbsolutePath());
			uniAndNameToFile.remove("湖南师范大学-欧新明");
		}else if("4028d88a3098b008013098b156ab2616".equals(id)){//21///
			file = uniAndNameToFile.get("华中科技大学-何士青");
			srcFile =new File(file.getAbsolutePath());
			uniAndNameToFile.remove("华中科技大学-何士青");
		}else if("4028d88a3098b008013098b158dd4543".equals(id)){//22///
			file = uniAndNameToFile.get("上海对外贸易学院-张永安");
			srcFile =new File(file.getAbsolutePath());
			uniAndNameToFile.remove("上海对外贸易学院-张永安");
		}else if("4028d88a3098b008013098b1592b4c3b".equals(id)){//23///
			file = uniAndNameToFile.get("新疆石河子大学-薛洁");
			srcFile =new File(file.getAbsolutePath());
			uniAndNameToFile.remove("新疆石河子大学-薛洁");
		}else if("4028d88a3098b008013098b1595a509f".equals(id)){//24///
			file = uniAndNameToFile.get("天津财经大学-梁秀伶");
			srcFile =new File(file.getAbsolutePath());
			uniAndNameToFile.remove("天津财经大学-梁秀伶");
		}else if("4028d88a3098b008013098b155720d3e".equals(id)){//25///
			file = uniAndNameToFile.get("东北师范大学-张杨");
			srcFile =new File(file.getAbsolutePath());
			uniAndNameToFile.remove("东北师范大学-张杨");
		}else if("4028d88a3098b008013098b1592b4c83".equals(id)){//26///
			file = uniAndNameToFile.get("首都经贸大学劳动经济学院-张骏生");
			srcFile =new File(file.getAbsolutePath());
			uniAndNameToFile.remove("首都经贸大学劳动经济学院-张骏生");
		}else if("4028d88a3098b008013098b15b6d6f96".equals(id)){//27///
			file = uniAndNameToFile.get("中山大学-朱琪");
			srcFile =new File(file.getAbsolutePath());
			uniAndNameToFile.remove("中山大学-朱琪");
		}else if("4028d88a3098b008013098b1587f3cf4".equals(id)){///
			file = nameToFile.get("张瑞甫张倩伟");
			srcFile =new File(file.getAbsolutePath());
			nameToFile.remove("张瑞甫张倩伟");
		}else if("4028d88a3098b008013098b15b7d7133".equals(id)){///
			file = nameToFile.get("韩复龄教授");
			srcFile =new File(file.getAbsolutePath());
			nameToFile.remove("韩复龄教授");
		}else if("4028d88a3098b008013098b15a256351".equals(id)){///
			file = nameToFile.get("王桂彩思竹");
			srcFile =new File(file.getAbsolutePath());
			nameToFile.remove("王桂彩思竹");
		}else if("4028d88a3098b008013098b1573731b5".equals(id)){///
			file = nameToFile.get("莎日娜");
			srcFile =new File(file.getAbsolutePath());
			nameToFile.remove("莎日娜");
		}
		return srcFile;
	}
	
	
	private ProjectApplication getApplicationbyId(String id){
		ProjectApplication app = (ProjectApplication)dao.queryUnique("select app from ProjectApplication app where app.id='" + id + "'");
		return app;
	}
}
