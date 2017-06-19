package csdc.tool.dataMining.classifier;

import java.util.List;

/**
 * 预测的结果类型
 * @author suwb
 *
 */
public class ClassifierResult {
	
	private List<ClassifyInfo> classifyInfos;
	
	public ClassifierResult(List<ClassifyInfo> classifyInfos){
		this.classifyInfos = classifyInfos;
	}

	public List<ClassifyInfo> getClassifyInfos() {
		return classifyInfos;
	}

}
