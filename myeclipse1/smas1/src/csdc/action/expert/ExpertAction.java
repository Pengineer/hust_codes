package csdc.action.expert;

import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;

import oracle.net.aso.s;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionContext;

import csdc.action.BaseAction;
import csdc.bean.Expert;
import csdc.bean.ProjectInfos;
import csdc.bean.SystemOption;
import csdc.bean.University;
import csdc.bean.common.Visitor;
import csdc.service.IExpertService;
import csdc.service.IGeneralService;
import csdc.service.webService.client.SmdbClient;
import csdc.tool.execution.finder.ExpertFinder;
import csdc.tool.info.GlobalInfo;
import csdc.tool.info.ImporterInfo;
import csdc.tool.tableKit.imp.ExpertKit;

/**
 * 专家管理
 * @author 徐涵
 * @author 龚凡
 * @version 1.0 2010.06.11
 * @version 1.1 2010.06.18
 */
@SuppressWarnings("unchecked")
public class ExpertAction extends BaseAction {
	
	private static final long serialVersionUID = 1L;
	private static final String HQL = "select expert.id, expert.name, u.name, expert.specialityTitle, expert.discipline, expert.reason, expert.isKey from Expert expert left join expert.applicationReview pr with pr.year = :defaultYear and  pr.type='general', University u where expert.universityCode = u.code ";
	private static final String HQLG = " group by expert.id, expert.name, u.name, expert.specialityTitle, expert.discipline, expert.reason, expert.isKey";
//	private static final String HQL = "select expert.id, expert.name, u.name, expert.specialityTitle, expert.discipline, expert.remark, expert.warning, COUNT(pr.id), expert.reason, expert.isKey from Expert expert left join expert.generalReviewer pr with pr.year = :defaultYear, University u where expert.universityCode = u.code ";
//	private static final String HQLG = " group by expert.id, expert.name, u.name, expert.specialityTitle, expert.discipline, expert.remark, expert.warning, expert.reason, expert.isKey";
	private static final String HQLC = "select COUNT(*) from Expert expert, University u where expert.universityCode = u.code ";
	private static final String DATE_FORMAT = "yyyy-MM-dd";// 列表时间格式
	private static final String PAGENAME = "expertPage";
	private static final String PAGE_BUFFER_ID = "expert.id";//缓存id
	private static final String column[] = {
			"expert.name",
			"u.name",
			"expert.specialityTitle",
			"expert.discipline",
			"expert.reason"
	};// 排序用的列
	private static Boolean busy = false;//添加同步重点人判忙标志，true：正在作业中；false：空闲中
	private IExpertService expertService;
	private IGeneralService generalService;
	private File xls;// 专家数据文件
	private Expert expert;// 专家对象
	private String expertid;// 查看
	private List<String> expertids;
	private String universityName;// 高校名称
	private int expertType;// 专家类型标识0内部专家，1外部专家
	private int isReviewer;// 是否参评0不参评，1参评
	private String projectId;
	private int exportAll;		//是否导出全部至xls
	private int countReviewer;	//是否导出评审项目计数
	private String levelOneDiscipline;	//专家的一级学科，用分号隔开
	private int isNotReviewAll;//是否退评所选专家当前年度评审项目的信息,0不退评，1退评
	private String notReviewerReason;	//退评原因
	private int viewType; //查看方式 0.默认 1.popView
	private int isKey;//是否重点人
	//导出
	private String fileFileName;//导出文件名
	private List<SystemOption> disList; //一级学科列表
	
	private String oldMoeProjectInfos;
	private String oldNationalProjectInfos;
	private List<ProjectInfos> newMoeProjectInfos;
	private List<ProjectInfos> newNationalProjectInfos;
	
	private List<String[]> personToDeal;
	
	@Autowired
	private ExpertKit expertKit;
	@Autowired
	private ExpertFinder expertFinder;
	/**
	 * 进入专家导入页面
	 * @return
	 */
	public String toImportExpert(){
		Visitor visitor = (Visitor) ActionContext.getContext().getSession().get("visitor");
		if (visitor != null && visitor.getUser().getIssuperuser() == 0) {
			request.setAttribute("errorInfo", "您无权进行此操作");
			return INPUT;
		}
		return SUCCESS;
	}

	/**
	 * 导入专家
	 * @return SUCCESS转向专家列表
	 * @throws Exception
	 */
	public String importExpert() throws Exception {
		Visitor visitor = (Visitor) ActionContext.getContext().getSession().get("visitor");
		if (visitor != null && visitor.getUser().getIssuperuser() == 0) {
			request.setAttribute("errorInfo", "您无权进行此操作");
			return INPUT;
		}
		StringBuffer err = expertKit.validate(xls);
		if (err.length() > 0){
			request.setAttribute("errorInfo", err);
			return INPUT;
		}
		expertKit.imprt();
		return SUCCESS;
	}

	/**
	 * 导入校验
	 */
	public void validateImportExpert() {
		if (xls == null) {
			this.addFieldError("GlobalInfo.ERROR_INFO", "请选择上传的文件");
		}
	}

	/**
	 * 导出专家模板
	 * @return SUCCESS
	 * @throws Exception
	 */
	public String expertTemplate() throws Exception {
		expertKit.exprtTemplate();
		return SUCCESS;
	}

	/**
	 * 弹出层导出
	 * @return
	 */
	public String popExport(){
		//获取所有的一级学科代码列表，导出弹出层学科代码checkbox的显示
		if(session.get("disList") == null) {
			disList = generalService.query("select s from SystemOption s where length(s.code) = 3 and s.standard like '%2009%' order by s.code asc ");
			session.put("disList", disList);
		} else {
			disList = (List<SystemOption>) session.get("disList");
		}
		return SUCCESS;
	}
	
	/**
	 * 导出专家
	 * @return
	 * @throws Exception
	 */
	public String exportExpert() throws Exception {
		expertKit.exportExpert(exportAll, countReviewer, request);
		return SUCCESS;
	}
	
	
	/**
	 * 导出MOEExpert2012
	 * @return
	 */
	public String exportExcel(){
//		String zipPath = expertService.createExpertZip("10001");
		return SUCCESS;
	}
	
	/**
	 * 导出excel
	 * @return 输入流
	 */
	public InputStream getDownloadFile() throws Exception{
		//导出的Excel文件名
		fileFileName = "教育部人文社会科学研究项目评审专家信息(MOEExpert2012).xls";
		fileFileName = new String(fileFileName.getBytes(), "ISO8859-1");
		return expertService.exportExcel(null);
//		return expertService.exportExcel("10001");
//		fileFileName = "2012年 未上报专家的高校名单.xls";
//		fileFileName = new String(fileFileName.getBytes(), "ISO8859-1");
//		return expertService.exportExcel();
	}
	
	
	/**
	 * 列表和初级检索条件
	 */
	@Override
	public Object[] simpleSearchCondition() {
		String t_hql = (expertType == 0 ? " expert.expertType = 1 " : " expert.expertType = 2 ") + " and expert.isReviewer = " + isReviewer;
		keyword = (keyword == null) ? "" : keyword.toLowerCase().trim();// 预处理关键字
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		hql.append(HQL);
		hql.append(" and ");
		hql.append(t_hql);
		map.put("defaultYear", session.get("defaultYear"));
		map.put("isReviewer", isReviewer);
		map.put("expertType", expertType + 1);
		String s_hql = "";
		if (keyword != null && !keyword.isEmpty()) {// 如果关键字为空，不加搜索条件
			hql.append(" and ");
			if (searchType == 1) {
				s_hql = "LOWER(expert.name) like :keyword";
				map.put("keyword", "%" + keyword + "%");
			} else if (searchType == 2) {
				s_hql = "LOWER(u.name) like :keyword";
				map.put("keyword", "%" + keyword + "%");
			} else if (searchType == 3) {
				s_hql = "LOWER(expert.specialityTitle) like :keyword";
				map.put("keyword", "%" + keyword + "%");
			} else if (searchType == 4) {
				s_hql = "LOWER(expert.discipline) like :keyword";
				map.put("keyword", "%" + keyword + "%");
			} else if (searchType == 5) {
				List<Object> dislist = generalService.getDisciplineCode(keyword);
				if (dislist != null && !dislist.isEmpty()) {
					s_hql += "(";
					for (int i = 0; i < dislist.size(); i++) {
						s_hql += "LOWER(expert.discipline) like '%" + dislist.get(i) + "%' or ";
					}
					s_hql = s_hql.substring(0, s_hql.length() - 4) + ")";
				} else {
					s_hql = "LOWER(expert.discipline) like :keyword";// 当没有找到相应的学科代码时，以汉字填补，只是为了输出为空
					map.put("keyword", "%" + keyword + "%");
				}
			} else if (searchType == 6) {
				s_hql = "LOWER(expert.reason) like :keyword";
				map.put("keyword", "%" + keyword + "%");
			} else {
				s_hql = "(LOWER(expert.name) like :keyword or LOWER(u.name) like :keyword or LOWER(expert.specialityTitle) like :keyword or LOWER(expert.reason) like :keyword";
				map.put("keyword", "%" + keyword + "%");
				List<Object> dislist = generalService.getDisciplineCode(keyword);
				if (dislist != null && !dislist.isEmpty()) {
					for (int i = 0; i < dislist.size(); i++) {
						s_hql += " or LOWER(expert.discipline) like '%" + dislist.get(i) + "%'";
					}
				} else {
					s_hql += " or LOWER(expert.discipline) like :keyword";// 当没有找到相应的学科代码时，以汉字填补，只是为了输出为空
					map.put("keyword", "%" + keyword + "%");
				}
				s_hql += ")";
			}
		}
		hql.append(s_hql);
		hql.append(HQLG);
		
		//为列表页面导出专家表准备条件
		StringBuffer c_hql = new StringBuffer();
		c_hql.append(HQLC);
		c_hql.append(" and ");
		c_hql.append(t_hql);
		if(s_hql != ""){
			c_hql.append(" and ");
			c_hql.append(s_hql);
		}
		StringBuffer HQL4ExpertExport = new StringBuffer();
		HQL4ExpertExport.append(c_hql.toString());
		HQL4ExpertExport.append(" order by expert.name asc, expert.id asc");
		String tmp1 = HQL4ExpertExport.toString();
		tmp1 = tmp1.replace(" count(pr.reviewer.id) asc,", " expert.name asc,");
		tmp1 = tmp1.replace(" count(pr.reviewer.id) desc,", " expert.name desc,");
//		String tmp = expertService.createQueryHql(tmp1, map);
		String tmp = tmp1;
		session.put("HQL4ExpertExport", tmp.replaceAll("COUNT\\(\\*\\)", "expert.number, expert.name, expert.universityCode, expert.mobilePhone, expert.email, expert.discipline"));
		session.put("Map4ExpertExport", map);
		
		// 调用初级检索功能
		return new Object[]{
			hql.toString(),
			map,
			0,
			null,
			c_hql.toString()
		};
	}
	
	/**
	 * 处理列表数据，如有需要，各模块可以重写此方法，本方法默认仅格式化时间。
	 */
	public void pageListDealWith() {
		//获取学科名称与学科代码的映射
		Map application = ActionContext.getContext().getApplication();
		List disList = (List) application.get("disall");//获取所有学科对象的list
		Map<String, String> disname2discode = new HashMap<String, String>();//学科名称与学科代码的映射
		for (Iterator iterator = disList.iterator(); iterator.hasNext();) {
			SystemOption so = (SystemOption) iterator.next();
			String disname = so.getName();
			String discode = so.getCode();
			disname2discode.put(discode, disname);
		}
		// 处理之后的列表数据
		laData = new ArrayList();
		Object[] item;// 缓存查询结果一行
		SimpleDateFormat dateformat = new SimpleDateFormat(dateFormat());// 时间格式化对象
		String datestr;// 格式化之后的时间字符串
		//学科代码
		String disciplineCode = "";
		//组装后的值：学科代码+学科名称
		String value = "";
		
		// 遍历初始查询列表数据，按照指定格式，格式化其中的时间字段，其它字段转化为字符串
		for (Object p : pageList) {
			item = (Object[]) p;
			for (int i = 0; i < item.length; i++) {
				if (item[i] == null) {// 如果字段值为空，则以""替换
					item[i] = "";
				} else {// 如果字段值非空，则做进一步判断
					if (item[i] instanceof Date) {// 如果字段为时间对象，则按照子类定义的时间格式格式化
						datestr = dateformat.format((Date) item[i]);
						item[i] = datestr;
					}
				}
			}
			if (item[4] != null) {
				disciplineCode = item[4].toString();//学科代码
				value = generalService.getDisciplineInfo(disname2discode, disciplineCode);
			}
			item[4] = value;
			laData.add(item);// 将处理好的数据存入laData
		}
	}
	
	public void validateSimpleSearchCondition() {
		if (expertType != 0 && expertType != 1) {
			this.addFieldError("GlobalInfo.ERROR_INFO", "无效的专家类型");
		}
		if (isReviewer != 0 && isReviewer != 1) {
			this.addFieldError("GlobalInfo.ERROR_INFO", "无效的参评状态");
		}
	}
	
	/**
	 * 进入查看页面
	 * @return
	 */
	public String toView(){
		if(viewType == 1) {
			return "popView";
		} else {
			return "normalView";
		}
	}
	
	/**
	 * 查看专家信息
	 * @return
	 * @throws DocumentException 
	 */
	public String view() throws DocumentException{
		List<Object[]> generalList = null;	// 一般项目列表
		List<Object[]> instpList = null;// 基地项目列表
		expert = (Expert) baseService.query(Expert.class, entityId);
		if(expert == null){
			jsonMap.put(GlobalInfo.ERROR_INFO, "该专家已不存在");
			return INPUT;
		}else{
			University u = (University) baseService.query(University.class, expert.getUniversityCode());
			if (u != null) {
				universityName = u.getName();
			}
			Map map = new HashMap();
			map.put("expertid", entityId);
			map.put("defaultYear", session.get("defaultYear"));
			String hql4General = "select p.id, p.projectName, p.projectType, p.director, u.name, p.discipline, pr.isManual, pr.isManual from ProjectApplication p, University u, ProjectApplicationReview pr where p.type='general' and pr.type='general' and p.year = :defaultYear and p.universityCode = u.code and p.id = pr.project.id and pr.reviewer.id = :expertid order by p.projectName asc";
			String hql4Instp = "select p.id, p.projectName, p.projectType, p.director, u.name, p.discipline, pr.isManual, pr.isManual from ProjectApplication p, University u, ProjectApplicationReview pr where p.type='instp' and pr.type='instp' and p.year = :defaultYear and p.universityCode = u.code and p.id = pr.project.id and pr.reviewer.id = :expertid order by p.projectName asc";
			generalList = baseService.query(hql4General, map);
			instpList = baseService.query(hql4Instp, map);
			if (!generalList.isEmpty()) {
				for (Object[] obj : generalList) {
					if (obj[5] != null) {
						//组装成学科代码加学科名称
						obj[5] = expertService.getDiscipline(obj[5].toString());
					}
				}
			}
			if (!instpList.isEmpty()) {
				for (Object[] obj : instpList) {
					if (obj[5] != null) {
						//组装成学科代码加学科名称
						obj[5] = expertService.getDiscipline(obj[5].toString());
					}
				}
			}
//			if (pageList != null && !pageList.isEmpty()) {
//				Object[] o;
//				String value = "";// 项目学科信息信息
//				for (int i = 0; i < pageList.size(); i++) {
//					o = (Object[]) pageList.get(i);
//					if (o[5] != null) {
//						value = expertService.getDiscipline(o[5].toString());
//					}
//					o[5] = value;//组装成学科代码加学科名称
//				}
//			}
			if (expert.getDiscipline() != null && !expert.getDiscipline().isEmpty()) {
				expert.setDiscipline(expertService.getDiscipline(expert.getDiscipline()));
			}
			
			/**
			 * 参评年份作了符号转换处理
			 */
/*			if(expert.getGeneralReviewYears() != null && !expert.getGeneralReviewYears().isEmpty()) {
				expert.setGeneralReviewYears(expert.getGeneralReviewYears().replace(" ", ""));
				expert.setGeneralReviewYears(expert.getGeneralReviewYears().replace(';', '；'));
			}*/
			//找出该专家的一级学科，供专家树使用
			Set<String> levelOneDiscipline = new TreeSet<String>();
			String[] splittedDisc = expert.getDiscipline() == null ? new String[0] : expert.getDiscipline().split("\\D+");
			for (String disc : splittedDisc) {
				if (disc.length() > 0) {
					levelOneDiscipline.add(disc.substring(0, 3));
				}
			}
			this.levelOneDiscipline = new String();
			for (Iterator iterator = levelOneDiscipline.iterator(); iterator.hasNext();) {
				String disc = (String) iterator.next();
				if (!this.levelOneDiscipline.isEmpty()) {
					this.levelOneDiscipline += ";";
				}
				this.levelOneDiscipline += disc;
			}
		}
		
		//放置承担教育部项目情况信息
		/**
		 * 原则：
		 * 1.数据库中没有old数据也没有new数据，则不放置
		 * 2.数据库中只要有new数据，则只放置new数据
		 * 3.数据库中没有new数据才考虑放置old数据
		 */
		if (expert.getMoeProject() != null) {
			String info = expert.getMoeProject();
			Document document = DocumentHelper.parseText(info);//根据数据库中数据生成“承担国家社科项目情况”xml节点树
			Element rootElement = document.getRootElement();
			Element newInfoElement = rootElement.element("newProjectInfos");
			if (newInfoElement != null) {
				List<Element> newProjects = newInfoElement.elements();
				newMoeProjectInfos = new ArrayList<ProjectInfos>();
				for (Element newProjectElement : newProjects) {
					ProjectInfos projectInfos = new ProjectInfos();
					projectInfos.setProjectNum(newProjectElement.elementText("projectNum"));
					projectInfos.setProjectName(newProjectElement.elementText("projectName"));
					projectInfos.setProjectType(newProjectElement.elementText("projectType"));
					newMoeProjectInfos.add(projectInfos);
				}
				jsonMap.put("newMoeProjectInfos", newMoeProjectInfos);
			}else{
				Element oldInfoElement = rootElement.element("oldProjectInfos");
				if (oldInfoElement != null) {
					jsonMap.put("oldMoeProjectInfos", oldInfoElement.getText());
				}else{
					jsonMap.put("oldMoeProjectInfos", "");
				}
				//前台需要newMoeProjectInfos，作为判断依据，所以newMoeProjectInfos需要传递空
				jsonMap.put("newMoeProjectInfos", "");
			}	
		}else{
			jsonMap.put("newMoeProjectInfos", "");
			jsonMap.put("oldMoeProjectInfos", "");
		}
		
		//放置承担国家社科项目情况信息
		if (expert.getNationalProject() !=  null) {
			String info = expert.getNationalProject();
			Document document = DocumentHelper.parseText(info);//根据数据库中数据生成“承担国家社科项目情况”xml节点树
			Element rootElement = document.getRootElement();
			Element newInfoElement = rootElement.element("newProjectInfos");
			if (newInfoElement != null) {
				List<Element> newProjects = newInfoElement.elements();
				newNationalProjectInfos = new ArrayList<ProjectInfos>();
				for (Element newProjectElement : newProjects) {
					ProjectInfos projectInfos = new ProjectInfos();
					projectInfos.setProjectNum(newProjectElement.elementText("projectNum"));
					projectInfos.setProjectName(newProjectElement.elementText("projectName"));
					projectInfos.setProjectType(newProjectElement.elementText("projectType"));
					newNationalProjectInfos.add(projectInfos);
				}
				jsonMap.put("newNationalProjectInfos", newNationalProjectInfos);
			}else{
				Element oldInfoElement = rootElement.element("oldProjectInfos");
				if (oldInfoElement != null) {
					jsonMap.put("oldNationalProjectInfos", oldInfoElement.getText());
				}else{
					jsonMap.put("oldNationalProjectInfos","");
				}
				//前台需要newNationalProjectInfos，作为判断依据，所以newNationalProjectInfos需要传递空
				jsonMap.put("newNationalProjectInfos", "");
			}	
		}else{
			jsonMap.put("newNationalProjectInfos", "");
			jsonMap.put("oldNationalProjectInfos","");
		}
		jsonMap.put("levelOneDiscipline", levelOneDiscipline);
		jsonMap.put("universityName", universityName);
		jsonMap.put("generalList", generalList);
		jsonMap.put("instituteList", instpList);
		jsonMap.put("expert", expert);
		return SUCCESS;
	}

	/**
	 * 查看校验
	 */
	public void validateView() {
		if (entityId == null || entityId.isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "请选择要查看的专家");
		}
	}

	/**
	 * 进入添加专家页面
	 * @return	SUCCESS进入添加页面
	 */
	public String toAdd() {	
		return SUCCESS;
	}
	
	public void validateToAdd() {
		if (expertType != 0 && expertType != 1) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "无效的专家类型");
		}
		if (isReviewer != 0 && isReviewer != 1) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "无效的参评状态");
		}
	}
	
	/**
	 * 添加专家
	 * @return
	 */
	public String add(){
		expert.setWarning(null);
		expert.setExpertType(expertType+1);
		List<Integer> numberList = baseService.query("select max(e.number) from Expert e");
		Integer number = numberList.get(0) + 1;
		expert.setNumber(number);
		//添加承担教育部项目情况
		Document document = DocumentHelper.createDocument();
		Element rootElement = document.addElement("moeProjectData");
		if (newMoeProjectInfos != null && newMoeProjectInfos.size() > 0) {
			Element newProjectInfos = rootElement.addElement("newProjectInfos");
			for(ProjectInfos projectInfos : newMoeProjectInfos){
				Element newProjectInfo = newProjectInfos.addElement("projectInfo");
				Element numElement = newProjectInfo.addElement("projectNum");
				numElement.addText(projectInfos.getProjectNum());
				Element nameElement = newProjectInfo.addElement("projectName");
				nameElement.addText(projectInfos.getProjectName());
				Element typeElement = newProjectInfo.addElement("projectType");
				typeElement.addText(projectInfos.getProjectType());
			}
			String moeProjectInfo = document.asXML();
			int a = moeProjectInfo.indexOf("<moeProjectData>");
			moeProjectInfo = moeProjectInfo.substring(a);
			expert.setMoeProject(moeProjectInfo);
		} else{
			expert.setMoeProject("");
		}		
		//添加承担国家社科项目情况
		document = DocumentHelper.createDocument();
		rootElement = document.addElement("nationalProjectData");
		if (newNationalProjectInfos != null && newNationalProjectInfos.size() > 0) {
			Element newProjectInfos = rootElement.addElement("newProjectInfos");
			for(ProjectInfos projectInfos : newNationalProjectInfos){
				Element newProjectInfo = newProjectInfos.addElement("projectInfo");
				Element numElement = newProjectInfo.addElement("projectNum");
				numElement.addText(projectInfos.getProjectNum());
				Element nameElement = newProjectInfo.addElement("projectName");
				nameElement.addText(projectInfos.getProjectName());
				Element typeElement = newProjectInfo.addElement("projectType");
				typeElement.addText(projectInfos.getProjectType());
			}
			String nationalProjectInfo = document.asXML();
			int a = nationalProjectInfo.indexOf("<nationalProjectData>");
			nationalProjectInfo = nationalProjectInfo.substring(a);
			expert.setNationalProject(nationalProjectInfo);
		} else{
			expert.setNationalProject("");
		}
		entityId = expertService.addExpert(expert);
		isReviewer = expert.getIsReviewer();
		return SUCCESS;
	}

	/**
	 * 添加校验
	 */
	public void validateAdd() {
		if (expert.getName() == null || expert.getName().isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "专家姓名不得为空");
		} else if (!Pattern.matches("[\u4E00-\u9FFFa-zA-Z]{2,50}", expert.getName().trim())) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "姓名不合法");
		}
		if (expert.getGender() == null || expert.getGender().isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "请选择性别");
		}
		if (expert.getBirthday() != null) {
			if (expert.getBirthday().compareTo(new Date()) > 0) {
				this.addFieldError(GlobalInfo.ERROR_INFO, "不合理的出生日期");
			}
		}
		if (expert.getIdCardNumber() == null || expert.getIdCardNumber().isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "身份证号不得为空");
		} 
		if (expert.getUniversityCode() == null || expert.getUniversityCode().isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "高校代码不得为空");
		} else if (expert.getUniversityCode().length() > 50) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "高校代码过长");
		}
		if (expert.getDepartment() == null || expert.getDepartment().isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "所在院系不得为空");
		} else if (expert.getDepartment().length() > 400) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "所在院系过长");
		}
		if (expert.getSpecialityTitle() == null || expert.getSpecialityTitle().isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "专业职称不得为空");
		} else if (expert.getSpecialityTitle().length() > 500) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "专业职称过长");
		}
		if (expert.getJob()!= null && expert.getJob().length() > 500) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "行政职务过长");
		}
		if (expert.getPositionLevel().length() > 100) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "岗位等级过长");
		}
		if (expert.getDegree().length() > 50) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "最后学位过长");
		}
		if (expert.getLanguage().length() > 400) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "外语语种过长");
		}
		if (expert.getIsDoctorTutor().length() > 40) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "是否博导过长");
		}
		if (expert.getEmail() == null || expert.getEmail().isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "邮箱不得为空");
		}else if (!Pattern.matches("[\\w\\.\\-]+@(?:[\\w\\-]+\\.)+[\\w\\-]+(?:; [\\w\\.\\-]+@(?:[\\w\\-]+\\.)+[\\w\\-]+)*", expert.getEmail().trim())) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "邮箱格式不合法");
		}
		if (expert.getMobilePhone() != null && !expert.getMobilePhone().isEmpty()) {
			if (!Pattern.matches("\\d{11}(?:; \\d{11})*", expert.getMobilePhone().trim())) {
				this.addFieldError(GlobalInfo.ERROR_INFO, "移动电话不合法");
			}
		}
		/*if (expert.getHomePhone() != null && !expert.getHomePhone().isEmpty()) {
			if (!Pattern.matches("(0\\d{2}-\\d{8}|0\\d{3}-\\d{7}|0\\d{3}-\\d{8}|\\d{11})", expert.getHomePhone().trim())) {
				this.addFieldError(GlobalInfo.ERROR_INFO, "家庭电话不合法");
			}
		}*/
		if (expert.getOfficePhone() != null && !expert.getOfficePhone().isEmpty()) {
			if (!Pattern.matches("\\d+-\\d+(?:-\\d+)?(?:; \\d+-\\d+(?:-\\d+)?)*", expert.getOfficePhone().trim())) {
				this.addFieldError(GlobalInfo.ERROR_INFO, "办公电话不合法");
			}
		}
		if (expert.getOfficeAddress() != null && expert.getOfficeAddress().length() > 400) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "办公地址过长");
		}
		//if (!Pattern.matches("\\d{4,10})", expert.getOfficePostcode().trim())) {
		//	this.addFieldError(GlobalInfo.ERROR_INFO, "办公邮编不合法");
		//}
		if (expert.getResearchField() != null && expert.getResearchField().length() > 4000) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "研究方向过长");
		}
		if (expert.getPartTime() != null && expert.getPartTime().length() > 2000) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "学术兼职过长");
		}
		if (expert.getDiscipline() != null && expert.getDiscipline().length() > 800) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "学科代码过长");
		}
		if (expert.getNationalProject() != null && expert.getNationalProject().length() > 4000) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "承担国家项目情况过长");
		}
		if (expert.getMoeProject() != null && expert.getMoeProject().length() > 4000) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "承担教育部项目情况过长");
		}
		if (expert.getAward() != null && expert.getAward().length() > 4000) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "个人奖励资助情况过长");
		}
		if (expert.getRemark() != null && expert.getRemark().length() > 2000) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "备注过长");
		}
		if (expertType != 0 && expertType != 1) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "无效的专家类型");
		}
		if (isReviewer != 0 && isReviewer != 1) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "无效的参评状态");
		}
	}
	
	/**
	 * 进入修改
	 * @return	SUCCESS进入修改页面
	 * @throws DocumentException 
	 */
	public String toModify() throws DocumentException{
		expert = (Expert) baseService.query(Expert.class, entityId);
		if (expert == null) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "无效的专家");
			jsonMap.put(GlobalInfo.ERROR_INFO, "无效的专家");
			return INPUT;
		}
		//放置承担教育部项目情况信息
		if (expert.getMoeProject() != null) {
			String info = expert.getMoeProject();
			Document document = DocumentHelper.parseText(info);//根据数据库中数据生成“承担国家社科项目情况”xml节点树
			Element rootElement = document.getRootElement();
			Element newInfoElement = rootElement.element("newProjectInfos");
			if (newInfoElement != null) {
				List<Element> newProjects = newInfoElement.elements();
				newMoeProjectInfos = new ArrayList<ProjectInfos>();
				for (Element newProjectElement : newProjects) {
					ProjectInfos projectInfos = new ProjectInfos();
					projectInfos.setProjectNum(newProjectElement.elementText("projectNum"));
					projectInfos.setProjectName(newProjectElement.elementText("projectName"));
					projectInfos.setProjectType(newProjectElement.elementText("projectType"));
					newMoeProjectInfos.add(projectInfos);
				}
			}else{
				newMoeProjectInfos = null;
			}
			Element oldInfoElement = rootElement.element("oldProjectInfos");
			if (oldInfoElement != null) {
				oldMoeProjectInfos = oldInfoElement.getText();
			}else{
				oldMoeProjectInfos = null;
			}
		}else{
			newMoeProjectInfos = null;
			oldMoeProjectInfos = null;
		}
		
		//放置承担国家社科项目情况信息
		if (expert.getNationalProject() != null) {
			String info = expert.getNationalProject();
			Document document = DocumentHelper.parseText(info);//根据数据库中数据生成“承担国家社科项目情况”xml节点树
			Element rootElement = document.getRootElement();
			Element newInfoElement = rootElement.element("newProjectInfos");
			if (newInfoElement != null) {
				List<Element> newProjects = newInfoElement.elements();
				newNationalProjectInfos = new ArrayList<ProjectInfos>();
				for (Element newProjectElement : newProjects) {
					ProjectInfos projectInfos = new ProjectInfos();
					projectInfos.setProjectNum(newProjectElement.elementText("projectNum"));
					projectInfos.setProjectName(newProjectElement.elementText("projectName"));
					projectInfos.setProjectType(newProjectElement.elementText("projectType"));
					newNationalProjectInfos.add(projectInfos);
				}
			}else{
				newNationalProjectInfos = null;
			}
			Element oldInfoElement = rootElement.element("oldProjectInfos");
			if (oldInfoElement != null) {
				oldNationalProjectInfos = oldInfoElement.getText();
			}else{
				oldNationalProjectInfos = null;
			}
		}else{
			newNationalProjectInfos = null;
			oldNationalProjectInfos = null;
		}
		return SUCCESS;
	}
	
	/**
	 * 校验
	 */
	public void validateToModify() {
		if (entityId == null || entityId.isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "请选择专家");
		}
		if (expertType != 0 && expertType != 1) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "无效的专家类型");
		}
		if (isReviewer != 0 && isReviewer != 1) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "无效的参评状态");
		}
	}

	/**
	 * 修改专家信息
	 * @return SUCCESS跳转查看
	 */
	public String modify(){
		Expert mexpert = (Expert) baseService.query(Expert.class, expert.getId());
		mexpert.setName(expert.getName());
		mexpert.setGender(expert.getGender());
		mexpert.setBirthday(expert.getBirthday());
		mexpert.setIdCardNumber(expert.getIdCardNumber());
		mexpert.setUniversityCode(expert.getUniversityCode());
		mexpert.setDepartment(expert.getDepartment());
		mexpert.setSpecialityTitle(expert.getSpecialityTitle());
		mexpert.setJob(expert.getJob());
		mexpert.setPositionLevel(expert.getPositionLevel());
		mexpert.setRating(expert.getRating());
		mexpert.setDegree(expert.getDegree());
		mexpert.setLanguage(expert.getLanguage());
		mexpert.setIsDoctorTutor(expert.getIsDoctorTutor());
		mexpert.setIsReviewer(expert.getIsReviewer());
		mexpert.setEmail(expert.getEmail());
		mexpert.setMobilePhone(expert.getMobilePhone());
		mexpert.setHomePhone(expert.getHomePhone());
		mexpert.setOfficePhone(expert.getOfficePhone());
		mexpert.setOfficeAddress(expert.getOfficeAddress());
		mexpert.setOfficePostcode(expert.getOfficePostcode());
		mexpert.setResearchField(expert.getResearchField());
		mexpert.setPartTime(expert.getPartTime());
		mexpert.setDiscipline(expert.getDiscipline());
		mexpert.setAward(expert.getAward());
		mexpert.setRemark(expert.getRemark());
		
		//修改承担教育部项目情况
		Document document = DocumentHelper.createDocument();
		Element rootElement = document.addElement("moeProjectData");
		if(oldMoeProjectInfos != null && !oldMoeProjectInfos.isEmpty()){
			Element oldProjectInfos = rootElement.addElement("oldProjectInfos");
			oldProjectInfos.addText(oldMoeProjectInfos);
		}
		if (newMoeProjectInfos != null && newMoeProjectInfos.size() > 0) {
			Element newProjectInfos = rootElement.addElement("newProjectInfos");
			for(ProjectInfos projectInfos : newMoeProjectInfos){
				Element newProjectInfo = newProjectInfos.addElement("projectInfo");
				Element numElement = newProjectInfo.addElement("projectNum");
				numElement.addText(projectInfos.getProjectNum());
				Element nameElement = newProjectInfo.addElement("projectName");
				nameElement.addText(projectInfos.getProjectName());
				Element typeElement = newProjectInfo.addElement("projectType");
				typeElement.addText(projectInfos.getProjectType());
			}
		}
		if ((oldMoeProjectInfos != null && !oldMoeProjectInfos.isEmpty()) || (newMoeProjectInfos != null && newMoeProjectInfos.size() > 0)) {
			String moeProjectInfo = document.asXML();
			int a = moeProjectInfo.indexOf("<moeProjectData>");
			moeProjectInfo = moeProjectInfo.substring(a);
			mexpert.setMoeProject(moeProjectInfo);
		}else{
			mexpert.setMoeProject("");
		}
		
		//修改承担国家社科项目情况
		document = DocumentHelper.createDocument();
		rootElement = document.addElement("nationalProjectData");
		if(oldNationalProjectInfos != null && !oldNationalProjectInfos.isEmpty()){
			Element oldProjectInfos = rootElement.addElement("oldProjectInfos");
			oldProjectInfos.addText(oldNationalProjectInfos);
		}
		if (newNationalProjectInfos != null && newNationalProjectInfos.size() > 0) {
			Element newProjectInfos = rootElement.addElement("newProjectInfos");
			for(ProjectInfos projectInfos : newNationalProjectInfos){
				Element newProjectInfo = newProjectInfos.addElement("projectInfo");
				Element numElement = newProjectInfo.addElement("projectNum");
				numElement.addText(projectInfos.getProjectNum());
				Element nameElement = newProjectInfo.addElement("projectName");
				nameElement.addText(projectInfos.getProjectName());
				Element typeElement = newProjectInfo.addElement("projectType");
				typeElement.addText(projectInfos.getProjectType());
			}
		}
		if ((oldNationalProjectInfos != null && !oldNationalProjectInfos.isEmpty()) || (newNationalProjectInfos != null && newNationalProjectInfos.size() > 0)) {
			String nationalProjectInfo = document.asXML();
			int a = nationalProjectInfo.indexOf("<nationalProjectData>");
			nationalProjectInfo = nationalProjectInfo.substring(a);
			mexpert.setNationalProject(nationalProjectInfo);
		}else{
			mexpert.setNationalProject("");
		}

		baseService.modify(mexpert);
		// 专家改为不参与评审后，删除原有的参与评审关系
		entityId = mexpert.getId();
		return SUCCESS;
	}

	/**
	 * 修改校验
	 */
	public void validateModify() {
		if (expert.getName() == null || expert.getName().isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "专家姓名不得为空");
		} else if (!Pattern.matches("[\u4E00-\u9FFFa-zA-Z]{2,50}", expert.getName().trim())) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "姓名不合法");
		}
		if (expert.getGender() == null || expert.getGender().isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "请选择性别");
		}
		if (expert.getBirthday() != null) {
			if (expert.getBirthday().compareTo(new Date()) > 0) {
				this.addFieldError(GlobalInfo.ERROR_INFO, "不合理的出生日期");
			}
		}
		if (expert.getIdCardNumber() == null || expert.getIdCardNumber().isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "身份证号不得为空");
		} 
		if (expert.getUniversityCode() == null || expert.getUniversityCode().isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "高校代码不得为空");
		} else if (expert.getUniversityCode().length() > 50) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "高校代码过长");
		}
		if (expert.getDepartment() == null || expert.getDepartment().isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "所在院系不得为空");
		} else if (expert.getDepartment().length() > 400) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "所在院系过长");
		}
		if (expert.getSpecialityTitle() == null || expert.getSpecialityTitle().isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "专业职称不得为空");
		} else if (expert.getSpecialityTitle().length() > 500) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "专业职称过长");
		}
		if (expert.getJob()!= null && expert.getJob().length() > 500) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "行政职务过长");
		}
		if (expert.getPositionLevel().length() > 100) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "岗位等级过长");
		}
		if (expert.getDegree().length() > 50) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "最后学位过长");
		}
		if (expert.getLanguage().length() > 400) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "外语语种过长");
		}
		if (expert.getIsDoctorTutor().length() > 40) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "是否博导过长");
		}
		if (expert.getEmail() == null || expert.getEmail().isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "邮箱不得为空");
		}else if (!Pattern.matches("[\\w\\.\\-]+@(?:[\\w\\-]+\\.)+[\\w\\-]+(?:; [\\w\\.\\-]+@(?:[\\w\\-]+\\.)+[\\w\\-]+)*", expert.getEmail().trim())) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "邮箱格式不合法");
		}
		if (expert.getMobilePhone() != null && !expert.getMobilePhone().isEmpty()) {
			if (!Pattern.matches("\\d{11}(?:; \\d{11})*", expert.getMobilePhone().trim())) {
				this.addFieldError(GlobalInfo.ERROR_INFO, "移动电话不合法");
			}
		}
		/*if (expert.getHomePhone() != null && !expert.getHomePhone().isEmpty()) {
			if (!Pattern.matches("(0\\d{2}-\\d{8}|0\\d{3}-\\d{7}|0\\d{3}-\\d{8}|\\d{11})", expert.getHomePhone().trim())) {
				this.addFieldError(GlobalInfo.ERROR_INFO, "家庭电话不合法");
			}
		}*/
		if (expert.getOfficePhone() != null && !expert.getOfficePhone().isEmpty()) {
			if (!Pattern.matches("\\d+-\\d+(?:-\\d+)?(?:; \\d+-\\d+(?:-\\d+)?)*", expert.getOfficePhone().trim())) {
				this.addFieldError(GlobalInfo.ERROR_INFO, "办公电话不合法");
			}
		}
		if (expert.getOfficeAddress() != null && expert.getOfficeAddress().length() > 400) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "办公地址过长");
		}
		//if (!Pattern.matches("\\d{4,10})", expert.getOfficePostcode().trim())) {
		//	this.addFieldError(GlobalInfo.ERROR_INFO, "办公邮编不合法");
		//}
		if (expert.getResearchField() != null && expert.getResearchField().length() > 4000) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "研究方向过长");
		}
		if (expert.getPartTime() != null && expert.getPartTime().length() > 2000) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "学术兼职过长");
		}
		if (expert.getDiscipline() != null && expert.getDiscipline().length() > 800) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "学科代码过长");
		}
		if (expert.getNationalProject() != null && expert.getNationalProject().length() > 4000) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "承担国家项目情况过长");
		}
		if (expert.getMoeProject() != null && expert.getMoeProject().length() > 4000) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "承担教育部项目情况过长");
		}
		if (expert.getAward() != null && expert.getAward().length() > 4000) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "个人奖励资助情况过长");
		}
		if (expert.getRemark() != null && expert.getRemark().length() > 2000) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "备注过长");
		}
		if (expertType != 0 && expertType != 1) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "无效的专家类型");
		}
		if (isReviewer != 0 && isReviewer != 1) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "无效的参评状态");
		}
	}

	/**
	 * 删除专家
	 * @return SUCCESS跳转列表
	 */
	public String delete(){
		try{
			expertService.deleteExperts(entityIds);
			return SUCCESS;
		}catch (Exception e){
			jsonMap.put(GlobalInfo.ERROR_INFO, "删除失败！");
			return INPUT;
		}
	}

	/**
	 * 删除校验
	 */
	public void validateDelete() {
		if (entityIds == null || entityIds.isEmpty()) {//删除的记录id不得为空
			this.addFieldError(GlobalInfo.ERROR_INFO, "请选择专家");
			jsonMap.put(GlobalInfo.ERROR_INFO, "请选择专家");
		}
		if (expertType != 0 && expertType != 1) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "无效的专家类型");
			jsonMap.put(GlobalInfo.ERROR_INFO, "无效的专家类型");
		}
		if (isReviewer != 0 && isReviewer != 1) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "无效的参评状态");
			jsonMap.put(GlobalInfo.ERROR_INFO, "无效的参评状态");
		}
	}

	
	/**
	 * 设置为参评
	 * @return
	 */
	public String enableReview() {
		expertService.setReview(entityIds, 1, null, 0);
		return SUCCESS;
	}
	
	public void validateEnableReview() {
		if (entityIds == null || entityIds.isEmpty()) {//删除的记录id不得为空
			this.addFieldError(GlobalInfo.ERROR_INFO, "请选择专家");
			jsonMap.put(GlobalInfo.ERROR_INFO, "请选择专家");
		}
		if (expertType != 0 && expertType != 1) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "无效的专家类型");
			jsonMap.put(GlobalInfo.ERROR_INFO, "无效的专家类型");
		}
		if (isReviewer != 0 && isReviewer != 1) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "无效的参评状态");
			jsonMap.put(GlobalInfo.ERROR_INFO, "无效的参评状态");
		}
	}
	
	// 填写退评原因
	public String toDisableReview() {
		session.put("entityIds", entityIds);
		return SUCCESS;
	}
	
	/**
	 * 设置为退评
	 * @return
	 */
	public String disableReview() {
		entityIds = (List<String>) session.get("entityIds");
		expertService.setReview(entityIds, 0, notReviewerReason,isNotReviewAll);
		return SUCCESS;
	}

	//专家名称下拉框自动补全
	public String fetchAutoData() {
		List<String> autoData = expertService.query("select distinct(e.name) from Expert e");
		jsonMap.put("autoData", autoData);
		return SUCCESS;
	}
	
	// 是否重点人
	public String toggleKey() {
		System.out.println(expertid + " + " + isKey);
		if (expertid != null && (isKey == 1 || isKey == 0)) {
			Expert expert = (Expert) expertService.query(Expert.class, expertid);
			expert.setIsKey(isKey);
			expertService.modify(expert);
		}
		return SUCCESS;
	}
	
	@Transactional
	public String toAddKeyPerson() throws DocumentException {
		synchronized (ExpertAction.class) {
			if(!busy) {//不忙中，该状态为忙碌中
				busy = true;
			} else {
				jsonMap.put(GlobalInfo.ERROR_INFO, "当前作业正在处理中，请稍等……");
				return INPUT;
			}
		}
		personToDeal = new ArrayList<String[]>();
		//初始化Map
		long begin = System.currentTimeMillis();
		Map<String, String> univNameCodeMap = (Map<String, String>) ActionContext.getContext().getApplication().get("univNameCodeMap");
		List<String[]> keyPersons = new ArrayList<String[]>();
		String content = SmdbClient.getInstance().invokeDirect("fwzx", "csdc702", "SmasService", "addKeyPerson", new HashMap<String, String>());
		Document document = DocumentHelper.parseText(content);
		List list = document.selectNodes("//item" );
		for( int i = 0; i < list.size(); i++) {
			String[] person = new String[11];
			Element element = (Element) list.get(i);
			person[0] = ((Element) element.selectNodes("//name").get(0)).getText();
			person[1] = ((Element) element.selectNodes("//homePhone").get(0)).getText();
			person[2] = ((Element) element.selectNodes("//officePhone").get(0)).getText();
			person[3] = ((Element) element.selectNodes("//officeFax").get(0)).getText();
			person[4] = ((Element) element.selectNodes("//email").get(0)).getText();
			person[5] = ((Element) element.selectNodes("//mobilePhone").get(0)).getText();
			person[6] = ((Element) element.selectNodes("//idcardType").get(0)).getText();
			person[7] = ((Element) element.selectNodes("//idcardNumber").get(0)).getText();
			person[8] = ((Element) element.selectNodes("//birthday").get(0)).getText();
			person[9] = ((Element) element.selectNodes("//agencyName").get(0)).getText();
			keyPersons.add(person);
		}		
		System.out.println("init map complete! Used time: " + (System.currentTimeMillis() - begin) + "ms");
		expertFinder.initData();
		int i = 1;
		Map<ImporterInfo, String> conditions = new HashMap<ImporterInfo, String>();
		
		for (String[] keyPerson : keyPersons) {
			if (i == 115) {
				System.out.println("yes");
			}
			System.out.println(i);
			i++;
			//填充匹配条件
			conditions.put(ImporterInfo.BIRTHDAY, keyPerson[8]);
			conditions.put(ImporterInfo.IDCARD_TYPE, keyPerson[6]);
			conditions.put(ImporterInfo.IDCARD_NUMBER, keyPerson[7]);
			conditions.put(ImporterInfo.MOBILE_PHONE, keyPerson[5]);
			conditions.put(ImporterInfo.EMAIL, keyPerson[4]);
			conditions.put(ImporterInfo.HOME_PHONE, keyPerson[1]);
			conditions.put(ImporterInfo.OFFICE_PHONE, keyPerson[2]);
			conditions.put(ImporterInfo.OFFICE_FAX, keyPerson[3]);
			
			Object otherExpert = expertFinder.findExpert("", keyPerson[0], conditions);
			if(otherExpert != null) {
				if(otherExpert instanceof Expert) {
					keyPerson[10] = ((Expert) otherExpert).getId();
				} 
				personToDeal.add(keyPerson);
			}
		}
		busy = false;
		return SUCCESS;
	}
	
	@Transactional
	public String addKeyPerson() {
		synchronized (ExpertAction.class) {
			if(!busy) {//不忙中，该状态为忙碌中
				busy = true;
			} else {
				jsonMap.put(GlobalInfo.ERROR_INFO, "当前作业正在处理中，请稍等……");
				return INPUT;
			}
		}
		int isConsiderCurrentYearProject = Integer.parseInt(request.getParameter("isConsiderCurrentYearProject"));
		for (String[] keyPerson : personToDeal) {
			if(keyPerson[10] != null && !keyPerson[10].isEmpty()) {
				List<String> otherExpertIds = new ArrayList<String>();
				Expert otherExpert = (Expert) expertService.query(Expert.class, keyPerson[10]);
				otherExpert.setIsKey(1);
				otherExpertIds.add(otherExpert.getId());
				String reason = otherExpert.getReason();
				if (reason == null) {
					reason = "永久退评：重点人";
				}
				if (reason != null && !reason.contains("永久退评：重点人")) {
					reason = "永久退评：重点人" + "; " + reason;
				}
				expertService.setReview(otherExpertIds, 0, reason, isConsiderCurrentYearProject);
			} else {//留做待人工审核的专家
				//TODO
			}
		}
		busy = false;
		return SUCCESS;
	}
	
	public int getIsKey() {
		return isKey;
	}

	public void setIsKey(int isKey) {
		this.isKey = isKey;
	}

	public File getXls() {
		return xls;
	}
	public void setXls(File xls) {
		this.xls = xls;
	}
	public String getExpertid() {
		return expertid;
	}
	public void setExpertid(String expertid) {
		this.expertid = expertid;
	}
	public Expert getExpert() {
		return expert;
	}
	public void setExpert(Expert expert) {
		this.expert = expert;
	}
	public List<String> getExpertids() {
		return expertids;
	}
	public void setExpertids(List<String> expertids) {
		this.expertids = expertids;
	}
	public String getUniversityName() {
		return universityName;
	}
	public void setUniversityName(String universityName) {
		this.universityName = universityName;
	}
	public void setExpertService(IExpertService expertService) {
		this.expertService = expertService;
	}
	public IExpertService getExpertService() {
		return expertService;
	}
	public int getExpertType() {
		return expertType;
	}
	public void setExpertType(int expertType) {
		this.expertType = expertType;
	}
	public int getIsReviewer() {
		return isReviewer;
	}
	public void setGeneralService(IGeneralService generalService) {
		this.generalService = generalService;
	}
	public void setIsReviewer(int isReviewer) {
		this.isReviewer = isReviewer;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	public int getExportAll() {
		return exportAll;
	}
	public void setExportAll(int exportAll) {
		this.exportAll = exportAll;
	}
	public int getCountReviewer() {
		return countReviewer;
	}
	public void setCountReviewer(int countReviewer) {
		this.countReviewer = countReviewer;
	}
	public String getLevelOneDiscipline() {
		return levelOneDiscipline;
	}

	public void setLevelOneDiscipline(String levelOneDiscipline) {
		this.levelOneDiscipline = levelOneDiscipline;
	}

	public int getIsNotReviewAll() {
		return isNotReviewAll;
	}

	public void setIsNotReviewAll(int isNotReviewAll) {
		this.isNotReviewAll = isNotReviewAll;
	}

	public String getNotReviewerReason() {
		return notReviewerReason;
	}

	public void setNotReviewerReason(String notReviewerReason) {
		this.notReviewerReason = notReviewerReason;
	}

	public int getViewType() {
		return viewType;
	}

	public void setViewType(int viewType) {
		this.viewType = viewType;
	}

	public List<Object[]> getPageList() {
		return pageList;
	}
	public void setPageList(List<Object[]> pageList) {
		this.pageList = pageList;
	}
	public String getFileFileName() {
		return fileFileName;
	}
	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}
	
	public List<SystemOption> getDisList() {
		return disList;
	}

	public void setDisList(List<SystemOption> disList) {
		this.disList = disList;
	}

	@Override
	public Object[] advSearchCondition() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String[] column() {
		return column;
	}
	@Override
	public String pageName() {
		return PAGENAME;
	}
	@Override
	public String dateFormat() {
		return DATE_FORMAT;
	}
	@Override
	public String pageBufferId() {
		return PAGE_BUFFER_ID;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getOldMoeProjectInfos() {
		return oldMoeProjectInfos;
	}

	public void setOldMoeProjectInfos(String oldMoeProjectInfos) {
		this.oldMoeProjectInfos = oldMoeProjectInfos;
	}

	public String getOldNationalProjectInfos() {
		return oldNationalProjectInfos;
	}

	public void setOldNationalProjectInfos(String oldNationalProjectInfos) {
		this.oldNationalProjectInfos = oldNationalProjectInfos;
	}

	public List<ProjectInfos> getNewMoeProjectInfos() {
		return newMoeProjectInfos;
	}

	public void setNewMoeProjectInfos(List<ProjectInfos> newMoeProjectInfos) {
		this.newMoeProjectInfos = newMoeProjectInfos;
	}

	public List<ProjectInfos> getNewNationalProjectInfos() {
		return newNationalProjectInfos;
	}

	public void setNewNationalProjectInfos(List<ProjectInfos> newNationalProjectInfos) {
		this.newNationalProjectInfos = newNationalProjectInfos;
	}

	public List<String[]> getPersonToDeal() {
		return personToDeal;
	}

	public void setPersonToDeal(List<String[]> personToDeal) {
		this.personToDeal = personToDeal;
	}
	
}
