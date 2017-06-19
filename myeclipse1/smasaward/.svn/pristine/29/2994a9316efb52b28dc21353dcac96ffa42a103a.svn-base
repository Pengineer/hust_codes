package csdc.action.statistic.review;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;

import csdc.service.IBaseService;
import csdc.tool.ApplicationContainer;
import csdc.tool.StringTool;

/**
 * 匹配算法的高校使用情况统计
 * @author wangst
 *
 */
public class UniversityUseStatisticAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private static final int STATIC_YEARS = 5;//统计最近5年的，随系统配置defaultyear动态改变，但是前台要动态处理
	private StringBuffer HQL_University2ExpertNums;
	private StringBuffer HQL_GENERAL_University2MatchNums;
	private StringBuffer HQL_INSTP_University2MatchNums;
	private StringBuffer HQL_GENERAL_University2UseExpertNums;
	private StringBuffer HQL_INSTP_University2UseExpertNums;
	public void hqlConstructor(){
		//数据库中所有高校的具有参评资格的专家
		HQL_University2ExpertNums = new StringBuffer("select u.name,count(*) from Expert expert,University u where expert.universityCode = u.code");
		HQL_University2ExpertNums.append(" and (((u.style like '11%' or u.style like '12%') and expert.expertType = 1)");			//所属高校办学类型为11和12的内部专家
		HQL_University2ExpertNums.append(" or expert.expertType = 2)");																//或所有外部专家
		HQL_University2ExpertNums.append(" and u.useExpert = 1");			                                                        //所属高校使用该校评审专家
		HQL_University2ExpertNums.append(" and (u.useViceExpert = 1 or expert.specialityTitle in (:seniorTitles))");	//所属高校使用该校副高级职称评审专家或者去除副高级职称评审专家			
		HQL_University2ExpertNums.append(" and expert.isReviewer = 1 and expert.isKey = 0 and expert.type = '专职人员'");				//参评，非重点人，专职人员
		HQL_University2ExpertNums.append(" and expert.email is not null and expert.mobilePhone is not null");						//手机和邮箱全非空
		HQL_University2ExpertNums.append(" and expert.rating > 0 ");												//评价等级大于限制阈值（数字越大，评价等级越高）	
		HQL_University2ExpertNums.append(" group by u.name");
		
		//ProjectApplication p.type = 'general' and
		//ProjectApplicationReview pr.type = 'general' and
		//
		//一般项目_高校名称与该高校每年匹配对的对应
		HQL_GENERAL_University2MatchNums = new StringBuffer("select u.name,count(*),gr.year from Expert expert,University u,ProjectApplicationReview gr where gr.type = 'general' and expert.universityCode = u.code and gr.reviewer.id = expert.id ");
		HQL_GENERAL_University2MatchNums.append(" and (((u.style like '11%' or u.style like '12%') and expert.expertType = 1)");			//所属高校办学类型为11和12的内部专家
		HQL_GENERAL_University2MatchNums.append(" or expert.expertType = 2)");																//或所有外部专家
		HQL_GENERAL_University2MatchNums.append(" and u.useExpert = 1");			                                                        //所属高校使用该校评审专家
		HQL_GENERAL_University2MatchNums.append(" and (u.useViceExpert = 1 or expert.specialityTitle in (:seniorTitles))");	//所属高校使用该校副高级职称评审专家或者去除副高级职称评审专家			
		HQL_GENERAL_University2MatchNums.append(" and expert.isReviewer = 1 and expert.isKey = 0 and expert.type = '专职人员'");				//参评，非重点人，专职人员
		HQL_GENERAL_University2MatchNums.append(" and expert.email is not null and expert.mobilePhone is not null");						//手机和邮箱全非空
		HQL_GENERAL_University2MatchNums.append(" and expert.rating > 0 ");												//评价等级大于限制阈值（数字越大，评价等级越高）	
		HQL_GENERAL_University2MatchNums.append(" group by u.name,gr.year");
		
		//基地项目_高校名称与该高校每年匹配对的对应
		HQL_INSTP_University2MatchNums = new StringBuffer("select u.name,count(*),ir.year from Expert expert,University u,ProjectApplicationReview ir where ir.type = 'instp' and expert.universityCode = u.code and ir.reviewer.id = expert.id ");
		HQL_INSTP_University2MatchNums.append(" and (((u.style like '11%' or u.style like '12%') and expert.expertType = 1)");			//所属高校办学类型为11和12的内部专家
		HQL_INSTP_University2MatchNums.append(" or expert.expertType = 2)");																//或所有外部专家
		HQL_INSTP_University2MatchNums.append(" and u.useExpert = 1");			                                                        //所属高校使用该校评审专家
		HQL_INSTP_University2MatchNums.append(" and (u.useViceExpert = 1 or expert.specialityTitle in (:seniorTitles))");	//所属高校使用该校副高级职称评审专家或者去除副高级职称评审专家			
		HQL_INSTP_University2MatchNums.append(" and expert.isReviewer = 1 and expert.isKey = 0 and expert.type = '专职人员'");				//参评，非重点人，专职人员
		HQL_INSTP_University2MatchNums.append(" and expert.email is not null and expert.mobilePhone is not null");						//手机和邮箱全非空
		HQL_INSTP_University2MatchNums.append(" and expert.rating > 0 ");												//评价等级大于限制阈值（数字越大，评价等级越高）	
		HQL_INSTP_University2MatchNums.append(" group by u.name,ir.year");
		
		//一般项目_高校名称与该高校每年参评专家数的对应
		HQL_GENERAL_University2UseExpertNums = new StringBuffer("select u.name,count(distinct expert.id),gr.year from Expert expert,University u,ProjectApplicationReview gr where gr.type = 'general' and expert.universityCode = u.code and gr.reviewer.id = expert.id");
		HQL_GENERAL_University2UseExpertNums.append(" and (((u.style like '11%' or u.style like '12%') and expert.expertType = 1)");			//所属高校办学类型为11和12的内部专家
		HQL_GENERAL_University2UseExpertNums.append(" or expert.expertType = 2)");																//或所有外部专家
		HQL_GENERAL_University2UseExpertNums.append(" and u.useExpert = 1");			                                                        //所属高校使用该校评审专家
		HQL_GENERAL_University2UseExpertNums.append(" and (u.useViceExpert = 1 or expert.specialityTitle in (:seniorTitles))");	//所属高校使用该校副高级职称评审专家或者去除副高级职称评审专家			
		HQL_GENERAL_University2UseExpertNums.append(" and expert.isReviewer = 1 and expert.isKey = 0 and expert.type = '专职人员'");				//参评，非重点人，专职人员
		HQL_GENERAL_University2UseExpertNums.append(" and expert.email is not null and expert.mobilePhone is not null");						//手机和邮箱全非空
		HQL_GENERAL_University2UseExpertNums.append(" and expert.rating > 0 ");												//评价等级大于限制阈值（数字越大，评价等级越高）	
		HQL_GENERAL_University2UseExpertNums.append("  group by u.name,gr.year");
		
		//基地项目_高校名称与该高校每年参评专家数的对应
		HQL_INSTP_University2UseExpertNums = new StringBuffer("select u.name,count(distinct expert.id),ir.year from Expert expert,University u,ProjectApplicationReview ir where ir.type = 'instp' and expert.universityCode = u.code and ir.reviewer.id = expert.id");
		HQL_INSTP_University2UseExpertNums.append(" and (((u.style like '11%' or u.style like '12%') and expert.expertType = 1)");			//所属高校办学类型为11和12的内部专家
		HQL_INSTP_University2UseExpertNums.append(" or expert.expertType = 2)");																//或所有外部专家
		HQL_INSTP_University2UseExpertNums.append(" and u.useExpert = 1");			                                                        //所属高校使用该校评审专家
		HQL_INSTP_University2UseExpertNums.append(" and (u.useViceExpert = 1 or expert.specialityTitle in (:seniorTitles))");	//所属高校使用该校副高级职称评审专家或者去除副高级职称评审专家			
		HQL_INSTP_University2UseExpertNums.append(" and expert.isReviewer = 1 and expert.isKey = 0 and expert.type = '专职人员'");				//参评，非重点人，专职人员
		HQL_INSTP_University2UseExpertNums.append(" and expert.email is not null and expert.mobilePhone is not null");						//手机和邮箱全非空
		HQL_INSTP_University2UseExpertNums.append(" and expert.rating > 0 ");												//评价等级大于限制阈值（数字越大，评价等级越高）	
		HQL_INSTP_University2UseExpertNums.append(" group by u.name,ir.year");
		
	}
	@Autowired
	private IBaseService baseService;
	private Integer listType;//1表示一般项目，2表示基地项目
	/**
	 * 高校使用情况页
	 * @return
	 */
	public String view(){
		//处理年份 , 与系统表配置中的默认年份保持一致，而不予session.defaultYear保持一致
		//一般系统当前年即为所有年中的最后一个
		ServletContext sc = ApplicationContainer.sc;
		Object o =  sc.getAttribute("allYears");
		String[] legalYears = (String[]) o; 
		List<String> legalYearList = new ArrayList();
		//系统已有 2010 - 2014 五年数据，以后可以动态变化
		int yearCnt = legalYears.length - STATIC_YEARS;
		for (int i = 0; i < STATIC_YEARS; i++) {
			legalYearList.add(StringTool.fix(legalYears[yearCnt + i]));
		}
		String statiCurrentYear = StringTool.fix(legalYears[legalYears.length - 1]);//当前年信息前台
		
		Map paraMap = new HashMap();
		/**
		 * 1.取出系统选项表中的c_name，c_code，c_description字段，组装‘c_code/c_name’与专家表中的c_speciality_title做对比
		 */
		Date begin = new Date();
		List<Object[]> systemOptionList = baseService.query("select so.code,so.name,so.description from SystemOption so where so.standard = 'GBT8561-2001' and so.description is not null");
		System.out.println("查询[系统选项表]用时：" + (new Date().getTime() - begin.getTime()) + "ms");
		System.out.println("查询[系统选项表]数目：" + systemOptionList.size() + "\n");
		HashMap<String, String> codeName2Description = new HashMap<String, String>();
		try {
			for (Object[] objects : systemOptionList) {
				String code = objects[0].toString();
				String name = objects[1].toString();
				String description = objects[2].toString();
				codeName2Description.put(code + "/" + name,description);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<Object> expertSeciality = baseService.query("select distinct expert.specialityTitle from Expert expert where expert.specialityTitle is not null");
		List<String> seniorTitles = new ArrayList<String>(); 
		for (Object object : expertSeciality) {
			String specialityTitle = object.toString();
			if (!codeName2Description.containsKey(specialityTitle)) {
				continue;
			}
			if (codeName2Description.get(specialityTitle).equals("正高级") || codeName2Description.get(specialityTitle).equals("高级")) {
				seniorTitles.add(specialityTitle);
			}
		}
		paraMap.put("seniorTitles", seniorTitles);	//正高级职称
		
		hqlConstructor();
		HttpServletRequest request = ServletActionContext.getRequest();
		Integer totalIsReviewer = 0;//数据库中所有高校的具有参评资格的专家
	
		List<Object[]> university2ExpertNums = new ArrayList<Object[]>();
		try {
			university2ExpertNums = baseService.query(HQL_University2ExpertNums.toString(), paraMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//高校 --> 高校具有参评资格的专家数
		Map<String, Integer> university2ExpertNumMap = new HashMap<String, Integer>();
		for(Object[] university2ExpertNum : university2ExpertNums){//高校名---高校参评专家数
			String universityName = university2ExpertNum[0].toString();
			Integer cnt = Integer.parseInt(String.valueOf(university2ExpertNum[1]));
			university2ExpertNumMap.put(universityName, cnt);
			totalIsReviewer += cnt;
		}
		
		String hql1 = null;
		String hql2 = null;
		if(listType==1){
			hql1 = HQL_GENERAL_University2MatchNums.toString();
			hql2 = HQL_GENERAL_University2UseExpertNums.toString();
		}else {
			hql1 = HQL_INSTP_University2MatchNums.toString();
			hql2 = HQL_INSTP_University2UseExpertNums.toString();
		}
		
		List<Object[]> university2MatchInfos = baseService.query(hql1, paraMap);
		List<Object[]> uinversity2UseExpertInfos = baseService.query(hql2, paraMap);
		//高校-->[year --> 高校每年匹配对数]
		Map<String, Map<String, Integer>> university2MatchMap = new HashMap<String, Map<String,Integer>>();
		//高校-->[year --> 高校每年评审专家数]
		Map<String, Map<String, Integer>> university2UseExpertMap = new HashMap<String, Map<String,Integer>>();
		//年--> 每年所有高校的评审対总数和
		Map<String, Integer> totalUniversity2MatchMap = new HashMap<String, Integer>();
		//年--> 每年所有高校的评审专家总数和
		Map<String, Integer> totalUniversity2UseExpertMap = new HashMap<String, Integer>();		

		for(Object[] university2MatchInfo : university2MatchInfos){//高校名---高校的专家和项目匹配对数
			String universityName = university2MatchInfo[0].toString();
			Integer cnt = Integer.parseInt(String.valueOf(university2MatchInfo[1]));
			String year = university2MatchInfo[2].toString();
			Map<String, Integer> year2MatchMap = university2MatchMap.get(universityName);
			if (year2MatchMap == null) {
				year2MatchMap = new HashMap<String, Integer>();
			}
			year2MatchMap.put(year, cnt);
			university2MatchMap.put(universityName, year2MatchMap);
			Integer cnt1 = totalUniversity2MatchMap.get(year);
			if (cnt1 == null) {
				cnt1 = 0;
			}
			totalUniversity2MatchMap.put(year, cnt1 + cnt);
		}
		
		Set<String> universitySet = university2MatchMap.keySet();//高校名set
		
		for (Object[] uinversity2UseExpertInfo : uinversity2UseExpertInfos) {//高校名---高校的参与评审的专家数
			String universityName = uinversity2UseExpertInfo[0].toString();
			Integer cnt = Integer.parseInt(String.valueOf(uinversity2UseExpertInfo[1]));
			String year = uinversity2UseExpertInfo[2].toString();
			Map<String, Integer> year2ReviewExpertMap = university2UseExpertMap.get(universityName);
			if (year2ReviewExpertMap == null) {
				year2ReviewExpertMap = new HashMap<String, Integer>();
			}
			year2ReviewExpertMap.put(year, cnt);
			university2UseExpertMap.put(universityName, year2ReviewExpertMap);
			Integer cnt1 = totalUniversity2UseExpertMap.get(year);
			if (cnt1 == null) {
				cnt1 = 0;
			}
			totalUniversity2UseExpertMap.put(year, cnt1 + cnt);
		}
		//统计高校列表，每年的专家匹配对/本校具有参评资格的专家数目 ，每年的评审专家数目/本校具有参评资格的专家数目 
		// 每一项是当前年各个学校list --> 子list中每一项：【学校名字，各个年份的统计信息】
		List<List<String>> universityUseStatisticList = new ArrayList<List<String>>();//高校名、每年的数据组成的list
		try {
			for (String universityName : universitySet ) {
				Integer university2ExpertNum = university2ExpertNumMap.get(universityName);
				List<Float> statisticInfos = new ArrayList<Float>();
				//统计高校，每年匹配对数量/高校参评专家人数默认5年
				for (int i = 0; i < legalYearList.size(); i++) {
					//每年匹配対数目
					Map<String, Integer> year2MatchMap = university2MatchMap.get(universityName);
					if (year2MatchMap == null) {
						statisticInfos.add(0.0f);
//						statisticInfos.add(0.0f);
//						statisticInfos.add(0.0f);
//						statisticInfos.add(0.0f);
					}else {
						//添加当前年此高校“匹配对数目/专家参评资格总数”
						Integer matchNum = year2MatchMap.get(legalYearList.get(i));
						if (matchNum == null) {
							statisticInfos.add(0.0f);
						}else {
							if (university2ExpertNum == null) {
								statisticInfos.add(0.0f);
							}else{
								float result = (float)(matchNum * 1.0 / university2ExpertNum);
								statisticInfos.add(result);
							}
						}
					}
				}
				//统计高校， 每年评审专家数量/高效具有参评专家数量
				for (int i = 0; i < legalYearList.size(); i++) {
					Map<String, Integer> year2ReviewExpert = university2UseExpertMap.get(universityName);
					if (year2ReviewExpert == null) {
						statisticInfos.add(0.0f);
						statisticInfos.add(0.0f);
						statisticInfos.add(0.0f);
						statisticInfos.add(0.0f);
					}else {
						Integer reviewExpert = year2ReviewExpert.get(legalYearList.get(i));
						if (reviewExpert == null) {
							statisticInfos.add(0.0f);
						}else {
							if (university2ExpertNum == null) {
//								statisticInfos.add(-1.0f);
								statisticInfos.add(0.0f);
							}else{
								float result = (float)(reviewExpert * 1.0 / university2ExpertNum);
								if (result == 1) {
									statisticInfos.add(0.0f);
								}else {
									statisticInfos.add(result);
								}
							}
						}
					}
				}
				//高校名字；（5）每年的匹配对比值；（5）每年的评审专家比值；=共11项一组ITEM
				List<String> universityUseInfoList = new ArrayList<String>();
				universityUseInfoList.add(universityName);
				for(Float data : statisticInfos){
					universityUseInfoList.add(Float.toString(data));
				}
				//所有高校的ITEM
				universityUseStatisticList.add(universityUseInfoList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Comparator comparator = new Comparator() {  
			public int compare(Object arg0, Object arg1) {
				List<String> aList = (List<String>)arg0;
				List<String> bList = (List<String>)arg1;
				float a = Float.parseFloat(aList.get(4));
				float b = Float.parseFloat(bList.get(4));
				if (a != b) {
					return a> b ? 1: -1;
				}else {
					return 0;
				}
			}
		};
		Collections.sort(universityUseStatisticList, comparator);
		
		//计算平均值
		//统计，每年的各个平均值。
		List<String> universityUseInfoList = new ArrayList<String>();
		universityUseInfoList.add("平均值");
		//计算“每年总高校匹配对数和”/“数据库中的所有具有参评资格的专家”的平均值
		//平均没人评审的因子
		for (int i = 0; i < legalYearList.size(); i++) {
			Integer totalNum = totalUniversity2MatchMap.get(legalYearList.get(i));
			if (totalNum == null) {
				universityUseInfoList.add(Float.toString(0.0f));
			}else{
				float totalDouble = (float)( totalNum * 1.0 / totalIsReviewer);
				universityUseInfoList.add(Float.toString(totalDouble));
			}
		}	
		//计算“每年总高校评审专家数和”/“数据库中的所有具有参评资格的专家”的平均值
		//每年的评审专家的评审比例。
		for (int i = 0; i < legalYearList.size(); i++) {
			Integer totalNum = totalUniversity2UseExpertMap.get(legalYearList.get(i));
			if (totalNum == null) {
				universityUseInfoList.add(Float.toString(0.0f));				
			}else{
				float totalDouble = (float)(totalNum * 1.0 / totalIsReviewer);
				universityUseInfoList.add(Float.toString(totalDouble));	
			}
		}
		//计算方差
		request.setAttribute("chartStaticYears", STATIC_YEARS);//当前年年份
		request.setAttribute("statiCurrentYear", Integer.parseInt(statiCurrentYear));//当前年年份
		request.setAttribute("universityUseStatisticList", universityUseStatisticList);
		request.setAttribute("universityUseStatisticAve", universityUseInfoList);//均值
		request.setAttribute("universityUseStatisticStd", calculateRowsStd(universityUseInfoList,universityUseStatisticList));//标准差
		return SUCCESS;
	}
	
	public IBaseService getBaseService() {
		return baseService;
	}
	public void setBaseService(IBaseService baseService) {
		this.baseService = baseService;
	}

	public Integer getListType() {
		return listType;
	}

	public void setListType(Integer listType) {
		this.listType = listType;
	}
	
	/**
	 *多列数据标准差 
	 */
	private List<String> calculateRowsStd(List<String> scoreMean,List<List<String>>originalSources){
		List<Float> column1 = new ArrayList<Float>();
		List<Float> column2 = new ArrayList<Float>();
		List<Float> column3 = new ArrayList<Float>();
		List<Float> column4 = new ArrayList<Float>();
		List<Float> column5 = new ArrayList<Float>();
		List<Float> column6 = new ArrayList<Float>();
		List<Float> column7 = new ArrayList<Float>();
		List<Float> column8 = new ArrayList<Float>();
		List<Float> column9 = new ArrayList<Float>();
		List<Float> column10 = new ArrayList<Float>();
		for(List<String> originalSource : originalSources){
			column1.add(Float.parseFloat(originalSource.get(1)));
			column2.add(Float.parseFloat(originalSource.get(2)));
			column3.add(Float.parseFloat(originalSource.get(3)));
			column4.add(Float.parseFloat(originalSource.get(4)));
			column5.add(Float.parseFloat(originalSource.get(5)));
			column6.add(Float.parseFloat(originalSource.get(6)));
			column7.add(Float.parseFloat(originalSource.get(7)));
			column8.add(Float.parseFloat(originalSource.get(8)));
			column9.add(Float.parseFloat(originalSource.get(9)));
			column10.add(Float.parseFloat(originalSource.get(10)));
		}
		List<String> result = new ArrayList<String>();
		result.add("标准差");
		float resultFloat = calculateSTD(column1,Float.parseFloat(scoreMean.get(1)));
		result.add(Float.toString(resultFloat));
		resultFloat = calculateSTD(column2,Float.parseFloat(scoreMean.get(2)));
		result.add(Float.toString(resultFloat));
		resultFloat = calculateSTD(column3,Float.parseFloat(scoreMean.get(3)));
		result.add(Float.toString(resultFloat));
		resultFloat = calculateSTD(column4,Float.parseFloat(scoreMean.get(4)));
		result.add(Float.toString(resultFloat));
		resultFloat = calculateSTD(column5,Float.parseFloat(scoreMean.get(5)));
		result.add(Float.toString(resultFloat));
		resultFloat = calculateSTD(column6,Float.parseFloat(scoreMean.get(6)));
		result.add(Float.toString(resultFloat));
		resultFloat = calculateSTD(column7,Float.parseFloat(scoreMean.get(7)));
		result.add(Float.toString(resultFloat));
		resultFloat = calculateSTD(column8,Float.parseFloat(scoreMean.get(8)));
		result.add(Float.toString(resultFloat));
		resultFloat = calculateSTD(column9,Float.parseFloat(scoreMean.get(9)));
		result.add(Float.toString(resultFloat));
		resultFloat = calculateSTD(column10,Float.parseFloat(scoreMean.get(10)));
		result.add(Float.toString(resultFloat));
		return result;
	}
//	
//	/**
//	 * 计算平均值
//	 * @param rawScores
//	 * @return
//	 */
//    private float calculateScoreMean(List<Float> rawScores) {  
//        float scoreAll = 0.0f;  
//        for (float score : rawScores) {  
//            scoreAll += score;  
//        }  
//        return (float)(scoreAll/rawScores.size());  
//    }  
//  
    /** 
     * 计算标准差 
     * @param rawScores 
     * @param scoreMean 
     * @return 
     */  
    private float calculateSTD(List<Float> rawScores, float scoreMean) {  
        float allSquare = 0.0f;  
        for (float rawScore : rawScores) {  
            allSquare += (rawScore - scoreMean)*(rawScore - scoreMean);  
        }  
        // (xi - x(平均分)的平方 的和计算完毕  
        double denominator = rawScores.size() * (rawScores.size() - 1);  
        return (float)(Math.sqrt(allSquare/denominator));  
    }  
}
