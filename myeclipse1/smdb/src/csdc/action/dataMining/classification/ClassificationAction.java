package csdc.action.dataMining.classification;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.action.dataMining.DadaMiningBaseAction;
import csdc.service.IClassificationService;
import csdc.tool.ApplicationContainer;
import csdc.tool.FileTool;
import csdc.tool.info.GlobalInfo;

/**
 * 数据挖掘：分类预测挖掘
 * @author fengcl
 *
 */
public class ClassificationAction extends DadaMiningBaseAction{
	private static final long serialVersionUID = 6024766231322324396L;
	
	@Autowired
	private IClassificationService classificationService;
	
	private String predictorVariables;	// 预测变量（因子）
	private String predictType;			// 预测类型
	private Integer predictYear;		// 项目结项年度
	
	/**
	 * 检测并获取模型名称
	 * @return
	 */
	public String fetchModelName(){
		String fileName = ApplicationContainer.sc.getRealPath("/dataMining/resources/classification/" + predictType + ".model");
		if (!FileTool.isExsits(fileName)) {
			jsonMap.put(GlobalInfo.ERROR_INFO, "模型不存在，请点击更新！");
		} else {
			jsonMap.put("hintInfo", predictType + ".model");
		}
		return SUCCESS;
	}

	/**
	 * 进入预测页面
	 * @return
	 */
	public String toPredict(){
		return SUCCESS;
	}
	
	/**
	 * 训练模型
	 * @return
	 */
	public String trainModel(){
		jsonMap = classificationService.handleTrain(predictType, predictYear, predictorVariables);
		return SUCCESS;
	}
	
	/**
	 * 项目预测
	 * @return
	 */
	public String predict(){
		jsonMap = classificationService.handlePredict(predictType, predictYear, toDataBase);
		return SUCCESS;
	}
	
	/**
	 * 项目测试
	 * @return
	 */
	public String test(){
		jsonMap = classificationService.handleTest(predictType, predictYear, toDataBase);
		return SUCCESS;
	}
	
	// 获取预测年度的列表数据
	protected List<Object[]> listData() {
		List<Object[]> listData = classificationService.getListData(predictType, predictYear);
		return listData;
	}
	
	public String pageName() {
		return "projectPredictPage";
	}
	public String[] column() {
		return null;
	}
	public String dateFormat() {
		return null;
	}
	public Object[] simpleSearchCondition() {
		return null;
	}
	public Object[] advSearchCondition() {
		return null;
	}
	public String pageBufferId() {
		return null;
	}


	public String getPredictorVariables() {
		return predictorVariables;
	}

	public void setPredictorVariables(String predictorVariables) {
		this.predictorVariables = predictorVariables;
	}
	public String getPredictType() {
		return predictType;
	}
	public void setPredictType(String predictType) {
		this.predictType = predictType;
	}
	public Integer getPredictYear() {
		return predictYear;
	}
	public void setPredictYear(Integer predictYear) {
		this.predictYear = predictYear;
	}
}
