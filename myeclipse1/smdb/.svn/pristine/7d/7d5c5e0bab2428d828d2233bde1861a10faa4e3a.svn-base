package csdc.service;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 数据挖掘：分类预测挖掘的接口
 * @author fengcl
 */
public interface IClassificationService extends IDataMiningService{

	/**
	 * 通过预测类型获取项目类型
	 * @param predictType	例如：general_end -> 一般项目
	 * @return
	 */
	public String getProjectTypeByPredictType(String predictType);
	
	/**
	 * 通过预测类型获取类型
	 * @param predictType	例如：general_end -> 结项，general_mid -> 中检
	 * @return
	 */
	public String getTypeByPredictType(String predictType);
	
	/**
	 * 准备项目中检数据<br>
	 * 1、数据获取（数据选择）<br>
	 * 2、数据处理（清洗、转换）<br>
	 * 3、数据构建（训练集、测试集、预测集）<br>
	 * @param predictType	预测类型
	 * @param predictYear	预测年度
	 * @param useType	使用类型，1：训练集 ；2： 测试集；3：预测集
	 * @return
	 */
	public boolean prepareProjectMidData(String predictType, int predictYear, int useType);
	
	/**
	 * 准备项目结项数据<br>
	 * 1、数据获取（数据选择）<br>
	 * 2、数据处理（清洗、转换）<br>
	 * 3、数据构建（训练集、测试集、预测集）<br>
	 * @param predictType	预测类型
	 * @param predictYear	预测年度
	 * @param useType	使用类型，1：训练集 ；2： 测试集；3：预测集
	 * @return
	 */
	public boolean prepareProjectEndData(String predictType, int endYear, int useType);
	
	/**
	 * 准备项目数据<br>
	 * 1、数据获取（数据选择）<br>
	 * 2、数据处理（清洗、转换）<br>
	 * 3、数据构建（训练集、测试集、预测集）<br>
	 * @param predictType	预测类型
	 * @param predictYear	预测年度
	 * @param useType	使用类型，1：训练集 ；2： 测试集；3：预测集
	 * @return
	 */
	public boolean prepareProjectData(String predictType, int predictYear, int useType);
	
	/**
	 * 获取每年的项目结项、中检的真实数据
	 * @param projectType	预测类型
	 * @param predictYear	预测年度
	 * @return [year -> cnt] : [2013 -> 1234]
	 */
	public TreeMap<Integer, Integer> fetchProjectCntPerYear(String projectType, int predictYear);
	
	/**
	 * 项目预测模型训练相关处理<br>
	 * @param predictType	预测类型
	 * @param predictYear	预测年度
	 * @param predictorVariables	预测因子
	 * @return
	 */
	public Map handleTrain(String predictType, int predictYear, String predictorVariables);
	
	/**
	 * 项目预测的测试阶段<br>
	 * 1、数据准备
	 * 2、结果预测
	 * 3、对预测的结果进行评估
	 * 4、图表结果入库
	 * @param predictType	预测类型
	 * @param predictYear	预测年度
	 * @param toDataBase	1：入库；0：不入库
	 * @return
	 */
	public Map handleTest(String predictType, int predictYear, int toDataBase);
	
	/**
	 * 项目预测相关处理<br>
	 * 1、数据准备
	 * 2、结果预测
	 * 3、将结果组装Highcharts图的json数据
	 * 4、图表结果入库
	 * @param predictType	预测类型
	 * @param predictYear	预测年度
	 * @param toDataBase	1：入库；0：不入库
	 * @return
	 */
	public Map handlePredict(String predictType, int predictYear, int toDataBase);
	
	/**
	 * 获取预测年度的列表数据
	 * @param predictType	预测类型
	 * @param predictYear	预测年度
	 * @return
	 */
	public List<Object[]> getListData(String predictType, int predictYear);
}
