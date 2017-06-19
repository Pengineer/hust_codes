package csdc.tool.dataMining.classifier;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Locale;


import org.apache.mahout.classifier.sgd.CsvRecordFactory;
import org.apache.mahout.classifier.sgd.LogisticModelParameters;
import org.apache.mahout.classifier.sgd.OnlineLogisticRegression;
import org.apache.mahout.classifier.sgd.RecordFactory;
import org.apache.mahout.math.RandomAccessSparseVector;
import org.apache.mahout.math.Vector;

import com.google.common.io.Closeables;

/**
 * 分类预测模型训练器<br>
 * Train a logistic regression(classifier) using stochastic gradient descent(SGD)
 * @author fengcl
 */
public abstract class OnlineLogisticRegressionTrainer {
	
	private OnlineLogisticRegression model;	//定义模型
	
	/**
	 * 指定模型训练器的参数<br>
	 * 1、inputFile		输入文件，训练集<br>
	 * 2、outputFile		输出文件，模型<br>
	 * 3、lmp			模型相关参数<br>
	 * 4、passes			训练轮数<br>
	 * 5、showScores		是否打印评估分数<br>
	 * @return
	 */
	protected abstract Object[] modelParameters();
	
	/**
	 * 模型训练
	 * @throws Exception 
	 */
	public void train() throws Exception{
		run(modelParameters());
	}
	
	/**
	 * 建模并输出
	 * @param output
	 * @throws Exception
	 */
	private void run(Object[] parameters) throws Exception {
		
		String inputFile = (String) parameters[0];
		String outputFile = (String) parameters[1];
		LogisticModelParameters lmp = (LogisticModelParameters) parameters[2];
		int passes = (Integer) parameters[3];
//		boolean showScores = (Boolean) parameters[4];
		
		// 对数似然函数值评估
//		double logPEstimate = 0;
//		int samples = 0;

		CsvRecordFactory csv = lmp.getCsvRecordFactory();//csv文件处理器的初始化
		OnlineLogisticRegression lr = lmp.createRegression();//生成预测模型，此处为空
		for (int pass = 0; pass < passes; pass++) {
			BufferedReader in = ClassifierHelper.open(inputFile);//从输入的预测集文件读取数据
			try {
				// 读取标题行，第一行变量名
				csv.firstLine(in.readLine());
				// 读取下一行，数据行第一行	
				String line = in.readLine();
				while (line != null) {
					// 逐行向量化
					Vector input = new RandomAccessSparseVector(lmp.getNumFeatures());
//					System.out.println(line);
					int targetValue = csv.processLine(line, input);

//					// 性能评估
//					double logP = lr.logLikelihood(targetValue, input);
//					if (!Double.isInfinite(logP)) {
//						if (samples < 20) {
//							logPEstimate = (samples * logPEstimate + logP) / (samples + 1);
//						} else {
//							logPEstimate = 0.95 * logPEstimate + 0.05 * logP;
//						}
//						samples++;
//					}
////					double p = lr.classifyScalar(input);
//					Vector a = lr.classify(input);
//					int p = a.maxValueIndex();
//					
//					if (showScores) {
//						System.out.printf(Locale.SIMPLIFIED_CHINESE, "%10d %2d %10.2f %2.4f %10.4f %10.4f%n", samples, targetValue, lr.currentLearningRate(), p, logP, logPEstimate);
//					}

					// 更新模型
					lr.train(targetValue, input);
					// 读取下一行	
					line = in.readLine();
				}
			} finally {
				Closeables.close(in, true);
			}
		}

		// 输出模型
		OutputStream modelOutput = new FileOutputStream(outputFile);
		try {
			lmp.saveTo(modelOutput);
		} finally {
			Closeables.close(modelOutput, false);
		}

		// 模型解析
		System.out.println(lmp.getNumFeatures());
		System.out.println(lmp.getTargetVariable() + " ~ ");
		String sep = "";
		for (String v : csv.getTraceDictionary().keySet()) {
			double weight = predictorWeight(lr, 0, csv, v);
			if (weight != 0) {
				System.out.printf(Locale.SIMPLIFIED_CHINESE, "%s%.3f*%s", sep, weight, v);
				sep = " + ";
			}
		}
		System.out.printf("%n");
		model = lr;
		int rowCnt = lr.getBeta().numRows();
		for (int row = 0; row < rowCnt; row++) {
			for (String key : csv.getTraceDictionary().keySet()) {
				double weight = predictorWeight(lr, row, csv, key);
				if (weight != 0) {
					System.out.printf(Locale.SIMPLIFIED_CHINESE, "%20s %.5f%n", key, weight);
				}
			}
			int colCnt = lr.getBeta().numCols();
			for (int column = 0; column < colCnt; column++) {
				System.out.printf(Locale.SIMPLIFIED_CHINESE, "%15.9f ", lr.getBeta().get(row, column));
			}
			System.out.println();
		}
	}

	/**
	 * 获取预测变量的权重
	 * @param lr	模型
	 * @param row	行号
	 * @param csv	csv数据
	 * @param predictor	预测变量名
	 * @return	predictor的权重值
	 */
	private static double predictorWeight(OnlineLogisticRegression lr, int row, RecordFactory csv, String predictor) {
		double weight = 0;
		for (Integer column : csv.getTraceDictionary().get(predictor)) {
			weight += lr.getBeta().get(row, column);
		}
		return weight;
	}

	/**
	 * 获取模型
	 * @return
	 */
	public OnlineLogisticRegression getModel() {
		return model;
	}
}
