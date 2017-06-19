package csdc.action.statistic;

import java.util.HashMap;
import java.util.Map;

/**
 * 钻取列表显示的列
 */
public class DrillProperty {
	//项目
	private static final String APPLYCOUNT = "申报数";
	private static final String GRANTEDCOUNT = "立项数";
	private static final String PASSMIDCOUNT = "中检通过数";
	private static final String PASSENDCOUNT = "结项数";
	
	//人员
	private static final String PERSONCOUNT = "人员数目";
	
	//机构
	private static final String MINISTRYUNIT = "部属高校";
	private static final String LOCALUNIT = "地方高校";
	private static final String UNITCOUNT = "高校总数";
	
	//成果
	private static final String PRODUCTCOUNT = "成果总数";
	private static final String PAPERCOUNT = "论文成果总数";
	private static final String BOOKCOUNT = "著作成果总数";
	private static final String CONSULTATIONCOUNT = "研究咨询报告成果总数";
	private static final String ELECTROLICCOUNT = "电子出版物成果总数";
	private static final String PATENTCOUNT = "专利成果总数";
	private static final String OTHERCOUNT = "成果总数";
	
	//奖励
	private static final String SPECIALAWARD = "特等奖";
	private static final String FIRSTAWARD = "一等奖";
	private static final String SECONDAWARD = "二等奖";
	private static final String THIRDAWARD = "三等奖";
	private static final String COMMONAWARD = "普及奖";
	private static final String AWARDCOUNT = "获奖总数";
	private static final String AWARDSCORE = "总分";
	
	private static final String[] APPCOUNTCOLUMNS = {
		"项目名称", "申请人", "高校名称", "项目子类", "学科门类", "项目年度"
	};
	private static final String[] GRACOUNTCOLUMNS = {
		"项目名称", "负责人", "高校名称", "项目子类", "学科门类", "是否立项"
	};
	private static final String[] MIDCOUNTCOLUMNS = {
		"项目名称", "负责人", "高校名称", "项目子类", "学科门类", "中检年度"
	};
	private static final String[] ENDCOUNTCOLUMNS = {
		"项目名称", "负责人", "高校名称", "项目子类", "学科门类", "结项年度"
	};
	
	private static final String[] PERSONCOUNTCOLUMNS = {
		"姓名", "性别", "职称", "学历"
	};
	
	private static final String[] UNITCOLUMNS = {
		"高校名称", "性质类别", "结构类别", "省份名称"
	};
	 
	private static final String[] PRODUCTCOLUMNS = {
		"成果名称", "成果类型", "学科门类", "高校名称", "省份名称"
	};
	
	private static final String[] AWARDCOLUMNS = {
		"成果名称", "成果类型", "学科门类", "获奖等级", "届次"
	};
	
	static final Map<String, String[]> drillMap = new HashMap<String, String[]>();
	static{
		//项目
		drillMap.put(APPLYCOUNT, APPCOUNTCOLUMNS);
		drillMap.put(GRANTEDCOUNT, GRACOUNTCOLUMNS);
		drillMap.put(PASSMIDCOUNT, MIDCOUNTCOLUMNS);
		drillMap.put(PASSENDCOUNT, ENDCOUNTCOLUMNS);
		
		//人员
		drillMap.put(PERSONCOUNT, PERSONCOUNTCOLUMNS);
		
		//机构
		drillMap.put(MINISTRYUNIT, UNITCOLUMNS);
		drillMap.put(LOCALUNIT, UNITCOLUMNS);
		drillMap.put(UNITCOUNT, UNITCOLUMNS);
		
		//成果
		drillMap.put(PRODUCTCOUNT, PRODUCTCOLUMNS);
		drillMap.put(PAPERCOUNT, PRODUCTCOLUMNS);
		drillMap.put(BOOKCOUNT, PRODUCTCOLUMNS);
		drillMap.put(CONSULTATIONCOUNT, PRODUCTCOLUMNS);
		drillMap.put(ELECTROLICCOUNT, PRODUCTCOLUMNS);
		drillMap.put(PATENTCOUNT, PRODUCTCOLUMNS);
		drillMap.put(OTHERCOUNT, PRODUCTCOLUMNS);
		
		//奖励
		drillMap.put(SPECIALAWARD, AWARDCOLUMNS);
		drillMap.put(FIRSTAWARD, AWARDCOLUMNS);
		drillMap.put(SECONDAWARD, AWARDCOLUMNS);
		drillMap.put(THIRDAWARD, AWARDCOLUMNS);
		drillMap.put(COMMONAWARD, AWARDCOLUMNS);
		drillMap.put(AWARDCOUNT, AWARDCOLUMNS);
		drillMap.put(AWARDSCORE, AWARDCOLUMNS);
	}
}