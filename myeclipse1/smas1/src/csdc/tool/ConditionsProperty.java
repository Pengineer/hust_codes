package csdc.tool;

import java.util.HashMap;
import java.util.Map;

public class ConditionsProperty {

	private static final String[][] YEAR_FILTER_CODE_URL = {
		{"project/general/delete", "删除一般项目"},
		{"project/general/enableReview", "一般项目参与评审"},
		{"project/general/disableReview", "一般项目退出评审"},
		{"project/general/granting", "一般项目设置立项状态"},
		{"project/general/toSelectExpert", "一般项目进入选择专家"},
		{"finance/toCheck", "进入立项核算"},
		{"finance/checkByRate", "立项核算"},
		{"project/general/firstAudit/updateFirstAudit", "一般项目更新查重情况"},	
		// 专家匹配相关
		{"project/general/matchExpert", "一般项目自动匹配"},
		{"project/general/reMatchExpert", "一般项目手动匹配"},
		{"project/general/deleteMatches", "删除一般项目已有匹配对所有信息"},
		{"project/general/batchReplaceExpert", "一般项目批量替换专家"},
		{"project/general/transferMatch", "一般项目指定由其他专家无条件接管"},
		{"project/general/deleteExpertProjects", "一般项目删除专家项目"},

		//专家相关
		{"expert/disableReview", "设为退评"},
		{"expert/enableReview", "设为参评"},
		{"expert/delete", "删除专家"},
		//基地项目
		{"project/instp/delete", "删除基地项目"},
		{"project/instp/enableReview", "基地项目参与评审"},
		{"project/instp/disableReview", "基地项目退出评审"},
		{"project/instp/toSelectExpert", "基地项目进入选择专家"},
		{"project/instp/firstAudit/updateFirstAudit", "基地项目更新查重情况"},		
		// 专家匹配相关
		{"project/instp/matchExpert", "基地项目自动匹配"},
		{"project/instp/reMatchExpert", "基地项目手动匹配"},
		{"project/instp/deleteMatches", "删除基地项目已有匹配对所有信息"},
		{"project/instp/batchReplaceExpert", "基地项目批量替换专家"},
		{"project/instp/transferMatch", "基地项目指定由其他专家无条件接管"},
		{"project/instp/deleteExpertProjects", "基地项目删除专家项目"},
		//专项任务
		{"project/general/special/delete", "删除专项任务项目"},
	};
	
	public static final Map<String, String> YEAR_FILTER_URL_MAP;
	static {
		YEAR_FILTER_URL_MAP = new HashMap<String, String>();
		for(String[] tmp : YEAR_FILTER_CODE_URL) {
			YEAR_FILTER_URL_MAP.put(tmp[0],tmp[1]);
		}
	}
}
