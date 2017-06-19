package csdc.tool.tableKit.imp;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Sheet;
import jxl.Workbook;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFFooter;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionContext;
import csdc.bean.Expert;
import csdc.bean.ProjectApplication;
import csdc.bean.ProjectApplicationReview;
import csdc.bean.SystemOption;
import csdc.bean.University;
import csdc.service.IBaseService;
import csdc.tool.DatetimeTool;
import csdc.tool.HSSFExport;
import csdc.tool.info.GlobalInfo;
import csdc.tool.tableKit.ITableKit;
import csdc.tool.validator.IValidator;
import csdc.tool.validator.imp.StringValidator;
import csdc.tool.widget.NumberHandle;

/**
 * 项目表工具包
 * @author 徐涵
 *
 */
@SuppressWarnings("unchecked")
public class ProjectKit implements ITableKit{
	/**
	 * 用于在页面显示list
	 */
	private List<List<String> > list;

	/**
	 * 用于导入到数据库
	 */
	private List<ProjectApplication> blist;
	
	@Autowired
	private IBaseService baseService;

	static private List<String> stdTitle = new ArrayList<String>();
	static {
		stdTitle.add("项目编号");
		stdTitle.add("添加日期");
		stdTitle.add("审核状态");
		stdTitle.add("申请书文件名");
		stdTitle.add("高校编号");
		stdTitle.add("高校名称");
		stdTitle.add("项目名称");
		stdTitle.add("项目类别");
		stdTitle.add("学科门类");
		stdTitle.add("申请日期");
		stdTitle.add("研究方向及代码");
		stdTitle.add("研究类别");
		stdTitle.add("计划完成日期");
		stdTitle.add("申请经费（万元）");
		stdTitle.add("其他来源经费");
		stdTitle.add("最终成果形式");
		stdTitle.add("申请人");
		stdTitle.add("性别");
		stdTitle.add("出生日期");
		stdTitle.add("职称");
		stdTitle.add("所在部门");
		stdTitle.add("职务");
		stdTitle.add("最后学历");
		stdTitle.add("最后学位");
		stdTitle.add("外语语种");
		stdTitle.add("通讯地址");
		stdTitle.add("邮编");
		stdTitle.add("固定电话");
		stdTitle.add("手机号");
		stdTitle.add("EMAIL");
		stdTitle.add("身份证号");
		stdTitle.add("成员信息[姓名(工作单位)]");
	}
	
	static private List<IValidator> vlist = new ArrayList<IValidator>();
	static {
		vlist.add(new StringValidator(500));
		vlist.add(new StringValidator(500));
		vlist.add(new StringValidator(500));
		vlist.add(new StringValidator(500));
		vlist.add(new StringValidator(500));
		vlist.add(new StringValidator(500));
		vlist.add(new StringValidator(500));
		vlist.add(new StringValidator(500));
		vlist.add(new StringValidator(500));
		vlist.add(new StringValidator(500));
		vlist.add(new StringValidator(500));
		vlist.add(new StringValidator(500));
		vlist.add(new StringValidator(500));
		vlist.add(new StringValidator(500));
		vlist.add(new StringValidator(500));
		vlist.add(new StringValidator(500));
		vlist.add(new StringValidator(500));
		vlist.add(new StringValidator(500));
		vlist.add(new StringValidator(500));
		vlist.add(new StringValidator(500));
		vlist.add(new StringValidator(500));
		vlist.add(new StringValidator(500));
		vlist.add(new StringValidator(500));
		vlist.add(new StringValidator(500));
		vlist.add(new StringValidator(500));
		vlist.add(new StringValidator(500));
		vlist.add(new StringValidator(500));
		vlist.add(new StringValidator(500));
		vlist.add(new StringValidator(500));
		vlist.add(new StringValidator(500));
		vlist.add(new StringValidator(500));
		vlist.add(new StringValidator(500));
	}
	
	String[] oo = new String[]{
			"专家评审表",
			"审核状态",
			"申请书文件名",
			"项目编号",
			"项目名称",
			"项目类别",
			"学科门类",
			"申请日期",
			"研究方向及代码",
			"研究类别",
			"计划完成日期",
			"申请经费",
			"其他来源经费",
			"最终成果形式",
			"高校代码",
			"高校名称",
			"申请人",
			"性别",
			"出生日期",
			"职称",
			"所在部门",
			"职务",
			"最后学历",
			"最后学位",
			"外语语种",
			"通讯地址",
			"邮编",
			"固定电话",
			"手机号",
			"EMAIL",
			"身份证号",
			"成员信息[姓名(工作单位)]",
			"备注",
			"专家1高校",
			"专家1高校代码",
			"专家1姓名",
			"专家1代码",
			"专家2高校",
			"专家2高校代码",
			"专家2姓名",
			"专家2代码",
			"专家3高校",
			"专家3高校代码",
			"专家3姓名",
			"专家3代码",
			"专家4高校",
			"专家4高校代码",
			"专家4姓名",
			"专家4代码",
			"专家5高校",
			"专家5高校代码",
			"专家5姓名",
			"专家5代码"
//			"专家6高校",
//			"专家6高校代码",
//			"专家6姓名",
//			"专家6代码",
//			"专家7高校",
//			"专家7高校代码",
//			"专家7姓名",
//			"专家7代码"
		};

	private void getList(File xls) throws Exception{
		Sheet rs = Workbook.getWorkbook(new FileInputStream(xls)).getSheet(0);
		list = new ArrayList<List<String> >();
		//System.out.println("row: " + rs.getRows());
		for (int i = 0; i < rs.getRows(); i++) {
			List row = new ArrayList<String>(); 
			for (int j = 0; j < stdTitle.size(); j++) {
				row.add(rs.getCell(j, i).getContents().trim());
			}
			list.add(row);
		}
		blist = new ArrayList<ProjectApplication>();
		for (int i = 1; i < list.size(); i++){
			//System.out.println(i + " / " + list.size());
			
			ProjectApplication project = new ProjectApplication();
			
			project.setId(list.get(i).get(0));
			project.setAddDate(DatetimeTool.getDate(list.get(i).get(1)));
			project.setAuditStatus(list.get(i).get(2));
			project.setFile(list.get(i).get(3));

			String cur = "", warning = "";
			List<University> ulist = baseService.query("select u from University u where u.code = '" + list.get(i).get(4).trim() + "'");
			University uByCode = null, uByName = null;
			if (ulist.size() > 0){
				uByCode = ulist.get(0);
			}
			ulist = baseService.query("select u from University u where u.name = '" + list.get(i).get(5).trim() + "'");
			if (ulist.size() > 0){
				uByName = ulist.get(0);
			}
			if (uByName != null){
				project.setUniversityCode(uByName.getCode());
				project.setUniversityName(uByName.getName());
			} else if (uByCode != null){
				project.setUniversityCode(uByCode.getCode());
				project.setUniversityName(uByCode.getName());
				warning = "高校名称错误，以高校代码为准 ";
			} else {
				warning = "高校名称、代码均错";
			}
//			project.setUniversityCode(list.get(i).get(4));
//			project.setUniversityName(list.get(i).get(5));
			
			
			project.setProjectName(list.get(i).get(6));
			project.setProjectType(list.get(i).get(7));
			project.setDisciplineType(list.get(i).get(8));
			project.setApplyDate(DatetimeTool.getDate(list.get(i).get(9)));
			project.setDiscipline(list.get(i).get(10).replaceAll("[^\\d\\w、]", "").replaceAll("、", "; "));

			List<String> disc = Arrays.asList(project.getDiscipline().split("[^\\d\\w]"));
			Collections.sort(disc, new Comparator() {
				public int compare(Object o1, Object o2) {
					String a = (String)o1, b = (String)o2;
					return a.compareTo(b) < 0 ? -1 : 1;
				}
			});
			for (int j = 0; j < disc.size(); j++){
				if (j == disc.size() - 1 || !disc.get(j+1).startsWith(disc.get(j))){
					cur += (cur.isEmpty() ? "" : "; ") + disc.get(j);
					if (baseService.query("select so.id from SystemOption so where so.code = '" + disc.get(j) + "'").size() == 0){
						warning += (warning.isEmpty() ? "" : "; ") + disc.get(j) + "不存在";
					}
				}
			}

			project.setDiscipline(cur);
			project.setResearchType(list.get(i).get(11));
			project.setPlanEndDate(DatetimeTool.getDate(list.get(i).get(12)));
			project.setApplyFee(list.get(i).get(13));
			project.setOtherFee(list.get(i).get(14));
			project.setFinalResultType(list.get(i).get(15));
			project.setDirector(list.get(i).get(16));
			project.setGender(list.get(i).get(17));
			project.setBirthday(DatetimeTool.getDate(list.get(i).get(18)));
			project.setTitle(list.get(i).get(19));
			project.setDepartment(list.get(i).get(20));
			project.setJob(list.get(i).get(21));
			project.setEducation(list.get(i).get(22));
			project.setDegree(list.get(i).get(23));
			project.setForeign(list.get(i).get(24));
			project.setAddress(list.get(i).get(25));
			project.setPostcode(list.get(i).get(26));
			project.setPhone(list.get(i).get(27));
			project.setMobile(list.get(i).get(28));
			project.setEmail(list.get(i).get(29));
			project.setIdcard(list.get(i).get(30));
			project.setMembers(list.get(i).get(31));
			project.setWarning(warning);
			project.setIsReviewable(1);

			blist.add(project);
		}
	}
	
	public List display(File xls) throws Exception {
		getList(xls);
		return list;
	}
	
	/**
	 * 导出xxxx年度教育部人文社会科学研究一般项目初审结果一览表
	 * @return
	 */
	public InputStream exportGeneralFirstAudit() {
		Map session = ActionContext.getContext().getSession();
		int year = (Integer) session.get("defaultYear");
		//项目初审结果
		String hql4Reason = "select p.id, p.projectName, p.projectType, p.universityName,  p.director, p.firstAuditResult from ProjectApplication p where p.type = 'general' and p.firstAuditResult is not null and p.firstAuditDate is not null and p.year = ? ";
		hql4Reason += " order by p.universityName, p.projectType asc, p.projectName asc";
		List dataList = baseService.query(hql4Reason, year);
		
		//添加序号
		for (int i=0, size = dataList.size(); i < size; i++) {
			Object[] obj = (Object[]) dataList.get(i);
			obj[0] = i + 1;
		}
		
		//导出配置
		String header = "";
		String[] title = {"序号", "项目名称", "项目类别", "高校名称", "申请人", "初审结果"};
//		String[] title = {"序号", "初审结果", "项目类别", "项目名称", "申请人", "高校名称"};
		String tail = "";
		float tailHeight = 0.0f;
		header = year + "年度教育部人文社会科学研究一般项目初审结果一览表";
		tail = "初审规则：\n" +
				"（1）职称与年龄审核：规划基金项目申请者，应为具有高级职称（含副高）的在编在岗教师；青年基金项目申请者，应为具有博士学位或中级以上（含中级）职称的在编在岗教师，年龄不超过40周岁（"+ (year - 40) +"年1月1日以后出生）；\n" +
				"（2）在研项目查重：项目申请人应不具有在研的国家自然科学基金、国家社会科学基金（含教育学、艺术学单列）、教育部人文社会科学研究一般项目、重大攻关项目、后期资助项目、基地项目、发展报告项目、专项任务项目；\n" +
				"（3）撤项项目审核：项目申请人应不具有三年内撤项的教育部人文社会科学研究一般项目、重大攻关项目、后期资助项目、基地项目、专项任务项目；\n" +
//					"（4）青年基金限制：已获得过青年基金项目资助的申请人（不管结项与否）不得再次申请青年基金项目，即申请人只能获得一次青年基金项目资助；\n" + 
				"（4）申请项目限制：申请国家社科基金年度、青年、后期资助、西部和单列学科项目的负责人同年度不能申请教育部一般项目。";
				
		tailHeight = 90.0f;
		return HSSFExport.commonExportExcel(dataList, header, title, tail, tailHeight);
	}
	
	/**
	 * 导出xxxx年度教育部人文社会科学研究基地项目查重情况一览表
	 * @throws Exception
	 */
	public InputStream exportInstpFirstAudit() {
		Map session = ActionContext.getContext().getSession();
		int year = (Integer) session.get("defaultYear");
		//退评项目
		String hql4Reason = "select '', p.projectName, p.projectType, p.universityName, p.director, p.firstAuditResult from ProjectApplication p where p.type = 'instp' and p.firstAuditResult is not null and p.firstAuditDate is not null and p.year = ? order by p.universityName, p.projectType asc, p.projectName asc";
		List dataList = baseService.query(hql4Reason, year);
		
		//添加序号
		for (int i=0, size = dataList.size(); i < size; i++) {
			Object[] obj = (Object[]) dataList.get(i);
			obj[0] = i + 1;
		}
		
		//导出配置
		String header = year + "年度教育部人文社会科学研究基地项目初审结果一览表";
		String[] title = {"序号", "项目名称", "项目类别", "高校名称", "申请人", "初审结果"};
		String tail = "初审规则：\n" +
				"（1）在研项目查重：项目申请人应不具有在研的国家社科基金重大项目、教育部哲学社会科学研究重大课题攻关项目、基地重大项目、后期资助项目；\n" +
				"（2）撤项项目审核：项目申请人应不具有三年内撤项的教育部哲学社会科学研究重大课题攻关项目、基地重大项目、后期资助项目；\n" + 
				"（3）职称审核：项目申请者必须是全国普通高等学校具有正高级专业技术职务的在编在岗教师。";
		float tailHeight = 65.0f;
		return HSSFExport.commonExportExcel(dataList, header, title, tail, tailHeight);
	}
	
	/**
	 * 导出拟立项统计表
	 * @throws Exception
	 */
	public void exportGrantingSchedule() throws Exception{
		Map parMap = new HashMap();
		Map session = ActionContext.getContext().getSession();
		parMap.put("defaultYear", session.get("defaultYear"));
		//1、按高校
		String hql4Granting1 = "select p.universityName, count(*) from ProjectApplication p where p.type = 'general' and (p.isGranting = 1 OR p.isGranting = 2) and p.year = :defaultYear group by p.universityName order by count(*) ASC";
		List dataList1 = baseService.query(hql4Granting1, parMap);
		int totalNum = 0;//总计
		for(int i = 0; i < dataList1.size(); i++){
			Object[] data = (Object[]) dataList1.get(i); 
			totalNum += ((Number)data[1]).intValue();//立项数总和
			dataList1.set(i, data);
		}
		dataList1.add(new Object[]{"总计", totalNum});
		Collections.reverse(dataList1);
		
		//2、按省市
		String hql4Granting2 = "select u.provinceName," +
				"(SELECT COUNT(*) FROM ProjectApplication p1, University u1 WHERE p1.type = 'general' and p1.universityCode = u1.code and u1.provinceName = u.provinceName and p1.year = :defaultYear GROUP BY u1.provinceName), " +
				" (SELECT COUNT(*) FROM ProjectApplication p2, University u2 WHERE p2.type = 'general' and p2.universityCode = u2.code and u2.provinceName = u.provinceName and (p2.isGranting = 1 OR p2.isGranting = 2) and p2.year = :defaultYear GROUP BY u2.provinceName)" +
				", 'grantRate' FROM ProjectApplication p, University u WHERE p.type = 'general' and p.universityCode = u.code and u.provinceName is not null and p.year = :defaultYear GROUP BY u.provinceName order by u.provinceName DESC";
		List dataList2 = baseService.query(hql4Granting2, parMap);
		//修整dataList2的部分数据
		int totalApplyNum = 0, totalGrantNum = 0;
		Object totalGrantRate = null;
		for(int i = 0; i < dataList2.size(); i++){
			Object[] data = (Object[]) dataList2.get(i); 
			data[1] = (data[1] == null) ? 0 : data[1]; 
			data[2] = (data[2] == null) ? 0 : data[2]; 
			int applyNum = ((Number)data[1]).intValue(); //申请数
			int grantNum = ((Number)data[2]).intValue(); //立项数
			data[3] = NumberHandle.getGrantedRate(applyNum, grantNum);//计算立项比例
			totalApplyNum += applyNum;//申请总数
			totalGrantNum += grantNum;//立项总数
			dataList2.set(i, data);
		}
		totalGrantRate = NumberHandle.getGrantedRate(totalApplyNum, totalGrantNum);
		dataList2.add(new Object[]{"总计", totalApplyNum, totalGrantNum, totalGrantRate});
		Collections.reverse(dataList2);
		
		List<List> dataList = new ArrayList<List>();
		dataList.add(dataList1);
		dataList.add(dataList2);
		//导出excel
		HttpServletResponse response = ServletActionContext.getResponse();
		//设置excel参数
		String fileName = session.get("defaultYear") + "年度拟立项统计表";
		String[] sheetNames = new String[]{"按高校", "按省市"};
		String[][] firstLineTitles = {{"高校名称", "立项数"}, {"省市", "申请数", "立项数", "立项比例"}};
		HSSFExport.exportData(response, dataList, fileName, sheetNames, firstLineTitles);
	}
	
	/**
	 * 导出拟立项清单(计划表)
	 * @throws Exception
	 */
	public void exportGrantingList() throws Exception{
		Map parMap = new HashMap();
		Map session = ActionContext.getContext().getSession();
		parMap.put("defaultYear", session.get("defaultYear"));
		parMap.put("xbAreas", GlobalInfo.XB_AREAS);
		parMap.put("specialAreas", GlobalInfo.SPECIAL_AREAS);
		parMap.put("xz", "西藏");
		parMap.put("xj", "新疆");
		
		String hql4Common = "select 'indexNum', p.projectName, p.director, p.researchType, p.projectType, p.disciplineType, p.voteNumber, p.score, p.isGranting, p.universityName from ProjectApplication p, University u where p.type = 'general' and p.universityCode = u.code and p.isGranting = 1 and p.year = :defaultYear ";
		//一般项目
		String hql4Granting1 = hql4Common + " and u.provinceName not in (:specialAreas)";
		//西部项目
		String hql4Granting2 = hql4Common + " and u.provinceName in (:xbAreas)";
		//西藏项目
		String hql4Granting3 = hql4Common + " and u.provinceName = :xz";
		//新疆项目
		String hql4Granting4 = hql4Common + " and u.provinceName = :xj";
		List dataList1 = baseService.query(hql4Granting1, parMap);
		List dataList2 = baseService.query(hql4Granting2, parMap);
		List dataList3 = baseService.query(hql4Granting3, parMap);
		List dataList4 = baseService.query(hql4Granting4, parMap);
		List<List> dataList = new ArrayList<List>();
		dataList.add(dataList1);
		dataList.add(dataList2);
		dataList.add(dataList3);
		dataList.add(dataList4);
		for(int j = 0; j < dataList.size(); j++){
			List list = dataList.get(j);
			for(int i = 0; i < list.size(); i++){
				Object[] data = (Object[]) list.get(i); 
				data[0] = i + 1;//设置序号
				data[8] = ((Integer)data[8] == 0) ? "否" : "是"; 
				list.set(i, data);
			}
			dataList.set(j, list);
		}
		//导出excel
		HttpServletResponse response = ServletActionContext.getResponse();
		//设置excel参数
		String fileName = session.get("defaultYear") + "年度拟立项计划表";
		String[] sheetNames = new String[]{"一般项目", "西部项目", "西藏项目", "新疆项目"};
		String[][] firstLineTitles = {
				{"序号", "项目名称", "负责人", "项目门类", "项目类别", "学科", "通过票数", "总分", "是否立项", "备注"}, 
				{"序号", "项目名称", "负责人", "项目门类", "项目类别", "学科", "通过票数", "总分", "是否立项", "备注"}, 
				{"序号", "项目名称", "负责人", "项目门类", "项目类别", "学科", "通过票数", "总分", "是否立项", "备注"}, 
				{"序号", "项目名称", "负责人", "项目门类", "项目类别", "学科", "通过票数", "总分", "是否立项", "备注"}
				};
		HSSFExport.exportData(response, dataList, fileName, sheetNames, firstLineTitles);
	}
	
	/**
	 * 导出公示项目清单
	 * @throws Exception 
	 */
	public void exportPublicityList() throws Exception{
		Map parMap = new HashMap();
		Map session = ActionContext.getContext().getSession();
		parMap.put("defaultYear", session.get("defaultYear"));
		parMap.put("xbAreas", GlobalInfo.XB_AREAS);
		parMap.put("specialAreas", GlobalInfo.SPECIAL_AREAS);
		parMap.put("xz", "西藏自治区");
		parMap.put("xj", "新疆维吾尔自治区");
		String hql4Common = "select 'indexNum', p.projectName, p.director, u.name, p.researchType, p.projectType, p.disciplineType, p.voteNumber, p.score, p.isGranting, p.isGranting, p.year from ProjectApplication p, University u where p.type = 'general' and p.universityCode = u.code and (p.isGranting = 1 OR p.isGranting = 2) and p.year = :defaultYear ";
		
		//一般项目
		String hql4Granting1 = hql4Common + " and u.provinceName not in (:specialAreas)";
		//西部项目
		String hql4Granting2 = hql4Common + " and u.provinceName in (:xbAreas)";
		//西藏项目
		String hql4Granting3 = hql4Common + " and u.provinceName = :xz";
		//新疆项目
		String hql4Granting4 = hql4Common + " and u.provinceName = :xj";
		List dataList1 = baseService.query(hql4Granting1, parMap);
		List dataList2 = baseService.query(hql4Granting2, parMap);
		List dataList3 = baseService.query(hql4Granting3, parMap);
		List dataList4 = baseService.query(hql4Granting4, parMap);
		List<List> dataList = new ArrayList<List>();
		dataList.add(dataList1);
		dataList.add(dataList2);
		dataList.add(dataList3);
		dataList.add(dataList4);
		for(int j = 0; j < dataList.size(); j++){
			List list = dataList.get(j);
			for(int i = 0; i < list.size(); i++){
				Object[] data = (Object[]) list.get(i); 
				data[0] = i + 1;//设置序号
				int grantStatus = (Integer)data[9];//立项状态
				data[9] =  (grantStatus == 0) ? "不立项" : ((grantStatus == 1)? "拟立项" : "确定立项");
				int isPublic = (Integer)data[10];//是否公示 
				data[10] = (isPublic == 0) ? "否" : "是"; 
				list.set(i, data);
			}
			dataList.set(j, list);
		}
		//导出excel
		HttpServletResponse response = ServletActionContext.getResponse();
		//设置excel参数
		String fileName = session.get("defaultYear") + "年度公示项目";
		String[] sheetNames = new String[]{"一般项目", "西部项目", "西藏项目", "新疆项目"};
		String[][] firstLineTitles = {
				{"序号", "项目名称", "负责人", "高校名称", "研究类型", "项目类别", "学科门类", "通过票数", "总分", "立项状态", "公示状态", "项目年度"}, 
				{"序号", "项目名称", "负责人", "高校名称", "研究类型", "项目类别", "学科门类", "通过票数", "总分", "立项状态", "公示状态", "项目年度"},
				{"序号", "项目名称", "负责人", "高校名称", "研究类型", "项目类别", "学科门类", "通过票数", "总分", "立项状态", "公示状态", "项目年度"},
				{"序号", "项目名称", "负责人", "高校名称", "研究类型", "项目类别", "学科门类", "通过票数", "总分", "立项状态", "公示状态", "项目年度"}
				};
		HSSFExport.exportData(response, dataList, fileName, sheetNames, firstLineTitles);
	}
	
	public void exprtTemplate() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		Vector v = new Vector();
		HSSFExport.commonExportData(oo, v, response);
	}

	public void exportProjectReviewer(int exportAll) throws Exception {
		//Date begin = new Date();
		ActionContext context = ActionContext.getContext();
		Map session = context.getSession();
		String titleString = session.get("defaultYear").toString() + "年度一般项目专家匹配表";
		String[] oo = new String[]{
				titleString,
				"项目编号",
				"项目名称",
				"高校代码",
				"高校名称",
				"申请人",
				"专家1高校",
				"专家1高校代码",
				"专家1姓名",
				"专家1代码",
				"专家2高校",
				"专家2高校代码",
				"专家2姓名",
				"专家2代码",
				"专家3高校",
				"专家3高校代码",
				"专家3姓名",
				"专家3代码",
				"专家4高校",
				"专家4高校代码",
				"专家4姓名",
				"专家4代码",
				"专家5高校",
				"专家5高校代码",
				"专家5姓名",
				"专家5代码"
			};
		HttpServletResponse response = ServletActionContext.getResponse();
		Vector v = new Vector();
		
		List list = baseService.query("select u from University u");
		HashMap<String, String> univMap = new HashMap<String, String>();
		for (Object object : list) {
			University university = (University) object;
			univMap.put(university.getCode(), university.getName());
		}
		HashMap<String, String> discMap = new HashMap<String, String>();
		int year = Integer.parseInt(session.get("defaultYear").toString());
		//从application中获取学科代码
		Map application = ActionContext.getContext().getApplication();
		list = (List) application.get("disall");
		for (Object object : list) {
			SystemOption so = (SystemOption) object;
			discMap.put(so.getCode(), so.getName());
		}
		HashMap<String, Expert> expMap = new HashMap<String, Expert>();
		list = baseService.query("select exp from Expert exp");
		for (Object object : list) {
			Expert exp = (Expert) object;
			expMap.put(exp.getId(), exp);
		}
		
		List<ProjectApplicationReview> prList = baseService.query("select pr from ProjectApplicationReview pr where pr.type = 'general' and pr.year = ? order by pr.project.id asc", year);
		HashMap<String, List<String>> grsMap = new HashMap<String, List<String>>();//项目ID与该项目的评审专家的映射
		for (ProjectApplicationReview pr : prList) {
			if(null != pr.getPriority() && pr.getPriority() > 5){//只取前5个专家，不考虑备评
				continue;
			}
			List<String> expertIds = grsMap.get(pr.getProject().getId());
			if (null == expertIds) {
				expertIds = new ArrayList<String>();
			}
			expertIds.add(pr.getReviewer().getId());
			grsMap.put(pr.getProject().getId(), expertIds);
		}
		
		if (exportAll != 0){
			list = baseService.query("select p from ProjectApplication p where p.type = 'general' and p.year = ? order by p.id asc", year);
		} else {
			list = baseService.query(((String) session.get("HQL4ProjectExport")).replaceAll("order by[\\s\\S]*", "order by p.id asc"), (Map)session.get("Map4ProjectExport"));
		}

		//Date mid = new Date();
		
		for (Object object : list) {
			ProjectApplication p = (ProjectApplication) object;
			List pList = new ArrayList<Object>();
			pList.add(p.getNumber());
			pList.add(p.getProjectName());
//			pList.add(DatetimeTool.getYearMonthDateString(p.getAddDate()));
//			pList.add(p.getAuditStatus());
//			pList.add(p.getFile());
			pList.add(p.getUniversityCode());
			pList.add(p.getUniversityName());
//			pList.add(p.getProjectType());
//			pList.add(p.getDisciplineType());
//			pList.add(DatetimeTool.getYearMonthDateString(p.getApplyDate()));
			
//			String discString = "";//学科代码+学科名称
//			if(null != p.getDiscipline() && !p.getDiscipline().isEmpty()){
//				String disc[] = p.getDiscipline().split("; ");
//				for (String s : disc) {
//					discString += (discString.isEmpty() ? "" : "、") + ((discMap.get(s)==null) ? "" : discMap.get(s)) + "(" + s + ")";
//				}
//			}
//			pList.add(discString);
//			pList.add(p.getResearchType());
//			pList.add(DatetimeTool.getYearMonthDateString(p.getPlanEndDate()));
//			pList.add(p.getApplyFee());
//			pList.add(p.getOtherFee());
//			pList.add(p.getFinalResultType());
			pList.add(p.getDirector());
//			pList.add(p.getGender());
//			pList.add(DatetimeTool.getYearMonthDateString(p.getBirthday()));
//			pList.add(p.getTitle());
//			pList.add(p.getDepartment());
//			pList.add(p.getJob());
//			pList.add(p.getEducation());
//			pList.add(p.getDegree());
//			pList.add(p.getForeign());
//			pList.add(p.getAddress());
//			pList.add(p.getPostcode());
//			pList.add(p.getPhone());
//			pList.add(p.getMobile());
//			pList.add(p.getEmail());
//			pList.add(p.getIdcard());
//			pList.add(p.getMembers());
//			pList.add("");
			
			List<String> expertIds = grsMap.get(p.getId());
			if (null != expertIds) {
				for (String expertId : expertIds) {
					Expert expert = expMap.get(expertId);
					pList.add(univMap.get(expert.getUniversityCode()));
					pList.add(expert.getUniversityCode());
					pList.add(expert.getName());
					pList.add(expert.getNumber());
				}
			}
			
			Object obj[] = pList.toArray(new Object[0]);
			for (int i = 0; i < obj.length; i++){
				if (obj[i] == null){
					obj[i] = "";
				}
			}
			v.add(obj);
		}
		HSSFExport.commonExportData(oo, v, response);
		
		//System.out.println("预处理用时: " + (mid.getTime() - begin.getTime()) + "ms");
		//System.out.println("导出用时: " + (new Date().getTime() - mid.getTime()) + "ms");
	}
	
	
	public void exportProject(int exportAll) throws Exception {
		//Date begin = new Date();
		Map session = ActionContext.getContext().getSession();
		String yearString = session.get("defaultYear").toString();
		HttpServletResponse response = ServletActionContext.getResponse();
		Vector v = new Vector();
		String oo[] = new String[this.oo.length - 20];
		for (int i = 0; i < oo.length; i++) {
			oo[i] = this.oo[i];
		}
		oo[0] = yearString + "年度一般项目一览表";

		HashMap<String, String> discMap = new HashMap<String, String>();
		int year = Integer.parseInt(yearString);
		Map application = ActionContext.getContext().getApplication();
		list = (List) application.get("disall");
		for (Object object : list) {
			SystemOption so = (SystemOption) object;
			discMap.put(so.getCode(), so.getName());
		}
		
		if (exportAll != 0){
			list = baseService.query("select p from ProjectApplication p where p.type = 'general' and p.year = ? order by p.id asc", year);
		} else {
			list = baseService.query((String) session.get("HQL4ProjectExport"), (Map)session.get("Map4ProjectExport"));
		}
		
		//Date mid = new Date();
		System.out.println(list.size());
		for (Object object : list) {
			ProjectApplication p = (ProjectApplication) object;
//			System.out.println("prj ID: " + p.getId());
			List pList = new ArrayList<Object>();
			pList.add(p.getAuditStatus());
			pList.add(p.getFile());
			pList.add(p.getNumber());
			pList.add(p.getProjectName());
			pList.add(p.getProjectType());
			pList.add(p.getDisciplineType());
			pList.add(DatetimeTool.getYearMonthDateString(p.getApplyDate()));
			
			String discString = "";//学科代码+学科名称
			if(null != p.getDiscipline() && !p.getDiscipline().isEmpty()){
				String disc[] = p.getDiscipline().split("; ");
				for (String s : disc) {
					discString += (discString.isEmpty() ? "" : "、") + ((discMap.get(s)==null) ? "" : discMap.get(s)) + "(" + s + ")";
				}
			}
			pList.add(discString);
			pList.add(p.getResearchType());
			pList.add(DatetimeTool.getYearMonthDateString(p.getPlanEndDate()));
			pList.add(p.getApplyFee());
			pList.add(p.getOtherFee());
			pList.add(p.getFinalResultType());
			pList.add(p.getUniversityCode());
			pList.add(p.getUniversityName());
			pList.add(p.getDirector());
			pList.add(p.getGender());
			pList.add(DatetimeTool.getYearMonthDateString(p.getBirthday()));
			pList.add(p.getTitle());
			pList.add(p.getDepartment());
			pList.add(p.getJob());
			pList.add(p.getEducation());
			pList.add(p.getDegree());
			pList.add(p.getForeign());
			pList.add(p.getAddress());
			pList.add(p.getPostcode());
			pList.add(p.getPhone());
			pList.add(p.getMobile());
			pList.add(p.getEmail());
			pList.add(p.getIdcard());
			pList.add(p.getMembers());
			pList.add("");
			pList.add("");
			pList.add("");
			pList.add("");
			
			Object obj[] = pList.toArray(new Object[0]);
			for (int i = 0; i < obj.length; i++){
				if (obj[i] == null){
					obj[i] = "";
				}
			}
			v.add(obj);
		}
		System.out.println("===== vector : " + v.size());
		HSSFExport.commonExportData(oo, v, response);
		
		//System.out.println("预处理用时: " + (mid.getTime() - begin.getTime()) + "ms");
		//System.out.println("导出用时: " + (new Date().getTime() - mid.getTime()) + "ms");
	}
	
	@Transactional
	public void exportMatchUpdate(Integer year, Date start, Date end) throws Exception {
		List<String []> v = new ArrayList();
		List<Object[]> prus = baseService.query(
				"select " +
				"	pru.project.id, " +
				"	pru.project.projectName, " +
				"	pru.project.number, " +
				"	pru.reviewer.name, " +
				"	university.name, " +
				"	university.code, " +
				"	pru.isAdd, " +
				"	pru.reviewer.number " +
				"from " +
				"	ProjectApplicationReviewUpdate pru, " +
				"	University university " +
				"where pru.type = 'general' and " +
				"	university.code = pru.reviewer.universityCode and " +
				"	pru.year = ? and " +
				"	pru.matchTime >= ? and " +
				"	pru.matchTime <= ? " +
				"order by pru.project.id, pru.matchTime", year, start, end);
		HashSet<String> originReviewers = null;
		HashSet<String> updatedReviewers = null;
		HashSet<Integer> updatedReviewerNumbers = new HashSet<Integer>();
		for (int i = 0; i < prus.size(); i++) {
			String prevProjectId = (String) (i > 0 ? prus.get(i - 1)[0] : null);
			String curProjectId = (String) prus.get(i)[0];
			String nextProjectId = (String) (i < prus.size() - 1 ? prus.get(i + 1)[0] : null);
			String projectName = (String) prus.get(i)[1];
			String projectNumber = (String) prus.get(i)[2];
			String reviewerName = (String) prus.get(i)[3];
			String reviewerUnivName = (String) prus.get(i)[4];
			String reviewerUnivCode = (String) prus.get(i)[5];
			Integer isAdd = (Integer) prus.get(i)[6];
			Integer reviewerNumber = (Integer) prus.get(i)[7];
			
			String curReviewer = reviewerNumber + "/" + reviewerName + "(" + reviewerUnivCode + "/" + reviewerUnivName + ")";
//			System.out.println(curProjectId + " " + projectName + " " + curReviewer + " - " + isAdd);
			
			if (prevProjectId == null || !curProjectId.equals(prevProjectId)) {
				originReviewers = new HashSet<String>();
				updatedReviewers = new HashSet<String>();
			}
			if (isAdd == 1) {
				updatedReviewers.add(curReviewer);
				updatedReviewerNumbers.add(reviewerNumber);
			} else if (updatedReviewers.contains(curReviewer)) {
				updatedReviewers.remove(curReviewer);
				updatedReviewerNumbers.remove(reviewerNumber);
			} else {
				originReviewers.add(curReviewer);
			}
			if (nextProjectId == null || !curProjectId.equals(nextProjectId)) {
				String[] row = new String[4];
				row[0] = projectNumber;
				row[1] = projectName;
				
				String originReviewersInfo = "";
				for (String reviewer : originReviewers) {
					if (updatedReviewers.contains(reviewer)) {
						continue;
					}
					if (!originReviewersInfo.isEmpty()) {
						originReviewersInfo += "; ";
					}
					originReviewersInfo += reviewer;
				}
				row[2] = originReviewersInfo;
				
				String updatedReviewersInfo = "";
				for (String reviewer : updatedReviewers) {
					if (originReviewers.contains(reviewer)) {
						continue;
					}
					if (!updatedReviewersInfo.isEmpty()) {
						updatedReviewersInfo += "; ";
					}
					updatedReviewersInfo += reviewer;
				}
				row[3] = updatedReviewersInfo;
				
				if (!row[2].isEmpty() || !row[3].isEmpty()) {
					v.add(row);
				}
			}
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String[] tableHeader = new String[]{
			"项目编号",
			"项目名称",
			"删除评审专家",
			"新增评审专家"
		};
		
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet1 = wb.createSheet();
		wb.setSheetName(0, "项目专家匹配更新表");
		sheet1.setDefaultRowHeightInPoints(13);
		sheet1.setDefaultColumnWidth(18);
		//设置页脚
		HSSFFooter footer = sheet1.getFooter();
		footer.setRight("Page " + HSSFFooter.page() + " of " + HSSFFooter.numPages());
		//设置样式 表头
		HSSFCellStyle style1 = wb.createCellStyle();
		style1.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		HSSFFont font1 = wb.createFont();
		font1.setFontHeightInPoints((short) 10);
		font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		style1.setFont(font1);
		//设置样式 正式数据
		HSSFCellStyle style2 = wb.createCellStyle(); 
		style2.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		style2.setWrapText(false);
		
		HSSFCell tmpCell;
		HSSFRow rowTime1 = sheet1.createRow(0);

		tmpCell = rowTime1.createCell(0);
		tmpCell.setCellStyle(style1);
		tmpCell.setCellValue("起始时间");
		
		tmpCell = rowTime1.createCell(1);
		tmpCell.setCellStyle(style1);
		tmpCell.setCellValue("截止时间");
		
		HSSFRow rowTime2 = sheet1.createRow(1);

		tmpCell = rowTime2.createCell(0);
		tmpCell.setCellStyle(style2);
		tmpCell.setCellValue(sdf.format(start));
		
		tmpCell = rowTime2.createCell(1);
		tmpCell.setCellStyle(style2);
		tmpCell.setCellValue(sdf.format(end));

		//设置表头
		HSSFRow row1 = sheet1.createRow(3);
		row1.setHeightInPoints(13);
		for(int i = 0; i < tableHeader.length; i++){
			HSSFCell cell1 = row1.createCell(i);
			cell1.setCellValue(tableHeader[i]);
			cell1.setCellStyle(style1);
		}
		//填充数据
		for(int j = 0; j < v.size(); j++){
			HSSFRow row2 = sheet1.createRow(j + 4);
			for(int k = 0; k < v.get(j).length; k++){
				HSSFCell cell = row2.createCell(k);
				cell.setCellValue(v.get(j)[k]);
				cell.setCellStyle(style2);
			}
		}
		
		//新增一个标签页“专家更新表”，包括“匹配更新表”标签页中“新增评审专家”的所有专家清单，具体内容按导出专家表的格式进行组织。
		List<Object[]> updatedReviewersInfoList = new ArrayList<Object[]>();
		List<Object[]> tmpUpdateReviewersInfoList = new ArrayList<Object[]>();
		if (updatedReviewerNumbers.size() != 0) {
			tmpUpdateReviewersInfoList = baseService.query("select expert.number, expert.name, expert.universityCode, expert.mobilePhone, expert.email, expert.discipline, expert.id from Expert expert");
			for (Object[] objects : tmpUpdateReviewersInfoList) {
				if (updatedReviewerNumbers.contains((Integer) objects[0])) {
					updatedReviewersInfoList.add(objects);
				}
			}
		}
		String[] tableHeader1 = new String[]{
			"专家代码",
			"专家姓名",
			"单位代码",
			"单位名称",
			"手机",
			"邮箱",
			"一级学科代码",
			"一级学科名称",
			"二级学科代码",
			"二级学科名称",
			"三级学科代码",
			"三级学科名称"
		};
		sheet1 = wb.createSheet();
		wb.setSheetName(1, "专家更新表");
		sheet1.setDefaultRowHeightInPoints(13);
		sheet1.setDefaultColumnWidth(18);
		//设置页脚
		footer = sheet1.getFooter();
		footer.setRight("Page " + HSSFFooter.page() + " of " + HSSFFooter.numPages());
		//设置样式 表头
		style1 = wb.createCellStyle();
		style1.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		font1 = wb.createFont();
		font1.setFontHeightInPoints((short) 10);
		font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		style1.setFont(font1);
		//设置样式 正式数据
		style2 = wb.createCellStyle(); 
		style2.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		style2.setWrapText(false);
		
		//设置表头
		row1 = sheet1.createRow(0);
		row1.setHeightInPoints(13);
		for(int i = 0; i < tableHeader1.length; i++){
			HSSFCell cell1 = row1.createCell(i);
			cell1.setCellValue(tableHeader1[i]);
			cell1.setCellStyle(style1);
		}
		Map application = ActionContext.getContext().getApplication();
		HashMap<String, String> univMap = (HashMap<String, String>) application.get("univCodeNameMap");
		HashMap<String, String> discMap = (HashMap<String, String>) application.get("disMap");
		List<String []> z = new ArrayList();
		for (Object[] object : updatedReviewersInfoList) {
			List eList = new ArrayList<String>();
			eList.add(object[0].toString());//专家代码
			eList.add(object[1].toString());//专家姓名
			eList.add(object[2].toString());//高校代码
			eList.add(univMap.get(object[2].toString()));//高校名称
			if(object[3] == null) {
				eList.add("");
			} else {
				String mobilePhone = (String) object[3].toString();//手机
				if(mobilePhone.indexOf(";") == -1) {
					eList.add(mobilePhone);
				} else {
					eList.add(mobilePhone.substring(0, mobilePhone.indexOf(";")));					
				}
			}
			if(object[4] == null) {
				eList.add("");
			} else {
				String email = (String) object[4].toString();//邮箱
				if(email.indexOf(";") == -1) {
					eList.add(email);
				} else {
					eList.add(email.substring(0, email.indexOf(";")));					
				}
			}
			String discipline;//学科代码
			if (object[5] == null) {
				discipline = "";
			} else {
				discipline = (String) object[5].toString();
			}
			String disc[] = discipline.split("; ");
			Set<String> discSet = new HashSet<String>();
			Set<String> tmpSet = new HashSet<String>();
			for (String d : disc) {
				discSet.add(d);
			}
			String d3Code="", d3Name="", d2Code="", d2Name="", d1Code="", d1Name="";
			for(Iterator i = discSet.iterator(); i.hasNext();){ 
				String st = (String)i.next();
				if (st.length() == 7)
					tmpSet.add(st.substring(0, 5));
				if (st.length() >= 5)
					tmpSet.add(st.substring(0, 3));
			}
			discSet.addAll(tmpSet);
			for(Iterator i = discSet.iterator(); i.hasNext();){ 
				String st = (String)i.next();
				if (st.length() == 7){
					d3Code += (d3Code.isEmpty()?"":"; ") + st;
					d3Name += (d3Name.isEmpty()?"":"、") + discMap.get(st);
				} else if (st.length() == 5){
					d2Code += (d2Code.isEmpty()?"":"; ") + st;
					d2Name += (d2Name.isEmpty()?"":"、") + discMap.get(st);
				} else if (st.length() == 3){
					d1Code += (d1Code.isEmpty()?"":"; ") + st;
					d1Name += (d1Name.isEmpty()?"":"、") + discMap.get(st);
				}
			}
			eList.add(d1Code);
			eList.add(d1Name);
			eList.add(d2Code);
			eList.add(d2Name);
			eList.add(d3Code);
			eList.add(d3Name);
			
			String obj[] = new String[13];
			for (int i = 0; i < eList.size(); i++){
				if (eList.get(i) == null){
					obj[i] = "";
				}else {
					obj[i] = eList.get(i).toString();
				}
			}
			z.add(obj);
		}
		//填充数据
		for(int j = 0; j < z.size(); j++){
			HSSFRow row2 = sheet1.createRow(j + 1);
			for(int k = 0; k < z.get(j).length; k++){
				HSSFCell cell = row2.createCell(k);
				cell.setCellValue(z.get(j)[k]);
				cell.setCellStyle(style2);
			}
		}
		
		Map session = ActionContext.getContext().getSession();
		String yearString = session.get("defaultYear").toString();
		String title =  yearString + "年度一般项目专家匹配更新表";
		HttpServletRequest request = ServletActionContext.getRequest();		
		if(request.getHeader("USER-AGENT").toLowerCase().indexOf("firefox") >= 0) {
			title = new String(title.getBytes("UTF-8"),"ISO8859-1");				
		} else {
			title = new String(title.getBytes("gb2312"), "iso-8859-1");					
		}		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/vnd.ms-excel");		
		response.setHeader("Content-Disposition","attachment;filename=" + title + ".xls");
		response.setHeader("Pragma", "no-cache");
		wb.write(response.getOutputStream());
		response.getOutputStream().flush();
		response.getOutputStream().close();
	}

	public StringBuffer validate(File xls) throws Exception {
		return null;
	}

	public void imprt() throws Exception {
	}

}
