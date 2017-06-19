package csdc.service.imp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionContext;

import csdc.bean.Expert;
import csdc.bean.ProjectApplicationReview;
import csdc.bean.ProjectApplicationReviewUpdate;
import csdc.bean.SystemOption;
import csdc.bean.University;
import csdc.service.IExpertService;
import csdc.tool.FileTool;
import csdc.tool.HSSFExport;
import csdc.tool.StringTool;
import csdc.tool.ZipUtil;

public class ExpertService extends BaseService implements IExpertService{
	
	public String addExpert(Expert expert) {
		String expertId = (String) this.add(expert);
		return expertId;
	}
	/**
	 * 群删专家
	 * @param expertIds
	 */
	public void deleteExperts(List<String> expertIds) {
		if (expertIds != null) {
			for (int i = 0; i < expertIds.size(); i++) {
				this.delete(Expert.class, expertIds.get(i));
			}
		}
	}
	
	/**
	 * 组装学科信息
	 */
	@SuppressWarnings("unchecked")
	public String getDiscipline(String discipline) {
		// map对象中用于value存学科
		String value = "";
		if (discipline != null) {
			// 拆分后的学科代码
			List<String> disids = new ArrayList<String>();
			discipline = discipline.replaceAll("\\s+", "");
			// 临时
			String disvalue = "";
			// 学科值
			List<Object> disvas;
			// 将学科代码组装成list
			do {
				if (discipline.indexOf(";") < 0) {// 没有了
					disvalue = discipline;
					disids.add(disvalue);
					break;
				} else {
					disvalue = discipline.substring(0, discipline.indexOf(";"));
					discipline = discipline.substring(discipline.indexOf(";") + 1);
					disids.add(disvalue);
				}
			} while (!discipline.isEmpty());// 直到为空
			// 遍历disids找到其名称并拼接为一个字符串，以逗号隔开
			if (!disids.isEmpty()) {// 如果学科不为空
				String test ="select s.code, min(s.name) from SystemOption s where s.code = '" + disids.get(0) + "'";// 测试
				for (int j = 1; j < disids.size(); j++) {
					test += " or s.code = '" + disids.get(j) + "'";
				}
				test += " group by s.code";
				disvas = this.query(test);
				if (disvas != null && !disvas.isEmpty()) {
					Object[] o;
					int disSize = disvas.size();
					for (int j = 0; j < disSize-1; j++ ) {//除去最后一个
						o = (Object[]) disvas.get(j);
						value += o[0].toString() + o[1].toString() + "; ";
					}
					o = (Object[]) disvas.get(disSize-1);//最后一个，不需要分号
					value += o[0].toString() + o[1].toString();
				}else{
					value = discipline;//如果没有，则返回原数字
				}
				
			}
		}
		return value;
	}
	
	/**
	 * 设置专家参评状态
	 * @param expertids待操作的专家ID
	 * @param label参评状态0退评，1参评
	 */
	@Transactional
	@SuppressWarnings("unchecked")
	public void setReview(List<String> expertIds, int label, String notReviewerReason, int isNotReviewAll) {
		if (expertIds == null || label != 0 && label != 1 || isNotReviewAll !=0 && isNotReviewAll !=1) {
			return;
		}
		Map application = ActionContext.getContext().getApplication();
		Integer defaultYear = (Integer) application.get("defaultYear");
		
		for (int i = 0; i < expertIds.size(); i++) {
			Expert expert = (Expert) this.query(Expert.class, expertIds.get(i));
			expert.setIsReviewer(label);
			expert.setReason(notReviewerReason);//设置退评原因
			if (label == 0) {// 如果是退评
				if(isNotReviewAll == 1) {// 如果确认删除当前年当前年评审项目的信息
					Map paraMap = new HashMap();
					paraMap.put("defaultYear", defaultYear);
					paraMap.put("reviewerId", expert.getId());
					List<ProjectApplicationReview> prs = this.query("select pr from ProjectApplicationReview pr where pr.type='general' and pr.year = :defaultYear and pr.reviewer.id = :reviewerId", paraMap);
					for (ProjectApplicationReview pr : prs) {
						this.add(new ProjectApplicationReviewUpdate(pr, 0, 1));
						this.delete(pr);
					}					
				}
			}
		}
	}
	
	public Map fetchTalentLevel(){
		Map map = new LinkedHashMap();
		List list = this.query("select distinct e.rating from Expert e order by e.rating asc");
		for (Object object : list) {
			map.put((String)object, (String)object);
		}
		return map;
	}
	
	/**
	 * 根据高校代码生成专家清单
	 * @param universityCode 高校代码
	 * @return 压缩包路径
	 */
	public String createExpertZip12(String universityCode) {
		String realPath = ServletActionContext.getServletContext().getRealPath("/");
		try {
			University university = (University) query(University.class, universityCode);
			String destFolder = "data/expert/archive/" + universityCode + "/" + universityCode + university.getName() + "专家表";
        	//生成目录
        	FileTool.mkdir_p(realPath + destFolder);
        	FileUtils.copyFile(new File(realPath + "data/expert/template/support.dll"), new File(realPath + destFolder + "/suppot.dll"));
        	FileUtils.copyFile(new File(realPath + "data/expert/template/expert.xls"), new File(realPath + destFolder + "/" + universityCode + university.getName() + "专家表.xls"));
        	FileUtils.copyFile(new File(realPath + "data/expert/template/readme.doc"), new File(realPath + destFolder + "/专家表使用说明.doc"));
        	FileUtils.copyFile(new File(realPath + "data/expert/template/expert.xls"), new File(realPath + destFolder + "/专家表模板.xls"));
        	try {
    			InputStream inp = new FileInputStream(realPath + destFolder + "/专家表模板.xls");
    	        Workbook wb =  WorkbookFactory.create(inp);
    	        Sheet sheet = wb.getSheetAt(0);
    	        Row row = sheet.getRow(0);
    	        row.getCell(0).setCellValue(row.getCell(0).getStringCellValue().replace("专家表", university.getName() + "专家表"));
    	        FileOutputStream fileOut = new FileOutputStream(realPath + destFolder + "/专家表模板.xls");
    	        wb.write(fileOut);
    	        fileOut.close();
        	} catch (Exception e) {
    			e.printStackTrace();
    		}
        	String destFile = realPath + destFolder + "/" + universityCode + university.getName() + "专家表.xls";
    		fillExpertExcel(universityCode, university.getName(), destFile);
        	// Zip打包
			ZipUtil.zip(realPath + "data/expert/archive/" + universityCode, realPath + "data/expert/archive/" + universityCode + university.getName() + "专家表.zip");
			// 删除临时文件夹
			FileTool.rm_fr(realPath + "data/expert/archive/" + universityCode);
			return destFolder + universityCode + university.getName() + "专家表.zip";
		}  catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public void fillExpertExcel(String universityCode, String universityName, String filePath) {
		List<SystemOption> soList = query(" select so from SystemOption so where so.standard = ? ", "[GBT13745-2009]");
		Map<String,String> map = new HashMap<String, String>();
		for(int i = 0; i < soList.size(); i++) {
			map.put(soList.get(i).getCode(), soList.get(i).getName());
		}
		List<Expert> expertList = query(" select e from Expert e where e.universityCode = ? order by e.department asc, e.name asc ", universityCode);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			InputStream inp = new FileInputStream(filePath);
	        Workbook wb =  WorkbookFactory.create(inp);
	        Sheet sheet = wb.getSheetAt(0);
	        Row row;
	        row = sheet.getRow(0);
	        row.getCell(0).setCellValue(row.getCell(0).getStringCellValue().replace("专家表", universityName + "专家表"));
	        System.out.println("expertList.size() " + expertList.size());
	        for(int i = 0; i < expertList.size(); i++) {
	        	Expert expert = expertList.get(i);
	        	row = sheet.createRow(2 + i);
	        	row.createCell(0).setCellValue(expert.getName());
	        	row.createCell(1).setCellValue(expert.getGender());
	        	row.createCell(2).setCellValue(expert.getBirthday() == null ? "" : df.format(expert.getBirthday()));
	        	//row.createCell(3).setCellValue(expert);
	        	//row.createCell(4).setCellValue(expert);
	        	row.createCell(5).setCellValue(expert.getIdCardNumber() == null ? "" : "身份证");
	        	row.createCell(6).setCellValue(expert.getIdCardNumber());
	        	row.createCell(7).setCellValue(expert.getJob());
	        	row.createCell(8).setCellValue(universityName);
	        	row.createCell(9).setCellValue(expert.getDepartment());
	        	//row.createCell(9).setCellValue(expert);
	        	row.createCell(11).setCellValue(expert.getOfficePhone());
	        	row.createCell(12).setCellValue(expert.getOfficePostcode());
	        	//row.createCell(12).setCellValue(expert);
	        	row.createCell(14).setCellValue(expert.getHomePhone());
	        	//row.createCell(14).setCellValue(expert);
	        	//row.createCell(15).setCellValue(expert);
	        	row.createCell(17).setCellValue(expert.getEmail());
	        	row.createCell(18).setCellValue(expert.getMobilePhone());
	        	//row.createCell(18).setCellValue(expert);
	        	row.createCell(20).setCellValue(expert.getOfficeAddress());
	        	//row.createCell(20).setCellValue(expert);
	        	//row.createCell(21).setCellValue(expert);
	        	row.createCell(23).setCellValue(expert.getDegree());
	        	row.createCell(24).setCellValue(expert.getSpecialityTitle());
	        	row.createCell(25).setCellValue(expert.getLanguage());
	        	row.createCell(26).setCellValue(expert.getIsDoctorTutor());
	        	//row.createCell(26).setCellValue();
	        	//row.createCell(27).setCellValue(expert);
	        	row.createCell(29).setCellValue(initDiscipline(map, expert.getDiscipline()));
	        	//row.createCell(29).setCellValue(expert);
	        	row.createCell(31).setCellValue(expert.getResearchField());
	        	//row.createCell(31).setCellValue(expert);
	        	row.createCell(33).setCellValue(expert.getPartTime());
	        	//row.createCell(33).setCellValue(expert);
	        	//row.createCell(34).setCellValue("一般");
	        	row.createCell(36).setCellValue(expert.getAward());
	        	row.createCell(37).setCellValue(expert.getMoeProject());
	        	row.createCell(38).setCellValue(expert.getNationalProject());
	        }
	        FileOutputStream fileOut = new FileOutputStream(filePath);
	        wb.write(fileOut);
	        fileOut.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public String initDiscipline(Map map, String disciplines) {
		if(disciplines != null && !disciplines.isEmpty() && !disciplines.equals("000")) {
			String[] dis = disciplines.split("; ");
			String re = "";
			for(int i = 0; i < dis.length; i++) {
				dis[i] = dis[i] + "/" + map.get(dis[i]);
				re += dis[i] + "; ";
			}
			return re.substring(0, re.length() - 2);
		}
		return null;
	}
	
	/**
	 * 根据高校代码生成专家清单
	 * @param universityCode 高校代码
	 * @return 压缩包路径
	 */
	public String createExpertZip(String universityCode,String id){
		Date begin = new Date();	
		String realPath = ServletActionContext.getServletContext().getRealPath("file");
		realPath = realPath.replace('\\', '/');
		try {
			Date date = new Date();
		    Calendar cal = Calendar.getInstance();
		    int year = cal.get(Calendar.YEAR);
			Map<String, String> univCodeNameMap = (Map<String, String>) ActionContext.getContext().getApplication().get("univCodeNameMap");
			String destFolder = "/template/expert/" + universityCode + "/" + "MOEExpert" + year + "_" + universityCode;
//			String destFolder = "file/template/expert/" + universityCode;
//          生成目录
	    	FileTool.mkdir_p(realPath + destFolder);
	    	FileUtils.copyFile(new File(realPath + "/template/expert/const.dat"), new File(realPath + destFolder + "/const.dat"));
	    	FileUtils.copyFile(new File(realPath + "/template/expert/support.dll"), new File(realPath + destFolder + "/support.dll"));
	    	FileUtils.copyFile(new File(realPath + "/template/expert/操作说明.doc"), new File(realPath + destFolder + "/操作说明.doc"));
	    	String destFile = realPath + destFolder + "/MOEExpert" + year + "_" + universityCode + ".xls";
	    	FileUtils.copyFile(new File(realPath + "/template/expert/MOEExpert.xls"), new File(destFile));
			HSSFExport.exportToFile(fetchExpertData(universityCode), destFile);
	    	// Zip打包
			String realZipPath = ServletActionContext.getServletContext().getRealPath("temp");
			realZipPath = realZipPath.replace('\\', '/');
			String zipPath = "/" + universityCode + univCodeNameMap.get(universityCode) + "专家表.zip"; 
			ZipUtil.zip(realPath + "/template/expert/" + universityCode, realZipPath + zipPath);
			// 删除临时文件夹
			FileTool.rm_fr(realPath + "/template/expert/" + universityCode);
			System.out.println("createUniversityExpert complete! Used time: " + (new Date().getTime() - begin.getTime()) + "ms");	
			return zipPath;
		}  catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 专家导出，返回输入流
	 */
	public InputStream exportExcel(String universityCode){
		String realPath = ServletActionContext.getServletContext().getRealPath("/");
		String modelFilePath = realPath + "file/template/expert/MOEExpert.xls"; 
		//调用导出公共方法
		return HSSFExport.exportFromModel(fetchExpertData(universityCode), modelFilePath);
	}
	
	/**
	 * 获取专家数据
	 * @param universityCode	等于null表示导入所有高校的专家
	 * @return
	 */
	private List<List> fetchExpertData(String universityCode) {
		Map<String, String> disMap = (Map<String, String>) ActionContext.getContext().getApplication().get("disMap");
		Map<String, String> univNameCodeMap = (Map<String, String>) ActionContext.getContext().getApplication().get("univNameCodeMap");
		String univCode = null;
		//获取待导出的数据
		StringBuffer hql = new StringBuffer();
		hql.append("select e.name, e.gender, to_char(e.birthday, 'yyyy-MM-dd'), e.idCardType, e.idCardNumber, u.name, e.usedName, e.department, e.job, e.specialityTitle," +
				" e.positionLevel, e.mobilePhone, e.email, e.homePhone, e.officePhone, e.officePostcode, e.officeFax, e.officeAddress, e.discipline," +
				" e.researchField, e.lastEducation, e.degree, e.isDoctorTutor, e.language, e.computerLevel, e.ethnicLanguage, e.postDoctor, e.partTime," +
				" e.abroad, e.award, e.moeProject, e.nationalProject, e.introduction, e.id from Expert e, University u ");
		hql.append(" where e.universityCode = u.code and e.type = '专职人员'");
		if (null != universityCode) {
			hql.append(" and e.universityCode = ? ");
		}
		hql.append(" order by u.name asc, e.name asc, e.gender asc");
		List<List> arrayList = new ArrayList<List>();
		List<Object[]> experts = null;
		if (null != universityCode) {
			experts = query(hql.toString(), universityCode);
		}else {
			experts = query(hql.toString());	
		}
		for (Object[] expert : experts) {
			if(null != expert[5]){//专家所在高校，格式为“code/name”
				univCode = univNameCodeMap.get(expert[5].toString());
				expert[5] = univCode + "/" + expert[5];
			}			
			if(null != expert[11]){//手机
				expert[11] = StringTool.toDBC(expert[11].toString()).split("[^-\\d\\(\\)]+")[0];
			}
			if(null != expert[12]){//邮箱
				expert[12] = StringTool.toDBC(expert[12].toString()).split("[\\s;,、]+")[0];
			}
			if(null != expert[13]){//家庭电话
				expert[13] = StringTool.toDBC(expert[13].toString()).split("[^-\\d\\(\\)]+")[0];
			}
			if(null != expert[14]){//家庭电话
				expert[14] = StringTool.toDBC(expert[14].toString()).split("[^-\\d\\(\\)]+")[0];
			}
			if(null != expert[16]){//办公传真
				expert[16] = StringTool.toDBC(expert[16].toString()).split("[^-\\d\\(\\)]+")[0];
			}
			if(null != expert[18]){//学科代码
				StringBuffer disCodeName = new StringBuffer(); 
				String[] disCodes = StringTool.toDBC(expert[18].toString()).split("\\s*;\\s*");
				for (String disCode : disCodes) {
					if(null != disMap.get(disCode)){
						disCodeName.append(disCode).append("/").append(disMap.get(disCode)).append("; ");
					}
				}
				if (disCodeName.length() > 2) {
					expert[18] = disCodeName.substring(0, disCodeName.length() - 2);
				}
			}
//			if(null != expert[30]){//承担教育部项目情况
//				expert[30] = "<moeProjectData><oldProjectInfos><![CDATA[" + expert[30].toString() + "]]></oldProjectInfos></moeProjectData>";
//			}
//			if(null != expert[31]){//承担国家社科项目情况
//				expert[31] = "<nationalProjectData><oldProjectInfos><![CDATA[" + expert[31].toString() + "]]></oldProjectInfos></nationalProjectData>";
//			}
		}
		arrayList.add(experts);
		return arrayList;
	}

	
	/**
	 * 没有上报专家的高校
	 */
	public InputStream exportExcel(){
		
//		String header = "教育部人文社会科学研究项目评审专家信息";
//		//设置标题行
//		String[] title = {
//			"姓名","性别","出生日期","证件类型","证件号码","所在高校","所在部门","行政职务","专业职称","岗位等级","" +
//			"手机","电子邮箱","家庭电话","办公电话","办公邮编","办公传真","办公地址","学科代码","研究方向","最后学历","最后学位","是否博导","" +
//			"外语语种","计算机操作水平","民族语言","博士后","学术兼职","出国经历","人才奖励资助情况","承担教育部项目情况","承担国家社科项目情况","个人简介","备注"
//		};
//		//调用导出公共方法
//		return HSSFExport.commonExportExcel(fetchExpertData(null).get(0), header, title);
		
		String header = "2012年 未上报专家的高校名单";
		//设置标题行
		String[] title = {
			"高校名称", "高校代码", "办学类型"
		};
		String hql1 = "select distinct e.universityCode from Expert e where e.importedDate is not null ";
		List list = query(hql1);
		Map map = new HashMap<String, List<String>>();
		map.put("list", list);
		String hql = "select u.name, u.code, u.style from University u where u.code not in (:list) and (u.style like '11%' or u.style like '12%')";
		//获取待导出的数据
		List dataList = query(hql, map);
		//调用导出公共方法
		return HSSFExport.commonExportExcel(dataList, header, title);
	}
	
}
