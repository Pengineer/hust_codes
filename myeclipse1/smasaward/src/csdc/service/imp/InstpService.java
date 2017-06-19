package csdc.service.imp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.dom4j.Document;

import com.opensymphony.xwork2.ActionContext;

import csdc.bean.Expert;
import csdc.bean.ProjectApplication;
import csdc.bean.ProjectApplicationReview;
import csdc.bean.University;
import csdc.service.IInstpService;
import csdc.tool.ApplicationContainer;
import csdc.tool.DatetimeTool;
import csdc.tool.ExpertTreeItem;
import csdc.tool.HSSFExport;
import csdc.tool.SortExpert;
import csdc.tool.execution.importer.Tools;

@SuppressWarnings("unchecked")
public class InstpService extends ProjectService implements IInstpService {
	
	/**
	 * 若正式参评，至少需正式参评的项目数
	 */
	Integer officalReviewLowerLimit;
	
	/**
	 * 每位专家正式评审的最大项目数
	 */
	Integer officalReviewUpperLimit;
	
	/**
	 * 每位专家备选评审的最大项目数
	 */
	Integer backupReviewUpperLimit;
	
	/**
	 * 每个项目所需部属高校正式评审专家数
	 */
	Integer subjectOfficalMinistryReviewerNumber;
	
	/**
	 * 每个项目所需地方高校正式评审专家数
	 */
	Integer subjectOfficalLocalReviewerNumber;
	
	/**
	 * 每个项目所需正式评审专家数
	 */
	Integer subjectOfficalReviewerNumber;
	
	/**
	 * 每个项目所需备用评审专家数
	 */
	Integer subjectBackupReviewerNumber;

	private Tools tools;//TODO  ???
	
	/**
	 * 群删项目
	 */
	public void deleteProjects(List<String> projectids) {
		if (projectids != null) {
			for (int i = 0; i < projectids.size(); i++) {
				this.delete(ProjectApplication.class, projectids.get(i));
			}
		}
	}
		
	public void updateWarningReviewer(ProjectApplication project) {
		List<ProjectApplicationReview> list = this.query("select pr from ProjectApplicationReview pr where pr.project.id = '" + project.getId() + "' order by pr.priority asc");
		HashSet<String> uSet = new HashSet<String>();
		int supCnt = 0, ordCnt = 0, i;
		for (i = 0; i < list.size() && i < 5; i++){
			Expert expert = (Expert) this.query(Expert.class, list.get(i).getReviewer().getId());
			University u = (University) this.query(University.class, expert.getUniversityCode());
			uSet.add(expert.getUniversityCode());
			if ("360".equals(u.getFounderCode())){
				supCnt++;
			} else {
				ordCnt++;
			}
		}

		ActionContext context = ActionContext.getContext();
		Map application = context.getApplication();

		/**
		 * 每个项目所需部属高校正式评审专家数
		 */
		Integer subjectOfficalMinistryReviewerNumber = (Integer) application.get("GeneralFormalReviewerNInBU");
		
		/**
		 * 每个项目所需地方高校正式评审专家数
		 */
		Integer subjectOfficalLocalReviewerNumber = (Integer) application.get("GeneralFormalReviewerNInDU");

		
		HashSet<String> wr = new HashSet<String>();
		if (supCnt > subjectOfficalMinistryReviewerNumber || ordCnt > subjectOfficalLocalReviewerNumber){
			wr.add("突破正式评审专家中部属地方" + subjectOfficalMinistryReviewerNumber + ":" + subjectOfficalLocalReviewerNumber + "限制");
		}
		if (uSet.size() != i){
			wr.add("突破评审人高校不同限制");
		}
		if(wr.size() == 0)
			project.setWarningReviewer(null);
		else
			project.setWarningReviewer(wr.toString());
		this.modify(project);
	}
	
	/**
	 * 导出基地项目评审信息
	 * @param exportAll
	 * @throws Exception
	 */
	public void exportInstpReviewer() throws Exception {
		String[] oo = new String[]{
			"基地项目评审表",
			"序号",
			"学科类别",
			"基地所在高校",
			"基地名称",
			"项目名称",
			"学科方向",
			"学科代码",
			"项目负责人",
			"项目负责人所在高校",
			"职称",
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
			"专家5代码",
			"专家6高校",
			"专家6高校代码",
			"专家6姓名",
			"专家6代码",
			"专家7高校",
			"专家7高校代码",
			"专家7姓名",
			"专家7代码",
			"专家8高校",
			"专家8高校代码",
			"专家8姓名",
			"专家8代码",
			"专家9高校",
			"专家9高校代码",
			"专家9姓名",
			"专家9代码",
			"专家10高校",
			"专家10高校代码",
			"专家10姓名",
			"专家10代码"
		};
		HttpServletResponse response = ServletActionContext.getResponse();
		Vector v = new Vector();
		
		HashMap<String, Expert> expMap = new HashMap<String, Expert>();
		List<Expert> eList = this.query("select e from Expert e");
		for (Expert expert : eList) {
			expMap.put(expert.getId(), expert);
		}
		
		List<ProjectApplicationReview> prList = this.query("select pr from ProjectApplicationReview pr where pr.type = 'instp' order by pr.project.id asc, pr.priority asc");
		int idx = 0;
		
		List<ProjectApplication> list = this.query("select p from ProjectApplication p where p.type = 'instp' order by p.id asc");

		
		int number = 1;
		for (ProjectApplication p : list) {
			List pList = new ArrayList<Object>();
			pList.add(number++);
			pList.add(p.getDisciplineType());
			pList.add(p.getUniversityName());
			pList.add(p.getInstituteName());
			pList.add(p.getProjectName());
			pList.add(p.getDisciplineDirection());
			pList.add(p.getDiscipline());
			pList.add(p.getDirector());
			pList.add(p.getDirectorUniversity());
			pList.add(p.getTitle());
			pList.add(p.getNote());

			while (idx < prList.size() && prList.get(idx).getProject().getId().compareTo(p.getId()) <= 0){
				if (prList.get(idx).getProject().getId().equals(p.getId())) {
					Expert expert = expMap.get(prList.get(idx).getReviewer().getId());
					pList.add(tools.getUnivByCode(expert.getUniversityCode()).getName());
					pList.add(expert.getUniversityCode());
					pList.add(expert.getName());
					pList.add(expert.getNumber());
				}
				idx++;
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
	}
	
	/**
	 * 判断是否名牌高校
	 * @param universitycode 高校代码
	 * @return 是否名牌高校
	 */
	public boolean isFamousUniversity(String universitycode) {
		//University university = (University) query(" select u from University u where u.universityCode = '" + universitycode + "' ").get(0);
		String[] dictionary = {
				"10001", //北大
				"10002", //人大
				"10003", //清华
				"10027", //北京师范大学
				"10055", //南开大学
				"10183", //吉林大学
				"10246", //复旦
				"10269", //华东师范大学
				"10284", //南京大学
				"10335", //浙江大学
				"10384", //厦门大学
				"10486", //武汉大学
				"10558", //中山大学
				"10610"  //四川大学
				};
		for(int i = 0; i < dictionary.length; i++) {
			if(dictionary[i].equals(universitycode)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 获得优先级
	 * @param prior
	 * @param nuicode
	 * @return
	 */
	public int getPriority(String prior, String nuicode) {
		//TODO 获得优先级
		int priority = 7;
		if(prior != null) {
			prior = prior.trim();
			try {
				priority = Integer.parseInt(prior);
				return priority;
			} catch (NumberFormatException e) {
				return priority;
			}
		} else {
			if(isFamousUniversity(nuicode))
				return 6;
			else
				return priority;
		}
	}
	
//	public void matchExpert(Integer currentYear, List<String> expertIds, List<String> projectids, List<String> rejectExpertIds) throws Exception {
//		InstpReviewerMatcher matcher = (InstpReviewerMatcher) SpringBean.getBean("instpReviewerMatcher", ApplicationContainer.sc);
//		matcher.matchExpert(currentYear, expertIds, projectids, rejectExpertIds);
//	}
	
//	public void updateWarningReviewer(List<String> projectIds, Integer year) {
//		MatcherWarningUpdater updater = (MatcherWarningUpdater) SpringBean.getBean("instpMatcherWarningUpdater", ApplicationContainer.sc);
//		updater.update(projectIds, year);
//	}
	
	public Object[] getMemberInfo(ProjectApplication project) {
		if (project == null || project.getMembers() == null) {
			return new Object[]{new HashSet<String>(), new HashSet<String>()};
		}
		HashMap<String, String> univNameCodeMap = (HashMap<String, String>) ApplicationContainer.sc.getAttribute("univNameCodeMap");
		
		String[] members = project.getMembers().split("\\)[,，]\\s*");
		Set<String> names = new HashSet<String>();
		Set<String> univCodes = new HashSet<String>();
		for (String string : members) {
			int splitIndex = string.indexOf('(');
			String personName = string.substring(0, splitIndex);
			String agencyName = string.substring(splitIndex + 1);
			for (int i = 0; i < agencyName.length(); i++) {
				for (int j = agencyName.length(); j > i; j--) {
					String subName = agencyName.substring(i, j);
					if (univNameCodeMap.get(subName) != null) {
						univCodes.add(univNameCodeMap.get(subName));//项目成员所属高校
						break;
					}
				}
			}
			names.add(personName);//项目成员
		}
		names.add(project.getDirector());//项目负责人
		univCodes.add(project.getUniversityCode());//项目所属高校
		return new Object[]{names, univCodes};
	}
	
	@Override
	public Map exportProjectDealWith(int exportAll, boolean containReviewer, Map container) {
		ActionContext context = ActionContext.getContext();
		Map session = context.getSession();
		int year = Integer.parseInt(container.get("year").toString());
		HashMap<String, String> univMap = (HashMap<String, String>) container.get("univMap");//高校代码 -> 高校名称
		HashMap<String, String> discMap = (HashMap<String, String>) container.get("discMap");//学科代码 -> 学科名称
		HashMap<String, Expert> expMap = (HashMap<String, Expert>) container.get("expMap");	//专家ID -> 专家实体
		HashMap<String, List<String>> irsMap = (HashMap<String, List<String>>) container.get("irsMap"); //项目ID -> 评审专家列表
		List<ProjectApplication> projects = null;
		if (exportAll != 0){
			projects = this.query("select p from ProjectApplication p where p.type = 'instp' and p.year = ? order by p.id asc", year);
		} else {
			projects = this.query((String) session.get("HQL4InstpExport"), (Map)session.get("Map4InstpExport"));
		}
		List<Object[]> dataList = new ArrayList<Object[]>();
		for (ProjectApplication project : projects) {
			List pList = new ArrayList<Object>();
			pList.add(project.getAuditStatus());
			pList.add(project.getFile());
			pList.add(project.getNumber());
			pList.add(project.getProjectName());
			pList.add(project.getProjectType());
			pList.add(project.getDisciplineType());
			String discString = "";//学科代码+学科名称
			if(null != project.getDiscipline() && !project.getDiscipline().isEmpty()){
				String disc[] = project.getDiscipline().split("; ");
				for (String s : disc) {
					discString += (discString.isEmpty() ? "" : "、") + ((discMap.get(s)==null) ? "" : discMap.get(s)) + "(" + s + ")";
				}
			}
			pList.add(discString);
			pList.add(DatetimeTool.getYearMonthDateString(project.getApplyDate()));
			pList.add(DatetimeTool.getYearMonthDateString(project.getPlanEndDate()));
			pList.add(project.getFinalResultType());
			pList.add(project.getUniversityCode());
			pList.add(project.getUniversityName());
			pList.add(project.getInstituteName());
			pList.add(project.getDirector());
			pList.add(DatetimeTool.getYearMonthDateString(project.getBirthday()));
			pList.add(project.getDirectorUniversity());
			pList.add(project.getTitle());
			pList.add(project.getJob());
			pList.add(project.getMembers());
			pList.add("");//备注
			
			if (containReviewer) {
				List<String> expertIds = irsMap.get(project.getId());
				if (null != expertIds) {
					for (String expertId : expertIds) {
						Expert expert = expMap.get(expertId);
						pList.add(univMap.get(expert.getUniversityCode()));
						pList.add(expert.getUniversityCode());
						pList.add(expert.getName());
						pList.add(expert.getNumber());
					}
				}
			}
			
			Object obj[] = pList.toArray(new Object[0]);
			for (int i = 0; i < obj.length; i++){
				if (obj[i] == null){
					obj[i] = "";
				}
			}
			dataList.add(obj);
		}
		
		String header = null;
		String[] title = null;
		if (containReviewer) {
			header = "项目专家匹配表";
			title = new String[]{"审核状态","申报书文件名","项目编号","项目名称","项目类别",
					"学科门类","研究方向及代码","申报日期","计划完成日期","最终成果形式",
					"高校代码","高校名称","基地名称","申请人","生日","申请人所属高校","职称","职务","成员信息[姓名(工作单位)]","备注",
					"专家1高校","专家1高校代码","专家1姓名","专家1代码",
					"专家2高校","专家2高校代码","专家2姓名","专家2代码",
					"专家3高校","专家3高校代码","专家3姓名","专家3代码",
					"专家4高校","专家4高校代码","专家4姓名","专家4代码",
					"专家5高校","专家5高校代码","专家5姓名","专家5代码"};
		}else {
			header = "项目一览表";
			title = new String[]{"审核状态","申报书文件名","项目编号","项目名称","项目类别",
					"学科门类","研究方向及代码","申报日期","计划完成日期","最终成果形式",
					"高校代码","高校名称","基地名称","申请人","生日","申请人所属高校","职称","职务","成员信息[姓名(工作单位)]","备注"};
		}
		Map map = new HashMap();
		map.put("dataList", dataList);
		map.put("header", header);
		map.put("title", title);
		return map;
	}

	/**
	 * 查询专家树所需数据
	 * @return	dataMap：<br>
	 * 包括专家总数totalExpert和expertTreeItem的list
	 */
	public Map fetchExpertData(){
		//学科代码与学科名称的映射
		ActionContext context = ActionContext.getContext();
		Map session = context.getSession();
		session.remove("nodesInfo4LevelOnes");
		session.remove("nodesInfoMap");
		
		int totalExpert = 0;//专家计数
		boolean hasProjectIds = false;//projectIds是否为空：true不为空，false为空
		//统计项目成员和成员所属高校代码的集合
		Set<String> expertNameList = new HashSet<String>();//去除的名字集合
		Set<String> uniCodeList = new HashSet<String>();//去除的高校代码集合
		String projectIds = (String) session.get("expertTree_projectIds");//获取选择的项目的ids
		if(projectIds != null && !projectIds.isEmpty()) {
			hasProjectIds = true;
			String[] ids = projectIds.split("[^a-zA-Z0-9]+");
			for(int i = 0; i < ids.length; i++) {
				ProjectApplication p = (ProjectApplication) query(ProjectApplication.class, ids[i]);
				if(p != null) {
					Object[] objects = getMemberInfo(p);
					expertNameList.addAll((Set<String>) objects[0]);//当前项目的成员集合
					uniCodeList.addAll((Set<String>) objects[1]);//当前项目的成员所在高校代码集合
				}
			}
		}
		
		boolean useSearch = false;//是否输入检索条件（高校名称或专家姓名）：true使用，false未使用
		boolean useDiscipline = false;//是否勾选学科（获取勾选了项目）：true勾选，false未勾选
		Integer defaultYear = (Integer) session.get("defaultYear");
		String universityName = (String) session.get("expertTree_universityName");
		String expertName = (String) session.get("expertTree_expertName");
		String expertTree_discipline1s = (String) session.get("expertTree_discipline1s");//获取选中的一级学科代码
		String[] disciplineLevelOnes = null;//一级学科代码数组
		if(expertTree_discipline1s != null && !expertTree_discipline1s.isEmpty()) {
			disciplineLevelOnes = expertTree_discipline1s.split("\\D+");
		}
		
		Map paraMap = new HashMap();
		Map discipline = selectSpecialityTitleInfo();
		paraMap.put("seniorTitles", discipline.get("seniorTitles"));	//正高级、高级职称专家
		//筛选所属高校为部署高校，属性为参评、非重点人、专职人员，手机和邮箱全非空，当前年没申报项目， 评价等级大于限制阈值，当前时间6个月内入库，获取人才奖励资助的专家
		StringBuffer hql4Reviewer = new StringBuffer("select expert.id, expert.name, expert.specialityTitle, expert.discipline, COUNT(pr), u.name, u.abbr, expert.universityCode from Expert expert left join expert.applicationReview pr with pr.year = " + defaultYear + " and pr.type = 'instp', University u where expert.universityCode = u.code");
		hql4Reviewer.append(selectReviewMatchExpertForManual());
		if(universityName != null && universityName.length() > 0) {
			useSearch = true;
			hql4Reviewer.append(" and u.name like '%" + universityName + "%' ");
		}
		if(expertName != null && expertName.length() > 0) {
			useSearch = true;
			hql4Reviewer.append(" and expert.name like '%" + expertName + "%' ");
		}
		if(disciplineLevelOnes != null && disciplineLevelOnes.length > 0 && !disciplineLevelOnes[0].isEmpty()) {
			useDiscipline = true;
			hql4Reviewer.append(" and ( expert.discipline like '" + disciplineLevelOnes[0] + "%' or expert.discipline like '%; " + disciplineLevelOnes[0] + "%'");
			for(int i = 1; i < disciplineLevelOnes.length; i++) {
				hql4Reviewer.append(" or expert.discipline like '" + disciplineLevelOnes[i] + "%' or expert.discipline like '%; " + disciplineLevelOnes[i] + "%'");
			}
			hql4Reviewer.append(" ) ");
		}
		if(!useSearch && !useDiscipline) {//如果没有任何检索条件，查询为空
			hql4Reviewer.append(" and 1 = 0 ");
		}
		
		hql4Reviewer.append(" group by expert.id, expert.name, expert.specialityTitle, expert.discipline, u.name, u.abbr, expert.universityCode ");
		//筛选出来的参评专家列表
		Date begin = new Date();
//		List expertList = this.query(hql.toString(), paraMap);
		List expertList = this.query(hql4Reviewer.toString(),paraMap);
		long end = System.currentTimeMillis();
		System.out.println("查询tree数据耗时：" + (new Date().getTime() - begin.getTime()) + "ms");
		
		//高校参评专家数量
		HashMap<String, String> univReviewerCnt = new HashMap<String, String>();
		String hql4urCnt = "select u.name, count(distinct pr.reviewer.id) from University u, ProjectApplicationReview pr, Expert expert where pr.type = 'instp' and u.code = expert.universityCode and expert.id = pr.reviewer.id and pr.year = ? group by u.name ";
		List<Object[]> univReviewers = this.query(hql4urCnt, defaultYear);
		for (Object[] obj : univReviewers) {
			univReviewerCnt.put((String)obj[0], obj[1]+"");
		}
		
		//专家详情map
		Map expDetailMap = new HashMap();
		List<ExpertTreeItem> expertTreeItems = new ArrayList();
		//专家信息处理，去除当前项目成员及当前项目成员所在高校的所有专家
		for (Object expert : expertList) {
			Object[] o = (Object[]) expert;
			if((!useDiscipline || checkDisciplineLegal(disciplineLevelOnes, (String) o[3])) && (!hasProjectIds || (hasProjectIds && !expertNameList.contains((String)o[1]) && !uniCodeList.contains((String)o[7]) )) ) {
				//高校参评专家数量
				String univExpertCnt = univReviewerCnt.get((String)o[5]); 
				if (null == univExpertCnt) {
					univExpertCnt = "0";
				}
				ExpertTreeItem expertTreeItem = new ExpertTreeItem((String)o[0], (String)o[1], (String)o[2], (String)o[3], o[4].toString(), (String)o[5], o[6].toString(), univExpertCnt);
				expDetailMap.put((String)o[0], expertTreeItem);
				totalExpert++;
				String expDiscipline = (String) o[3];//专家学科代码
				expDiscipline = (expDiscipline == null) ? "0000000" : expDiscipline;
				String[] expertDisciplines = expDiscipline.split("\\D+");
				/*同一个专家因为有多个所属学科代码，所以在专家树中可能出现多次，为了区分他在tree中的所属的学科位置，在他的id后面加上0,1,2,3...等用于区分
				 	例如：某个专家id为4028d88a29920f0901299215f71111ab；则在不同的学科类别中表示成：4028d88a29920f0901299215f71111ab0、
				 	4028d88a29920f0901299215f71111ab1、4028d88a29920f0901299215f71111ab2等等
				 */ 
				int idCount = 0;//用于标记数字0,1,2,3...
				for (String expertDiscipline : expertDisciplines) {//遍历专家的不同所属学科，按照上述规则给专家id做好标记
					if((useDiscipline && checkDisciplineLegal(disciplineLevelOnes, expertDiscipline)) || (useSearch && !useDiscipline && (!expertDiscipline.isEmpty()) && expertDiscipline.length() >= 3) ) {
						String temp = "";//补足7位
						if(expertDiscipline.length() == 3)
							temp = expertDiscipline + "0000";
						else if(expertDiscipline.length() == 5)
							temp = expertDiscipline + "00";
						else 
							temp = expertDiscipline;
						ExpertTreeItem item = new ExpertTreeItem((String)o[0]+(idCount++), (String)o[1], (String)o[2], temp, o[4].toString(), (String)o[5], o[6].toString(), univExpertCnt);
						expertTreeItems.add(item);
					}
				}
			}
		}
		long end2 = System.currentTimeMillis();
		System.out.println("组装ExpertTreeItem耗时：" + (end2 - end) + "ms");
		//对expertTreeItems进行排序
		SortExpert sortRule = new SortExpert();
		Collections.sort(expertTreeItems, sortRule);
		System.out.println("排序ExpertTreeItem耗时：" + (System.currentTimeMillis() - end2) + "ms");
		
		Map dataMap = new HashMap();
		dataMap.put("totalExpert", totalExpert);
		dataMap.put("expertTreeItems", expertTreeItems);
		return dataMap;
	}

	/**
	 * 查询参与匹配专家的查询语句，在（匹配算法中、专家树中、导出专家中）使用
	 * @return
	 */
	public String selectReviewMatchExpert(){
		ActionContext context = ActionContext.getContext();
		Map session = context.getSession();
		Integer defaultYear = (Integer) session.get("defaultYear");
		Map application = ActionContext.getContext().getApplication();
		String instpReviewerImportedStartDate = (String) application.get("InstpReviewerImportedStartDate");
		String instpReviewerImportedEndDate = (String) application.get("InstpReviewerImportedEndDate");
		String instpReviewerBirthdayStartDate = (String) application.get("InstpReviewerBirthdayStartDate");
		String instpReviewerBirthdayEndDate = (String) application.get("InstpReviewerBirthdayEndDate");
		//筛选所属高校办学类型为11和12，属性为参评、非重点人、专职人员，手机和邮箱全非空，当前年没申报项目， 评价等级大于限制阈值，当前时间6个月内更新入库的专家
		StringBuffer hql4Reviewer = new StringBuffer(" and (((u.style like '11%' or u.style like '12%') and (u.founderCode in ('308', '339', '360', '435')) and expert.expertType = 1)");//所属高校办学类型为11和12且为部属高校的内部专家
		hql4Reviewer.append(" or expert.expertType = 2)");																//或所有外部专家
		hql4Reviewer.append(" and expert.specialityTitle in (:seniorTitles)");	                                    //正高级职称专家	
		hql4Reviewer.append(" and expert.isReviewer = 1 and expert.isKey = 0 and expert.type = '专职人员'");				//参评，非重点人，专职人员
		hql4Reviewer.append(" and expert.email is not null and expert.mobilePhone is not null");						//手机和邮箱全非空
		hql4Reviewer.append(" and (expert.instpApplyYears is null or expert.instpApplyYears not like '%" + defaultYear + "%')");	//当前年没申报项目
		hql4Reviewer.append(" and expert.rating > " + 0 + " ");												//评价等级大于限制阈值
		hql4Reviewer.append(" and expert.discipline is not null ");	//专家学科代码属性做非空限制
		if (instpReviewerImportedStartDate != null && !instpReviewerImportedStartDate.equals("不限")) {
			hql4Reviewer.append(" and expert.importedDate > to_date('" + instpReviewerImportedStartDate + "','yyyy-mm-dd') ");	    //设置一般项目_评审专家_导入_开始时间         
		}
		if (instpReviewerImportedEndDate != null && !instpReviewerImportedEndDate.equals("不限")) {
			hql4Reviewer.append(" and expert.importedDate < to_date('" + instpReviewerImportedEndDate + "','yyyy-mm-dd') ");		    //设置一般项目_评审专家_导入_结束时间
		}
		if (instpReviewerBirthdayStartDate != null && !instpReviewerBirthdayStartDate.equals("不限")) {
			hql4Reviewer.append(" and expert.birthday > to_date('" + instpReviewerBirthdayStartDate + "','yyyy-mm-dd') ");		    //设置一般项目_评审专家_出生日期_开始时间
		}
		if (instpReviewerBirthdayEndDate != null && !instpReviewerBirthdayEndDate.equals("不限")) {
			hql4Reviewer.append(" and expert.birthday < to_date('" + instpReviewerBirthdayEndDate + "','yyyy-mm-dd') ");		        //设置一般项目_评审专家_出生日期_结束时间
		}
		return hql4Reviewer.toString();
	}
	
	/**
	 * 查询参与匹配专家的查询语句，在（匹配算法中、专家树中、导出专家中）使用
	 * @return
	 */
	public String selectReviewMatchExpertForManual(){
		ActionContext context = ActionContext.getContext();
		Map session = context.getSession();
		Integer defaultYear = (Integer) session.get("defaultYear");
		Map application = ActionContext.getContext().getApplication();
		String instpReviewerImportedStartDate = (String) application.get("InstpReviewerImportedStartDate");
		String instpReviewerImportedEndDate = (String) application.get("InstpReviewerImportedEndDate");
		String instpReviewerBirthdayStartDate = (String) application.get("InstpReviewerBirthdayStartDate");
		String instpReviewerBirthdayEndDate = (String) application.get("InstpReviewerBirthdayEndDate");
		//筛选所属高校办学类型为11和12，属性为参评、非重点人、专职人员，手机和邮箱全非空，当前年没申报项目， 评价等级大于限制阈值，当前时间6个月内更新入库的专家
		StringBuffer hql4Reviewer = new StringBuffer(" and (((u.style like '11%' or u.style like '12%') and expert.expertType = 1)");//所属高校办学类型为11和12的内部专家
		hql4Reviewer.append(" or expert.expertType = 2)");																//或所有外部专家
		hql4Reviewer.append(" and expert.specialityTitle in (:seniorTitles)");	                                    //正高级职称专家	
		hql4Reviewer.append(" and expert.isReviewer = 1 and expert.isKey = 0 and expert.type = '专职人员'");				//参评，非重点人，专职人员
		hql4Reviewer.append(" and expert.email is not null and expert.mobilePhone is not null");						//手机和邮箱全非空
		hql4Reviewer.append(" and (expert.instpApplyYears is null or expert.instpApplyYears not like '%" + defaultYear + "%')");	//当前年没申报项目
		hql4Reviewer.append(" and expert.rating > " + 0 + " ");												//评价等级大于限制阈值
		if (instpReviewerImportedStartDate != null && !instpReviewerImportedStartDate.equals("不限")) {
			hql4Reviewer.append(" and expert.importedDate > to_date('" + instpReviewerImportedStartDate + "','yyyy-mm-dd') ");	    //设置一般项目_评审专家_导入_开始时间         
		}
		if (instpReviewerImportedEndDate != null && !instpReviewerImportedEndDate.equals("不限")) {
			hql4Reviewer.append(" and expert.importedDate < to_date('" + instpReviewerImportedEndDate + "','yyyy-mm-dd') ");		    //设置一般项目_评审专家_导入_结束时间
		}
		if (instpReviewerBirthdayStartDate != null && !instpReviewerBirthdayStartDate.equals("不限")) {
			hql4Reviewer.append(" and expert.birthday > to_date('" + instpReviewerBirthdayStartDate + "','yyyy-mm-dd') ");		    //设置一般项目_评审专家_出生日期_开始时间
		}
		if (instpReviewerBirthdayEndDate != null && !instpReviewerBirthdayEndDate.equals("不限")) {
			hql4Reviewer.append(" and expert.birthday < to_date('" + instpReviewerBirthdayEndDate + "','yyyy-mm-dd') ");		        //设置一般项目_评审专家_出生日期_结束时间
		}
		return hql4Reviewer.toString();
	}

	public String initParam(String data) {
		ActionContext context = ActionContext.getContext();
		Map application = context.getApplication();
		Integer expertProjectMin = (Integer) application.get("InstpExpertProjectMin");
		Integer expertProjectMax = (Integer) application.get("InstpExpertProjectMax");
		Integer projectMinistryExpertNumber = (Integer) application.get("InstpMinistryExpertNumber");
		Integer projectLocalExpertNumber = (Integer) application.get("InstpLocalExpertNumber");
		Integer projectExpertNumber = projectMinistryExpertNumber + projectLocalExpertNumber;
		Map session = context.getSession();
		if( (Integer)session.get("expertTree_useProjects") == 0) {
			projectExpertNumber = 9999;
		}
		//返回字符串格式："每个项目所需专家数,每个专家最大参评的项目数,选中的项目ids,选中的专家ids,选中的一级学科代码"
		String result = projectExpertNumber + "," + expertProjectMin + "," + expertProjectMax + "," + session.get("expertTree_projectIds") + "," + session.get("expertTree_selectExpertIds") + "," + session.get("expertTree_discipline1s");
		return result;
	}

	public Document createRightXML(String[] str, String selectExp, String uname, String ename, int flag, String projectid) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
