package csdc.action.award;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionContext;

import csdc.action.BaseAction;
import csdc.bean.AwardApplication;
import csdc.bean.AwardApplicationReview;
import csdc.bean.AwardApplicationReviewUpdate;
import csdc.bean.Expert;
import csdc.bean.SystemOption;
import csdc.bean.University;
import csdc.bean.common.ExpertLink;
import csdc.service.IAwardService;
import csdc.service.IExpertService;
import csdc.service.IProjectService;
import csdc.tool.info.AwardInfo;
import csdc.tool.info.GlobalInfo;

/**
 */
@SuppressWarnings("unchecked")
public class MoeSocialAction extends BaseAction {
	
	private static final long serialVersionUID = 1L;
	private static final String HQL = "select aa.id, aa.productName, aa.applicantName, u.name, aa.discipline, aa.productType, aa.session, count(ar.id), aa.warningReviewer, 'expertlink', 'expertinfo' from AwardApplication aa left join aa.awardApplicationReview ar, University u where aa.type = 'moeSocial' and aa.year = :defaultYear and aa.unitCode = u.code";
	private static final String HQLG = " group by aa.id, aa.productName, aa.applicantName, u.name, aa.discipline, aa.productType, aa.session, aa.warningReviewer";
	private static final String HQLC = "select COUNT(*) from AwardApplication aa, University u where aa.type = 'moeSocial' and aa.year = :defaultYear and aa.unitCode = u.code";
	private static final String DATE_FORMAT = "yyyy-MM-dd";// 列表时间格式
	private static final String AWARD_TYPE = "moeSocial";
	private static final String CLASS_NAME = "AwardApplication";
	private static final String PAGENAME = "awardPage";
	private static final String PAGE_BUFFER_ID = "aa.id";//缓存id
	private static final String column[] = {
		"aa.productName",
		"aa.applicantName",
		"u.name",
		"aa.discipline",
		"aa.productType",
		"aa.session",
		"count(ar.id) desc",
		"aa.warningReviewer"
	};// 排序用的列
	private int keyword1;//初级检索届次
	private AwardApplication awardApplication;//奖励对象
	
	private int isReviewable;//是否参与评审
	private long startTime;				//导出匹配更新结果所用起始时间
	private long endTime;					//导出匹配更新结果所用截止时间
	//导出
	protected String fileFileName;			//导出文件名
	protected int exportAll;				//是否导出全部至xls
	
	
	//专家匹配变量
	protected List<String> expertIds;		//后台代码中应用的变量
	protected String selectedExpertIds;		//匹配时选择的专家ids	
	protected String awardId;				//前台奖励id（批量替换的需要替换的id）
	protected String selectExp;				//从专家树中选择的专家的ID
	protected String expertId;				//专家id，用于前后台交互（专家制定移交）
	protected List awardList;					//奖励列表列表
	
	protected int deleteManualMatches;		//是否删除手工匹配对，1是，0否
	protected int deleteAutoMatches;		//是否删除自动匹配对，1是，0否

	//专家树变量
	protected List nodesInfo;				//树节点列表
	protected String discipline;			//一级学科代码字符串（用户勾选的一级学科代码）
	protected String uname;					//专家树页面检索用的高校名称
	protected String ename;					//专家树页面检索用的专家姓名
	protected List<SystemOption> disList;	//一级学科列表(disList用于expertTree.jsp页面生成学科代码checkbox)
	
	private IProjectService projectService;
	private IAwardService awardService;
	
	private IExpertService expertService;
	
	
	public String listHQL() {
		return MoeSocialAction.HQL;
	}	
	public String listHQLG() {
		return MoeSocialAction.HQLG;
	}	
	public String listHQLC() {
		return MoeSocialAction.HQLC;
	}	
	public String awardType(){
		return MoeSocialAction.AWARD_TYPE;
	}
	public String className(){
		return MoeSocialAction.CLASS_NAME;
	}
	
	@Override
	public String pageName() {
		return MoeSocialAction.PAGENAME;
	}
	@Override
	public String[] column() {
		return MoeSocialAction.column;
	}
	@Override
	public String dateFormat() {
		return MoeSocialAction.DATE_FORMAT;
	}
	
	@Override
	public String pageBufferId() {
		return MoeSocialAction.PAGE_BUFFER_ID;
	}
	
	@Override
	public Object[] simpleSearchCondition() {
		keyword = (keyword == null) ? "" : keyword.toLowerCase().trim();// 预处理关键字
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		hql.append(HQL);
		hql.append(" and aa.isReviewable = :isReviewable");
		map.put("isReviewable", isReviewable);
		map.put("defaultYear", session.get("defaultYear"));
		
		StringBuffer c_hql = new StringBuffer();
		c_hql.append(HQLC);
		
		if (keyword1 > 0) {
			hql.append(" and aa.session =:session ");
			map.put("session", keyword1);
		}
		String s_hql = "";
		if (keyword != null && !keyword.isEmpty()) {// 如果关键字为空，不加搜索条件
			hql.append(" and ");
			c_hql.append(" and ");
			if (searchType == 1) {
				s_hql = "LOWER(aa.productName) like :keyword";
				map.put("keyword", "%" + keyword + "%");
			} else if (searchType == 2) {
				s_hql = "LOWER(aa.applicantName) like :keyword";
				map.put("keyword", "%" + keyword + "%");
			} else if (searchType == 3) {
				s_hql = "LOWER(u.name) like :keyword";
				map.put("keyword", "%" + keyword + "%");
			} else if (searchType == 4) {// 按学科名称搜索	
					s_hql = "LOWER(aa.discipline) like :keyword";
					map.put("keyword", "%" + keyword + "%");
			} else if (searchType == 5) {
				s_hql = "LOWER(aa.productType) like :keyword";
				map.put("keyword", "%" + keyword + "%");
			} else {
				s_hql = "(LOWER(aa.productName) like :keyword or LOWER(aa.applicantName) like :keyword or LOWER(u.name) like :keyword or LOWER(aa.discipline) like :keyword or LOWER(aa.productType) like :keyword)";
				map.put("keyword", "%" + keyword + "%");
			}
		}
		hql.append(s_hql);
		c_hql.append(s_hql);
		StringBuffer HQL4AwardExport = new StringBuffer();
		HQL4AwardExport.append(c_hql.toString());
		
		hql.append(HQLG);
		
		//为列表页面项目导出做准备
		String tmp = HQL4AwardExport.toString();
		String exporthql = tmp;
		session.put("HQL4AwardExport", exporthql.replaceAll("COUNT\\(\\*\\)", "aa"));
		session.put("Map4AwardExport", map);
		
		// 调用初级检索功能
		return new Object[]{
			hql.toString(),
			map,
			6,
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
			laData = new ArrayList();// 处理之后的列表数据
			Object[] item;// 缓存查询结果一行
			
			Date begin = new Date();
			List<ExpertLink> expertLink = new ArrayList<ExpertLink>();// 用于专家信息
			// 遍历初始查询列表数据，按照指定格式，格式化其中的时间字段，其它字段转化为字符串
			for (Object p : pageList) {
				item = (Object[]) p;
				
				String awardId;// 项目ID
				String value = "";// 项目评审专家信息
				String disciplineCode = "";// 项目学科信息
				awardId = item[0].toString();
				expertLink = awardService.getExpert(awardId);
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
				//处理专家信息
				item[9] = expertLink;//评审专家
				item[10] = value;//专家信息
				
				laData.add(item);// 将处理好的数据存入laData
			}
			Date end = new Date();
			System.out.println("组装数据耗时：" + (end.getTime() - begin.getTime()) + "ms");
		}else{
			super.pageListDealWith();
		}
	}
	
	/**
	 * 进入查看页面
	 * @return
	 */
	public String toView(){
		return SUCCESS;
	}
	
	/**
	 * 查看校验
	 */
	public void validateView() {
		if (entityId == null || entityId.isEmpty()) {// 项目ID不得为空
			this.addFieldError(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_VIEW_NULL);
			jsonMap.put(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_VIEW_NULL);
		}
	}
	
	public String view() {
		awardApplication = (AwardApplication) baseService.query(AwardApplication.class, entityId);
		if(awardApplication == null) {
			jsonMap.put(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_AWARD_NULL);
			return INPUT;
		} else {
			University universityName = (University) baseService.query(University.class, awardApplication.getUnitCode());
			jsonMap.put("universityName", universityName.getName());//高校名称
			jsonMap.put("awardApplication", awardApplication);
			StringBuffer hql4Expert = new StringBuffer();
			Map map = new HashMap();
			hql4Expert.append("select e.id, e.name, u.name, e.specialityTitle, u.founderCode, e.discipline, ar.isManual from Expert e, University u, AwardApplicationReview ar where ar.year = :defaultYear and ar.award.id = :awardId and ar.reviewer.id = e.id and e.universityCode = u.code order by ar.priority asc");
			map.put("awardId", entityId);
			map.put("defaultYear", session.get("defaultYear"));
			pageList = baseService.query(hql4Expert.toString(), map);
			jsonMap.put("pageList", pageList);
			if (pageList != null && !pageList.isEmpty()) {
				Map<String, String> expertInfo = new HashMap<String, String>();
				for (int i = 0; i < pageList.size(); i++) {
					Object[] o;
					String mapid;// 专家ID
					String value = "";// 专家学科信息信息
					o = (Object[]) pageList.get(i);
					mapid = o[0].toString();
					// 获得专家学科
					if (o[5] != null) {
						value = expertService.getDiscipline(o[5].toString());
					}
					expertInfo.put(mapid, value);
				}
				jsonMap.put("expertInfo", expertInfo);
			}
			return SUCCESS;
		}
	}
	
	/**
	 * 删除项目
	 * @return SUCCESS跳转列表
	 */
	public String delete() {
		try{
			awardService.deleteProjects(entityIds);
			return SUCCESS;
		}catch(Exception e){
			jsonMap.put(GlobalInfo.ERROR_INFO, "删除不成功");
			return INPUT;
		}
	}
	
	/**
	 * 删除校验
	 */
	public void validateDelete() {
		if (entityIds == null || entityIds.isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "请选择项目");
		}
		if (isReviewable != 0 && isReviewable != 1) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "无效的参评状态");
		}
	}
	
	@Override
	public Object[] advSearchCondition() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	//TODO 如果后续奖励其他类型多了，可以将一下公共代码提取到中间层
	/**
	 * 为奖励模块匹配专家（自动匹配）
	 * @return
	 * @throws Exception 
	 */
	public String matchExpert() throws Exception {
		Map httpSession = ActionContext.getContext().getSession();
		Map application = ActionContext.getContext().getApplication();
		Integer currentYear = (Integer) application.get("defaultYear");		//系统配置中默认显示的年份
		Integer displayedYear = (Integer) httpSession.get("defaultYear");	//首页上选择的显示年份
		if (!currentYear.equals(displayedYear)) {//如果两个年份不相同，禁止执行
			return ERROR;
		}
		expertIds = new ArrayList<String>();
		for (String eid : selectedExpertIds.split("\\s*;\\s*")) {
			if (eid.length() > 0) {
				expertIds.add(eid);
			}
		}
		awardService.matchExpert(awardType(), currentYear, expertIds, null, null);
		return SUCCESS;
	}
	
	
	/**
	 * 奖励批量"替换专家"
	 * 将一个专家的某些项目替给其他专家
	 * (算法公共Action)
	 * @return
	 * @throws Exception 
	 */
	public String batchReplaceExpert() throws Exception {
		Map httpSession = ActionContext.getContext().getSession();
		Map application = ActionContext.getContext().getApplication();
		Integer currentYear = (Integer) application.get("defaultYear");		//系统配置中默认显示的年份
		Integer displayedYear = (Integer) httpSession.get("defaultYear");	//首页上选择的显示年份
		if (!currentYear.equals(displayedYear)) {//如果两个年份不相同，禁止执行
			return ERROR;
		}
		
		//选择的新专家
		Set<String> expertIds = new HashSet<String>();
		for (String eid : selectedExpertIds.split("\\s*;\\s*")) {
			if (eid.length() > 0) {
				expertIds.add(eid);
			}
		}
		
		String[] aIds = awardId.split("[^a-zA-Z0-9]+");//处理选中的奖励ids
		List awardIds = Arrays.asList(aIds);
		
		//待剔除的专家
		List rejectExpertIds = new ArrayList();
		rejectExpertIds.add(selectExp);
		awardService.matchExpert(awardType(), currentYear, new ArrayList<String>(expertIds), awardIds, rejectExpertIds);
//		projectService.updateWarningReviewer(projectIds, currentYear);//（合并前已经注释，是否保留需要进一步分析！！！注释调的原因？匹配警告更新已经在匹配最后实现。其他）
		//其他如果进行手动匹配，或者批量替换，或者移交之后都需要一次匹配更新（建议）
		System.out.println("批量替换结束！");
		return SUCCESS;
	}

	/**
	 * 奖励指定由其他专家无条件接管
	 * (算法公共Action)
	 * @return
	 */
	public String transferMatch(){
		Map httpSession = ActionContext.getContext().getSession();
		Map application = ActionContext.getContext().getApplication();
		Integer currentYear = (Integer) application.get("defaultYear");		//系统配置中默认显示的年份
		Integer displayedYear = (Integer) httpSession.get("defaultYear");	//首页上选择的显示年份
		if (!currentYear.equals(displayedYear)) {//如果两个年份不相同，禁止执行
			return ERROR;
		}
		
		//指定的专家
		Expert newExpert = (Expert) baseService.query(Expert.class, expertId);
		
		//处理选中的奖励ids
		String[] aIds = awardId.split("[^a-zA-Z0-9]+");
		List awardIds = Arrays.asList(aIds);
		
		//该项目已存在的匹配结果
		Map paraMap = new HashMap();
		paraMap.put("entityId", entityId);	//projectIds有更新，需重新放入paraMap
		paraMap.put("awardIds", awardIds);	//projectIds有更新，需重新放入paraMap
		paraMap.put("year", currentYear);	//当前年份
		List<AwardApplicationReview> aarList = baseService.query("select aar from AwardApplicationReview aar where aar.type = '" + awardType() +"' and aar.award.id in (:awardIds) and aar.reviewer.id = :entityId and pr.year = :year", paraMap);
		for (AwardApplicationReview aar : aarList) {
			//更新删除
			AwardApplicationReviewUpdate aaruDel = new AwardApplicationReviewUpdate(aar, 0, 1);
			baseService.add(aaruDel);
			//更新专家
			aar.setIsManual(1);
			aar.setMatchTime(new Date());
			aar.setReviewer(newExpert);
			baseService.modify(aar);
			//更新新增
			AwardApplicationReviewUpdate aaruAdd = new AwardApplicationReviewUpdate(aar, 1, 1);
			baseService.add(aaruAdd);
		}
		
		//更新警告信息
		awardService.awardUpdateWarningReviewer(awardType(), awardIds, currentYear);
		System.out.println("奖励指定移交结束！");
		return SUCCESS;
	}
	
	/**
	 * 手工调整奖励专家匹配
	 * 不经过多伦匹配过程
	 * (算法公共Action)
	 * @return
	 */
	@Transactional
	public String reMatchExpert() {
		Map httpSession = ActionContext.getContext().getSession();
		Map application = ActionContext.getContext().getApplication();
		Integer currentYear = (Integer) application.get("defaultYear");	//系统配置中的默认显示年份
		Integer displayedYear = (Integer) httpSession.get("defaultYear");	//首页上选择的显示年份
		if (!currentYear.equals(displayedYear)) {//如果两个年份不相同，禁止执行
			return ERROR;
		}
		
		List<AwardApplication> awards = new ArrayList<AwardApplication>();	//待手工调整的项目
		String[] awardIds = entityId.split("[;\\s]+");	//页面上选择的，待手工调整的项目的ID
		if(null == selectExp) 
			selectExp = "";
		String[] selectedExpertIds = selectExp.split("[;\\s]+");	//从专家树中选择的专家的ID
		Set<String> selectedReviewerIds = new HashSet(Arrays.asList(selectedExpertIds));//选择的专家的ID集合
		//TODO 选择的专家的数量不能超过5人
		if (selectedReviewerIds.size() > 5) {
			return "more";
		}
		for (String aid : awardIds) {
			AwardApplication award = (AwardApplication) baseService.query(AwardApplication.class, aid);
			//只允许调整当前年的项目
			if (!award.getYear().equals(currentYear)) {
				continue;
			}
			awards.add(award);
			//该项目已存在的匹配结果
			List<AwardApplicationReview> existingPrs = baseService.query("select aar from AwardApplication aa, AwardApplicationReview aar where aa.id = aar.award.id and aa.id = ?", aid);
			Set<String> existingReviewerIds = new HashSet<String>();//该项目已存在的专家集合
			
			//在已有匹配结果中删除selectExp中没有的专家
			for (AwardApplicationReview aar : existingPrs) {
				existingReviewerIds.add(aar.getReviewer().getId());
				if (!selectedReviewerIds.contains(aar.getReviewer().getId())) {
					AwardApplicationReviewUpdate aaru = new AwardApplicationReviewUpdate(aar, 0, 1);
					baseService.add(aaru);
					baseService.delete(aar);
				}
			}
			//添加selectExpert中新加入的专家
			for (String selectedReviewerId : selectedReviewerIds) {
				if (!selectedReviewerId.trim().isEmpty() && !existingReviewerIds.contains(selectedReviewerId)) {
					AwardApplicationReview aar = new AwardApplicationReview(selectedReviewerId, aid, 1, award.getYear(), awardType());
					AwardApplicationReviewUpdate aaru = new AwardApplicationReviewUpdate(aar, 1, 1);
					baseService.add(aar);
					baseService.add(aaru);
				}
			}
		}
		awardService.awardUpdateWarningReviewer(awardType(), Arrays.asList(awardIds), currentYear);
		System.out.println("手动匹配结束！");
		return awardIds.length == 1 ? "one" : "more";
	}
	
	/**
	 * 返回删除匹配页面
	 * @return
	 */
	public String toDeleteMatches(){
		return SUCCESS;
	}
	
	/**
	 * 删除已有匹配对所有信息
	 * @return
	 */
	@Transactional
	public String deleteMatches(){
		Integer defaultYear = (Integer) session.get("defaultYear");
		awardService.deleteAwardMatchInfos(deleteAutoMatches,deleteManualMatches,defaultYear,awardType());
		return SUCCESS;
	}
	
	//项目与专家树耦合性太强，并且二者是对等的地位，故重新再奖励模块实现专家树不与项目共用
	/**
	 * 创建专家树
	 * @return
	 */
	public String createExpertTree() {
		Map dataMap = awardService.fetchExpertData(); 
		//nodesInfo为zTree插件要求的数据格式，这里调用service中的方法实现专家树的数据组织
		nodesInfo = projectService.getNodesInfo(dataMap);
		return SUCCESS;
	}
	
	/**
	 * 《公共方法》
	 * 进入手工选择专家页面<br>
	 * 给选定项目选择专家
	 * @return
	 */
	public String toSelectExpert() {
		String[] pojids = entityId.split("[^a-zA-Z0-9]+");
		String tmp = "'" + pojids[0] + "'";
		for(int i = 1; i < pojids.length; i++) 
			tmp += ", '" + pojids[i] + "'";
		Map paraMap = new HashMap();
		paraMap.put("defaultYear", session.get("defaultYear"));
		try {
			
			awardList = baseService.query("select aa.id, aa.productName, aa.productType, aa.applicantName, u.name, aa.disciplineCode from AwardApplication aa, University u where aa.type = '" + awardType() +"' and aa.year = :defaultYear and aa.unitCode = u.code and aa.id in ( " + tmp + ") ", paraMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//奖励不需要学科代码的组装
//		if(awardList != null && !awardList.isEmpty()) {
//			String value = null;
//			for(int i = 0; i < awardList.size(); i++) {
//				Object[] o = (Object[]) awardList.get(i);
//				if (o[5] != null) {
//					value = projectService.getDiscipline(o[5].toString());
//				}
//				o[5] = value;
//			}
//		}
		return SUCCESS;
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
			disList = awardService.query("select s from SystemOption s where length(s.code) = 3 and s.standard like '%2009%' order by s.code asc ");
			session.put("disList", disList);
		} else {
			disList = (List<SystemOption>) session.get("disList");
		}
		return SUCCESS;
	}
	
	/**
	 * 传入参数：entityid
	 * iframe里面内嵌此action可以获得专家树,可以post传值projectId及discipline
	 * 并初始化session中的参数
	 * @return
	 */
	public String showExpertTree() {
		if(entityId != null) {//entityId表示当前项目的id
			awardService.initExpertTree(entityId);
		} else {
			projectService.initExpertTree(null);
			session.put("expertTree_discipline1s", discipline);	//discipline表示一级学科代码
		}
		//获取所有的一级学科代码列表，用户页面学科代码checkbox的显示
		if(session.get("disList") == null) {
			disList = projectService.query("select s from SystemOption s where length(s.code) = 3 and s.standard like '%2009%' order by s.code asc ");
			session.put("disList", disList);
		} else {
			disList = (List<SystemOption>) session.get("disList");
		}
		return SUCCESS;
	}
	
	/**
	 * 弹出层导出
	 * @return
	 */
	public String popExport(){
		Map session = ActionContext.getContext().getSession();
		Integer defaultYear = (Integer) session.get("defaultYear");
		List<Object> a = baseService.query("select distinct aru.matchTime from AwardApplicationReviewUpdate aru where aru.type = '" + awardType() + "' and aru.matchTime = (select min(aru1.matchTime) from AwardApplicationReviewUpdate aru1 where aru1.type = '" + awardType() + "' and aru1.year = " + defaultYear+ ")");
		if (a != null && a.size() > 0) {
			Date aDate = (Date) a.get(0);
			startTime = aDate.getTime();
		}
		List<Object> b = baseService.query("select distinct aru.matchTime from AwardApplicationReviewUpdate aru where aru.type = '" + awardType() + "' and aru.matchTime = (select max(aru1.matchTime) from AwardApplicationReviewUpdate aru1 where aru1.type = '" + awardType() + "' and aru1.year = " + defaultYear+ ")");
		if (b != null && b.size() > 0) {
			Date bDate = (Date) b.get(0);
			endTime = bDate.getTime();	
		}		
		return SUCCESS;
	}
	
	/**
	 * 导出奖励一览表
	 * @return
	 * @throws Exception
	 */
	public String exportAward() {
		return SUCCESS;
	}
	
	/**
	 * 导出项目一览表
	 * @return
	 * @throws Exception
	 */
	public InputStream getAwardFile() throws Exception{
		//导出的Excel文件名
		Map session = ActionContext.getContext().getSession();
		String yearString = session.get("defaultYear").toString();
		fileFileName = yearString + "年度奖励一览表.xls";
		fileFileName = new String(fileFileName.getBytes(), "ISO8859-1");
		return awardService.exportAward(exportAll, false);
	}
	
	/**
	 * 导出项目专家匹配表
	 * @return
	 * @throws Exception
	 */
	public String exportAwardReviewer() {
		return SUCCESS;
	}
	
	/**
	 * 导出奖励专家匹配表
	 * @return 输入流
	 */
	public InputStream getAwardReviewerFile() throws Exception {
		//导出的Excel文件名
		Map session = ActionContext.getContext().getSession();
		String yearString = session.get("defaultYear").toString();
		fileFileName = yearString + "年度奖励专家匹配表.xls";
		fileFileName = new String(fileFileName.getBytes(), "ISO8859-1");
		return awardService.exportAward(exportAll, true);
	}
	
	/**
	 * 导出专家调整结果
	 * @return
	 * @throws Exception
	 */
	public String exportMatchUpdate() {
		return SUCCESS;
	}
	
	
	/**
	 * 导出专家匹配更新表
	 * @return 输入流
	 * @throws Exception
	 */
	public InputStream getMatchUpdateFile() throws Exception {
		Map session = ActionContext.getContext().getSession();
		Integer year = (Integer) session.get("defaultYear");
		fileFileName = year + "年度奖励专家匹配更新表.xls";
		fileFileName = new String(fileFileName.getBytes(), "ISO8859-1");
		return awardService.exportMatchUpdate(year, new Date(startTime), new Date(endTime));
	}
	
	public int getIsReviewable() {
		return isReviewable;
	}
	public void setIsReviewable(int isReviewable) {
		this.isReviewable = isReviewable;
	}
	public IProjectService getProjectService() {
		return projectService;
	}
	public void setProjectService(IProjectService projectService) {
		this.projectService = projectService;
	}
	public IAwardService getAwardService() {
		return awardService;
	}
	public void setAwardService(IAwardService awardService) {
		this.awardService = awardService;
	}
	public IExpertService getExpertService() {
		return expertService;
	}
	public void setExpertService(IExpertService expertService) {
		this.expertService = expertService;
	}
	public int getKeyword1() {
		return keyword1;
	}

	public void setKeyword1(int keyword1) {
		this.keyword1 = keyword1;
	}

	public AwardApplication getAwardApplication() {
		return awardApplication;
	}

	public void setAwardApplication(AwardApplication awardApplication) {
		this.awardApplication = awardApplication;
	}
	public List<String> getExpertIds() {
		return expertIds;
	}
	public void setExpertIds(List<String> expertIds) {
		this.expertIds = expertIds;
	}
	public String getSelectedExpertIds() {
		return selectedExpertIds;
	}
	public void setSelectedExpertIds(String selectedExpertIds) {
		this.selectedExpertIds = selectedExpertIds;
	}
	public String getAwardId() {
		return awardId;
	}
	public void setAwardId(String awardId) {
		this.awardId = awardId;
	}
	public String getSelectExp() {
		return selectExp;
	}
	public void setSelectExp(String selectExp) {
		this.selectExp = selectExp;
	}
	public String getExpertId() {
		return expertId;
	}
	public void setExpertId(String expertId) {
		this.expertId = expertId;
	}
	public List getAwardList() {
		return awardList;
	}
	public void setAwardList(List awardList) {
		this.awardList = awardList;
	}
	public List getNodesInfo() {
		return nodesInfo;
	}
	public void setNodesInfo(List nodesInfo) {
		this.nodesInfo = nodesInfo;
	}
	public String getDiscipline() {
		return discipline;
	}
	public void setDiscipline(String discipline) {
		this.discipline = discipline;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getEname() {
		return ename;
	}
	public void setEname(String ename) {
		this.ename = ename;
	}
	public List<SystemOption> getDisList() {
		return disList;
	}
	public void setDisList(List<SystemOption> disList) {
		this.disList = disList;
	}
	public int getDeleteManualMatches() {
		return deleteManualMatches;
	}
	public void setDeleteManualMatches(int deleteManualMatches) {
		this.deleteManualMatches = deleteManualMatches;
	}
	public int getDeleteAutoMatches() {
		return deleteAutoMatches;
	}
	public void setDeleteAutoMatches(int deleteAutoMatches) {
		this.deleteAutoMatches = deleteAutoMatches;
	}
	
	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	public long getEndTime() {
		return endTime;
	}
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
	public String getFileFileName() {
		return fileFileName;
	}
	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}
	public int getExportAll() {
		return exportAll;
	}
	public void setExportAll(int exportAll) {
		this.exportAll = exportAll;
	}
}

