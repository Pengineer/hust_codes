package csdc.action.project.instp;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;

import csdc.action.project.ApplicationAction;
import csdc.bean.Expert;
import csdc.bean.ProjectApplication;
import csdc.bean.ProjectApplicationReview;
import csdc.bean.SystemOption;
import csdc.bean.common.ExpertLink;
import csdc.service.IExpertService;
import csdc.service.IInstpService;
import csdc.tool.execution.finder.InstpFinder;
import csdc.tool.execution.importer.Application2014Importer;
import csdc.tool.info.GlobalInfo;

/**
 * 基地项目管理
 * @author 徐涵
 * @author fengcl
 * @version 1.0 2010.06.11
 * @version 1.1 2012.02.26to
 */
@SuppressWarnings("unchecked")
public class InstpAction extends ApplicationAction {
	
	private static final long serialVersionUID = 1L;
	private static final String HQL = "select p.id, p.projectName, p.director, u.name, count(pr.id), p.warningReviewer, p.discipline, p.reason, 'expertlink', 'expertinfo', p.instituteName from ProjectApplication p left join p.applicationReview pr, University u where p.type = 'instp' and p.year = :defaultYear and p.universityCode = u.code";
	private static final String HQLG = " group by p.id, p.projectName, p.director, u.name, p.warningReviewer, p.discipline, p.reason, p.instituteName";
	private static final String HQLC = "select COUNT(*) from ProjectApplication p, University u where p.type = 'instp' and p.year = :defaultYear and p.universityCode = u.code";
	private static final String PROJECT_TYPE = "instp";//项目类别
	private static final String CLASS_NAME = "ProjectApplication";//项目类名
	private static final String PAGENAME = "instpPage";
	private static final String PAGE_BUFFER_ID = "p.id";//缓存id
	private static final String column[] = {
		"p.projectName",
		"p.director",
		"u.name",
		"count(pr.id)",
		"p.warningReviewer",
		"p.reason",
		"p.discipline",
		"p.instituteName"
	};
	private IInstpService instpService;		//基地项目业务接口对象	
	@Autowired
	private InstpFinder instpFinder;	

	public String listHQL() {
		return InstpAction.HQL;
	}	
	public String listHQLG() {
		return InstpAction.HQLG;
	}	
	public String listHQLC() {
		return InstpAction.HQLC;
	}	
	public String projectType(){
		return InstpAction.PROJECT_TYPE;
	}	
	public String className(){
		return InstpAction.CLASS_NAME;
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
		return InstpAction.PAGE_BUFFER_ID;
	}
	
	/**
	 * 导出项目一览表
	 * @return 输入流
	 */
	public InputStream getProjectFile() throws Exception{
		//导出的Excel文件名
		Map session = ActionContext.getContext().getSession();
		String yearString = session.get("defaultYear").toString();
		fileFileName = yearString + "年度基地项目一览表.xls";
		fileFileName = new String(fileFileName.getBytes(), "ISO8859-1");
		return instpService.exportProject(exportAll, false, projectType());
	}
	
	/**
	 * 导出项目专家匹配表
	 * @return 输入流
	 */
	public InputStream getProjectReviewerFile() throws Exception {
		//导出的Excel文件名
		Map session = ActionContext.getContext().getSession();
		String yearString = session.get("defaultYear").toString();
		fileFileName = yearString + "年度基地项目专家匹配表.xls";
		fileFileName = new String(fileFileName.getBytes(), "ISO8859-1");
		return instpService.exportProject(exportAll, true, projectType());
	}
			
	/**
	 * 导出匹配更新结果
	 * @return 输入流
	 * @throws Exception
	 */
	public InputStream getMatchUpdateFile() throws Exception {
		Map session = ActionContext.getContext().getSession();
		Integer year = (Integer) session.get("defaultYear");
		fileFileName = year + "年度基地项目专家匹配更新表.xls";
		fileFileName = new String(fileFileName.getBytes(), "ISO8859-1");
		return instpService.exportMatchUpdate(year, new Date(startTime), new Date(endTime), projectType());
	}
	
//	/**
//	 * 导出基地项目匹配结果
//	 * @return
//	 * @throws Exception 
//	 */
//	public String exportInstpReviewer() throws Exception {
//		instpService.exportInstpReviewer();
//		return SUCCESS;
//	}	
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
			disList = instpService.query("select s from SystemOption s where length(s.code) = 3 and s.standard like '%2009%' order by s.code asc ");
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
		Map dataMap = instpService.fetchExpertData(); 
		//nodesInfo为zTree插件要求的数据格式，这里调用service中的方法实现专家树的数据组织
		nodesInfo = projectService.getNodesInfo(dataMap);
		return SUCCESS;
	}
	
	/**
	 * 参评或退评项目列表和初级检索条件
	 */
	@Override
	public Object[] simpleSearchCondition() {
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
		if (keyword != null && !keyword.isEmpty()) {
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
				List<Object> dislist = instpService.getDisciplineCode(keyword);
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
				s_hql = "LOWER(p.instituteName) like :keyword";
				map.put("keyword", "%" + keyword + "%");
			}  else if (searchType == 7) {
				s_hql = "LOWER(p.reason) like :keyword";
				map.put("keyword", "%" + keyword + "%");
			} else {
				s_hql = "(LOWER(p.projectName) like :keyword or LOWER(p.director) like :keyword or LOWER(u.name) like :keyword or LOWER(p.instituteName) like :keyword or LOWER(p.reason) like :keyword";
				map.put("keyword", "%" + keyword + "%");
				List<Object> dislist = instpService.getDisciplineCode(keyword);
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
		hql.append(HQLG);
		c_hql.append(s_hql);
		
		StringBuffer HQL4InstpExport = new StringBuffer();
		HQL4InstpExport.append(c_hql.toString());
		
		if(projectId != null && projectId.length() > 0) {
			String[] tmp = projectId.split(";");
			String ids = "'" + tmp[0] + "' ";
			for(int i = 1; i < tmp.length; i++) {
				ids += ", '" + tmp[i] + "' ";
			}
			hql.append(" and p.id in ( " + ids + " ) order by p.projectName asc, p.id asc ");
			HQL4InstpExport.append(" and p.id in ( " + ids + " ) order by p.projectName asc, p.id asc ");
			
			// 调用初级检索功能
			return new Object[]{
				hql.toString(),
				map,
				0,
				null,
				null
			};
		}
		//为列表页面项目导出做准备
		String tmp = HQL4InstpExport.toString();
		tmp = tmp.replace(" count(pr.project.id) asc,", " p.projectName asc,");
		tmp = tmp.replace(" count(pr.project.id) desc,", " p.projectName desc,");
//		String exporthql = instpService.createQueryHql(tmp, map);
		String exporthql = tmp;
		session.put("HQL4InstpExport", exporthql.replaceAll("COUNT\\(\\*\\)", "p"));
		session.put("Map4InstpExport", map);
		
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
		if(listType == 1){//参评,退评项目列表
			Map application = ActionContext.getContext().getApplication();
			//获取学科名称与学科代码的映射
			HashMap<String, String> disMap = (HashMap<String, String>) application.get("disMap");
			
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
				expertLink = instpService.getExpert(projectId);
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
					disciplineCode = instpService.getDisciplineInfo(disMap, disciplineCode);
					item[6] = disciplineCode;
				}
				//处理专家信息
				item[8] = expertLink;//评审专家
				item[9] = value;//专家信息
				
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
//		if (project.getInstpName() == null || project.getInstpName().isEmpty()) {
//			this.addFieldError(GlobalInfo.ERROR_INFO, "项目名称不得为空");
//		} else if (project.getInstpName().length() > 10) {
//			this.addFieldError(GlobalInfo.ERROR_INFO, "项目名称过长");
//		}
//		if (project.getInstpType().length() > 10) {
//			this.addFieldError(GlobalInfo.ERROR_INFO, "项目类型过长");
//		}
//		if (project.getResearchType().length() > 10) {
//			this.addFieldError(GlobalInfo.ERROR_INFO, "研究类型过长");
//		}
		if (project.getDisciplineType().length() > 10) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "学科类型过长");
		}
		if (project.getDiscipline().length() > 10) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "依托学科过长");
		}
		if (project.getUniversityCode().length() > 10) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "高校代码过长");
		}
//		if (project.getApplyDate() != null) {
//			if (project.getApplyDate().compareTo(new Date()) > 0) {
//				this.addFieldError(GlobalInfo.ERROR_INFO, "不合理的申请日期");
//			}
//		}
//		if (project.getAddDate() != null) {
//			if (project.getAddDate().compareTo(new Date()) > 0) {
//				this.addFieldError(GlobalInfo.ERROR_INFO, "不合理的立项时间");
//			}
//		}
//		if (project.getAuditStatus().length() > 10) {
//			this.addFieldError(GlobalInfo.ERROR_INFO, "审核状态过长");
//		}
//		if (project.getIsReviewable() != 0 && project.getIsReviewable() != 1) {
//			this.addFieldError(GlobalInfo.ERROR_INFO, "请选择是否参加审批");
//		}
//		if (project.getPlanEndDate() != null) {
//			if (project.getPlanEndDate().compareTo(new Date()) < 0) {
//				this.addFieldError(GlobalInfo.ERROR_INFO, "预计结项日期不合法");
//			}
//		}
//		if (!Pattern.matches("[0-9]{0,7}.[0-9]{0,5}", project.getOtherFee().trim())) {
//			this.addFieldError(GlobalInfo.ERROR_INFO, "其他费用不合法");
//		}
//		if (!Pattern.matches("[0-9]{0,7}.[0-9]{0,5}", project.getApplyFee().trim())) {
//			this.addFieldError(GlobalInfo.ERROR_INFO, "申请费用不合法");
//		}
//		if (project.getMembers().length() > 100) {
//			this.addFieldError(GlobalInfo.ERROR_INFO, "项目成员过长");
//		}
//		if (project.getFinalResultType().length() > 10) {
//			this.addFieldError(GlobalInfo.ERROR_INFO, "最终成果类型过长");
//		}
		
		if (project.getDirector().length() > 10) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "姓名过长");
		}
//		if (!Pattern.matches("[男女]", project.getGender().trim())) {
//			this.addFieldError(GlobalInfo.ERROR_INFO, "请选择性别");
//		}
//		if (project.getApplyDate() != null) {
//			if (project.getApplyDate().compareTo(new Date()) > 0) {
//				this.addFieldError(GlobalInfo.ERROR_INFO, "生日不合法");
//			}
//		}
//		if (!Pattern.matches("男|女", project.getIdcard().trim())) {
//			this.addFieldError(GlobalInfo.ERROR_INFO, "请选择性别");
//		}
//		if (project.getIdcard() == null || project.getIdcard().isEmpty()) {
//			this.addFieldError(GlobalInfo.ERROR_INFO, "身份证号不得为空");
//		} else if (!Pattern.matches("(\\d{6}((19)|(2\\d))\\d{2}((0\\d)|(1[0-2]))((3[01])|([0-2]\\d))\\d{3}(\\d|x))|(\\d{8}((0\\d)|(1[0-2]))((3[01])|([0-2]\\d))\\d{3})", project.getIdcard().trim())) {
//			this.addFieldError(GlobalInfo.ERROR_INFO, "身份证号不合法");
//		}
//		if (project.getForeign().length() > 10) {
//			this.addFieldError(GlobalInfo.ERROR_INFO, "外语过长");
//		}
//		if (project.getEducation().length() > 10) {
//			this.addFieldError(GlobalInfo.ERROR_INFO, "学历过长");
//		}
//		if (project.getDegree().length() > 10) {
//			this.addFieldError(GlobalInfo.ERROR_INFO, "学位过长");
//		}
//		if (project.getDepartment().length() > 10) {
//			this.addFieldError(GlobalInfo.ERROR_INFO, "院系过长");
//		}
		if (project.getTitle().length() > 10) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "职称过长");
		}
//		if (project.getJob().length() > 10) {
//			this.addFieldError(GlobalInfo.ERROR_INFO, "职务过长");
//		}
//		if (project.getEmail() == null || project.getEmail().isEmpty()) {
//			this.addFieldError(GlobalInfo.ERROR_INFO, "邮箱不得为空");
//		}else if (!Pattern.matches("[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+", project.getEmail().trim())) {
//			this.addFieldError(GlobalInfo.ERROR_INFO, "邮箱格式不合法");
//		}
//		if (project.getMobile() != null && !project.getMobile().isEmpty()) {
//			if (!Pattern.matches("\\b(13\\d{9})|(15\\d{9})|(189\\d{8})\\b|无", project.getMobile().trim())) {
//				this.addFieldError(GlobalInfo.ERROR_INFO, "移动电话不合法");
//			}
//		}
//		if (project.getPhone() != null && !project.getPhone().isEmpty()) {
//			if (!Pattern.matches("(0\\d{2}-\\d{8}|0\\d{3}-\\d{7}|0\\d{3}-\\d{8}|\\d{11})|无", project.getPhone().trim())) {
//				this.addFieldError(GlobalInfo.ERROR_INFO, "固定电话不合法");
//			}
//		}
//		if (project.getAddress().length() > 100) {
//			this.addFieldError(GlobalInfo.ERROR_INFO, "地址过长");
//		}
//		if (!Pattern.matches("\\d{4,10})", project.getPostcode().trim())) {
//			this.addFieldError(GlobalInfo.ERROR_INFO, "邮编不合法");
//		}
	}
			
	/**
	 * 修改项目信息
	 * @return SUCCESS跳转查看
	 */
	public String modify() {
		entityId = (String) session.get("projectId");
		listType = (Integer) session.get("projectListType");
		ProjectApplication mproject = (ProjectApplication) baseService.query(ProjectApplication.class, project.getId());
//		mproject.setAddDate(project.getAddDate());
//		mproject.setAddress(project.getAddress());
//		mproject.setApplyDate(project.getApplyDate());
//		mproject.setApplyFee(project.getApplyFee());
//		mproject.setAuditStatus(project.getAuditStatus());
//		mproject.setBirthday(project.getBirthday());
//		mproject.setDegree(project.getDegree());
//		mproject.setDepartment(project.getDepartment());
		mproject.setDirector(project.getDirector());
		mproject.setDiscipline(project.getDiscipline());
		mproject.setDisciplineType(project.getDisciplineType());
//		mproject.setEducation(project.getEducation());
//		mproject.setEmail(project.getEmail());
//		mproject.setFinalResultType(project.getFinalResultType());
//		mproject.setForeign(project.getForeign());
//		mproject.setGender(project.getGender());
//		mproject.setIdcard(project.getIdcard());
		if(project.getIsReviewable() == 0) {
			mproject.setWarningReviewer("");
			// 项目改为不参与评审后，分配的评审专家信息要删除
			List<ProjectApplicationReview> list = baseService.query(" select pr from ProjectApplicationReview pr where pr.project.id = '" + project.getId() + "' " );
			for(int i = 0; i < list.size(); i++) {
				baseService.delete(list.get(i));
			}
		}
		mproject.setIsReviewable(project.getIsReviewable());
//		mproject.setJob(project.getJob());
//		mproject.setMembers(project.getMembers());
//		mproject.setMobile(project.getMobile());
//		mproject.setOtherFee(project.getOtherFee());
//		mproject.setPhone(project.getPhone());
//		mproject.setPlanEndDate(project.getPlanEndDate());
//		mproject.setPostcode(project.getPostcode());
//		mproject.setInstpName(project.getInstpName());
//		mproject.setInstpType(project.getInstpType());
//		mproject.setResearchType(project.getResearchType());
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
//		if (project.getInstpName() == null || project.getInstpName().isEmpty()) {
//			this.addFieldError(GlobalInfo.ERROR_INFO, "项目名称不得为空");
//		} else if (project.getInstpName().length() > 200) {
//			this.addFieldError(GlobalInfo.ERROR_INFO, "项目名称过长");
//		}
//		if (project.getInstpType().length() > 100) {
//			this.addFieldError(GlobalInfo.ERROR_INFO, "项目类型过长");
//		}
//		if (project.getResearchType().length() > 100) {
//			this.addFieldError(GlobalInfo.ERROR_INFO, "研究类型过长");
//		}
		if (project.getDisciplineType().length() > 10) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "学科类型过长");
		}
		if (project.getDiscipline().length() > 100) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "依托学科过长");
		}
		if (project.getUniversityCode().length() > 10) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "高校代码过长");
		}
//		if (project.getApplyDate() != null) {
//			if (project.getApplyDate().compareTo(new Date()) > 0) {
//				this.addFieldError(GlobalInfo.ERROR_INFO, "不合理的申请日期");
//			}
//		}
//		if (project.getAddDate() != null) {
//			if (project.getAddDate().compareTo(new Date()) > 0) {
//				this.addFieldError(GlobalInfo.ERROR_INFO, "不合理的立项时间");
//			}
//		}
//		if (project.getAuditStatus().length() > 10) {
//			this.addFieldError(GlobalInfo.ERROR_INFO, "审核状态过长");
//		}
//		if (project.getPlanEndDate() != null) {
//			if (project.getPlanEndDate().compareTo(new Date()) < 0) {
//				this.addFieldError(GlobalInfo.ERROR_INFO, "预计结项日期不合法");
//			}
//		}
//		if (!Pattern.matches("[0-9]{0,7}.[0-9]{0,5}", project.getOtherFee().trim())) {
//			this.addFieldError(GlobalInfo.ERROR_INFO, "其他费用不合法");
//		}
//		if (!Pattern.matches("[0-9]{0,7}.[0-9]{0,5}", project.getApplyFee().trim())) {
//			this.addFieldError(GlobalInfo.ERROR_INFO, "申请费用不合法");
//		}
//		if (project.getMembers().length() > 400) {
//			this.addFieldError(GlobalInfo.ERROR_INFO, "项目成员过长");
//		}
//		if (project.getFinalResultType().length() > 10) {
//			this.addFieldError(GlobalInfo.ERROR_INFO, "最终成果类型过长");
//		}
		
		if (project.getDirector().length() > 10) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "姓名过长");
		}
//		if (!Pattern.matches("[男女]", project.getGender().trim())) {
//			this.addFieldError(GlobalInfo.ERROR_INFO, "请选择性别");
//		}
//		if (project.getApplyDate() != null) {
//			if (project.getApplyDate().compareTo(new Date()) > 0) {
//				this.addFieldError(GlobalInfo.ERROR_INFO, "生日不合法");
//			}
//		}
//		if (project.getIdcard() == null || project.getIdcard().isEmpty()) {
//			this.addFieldError(GlobalInfo.ERROR_INFO, "身份证号不得为空");
//		} else if (!Pattern.matches("(\\d{6}((19)|(2\\d))\\d{2}((0\\d)|(1[0-2]))((3[01])|([0-2]\\d))\\d{3}(\\d|x))|(\\d{8}((0\\d)|(1[0-2]))((3[01])|([0-2]\\d))\\d{3})", project.getIdcard().trim())) {
//			this.addFieldError(GlobalInfo.ERROR_INFO, "身份证号不合法");
//		}
//		if (project.getForeign().length() > 10) {
//			this.addFieldError(GlobalInfo.ERROR_INFO, "外语过长");
//		}
//		if (project.getEducation().length() > 10) {
//			this.addFieldError(GlobalInfo.ERROR_INFO, "学历过长");
//		}
//		if (project.getDegree().length() > 10) {
//			this.addFieldError(GlobalInfo.ERROR_INFO, "学位过长");
//		}
//		if (project.getDepartment().length() > 10) {
//			this.addFieldError(GlobalInfo.ERROR_INFO, "院系过长");
//		}
//		if (project.getTitle().length() > 10) {
//			this.addFieldError(GlobalInfo.ERROR_INFO, "职称过长");
//		}
//		if (project.getJob().length() > 10) {
//			this.addFieldError(GlobalInfo.ERROR_INFO, "职务过长");
//		}
//		if (project.getEmail() == null || project.getEmail().isEmpty()) {
//			this.addFieldError(GlobalInfo.ERROR_INFO, "邮箱不得为空");
//		}else if (!Pattern.matches("[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+", project.getEmail().trim())) {
//			this.addFieldError(GlobalInfo.ERROR_INFO, "邮箱格式不合法");
//		}
//		if (project.getMobile() != null && !project.getMobile().isEmpty()) {
//			if (!Pattern.matches("\\b(13\\d{9})|(15\\d{9})|(189\\d{8})\\b|无", project.getMobile().trim())) {
//				this.addFieldError(GlobalInfo.ERROR_INFO, "移动电话不合法");
//			}
//		}
//		if (project.getPhone() != null && !project.getPhone().isEmpty()) {
//			if (!Pattern.matches("(0\\d{2}-\\d{8}|0\\d{3}-\\d{7}|0\\d{3}-\\d{8}|\\d{11})|无", project.getPhone().trim())) {
//				this.addFieldError(GlobalInfo.ERROR_INFO, "固定电话不合法");
//			}
//		}
//		if (project.getAddress().length() > 100) {
//			this.addFieldError(GlobalInfo.ERROR_INFO, "地址过长");
//		}
		//if (!Pattern.matches("\\d{4,10})", project.getPostcode().trim())) {
		//	this.addFieldError(GlobalInfo.ERROR_INFO, "邮编不合法");
		//}
}
	/**
	 * 数据入库代码
	 * @throws Exception 
	 */
	public String importData() throws Exception{
		//初始化expertMap
		Map<String, Expert> expertMap = new HashMap<String, Expert>();
		List<Expert> experts = dao.query("select e from Expert e");
		for (Expert expert : experts) {
			expertMap.put(expert.getUniversityCode() + expert.getName(), expert);
		}
		
		//基地项目
		String fileName = "F:\\SINOSS\\20140321\\2014-03-21_getApplyProject_base.xml";
		String file = "F:\\SINOSS\\20140314_教育部社会科学研究管理平台数据交换职称代码.xls";
		Application2014Importer test = new  Application2014Importer(fileName, dao, tool, instpFinder, expertMap,file);
		int existingExperts = test.instpRun();
		System.out.println("2014年度基地项目专家库中存在申请项目的专家数量为：" + existingExperts);
		
		return SUCCESS;
	}
	
	public String getInstpId() {
		return projectId;
	}
	public void setInstpId(String projectId) {
		this.projectId = projectId;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	public void setExpertService(IExpertService expertService) {
		this.expertService = expertService;
	}

	public IExpertService getExpertService() {
		return expertService;
	}
	public IInstpService getInstpService() {
		return instpService;
	}
	public void setInstpService(IInstpService instpService) {
		this.instpService = instpService;
	}
	
}

