package csdc.tool.dataMining.classifier;

import java.util.List;

import org.apache.mahout.classifier.ConfusionMatrix;

/**
 * 测试的结果类型
 * @author suwb
 *
 */
public class TestResult {
	private ConfusionMatrix confusionMatrix;
	private List<ClassifyInfo> classifyInfos;
	
	public TestResult(ConfusionMatrix confusionMatrix, List<ClassifyInfo> classifyInfos){
		this.confusionMatrix = confusionMatrix;
		this.classifyInfos = classifyInfos;
	}

	public ConfusionMatrix getConfusionMatrix() {
		return confusionMatrix;
	}
	public List<ClassifyInfo> getClassifyInfos() {
		return classifyInfos;
	}
}
