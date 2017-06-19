package csdc.service.imp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;


import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFFooter;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.dao.support.DaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionContext;

import csdc.bean.AwardApplication;
import csdc.bean.AwardApplicationReview;
import csdc.bean.Expert;
import csdc.bean.common.ExpertLink;
import csdc.service.IAwardService;
import csdc.tool.ApplicationContainer;
import csdc.tool.DatetimeTool;
import csdc.tool.ExpertTreeItem;
import csdc.tool.HSSFExport;
import csdc.tool.SortExpert;
import csdc.tool.SpringBean;
import csdc.tool.matcher.MatcherWarningUpdater;
import csdc.tool.matcher.MoeSocialReviewerMatcher;

public class AwardService extends BaseService implements IAwardService{
	
	/**
	 * 奖励模块的DWR调用部分
	 */
	public String initParam(String data) {
		ActionContext context = ActionContext.getContext();
		Map application = context.getApplication();
		Integer expertProjectMin = (Integer) application.get("AwardExpertProjectMin");
		Integer expertProjectMax = (Integer) application.get("AwardExpertProjectMax");
		Integer projectMinistryExpertNumber = (Integer) application.get("AwardMinistryExpertNumber");
		Integer projectLocalExpertNumber = (Integer) application.get("AwardLocalExpertNumber");
		Integer projectExpertNumber = projectMinistryExpertNumber + projectLocalExpertNumber;
		Map session = context.getSession();
		if( (Integer)session.get("expertTree_useProjects") == 0) {
			projectExpertNumber = 9999;
		}
		//返回字符串格式："每个项目所需专家数,每个专家最大参评的项目数,选中的项目ids,选中的专家ids,选中的一级学科代码"
		String result = projectExpertNumber + "," + expertProjectMin + "," + expertProjectMax + "," + session.get("expertTree_projectIds") + "," + session.get("expertTree_selectExpertIds") + "," + session.get("expertTree_discipline1s");
		return result;
	}
	
	/**
	 * 专家树中根据选中的专家id","的字符串，获取响应的专家信息（用于右侧已经选择专家列表显示）
	 */
	public String getExpInfo(String awardType, String ids) {
		
		if(ids == null || ids.isEmpty() || ids.equals("null"))
			return null;
		String[] str = ids.split("[^a-zA-Z0-9]");
		String re = "";
		ActionContext context = ActionContext.getContext();
		Map session = context.getSession();
		Map expDetailMap = (Map) session.get("allExp");
		Integer defaultYear = (Integer) session.get("defaultYear");
		//高校代码 -> 高校名称
		HashMap<String, String> univMap = (HashMap<String, String>) context.getApplication().get("univCodeNameMap");
				
		ExpertTreeItem ExpItem = null;
		for(int i = 0; i < str.length; i++) {
			if(!str[i].equals("null")) {
				if(expDetailMap != null && expDetailMap.size() >= 0) {
					ExpItem = (ExpertTreeItem) expDetailMap.get(str[i]);
					if(ExpItem != null) {
						re = re + str[i] + "," + "[" + ExpItem.discipline + "]^" + ExpItem.name + "^" + ExpItem.university + ExpItem.specialityTitle + "[参评" + ExpItem.pojcnt + "项]" + "%";
					}
					else {
						Expert tmp = (Expert) this.query(Expert.class, str[i]);
						int xx = this.query("select aar from Expert e left join e.awardApplicationReview aar with aar.year = " + defaultYear + " and aar.type = '" + awardType + "' where e.isReviewer = 1 and e.id = '" + str[i] + "' ").size();
//						List tmpp = this.query("select p from GeneralReviewer p left outer join p.reviewer r where p.year = " + defaultYear + " and p.priority <= 5 and r.isReviewer = 1 and r.id = '" + str[i] + "' ");
//						List tmpp = this.query("select p from GeneralReviewer p left outer join p.reviewer r where p.year = " + defaultYear + " and r.isReviewer = 1 and r.id = '" + str[i] + "' ");
//						int yy = 0;
//						if(tmpp == null || tmpp.isEmpty())
//							yy = 0;
//						else
//							yy = tmpp.size();
						re = re + str[i] + "," + "[" + tmp.getDiscipline() + "]^" + tmp.getName() + "^" + univMap.get(tmp.getUniversityCode()) + tmp.getSpecialityTitle() + "[参评" + xx + "项]" + "%";
					}
				}
				else {
					Expert tmp = (Expert) this.query(Expert.class, str[i]);
					int xx = this.query("select aar from Expert e left join e.awardApplicationReview aar with aar.type = '" + awardType + "' where e.isReviewer = 1 and e.id = '" + str[i] + "' and aar.year = " + defaultYear ).size();
//					List tmpp2 =  this.query("select p from GeneralReviewer p left outer join p.reviewer r where p.year = " + defaultYear + " and p.priority <= 5 and r.isReviewer = 1 and r.id = '" + str[i] + "' ");
//					List tmpp2 =  this.query("select p from GeneralReviewer p left outer join p.reviewer r where p.year = " + defaultYear + " and r.isReviewer = 1 and r.id = '" + str[i] + "' ");
//					if(tmpp2 == null || tmpp2.isEmpty())
//						yy = 0;
//					else 
//						yy = tmpp2.size();
					re = re + str[i] + "," + "[" + tmp.getDiscipline() + "]^" + tmp.getName() + "^" + univMap.get(tmp.getUniversityCode()) + tmp.getSpecialityTitle() + "[参评" + xx + "项]" + "%";
				}
			}
		}
		if(re.length() > 0)
			re = re.substring(0, re.length() - 1);
		return re;
	}
	


	/**
	 * 根据奖励ID找到已分配专家的名称，高校
	 * @param projectid项目ID
	 * @return 相关信息
	 */
	public List<ExpertLink> getExpert(String awardId) {
		List<ExpertLink> back = new ArrayList<ExpertLink>();// 用于返回
//		List<Object> re = this.query("select e.id, e.name, u.name, e.specialityTitle, e.universityCode, e.isReviewer from Expert e, University u, GeneralReviewer pr where pr.project.id = '" + projectid + "' and pr.reviewer.id = e.id and e.universityCode = u.code order by pr.priority asc");
		List<Object> re = this.query("select e.id, e.name, u.name, e.specialityTitle, e.universityCode, e.isReviewer from Expert e, University u, AwardApplicationReview ar where ar.award.id = ? and ar.reviewer.id = e.id and e.universityCode = u.code order by ar.priority asc ", awardId);
		if (re.size() > 0) {
			Object[] o;
			for (int i = 0; i < re.size(); i++) {
				o = (Object[]) re.get(i);
				ExpertLink e = new ExpertLink();
				e.setId(o[0].toString());
				e.setName(o[1].toString());
				e.setUname(o[2].toString());
				e.setTitle(o[3] != null ? o[3].toString() : "");
				e.setIsInner((Integer.parseInt(o[4].toString()) < 10000 ? 1 : 0));
				e.setIsReviewer(Integer.parseInt(o[5].toString()));
				back.add(e);
			}
		}
		return back;
	}
	
	/**
	 * 自动匹配
	 * （公共业务方法）
	 * Action 调用 公共Service
	 * @param year
	 * @param expertIds
	 * @param projectids
	 * @param rejectExpertIds
	 * @throws Exception
	 */
	public void matchExpert(String awardType, 
			Integer year, 
			List<String> expertIds, 
			List<String> awardids, 
			List<String> rejectExpertIds) throws Exception {
		System.out.println("》》》开始" + awardType + "奖励的专家匹配 ···");
		long begin = System.currentTimeMillis();
		if (awardType.equals("moeSocial")) {
			MoeSocialReviewerMatcher matcher = (MoeSocialReviewerMatcher) SpringBean.getBean("moeSocialReviewerMatcher", ApplicationContainer.sc);
			matcher.matchExpert(year, expertIds, awardids, rejectExpertIds);
		} 
		//扩展其他项目类型的自动匹配器处理
		long end = System.currentTimeMillis();
		System.out.println("完成" + awardType + "奖励的专家匹配 ！用时 ：" + (end - begin) + " ms");
	}
	/**
	 * 项目匹配更新子类业务直接调用
	 * （公共业务方法）
	 * @param projectType
	 * @param projectIds
	 * @param year
	 */
	public void awardUpdateWarningReviewer(String awardType, List<String> awardIds, Integer year) {
		long begin = System.currentTimeMillis();
		System.out.println(">>>开始" + awardType + "项目的匹配警告更新 ···");
		
		MatcherWarningUpdater updater = (MatcherWarningUpdater) SpringBean.getBean("matcherWarningUpdater", ApplicationContainer.sc);
		updater.update(awardType, awardIds, year);
		
		long end = System.currentTimeMillis();
		System.out.println(">>>完成" + awardType + "项目的匹配警告更新!用时 ：" + (end - begin) + " ms");
	}
	
	
	/**
	 * 清除奖励某年匹配信息
	 * 
	 * @param deleteAutoMatches 是否清除自动匹配对，1是，0否
	 * @param deleteManualMatches 是否清除手工匹配对，1是，0否
	 * @param year 待清除年
	 */
	public void deleteAwardMatchInfos(int deleteAutoMatches,int deleteManualMatches,int year,String awardType){
		long begin = System.currentTimeMillis();
		//1.清除匹配对信息
		try {
			if (deleteAutoMatches == 1) {
				this.execute("delete from AwardApplicationReview aar where aar.type = '" + awardType + "' and aar.year=? and aar.isManual = 0",year);
			}
			if (deleteManualMatches == 1) {
				this.execute("delete from AwardApplicationReview aar where aar.type = '" + awardType + "' and aar.year=? and aar.isManual = 1",year);
			}
			this.execute("delete from AwardApplicationReviewUpdate aaru where aaru.type = '" + awardType + "' and aaru.year=?",year);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//2.清除项目警告信息(如果不清除匹配对即，则匹配警告信息不清空)
		if (!(deleteAutoMatches == 0 && deleteManualMatches == 0)) {
			try {
				StringBuffer hql = new StringBuffer();
				Map map = new HashMap();
				hql.append("update AwardApplication aa set aa.warningReviewer = null where aa.type = '" + awardType + "' and aa.year =:year and aa.isReviewable = 1");
				map.put("year", year);
				this.execute(hql.toString(), map);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//执行一次匹配更新警告操作(自动匹配更新警告)
		long end = System.currentTimeMillis();
		System.out.println("清空匹配完成，用时 ：" + (end - begin) + " ms" );
	}
	//奖励需要首先进行分组操作，通过以下方法实现
	
	public void gruopAward(String currentYear, String awardType ,Map argumentMap) {
		//添加系统配置表的一级学科代码
		Map map = (Map) argumentMap.get("disciplineOne");//获取一级学科代码
		Set discOneSet = new HashSet<String>();//一级学科代码集合
		Map argsMap = new HashMap<String, String>();
		argsMap.put("year", currentYear);
		argsMap.put("awardType", awardType);
		String hql4Award = "select project from AwardApplication aa where aa.type = :awardType and aa.year = :year and aa.isReviewable = 1";
		List<AwardApplication> awardsList = query(hql4Award, argsMap);
		/**
		 * 预处理（每个奖励的学科代码预处理，后续小组的学科代码依据）
		 */
		//学科门类处理
		List<AwardApplication> discTypeAwardsList = new LinkedList<AwardApplication>();
		//“成果普及奖”的奖励去除“工科学科代码”后保留文科学科代码，后单独作为一组
		List<AwardApplication> cgpjjAwards = new LinkedList<AwardApplication>();
		for (AwardApplication awardApplication : awardsList) {
			//学科类别
			String disciplineType = awardApplication.getDisciplinePrimitive();
			String disciplineTypeCode = _disciplineType2Code(disciplineType);
			String discCode = awardApplication.getDisciplineCode();
			
			String awardCatalog = awardApplication.getAwardCatalog();
			String[] result = discCode.split("\\D+");
			String resultDiscs = "";
			
			//特殊的学科门类已在选用smdb的学科代码如：980,970,990
			//交叉学科处理后去除“工科学科代码”后保留文科学科代码，和其他奖励分区情况一样
			if (disciplineTypeCode.equals("zjxxk") ) {
				//按照综合交叉学科处理门类，去除非一级学科代码开头的工科学科代码
				for (int i = 0; i < result.length; i++) {
					String temp = result[i];
					if (temp.length() >= 3 && discOneSet.contains(temp.substring(0, 3))) {
						//找到文科学科代码
						resultDiscs += (disciplineTypeCode.equals("") ? "" : "; ") + temp;
					}
				}
				//综合交叉学科必有文科学科代码
				awardApplication.setDisciplineCode(resultDiscs);
			} else {
				//其他门类，仅保留此学科门类的学科代码，如果没有，则以此“disciplineTypeCode”作为其学科代码
				for (int i = 0; i < result.length; i++) {
					String temp = result[i];
					if (temp.startsWith(disciplineTypeCode)) {
						resultDiscs += (disciplineTypeCode.equals("") ? "" : "; ") + temp;
					}
				}
				if (resultDiscs.equals("")) {
					resultDiscs = disciplineTypeCode;
				}
				awardApplication.setDisciplineCode(resultDiscs);
			}
			//“成果普及奖”的奖励处理方式和“交叉学科”“保留以学科门类开头”的处理方式一致，但是要单独作为一组
			if (awardCatalog.equals("成果普及奖")) {
				cgpjjAwards.add(awardApplication);
			} else {
				discTypeAwardsList.add(awardApplication);
			}
		}
		/**
		 * 排序
		 */
		//以AwardApplication.DisciplineCode的字段排序，字段变成了学科代码+ “; ”的形式
		Comparator<AwardApplication> groupCompator4Award = new Comparator<AwardApplication>() {
			//学科代码每个字母比对“8200602; 42002”
			public int compare(AwardApplication o1, AwardApplication o2) {
				AwardApplication award1 = (AwardApplication)o1;
				AwardApplication award2 = (AwardApplication)o2;
				String disciplineCode1 = award1.getDisciplineCode();
				String disciplineCode2 = award2.getDisciplineCode();
				if (!disciplineCode1.equals(disciplineCode2)) {
					return disciplineCode1.compareTo(disciplineCode2);
				}
				//学科代码相等，则按照id属性排列
				String id1 = award1.getId();
				String id2 = award2.getId();
				return  id1.compareTo(id2);
			}
		};
		Collections.sort(discTypeAwardsList, groupCompator4Award);
		Collections.sort(cgpjjAwards, groupCompator4Award);
		/**
		 * 分组
		 */
		//以一级学科代码作为“大组”,以一级学科为大组进行分类且必然存在有其学科门类对应的一级学科
		Map groupDiscOneMap = new HashMap<String, List>();//一级学科代码 -> 有序大组
		for (int i = 0; i < discTypeAwardsList.size(); i++) {
			AwardApplication tempAward  = discTypeAwardsList.get(i);
			String discOne = (String) tempAward.getDisciplineCode().subSequence(0, 3);
			List discOneList = (List) groupDiscOneMap.get(discOne);
			if (discOneList == null ) {
				discOneList = new LinkedList<AwardApplication>();
			}
			discOneList.add(tempAward);
		}
		//大组分小组处理
		System.out.println("奖励模块的分组的信息：");
		System.err.println("成果普及奖 --> " + cgpjjAwards.size() + "个数量");
		Set<String> keySet = groupDiscOneMap.keySet();
		for(String key : keySet) {
			System.out.println(key + " --> " + ((List)groupDiscOneMap.get(key)).size() + "个数量");
		}
		
		for(String key : keySet) {
			List tempList = (List)groupDiscOneMap.get(key);
			//大组内依据小组划分
			
		}
		
		
		
		
		
		
		
		
	}
	public static void main(String[] args) {
		String arg1 = "70212";
		String arg2 = "70212; 15023";
		System.out.println(arg1.compareTo(arg2));
		
		String arg3 = "80212; 45023";
		String arg4 = "70212";
		System.out.println(arg1.compareTo(arg3));
		System.out.println(arg1.compareTo(arg4));
		String arg5 = "70212";
		
	}
	//对于大组内的奖励列表，以二级学科作为小组划分依据，每个小组人数的容忍区间：40 ~ 70
	private void groupAwardOnDiscTwo(List<AwardApplication> discOneAwardLists) {
		//容忍区间范围
		Integer scopeBegin = 40;
		Integer scopeEnd = 70;
		//先用二级学科划分，没有二级学科的以一级学科作为依据
		Map<String, List<AwardApplication>> blocksMap = new HashMap<String, List<AwardApplication>>();
		for (int i = 0; i < discOneAwardLists.size(); i++) {
			AwardApplication aa = discOneAwardLists.get(i);
			String disciplineCode = aa.getDisciplineCode();
			String discTwo = "";
			if (disciplineCode.length() > 3) {
				discTwo = disciplineCode.substring(0, 5);
			} else {
				//只有一级学科
				discTwo = disciplineCode.substring(0, 3);
			}
			
			List<AwardApplication> discTwoList = blocksMap.get(discTwo);
			if (discTwoList == null) {
				discTwoList = new LinkedList<AwardApplication>();
			}
			discTwoList.add(aa);
			//创建分组对象
			
		}
		
		//根据小组数量容忍度整合各个小组使之在容忍区间内
		 blocksMap.size();
		for (String discTwo : blocksMap.keySet()) {
			Integer size = ((List<AwardApplication>)blocksMap.get(discTwo)).size();
		}
		
		Object[] 
		
		
		
		
	}
	
	/**
	 * 因为奖励申请中的学科代码是与smdb保持一致的，即将smdb的学科代码属性格式“82099/法学其他学科; 1902099/社会心理学其他学科”
	 * 转化为匹配器通用格式“82099; 1902099”
	 */
	private String filterDisciplineCode(String smdbStyleDiscipline) {
		if (smdbStyleDiscipline == null || smdbStyleDiscipline.equals("")) {
			return null;
		}
		String[] diss = smdbStyleDiscipline.split("\\D+");
		String resultDis = "";
		for (int i = 0; i < diss.length; i++) {
			String item = diss[i].trim();
			resultDis += (resultDis.equals("") ? item : "; " + item);
		}
		return resultDis;
	}

	/**
	 * 学科门类转换（需要核对是否映射准确）
	 */
	private String _disciplineType2Code(String subject){
		if(subject.equals("地球科学")){
			return "170";
		}else if(subject.equals("心理学")){
			return "190";
		}else if(subject.equals("管理学")){
			return "630";
		}else if(subject.equals("马克思主义")){
			return "710";
		}else if(subject.equals("逻辑学")){
			return "72040";
		}else if(subject.equals("哲学")){
			return "720";
		}else if(subject.equals("宗教学")){
			return "730";
		}else if(subject.equals("语言学")){
			return "740";
		}else if (subject.equals("中国文学")){
			return "75011-44";//无
		}else if(subject.equals("外国文学")){
			return "75047-99";//750
		}else if(subject.equals("艺术学")){
			return "760";
		}else if(subject.equals("历史学")){
			return "770";
		}else if(subject.equals("考古学")){
			return "780";
		}else if(subject.equals("经济学")){
			return "790";
		}else if(subject.equals("政治学")){
			return "810";
		}else if(subject.equals("法学")){
			return "820";
		}else if(subject.equals("社会学")){
			return "840";
		}else if(subject.equals("民族学与文化学")){
			return "850";
		}else if(subject.equals("新闻学与传播学")){
			return "860";
		}else if(subject.equals("图书馆、情报与文献学")){
			return "870";
		}else if(subject.equals("教育学")){
			return "880";
		}else if(subject.equals("体育学")){
			return "890";
		}else if(subject.equals("统计学")){
			return "910";
		}else if(subject.equals("港澳台问题研究")){
			return "980"; //无
		}else if(subject.equals("国际问题研究")){
			return "990";
		}else if(subject.equals("国际问题研究")){
			return "970";
		}else if(subject.equals("综合研究/交叉学科")){
			return "zjxxk";
		}else return "未知";
	}
	public String _disciplineType(String subject){
		if(subject.contains("170")){
			return "地球科学";
		}else if(subject.startsWith("190")){
			return "心理学";
		}else if(subject.startsWith("630")){
			return "管理学";
		}else if(subject.startsWith("710")){
			return "马克思主义";
		}else if(subject.startsWith("720")){
			if(subject.startsWith("72040")){
				return "逻辑学";
			}else return "哲学";
		}else if(subject.startsWith("730")){
			return "宗教学";
		}else if(subject.startsWith("740")){
			return "语言学";
		}else if (subject.startsWith("75011-44")){
			return "中国文学";
		}else if(subject.startsWith("75047-99")){
			return "外国文学";	
		}else if(subject.startsWith("760")){
			return "艺术学";
		}else if(subject.startsWith("770")){
			return "历史学";
		}else if(subject.startsWith("780")){
			return "考古学";
		}else if(subject.startsWith("790")){
			return "经济学";
		}else if(subject.startsWith("810")){
			return "政治学";
		}else if(subject.startsWith("820")){
			return "法学";
		}else if(subject.startsWith("840")){
			return "社会学";
		}else if(subject.startsWith("850")){
			return "民族学与文化学";
		}else if(subject.startsWith("860")){
			return "新闻学与传播学";
		}else if(subject.startsWith("870")){
			return "图书馆、情报与文献学";
		}else if(subject.startsWith("880")){
			return "教育学";
		}else if(subject.startsWith("890")){
			return "体育学";
		}else if(subject.startsWith("910")){
			return "统计学";
		}else if(subject.startsWith("GAT")){
			return "港澳台问题研究";
		}else if(subject.startsWith("GJW")){
			return "国际问题研究";
		}else if(subject.startsWith("zjxxk")){
			return "综合研究/交叉学科";
		}else return "未知";
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
			//奖励不需要考虑项目成员情况，因为成员无高校信息
			for(int i = 0; i < ids.length; i++) {
				AwardApplication award = (AwardApplication) query(AwardApplication.class, ids[i]);
				if(award != null) {
					//因为奖励的的项成员无高校信息，则只能限排除与“申请人”的姓名与高校集合
					expertNameList.add(award.getApplicantName());
					uniCodeList.add(award.getUnitCode());
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
		//筛选所属高校为部署高校，属性为参评、非重点人、专职人员，手机和邮箱全非空，当前年没申请奖励， 评价等级大于限制阈值，当前时间6个月内入库，获取人才奖励资助的专家
		StringBuffer hql4Reviewer = new StringBuffer("select expert.id, expert.name, expert.specialityTitle, expert.discipline, COUNT(aar), u.name, u.abbr, expert.universityCode from Expert expert left join expert.awardApplicationReview aar with aar.year = " + defaultYear + " and aar.type = 'moeSocial', University u where expert.universityCode = u.code");
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
		
		//高校参评（奖励）专家数量
		HashMap<String, String> univReviewerCnt = new HashMap<String, String>();
		String hql4urCnt = "select u.name, count(distinct aar.reviewer.id) from University u, AwardApplicationReview aar, Expert expert where aar.type = 'moeSocial' and u.code = expert.universityCode and expert.id = aar.reviewer.id and aar.year = ? group by u.name ";
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
	 * 初始化专家树
	 * @param projectIds 奖励id，用分号空格隔开，如果不涉及项目则填null
	 */
	public void initExpertTree(String awardIds) {
		Map session = ActionContext.getContext().getSession();
		if(awardIds != null && !awardIds.isEmpty()) {
			session.remove("expertTree_universityName");//高校名称
			session.remove("expertTree_expertName");//专家姓名
			session.put("expertTree_discipline1s", unionProjectDis(awardIds));//专家树中的学科代码
			session.put("expertTree_selectExpertIds", unionProjectRev(awardIds));//专家树中的已选专家ID
			session.put("expertTree_projectIds", awardIds);//选中的奖励ids
			session.put("expertTree_useProjects", 1);
		} else {
			session.remove("expertTree_discipline1s");
			session.remove("expertTree_selectExpertIds");
			session.remove("expertTree_universityName");
			session.remove("expertTree_expertName");
			session.put("expertTree_useProjects", 0);
		}
	}

	/**
	 * 联合项目的各个学科代码（找到各个项目共有的学科代码）
	 * @param ids 项目ids
	 */
	public String unionProjectDis(String ids) {
		String[] awardids = ids.split("[^a-zA-Z0-9]+");
		List<String> allLevelOnes = new ArrayList();//所以项目的一级学科代码
		for(int i = 0; i < awardids.length; i++) {//对每个项目进行遍历
			AwardApplication a = (AwardApplication)this.query(AwardApplication.class, awardids[i]);
			String dis = a.getDisciplineCode();
			if(null == dis){
				continue;
			}
			String[] diss = dis.split("\\D+");//得到该项目的各个学科代码
			Set<String> levelOneSet = new HashSet<String>();
			for(int j = 0; j < diss.length; j++) {
				if(diss[j].length() < 3){
					continue;
				}
				levelOneSet.add(diss[j].substring(0, 3));
			}
			StringBuffer levelOneStr = new StringBuffer();
			for (String levelOne : levelOneSet) {
				levelOneStr.append(levelOne).append(";");
			}
			int len = levelOneStr.length();
			if(len >0){//去除最后一个分号
				levelOneStr.deleteCharAt(len-1);
			}
			allLevelOnes.add(levelOneStr.toString());
		}
		String value = "";
		if (null != allLevelOnes && allLevelOnes.size() != 0){
			value = allLevelOnes.get(0);
			for(int i = 1; i < allLevelOnes.size(); i++) {
				value = unionString(value, allLevelOnes.get(i));
			}
		}
		//System.out.println("disci " + disci);
		return value;
	}
	
	/**
	 * 联合项目评审专家
	 * @param ids 项目ids
	 */
	public String unionProjectRev(String ids) {
		String[] pojids = ids.split("[^a-zA-Z0-9]+");
		List<String> ei = new ArrayList();
		for(int i = 0; i < pojids.length; i++) {
			List<String> l = this.query("select aar.reviewer.id from AwardApplicationReview aar where aar.award.id = '" + pojids[i] + "' order by aar.priority asc ");
			String re = "";
			for(int j = 0; j < l.size(); j++) {
				re += l.get(j) + ";";
			}
			if(re.length() > 0)
				re = re.substring(0, re.length() - 1);
			ei.add(re);
		}
		String exps = "";
		if(null != ei && ei.size() != 0){
			exps = ei.get(0);
			for(int i = 1; i < ei.size(); i++) {
				exps = unionString(exps, ei.get(i));
			}
		}
		//System.out.println("exps " + exps);
		return exps;
	}
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
	 * 连接字符串（取两个项目的学科代码的交集，用于找到相同学科类属的专家）
	 * @param str1	项目1的学科代码字符串
	 * @param str2	项目2的学科代码字符串
	 * @return	两个项目的学科代码的交集
	 */
	public String unionString(String str1, String str2) {
		String[] s1 = str1.split(";");
		String[] s2 = str2.split(";");
		String re = "";
		for(int i = 0; i < s1.length; i++) {
			for(int j = 0; j < s2.length; j++) {
				if(s1[i].equals(s2[j])) {
					re += s1[i] + ";";
					break;
				}
			}
		}
		if(re.length() > 0)
			re = re.substring(0, re.length() - 1);
		return re;
	}
	
	/**
	 * 查询参与匹配专家的查询语句，在（匹配算法中、专家树中、导出专家中）使用
	 * 用于moeSocial奖励
	 * (手动选择的页面)在匹配更新器的地方也有用到
	 * @return
	 */
	public String selectReviewMatchExpertForManual(){
		ActionContext context = ActionContext.getContext();
		Map session = context.getSession();
		Integer defaultYear = (Integer) session.get("defaultYear");
		Map application = ActionContext.getContext().getApplication();
		String awardReviewerImportedStartDate = (String) application.get("AwardReviewerImportedStartDate");
		String awardReviewerImportedEndDate = (String) application.get("AwardReviewerImportedEndDate");
		String awardReviewerBirthdayStartDate = (String) application.get("AwardReviewerBirthdayStartDate");
		String awardReviewerBirthdayEndDate = (String) application.get("AwardReviewerBirthdayEndDate");
		
		//筛选所属高校办学类型为11和12，属性为参评、非重点人、专职人员，手机和邮箱全非空，当前年没申请项目， 评价等级大于限制阈值，当前时间6个月内更新入库的专家
		StringBuffer hql4Reviewer = new StringBuffer(" and (((u.style like '11%' or u.style like '12%') and expert.expertType = 1 ");//所属高校办学类型为11和12的内部专家
		
			if (awardReviewerImportedStartDate != null && !awardReviewerImportedStartDate.equals("不限")) {
				hql4Reviewer.append(" and expert.importedDate > to_date('" + awardReviewerImportedStartDate + "','yyyy-mm-dd') ");	    //设置一般项目_评审专家_导入_开始时间         
			}
			if (awardReviewerImportedEndDate != null && !awardReviewerImportedEndDate.equals("不限")) {
				hql4Reviewer.append(" and expert.importedDate < to_date('" + awardReviewerImportedEndDate + "','yyyy-mm-dd') ");		    //设置一般项目_评审专家_导入_结束时间
			}
		
		hql4Reviewer.append(" ) or expert.expertType = 2)");																//或所有外部专家
		hql4Reviewer.append(" and expert.specialityTitle in (:seniorTitles)");	                                    //正高级职称专家	
		hql4Reviewer.append(" and expert.isReviewer = 1 and expert.isKey = 0 and expert.type = '专职人员'");				//参评，非重点人，专职人员
		hql4Reviewer.append(" and expert.email is not null and expert.mobilePhone is not null");						//手机和邮箱全非空
		hql4Reviewer.append(" and (expert.awardApplyYears is null or expert.awardApplyYears not like '%" + defaultYear + "%')");	//当前年没申请项目
		hql4Reviewer.append(" and expert.rating > " + 0 + " ");												//评价等级大于限制阈值
		hql4Reviewer.append(" and expert.discipline is not null ");	
//		if (awardReviewerImportedStartDate != null && !awardReviewerImportedStartDate.equals("不限")) {
//			hql4Reviewer.append(" and expert.importedDate > to_date('" + awardReviewerImportedStartDate + "','yyyy-mm-dd') ");	    //设置一般项目_评审专家_导入_开始时间         
//		}
//		if (awardReviewerImportedEndDate != null && !awardReviewerImportedEndDate.equals("不限")) {
//			hql4Reviewer.append(" and expert.importedDate < to_date('" + awardReviewerImportedEndDate + "','yyyy-mm-dd') ");		    //设置一般项目_评审专家_导入_结束时间
//		}
		if (awardReviewerBirthdayStartDate != null && !awardReviewerBirthdayStartDate.equals("不限")) {
			hql4Reviewer.append(" and expert.birthday > to_date('" + awardReviewerBirthdayStartDate + "','yyyy-mm-dd') ");		    //设置一般项目_评审专家_出生日期_开始时间
		}
		if (awardReviewerBirthdayEndDate != null && !awardReviewerBirthdayEndDate.equals("不限")) {
			hql4Reviewer.append(" and expert.birthday < to_date('" + awardReviewerBirthdayEndDate + "','yyyy-mm-dd') ");		        //设置一般项目_评审专家_出生日期_结束时间
		}
		return hql4Reviewer.toString();
	}

	/**
	 * 查询参与匹配专家的查询语句，在（匹配算法中、专家树中、导出专家中）使用
	 * 注意：
	 * 由于近年来外部专家没有更新，所以外部专家的更新时间不受限制
	 * @return
	 */
	public String selectReviewMatchExpert(){
		ActionContext context = ActionContext.getContext();
		Map session = context.getSession();
		Integer defaultYear = (Integer) session.get("defaultYear");
		Map application = ActionContext.getContext().getApplication();
		String awardReviewerImportedStartDate = (String) application.get("AwardReviewerImportedStartDate");
		String awardReviewerImportedEndDate = (String) application.get("AwardReviewerImportedEndDate");
		String awardReviewerBirthdayStartDate = (String) application.get("AwardReviewerBirthdayStartDate");
		String awardReviewerBirthdayEndDate = (String) application.get("AwardReviewerBirthdayEndDate");
		//筛选所属高校办学类型为11和12，属性为参评、非重点人、专职人员，手机和邮箱全非空，当前年没申请项目， 评价等级大于限制阈值，当前时间6个月内更新入库的专家
		StringBuffer hql4Reviewer = new StringBuffer(" and (((u.style like '11%' or u.style like '12%') and u.founderCode in ('308', '339', '360', '435') and expert.expertType = 1 ");
			if (awardReviewerImportedStartDate != null && !awardReviewerImportedStartDate.equals("不限")) {
				hql4Reviewer.append(" and expert.importedDate > to_date('" + awardReviewerImportedStartDate + "','yyyy-mm-dd') ");	    //设置一般项目_评审专家_导入_开始时间         
			}
			if (awardReviewerImportedEndDate != null && !awardReviewerImportedEndDate.equals("不限")) {
				hql4Reviewer.append(" and expert.importedDate < to_date('" + awardReviewerImportedEndDate + "','yyyy-mm-dd') ");		    //设置一般项目_评审专家_导入_结束时间
			};//所属高校办学类型为11和12且为部属高校的内部专家, 且 只对内部专家的导入时间做限制
		hql4Reviewer.append(" ) or expert.expertType = 2)");																//或所有外部专家
		hql4Reviewer.append(" and expert.specialityTitle in (:seniorTitles)");	                                    //正高级职称专家	
		hql4Reviewer.append(" and expert.isReviewer = 1 and expert.isKey = 0 and expert.type = '专职人员'");				//参评，非重点人，专职人员
		hql4Reviewer.append(" and expert.email is not null and expert.mobilePhone is not null");						//手机和邮箱全非空
		hql4Reviewer.append(" and (expert.awardApplyYears is null or expert.awardApplyYears not like '%" + defaultYear + "%')");	//当前年没申请项目
		hql4Reviewer.append(" and expert.rating > " + 0 + " ");												//评价等级大于限制阈值
		hql4Reviewer.append(" and expert.discipline is not null ");	//专家学科代码属性做非空限制
				
		if (awardReviewerBirthdayStartDate != null && !awardReviewerBirthdayStartDate.equals("不限")) {
			hql4Reviewer.append(" and expert.birthday > to_date('" + awardReviewerBirthdayStartDate + "','yyyy-mm-dd') ");		    //设置一般项目_评审专家_出生日期_开始时间
		}
		if (awardReviewerBirthdayEndDate != null && !awardReviewerBirthdayEndDate.equals("不限")) {
			hql4Reviewer.append(" and expert.birthday < to_date('" + awardReviewerBirthdayEndDate + "','yyyy-mm-dd') ");		        //设置一般项目_评审专家_出生日期_结束时间
		}
		
		return hql4Reviewer.toString();
	}
	
	/**
	 * 群删奖励
	 * 同时要删除奖励相关的其他表信息：
	 * @param awardids 待删奖励Id
	 */
	@Transactional
	public void deleteProjects(List<String> awardids) {
		//删除项目申请表相关记录, 关联删除匹配表、 删除匹配更新表相关记录
		if (awardids != null) {
			for (int i = 0; i < awardids.size(); i++) {
				this.delete(AwardApplication.class, awardids.get(i));
			}
		}
	}

	/**
	 * 奖励导出
	 * @param exportAll：1，导出全部奖励（参评奖励+退评奖励）；0，导出参评奖励
	 * @param containReviewer：是否包含专家评审信息
	 * @return
	 */
	public InputStream exportAward(int exportAll, boolean containReviewer) {
		ActionContext context = ActionContext.getContext();
		Map session = context.getSession();
		int year = Integer.parseInt(session.get("defaultYear").toString());	
		//高校代码 -> 高校名称
		HashMap<String, String> univMap = (HashMap<String, String>) context.getApplication().get("univCodeNameMap");
		//学科代码 -> 学科名称
		HashMap<String, String> discMap = (HashMap<String, String>) context.getApplication().get("disMap");
		HashMap<String, Expert> expMap = null;	//专家ID -> 专家实体
		HashMap<String, List<String>> irsMap = null; //项目ID -> 评审专家列表
		if (containReviewer) {
			//专家ID -> 专家实体
			expMap = new HashMap<String, Expert>();
			
			List<Expert> experts = this.query("from Expert");
			for (Expert expert : experts) {
				expMap.put(expert.getId(), expert);
			}
			//项目ID -> 评审专家列表
			try {
		
				List<AwardApplicationReview> arList = this.query("from AwardApplicationReview ar where ar.type = 'moeSocial' and ar.year = ? order by ar.award.id asc, ar.priority asc", year);
				irsMap = new HashMap<String, List<String>>();//项目ID与该奖励的评审专家的映射
				for (AwardApplicationReview ar : arList) {
					if(null != ar.getPriority() && ar.getPriority() > 7){//只取前5个专家，不考虑备评
						continue;
					}
					List<String> expertIds = irsMap.get(ar.getAward().getId());
					if (null == expertIds) {
						expertIds = new ArrayList<String>();
					}
					expertIds.add(ar.getReviewer().getId());
					irsMap.put(ar.getAward().getId(), expertIds);
				}
				
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
		/**
		 * 
		 */
		List<AwardApplication> awards = null;
		try {
			if (exportAll != 0){
				awards = this.query("select aa from AwardApplication aa where aa.type = 'moeSocial' and aa.year = ? order by aa.id asc", year);
			} else {
				awards = this.query(((String) session.get("HQL4AwardExport")).replaceAll("order by[\\s\\S]*", "order by aa.id asc"), (Map)session.get("Map4AwardExport"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		List<Object[]> dataList = new ArrayList<Object[]>();
		String header = null;
		String[] title = null;
		if(containReviewer) {
			header = "奖励专家匹配表";
			title = new String[]{
					"成果编号",
					"成果名称",
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
					"专家5代码",
					"专家6高校",
					"专家6高校代码",
					"专家6姓名",
					"专家6代码",
					"专家7高校",
					"专家7高校代码",
					"专家7姓名",
					"专家7代码"
			};
			for (AwardApplication award : awards) {
				List pList = new ArrayList<Object>();
				pList.add(award.getNumber());
				pList.add(award.getProductName());
				pList.add(award.getUnitCode());
				pList.add(univMap.get(award.getUnitCode()));
				pList.add(award.getApplicantName());
				List<String> expertIds = irsMap.get(award.getId());
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
			header = "奖励一览表";
			title = new String[] {
					"成果编号",
					"成果名称",
					"成果类型",
					"奖项种类",
					"成果语种",
					"高校代码",
					"学科类别",
					"学科代码",
					"创建时间",
					"申请类型",
					"成果字数",
					"著作类型",
					"出版、发表或使用单位",
					"出版、发表或使用时间",
					"出版年度-期刊号",
					"申请人",
					"第一作者",
					"性别",
					"出生日期",
					"行政职务",
					"职称",
					"是否第一作者",
					"证件类型",
					"证件号码",
					"工作单位",
					"办公电话",
					"手机",
					"邮箱",
					"是否合格",
					"是否需要跟踪",
					"备注"
					};
			for (AwardApplication award : awards) {
				List pList = new ArrayList<Object>();
				pList.add(award.getNumber());
				pList.add(award.getProductName());
				pList.add(award.getProductType());
				pList.add(award.getAwardCatalog());
				pList.add(award.getProductLanguage());
				pList.add(award.getUnitCode());
				pList.add(award.getDisciplinePrimitive());
				pList.add(award.getDisciplineCode());
				pList.add(award.getCreateDate());
				pList.add(award.getApplicationType());
				pList.add(award.getWordNumber());
				pList.add(award.getBookType());
				pList.add(award.getPublishUnit());
				pList.add(award.getPublishDate());
				pList.add(award.getJournalNumber());
				pList.add(award.getApplicantName());
				pList.add(award.getFirstAuthor());
				pList.add(award.getGender());
				pList.add(award.getBirthday());
				pList.add(award.getPosition());
				pList.add(award.getSpecialistTitle());
				pList.add((award.getIsFirstAuthor() == 1) ? "是" : "否");
				pList.add(award.getIdcardType());
				pList.add(award.getIdcardNumber());
				pList.add(award.getDivisionName());
				pList.add(award.getOfficePhone());
				pList.add(award.getMobilePhone());
				pList.add(award.getEmail());
				pList.add((award.getIsPassed() == 1) ? "是" : "否");
				pList.add((award.getIsTracked() == 1) ? "是" : "否");
				pList.add(award.getNote());

				Object obj[] = pList.toArray(new Object[0]);
				for (int i = 0; i < obj.length; i++){
					if (obj[i] == null){
						obj[i] = "";
					}
				}
				dataList.add(obj);
			}
		}
		/**
		 * 
		 */
		return HSSFExport.commonExportExcel(dataList, header, title);
	}
	
	
	/**
	 * 导出奖励专家调整更新表
	 * @param year	匹配年度
	 * @param start	更新开始时间
	 * @param end	更新结束时间
	 */
	@Transactional
	public InputStream exportMatchUpdate(Integer year, Date start, Date end) throws IOException {
		List<String []> v = new ArrayList();
		List<Object[]> prus = this.query(
				"select " +
				"	aru.award.id, " +
				"	aru.award.productName, " +
				"	aru.award.number, " +
				"	aru.reviewer.name, " +
				"	university.name, " +
				"	university.code, " +
				"	aru.isAdd, " +
				"	aru.reviewer.number " +
				"from " +
				"	AwardApplicationReviewUpdate aru, " +
				"	University university " +
				"where aru.type = 'moeSocial' and" +
				"	university.code = aru.reviewer.universityCode and " +
				"	aru.year = ? and " +
				"	aru.matchTime >= ? and " +
				"	aru.matchTime <= ? " +
				"order by aru.award.id, aru.matchTime", year, start, end);
		HashSet<String> originReviewers = null;
		HashSet<String> updatedReviewers = null;
		HashSet<Integer> updatedReviewerNumbers = new HashSet<Integer>();
		for (int i = 0; i < prus.size(); i++) {
			String prevAwardId = (String) (i > 0 ? prus.get(i - 1)[0] : null);
			String curAwardId = (String) prus.get(i)[0];
			String nextAwardId = (String) (i < prus.size() - 1 ? prus.get(i + 1)[0] : null);
			String awardName = (String) prus.get(i)[1];
			String awardNumber = (String) prus.get(i)[2];
			String reviewerName = (String) prus.get(i)[3];
			String reviewerUnivName = (String) prus.get(i)[4];
			String reviewerUnivCode = (String) prus.get(i)[5];
			Integer isAdd = (Integer) prus.get(i)[6];
			Integer reviewerNumber = (Integer) prus.get(i)[7];
			
			String curReviewer = reviewerNumber + "/" + reviewerName + "（" + reviewerUnivCode + "/" + reviewerUnivName + "）";
//			System.out.println(curProjectId + " " + projectName + " " + curReviewer + " - " + isAdd);
			
			if (prevAwardId == null || !curAwardId.equals(prevAwardId)) {
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
			if (nextAwardId == null || !curAwardId.equals(nextAwardId)) {
				String[] row = new String[4];
				row[0] = awardNumber;
				row[1] = awardName;
				
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
			"成果编号",
			"成果名称",
			"删除评审专家",
			"新增评审专家"
		};
		
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet1 = wb.createSheet();
		wb.setSheetName(0, "奖励专家匹配更新表");
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
			tmpUpdateReviewersInfoList = this.query("select expert.number, expert.name, expert.universityCode, expert.mobilePhone, expert.email, expert.discipline, expert.id from Expert expert");
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
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			wb.write(bos);
			bos.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		byte[] content = bos.toByteArray();
		ByteArrayInputStream bis = null;
		try{
			bis = new ByteArrayInputStream(content);
			bis.close();
		} catch (Exception e){
			e.printStackTrace();
		}
		return bis;		
	}
	
}
