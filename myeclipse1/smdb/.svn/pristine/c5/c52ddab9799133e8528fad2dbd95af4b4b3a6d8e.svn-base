package csdc.tool.dataMining.classifier;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.mahout.classifier.ConfusionMatrix;
import org.apache.mahout.classifier.evaluation.Auc;
import org.apache.mahout.classifier.sgd.CsvRecordFactory;
import org.apache.mahout.classifier.sgd.LogisticModelParameters;
import org.apache.mahout.classifier.sgd.ModelDissector;
import org.apache.mahout.classifier.sgd.OnlineLogisticRegression;
import org.apache.mahout.math.Matrix;
import org.apache.mahout.math.SequentialAccessSparseVector;
import org.apache.mahout.math.Vector;

/**
 * 分类预测模型测试器
 * 用于预测的测试和评估
 * @author suwb
 *
 */
public class OnlineLogisticRegressionTest {
	
	private String inputFile;		//输入文件，待分类预测的数据文件
	private String modelFile;		//分类模型，已建立好的模型文件
	private String predictType;		//预测类型
	private String idColumn;		//id列名
	private boolean showAuc;		//是否显示AUC
	private boolean showScores;		//是否显示分数
	private boolean showConfusion;	//是否显示混淆矩阵
	private boolean showDissection;	//是否显示模型解析结果
	
	/**
	 * 构造器
	 * @param inputFile	待预测数据文件的路径
	 * @param modelFile	模型文件的路径
	 */
	public OnlineLogisticRegressionTest(String inputFile, String modelFile, String predictType){
		this.inputFile = inputFile;
		this.modelFile = modelFile;
		this.predictType = predictType;
	}
	
	/**
	 * 执行预测、评估
	 * @return	预测结果 <-> {@link ClassifyInfo}
	 * @throws Exception
	 */
	public TestResult run() throws Exception {
		
		if (!showAuc && !showConfusion && !showScores) {
			showAuc = true;
			showConfusion = true;
		}

		// 加载模型：读取模型文件，转换成模型对象
		LogisticModelParameters lmp = LogisticModelParameters.loadFrom(new File(modelFile));

		Auc collector = new Auc();//曲线下方的面积 (Area Under the Curve)，用于评估模型的质量
		ConfusionMatrix cm = new ConfusionMatrix(lmp.getTargetCategories(), "unknown");// 构造混淆矩阵，用于结果输出
		List<ClassifyInfo> classifyInfos = new ArrayList<ClassifyInfo>();	// 自定义，用于逐条存放分类信息

		CsvRecordFactory csv = lmp.getCsvRecordFactory();//csv文件处理器的初始化
		csv.setIdName(getIdColumn());//设定csv文件的唯一标识
		OnlineLogisticRegression lr = lmp.createRegression();//生成预测模型
		BufferedReader in = ClassifierHelper.open(inputFile);//从输入的预测集文件读取数据
		String line = in.readLine();//从第一行(标题)开始读取
		csv.firstLine(line);//指定为标题行
		line = in.readLine();//数据行
		
		if (showScores) {
			System.out.println("\"target\",\"model-output\",\"log-likelihood\"");
		}
		
		//对预测集数据逐行遍历，并进行向量化
		while (line != null) {
			 Vector v = new SequentialAccessSparseVector(lmp.getNumFeatures());//定义向量
			 int target = csv.processLine(line, v);//向量化
			 
//			 double score = lr.classifyScalar(v);//调用模型进行分类预测，返回的score值用于后续的评估
			 Vector a = lr.classify(v);//调用模型进行分类预测，返回的score值用于后续的评估
			 int score = a.maxValueIndex();
			 
			 if (showScores) {
				 System.out.printf(Locale.SIMPLIFIED_CHINESE, "%d,%.3f,%.6f%n", target, score, lr.logLikelihood(target, v));
			 }
			 collector.add(target, score);

			 Vector scores = lr.classifyFull(v);//调用模型进行分类预测
			 String correctLabel = csv.getTargetString(line);//获取真实的类别
			 String classifiedLabel = csv.getTargetLabel(scores.maxValueIndex());//获取预测值
//			 if(Integer.parseInt(correctLabel)>6 && Integer.parseInt(classifiedLabel)<=6){
//				 System.out.println(correctLabel + "--->" + classifiedLabel);
//			 }
			 if(predictType.equals("结项")){//用于训练的训练集中结项年数最大只到6，因此对于大于6的会报错，将这小部分数据处理掉
				 if(Integer.parseInt(correctLabel)>6) correctLabel = "6";
				 cm.addInstance(correctLabel, classifiedLabel);
			 }else if(predictType.equals("中检")){
				 if(Integer.parseInt(correctLabel)>4) correctLabel = "4";
				 cm.addInstance(correctLabel, classifiedLabel);
			 }
			 //预测结果集进行格式化处理
			 classifyInfos.add(new ClassifyInfo(line, correctLabel, classifiedLabel, score));
			 
			 line = in.readLine();//读取下一行
		}

		if (showAuc) {
			System.out.printf(Locale.SIMPLIFIED_CHINESE, "AUC = %.2f%n", collector.auc());
		}
		if (showConfusion) {
			System.out.printf(Locale.ENGLISH, "%n%s%n%n", cm.toString());
			
			Matrix m = collector.confusion();
			System.out.printf(Locale.SIMPLIFIED_CHINESE, "confusion: [[%.1f, %.1f], [%.1f, %.1f]]%n", m.get(0, 0), m.get(1, 0), m.get(0, 1), m.get(1, 1));
			m = collector.entropy();
			System.out.printf(Locale.SIMPLIFIED_CHINESE, "entropy: [[%.1f, %.1f], [%.1f, %.1f]]%n", m.get(0, 0), m.get(1, 0), m.get(0, 1), m.get(1, 1));
		}
		if(showDissection){
			dissect(lmp, csv);
		}
		
		return new TestResult(cm, classifyInfos);
	}
	
	/**
	 * 模型解剖，分析并打印每个预测因子的权重
	 * @param lmp	
	 * @param csv
	 * @throws IOException
	 */
	private void dissect(LogisticModelParameters lmp, CsvRecordFactory csv) throws IOException {
		OnlineLogisticRegression lr = lmp.createRegression();
		lr.close();
		ModelDissector md = new ModelDissector();
		BufferedReader in = ClassifierHelper.open(inputFile);
		
		String line = in.readLine();
		csv.firstLine(line);
		line = in.readLine();
		
		while (line != null) {
			Vector v = new SequentialAccessSparseVector(lmp.getNumFeatures());
			csv.processLine(line, v);

			md.update(v, csv.getTraceDictionary(), lr);
			
			line = in.readLine();
		}
		
		List<ModelDissector.Weight> weights = md.summary(100);
		for (ModelDissector.Weight w : weights) {
		  System.out.printf("%s\t%.1f\n", w.getFeature(), w.getWeight());
		}
		
	}

	public String getIdColumn() {
		return idColumn;
	}

	/**
	 * 设置id列名
	 * @param idColumn
	 */
	public void setIdColumn(String idColumn) {
		this.idColumn = idColumn;
	}
	
	/**
	 * 设置是否显示Auc 
	 * @param showAuc	true：是，false：否
	 */
	public void setShowAuc(boolean showAuc) {
		this.showAuc = showAuc;
	}

	/**
	 * 设置是否显示Scores 
	 * @param showScores	true：是，false：否
	 */
	public void setShowScores(boolean showScores) {
		this.showScores = showScores;
	}
	
	/**
	 * 设置是否显示混淆矩阵 
	 * @param showConfusion	true：是，false：否
	 */
	public void setShowConfusion(boolean showConfusion) {
		this.showConfusion = showConfusion;
	}

	/**
	 * 是否显示模型解析结果
	 * @param showDissection	true：是，false：否
	 */
	public void setShowDissection(boolean showDissection) {
		this.showDissection = showDissection;
	}


}
