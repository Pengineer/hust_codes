/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package csdc.tool.dataMining.classifier;

import csdc.tool.ApplicationContainer;


/**
 * 项目分类预测器
 * @author fengcl
 */
public final class ProjectPredictor{

	private String predictType;		//预测类型
	private int predictYear;		//预测年度
	
	/**
	 * 项目预测器：构造器
	 * @param inputFile	输入文件，预测集或测试集
	 * @param modelFile	输入文件，预测模型文件
	 */
	public ProjectPredictor(String predictType, int predictYear){
		this.predictType = predictType;
		this.predictYear = predictYear;
	}

	/**
	 * 项目情况预测
	 * @return
	 * @throws Exception 
	 */
	public ClassifierResult predict() throws Exception{
		//获取文件实际路径
		String inputFile = ApplicationContainer.sc.getRealPath("/dataMining/resources/classification/" + predictYear + "_" + predictType + "_predict.csv");
		String modelFile = ApplicationContainer.sc.getRealPath("/dataMining/resources/classification/" + predictType + ".model");
		
		//构造分类器
		OnlineLogisticRegressionClassifier classifier = new OnlineLogisticRegressionClassifier(inputFile, modelFile);
		
		classifier.setIdColumn("PROJECTID");
//		classifier.setShowAuc(true);
//		classifier.setShowConfusion(true);
//		classifier.setShowScores(false);
//		classifier.setShowDissection(false);
		
		ClassifierResult results = classifier.run();
//		ConfusionMatrix cm = results.getConfusionMatrix();
//		int[][] out = cm.getConfusionMatrix();
//		System.out.printf(Locale.ENGLISH, "%n%s%n%n", cm.toString());
//		System.out.println("已结项[Y]：" + out[0][0] + "\t已结项[N]：" + out[0][1] + "\n未结项[N]：" + out[1][0] + "\t未结项[Y]：" + out[1][1]);
		return results;
	}
}
