package csdc.action.project.general;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;

import com.opensymphony.xwork2.ActionContext;

import csdc.action.project.ApplicationAction;
import csdc.bean.Expert;
import csdc.bean.ProjectApplication;
import csdc.bean.ProjectApplicationReview;
import csdc.bean.SystemOption;
import csdc.bean.common.ExpertLink;
import csdc.service.IGeneralService;
import csdc.tool.execution.finder.GeneralFinder;
import csdc.tool.execution.importer.Application2014Importer;
import csdc.tool.info.GlobalInfo;


/**
 * 一般项目管理基类
 * @author fengcl
 * 
 */
@SuppressWarnings("unchecked")
public class GeneralAction extends ApplicationAction {
	
	private static final long serialVersionUID = 1L;
	private static final String HQL = "select p.id, p.projectName, p.director, u.name, count(pr.id), p.warningReviewer, p.discipline, p.isGranting, p.voteNumber, p.score, p.number, 'expertlink', 'expertinfo', p.reason, p.department, p.finalAuditResult from ProjectApplication p left join p.applicationReview pr, University u where p.type = 'general' and p.year = :defaultYear and p.universityCode = u.code ";
	private static final String HQLG = " group by p.id, p.projectName, p.director, u.name, p.warningReviewer, p.discipline, p.isGranting, p.voteNumber, p.score, p.number, p.reason, p.department, p.finalAuditResult";
	private static final String HQLC = "select COUNT(*) from ProjectApplication p, University u where p.type = 'general' and p.year = :defaultYear and p.universityCode = u.code";
	private static final String PROJECT_TYPE = "general";//项目类别
	private static final String CLASS_NAME = "ProjectApplication";//项目类名
	private static final String PAGENAME = "generalPage";
	private static final String PAGE_BUFFER_ID = "p.id";//缓存id
	private static final String column[] = {
		"p.projectName",
		"p.director",
		"u.name",
		"count(pr.id) desc",
		"p.warningReviewer",
		"CONCAT(p.voteNumber, p.score)",
		"p.isGranting",
		"p.reason",
		"p.discipline",
		"p.department",
		"p.finalAuditResult"
	};
	protected IGeneralService generalService;	//一般项目业务接口对象
		
	@Autowired
	private GeneralFinder generalFinder;
	
	public String listHQL() {
		return GeneralAction.HQL;
	}	
	public String listHQLG() {
		return GeneralAction.HQLG;
	}	
	public String listHQLC() {
		return GeneralAction.HQLC;
	}	
	public String projectType(){
		return GeneralAction.PROJECT_TYPE;
	}	
	public String className(){
		return GeneralAction.CLASS_NAME;
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
	public String pageBufferId() {
		return GeneralAction.PAGE_BUFFER_ID;
	}
			
	/**
	 * 参评或退评项目列表和初级检索条件
	 * @throws FileNotFoundException 
	 */
	@Override
	public Object[] simpleSearchCondition(){
		keyword = (keyword == null) ? "" : keyword.toLowerCase().trim();// 预处理关键字
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		hql.append(HQL);
		hql.append(" and p.isReviewable = :isReviewable");
		map.put("isReviewable", isReviewable);
		map.put("defaultYear", session.get("defaultYear"));
		
		StringBuffer c_hql = new StringBuffer();
		c_hql.append(HQLC);
		c_hql.append(" and p.isReviewable = :isReviewable");
		String s_hql = "";// 检索条件
		if (!keyword.isEmpty()) {
			hql.append(" and ");
			c_hql.append(" and ");
			if (searchType == 1) {
				s_hql = "LOWER(p.projectName) like :keyword";
				map.put("keyword", "%" + keyword + "%");
			} else if (searchType == 2) {
				s_hql = "LOWER(p.director) like :keyword";
				map.put("keyword", "%" + keyword + "%");
			} else if (searchType == 3) {
				s_hql = "LOWER(u.name) like :keyword";
				map.put("keyword", "%" + keyword + "%");
			} else if (searchType == 4) {
				s_hql = "LOWER(p.discipline) like :keyword";
				map.put("keyword", "%" + keyword + "%");
			} else if (searchType == 5) {// 按学科名称搜索
				List<Object> dislist = generalService.getDisciplineCode(keyword);
				if (dislist != null && !dislist.isEmpty()) {
					for (int i = 0; i < dislist.size(); i++) {
						s_hql += "LOWER(p.discipline) like '%" + dislist.get(i) + "%' or ";
					}
					s_hql = s_hql.substring(0, s_hql.length() - 4);
					s_hql = "(" + s_hql + ")";
				} else {
					s_hql = "LOWER(p.discipline) like :keyword";// 当没有找到相应的学科代码时，以汉字填补，只是为了输出为空
					map.put("keyword", "%" + keyword + "%");
				}
			} else if (searchType == 6) {
				s_hql = "LOWER(p.department) like :keyword";
				map.put("keyword", "%" + keyword + "%");
			} else if (searchType == 7) {
				s_hql = "LOWER(p.reason) like :keyword";
				map.put("keyword", "%" + keyword + "%");
			} else {
				s_hql = "(LOWER(p.projectName) like :keyword or LOWER(p.director) like :keyword or LOWER(u.name) like :keyword or LOWER(p.department) like :keyword or LOWER(p.reason) like :keyword";
				map.put("keyword", "%" + keyword + "%");
				List<Object> dislist = generalService.getDisciplineCode(keyword);
				if (dislist != null && !dislist.isEmpty()) {
					for (int i = 0; i < dislist.size(); i++) {
						s_hql += " or LOWER(p.discipline) like '%" + dislist.get(i) + "%'";
					}
				} else {
					s_hql += " or LOWER(p.discipline) like :keyword";// 当没有找到相应的学科代码时，以汉字填补，只是为了输出为空
					map.put("keyword", "%" + keyword + "%");
				}
				s_hql += ")";
			}
		}
		hql.append(s_hql);
		c_hql.append(s_hql);
		StringBuffer HQL4ProjectExport = new StringBuffer();
		HQL4ProjectExport.append(c_hql.toString());
		
//		//手工给多个项目分配专家是返回列表页面（这里entityId可能为多个）
//		if(entityId != null && entityId.length() > 0) {
//			String[] tmp = entityId.split(";");
//			String ids = "'" + tmp[0] + "' ";
//			for(int i = 1; i < tmp.length; i++) {
//				ids += ", '" + tmp[i] + "' ";
//			}
//			hql.append(" and p.id in ( " + ids + " )");
//			c_hql.append(" and p.id in ( " + ids + " )");
//			HQL4ProjectExport.append(" and p.id in ( " + ids + " ) ");
//		}
		hql.append(HQLG);
		
		//为列表页面项目导出做准备
		String tmp = HQL4ProjectExport.toString();
		tmp = tmp.replace(" count(pr.project.id) asc,", " p.projectName asc,");
		tmp = tmp.replace(" count(pr.project.id) desc,", " p.projectName desc,");
		String exporthql = tmp;
		session.put("HQL4ProjectExport", exporthql.replaceAll("COUNT\\(\\*\\)", "p"));
		session.put("Map4ProjectExport", map);
		
		// 调用初级检索功能
		return new Object[]{
			hql.toString(),
			map,
			3,
			null,
			c_hql.toString()
		};
	}
		
	/**
	 * 处理列表数据，如有需要，各模块可以重写此方法，本方法默认仅格式化时间。
	 */
	@Transactional
	public void pageListDealWith() {
		if(listType == 1){//参评,退评项目列表
			Map application = ActionContext.getContext().getApplication();
			//获取学科名称与学科代码的映射
			HashMap<String, String> disMap = (HashMap<String, String>) application.get("disMap");
//			List disList = (List) application.get("disall");//获取所有学科对象的list
//			Map<String, String> disname2discode = new HashMap<String, String>();//学科名称与学科代码的映射
//			for (Iterator iterator = disList.iterator(); iterator.hasNext();) {
//				SystemOption so = (SystemOption) iterator.next();
//				String disname = so.getName();
//				String discode = so.getCode();
//				disname2discode.put(discode, disname);
//			}
			
			laData = new ArrayList();// 处理之后的列表数据
			Object[] item;// 缓存查询结果一行
			
			Date begin = new Date();
			List<ExpertLink> expertLink = new ArrayList<ExpertLink>();// 用于专家信息
			// 遍历初始查询列表数据，按照指定格式，格式化其中的时间字段，其它字段转化为字符串
			for (Object p : pageList) {
				item = (Object[]) p;
				
				String projectId;// 项目ID
				String value = "";// 项目评审专家信息
				String disciplineCode = "";// 项目学科信息
				projectId = item[0].toString();
				expertLink = generalService.getExpert(projectId);
				Integer k = 1;
				if (expertLink != null && !expertLink.isEmpty()) {
					for (int j = 0; j < expertLink.size(); j++) {
						value += k.toString() + "、" + expertLink.get(j).getName() + "(" + expertLink.get(j).getUname() + expertLink.get(j).getTitle() + ")" + "；\n";
						k += 1;
					}
					if (!value.isEmpty()) {
						value = value.substring(0, value.length() - 2);
					}
				}
				if (item[6] != null) {
					disciplineCode = item[6].toString();
					disciplineCode = generalService.getDisciplineInfo(disMap, disciplineCode);
					item[6] = disciplineCode;
				}
				//处理专家信息
				item[11] = expertLink;//评审专家
				item[12] = value;//专家信息
				
				laData.add(item);// 将处理好的数据存入laData
			}
			Date end = new Date();
			System.out.println("组装数据耗时：" + (end.getTime() - begin.getTime()) + "ms");
		}else{
			super.pageListDealWith();
		}
	}

	/**
	 * 添加校验
	 */
	public void validateAdd() {
		if (isReviewable != 0 && isReviewable != 1) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "无效的参评状态");
		}
		if (project.getProjectName() == null || project.getProjectName().isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "项目名称不得为空");
		} else if (project.getProjectName().length() > 10) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "项目名称过长");
		}
		if (project.getProjectType().length() > 10) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "项目类型过长");
		}
		if (project.getResearchType().length() > 10) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "研究类型过长");
		}
		if (project.getDisciplineType().length() > 10) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "学科类型过长");
		}
		if (project.getDiscipline().length() > 10) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "依托学科过长");
		}
		if (project.getUniversityCode().length() > 10) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "高校代码过长");
		}
		if (project.getApplyDate() != null) {
			if (project.getApplyDate().compareTo(new Date()) > 0) {
				this.addFieldError(GlobalInfo.ERROR_INFO, "不合理的申请日期");
			}
		}
		if (project.getAddDate() != null) {
			if (project.getAddDate().compareTo(new Date()) > 0) {
				this.addFieldError(GlobalInfo.ERROR_INFO, "不合理的立项时间");
			}
		}
		if (project.getAuditStatus().length() > 10) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "审核状态过长");
		}
		if (project.getIsReviewable() != 0 && project.getIsReviewable() != 1) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "请选择是否参加审批");
		}
		if (project.getPlanEndDate() != null) {
			if (project.getPlanEndDate().compareTo(new Date()) < 0) {
				this.addFieldError(GlobalInfo.ERROR_INFO, "预计结项日期不合法");
			}
		}
		if (project.getOtherFee() != null && !project.getOtherFee().isEmpty()) {
			if (!project.getOtherFee().trim().equals(null) && project.getOtherFee().trim() != "" && !Pattern.matches("[0-9]{0,7}.[0-9]{0,5}", project.getOtherFee().trim())) {
				this.addFieldError(GlobalInfo.ERROR_INFO, "其他费用不合法");
			}
		}
		if (project.getApplyFee() != null && !project.getApplyFee().isEmpty()) {
			if (!project.getApplyFee().trim().equals(null) && project.getApplyFee().trim() != "" && !Pattern.matches("[0-9]{0,7}.[0-9]{0,5}", project.getApplyFee().trim())) {
				this.addFieldError(GlobalInfo.ERROR_INFO, "申请费用不合法");
			}
		}
		if (project.getMembers().length() > 100) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "项目成员过长");
		}
		if (project.getFinalResultType().length() > 10) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "最终成果类型过长");
		}
		if (project.getDirector().length() > 10) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "姓名过长");
		}
		if (!Pattern.matches("[男女]", project.getGender().trim())) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "请选择性别");
		}
		if (project.getApplyDate() != null) {
			if (project.getApplyDate().compareTo(new Date()) > 0) {
				this.addFieldError(GlobalInfo.ERROR_INFO, "生日不合法");
			}
		}
		if (!Pattern.matches("男|女", project.getGender().trim())) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "请选择性别");
		}
		if (project.getIdcard() == null || project.getIdcard().isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "身份证号不得为空");
		} 
//		else if (!Pattern.matches("(\\d{6}((19)|(2\\d))\\d{2}((0\\d)|(1[0-2]))((3[01])|([0-2]\\d))\\d{3}(\\d|x))|(\\d{8}((0\\d)|(1[0-2]))((3[01])|([0-2]\\d))\\d{3})", project.getIdcard().trim())) {
//			this.addFieldError(GlobalInfo.ERROR_INFO, "身份证号不合法");
//		}
		if (project.getForeign().length() > 10) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "外语过长");
		}
		if (project.getEducation().length() > 10) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "学历过长");
		}
		if (project.getDegree().length() > 10) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "学位过长");
		}
		if (project.getDepartment().length() > 10) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "院系过长");
		}
		if (project.getTitle().length() > 10) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "职称过长");
		}
		if (project.getJob().length() > 10) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "职务过长");
		}
		if (project.getEmail() == null || project.getEmail().isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "邮箱不得为空");
		}else if (!Pattern.matches("[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+", project.getEmail().trim())) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "邮箱格式不合法");
		}
		if (project.getMobile() != null && !project.getMobile().isEmpty()) {
			if (!Pattern.matches("\\b(13\\d{9})|(15\\d{9})|(189\\d{8})\\b|无", project.getMobile().trim())) {
				this.addFieldError(GlobalInfo.ERROR_INFO, "移动电话不合法");
			}
		}
		if (project.getPhone() != null && !project.getPhone().isEmpty()) {
			if (!Pattern.matches("(0\\d{2}-\\d{8}|0\\d{3}-\\d{7}|0\\d{3}-\\d{8}|\\d{11})|无", project.getPhone().trim())) {
				this.addFieldError(GlobalInfo.ERROR_INFO, "固定电话不合法");
			}
		}
		if (project.getAddress().length() > 100) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "地址过长");
		}
		if (project.getPostcode() != null && !project.getPostcode().isEmpty()) {
			if (!Pattern.matches("\\d{4,10})", project.getPostcode().trim())) {
				this.addFieldError(GlobalInfo.ERROR_INFO, "邮编不合法");
			}
		}
	}
		
	/**
	 * 修改项目信息
	 * @return SUCCESS跳转查看
	 */
	public String modify() {
		entityId = (String) session.get("projectId");
		listType = (Integer) session.get("projectListType");
		ProjectApplication mproject = (ProjectApplication) baseService.query(ProjectApplication.class, entityId);
		mproject.setAddDate(project.getAddDate());
		mproject.setAddress(project.getAddress());
		mproject.setApplyDate(project.getApplyDate());
		mproject.setApplyFee(project.getApplyFee());
		mproject.setAuditStatus(project.getAuditStatus());
		mproject.setBirthday(project.getBirthday());
		mproject.setDegree(project.getDegree());
		mproject.setDepartment(project.getDepartment());
		mproject.setDirector(project.getDirector());
		mproject.setDiscipline(project.getDiscipline());
		mproject.setDisciplineType(project.getDisciplineType());
		mproject.setEducation(project.getEducation());
		mproject.setEmail(project.getEmail());
		mproject.setFinalResultType(project.getFinalResultType());
		mproject.setForeign(project.getForeign());
		mproject.setGender(project.getGender());
		mproject.setIdcard(project.getIdcard());
		if(project.getIsReviewable() == 0) {
			mproject.setWarningReviewer("");
			// 项目改为不参与评审后，分配的评审专家信息要删除
			List<ProjectApplicationReview> list = baseService.query(" select pr from ProjectApplicationReview pr where pr.project.id = '" + project.getId() + "' " );
			for(int i = 0; i < list.size(); i++) {
				baseService.delete(list.get(i));
			}
		}
		mproject.setIsReviewable(project.getIsReviewable());
		mproject.setJob(project.getJob());
		mproject.setMembers(project.getMembers());
		mproject.setMobile(project.getMobile());
		mproject.setOtherFee(project.getOtherFee());
		mproject.setPhone(project.getPhone());
		mproject.setPlanEndDate(project.getPlanEndDate());
		mproject.setPostcode(project.getPostcode());
		mproject.setProjectName(project.getProjectName());
		mproject.setProjectType(project.getProjectType());
		mproject.setResearchType(project.getResearchType());
		mproject.setTitle(project.getTitle());
		mproject.setUniversityCode(project.getUniversityCode());
		baseService.modify(mproject);
		entityId = mproject.getId();
		return SUCCESS;
	}

	/**
	 * 修改校验
	 */
	public void validateModify() {
		if (isReviewable != 0 && isReviewable != 1) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "无效的参评状态");
		}
		if (project.getProjectName() == null || project.getProjectName().isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "项目名称不得为空");
		} else if (project.getProjectName().length() > 200) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "项目名称过长");
		}
		if (project.getProjectType().length() > 100) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "项目类型过长");
		}
		if (project.getResearchType().length() > 100) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "研究类型过长");
		}
		if (project.getDisciplineType().length() > 40) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "学科类型过长");
		}
		if (project.getDiscipline().length() > 100) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "依托学科过长");
		}
		if (project.getUniversityCode().length() > 40) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "高校代码过长");
		}
		if (project.getApplyDate() != null) {
			if (project.getApplyDate().compareTo(new Date()) > 0) {
				this.addFieldError(GlobalInfo.ERROR_INFO, "不合理的申请日期");
			}
		}
		if (project.getAddDate() != null) {
			if (project.getAddDate().compareTo(new Date()) > 0) {
				this.addFieldError(GlobalInfo.ERROR_INFO, "不合理的立项时间");
			}
		}
		if (project.getAuditStatus().length() > 40) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "审核状态过长");
		}
		if (project.getPlanEndDate() != null) {
			if (project.getPlanEndDate().compareTo(new Date()) < 0) {
				this.addFieldError(GlobalInfo.ERROR_INFO, "预计结项日期不合法");
			}
		}
		if(project.getOtherFee() != null && !project.getOtherFee().isEmpty()){
			if (!Pattern.matches("[0-9]{0,7}.[0-9]{0,5}", project.getOtherFee().trim())) {
				this.addFieldError(GlobalInfo.ERROR_INFO, "其他费用不合法");
			}
		}
		if(project.getApplyFee() != null && !project.getApplyFee().isEmpty()){
			if (!Pattern.matches("[0-9]{0,7}.[0-9]{0,5}", project.getApplyFee().trim())) {
				this.addFieldError(GlobalInfo.ERROR_INFO, "申请费用不合法");
			}
		}
		if (project.getMembers().length() > 400) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "项目成员过长");
		}
		if (project.getFinalResultType().length() > 40) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "最终成果类型过长");
		}
		
		if (project.getDirector().length() > 40) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "姓名过长");
		}
		if (!Pattern.matches("[男女]", project.getGender().trim())) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "请选择性别");
		}
		if (project.getApplyDate() != null) {
			if (project.getApplyDate().compareTo(new Date()) > 0) {
				this.addFieldError(GlobalInfo.ERROR_INFO, "生日不合法");
			}
		}
		if (project.getIdcard() == null || project.getIdcard().isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "身份证号不得为空");
		} 
//		else if (!Pattern.matches("(\\d{6}((19)|(2\\d))\\d{2}((0\\d)|(1[0-2]))((3[01])|([0-2]\\d))\\d{3}(\\d|x))|(\\d{8}((0\\d)|(1[0-2]))((3[01])|([0-2]\\d))\\d{3})", project.getIdcard().trim())) {
//			this.addFieldError(GlobalInfo.ERROR_INFO, "身份证号不合法");
//		}
		if (project.getForeign().length() > 40) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "外语过长");
		}
		if (project.getEducation().length() > 40) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "学历过长");
		}
		if (project.getDegree().length() > 40) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "学位过长");
		}
		if (project.getDepartment().length() > 40) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "院系过长");
		}
		if (project.getTitle().length() > 40) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "职称过长");
		}
		if (project.getJob().length() > 40) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "职务过长");
		}
		if (project.getEmail() == null || project.getEmail().isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "邮箱不得为空");
		} else if (!Pattern.matches("[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+", project.getEmail().trim())) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "邮箱格式不合法");
		}
		if (project.getMobile() != null && !project.getMobile().isEmpty()) {
			if (!Pattern.matches("\\b(13\\d{9})|(15\\d{9})|(189\\d{8})\\b|无", project.getMobile().trim())) {
				this.addFieldError(GlobalInfo.ERROR_INFO, "移动电话不合法");
			}
		}
		if (project.getPhone() != null && !project.getPhone().isEmpty()) {
			if (!Pattern.matches("(0\\d{2}-\\d{8}|0\\d{3}-\\d{7}|0\\d{3}-\\d{8}|\\d{11})|无", project.getPhone().trim())) {
				this.addFieldError(GlobalInfo.ERROR_INFO, "固定电话不合法");
			}
		}
		if (project.getAddress().length() > 100) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "地址过长");
		}
		//if (!Pattern.matches("\\d{4,10})", project.getPostcode().trim())) {
		//	this.addFieldError(GlobalInfo.ERROR_INFO, "邮编不合法");
		//}
	}
							
	/**
	 * 导出项目一览表
	 * @return
	 * @throws Exception
	 */
	public InputStream getProjectFile() throws Exception{
		//导出的Excel文件名
		Map session = ActionContext.getContext().getSession();
		String yearString = session.get("defaultYear").toString();
		fileFileName = yearString + "年度一般项目一览表.xls";
		fileFileName = new String(fileFileName.getBytes(), "ISO8859-1");
		return generalService.exportProject(exportAll, false, projectType());
	}
	
	/**
	 * 导出项目专家匹配表
	 * @return 输入流
	 */
	public InputStream getProjectReviewerFile() throws Exception {
		//导出的Excel文件名
		Map session = ActionContext.getContext().getSession();
		String yearString = session.get("defaultYear").toString();
		fileFileName = yearString + "年度一般项目专家匹配表.xls";
		fileFileName = new String(fileFileName.getBytes(), "ISO8859-1");
		return generalService.exportProject(exportAll, true, projectType());
	}
		
	/**
	 * 导出专家匹配更新表
	 * @return 输入流
	 * @throws Exception
	 */
	public InputStream getMatchUpdateFile() throws Exception {
		Map session = ActionContext.getContext().getSession();
		Integer year = (Integer) session.get("defaultYear");
		fileFileName = year + "年度一般项目专家匹配更新表.xls";
		fileFileName = new String(fileFileName.getBytes(), "ISO8859-1");
		return generalService.exportMatchUpdate(year, new Date(startTime), new Date(endTime), projectType());
	}

	/**
	 * 项目模板
	 * @return
	 * @throws Exception
	 */
	public String projectTemplate() throws Exception{
		ek.exprtTemplate();
		return null;
	}
	
	/**
	 * 导出拟立项统计表（计划表）
	 * @return
	 * @throws Exception 
	 */
	public String exportGrantingSchedule() throws Exception{
		ek.exportGrantingSchedule();
		return null;
	}
	
	/**
	 * 导出拟立项清单（计划表）
	 * @return
	 * @throws Exception
	 */
	public String exportGrantingList() throws Exception{
		ek.exportGrantingList();
		return null;
	}

	/**
	 * 检索专家并为生成树提供参数
	 * @return
	 */
	public String searchExpert() {
		session.put("expertTree_discipline1s", discipline);//用户勾选的一级学科代码
		session.put("expertTree_selectExpertIds", selectExp);//选中的专家ids
		session.put("expertTree_universityName", uname);//用户填写的高校名称
		session.put("expertTree_expertName", ename);//用户填写的专家姓名
		//disList用于expertTree.jsp页面生成学科代码checkbox
		if(session.get("disList") == null) {
			disList = generalService.query("select s from SystemOption s where length(s.code) = 3 and s.standard like '%2009%' order by s.code asc ");
			session.put("disList", disList);
		} else {
			disList = (List<SystemOption>) session.get("disList");
		}
		return SUCCESS;
	}
	
	/**
	 * 创建专家树
	 * @return
	 */
	public String createExpertTree() {
		Map dataMap = generalService.fetchExpertData(); 
		//nodesInfo为zTree插件要求的数据格式，这里调用projectService中的公共方法实现专家树的数据组织
		nodesInfo = projectService.getNodesInfo(dataMap);
		return SUCCESS;
	}
	
//	/**
//	 * 创建专家树
//	 * @return
//	 */
//	public String createExpertTree() {
//		String expertTree_projectIds = (String) session.get("expertTree_projectIds");
//		String expertTree_discipline1s = (String) session.get("expertTree_discipline1s");
//		String expertTree_selectExpertIds = (String) session.get("expertTree_selectExpertIds");
//		String expertTree_universityName = (String) session.get("expertTree_universityName");
//		String expertTree_expertName = (String) session.get("expertTree_expertName");
//		String[] dis = null;//一级学科代码数组
//		if(expertTree_discipline1s != null && !expertTree_discipline1s.isEmpty()) {
//			dis = expertTree_discipline1s.split("\\D+");
//		}
//		Document doc;
//		doc = generalService.createExpertTree(dis, expertTree_universityName, expertTree_expertName, expertTree_selectExpertIds, expertTree_projectIds);
//		String content = doc.asXML();
//		HttpServletResponse response = ServletActionContext.getResponse();
//		response.setContentType("text/xml;charset=UTF-8");
//		PrintWriter out = null;
//		try {
//			out = response.getWriter();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		out.print(content);
//		return null;
//	}
				
	/**
	 * 修改拟立项状态
	 * @return
	 */
	public String granting() {
		generalService.setGranting(entityIds, flag);
		return SUCCESS;
	}
	
	public void validateGranting() {
		if (entityIds == null || entityIds.isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "请选择项目");
		}
	}
	
	/**
	 * 数据入库测试代码
	 * @throws Exception 
	 */
	public String importData() throws Exception{
		//初始化expertMap
		Map<String, Expert> expertMap = new HashMap<String, Expert>();
		List<Expert> experts = dao.query("select e from Expert e");
		for (Expert expert : experts) {
			expertMap.put(expert.getUniversityCode() + expert.getName(), expert);
		}
		String fileName = "F:\\SINOSS\\20140314_教育部社会科学研究管理平台数据交换职称代码.xls";
		
		//一般项目
		for(int i=1; i<29; i++){
    		String file = "F:\\SINOSS\\20140321\\2014-03-21_getApplyProject_gener_" + i + ".xml";
    		FileInputStream fInputStream = new FileInputStream(file);
    		InputStreamReader inputStreamReader = new InputStreamReader(fInputStream, "utf-8");
    		BufferedReader in = new BufferedReader(inputStreamReader);
    		StringBuffer sBuffer = new StringBuffer();
    		String line = in.readLine();
    		while (line != null) {
    			sBuffer.append(line);
    			line = in.readLine();
    		}
    		String s = sBuffer.toString().substring(117, sBuffer.toString().length()-21);;
    		String[] a = s.split("</result>");
    		int j = 0;
    		for(String b : a){
    			if(b.length()>10){
    				InputStream input = new ByteArrayInputStream((b + "</result>").getBytes("UTF-8"));
    				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    				DocumentBuilder builder = factory.newDocumentBuilder();
    				Document doc=builder.parse(input);//读取输入流
    				Application2014Importer test = new  Application2014Importer(doc, dao, tool, generalFinder, expertMap, fileName);
					test.generalRun();
					j++;				
					System.out.println("当前文档的第" + j + "条数据解析入库完毕");
				}
    		}
			System.out.println("当前已经解析到第" + i + "个文档");
		}
		System.out.println("全部解析完毕！");
		
		return SUCCESS;
	}
	

	public IGeneralService getGeneralService() {
		return generalService;
	}
	public void setGeneralService(IGeneralService generalService) {
		this.generalService = generalService;
	}
	
}
