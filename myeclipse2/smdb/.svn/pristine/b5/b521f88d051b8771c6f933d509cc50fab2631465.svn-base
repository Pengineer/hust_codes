package csdc.service;

import java.util.List;
import java.util.Map;

/**
 * 数据挖掘：领域热点分析的接口
 * @author fengcl
 */
public interface IHotspotService extends IDataMiningService{

	/**
	 * 创建或更新索引
	 * @param hotstopType	研究热点类型
	 * @param analyzeAngle	分析角度（0：项目申报数据；1：项目立项数据）
	 */
	public boolean updataIndex(String hotstopType, int analyzeAngle);
	
	/**
	 * 判断索引文件是否存在
	 * @param hotstopType	研究热点类型
	 * @param analyzeAngle	分析角度（0：项目申报数据；1：项目立项数据）
	 * @return
	 */
	public boolean isExistIndexFile(String hotstopType, int analyzeAngle);
	
	/**
	 * 研究热点处理<br>
	 * 1、热点分析
	 * 2、结果分析，组装D3图的json数据
	 * 3、图表结果入库
	 * @param hotstopType	研究热点类型
	 * @param analyzeAngle	分析角度（0：项目申报数据；1：项目立项数据）
	 * @param topK			前K条
	 * @param toDataBase	1：入库；0：不入库
	 * @return
	 */
	public Map handleHotspot(String hotstopType, int analyzeAngle, int topK, int toDataBase);
	
	/**
	 * 根据keyword，检索并获取的列表数据
	 * @param hotstopType	研究热点类型
	 * @param analyzeAngle	分析角度（0：项目申报数据；1：项目立项数据）
	 * @param keyword		搜索关键词
	 * @return
	 */
	public List<Object[]> getListData(String hotstopType, int analyzeAngle, String keyword);
}
