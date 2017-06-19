package csdc.tool.dataMining.classifier;

import java.util.List;

import org.apache.mahout.classifier.sgd.LogisticModelParameters;

import com.google.common.collect.Lists;

import csdc.tool.ApplicationContainer;

/**
 * 项目结项分类预测模型训练器
 * @author	fengcl
 */
public final class ProjectEndinspectionTrainer extends OnlineLogisticRegressionTrainer{
	
	private String predictType;			//预测类型
	private String predictorVariables;	//指定预测因子（变量）

	/**
	 * 项目结项分类预测模型训练器：构造器
	 * @param predictType	预测类型
	 * @param predictorVariables	预测因子（变量）
	 */
	public ProjectEndinspectionTrainer(String predictType, String predictorVariables){
		this.predictType = predictType;
		this.predictorVariables = predictorVariables;
	}
	
	// 准备训练器参数
	protected Object[] modelParameters() {
		
		//获取文件实际路径
		String inputFile = ApplicationContainer.sc.getRealPath("/dataMining/resources/classification/" + predictType + "_train.csv");
		String outputFile = ApplicationContainer.sc.getRealPath("/dataMining/resources/classification/" + predictType + ".model");

		List<String> typeList = Lists.newArrayList();	//预测变量的类型，只能是 numeric, word, or text中的一种.
		List<String> predictorList = Lists.newArrayList();//指定预测因子（变量）的名称。
		
		if (predictorVariables.contains("SUBTYPE")) {//项目子类
			typeList.add("word");	
			predictorList.add("SUBTYPE");
		}
		if (predictorVariables.contains("DISCIPLINETYPE")) {//学科门类
			typeList.add("word");	
			predictorList.add("DISCIPLINETYPE");
		}
		if (predictorVariables.contains("PASSMID")) {//中检通过状态
			typeList.add("numeric");	
			predictorList.add("PASSMID");
		}
		if (predictorVariables.contains("YEARS")) {//项目进行年数（已结项项目：立项时间->结项时间；未结项项目：立项时间->现在时间）
			typeList.add("numeric");	
			predictorList.add("YEARS");
		}
		if (predictorVariables.contains("UNIVTYPE")) {//高校类型
			typeList.add("word");	
			predictorList.add("UNIVTYPE");
		}
		if (predictorVariables.contains("LASTDEGREE")) {//负责人最后学历
			typeList.add("word");	
			predictorList.add("LASTDEGREE");
		}
		if (predictorVariables.contains("TITLE")) {//负责人职称级别
			typeList.add("word");	
			predictorList.add("TITLE");
		}
		if (predictorVariables.contains("GENDER")) {//负责人性别
			typeList.add("word");	
			predictorList.add("GENDER");
		}
		if (predictorVariables.contains("AGEGROUP")) {//负责人年龄区间
			typeList.add("word");	
			predictorList.add("AGEGROUP");
		}

		// 模型参数
		LogisticModelParameters lmp = new LogisticModelParameters();
//		lmp.setTargetVariable("FINISHED");	//使用指定的变量作为目标（这里是FINISHED）。
//		lmp.setMaxTargetCategories(2);	//目标变量的数量（这里是结项1 和 未结项0 两种）。
		lmp.setTargetVariable("YEARS");	//使用指定的变量作为目标（这里是YEARS）。
		lmp.setMaxTargetCategories(8);	//目标变量的数量（这里是是结项年数）。
		lmp.setNumFeatures(10000);	//设置用于构建模型的特征向量大小，当输入为text-like类型的值时，大的值是更好的。
		lmp.setUseBias(true);	//Eliminates the intercept term (a built-in constant predictor variable) from the model. Occasionally this is a good idea, but generally it isn’t since the SGD learning algorithm can usually eliminate the intercept term if warranted.是否使用常量bias，默认为1
		lmp.setTypeMap(predictorList, typeList);//设置预测变量（因子）的类型，用于后面读取CSV中的数据.
		lmp.setLambda(0);
		lmp.setLearningRate(50);	//设置学习率This can be large if you have lots of data or use lots of passes because it’s decreased progressively as data is examined.

		// 训练轮数
		int passes = 50;	
		// 是否打印分数
//		boolean showScores = false;	
		
		return new Object[]{
			inputFile,
			outputFile,
			lmp,
			passes,
//			showScores
		};
	}
}
