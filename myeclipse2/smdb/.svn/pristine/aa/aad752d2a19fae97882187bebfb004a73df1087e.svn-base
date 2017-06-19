package csdc.service;

import java.util.Map;

/**
 * 数据挖掘：关联规则挖掘的接口
 * @author fengcl
 */
public interface IAssociationService extends IDataMiningService{

	/**
	 * 关联性分析结果处理<br>
	 * 1、数据准备
	 * 2、关联规则挖掘
	 * 3、数据分析，并组装D3图的json数据
	 * 4、图表结果入库
	 * @param hotstopType	分析类型
	 * @param analyzeAngle	分析角度（0：项目申报数据；1：项目立项数据）
	 * @param topK			前K条
	 * @param minSupport	最小支持度
	 * @param toDataBase	1：入库；0：不入库
	 * @return
	 */
	public Map handleAnalyze(String analysisType, int analyzeAngle, int topK, Long minSupport, int toDataBase, int startYear, int endYear);
	
	/**
	 * 跨单位合作项目：
	 * 高校关联性-项目批准经费的关联性分析
	 * @param topK			挖掘条数
	 * @param toDataBase	1：入库；0：不入库
	 * @return
	 */
	public Map universityAssociation(int topK, int toDataBase, int startYear, int endYear);
	
	/**
	 * 获取项目年度：用于关联规则页面的分析年度下拉框数据
	 * @return
	 */
	public Map<Object,String> getProjectYear();
}
