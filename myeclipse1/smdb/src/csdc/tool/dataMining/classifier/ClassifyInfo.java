package csdc.tool.dataMining.classifier;

/**
 * 分类输出信息辅助类
 * @author fengcl
 *
 */
public class ClassifyInfo {
	private String data;
	private String originalLabel;
	private String classifiedLabel;
	private double score; 

	/**
	 * 构造器：分类输出信息
	 * @param data				数据
	 * @param originalLabel		分类前的类别
	 * @param classifiedLabel	分类后的类别
	 * @param score				用于分类的分数
	 */
	public ClassifyInfo(String data, String originalLabel, String classifiedLabel, double score){
		this.data = data;
		this.originalLabel = originalLabel;
		this.classifiedLabel = classifiedLabel;
		this.score = score;
	}

	/**
	 * 获取数据
	 * @return
	 */
	public String getData() {
		return data;
	}
	
	/**
	 * 获取原始类别Label
	 * @return
	 */
	public String getOriginalLabel() {
		return originalLabel;
	}
	
	/**
	 * 获取分类结果Label
	 * @return
	 */
	public String getClassifiedLabel() {
		return classifiedLabel;
	}
	
	/**
	 * 获取分类分数
	 * @return
	 */
	public double getScore() {
		return score;
	}
}
