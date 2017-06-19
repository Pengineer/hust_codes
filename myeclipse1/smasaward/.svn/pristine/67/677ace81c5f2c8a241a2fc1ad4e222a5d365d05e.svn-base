package csdc.service.imp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.opensymphony.xwork2.ActionContext;

import csdc.bean.Expert;
import csdc.bean.ProjectApplication;
import csdc.service.IGeneralService;
import csdc.tool.ApplicationContainer;
import csdc.tool.DatetimeTool;
import csdc.tool.ExpertTreeItem;
import csdc.tool.SortExpert;

public class GeneralService extends ProjectService implements IGeneralService {
	
	/**
	 * 专家树的初始化函数，用于获取专家树相关的初始化信息
	 */
	public String initParam(String data) {
		ActionContext context = ActionContext.getContext();
		Map application = context.getApplication();
		Integer expertProjectMin = (Integer) application.get("GeneralExpertProjectMin");	//一般项目_每个专家_评审的最小项目数
		Integer expertProjectMax = (Integer) application.get("GeneralExpertProjectMax");	//一般项目_每个专家_评审的最大项目数
		Integer projectMinistryExpertNumber = (Integer) application.get("GeneralMinistryExpertNumber");
		Integer projectLocalExpertNumber = (Integer) application.get("GeneralLocalExpertNumber");
		Integer projectExpertNumber = projectMinistryExpertNumber + projectLocalExpertNumber;		//一般项目_每个项目_所需的专家数
		Map session = context.getSession();
		if( !session.containsKey("expertTree_useProjects") || (Integer)session.get("expertTree_useProjects") == 0) {
			projectExpertNumber = 9999;
		}
		//返回字符串格式："每个项目所需专家数,每个专家最大参评的项目数,选中的项目ids,选中的专家ids,选中的一级学科代码"
		String result = projectExpertNumber + "," + expertProjectMin + "," + expertProjectMax + "," + session.get("expertTree_projectIds") + "," + session.get("expertTree_selectExpertIds") + "," + session.get("expertTree_discipline1s");
		return result;
	}
	
	/**
	 * 判断专家学科（disciplines，用分号空格隔开）是否包含一个所选的一级学科
	 * @param discipline1Strings 一级学科
	 * @param disciplines 专家学科，用分号空格隔开
	 * @return
	 */
	public boolean checkDisciplineLegal(String[] discipline1Strings, String disciplines) {
		if(disciplines != null && !disciplines.isEmpty() && discipline1Strings != null) {
			String[] disStr = disciplines.split("\\D+");
			for(int i = 0; i < disStr.length; i++) {
				for(int j = 0; j < discipline1Strings.length; j++) {
					if(disStr[i].startsWith(discipline1Strings[j])) {
						return true;
					}
				}
			}
		}
		return false;
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
					Object[] objects = null;
					try {
						objects = getMemberInfo(p);
					} catch (Exception e) {
						e.printStackTrace();
					}
//					Object[] objects = getMemberInfo(p);
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
		String expertTree_discipline1s = (String) session.get("expertTree_discipline1s");//获取选中项目的一级学科代码
		String[] disciplineLevelOnes = null;//一级学科代码数组
		if(expertTree_discipline1s != null && !expertTree_discipline1s.isEmpty()) {
			disciplineLevelOnes = expertTree_discipline1s.split("\\D+");
		}
		
		StringBuffer hql4Reviewer = new StringBuffer("select expert.id, expert.name, expert.specialityTitle, expert.discipline, COUNT(pr), u.name, u.abbr, expert.universityCode from Expert expert left join expert.applicationReview pr with pr.year = " + defaultYear + " and pr.type = 'general', University u where expert.universityCode = u.code");
		hql4Reviewer.append(selectReviewMatchExpert());
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
		
		Map paraMap = new HashMap();
		Map disciplineMap = selectSpecialityTitleInfo();
		paraMap.put("aboveSubSeniorTitles", disciplineMap.get("aboveSubSeniorTitles"));	//副高级职称以上专家
		paraMap.put("seniorTitles", disciplineMap.get("seniorTitles"));	//正高级、高级职称专家
		//筛选出来的参评专家列表
		Date begin = new Date();
		List expertList = this.query(hql4Reviewer.toString(),paraMap);
		long end = System.currentTimeMillis();
		System.out.println("查询tree数据耗时：" + (new Date().getTime() - begin.getTime()) + "ms");
		
		//高校参评专家数量
		HashMap<String, String> univReviewerCnt = new HashMap<String, String>();
		String hql4urCnt = "select u.name, count(distinct pr.reviewer.id) from University u, ProjectApplicationReview pr, Expert expert where pr.type = 'general' and u.code = expert.universityCode and expert.id = pr.reviewer.id and pr.year = ? group by u.name ";
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
		String generalReviewerImportedStartDate = (String) application.get("GeneralReviewerImportedStartDate");
		String generalReviewerImportedEndDate = (String) application.get("GeneralReviewerImportedEndDate");
		String generalReviewerBirthdayStartDate = (String) application.get("GeneralReviewerBirthdayStartDate");
		String generalReviewerBirthdayEndDate = (String) application.get("GeneralReviewerBirthdayEndDate");
		//筛选所属高校办学类型为11和12，属性为参评、非重点人、专职人员，手机和邮箱全非空，当前年没申报项目， 评价等级大于限制阈值，当前时间6个月内更新入库的专家
		StringBuffer hql4Reviewer = new StringBuffer(" and (((u.style like '11%' or u.style like '12%') and expert.expertType = 1)");			//所属高校办学类型为11和12的内部专家
		hql4Reviewer.append(" or expert.expertType = 2)");																//或所有外部专家
		hql4Reviewer.append(" and u.useExpert = 1");			                                                        //使用该校评审专家
		hql4Reviewer.append(" and ((u.useViceExpert = 1 and expert.specialityTitle in (:aboveSubSeniorTitles)) or (expert.specialityTitle in (:seniorTitles)))");             	//使用该校副高级职称专家			
//		hql4Reviewer.append(" and u.code in (:selectedUnivCodes)");                                                     //使用有参与评审专家的高校
		hql4Reviewer.append(" and expert.isReviewer = 1 and expert.isKey = 0 and expert.type = '专职人员'");				//参评，非重点人，专职人员
		hql4Reviewer.append(" and expert.email is not null and expert.mobilePhone is not null");						//手机和邮箱全非空
		hql4Reviewer.append(" and (expert.generalApplyYears is null or expert.generalApplyYears not like '%" + defaultYear + "%')");	//当前年没申报项目
		hql4Reviewer.append(" and expert.rating > 0 ");		
		hql4Reviewer.append(" and expert.discipline is not null ");	
		//评价等级大于限制阈值（数字越大，评价等级越高）	
		if (generalReviewerImportedStartDate != null && !generalReviewerImportedStartDate.equals("不限")) {
			hql4Reviewer.append(" and expert.importedDate > to_date('" + generalReviewerImportedStartDate + "','yyyy-mm-dd') ");	    //设置一般项目_评审专家_导入_开始时间         
		}
		if (generalReviewerImportedEndDate != null && !generalReviewerImportedEndDate.equals("不限")) {
			hql4Reviewer.append(" and expert.importedDate < to_date('" + generalReviewerImportedEndDate + "','yyyy-mm-dd') ");		    //设置一般项目_评审专家_导入_结束时间
		}
		if (generalReviewerBirthdayStartDate != null && !generalReviewerBirthdayStartDate.equals("不限")) {
			hql4Reviewer.append(" and expert.birthday > to_date('" + generalReviewerBirthdayStartDate + "','yyyy-mm-dd') ");		    //设置一般项目_评审专家_出生日期_开始时间
		}
		if (generalReviewerBirthdayEndDate != null && !generalReviewerBirthdayEndDate.equals("不限")) {
			hql4Reviewer.append(" and expert.birthday < to_date('" + generalReviewerBirthdayEndDate + "','yyyy-mm-dd') ");		        //设置一般项目_评审专家_出生日期_结束时间
		}
		return hql4Reviewer.toString();
	}
	
	/**
	 * 
	 * 《方法未使用》
	 * 
	 * 根据传的一级学科数组生成专家树，同时支持高校名称和专家名称检索
	 * @param discipline1Strings 一级学科数组
	 * @param universityName 高校名称
	 * @param expertName 专家名称
	 * @param selectExpIds 已选专家id，用分号隔开
	 * @param projectIds 选中的项目id，分号空格隔开（手动选择专家时需要此参数）
	 * @return 树的dom对象
	 */
	public Document createExpertTree(String[] discipline1Strings, String universityName, String expertName, String selectExpIds, String projectIds) {
		Date d1 = new Date();
		System.out.println("开始生成dom... ");
		int totalExpert = 0;//专家计数
		int projectFlag = 0;//projectIds是否为空：1不为空，0为空
		int useSearch = 0;//是否使用检索条件（高校名称或专家姓名）：1使用，0未使用
		int useDiscipline = 0;//是否勾选有学科（高校名称或专家姓名）：1勾选，0未勾选
		//统计项目成员和成员所属高校代码的集合
		Set<String> expertNameList = new HashSet<String>();//去除的名字集合
		Set<String> uniCodeList = new HashSet<String>();//去除的高校代码集合
		if(projectIds != null && !projectIds.isEmpty()) {
			projectFlag = 1;
			String[] ids = projectIds.split("\\D+");
			for(int i = 0; i < ids.length; i++) {
				ProjectApplication p = (ProjectApplication) query(ProjectApplication.class, ids[i]);
				if(p != null) {
					Object[] objects = getMemberInfo(p);
					expertNameList.addAll((Set<String>) objects[0]);//当前项目的成员集合
					uniCodeList.addAll((Set<String>) objects[1]);//当前项目的成员所在高校代码集合
				}
			}
		}
		Map session = ActionContext.getContext().getSession();
		Integer defaultYear = (Integer) session.get("defaultYear");
		StringBuffer hql4Reviewer = new StringBuffer("select expert.id, expert.name, expert.specialityTitle, expert.discipline, COUNT(pr), u.name, u.abbr, expert.universityCode from Expert expert left join expert.applicationReview pr with pr.year = " + defaultYear + " and pr.type = 'general', University u where expert.universityCode = u.code");
		hql4Reviewer.append(selectReviewMatchExpert());
		if(universityName != null && universityName.length() > 0) {
			useSearch = 1;
			hql4Reviewer.append(" and u.name like '%" + universityName + "%' ");
		}
		if(expertName != null && expertName.length() > 0) {
			useSearch = 1;
			hql4Reviewer.append(" and expert.name like '%" + expertName + "%' ");
		}
		if(discipline1Strings != null && discipline1Strings.length > 0 && !discipline1Strings[0].isEmpty()) {
			useDiscipline = 1;
			hql4Reviewer.append(" and ( expert.discipline like '%" + discipline1Strings[0] + "%' ");
			for(int i = 1; i < discipline1Strings.length; i++) {
				hql4Reviewer.append(" or expert.discipline like '%" + discipline1Strings[i] + "%' ");
			}
			hql4Reviewer.append(" ) ");
		}
		if(useSearch == 0 && useDiscipline == 0) {//如果没有检索条件，查询为空
			hql4Reviewer.append(" and 1 = 0 ");
		}
		hql4Reviewer.append(" group by expert.id, expert.name, expert.specialityTitle, expert.discipline, u.name, u.abbr, expert.universityCode ");
		
		Map paraMap = new HashMap();
		Map disciplineMap = selectSpecialityTitleInfo();
		paraMap.put("aboveSubSeniorTitles", disciplineMap.get("aboveSubSeniorTitles"));	//副高级职称以上专家
		paraMap.put("seniorTitles", disciplineMap.get("seniorTitles"));	//正高级、高级职称专家
		//筛选出来的参评专家列表
		List expList = this.query(hql4Reviewer.toString(),paraMap);
//		String hql1 = hql.replace(", University u where", "and gr.priority <= 5, University u where");
//		List expList1 = this.query(hql1);
//		//筛选出来的正评专家列表
//		List expList1 = this.query(hql);
		
		//专家详情map
		Map expDetailMap = new HashMap();
		ExpertTreeItem expertTreeItem;
		List<ExpertTreeItem> items = new ArrayList();
//		Map<String, String> cntMap = new HashMap();//专家id->参评数量的map
//		for (Object expert : expList1) {
//			Object[] o = (Object[]) expert;
//			cntMap.put((String)o[0], o[5].toString());
//		}
		//专家信息处理
		for (Object expert : expList) {
			Object[] o = (Object[]) expert;
			if((useDiscipline == 0 || checkDisciplineLegal(discipline1Strings, (String) o[3])) && (projectFlag == 0 || (projectFlag == 1 && !expertNameList.contains((String)o[1]) && !uniCodeList.contains((String)o[7]) )) ) {
				expertTreeItem = new ExpertTreeItem((String)o[0], (String)o[1], (String)o[2], (String)o[3], o[4].toString(), (String)o[5], o[6].toString(), "0");
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
					if( (useDiscipline == 1 && checkDisciplineLegal(discipline1Strings, expertDiscipline)) || (useSearch == 1 && useDiscipline == 0 && (!expertDiscipline.isEmpty()) && expertDiscipline.length() >= 3) ) {
						String temp = "";
						if(expertDiscipline.length() == 3)
							temp = expertDiscipline + "0000";
						else if(expertDiscipline.length() == 5)
							temp = expertDiscipline + "00";
						else 
							temp = expertDiscipline;
						ExpertTreeItem item1 = new ExpertTreeItem((String)o[0]+(idCount++), (String)o[1], (String)o[2], temp, o[4].toString(), (String)o[5], o[6].toString(), "0");
						items.add(item1);
					}
				}
			}
		}
		//构建document
		Document document = DocumentHelper.createDocument();
		document.setXMLEncoding("utf-8");
		Element root = document.addElement("tree");
		root.addAttribute("id", "0");
		Element item0 = root.addElement("item");
		item0.addAttribute("text", "所有专家[" + totalExpert + "人]");
		item0.addAttribute("id", "all");
		item0.addAttribute("im0", "folder_closed.gif");
		item0.addAttribute("im1", "folder_open.gif");
		item0.addAttribute("im2", "folder_closed.gif");
		item0.addAttribute("open", "1");
		item0.addAttribute("select", "1");
		session.put("allExp", expDetailMap);
		
		List<String> selectExpertId = new ArrayList();
		if(selectExpIds!=null && !selectExpIds.isEmpty()) {
			String[] selectExpIdArray = selectExpIds.split(";");
			for (String selectExpId : selectExpIdArray) {
				selectExpertId.add(selectExpId.trim());
			}
		}
		SortExpert sortRule = new SortExpert();
		Collections.sort(items, sortRule);
		String pre1 = null;
		String pre2 = null;
		String pre3 = null;
		Element item1 = null;//一级学科节点
		Element item2 = null;//二级学科节点
		Element item3 = null;//三级学科节点
		List<String> c1 = new ArrayList();
		List<String> c2 = new ArrayList();
		List<String> c3 = new ArrayList();
		//学科代码与学科名称的映射
		Map disMap = (Map) ActionContext.getContext().getApplication().get("disMap");
//		List<SystemOption> disciplines = this.query("select s from SystemOption s where s.standard like '%GBT13745%' ");
//		Map disMap = new HashMap();
//		for (SystemOption so : disciplines) {
//			if (null != so.getCode() && !disMap.containsKey(so.getCode())) {
//				disMap.put(so.getCode(), so.getName());
//			}
//		}
		for(int i = 0; i < items.size(); i++) {
			if(!(items.get(i).discipline.substring(0, 3).equals(pre1))) {//一级学科节点
				c1.clear();
				c2.clear();
				c3.clear();
				String tmp1 = items.get(i).discipline.substring(0, 3) + getDiscipline1Name(items.get(i).discipline.substring(0, 3), disMap);
				if( items.get(i).discipline.substring(0, 3).equals("000") ) {
					tmp1 = "其他";
				}
				item1 = item0.addElement("item");
				item1.addAttribute("text", tmp1);
				item1.addAttribute("id", items.get(i).discipline.substring(0, 3));
				item1.addAttribute("im0", "folder_closed.gif");
				item1.addAttribute("im1", "folder_open.gif");
				item1.addAttribute("im2", "folder_closed.gif");
				pre1 = items.get(i).discipline.substring(0, 3);
				pre2 = null;
				pre3 = null;
			}
			if( items.get(i).discipline.substring(0, 3).equals("000") ) {//学科代码直接为一级学科的专家放入当前一级学科的"其他"目录下
				Element item4 = item1.addElement("item");
				item4.addAttribute("text", items.get(i).name + "（" + items.get(i).university + "，" + items.get(i).specialityTitle + "）[参评" + items.get(i).pojcnt + "项]");
				item4.addAttribute("id", items.get(i).id);
				item4.addAttribute("name", items.get(i).id);
				item4.addAttribute("im0", "zj.gif");
				if(selectExpertId.contains(items.get(i).id.substring(0, 32)))
					item4.addAttribute("checked", "1");
				if( !c1.contains(items.get(i).id.substring(0, 32)) ) {
					c1.add(items.get(i).id.substring(0, 32));
					item1.addAttribute("text", "其他[" + c1.size() + "人]");
				}
				continue;
			}
			if(!(items.get(i).discipline.substring(0, 5).equals(pre2)) ) {//二级学科节点
				c2.clear();
				c3.clear();
				String tmp2 = items.get(i).discipline.substring(0, 5) + getDiscipline1Name(items.get(i).discipline.substring(0, 5), disMap);
				if( items.get(i).discipline.substring(3, 5).equals("00") ) {
					tmp2 = "其他";
				}
				item2 = item1.addElement("item");
				item2.addAttribute("text", tmp2);
				item2.addAttribute("id", items.get(i).discipline.substring(0, 5));
				item2.addAttribute("im0", "folder_closed.gif");
				item2.addAttribute("im1", "folder_open.gif");
				item2.addAttribute("im2", "folder_closed.gif");
				pre2 = items.get(i).discipline.substring(0, 5);
				pre3 = null;
			}
			if( items.get(i).discipline.substring(3, 5).equals("00") ) {//学科代码直接为二级学科的专家放入当前二级学科的"其他"目录下
				Element item4 = item2.addElement("item");
				item4.addAttribute("text", items.get(i).name + "（" + items.get(i).university + "，" + items.get(i).specialityTitle + "）[参评" + items.get(i).pojcnt + "项]");
				item4.addAttribute("id", items.get(i).id);
				item4.addAttribute("name", items.get(i).id);
				item4.addAttribute("im0", "zj.gif");
				if(selectExpertId.contains(items.get(i).id.substring(0, 32)))
					item4.addAttribute("checked", "1");
				if( !c1.contains(items.get(i).id.substring(0, 32)) ) {
					c1.add(items.get(i).id.substring(0, 32));
					item1.addAttribute("text", pre1 + getDiscipline1Name(pre1, disMap) + "[" + c1.size() + "人]");
				}
				if( !c2.contains(items.get(i).id.substring(0, 32)) ) {
					c2.add(items.get(i).id.substring(0, 32));
					item2.addAttribute("text", "其他[" + c2.size() + "人]");
				}
				continue;
			}
			if( !(items.get(i).discipline.substring(0, 7).equals(pre3)) ) {//三级学科节点
				c3.clear();
				String tmp3 = items.get(i).discipline.substring(0, 7) + getDiscipline1Name(items.get(i).discipline.substring(0, 7), disMap);
				if( items.get(i).discipline.substring(5, 7).equals("00") ) {
					tmp3 = "其他";
				}
				item3 = item2.addElement("item");
				item3.addAttribute("text", tmp3);
				item3.addAttribute("id", items.get(i).discipline.substring(0, 7));
				item3.addAttribute("im0", "folder_closed.gif");
				item3.addAttribute("im1", "folder_open.gif");
				item3.addAttribute("im2", "folder_closed.gif");
				pre3 = items.get(i).discipline.substring(0, 7);
			}
			if( items.get(i).discipline.substring(5, 7).equals("00") ) {//学科代码直接为三级学科的专家放入当前三级学科的"其他"目录下
				Element item4 = item3.addElement("item");
				item4.addAttribute("text", items.get(i).name + "（" + items.get(i).university + "，" + items.get(i).specialityTitle + "）[参评" + items.get(i).pojcnt + "项]");
				item4.addAttribute("id", items.get(i).id);
				item4.addAttribute("name", items.get(i).id);
				item4.addAttribute("im0", "zj.gif");
				if(selectExpertId.contains(items.get(i).id.substring(0, 32)))
					item4.addAttribute("checked", "1");
				c3.add(items.get(i).id.substring(0, 32));
				item3.addAttribute("text", "其他" + "[" + c3.size() + "人]");
				if( !c1.contains(items.get(i).id.substring(0, 32)) ) {
					c1.add(items.get(i).id.substring(0, 32));
					item1.addAttribute("text", pre1 + getDiscipline1Name(pre1, disMap) + "[" + c1.size() + "人]");
				}
				if( !c2.contains(items.get(i).id.substring(0, 32)) ) {
					c2.add(items.get(i).id.substring(0, 32));
					item2.addAttribute("text", pre2 + getDiscipline1Name(pre2, disMap) + "[" + c2.size() + "人]");
				}
				continue;
			}
			Element item4 = item3.addElement("item");
			item4.addAttribute("text", items.get(i).name + "（" + items.get(i).university + "，" + items.get(i).specialityTitle + "）[参评" + items.get(i).pojcnt + "项]");
			item4.addAttribute("id", items.get(i).id);
			item4.addAttribute("name", items.get(i).id);
			item4.addAttribute("im0", "zj.gif");
			if(selectExpertId.contains(items.get(i).id.substring(0, 32)))
				item4.addAttribute("checked", "1");
			if( !c1.contains(items.get(i).id.substring(0, 32)) ) {
				c1.add(items.get(i).id.substring(0, 32));
				item1.addAttribute("text", pre1 + getDiscipline1Name(pre1, disMap) + "[" + c1.size() + "人]");
			}
			if( !c2.contains(items.get(i).id.substring(0, 32)) ) {
				c2.add(items.get(i).id.substring(0, 32));
				item2.addAttribute("text", pre2 + getDiscipline1Name(pre2, disMap) + "[" + c2.size() + "人]");
			}
			if( !c3.contains(items.get(i).id.substring(0, 32)) ) {
				c3.add(items.get(i).id.substring(0, 32));
				item3.addAttribute("text", pre3 + getDiscipline1Name(pre3, disMap) + "[" + c3.size() + "人]");
			}
		}
		System.out.println("完成生成dom... ");
		System.out.println("生成dom耗时：" + (new Date().getTime() - d1.getTime()) + "ms");
		return document;
	}
		
	/**
	 * 设置项目立项状态
	 * @param projectids待操作的项目集合
	 * @param label项目立项（0 不立项 → 1拟立项 → 2确定立项）
	 */
	public void setGranting(List<String> projectids, int label) {
		ProjectApplication p = (ProjectApplication) this.query(ProjectApplication.class, projectids.get(0));
		switch (label) {
		case 0:
			p.setIsGranting(1);
			break;
		case 1:
			p.setIsGranting(2);
			break;
		case 2:
			p.setIsGranting(0);
			break;
		default:
			break;
		}
		this.modify(p);
	}
	
	/**
	 * 获取项目[成员集合]和[成员所在高校代码集合](成员包括负责人)<br>
	 * 其中，项目的成员信息形如[张三(北京大学), 李四(清华大学)]
	 * @param memberInfo
	 * @return 0: Set姓名集合	1: Set高校代码集合	 若未能识别出高校，则代码置为null
	 */
	public Object[] getMemberInfo(ProjectApplication project) {
		if (project == null || project.getMembers() == null) {
			return new Object[]{new HashSet<String>(), new HashSet<String>()};
		}
		HashMap<String, String> univNameCodeMap = (HashMap<String, String>) ApplicationContainer.sc.getAttribute("univNameCodeMap");
		
		String[] members = project.getMembers().split("\\)[,，;]\\s*");
		Set<String> names = new HashSet<String>();
		Set<String> univCodes = new HashSet<String>();
		for (String string : members) {
		
			int splitIndex = string.indexOf('(');
			if(splitIndex == -1) {
				//没有机构信息，string即name
				int tag = string.indexOf(";");
				String nameTemp = (String) (tag == -1 ? string : string.subSequence(0, tag));
				names.add(nameTemp);
			} else {
				String personName = string.substring(0, splitIndex);
				String agencyName = string.substring(splitIndex + 1);
				for (int i = 0; i < agencyName.length(); i++) {
					for (int j = agencyName.length(); j > i; j--) {
						String subName = agencyName.substring(i, j);
						if (univNameCodeMap.get(subName) != null) {
							univCodes.add(univNameCodeMap.get(subName));
							break;
						}
					}
				}
				names.add(personName);
			}
		}
		names.add(project.getDirector());
		univCodes.add(project.getUniversityCode());
		return new Object[]{names, univCodes};
	}
	//	没用到的代码
//	public void matchExpert(Integer year, List<String> expertIds, List<String> projectids, List<String> rejectExpertIds) throws Exception {
//		GeneralReviewerMatcher matcher = (GeneralReviewerMatcher) SpringBean.getBean("generalReviewerMatcher", ApplicationContainer.sc);
//		matcher.matchExpert(year, expertIds, projectids, rejectExpertIds);
//	}
	
//	public void updateWarningReviewer(List<String> projectIds, Integer year) {
//		MatcherWarningUpdater updater = (MatcherWarningUpdater) SpringBean.getBean("generalMatcherWarningUpdater", ApplicationContainer.sc);
//		updater.update(projectIds, year);
//	}
	
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
		try {
			if (exportAll != 0){
				projects = this.query("select p from ProjectApplication p where p.type = 'general' and p.year = ? order by p.id asc", year);
			} else {
				projects = this.query(((String) session.get("HQL4ProjectExport")).replaceAll("order by[\\s\\S]*", "order by p.id asc"), (Map)session.get("Map4ProjectExport"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		List<Object[]> dataList = new ArrayList<Object[]>();
		String header = null;
		String[] title = null;
		if(containReviewer) {
			header = "项目专家匹配表";
			title = new String[]{
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
			for (ProjectApplication project : projects) {
				List pList = new ArrayList<Object>();
				//项目编号用sinoss的application iD（或者根据同步情况，取同步的number字段）
//				pList.add(project.getNumber());
				pList.add(project.getSinossID());
				pList.add(project.getProjectName());
//				pList.add(DatetimeTool.getYearMonthDateString(project.getAddDate()));
//				pList.add(project.getAuditStatus());
//				pList.add(project.getFile());
				pList.add(project.getUniversityCode());
				pList.add(project.getUniversityName());
//				pList.add(project.getProjectType());
//				pList.add(project.getDisciplineType());
//				pList.add(DatetimeTool.getYearMonthDateString(project.getApplyDate()));
				
//				String discString = "";//学科代码+学科名称
//				if(null != project.getDiscipline() && !project.getDiscipline().isEmpty()){
//					String disc[] = project.getDiscipline().split("; ");
//					for (String s : disc) {
//						discString += (discString.isEmpty() ? "" : "、") + ((discMap.get(s)==null) ? "" : discMap.get(s)) + "(" + s + ")";
//					}
//				}
//				pList.add(discString);
//				pList.add(project.getResearchType());
//				pList.add(DatetimeTool.getYearMonthDateString(project.getPlanEndDate()));
//				pList.add(project.getApplyFee());
//				pList.add(project.getOtherFee());
//				pList.add(project.getFinalResultType());
				pList.add(project.getDirector());
//				pList.add(project.getGender());
//				pList.add(DatetimeTool.getYearMonthDateString(project.getBirthday()));
//				pList.add(project.getTitle());
//				pList.add(project.getDepartment());
//				pList.add(project.getJob());
//				pList.add(project.getEducation());
//				pList.add(project.getDegree());
//				pList.add(project.getForeign());
//				pList.add(project.getAddress());
//				pList.add(project.getPostcode());
//				pList.add(project.getPhone());
//				pList.add(project.getMobile());
//				pList.add(project.getEmail());
//				pList.add(project.getIdcard());
//				pList.add(project.getMembers());
//				pList.add("");
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
				Object obj[] = pList.toArray(new Object[0]);
				for (int i = 0; i < obj.length; i++){
					if (obj[i] == null){
						obj[i] = "";
					}
				}
				dataList.add(obj);
			}
		} else {
			header = "项目一览表";
			title = new String[] {
					"审核状态",
					"申报书文件名",
					"项目编号",
					"项目名称",
					"项目类别",
					"学科门类",
					"申报日期",
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
					"备注"
					};
			for (ProjectApplication project : projects) {
				List pList = new ArrayList<Object>();
				pList.add(project.getAuditStatus());
				pList.add(project.getFile());
				//
//				pList.add(project.getNumber());
				pList.add(project.getSinossID());
				pList.add(project.getProjectName());
				pList.add(project.getProjectType());
				pList.add(project.getDisciplineType());
				pList.add(DatetimeTool.getYearMonthDateString(project.getApplyDate()));
				
				String discString = "";//学科代码+学科名称
				if(null != project.getDiscipline() && !project.getDiscipline().isEmpty()){
					String disc[] = project.getDiscipline().split("; ");
					for (String s : disc) {
						discString += (discString.isEmpty() ? "" : "、") + ((discMap.get(s)==null) ? "" : discMap.get(s)) + "(" + s + ")";
					}
				}
				pList.add(discString);
				pList.add(project.getResearchType());
				pList.add(DatetimeTool.getYearMonthDateString(project.getPlanEndDate()));
				pList.add(project.getApplyFee());
				pList.add(project.getOtherFee());
				pList.add(project.getFinalResultType());
				pList.add(project.getUniversityCode());
				pList.add(project.getUniversityName());
				pList.add(project.getDirector());
				pList.add(project.getGender());
				pList.add(DatetimeTool.getYearMonthDateString(project.getBirthday()));
				pList.add(project.getTitle());
				pList.add(project.getDepartment());
				pList.add(project.getJob());
				pList.add(project.getEducation());
				pList.add(project.getDegree());
				pList.add(project.getForeign());
				pList.add(project.getAddress());
				pList.add(project.getPostcode());
				pList.add(project.getPhone());
				pList.add(project.getMobile());
				pList.add(project.getEmail());
				pList.add(project.getIdcard());
				pList.add(project.getMembers());
				pList.add("");
				Object obj[] = pList.toArray(new Object[0]);
				for (int i = 0; i < obj.length; i++){
					if (obj[i] == null){
						obj[i] = "";
					}
				}
				dataList.add(obj);
			}
		}
		Map map = new HashMap();
		map.put("dataList", dataList);
		map.put("header", header);
		map.put("title", title);
		return map;
	}
	
}
