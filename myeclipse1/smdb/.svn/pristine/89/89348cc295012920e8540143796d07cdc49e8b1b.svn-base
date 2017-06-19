package csdc.service.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.DMResult;
import csdc.dao.JdbcDao;
import csdc.service.IClassificationService;
import csdc.tool.ApplicationContainer;
import csdc.tool.CsvTool;
import csdc.tool.JsonUtil;
import csdc.tool.dataMining.classifier.ClassifierResult;
import csdc.tool.dataMining.classifier.ClassifyInfo;
import csdc.tool.dataMining.classifier.OnlineLogisticRegressionTrainer;
import csdc.tool.dataMining.classifier.ProjectEndinspectionTrainer;
import csdc.tool.dataMining.classifier.ProjectMidinspectionTrainer;
import csdc.tool.dataMining.classifier.ProjectPredictor;
import csdc.tool.info.DataMiningInfo;
import csdc.tool.info.GlobalInfo;

/**
 * 数据挖掘：分类预测挖掘的业务逻辑层
 * @author fengcl
 */
public class ClassificationService extends DataMiningService implements IClassificationService{

	@Autowired
	private JdbcDao jdbcDao;
	
	public String getProjectTypeByPredictType(String predictType){
		String projectType = null;
		if(predictType != null){
			if (predictType.startsWith("general")) {
				projectType =  "一般项目";
			} else if (predictType.startsWith("key")) {
				projectType =  "重大攻关项目";
			} else if (predictType.startsWith("instp")) {
				projectType =  "基地项目";
			} else if (predictType.startsWith("post")) {
				projectType =  "后期资助项目";
			} else if (predictType.startsWith("entrust")) {
				projectType =  "应急委托课题";
			}
		}
		return projectType;
	}
	
	public String getTypeByPredictType(String predictType){
		String type = null;
		if(predictType != null){
			if (predictType.endsWith("end")) {
				type =  "结项";
			} else if (predictType.endsWith("mid")) {
				type =  "中检";
			}
		}
		return type;
	}
	
	public boolean prepareProjectMidData(String predictType, int predictYear, int useType) {
		String projectType = getProjectTypeByPredictType(predictType);
		if (projectType == null) {
			return false;
		}
		
		// 获取专业职称映射[code/name -> description(级别)]
		List<Object[]> sos = dao.query("select so.code, so.name, so.description from SystemOption so where so.standard = 'GBT8561-2001'");
		Map<String, String> titleMap = new HashMap<String, String>();
		for (Object[] so : sos) {
			if (null != so[2]) {
				titleMap.put(so[0] + "/" + so[1], (String) so[2]);
			}
		}

		int varietyYear = 0;	// 当useType = 1，2，3 时分别表示：训练（集）年份 ，测试（集）年份，预测（集）年份
		
		// 数据获取（数据选择）
		String sql = "select distinct dp.c_id, dp.c_name, dp.c_subtype, dp.c_discipline_type, du.c_type, " +
				" p.c_last_degree, p.c_title, p.c_gender, p.c_age_group, dp.c_year, dp.c_mid_year, sp.c_is_passmid" +
				" from s_s_project sp, s_d_person p, s_d_project dp, s_d_unit du " +
				" where sp.c_d_person_id = p.c_id and sp.c_d_project_id = dp.c_id and du.c_id = sp.c_d_unit_id " +
				" and sp.c_is_granted = 1 and dp.c_status < 3 and dp.c_project_type = '" + projectType + "'";
		
//		if (useType == 1) {// 训练集：通过中检且中检年份小于等于预测中检年份
//			varietyYear = predictYear - 2;	// 训练（集）年份
//			sql += " and ((dp.c_mid_year is null and dp.c_year <= " + varietyYear + ")";
//			sql += " or (dp.c_mid_year is not null and dp.c_mid_year <= " + varietyYear + "))";
//		}else if (useType == 2) {// 测试集：未中检且项目年度小于等于测试年份，或者中检年份为测试年份
//			varietyYear = predictYear - 1;	// 测试（集）年份
//			sql += " and ((dp.c_mid_year is null and dp.c_year = " + varietyYear + ")";
//			sql += " or (dp.c_mid_year = " + varietyYear + "))";
//		}else if (useType == 3) {// 预测集：未通过中检且项目年度小于等于预测中检年份，或者中检年份为预测中检年份
//			varietyYear = predictYear;		//预测（集）年份
//			sql += " and ((dp.c_mid_year is null and dp.c_year = " + varietyYear + ")";
//			sql += " or (dp.c_mid_year = " + varietyYear + "))";
//		}
		if (useType == 1) {// 训练集：通过中检且中检年份小于等于预测中检年份-2
			varietyYear = predictYear - 2;	// 训练（集）年份
			sql += " and (dp.c_mid_year is not null and dp.c_mid_year <= " + varietyYear + ")";
		}else if (useType == 2) {// 测试集：通过中检且中检年份小于等于预测中检年份-1
			varietyYear = predictYear - 1;	// 测试（集）年份
			sql += " and (dp.c_mid_year is not null and dp.c_mid_year <= " + varietyYear + ")";
		}else if (useType == 3) {// 预测集：未通过中检且项目年度小于等于预测中检年份，或者中检年份为预测中检年份
			varietyYear = predictYear;		//预测（集）年份
			sql += " and ((dp.c_mid_year is null and dp.c_year <= " + varietyYear + " and dp.c_year >= " + ((varietyYear-2>2011)?2011:(varietyYear-2)) + ")";
			sql += " or (dp.c_mid_year = " + varietyYear + "))";
		}
		
		List<String[]> list = jdbcDao.query(sql);
		
		//定义csv文件标题
		String[] header = {"PROJECTID", "PROJECTNAME", "SUBTYPE", "DISCIPLINETYPE", "UNIVTYPE", 
				"LASTDEGREE", "TITLE", "GENDER", "AGEGROUP", "PROJECTYEAR", "YEARS", "PASSMID"};
		
		List<String[]> dataList = new ArrayList<String[]>();	//训练数据集
		String[] datas = null; 
		int projectMidYear = 0;	//中检年度
		int projectYear = 0;	//项目年度
		HashSet sets = new HashSet();
		// 数据处理（清洗、转换）
		for (String[] rawDatas : list) {
			// 过滤掉不完整的数据
			if (rawDatas[2] == null || rawDatas[3] == null || rawDatas[4] == null || rawDatas[5] == null
        			|| !titleMap.containsKey(rawDatas[6]) || rawDatas[7] == null || rawDatas[8] == null) {
				continue;
			} else {
				datas = new String[header.length];
				datas[0] = rawDatas[0];		//项目appId
				datas[1] = rawDatas[1].replaceAll(",", "，").replaceAll("\\s", "");	//项目名称
				datas[2] = rawDatas[2];		//项目子类
				datas[3] = rawDatas[3];		//项目学科门类
				datas[4] = rawDatas[4];		//学校结构类别（部属、地方）
				datas[5] = rawDatas[5];		//负责人学历
				datas[6] = titleMap.get(rawDatas[6]);	//负责人职称
				datas[7] = rawDatas[7];		//负责人性别
				datas[8] = rawDatas[8];		//负责人年龄区间
				datas[9] = rawDatas[9];		//项目年度
				
				projectYear = Integer.valueOf(rawDatas[9]);	//项目年度
				projectMidYear = (rawDatas[10] != null) ? Integer.valueOf(rawDatas[10]) : 0;	//项目中检年度
				// 项目进行年数（已中检项目：立项时间->中检时间；未结项项目：立项时间->predictYear）
				if (useType == 1 || useType == 2) {	//已通过中检
					datas[10] = (projectMidYear - projectYear) + "";
				}else {	//未中检
					datas[10] = (predictYear - projectYear) + "";
				}
				sets.add(datas[10]);
				datas[11] = (rawDatas[11] == null) ? "0" : rawDatas[11];	//项目中检状态(0表示未中检，1表示通过中检)
				
				dataList.add(datas);
			}
		}
		
		System.out.println(sets.toString());
		
		// 数据构建（训练集、测试集）
		String filePath = ApplicationContainer.sc.getRealPath("/dataMining/resources/classification");	//获取文件实际路径
		String fileName = "/";
		if (useType == 1) {
			fileName += predictType + "_train.csv";
		} else if (useType == 2) {
			fileName += predictYear + "_" + predictType + "_test.csv";
		} else if (useType == 3) {
			fileName += predictYear + "_" + predictType + "_predict.csv";
		}
		CsvTool.writeCsv(filePath + fileName, header, dataList);
		
		return true;
	}

	public boolean prepareProjectEndData(String predictType, int predictYear, int useType) {
		String projectType = getProjectTypeByPredictType(predictType);
		if (projectType == null) {
			return false;
		}
		
		// 获取专业职称映射[code/name -> description(级别)]
		List<Object[]> sos = dao.query("select so.code, so.name, so.description from SystemOption so where so.standard = 'GBT8561-2001'");
		Map<String, String> titleMap = new HashMap<String, String>();
		for (Object[] so : sos) {
			if (null != so[2]) {
				titleMap.put(so[0] + "/" + so[1], (String) so[2]);
			}
		}

		int varietyYear = 0;	// 当useType = 1，2，3 时分别表示：训练（集）年份 ，测试（集）年份，预测（集）年份
		
		// 数据获取（数据选择）
//		String sql = "select distinct dp.c_id, dp.c_name, dp.c_subtype, dp.c_discipline_type, du.c_type, " +
//				" p.c_last_degree, p.c_title, p.c_gender, p.c_age_group, dp.c_year, dp.c_end_year, sp.c_is_passmid, sp.c_is_finished" +
//				" from s_s_project sp, s_d_person p, s_d_project dp, s_d_unit du " +
//				" where sp.c_d_person_id = p.c_id and sp.c_d_project_id = dp.c_id and du.c_id = sp.c_d_unit_id " +
//				" and sp.c_is_granted = 1 and dp.c_status < 3 and dp.c_project_type = '" + projectType + "'";
		String sql = "select distinct dp.c_id, dp.c_name, dp.c_subtype, dp.c_discipline_type, du.c_type, " +
				" p.c_last_degree, p.c_title, p.c_gender, p.c_age_group, dp.c_year, dp.c_end_year, sp.c_is_passmid" +
				" from s_s_project sp, s_d_person p, s_d_project dp, s_d_unit du " +
				" where sp.c_d_person_id = p.c_id and sp.c_d_project_id = dp.c_id and du.c_id = sp.c_d_unit_id " +
				" and sp.c_is_granted = 1 and dp.c_status < 3 and dp.c_project_type = '" + projectType + "'";
		
//		if (useType == 1) {// 训练集：未结项且项目年度小于等于训练年份，或者结项且结项年份小于等于训练年份
//			varietyYear = predictYear - 2;	// 训练（集）年份
//			sql += " and ((dp.c_end_year is null and dp.c_year <= " + varietyYear + ")";
//			sql += " or (dp.c_end_year is not null and dp.c_end_year <= " + varietyYear + "))";
//		}else if (useType == 2) {// 测试集：未结项且项目年度小于等于测试年份，或者结项年份为测试年份
//			varietyYear = predictYear - 1;	// 测试（集）年份
//			sql += " and ((dp.c_end_year is null and dp.c_year <= " + varietyYear + ")";
//			sql += " or (dp.c_end_year = " + varietyYear + "))";
//		}else if (useType == 3) {// 预测集：未结项且项目年度小于等于预测结项年份，或者结项年份为预测结项年份
//			varietyYear = predictYear;		//预测（集）年份
//			sql += " and ((dp.c_end_year is null and dp.c_year <= " + varietyYear + ")";
//			sql += " or (dp.c_end_year = " + varietyYear + "))";
//		}
		if (useType == 1) {// 训练集：已结项且结项年份小于等于预测年份-2;
			varietyYear = predictYear - 2;	// 训练（集）年份
			sql += " and sp.c_is_finished = 1 and (dp.c_end_year is not null and dp.c_end_year <= " + varietyYear + ")";			
		}else if (useType == 2) {// 测试集：已结项且结项年份等于预测年份-1;
			varietyYear = predictYear - 1;	// 测试（集）年份
			sql += " and sp.c_is_finished = 1 and (dp.c_end_year is not null and dp.c_end_year = " + varietyYear + ")";
		}else if (useType == 3) {// 预测集：未结项且项目年度小于等于预测结项年份，或者结项年份为预测结项年份
			varietyYear = predictYear;		//预测（集）年份
			sql += " and ((sp.c_is_finished is null and dp.c_year <= " + varietyYear + " and dp.c_year >= " + ((varietyYear-2>2011)?2011:(varietyYear-2)) + ")";
			sql += " or (dp.c_end_year = " + varietyYear + "))";
		}		
		
		List<String[]> list = jdbcDao.query(sql);
		
		//定义csv文件标题
		String[] header = {"PROJECTID", "PROJECTNAME", "SUBTYPE", "DISCIPLINETYPE", "UNIVTYPE", 
				"LASTDEGREE", "TITLE", "GENDER", "AGEGROUP", "PROJECTYEAR", "YEARS", "PASSMID"};
		
		List<String[]> dataList = new ArrayList<String[]>();	//训练数据集
		String[] datas = null; 
		HashSet sets = new HashSet();
		int projectEndYear = 0;	//结项年度
		int projectYear = 0;	//项目年度
		// 数据处理（清洗、转换）
		for (String[] rawDatas : list) {
			// 过滤掉不完整的数据
        	if (rawDatas[2] == null || rawDatas[3] == null || rawDatas[4] == null || rawDatas[5] == null
        			|| !titleMap.containsKey(rawDatas[6]) || rawDatas[7] == null || rawDatas[8] == null) {
				continue;
			} else {
				datas = new String[header.length];
				datas[0] = rawDatas[0];		//项目appId
				datas[1] = rawDatas[1].replaceAll(",", "，").replaceAll("\\s", "");	//项目名称
				datas[2] = rawDatas[2];		//项目子类
				datas[3] = rawDatas[3];		//项目学科门类
				datas[4] = rawDatas[4];		//学校结构类别（部属、地方）
				datas[5] = rawDatas[5];		//负责人学历
				datas[6] = titleMap.get(rawDatas[6]);	//负责人职称
				datas[7] = rawDatas[7];		//负责人性别
				datas[8] = rawDatas[8];		//负责人年龄区间
				datas[9] = rawDatas[9];		//项目年度
				
				projectYear = Integer.valueOf(rawDatas[9]);	//项目年度
				projectEndYear = (rawDatas[10] != null) ? Integer.valueOf(rawDatas[10]) : 0;	//项目结项年度
				// 项目进行年数（已结项项目：立项时间->结项时间；未结项项目：立项时间->varietyYear）
				if (useType == 1 || useType == 2) {	//已结项
					datas[10] = (projectEndYear - projectYear) + "";
				}else {	//未结项
					datas[10] = (predictYear - projectYear) + "";
				}
				sets.add(datas[10]);
				datas[11] = (rawDatas[11] == null) ? "0" : rawDatas[11];	//项目中检状态(0表示未中检，1表示通过中检)
//				datas[12] = (rawDatas[12] == null) ? "0" : rawDatas[12];	//项目结项状态(0表示未结项，1表示已结项)

				dataList.add(datas);
			}
		}
		
		System.out.println(sets.toString());
		
		// 数据构建（训练集、测试集）
		String filePath = ApplicationContainer.sc.getRealPath("/dataMining/resources/classification");	//获取文件实际路径
		String fileName = "/";
		if (useType == 1) {
			fileName += predictType + "_train.csv";
		} else if (useType == 2) {
			fileName += predictYear + "_" + predictType + "_test.csv";
		} else if (useType == 3) {
			fileName += predictYear + "_" + predictType + "_predict.csv";
		}
		CsvTool.writeCsv(filePath + fileName, header, dataList);
		
		return true;
	}
	
	public boolean prepareProjectData(String predictType, int predictYear, int useType) {
		boolean flag = false;
		String type = getTypeByPredictType(predictType);
		
		if("结项".equals(type)){
			flag = prepareProjectEndData(predictType, predictYear, useType);
		} else if ("中检".equals(type)) {
			flag = prepareProjectMidData(predictType, predictYear, useType);
		}
		return flag;
	}
	
	public TreeMap<Integer, Integer> fetchProjectCntPerYear(String predictType, int predictYear) {
		Map<Integer, Integer> year2cntMap = new HashMap<Integer, Integer>(); 
		String projectType = getProjectTypeByPredictType(predictType);
		String sql = null;
		if (predictType.endsWith("end")) {
			// 数据获取（数据选择）
			sql = "select dp.c_end_year, count(*) from s_d_project dp where dp.c_project_type = '" + projectType + "' and dp.c_end_year is not null " +
		        " and dp.c_subtype is not null and dp.c_discipline_type is not null and dp.c_end_year <= '" + predictYear + "'" +
				" group by dp.c_end_year order by dp.c_end_year" ;
		}else if (predictType.endsWith("mid")) {
			// 数据获取（数据选择）
			sql = "select dp.c_mid_year, count(*) from s_d_project dp where dp.c_project_type = '" + projectType + "' and dp.c_mid_year is not null " +
		        " and dp.c_subtype is not null and dp.c_discipline_type is not null and dp.c_mid_year <= '" + predictYear + "'" +
				" group by dp.c_mid_year order by dp.c_mid_year" ;
		}
		List<String[]> results = jdbcDao.query(sql);
		for (String[] strs : results) {
			year2cntMap.put(Integer.valueOf(strs[0]), Integer.valueOf(strs[1]));
		}
		if (!year2cntMap.containsKey(predictYear)) {
			year2cntMap.put(predictYear, 0);
		}
		return new TreeMap(year2cntMap);
	}
	
	public Map handleTrain(String predictType, int predictYear, String predictorVariables){
		Map map = new HashMap();
		
		String type = this.getTypeByPredictType(predictType);
		String projectType = this.getProjectTypeByPredictType(predictType);
		
		// 准备训练数据
		this.prepareProjectData(predictType, predictYear, 1);
		
		OnlineLogisticRegressionTrainer trainer = null;
		
		if("结项".equals(type)){
			// 项目结项模型训练器
			trainer = new ProjectEndinspectionTrainer(predictType, predictorVariables);
		}else if ("中检".equals(type)) {
			// 项目中检模型训练器
			trainer = new ProjectMidinspectionTrainer(predictType, predictorVariables);
		}

		try {
			trainer.train();
			map.put("hintInfo", projectType + type + "预测模型训练成功！");
		} catch (Exception e) {
			e.printStackTrace();
			map.put(GlobalInfo.ERROR_INFO, projectType + type + "模型训练失败！错误原因：\n" + e.getMessage());
		}
		
		return map;
	}
	
	public Map handlePredict(String predictType, int predictYear, int toDataBase){
		Map map = new HashMap();
		
		String type = this.getTypeByPredictType(predictType);	//获取类型：中检、结项等
		String projectType = this.getProjectTypeByPredictType(predictType);	//项目类型：一般项目、基地项目等

		List xDataList = new ArrayList<Integer>();	//x轴坐标数据
		List yData1List = new ArrayList<Integer>();	//y轴坐标数据1：真实数据
		List yData2List = new ArrayList<Integer>();	//y轴坐标数据2：预测数据
		TreeMap<Integer, Integer> year2cnts = this.fetchProjectCntPerYear(predictType, predictYear);
		for (Entry<Integer, Integer> entry : year2cnts.entrySet()) {
			int year = entry.getKey();
			//准备真实值
			xDataList.add(year);				//结项（中检）年度
			yData1List.add(entry.getValue());	//真实值
			
			if (year <= predictYear) {
				//获取预测值
				this.prepareProjectData(predictType, year, 3);
				
				// 项目预测器
				ProjectPredictor predictor = new ProjectPredictor(predictType, year);	
				
				// 预测器结果
				ClassifierResult results = null;
				try {
					results = predictor.predict();
				} catch (Exception e) {
					e.printStackTrace();
				}
				//预测值计数
				int data2 = 0;
//				if(type.equals("结项")){
					if (results != null){
						for (ClassifyInfo ci: results.getClassifyInfos()) {
							int costYear = Integer.valueOf(ci.getClassifiedLabel());
							String[] a = ci.getData().split(",");
							if(Integer.valueOf(a[9])+costYear == year){//==还是<=
								data2++;								
							}
//							if (costYear + ) {	// 1：已结项（通过中检）；0：未结项（未中检）
//							}
						}
					}					
//				}
				yData2List.add(data2);	//预测值
			}
		}
		
		JsonUtil util = new JsonUtil();
		util.put("title", "项目" + type + "预测");	//图形主标题
		util.put("subTitle", projectType);			//图形子标题
		util.put("yAxisTitle", type + "情况");		//y轴标题
		util.put("categories", xDataList);			//x轴数据
		util.put("line1Name", "真实值");				//y1轴名称
		util.put("line1Data", yData1List);			//y1轴数据
		util.put("line2Name", "预测值");				//y2轴名称
		util.put("line2Data", yData2List);			//y2轴数据
		//json数据返回
		map = util.getMap();
		
		//如果需要入库，则更新或添加记录到数据库
		if(toDataBase == 1){
			//根据挖掘主题获取挖掘结果
			String title = predictYear + "年" + projectType + type + "预测";
			DMResult dmResult = (DMResult) dao.queryUnique("from DMResult dm where dm.title = ?", title);
			//如果挖掘结果不存在，则开始预测，并存库；否则直接将resultJson转为jsonMap返回
			if (dmResult == null) {
				//新增结果
				dmResult = new DMResult();
			}
			dmResult.setTitle(title);
			JsonUtil configUtil = new JsonUtil();
			configUtil.put(DataMiningInfo.GRAPH_TYPE, DataMiningInfo.HIGHCHARTS);
			configUtil.put("预测类型", projectType + type + "预测");
			configUtil.put("预测年度", predictYear);
			dmResult.setConfig(configUtil.toString());
			dmResult.setResultJson(util.toString());
			dmResult.setType(DataMiningInfo.CLASSFICATION);
			dmResult.setDate(new Date());
			dao.addOrModify(dmResult);
		}
		
		return map;
	}
	
	public Map handleTest(String predictType, int predictYear, int toDataBase) {
		return null;
	}

	public List<Object[]> getListData(String predictType, int predictYear) {
		List<Object[]> listData = null;
		//获取预测值
		this.prepareProjectData(predictType, predictYear, 3);
		ProjectPredictor predictor = new ProjectPredictor(predictType, predictYear);	// 项目结项预测器		
		try {
			ClassifierResult results = predictor.predict();
			// 组装列表数据：预测结果为结项（中检）的项目
			listData = new ArrayList<Object[]>();
			for (ClassifyInfo ci: results.getClassifyInfos()) {
				String[] temp = ci.getData().split(",");
				if (Integer.parseInt(ci.getClassifiedLabel())+Integer.parseInt(temp[9])== predictYear) {// 已结项（通过中检）
					Object[] project = new Object[7];
					project[0] = temp[0];	//项目id
					project[1] = temp[1];	//项目名称
					project[2] = temp[2];	//项目子类
					project[3] = temp[3];	//项目学科
					project[4] = temp[9];	//项目年度
					project[5] = temp[10] + "年";	//项目进展年数
					project[6] = ("1".equals(temp[11]) ? "通过中检" : "未中检");	//中检状态
					listData.add(project);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listData;
	}
}
