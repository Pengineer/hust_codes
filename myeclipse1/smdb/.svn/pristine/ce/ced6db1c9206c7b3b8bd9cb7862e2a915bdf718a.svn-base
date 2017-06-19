package csdc.tool.dataMining.classifier;

import java.io.BufferedReader;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.mahout.classifier.sgd.CsvRecordFactory;
import org.apache.mahout.classifier.sgd.LogisticModelParameters;
import org.apache.mahout.classifier.sgd.OnlineLogisticRegression;
import org.apache.mahout.math.SequentialAccessSparseVector;
import org.apache.mahout.math.Vector;

/**
 * 线性回归分类预测器
 * Run a logistic regression model against CSV data
 * @author fengcl
 *
 */
public class OnlineLogisticRegressionClassifier {
	
	private String inputFile;		//输入文件，待分类预测的数据文件
	private String modelFile;		//分类模型，已建立好的模型文件
	private String idColumn;		//id列名
	
	/**
	 * 构造器
	 * @param inputFile	待预测数据文件的路径
	 * @param modelFile	模型文件的路径
	 */
	public OnlineLogisticRegressionClassifier(String inputFile, String modelFile){
		this.inputFile = inputFile;
		this.modelFile = modelFile;
	}
	
	/**
	 * 执行分类、预测
	 * @return	分类预测结果 <-> {@link ClassifyInfo}
	 * @throws Exception
	 */
	public ClassifierResult run() throws Exception {

		// 加载模型：读取模型文件，转换成模型对象
		LogisticModelParameters lmp = LogisticModelParameters.loadFrom(new File(modelFile));

		List<ClassifyInfo> classifyInfos = new ArrayList<ClassifyInfo>();	// 自定义，用于逐条存放分类信息

		CsvRecordFactory csv = lmp.getCsvRecordFactory();//csv文件处理器的初始化
		csv.setIdName(getIdColumn());//设定csv文件的唯一标识
		OnlineLogisticRegression lr = lmp.createRegression();//生成预测模型
		BufferedReader in = ClassifierHelper.open(inputFile);//从输入的预测集文件读取数据
		String line = in.readLine();//从第一行(标题)开始读取
		csv.firstLine(line);//指定为标题行
		line = in.readLine();//数据行
		
		//对预测集数据逐行遍历，并进行向量化
		while (line != null) {
			 Vector v = new SequentialAccessSparseVector(lmp.getNumFeatures());//定义向量
			 csv.processLine(line, v);//向量化
			 
			 Vector a = lr.classify(v);//调用模型进行分类预测，返回的score值用于后续的评估
			 int score = a.maxValueIndex();

			 Vector scores = lr.classifyFull(v);//调用模型进行分类预测
			 String correctLabel = csv.getTargetString(line);//获取真实的类别
			 String classifiedLabel = csv.getTargetLabel(scores.maxValueIndex());//获取预测值
			 if(Integer.parseInt(correctLabel)>6 && Integer.parseInt(classifiedLabel)<=6){
				 System.out.println(correctLabel + "--->" + classifiedLabel);
			 }
			 
			 //预测结果集进行格式化处理
			 classifyInfos.add(new ClassifyInfo(line, correctLabel, classifiedLabel, score));
			 
			 line = in.readLine();//读取下一行
		}
		
		return new ClassifierResult(classifyInfos);
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

}
