package csdc.service.imp;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;

import csdc.bean.ProjectApplication;
import csdc.service.IFinanceService;

/**
 * 项目经费核算
 */
public class FinanceService extends BaseService implements IFinanceService {
	
	public static final String CLIENTDATA = "clientData";//客户端数据
	public static String IDSDATA = "idsData";//服务端缓存id数据
	
	private String[] lib = new String[]{
		"重庆市", "四川省", "贵州省", "云南省", "西藏自治区", "陕西省", "甘肃省", "宁夏回族自治区", "青海省", "新疆维吾尔自治区", "内蒙古自治区", "广西壮族自治区"
	};
	private String[] specialAreas = new String[]{"西部", "新疆维吾尔自治区", "西藏自治区"};
	private int year;//项目年份
	
	/**
	 * 字符串join
	 * @return
	 */
	private String join(String[] arr){
		String str = "";
		for(int i = 0; i < arr.length; i++){
			if(i < arr.length - 1){
				str += ("'" + arr[i] + "', ");
			} else {
				str += ("'" + arr[i] + "'");
			}
		}
		return ("".equals(str)) ? "''" : str;
	}
	
	/**
	 * 根据省份判断是否是西部、新疆、西藏
	 */
	private boolean isSpeciaclArea(String provinceName){
		for(String name : lib){
			if(name.equals(provinceName)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 根据申请数、立项数计算立项比例、返回格式化数据
	 */
	private String getGrantedRate(int applyNum, int grantNum){
		double rate = (grantNum * 1.0) / (applyNum * 1.0);
		DecimalFormat df = new DecimalFormat();
		df.applyPattern("#0.00%");
		return df.format(rate);
	}
	
	/**
	 * 根据项目类型, 申请项目经费返回修订过的项目经费
	 */
	private double getRevisedFee(String projectType, double appFee, double planFee, double youthFee){
		int temp = (int)(appFee * 100);
		//按经费百位向上取整
		int fNum = temp % 10; //个位数
		if(fNum != 0) {
			temp = temp + 10 - fNum;
		}
		appFee = temp / 100.0;
		if(projectType.equals("规划基金项目")){
			appFee = (appFee <= planFee) ? appFee : planFee;
		} else if(projectType.equals("青年基金项目")){
			appFee = (appFee <= youthFee) ? appFee : youthFee;
		}
		return appFee;
	}
	
	/**
	 * 格式化经费为两位小数
	 */
	private double formatFee(double fee){
		DecimalFormat df = new DecimalFormat();
		df.applyPattern("#0.00");
		return Double.parseDouble(df.format(fee));
	}
	
	/**
	 * 初始化容器Map
	 * 1.客户端数据 resultMap
	 * 2.服务端缓存数据 idsMap(记录拟立项id)
	 */
	@SuppressWarnings("unchecked")
	private Map initMap(List<String> disTypeList, String[] projectTypes){
		Map dataMap = new HashMap();//总容器
		
		Map resultMap = new HashMap();//客户端数据容器
		Map idsMap = new HashMap();//拟立项id缓存容器
		
		//学科门类Map
		Map disTypeMap = new HashMap();
		Map idsDisTypeMap = new HashMap();//学科缓存
		//初始化各个学科
		for(String disciplineType : disTypeList){
			Map disMap = new HashMap();
			
			//合计数map
			Map countMap = new HashMap();
			countMap.put("立项合计", 0);
			countMap.put("立项总经费", 0.0);
			
			//项目类型Map
			Map proTypeMap = new HashMap();
			Map idsProTypeMap = new HashMap();
			for(String ptype : projectTypes){
				Map proMap = new HashMap();
				proMap.put("申请数", 0);
				proMap.put("立项数", 0);
				proMap.put("立项比例", "0.0%");
				proMap.put("立项经费", 0.0);
				
				proTypeMap.put(ptype, proMap);
				idsProTypeMap.put(ptype, new HashMap());
			}
			for(String specialArea : specialAreas){
				Map proTypeMap2 = new HashMap();
				Map idsProTypeMap2 = new HashMap();
				for(String ptype : projectTypes){
					Map proMap = new HashMap();
					proMap.put("申请数", 0);
					proMap.put("立项数", 0);
					proMap.put("立项比例", "0.0%");
					proMap.put("立项经费", 0.0);
					
					proTypeMap2.put(ptype, proMap);
					idsProTypeMap2.put(ptype, new HashMap());
				}
				proTypeMap.put(specialArea, proTypeMap2);
				idsProTypeMap.put(specialArea, idsProTypeMap2);
			}
			
			disMap.put("合计数", countMap);
			disMap.put("项目类型", proTypeMap);
			
			disTypeMap.put(disciplineType, disMap);
			
			idsDisTypeMap.put(disciplineType, idsProTypeMap);
		}
		
		//总计map
		Map totalMap = new HashMap();
		//合计数map
		Map countMap2 = new HashMap();
		countMap2.put("立项合计", 0);
		countMap2.put("立项总经费", 0.0);
		
		//项目类型map
		Map proTypeMap2 = new HashMap();
		for(String ptype : projectTypes){
			Map proMap = new HashMap();
			proMap.put("申请数", 0);
			proMap.put("立项数", 0);
			proMap.put("立项比例", "0.0%");
			proMap.put("立项经费", 0.0);
			
			proTypeMap2.put(ptype, proMap);
		}
		for(String specialArea : specialAreas){
			Map proTypeMap3 = new HashMap();
			for(String ptype : projectTypes){
				Map proMap = new HashMap();
				proMap.put("申请数", 0);
				proMap.put("立项数", 0);
				proMap.put("立项比例", "0.0%");
				proMap.put("立项经费", 0.0);
				
				proTypeMap3.put(ptype, proMap);
			}
			proTypeMap2.put(specialArea, proTypeMap3);
		}
		totalMap.put("合计数", countMap2);
		totalMap.put("项目类型", proTypeMap2);
		
		//自筹map
		Map zcMap = new HashMap();
		//总计map
		Map countMap3 = new HashMap();
		countMap3.put("自筹申请数", 0);
		countMap3.put("立项比例", "0.0%");
		countMap3.put("立项数", 0);
		
		//学科门类map
		
		//初始化各个学科
		Map zcDisTypeMap = new HashMap();
		Map zcIdsMap = new HashMap();
		for(String disciplineType : disTypeList){
			Map zcdisMap = new HashMap();
			zcdisMap.put("自筹申请数", 0);
			zcdisMap.put("立项比例", "0.0%");
			zcdisMap.put("立项数", 0);
			zcDisTypeMap.put(disciplineType, zcdisMap);
			zcIdsMap.put(disciplineType, new HashMap());
		}
		zcMap.put("总计", countMap3);
		zcMap.put("学科门类", zcDisTypeMap);
		
		resultMap.put("学科门类", disTypeMap);
		resultMap.put("总计", totalMap);
		resultMap.put("自筹项目", zcMap);
		
		idsMap.put("学科门类", idsDisTypeMap);
		idsMap.put("自筹项目", zcIdsMap);
		
		dataMap.put(FinanceService.CLIENTDATA, resultMap);
		dataMap.put(FinanceService.IDSDATA, idsMap);
		
		return dataMap;
	}
	
	/**
	 * 经费核算初始化
	 */
	@SuppressWarnings("unchecked")
	public Map init(int year, double planFee, double youthFee){
		//设置量
		String[] projectTypes = new String[] {"规划基金项目", "青年基金项目"};
		String[] zichouTypes = new String[] {"自筹经费项目"};
		this.year = year;//供后续s用
		//year = 2010;
		
		//为减少访问数据库消耗，采用一次访问数据库
		Map parMap = new HashMap();
		parMap.put("year", year);
		String proTypes = "(" + join(projectTypes) + ")";
		String zcTypes = "(" + join(zichouTypes) + ")";
		// 规划和青年项目
		List projectList = this.query("select p.disciplineType, p.projectType, p.applyFee, p.isGranting, u.provinceName, " +
			"p.id from ProjectApplication p, University u where p.type = 'general' and p.year = :year and p.projectType in " + proTypes + 
			" and p.universityCode = u.code and p.isReviewable <> 0 order by p.disciplineType", parMap);
		// 自筹项目
		List zcList = this.query("select p.disciplineType, p.projectType, p.applyFee, p.isGranting, u.provinceName, " +
				"p.id from ProjectApplication p, University u where p.type = 'general' and p.year = :year and p.projectType in " + zcTypes + 
				" and p.universityCode = u.code and p.isReviewable <> 0 order by p.disciplineType", parMap);
			
		List disTypeList = this.query("select p.disciplineType from ProjectApplication p where p.type = 'general' group by p.disciplineType");
		
		Map dataMap = initMap(disTypeList, projectTypes);//初始化容器map
		Map resultMap = (Map)dataMap.get(FinanceService.CLIENTDATA);
		Map idsMap = (Map)dataMap.get(FinanceService.IDSDATA);
		
		//解析所需数据
		for(Object o : projectList){
			Object[] oo = (Object[])o;
			String disType = (String)oo[0], proType = (String)oo[1];
			double applyFee = Double.parseDouble((String)oo[2]);
			applyFee = getRevisedFee(proType, applyFee, planFee, youthFee);
			int isGranting = (Integer)oo[3];
			String provinceName = (String)oo[4];
			String proId = (String)oo[5];//项目id
			
			Map idsDisTypeMap = (Map)idsMap.get("学科门类");//ids缓存容器
			Map idsDisMap = (Map)idsDisTypeMap.get(disType);
			
			/**运算引擎*/
			Map disTypeMap = (Map)resultMap.get("学科门类");
			Map totalMap = (Map)resultMap.get("总计");
			
			//合计map
			Map tcountMap = (Map)totalMap.get("合计数");
			Map tproTypeMap = (Map)totalMap.get("项目类型");
			
			Map disMap = (Map)disTypeMap.get(disType);
			Map proTypeMap = (Map)disMap.get("项目类型");
			Map countMap = (Map)disMap.get("合计数");
			
			//1.对应项目类型申请数始终累加1
			Map tproMap = (Map)tproTypeMap.get(proType);// 总计-项目类型-规划/青年
			tproMap.put("申请数", (Integer)tproMap.get("申请数") + 1);//更新
			
			Map proMap = (Map)proTypeMap.get(proType);
			proMap.put("申请数", (Integer)proMap.get("申请数") + 1);//更新
			
			//特殊地区立项数算在一般项目中
			if(isSpeciaclArea(provinceName)){
				//特殊地区也要加入一般项目
				if(isGranting > 0){//该项目拟立项
					tproMap.put("立项数", (Integer)tproMap.get("立项数") + 1);
					tproMap.put("立项经费", formatFee((Double)tproMap.get("立项经费") + applyFee));
					proMap.put("立项数", (Integer)proMap.get("立项数") + 1);
					proMap.put("立项经费", formatFee((Double)proMap.get("立项经费") + applyFee));
				}
				
				String area = ("新疆维吾尔自治区".equals(provinceName)) ? "新疆维吾尔自治区" : (("西藏自治区".equals(provinceName)) ? "西藏自治区" : "西部");
				//总
				Map tproTypeMap2 = new HashMap();
				tproTypeMap2 = (Map)tproTypeMap.get(area);// 总计-项目类型-西部/新疆/西藏
				Map tproMap2 = (Map)tproTypeMap2.get(proType);// 总计-项目类型-西部/新疆/西藏-规划/青年
				//单
				Map proTypeMap2 = new HashMap();
				proTypeMap2 = (Map)proTypeMap.get(area);
				Map proMap2 = (Map)proTypeMap2.get(proType);
				//更新相关信息
				tproMap2.put("申请数", (Integer)tproMap2.get("申请数") + 1);
				proMap2.put("申请数", (Integer)proMap2.get("申请数") + 1);
				
				//初始化ids缓存信息
				Map idsProTypeMap2 = (Map)idsDisMap.get(area);
				Map idsProMap = (Map)idsProTypeMap2.get(proType);
				Map idsProTypeMap = (Map)idsDisMap.get(proType);//一般项目包含特殊地区，但是id在idsMap里只维护一份
				
				if(isGranting > 0){//该项目拟立项
					tproMap2.put("立项数", (Integer)tproMap2.get("立项数") + 1);
					tproMap2.put("立项经费", formatFee((Double)tproMap2.get("立项经费") + applyFee));
					proMap2.put("立项数", (Integer)proMap2.get("立项数") + 1);
					proMap2.put("立项经费", formatFee((Double)proMap2.get("立项经费") + applyFee));
					
					//放置缓存id
					idsProMap.put(proId, isGranting);
				}
				tproMap2.put("立项比例", getGrantedRate((Integer)tproMap2.get("申请数"), (Integer)tproMap2.get("立项数")));
				proMap2.put("立项比例", getGrantedRate((Integer)proMap2.get("申请数"), (Integer)proMap2.get("立项数")));
				//更新容器内容
				tproTypeMap2.put(proType, tproMap2);
				tproTypeMap.put(area, tproTypeMap2);
				
				proTypeMap2.put(proType, proMap2);
				proTypeMap.put(area, proTypeMap2);
				
				idsProTypeMap2.put(proType, idsProMap);
				idsDisMap.put(area, idsProTypeMap2);
				idsDisMap.put(proType, idsProTypeMap);//一般项目的idsMap不变
				
			} else {
				Map idsProTypeMap = (Map)idsDisMap.get(proType);
				
				//更新相关信息
				if(isGranting > 0){//该项目拟立项
					tproMap.put("立项数", (Integer)tproMap.get("立项数") + 1);
					tproMap.put("立项经费", formatFee((Double)tproMap.get("立项经费") + applyFee));
					proMap.put("立项数", (Integer)proMap.get("立项数") + 1);
					proMap.put("立项经费", formatFee((Double)proMap.get("立项经费") + applyFee));
					
					//放置缓存id
					idsProTypeMap.put(proId, isGranting);
				}
				tproMap.put("立项比例", getGrantedRate((Integer)tproMap.get("申请数"), (Integer)tproMap.get("立项数")));
				proMap.put("立项比例", getGrantedRate((Integer)proMap.get("申请数"), (Integer)proMap.get("立项数")));
				//更新容器内容
				tproTypeMap.put(proType, tproMap);
				proTypeMap.put(proType, proMap);
				
				idsDisMap.put(proType, idsProTypeMap);//更新一般项目idsMap
			}
			
			//如果是立项项目，则更新合计map
			if(isGranting > 0){//该项目拟立项
				//单学科经费
				countMap.put("立项合计", (Integer)countMap.get("立项合计") + 1);
				countMap.put("立项总经费", formatFee((Double)countMap.get("立项总经费") + applyFee));
				
				//总经费
				tcountMap.put("立项合计", (Integer)tcountMap.get("立项合计") + 1);
				tcountMap.put("立项总经费", formatFee((Double)tcountMap.get("立项总经费") + applyFee));
			}
			
			//更新学科门类容器
			disMap.put("项目类型", proTypeMap);
			disMap.put("合计数", countMap);
			disTypeMap.put(disType, disMap);
			
			//更新总计类容器
			totalMap.put("项目类型", tproTypeMap);
			totalMap.put("合计数", tcountMap);
			
			
			resultMap.put("学科门类", disTypeMap);
			resultMap.put("总计", totalMap);
			
			idsDisTypeMap.put(disType, idsDisMap);
			idsMap.put("学科门类", idsDisTypeMap);
			
		}
		
		// select p.disciplineType, p.projectType, p.applyFee, p.isGranting, u.provinceName, p.id from General p, University u 
		//自筹项目
		for(Object o : zcList){
			Object[] oo = (Object[])o;
			String disType = (String)oo[0];
			int isGranting = (Integer)oo[3];
			String proId = (String)oo[5];//项目id
			
			//初始化ids缓存信息
			Map zcIdMap = (Map)idsMap.get("自筹项目");//ids缓存容器
			Map idsDisMap = (Map)zcIdMap.get(disType);
			
			/**运算引擎*/
			Map zcMap = (Map)resultMap.get("自筹项目");
//			Map disTypeMap = (Map)resultMap.get("学科门类");
//			Map totalMap = (Map)resultMap.get("总计");
			
			Map tcountMap = (Map)zcMap.get("总计");
			Map disTypeMap = (Map)zcMap.get("学科门类");
			Map disMap = (Map)disTypeMap.get(disType);
			
			//1.对应项目类型申请数始终累加1
			tcountMap.put("自筹申请数", (Integer)tcountMap.get("自筹申请数") + 1);
			tcountMap.put("立项比例", getGrantedRate((Integer)tcountMap.get("自筹申请数"), (Integer)tcountMap.get("立项数")));
			
			disMap.put("自筹申请数", (Integer)disMap.get("自筹申请数") + 1);
			disMap.put("立项比例", getGrantedRate((Integer)disMap.get("自筹申请数"), (Integer)disMap.get("立项数")));
			
			if(isGranting > 0){//该项目拟立项
				tcountMap.put("立项数", (Integer)tcountMap.get("立项数") + 1);
				disMap.put("立项数", (Integer)disMap.get("立项数") + 1);//放置缓存id
				idsDisMap.put(proId, isGranting);
			}
			
			//更新学科门类容器
			disTypeMap.put(disType, disMap);
			
			//更新自筹项目容器
			zcMap.put("总计", tcountMap);
			zcMap.put("学科门类", disTypeMap);
			
			resultMap.put("自筹项目", zcMap);
			
			zcIdMap.put(disType, idsDisMap);
			idsMap.put("自筹项目", zcIdMap);
		}
		
		//更新总容器
		dataMap.put(FinanceService.CLIENTDATA, resultMap);
		dataMap.put(FinanceService.IDSDATA, idsMap);
		
		return dataMap;
		
	}
	
	/**
	 * 通过基本硬性条件
	 * 1.得票数至少是3票以上
	 */
	private boolean isBasedPassing(int voteNumber){
		return (voteNumber >= 3) ? true : false;
	}
	
	/**
	 * 根据申请数、立项比例算出最小立项数
	 */
	private int getMinGrantedNumber(int appNum, double rate){
		return (int)(appNum * rate / 100.0);
	}
	
	/**
	 * 将list转换成数组
	 */
	private String[] castToArray(List<String> list){
		String[] temp = new String[list.size()];
		for(int i = 0; i < list.size(); i++){
			temp[i] = list.get(i);
		}
		return temp;
	}
	
	/**
	 * 将Object数组转换成String数组
	 */
	private String[] castToArray(Object[] arr){
		String[] temp = new String[arr.length];
		for(int i = 0; i < arr.length; i++){
			temp[i] = (String)arr[i];
		}
		return temp;
	}
	
	/**
	 * 比例核算时，初始化核算map（保留申请数信息，其它清零）
	 */
	@SuppressWarnings("unchecked")
	private Map initCheckedMap(Map resultMap){
		// step1: 学科门类
		Map disTypeMap = (Map)resultMap.get("学科门类");
		
		Iterator<String> it = disTypeMap.keySet().iterator();
		while(it.hasNext()){
			String disType = it.next();
			Map disMap = (Map)disTypeMap.get(disType);
			
			Map countMap = (Map)disMap.get("合计数");
			countMap.put("立项合计", 0);
			countMap.put("立项总经费", 0.0);
			
			Map proTypeMap = (Map)disMap.get("项目类型");
			Iterator<String> it2 = proTypeMap.keySet().iterator();
			while(it2.hasNext()){
				String proType = it2.next();
				//如果是特殊地区
				if("西部".equals(proType) || "新疆维吾尔自治区".equals(proType) || "西藏自治区".equals(proType)){
					Map proTypeMap2 = (Map)proTypeMap.get(proType);
					Iterator<String> it3 = proTypeMap2.keySet().iterator();
					while(it3.hasNext()){
						String proType2 = it3.next();
						Map proMap = (Map)proTypeMap2.get(proType2);
						
						proMap.put("立项数", 0);
						proMap.put("立项比例", "0.0%");
						proMap.put("立项经费", 0.0);
						
						proTypeMap2.put(proType2, proMap);
					}
					proTypeMap.put(proType, proTypeMap2);
				} else {
					Map proMap = (Map)proTypeMap.get(proType);
					
					proMap.put("立项数", 0);
					proMap.put("立项比例", "0.0%");
					proMap.put("立项经费", 0.0);
					
					proTypeMap.put(proType, proMap);
				}
			}
			disMap.put("项目类型", proTypeMap);
			disMap.put("合计数", countMap);
			
			disTypeMap.put(disType, disMap);
		}

		// step2: 总计
		Map totalMap = (Map)resultMap.get("总计");
		
		Map tcountMap = (Map)totalMap.get("合计数");
		tcountMap.put("立项合计", 0);
		tcountMap.put("立项总经费", 0.0);
		
		Map tproTypeMap = (Map)totalMap.get("项目类型");
		Iterator<String> tit = tproTypeMap.keySet().iterator();
		while(tit.hasNext()){
			String proType = tit.next();
			//如果是特殊地区
			if("西部".equals(proType) || "新疆维吾尔自治区".equals(proType) || "西藏自治区".equals(proType)){
				Map tproTypeMap2 = (Map)tproTypeMap.get(proType);
				Iterator<String> tit2 = tproTypeMap2.keySet().iterator();
				while(tit2.hasNext()){
					String proType2 = tit2.next();
					Map tproMap = (Map)tproTypeMap2.get(proType2);
					
					tproMap.put("立项数", 0);
					tproMap.put("立项比例", "0.0%");
					tproMap.put("立项经费", 0.0);
					
					tproTypeMap2.put(proType2, tproMap);
				}
				tproTypeMap.put(proType, tproTypeMap2);
			} else {
				Map tproMap = (Map)tproTypeMap.get(proType);
				
				tproMap.put("立项数", 0);
				tproMap.put("立项比例", "0.0%");
				tproMap.put("立项经费", 0.0);
				
				tproTypeMap.put(proType, tproMap);
			}
		}
		totalMap.put("项目类型", tproTypeMap);
		totalMap.put("合计数	", tcountMap);
		
		// step3: 自筹项目
		Map zcMap = (Map)resultMap.get("自筹项目");
		
		Map zccountMap = (Map)zcMap.get("总计");
		Map zcDisTypeMap = (Map)zcMap.get("学科门类");
		zccountMap.put("立项比例", "0.0%");
		zccountMap.put("立项数", 0);
		Iterator<String> zc = zcDisTypeMap.keySet().iterator();
		while(zc.hasNext()){
			String disType = zc.next();
			Map zcDisMap = (Map)zcDisTypeMap.get(disType);
			zcDisMap.put("立项比例", "0.0%");
			zcDisMap.put("立项数", 0);
			zcDisTypeMap.put(disType, zcDisMap);
		}
		zcMap.put("总计", zccountMap);
		zcMap.put("学科门类", zcDisTypeMap);
		
		// step4: 拼装结果
		resultMap.put("学科门类", disTypeMap);
		resultMap.put("总计", totalMap);
		resultMap.put("自筹项目", zcMap);
		
		return resultMap;
	}
	
	
	/**
	 * 根据立项比例、西部、新疆、新疆立项下阀值、宏观核算立项经费
	 * 
	 * 1. 查找所有项目的票数分数经费等信息存入projectList
	 * 2. 遍历项目list，如果符合条件则更新proMap和idsMap
	 * 3. 判断条件为满足硬性条件&&这批项目（同票数分数）加上后不超过设定的指标
	 * 4. 设定的指标：rate为一般项目每个学科立项比例，count为特殊地区所有学科立项数之和
	 */
	@SuppressWarnings("unchecked")
	public Map checkByRate(Map dataMap, Map setMap){
		List<String> disTypeList = this.query("select p.disciplineType from ProjectApplication p where p.type = 'general' group by p.disciplineType");

		//设置量
		String[] projectTypes = new String[] {"规划基金项目", "青年基金项目"};
		String[] zcprojectType = new String[] {"自筹经费项目"};
		
		//存储是否到达分数线的标志位 和 每类项目（一般项目存学科，特殊地区存地区）在线上的项目数量
		Map tmpMap = new HashMap();
		
		//存储j个同分同票项目中西部新疆西藏一般的数量，作为判断是否达标的标准
		//一般项目学科map和特殊地区地区map混合存储
		Map scoreVoteMap = new HashMap();
		Map scoreCountMap = new HashMap();//存储此分数线上各学科、地区已考虑的项目数
		Map flagMap = new HashMap();
		
		Map planFlagMap = new HashMap();Map youthFlagMap = new HashMap();
		Map xbFlagMap = new HashMap();Map xjFlagMap = new HashMap();Map xzFlagMap = new HashMap();
		xbFlagMap.put("规划基金项目", true);xbFlagMap.put("青年基金项目", true);
		xjFlagMap.put("规划基金项目", true);xjFlagMap.put("青年基金项目", true);
		xzFlagMap.put("规划基金项目", true);xzFlagMap.put("青年基金项目", true);
		
		for(String disType : disTypeList){
			Map scoreProTypeMap = new HashMap();
			scoreProTypeMap.put("规划基金项目", 0);
			scoreProTypeMap.put("青年基金项目", 0);
			scoreVoteMap.put(disType, scoreProTypeMap);
		}
		for(String specialArea : specialAreas){
			Map scoreProTypeMap = new HashMap();
			scoreProTypeMap.put("规划基金项目", 0);
			scoreProTypeMap.put("青年基金项目", 0);
			scoreVoteMap.put(specialArea, scoreProTypeMap);
		}
		for(String disType : disTypeList){
			Map scoreProTypeMap = new HashMap();
			scoreProTypeMap.put("规划基金项目", 0);
			scoreProTypeMap.put("青年基金项目", 0);
			scoreCountMap.put(disType, scoreProTypeMap);
		}
		for(String specialArea : specialAreas){
			Map scoreProTypeMap = new HashMap();
			scoreProTypeMap.put("规划基金项目", 0);
			scoreProTypeMap.put("青年基金项目", 0);
			scoreCountMap.put(specialArea, scoreProTypeMap);
		}
		
		for(String disciplineType : disTypeList){
			planFlagMap.put(disciplineType, true);
			youthFlagMap.put(disciplineType, true);
		}
		flagMap.put("规划基金项目", planFlagMap);flagMap.put("青年基金项目", youthFlagMap);//项目更新标志位
		flagMap.put("西部", xbFlagMap);flagMap.put("新疆维吾尔自治区", xjFlagMap);flagMap.put("西藏自治区", xzFlagMap);
		
		tmpMap.put("分数线总项目数", scoreVoteMap);
		tmpMap.put("分数线已考虑项目数", scoreCountMap);
		tmpMap.put("标志位", flagMap);
		//为减少访问数据库消耗，采用一次访问数据库
		Map parMap = new HashMap();
		parMap.put("year", year);
		String proTypes = "(" + join(projectTypes) + ")";
		String zcproType = "(" + join(zcprojectType) + ")";
		
		// 处理总计和项目类型
		List projectList = this.query("select p.disciplineType, p.projectType, p.applyFee, p.isGranting, u.provinceName, " +
				"p.voteNumber, p.score, p.id from ProjectApplication p, University u where p.type = 'general' and p.year = :year and p.projectType in " + proTypes + 
				" and p.universityCode = u.code and p.isReviewable <> 0 order by p.voteNumber desc, p.score desc", parMap);
		
		//Map idsMap = (Map)dataMap.get(FinanceService.IDSDATA);//ids缓存
		Map idsMap = (Map)initMap(disTypeList, projectTypes).get(FinanceService.IDSDATA);//清空ids缓存
		//Map resultMap = (Map)dataMap.get(FinanceService.CLIENTDATA);
		Map resultMap = initCheckedMap((Map)dataMap.get(FinanceService.CLIENTDATA));
		
		Map disTypeMap = (Map)resultMap.get("学科门类");
		Map totalMap = (Map)resultMap.get("总计");
		Map tcountMap = (Map)totalMap.get("合计数");
		Map tproTypeMap = (Map)totalMap.get("项目类型");
		
		Map idsDisTypeMap = (Map)idsMap.get("学科门类");
		int j = 1;//从第i+1个项目开始票数和分数相同的项目数，初始值为0
		//解析所需数据
		for(int i = 0; i < projectList.size(); ){
			Object[] oo = (Object[])projectList.get(i);
			String disType = (String)oo[0], proType = (String)oo[1];
			double applyFee = Double.parseDouble((String)oo[2]);
			int isGranting = (Integer)oo[3];
			applyFee = getRevisedFee(proType, applyFee, (Double)setMap.get("规划项目经费"), (Double)setMap.get("青年项目经费"));
			String provinceName = (String)oo[4];
			int voteNumber = (Integer)oo[5];
			double score = (Double)oo[6];
			String proId = (String)oo[7];
			
			for(int k = 0; k < j; k++){//更新每类项目在分数线上的学科数
				oo = (Object[])projectList.get(i + k);
				disType = (String)oo[0];
				proType = (String)oo[1];
				provinceName = (String)oo[4];
				if(isSpeciaclArea(provinceName)){
					String area = ("新疆维吾尔自治区".equals(provinceName)) ? "新疆维吾尔自治区" : (("西藏自治区".equals(provinceName)) ? "西藏自治区" : "西部");
					scoreVoteMap = (Map) tmpMap.get("分数线总项目数");
					Map scoreProTypeMap = (Map) scoreVoteMap.get(area);
					scoreProTypeMap.put(proType, (Integer)scoreProTypeMap.get(proType) + 1);
					scoreVoteMap.put(area, scoreProTypeMap);
					tmpMap.put("分数线总项目数", scoreVoteMap);
				} else {
					scoreVoteMap = (Map) tmpMap.get("分数线总项目数");
					Map scoreProTypeMap = (Map) scoreVoteMap.get(disType);
					scoreProTypeMap.put(proType, (Integer)scoreProTypeMap.get(proType) + 1);
					scoreVoteMap.put(disType, scoreProTypeMap);
					tmpMap.put("分数线总项目数", scoreVoteMap);
				}
			}
			
			for(int k = 0; k < j; k++){
				//抽取出这j个同分同票的项目，分类（一般或者特殊地区）判断
				
				oo = (Object[])projectList.get(i + k);
				disType = (String)oo[0];
				proType = (String)oo[1];
				applyFee = Double.parseDouble((String)oo[2]);
				applyFee = getRevisedFee(proType, applyFee, (Double)setMap.get("规划项目经费"), (Double)setMap.get("青年项目经费"));
				isGranting = (Integer)oo[3];
				provinceName = (String)oo[4];
				voteNumber = (Integer)oo[5];//这j个相同
				score = (Double)oo[6];//这j个相同
				proId = (String)oo[7];
				
				//学科map
				Map disMap = (Map)disTypeMap.get(disType);
				//合计数map
				Map countMap = (Map)disMap.get("合计数");
				//项目类型map
				Map proTypeMap = (Map)disMap.get("项目类型");
				
				Map idsDisMap = (Map)idsDisTypeMap.get(disType);
				// lastVote和lastScore供判断分数线以处理相同得票数和分数的项目
				// lastFlag 用来判断此地区此类型的项目是否需要更新，分数线上的项目正常处理，分数线以下只处理拟立项为2的项目
				boolean lastFlag;
				if(isSpeciaclArea(provinceName)){
					String area = ("新疆维吾尔自治区".equals(provinceName)) ? "新疆维吾尔自治区" : (("西藏自治区".equals(provinceName)) ? "西藏自治区" : "西部");
					lastFlag = (Boolean)((Map)((Map)tmpMap.get("标志位")).get(area)).get(proType);
				} else {
					lastFlag = (Boolean)((Map)((Map)tmpMap.get("标志位")).get(proType)).get(disType);
				}
				if(lastFlag){//更新isGranting == 1的情况
					//如果是西部、新疆、西藏项目
					if(isSpeciaclArea(provinceName)){
						String area = ("新疆维吾尔自治区".equals(provinceName)) ? "新疆维吾尔自治区" : (("西藏自治区".equals(provinceName)) ? "西藏自治区" : "西部");
						Map tproTypeMap2 = (Map)tproTypeMap.get(area);
						Map tproTypeMap1 = (Map)tproTypeMap.get(proType);
						Map proTypeMap2 = (Map)proTypeMap.get(area);
						Map proTypeMap1 = (Map)proTypeMap.get(proType);
						
						//项目map
						Map tproMap = (Map)tproTypeMap2.get(proType);
						Map proMap = (Map)proTypeMap2.get(proType);
						
						//获取最少立项数参数-------------------------------
						Map setMap1 = (Map)setMap.get(area);
						double rate = (Double)setMap.get(proType);
						int minGrantNum1 = (Integer)setMap1.get(proType);
						int minGrantNum2 = getMinGrantedNumber((Integer)tproMap.get("申请数"), rate);
						int minGrantNum = (minGrantNum1 > minGrantNum2) ? minGrantNum1 : minGrantNum2;
						// 此地区分数线上的项目数
						int scoreVoteNumber = (Integer)((Map)((Map)tmpMap.get("分数线总项目数")).get(area)).get(proType);
						
						//刷新缓存信息
						Map idsProTypeMap2 = (Map)idsDisMap.get(area);
						Map idsProMap = (Map)idsProTypeMap2.get(proType);
						Map idsProTypeMap = (Map)idsDisMap.get(proType);//特殊地区项目，不更新一般项目idsMap
						// 计算分数线已考虑项目数
						Integer scoreCount = (Integer)((Map)((Map)tmpMap.get("分数线已考虑项目数")).get(area)).get(proType);
						
						if(isBasedPassing(voteNumber) && (Integer)tproMap.get("立项数") + scoreVoteNumber - scoreCount <= minGrantNum ){//硬性条件比最小立项数优先级高
							// 分数线已考虑项目数 + 1
							scoreCountMap = (Map)tmpMap.get("分数线已考虑项目数");
							Map scoreProTypeMap = (Map)scoreCountMap.get(area);
							scoreProTypeMap.put(proType, (Integer)scoreProTypeMap.get(proType) + 1);
							scoreCountMap.put(area, scoreProTypeMap);
							tmpMap.put("分数线已考虑项目数", scoreCountMap);
							
							//总合计数更新
							tcountMap.put("立项合计", (Integer)tcountMap.get("立项合计") + 1);
							tcountMap.put("立项总经费", formatFee((Double)tcountMap.get("立项总经费") + applyFee));
							
							// 学科合计数更新
							countMap.put("立项合计", (Integer)countMap.get("立项合计") + 1);
							countMap.put("立项总经费", formatFee((Double)countMap.get("立项总经费") + applyFee));
							
							//一般项目总计更新
							tproTypeMap1.put("立项数", (Integer)tproTypeMap1.get("立项数") + 1);
							tproTypeMap1.put("立项经费", formatFee((Double)tproTypeMap1.get("立项经费") + applyFee));
							tproTypeMap1.put("立项比例", getGrantedRate((Integer)tproTypeMap1.get("申请数"), (Integer)tproTypeMap1.get("立项数")));
							
							//一般项目学科更新
							proTypeMap1.put("立项数", (Integer)proTypeMap1.get("立项数") + 1);
							proTypeMap1.put("立项经费", formatFee((Double)proTypeMap1.get("立项经费") + applyFee));
							proTypeMap1.put("立项比例", getGrantedRate((Integer)proTypeMap1.get("申请数"), (Integer)proTypeMap1.get("立项数")));
							
							// 当前地区总计更新
							tproMap.put("立项数", (Integer)tproMap.get("立项数") + 1);
							tproMap.put("立项经费", formatFee((Double)tproMap.get("立项经费") + applyFee));
							tproMap.put("立项比例", getGrantedRate((Integer)tproMap.get("申请数"), (Integer)tproMap.get("立项数")));
							
							// 当前地区学科更新
							proMap.put("立项数", (Integer)proMap.get("立项数") + 1);
							proMap.put("立项经费", formatFee((Double)proMap.get("立项经费") + applyFee));
							proMap.put("立项比例", getGrantedRate((Integer)proMap.get("申请数"), (Integer)proMap.get("立项数")));
							
							//缓存id
							idsProMap.put(proId, 1);
							
							//更新map
							tproTypeMap.put(proType, tproTypeMap1);
							proTypeMap.put(proType, proTypeMap1);
							
							tproTypeMap2.put(proType, tproMap);
							tproTypeMap.put(area, tproTypeMap2);
							
							proTypeMap2.put(proType, proMap);
							proTypeMap.put(area, proTypeMap2);
							
							idsProTypeMap2.put(proType, idsProMap);
							idsDisMap.put(area, idsProTypeMap2);
							idsDisMap.put(proType, idsProTypeMap);
							
						} else {//设置此类（地区+项目类型）的更新标志位为true
							if(area == "西部"){
								xbFlagMap.put(proType, false);
								flagMap.put(area, xbFlagMap);
							} else if(area == "新疆维吾尔自治区"){
								xjFlagMap.put(proType, false);
								flagMap.put(area, xjFlagMap);
							} else if (area == "西藏自治区"){
								xzFlagMap.put(proType, false);
								flagMap.put(area, xzFlagMap);
							}
							tmpMap.put("标志位", flagMap);
						}//此处预留特别项目立项判决
					} else {
						double rate = (Double)setMap.get(proType);
						Map tproMap = (Map)tproTypeMap.get(proType);
						Map proMap = (Map)proTypeMap.get(proType);
						
						int minGrantNum = getMinGrantedNumber((Integer)proMap.get("申请数"), rate);
						//此学科分数线上非特殊地区项目数
						int scoreVoteNumber = (Integer)((Map)((Map)tmpMap.get("分数线总项目数")).get(disType)).get(proType);
						// 此学科分数线上已考虑项目数
						Integer scoreCount = (Integer)((Map)((Map)tmpMap.get("分数线已考虑项目数")).get(disType)).get(proType);
						
						Map idsProTypeMap = (Map)idsDisMap.get(proType);
						
						if(isBasedPassing(voteNumber) && (Integer)proMap.get("立项数") + scoreVoteNumber - scoreCount <= minGrantNum ){//硬性条件比最小立项数优先级高
							// 分数线已考虑项目数 + 1
							scoreCountMap = (Map)tmpMap.get("分数线已考虑项目数");
							Map scoreProTypeMap = (Map)scoreCountMap.get(disType);
							scoreProTypeMap.put(proType, (Integer)scoreProTypeMap.get(proType) + 1);
							scoreCountMap.put(disType, scoreProTypeMap);
							tmpMap.put("分数线已考虑项目数", scoreCountMap);
							
							//总合计数更新
							tcountMap.put("立项合计", (Integer)tcountMap.get("立项合计") + 1);
							tcountMap.put("立项总经费", formatFee((Double)tcountMap.get("立项总经费") + applyFee));
							
							//合计数更新
							countMap.put("立项合计", (Integer)countMap.get("立项合计") + 1);
							countMap.put("立项总经费", formatFee((Double)countMap.get("立项总经费") + applyFee));
							
							//一般项目总计更新
							tproMap.put("立项数", (Integer)tproMap.get("立项数") + 1);
							tproMap.put("立项经费", formatFee((Double)tproMap.get("立项经费") + applyFee));
							tproMap.put("立项比例", getGrantedRate((Integer)tproMap.get("申请数"), (Integer)tproMap.get("立项数")));
							
							//一般项目学科更新
							proMap.put("立项数", (Integer)proMap.get("立项数") + 1);
							proMap.put("立项经费", formatFee((Double)proMap.get("立项经费") + applyFee));
							proMap.put("立项比例", getGrantedRate((Integer)proMap.get("申请数"), (Integer)proMap.get("立项数")));
							
							idsProTypeMap.put(proId, isGranting);
							
							//更新map
							tproTypeMap.put(proType, tproMap);
							proTypeMap.put(proType, proMap);
							
							idsDisMap.put(proType, idsProTypeMap);
							
						} else {//设置此类的更新标志位为true
							System.out.println(proType + " + " + disType + " + " + score + " + " + voteNumber);
							if(proType.equals("青年基金项目")){
								youthFlagMap.put(disType, false);
								flagMap.put(proType, youthFlagMap);
							}else if (proType.equals("规划基金项目")){
								planFlagMap.put(disType, false);
								flagMap.put(proType, planFlagMap);
							}
							tmpMap.put("标志位", flagMap);
						}//此处预留特别项目立项判决
						
					}
				} else {// 更新剩下pro中的isGranting == 2的情况
					if(isGranting == 2){
						if(isSpeciaclArea(provinceName)){
							String area = ("新疆维吾尔自治区".equals(provinceName)) ? "新疆维吾尔自治区" : (("西藏自治区".equals(provinceName)) ? "西藏自治区" : "西部");
							Map tproTypeMap2 = (Map)tproTypeMap.get(area);
							Map tproTypeMap1 = (Map)tproTypeMap.get(proType);
							Map proTypeMap2 = (Map)proTypeMap.get(area);
							Map proTypeMap1 = (Map)proTypeMap.get(proType);
							
							//项目map
							Map tproMap = (Map)tproTypeMap2.get(proType);
							Map proMap = (Map)proTypeMap2.get(proType);
							
							//刷新缓存信息
							Map idsProTypeMap2 = (Map)idsDisMap.get(area);
							Map idsProMap = (Map)idsProTypeMap2.get(proType);
							
							//总合计数更新
							tcountMap.put("立项合计", (Integer)tcountMap.get("立项合计") + 1);
							tcountMap.put("立项总经费", formatFee((Double)tcountMap.get("立项总经费") + applyFee));
							
							// 学科合计数更新
							countMap.put("立项合计", (Integer)countMap.get("立项合计") + 1);
							countMap.put("立项总经费", formatFee((Double)countMap.get("立项总经费") + applyFee));
							
							//一般项目总计更新
							tproTypeMap1.put("立项数", (Integer)tproTypeMap1.get("立项数") + 1);
							tproTypeMap1.put("立项经费", formatFee((Double)tproTypeMap1.get("立项经费") + applyFee));
							tproTypeMap1.put("立项比例", getGrantedRate((Integer)tproTypeMap1.get("申请数"), (Integer)tproTypeMap1.get("立项数")));
							
							//一般项目学科更新
							proTypeMap1.put("立项数", (Integer)proTypeMap1.get("立项数") + 1);
							proTypeMap1.put("立项经费", formatFee((Double)proTypeMap1.get("立项经费") + applyFee));
							proTypeMap1.put("立项比例", getGrantedRate((Integer)proTypeMap1.get("申请数"), (Integer)proTypeMap1.get("立项数")));
							
							// 当前地区总计更新
							tproMap.put("立项数", (Integer)tproMap.get("立项数") + 1);
							tproMap.put("立项经费", formatFee((Double)tproMap.get("立项经费") + applyFee));
							tproMap.put("立项比例", getGrantedRate((Integer)tproMap.get("申请数"), (Integer)tproMap.get("立项数")));
							
							// 当前地区学科更新
							proMap.put("立项数", (Integer)proMap.get("立项数") + 1);
							proMap.put("立项经费", formatFee((Double)proMap.get("立项经费") + applyFee));
							proMap.put("立项比例", getGrantedRate((Integer)proMap.get("申请数"), (Integer)proMap.get("立项数")));
							
							//缓存id
							idsProMap.put(proId, isGranting);
							
							//更新map
							tproTypeMap.put(proType, tproTypeMap1);
							proTypeMap.put(proType, proTypeMap1);
							
							tproTypeMap2.put(proType, tproMap);
							tproTypeMap.put(area, tproTypeMap2);
							
							proTypeMap2.put(proType, proMap);
							proTypeMap.put(area, proTypeMap2);
							
							idsProTypeMap2.put(proType, idsProMap);
							idsDisMap.put(area, idsProTypeMap2);
						}else {
							Map tproMap = (Map)tproTypeMap.get(proType);
							Map proMap = (Map)proTypeMap.get(proType);
							
							Map idsProTypeMap = (Map)idsDisMap.get(proType);
							
							//总合计数更新
							tcountMap.put("立项合计", (Integer)tcountMap.get("立项合计") + 1);
							tcountMap.put("立项总经费", formatFee((Double)tcountMap.get("立项总经费") + applyFee));
							
							//合计数更新
							countMap.put("立项合计", (Integer)countMap.get("立项合计") + 1);
							countMap.put("立项总经费", formatFee((Double)countMap.get("立项总经费") + applyFee));
							
							//一般项目总计更新
							tproMap.put("立项数", (Integer)tproMap.get("立项数") + 1);
							tproMap.put("立项经费", formatFee((Double)tproMap.get("立项经费") + applyFee));
							tproMap.put("立项比例", getGrantedRate((Integer)tproMap.get("申请数"), (Integer)tproMap.get("立项数")));
							
							//一般项目学科更新
							proMap.put("立项数", (Integer)proMap.get("立项数") + 1);
							proMap.put("立项经费", formatFee((Double)proMap.get("立项经费") + applyFee));
							proMap.put("立项比例", getGrantedRate((Integer)proMap.get("申请数"), (Integer)proMap.get("立项数")));
							
							idsProTypeMap.put(proId, isGranting);
							
							//更新map
							tproTypeMap.put(proType, tproMap);
							proTypeMap.put(proType, proMap);
							
							idsDisMap.put(proType, idsProTypeMap);
						}
					}
				}
				//更新
				disMap.put("项目类型", proTypeMap);
				disMap.put("合计数", countMap);
				disTypeMap.put(disType, disMap);
				totalMap.put("合计数", tcountMap);
				totalMap.put("项目类型", tproTypeMap);
				idsDisTypeMap.put(disType, idsDisMap);
			}
			//如果一次处理了j > 1个项目，则下一次的项目从i + j开始
			i = i + j;
			j = 1;
			if(i + j < projectList.size()){
				//重置同分同票项目数为0，再检查i + 1起有多少同分同票的
				Object[] newOo = (Object[])projectList.get(i);
				Object[] newOo1 = (Object[])projectList.get(i + j);
				//计算从第i + 1个项目开始，同票数和分数的项目有多少
				int newVoteNumber = (Integer)newOo[5], newVoteNumber1 = (Integer)newOo1[5];
				double newScore = (Double)newOo[6], newScore1 = (Double)newOo1[6];
				while(newVoteNumber == newVoteNumber1 && newScore == newScore1){
					j++;
					newOo1 = (Object[])projectList.get(i + j);
					newVoteNumber1 = (Integer)newOo1[5];
					newScore1 = (Double)newOo1[6];
				}
				if( i == 7 ){
					System.out.println(1);
				}
				System.out.println(i + "____" + j + "____" + newScore + "____" + newVoteNumber);
			}
			//清空此次判断中分数线上学科数和已考虑学科数
			for(String dis : disTypeList){
				Map scoreProTypeMap = new HashMap();
				scoreProTypeMap.put("规划基金项目", 0);
				scoreProTypeMap.put("青年基金项目", 0);
				scoreVoteMap.put(dis, scoreProTypeMap);
			}
			for(String specialArea : specialAreas){
				Map scoreProTypeMap = new HashMap();
				scoreProTypeMap.put("规划基金项目", 0);
				scoreProTypeMap.put("青年基金项目", 0);
				scoreVoteMap.put(specialArea, scoreProTypeMap);
			}
			for(String dis : disTypeList){
				Map scoreProTypeMap = new HashMap();
				scoreProTypeMap.put("规划基金项目", 0);
				scoreProTypeMap.put("青年基金项目", 0);
				scoreCountMap.put(dis, scoreProTypeMap);
			}
			for(String specialArea : specialAreas){
				Map scoreProTypeMap = new HashMap();
				scoreProTypeMap.put("规划基金项目", 0);
				scoreProTypeMap.put("青年基金项目", 0);
				scoreCountMap.put(specialArea, scoreProTypeMap);
			}
			tmpMap.put("分数线总项目数", scoreVoteMap);
			tmpMap.put("分数线已考虑项目数", scoreCountMap);
		}

		// 以下处理自筹项目
		Map zcMap = (Map)resultMap.get("自筹项目");
		Map zccountMap = (Map)zcMap.get("总计");
		Map zcdisTypeMap = (Map)zcMap.get("学科门类");
		Map zcIdsMap = (Map)idsMap.get("自筹项目");
		
		//自筹列表
		List zcList = this.query("select p.disciplineType, p.projectType, p.applyFee, p.isGranting, u.provinceName, " +
				"p.voteNumber, p.id from ProjectApplication p, University u where p.type = 'general' and p.year = :year and p.projectType in " + zcproType + 
				" and p.universityCode = u.code and p.isReviewable <> 0 order by p.voteNumber desc, p.score desc", parMap);
			
		for(int i = 0; i < zcList.size(); i++){
			Object[] oo = (Object[])zcList.get(i);
			String disType = (String)oo[0];
			int isGranting = (Integer)oo[3];
			int voteNumber = (Integer)oo[5];
			String proId = (String)oo[6];
			Map zcDisIdsMap = (Map)zcIdsMap.get(disType);
			
			if(isBasedPassing(voteNumber) && (Integer)zccountMap.get("立项数") < (Integer)setMap.get("自筹经费项目")){//硬性条件比最小立项数优先级高
				Map zcDisMap = (Map) zcdisTypeMap.get(disType);
				zcDisMap.put("立项数", (Integer)zcDisMap.get("立项数") + 1);
				zcDisMap.put("立项比例", getGrantedRate((Integer)zcDisMap.get("自筹申请数"), (Integer)zcDisMap.get("立项数")));
				zcdisTypeMap.put(disType, zcDisMap);//当前学科立项数+1 & 重新计算立项比例
				zccountMap.put("立项数", (Integer)zccountMap.get("立项数") + 1);//自筹项目总立项数+1
				zccountMap.put("立项比例", getGrantedRate((Integer)zccountMap.get("自筹申请数"), (Integer)zccountMap.get("立项数")));
				//缓存id
				zcDisIdsMap.put(proId, isGranting);
				zcIdsMap.put(disType, zcDisIdsMap);
			}
		}
		zcMap.put("总计", zccountMap);
		zcMap.put("学科门类", zcdisTypeMap);
		
		//拼装最后结果
		resultMap.put("学科门类", disTypeMap);
		resultMap.put("总计", totalMap);
		resultMap.put("自筹项目", zcMap);
		
		idsMap.put("学科门类", idsDisTypeMap);
		idsMap.put("自筹项目", zcIdsMap);
		
		dataMap.put(FinanceService.CLIENTDATA, resultMap);
		dataMap.put(FinanceService.IDSDATA, idsMap);
		
		return dataMap;
	}
	
	/**
	 * 初始化微观调整页面
	 * @param dataMap 数据容器
	 * @param disType 学科门类
	 * @param area 区域
	 * @param proType 项目类型
	 */
	@SuppressWarnings("unchecked")
	public List initCheckById(Map dataMap, String disType, String area, String proType){
		//设置量
		//int year = 2010;
		
		//为减少访问数据库消耗，采用一次访问数据库
		Map parMap = new HashMap();
		parMap.put("year", year);
		parMap.put("proType", proType);
		parMap.put("disType", disType);
		String conHql = "";
		if(isSpeciaclArea(area) || area.equals("西部")){//如果是特殊地区
			if("新疆维吾尔自治区".equals(area) || "西藏自治区".equals(area)){
				conHql = "and u.provinceName  = :area ";
				parMap.put("area", area);
			} else {
				conHql = "and u.provinceName  in (" + join(lib) + ") and u.provinceName <> '新疆维吾尔自治区' and u.provinceName <> '西藏自治区' " ;
			}
		} else {
			conHql = "";
			//conHql = " not in (" + join(lib) + ")";
		}
		
		List projectList = this.query("select p.id, p.projectName, p.director, u.name, '是否立项', p.voteNumber, p.score, u.provinceName, p.isGranting " +
			"from ProjectApplication p, University u where p.type = 'general' and p.year = :year and p.projectType = :proType and p.universityCode = u.code " + conHql +
			" and p.disciplineType = :disType and p.isReviewable <> 0 order by p.voteNumber desc, p.score desc", parMap);
		
		//重构项目列表
		Map idsMap = (Map)dataMap.get(FinanceService.IDSDATA);
		Map idsDisTypeMap = (Map)idsMap.get("学科门类");

		Map idsProTypeMap2 = new HashMap();//一般项目idsMap
		Map idsDisMap = (Map)idsDisTypeMap.get(disType);
		Map idsProMap = new HashMap();//特殊地区idsMap
		
		String proId = "";
		// 格式化列表中的数据（立项状态和特殊地区）
		for(int i = 0; i < projectList.size(); i++){
			Object[] oo = (Object[])projectList.get(i);
			proId = (String)oo[0];
			String provinceName = (String)oo[7];
			//在对应缓存ids中搜索
			if(isSpeciaclArea(provinceName)){
				//格式化立项状态，特殊地区项目在特殊地区idsMap中找
				area = ("新疆维吾尔自治区".equals(provinceName)) ? "新疆维吾尔自治区" : (("西藏自治区".equals(provinceName)) ? "西藏自治区" : "西部");
				Map idsProTypeMap = (Map)idsDisMap.get(area);
				idsProMap = (Map)idsProTypeMap.get(proType);
				if(null != idsProMap.get(proId) && 1 == (Integer)idsProMap.get(proId)){
					oo[4] = 1;
				} else if(null != idsProMap.get(proId) && 2 == (Integer)idsProMap.get(proId)){
					oo[4] = 2;
				} else {
					oo[4] = 0;
				}
				//格式化特殊地区
				provinceName = ("新疆维吾尔自治区".equals(provinceName)) ? "新疆维吾尔自治区" : (("西藏自治区".equals(provinceName)) ? "西藏自治区" : "西部");
				oo[7] = provinceName;
			} else {
				//格式化立项状态，一般项目在一般项目的idsMap中找
				idsProTypeMap2 = (Map)idsDisMap.get(proType);
				if(null != idsProTypeMap2.get(proId) && 1 == (Integer)idsProTypeMap2.get(proId)){
					oo[4] = 1;
				} else if(null != idsProTypeMap2.get(proId) && 2 == (Integer)idsProTypeMap2.get(proId)){
					oo[4] = 2;
				} else {
					oo[4] = 0;
				}
			}
			if(isSpeciaclArea(provinceName)){
				provinceName = ("新疆维吾尔自治区".equals(provinceName)) ? "新疆维吾尔自治区" : (("西藏自治区".equals(provinceName)) ? "西藏自治区" : "西部");
				oo[7] = provinceName;
			}
			projectList.set(i, oo);
		}
		return projectList;
	}
	
	/**
	 * 微观核算
	 * case1: 特殊地区的列表，首先清除此地区&学科&项目类型的idsMap和proMap，减去合计map里的立项数，立项比例和立项经费，
	 * 再检测新的项目list，逐一更新idsMap和proMap
	 * case2: 一般地区列表，首先清除此学科&项目类型的所有项目（包含一般项目和三类特殊地区的项目）idsMap和proMap，减去合计map里的立项数，
	 * 再逐一检查每个项目，如果是特殊地区项目，就更新此地区和一般项目的proMap，以及此地区的idsMap
	 */
	@SuppressWarnings("unchecked")
	public Map checkById(Map dataMap, List<String> idsList1, List<String> idsList2, String disType, String area, String proType, Double planFee, Double youthFee){
		//设置量
		//int year = 2010;
		
		//为减少访问数据库消耗，采用一次访问数据库
		Map parMap = new HashMap();
		parMap.put("year", year);
		parMap.put("proType", proType);
		parMap.put("disType", disType);
//		String conHql = "";
//		if(isSpeciaclArea(area) || area.equals("西部")){//如果是特殊地区
//			if("新疆维吾尔自治区".equals(area) || "西藏自治区".equals(area)){
//				conHql = " = :area ";
//				parMap.put("area", area);
//			} else {
//				conHql = " in (" + join(lib) + ") and u.provinceName <> '新疆' and u.provinceName <> '西藏' " ;
//			}
//		} else {
//			conHql = " not in (" + join(lib) + ")";
//		}
		
		String[] idsArr1 = castToArray(idsList1);//可能包含不存在id,以核对数据库为准
		String[] idsArr2 = castToArray(idsList2);
		String idsScope = "(" + join(idsArr1) + ", " + join(idsArr2) + ")";
		List projectList = this.query("select p.applyFee, p.voteNumber, u.provinceName, p.id from ProjectApplication p, University u " +
			"where p.type = 'general' and p.year = :year and p.projectType = :proType and p.universityCode = u.code and " +
			"p.disciplineType = :disType and p.id in " + idsScope + " and p.isReviewable <> 0 order by p.voteNumber desc, p.score desc", parMap);
		
		//更新resultMap
		Map resultMap = (Map)dataMap.get(FinanceService.CLIENTDATA);
		
		Map totalMap = (Map)resultMap.get("总计");
		Map disTypeMap = (Map)resultMap.get("学科门类");
		
		Map tcountMap = (Map)totalMap.get("合计数");
		Map tproTypeMap = (Map)totalMap.get("项目类型");
		
		Map disMap = (Map)disTypeMap.get(disType);
		Map countMap = (Map)disMap.get("合计数");
		Map proTypeMap = (Map)disMap.get("项目类型");
		
		Map tproTypeMap3 = (Map)tproTypeMap.get(proType);
		Map proTypeMap3 = (Map) proTypeMap.get(proType);
		
		// 			总和			一般				地区
		// 总计		tcountMap	tproTypeMap3	tproMap
		// 学科		countMap	proTypeMap3		proMap
		
		//更新缓存idsMap
		Map idsMap = (Map)dataMap.get(FinanceService.IDSDATA);
		Map idsDisTypeMap = (Map)idsMap.get("学科门类");
		
		Map idsDisMap = (Map)idsDisTypeMap.get(disType);
		
		// 先删除Map里旧的数据
		// 如果是特殊地区
		if(isSpeciaclArea(area) || area.equals("西部")){
			area = ("新疆维吾尔自治区".equals(area)) ? "新疆维吾尔自治区" : (("西藏自治区".equals(area)) ? "西藏自治区" : "西部");
			Map tproTypeMap2 = (Map)tproTypeMap.get(area);
			Map tproMap = (Map)tproTypeMap2.get(proType);
			Map proTypeMap2 = (Map) proTypeMap.get(area);
			Map proMap = (Map)proTypeMap2.get(proType);//西部-规划/青年
			
			//更新idsMap
			Map idsProTypeMap = (Map)idsDisMap.get(area);
			Map idsProMap = (Map)idsProTypeMap.get(proType);//特殊地区项目
			idsProMap.clear();
			idsProTypeMap.put(proType, idsProMap);
			idsDisMap.put(area, idsProTypeMap);
			idsDisTypeMap.put(disType, idsDisMap);
			idsMap.put("学科门类", idsDisTypeMap);
			
			tcountMap.put("立项合计", (Integer)tcountMap.get("立项合计") - (Integer)proMap.get("立项数"));
			tcountMap.put("立项总经费", formatFee((Double)tcountMap.get("立项总经费") - (Double)proMap.get("立项经费")));
			
			countMap.put("立项合计", (Integer)countMap.get("立项合计") - (Integer)proMap.get("立项数"));
			countMap.put("立项总经费", formatFee((Double)countMap.get("立项总经费") - (Double)proMap.get("立项经费")));
			
			tproTypeMap3.put("立项数", (Integer)tproTypeMap3.get("立项数")- (Integer)proMap.get("立项数"));
			tproTypeMap3.put("立项比例", getGrantedRate((Integer)tproTypeMap3.get("申请数"), (Integer)tproTypeMap3.get("立项数")));
			tproTypeMap3.put("立项经费", formatFee((Double)tproTypeMap3.get("立项经费") - (Double)proMap.get("立项经费")));
			
			//一般项目学科立项数&立项经费
			proTypeMap3.put("立项数", (Integer)proTypeMap3.get("立项数")- (Integer)proMap.get("立项数"));
			proTypeMap3.put("立项比例", getGrantedRate((Integer)proTypeMap3.get("申请数"), (Integer)proTypeMap3.get("立项数")));
			proTypeMap3.put("立项经费", formatFee((Double)proTypeMap3.get("立项经费") - (Double)proMap.get("立项经费")));
			
			tproMap.put("立项数", (Integer)tproMap.get("立项数") - (Integer)proMap.get("立项数"));
			tproMap.put("立项比例", getGrantedRate((Integer)tproMap.get("申请数"), (Integer)tproMap.get("立项数")));
			tproMap.put("立项经费", formatFee((Double)tproMap.get("立项经费") - (Double)proMap.get("立项经费")));
			
			proMap.put("立项数", 0);
			proMap.put("立项比例", getGrantedRate((Integer)proMap.get("申请数"), (Integer)proMap.get("立项数")));
			proMap.put("立项经费", 0.0);//立项经费清零//更新容器
			//更新容器
			proTypeMap2.put(proType, proMap);
			tproTypeMap2.put(proType, tproMap);
			proTypeMap.put(proType, proTypeMap3);
			tproTypeMap.put(proType, tproTypeMap3);
			proTypeMap.put(area, proTypeMap2);
			tproTypeMap.put(area, tproTypeMap2);
		} else {
			//清空idsMap
			Map idsProTypeMap2 = (Map)idsDisMap.get(proType);//一般项目
			idsProTypeMap2.clear();
			
			idsDisMap.put(proType, idsProTypeMap2);
			idsDisTypeMap.put(disType, idsDisMap);
			idsMap.put("学科门类", idsDisTypeMap);
			Map idsXbMap = (Map)idsDisMap.get("西部");
			Map idsXjMap = (Map)idsDisMap.get("新疆维吾尔自治区");
			Map idsXzMap = (Map)idsDisMap.get("西藏自治区");
			Map idsProMap1 = (Map)idsXbMap.get(proType);//特殊地区项目
			Map idsProMap2 = (Map)idsXjMap.get(proType);//特殊地区项目
			Map idsProMap3 = (Map)idsXzMap.get(proType);//特殊地区项目
			idsProMap1.clear();
			idsProMap2.clear();
			idsProMap3.clear();
			
			idsXbMap.put(proType, idsProMap1);
			idsXjMap.put(proType, idsProMap2);
			idsXzMap.put(proType, idsProMap3);
			idsDisMap.put("西部", idsXbMap);
			idsDisMap.put("新疆维吾尔自治区", idsXjMap);
			idsDisMap.put("西藏自治区", idsXzMap);
			
			idsDisTypeMap.put(disType, idsDisMap);
			idsMap.put("学科门类", idsDisTypeMap);
			
			//清空特殊地区的立项信息（立项数、立项比例、立项经费）
			String[] specialArea = new String[]{"西部", "新疆维吾尔自治区", "西藏自治区"};
			for(String area1 : specialArea){
				Map tproTypeMap2 = (Map)tproTypeMap.get(area1);
				Map tproMap = (Map)tproTypeMap2.get(proType);
				Map proTypeMap2 = (Map) proTypeMap.get(area1);
				Map proMap = (Map)proTypeMap2.get(proType);//西部-规划/青年
				
				tcountMap.put("立项合计", (Integer)tcountMap.get("立项合计") - (Integer)proMap.get("立项数"));
				tcountMap.put("立项总经费", formatFee((Double)tcountMap.get("立项总经费") - (Double)proMap.get("立项经费")));
				
				countMap.put("立项合计", (Integer)countMap.get("立项合计") - (Integer)proMap.get("立项数"));
				countMap.put("立项总经费", formatFee((Double)countMap.get("立项总经费") - (Double)proMap.get("立项经费")));
				
				tproTypeMap3.put("立项数", (Integer)tproTypeMap3.get("立项数")- (Integer)proMap.get("立项数"));
				tproTypeMap3.put("立项比例", getGrantedRate((Integer)tproTypeMap3.get("申请数"), (Integer)tproTypeMap3.get("立项数")));
				tproTypeMap3.put("立项经费", formatFee((Double)tproTypeMap3.get("立项经费") - (Double)proMap.get("立项经费")));
				
				//一般项目学科立项数&立项经费
				proTypeMap3.put("立项数", (Integer)proTypeMap3.get("立项数")- (Integer)proMap.get("立项数"));
				proTypeMap3.put("立项比例", getGrantedRate((Integer)proTypeMap3.get("申请数"), (Integer)proTypeMap3.get("立项数")));
				proTypeMap3.put("立项经费", formatFee((Double)proTypeMap3.get("立项经费") - (Double)proMap.get("立项经费")));
				
				tproMap.put("立项数", (Integer)tproMap.get("立项数") - (Integer)proMap.get("立项数"));
				tproMap.put("立项比例", getGrantedRate((Integer)tproMap.get("申请数"), (Integer)tproMap.get("立项数")));
				tproMap.put("立项经费", formatFee((Double)tproMap.get("立项经费") - (Double)proMap.get("立项经费")));
				
				proMap.put("立项数", 0);
				proMap.put("立项比例", getGrantedRate((Integer)proMap.get("申请数"), (Integer)proMap.get("立项数")));
				proMap.put("立项经费", 0.0);
				
				//更新容器
				proTypeMap2.put(proType, proMap);
				tproTypeMap2.put(proType, tproMap);
				proTypeMap.put(proType, proTypeMap3);
				tproTypeMap.put(proType, tproTypeMap3);
				proTypeMap.put(area, proTypeMap2);
				tproTypeMap.put(area, tproTypeMap2);
			}
			
			countMap.put("立项合计", (Integer)countMap.get("立项合计") - (Integer)proTypeMap3.get("立项数"));
			countMap.put("立项总经费", formatFee((Double)countMap.get("立项总经费") - (Double)proTypeMap3.get("立项经费")));
			
			tcountMap.put("立项合计", (Integer)tcountMap.get("立项合计") - (Integer)proTypeMap3.get("立项数"));
			tcountMap.put("立项总经费", formatFee((Double)tcountMap.get("立项总经费") - (Double)proTypeMap3.get("立项经费")));
			
			tproTypeMap3.put("立项数", (Integer)tproTypeMap3.get("立项数") - (Integer)proTypeMap3.get("立项数"));
			tproTypeMap3.put("立项比例", getGrantedRate((Integer)tproTypeMap3.get("申请数"), (Integer)tproTypeMap3.get("立项数")));
			tproTypeMap3.put("立项经费", formatFee((Double)tproTypeMap3.get("立项经费") - (Double)proTypeMap3.get("立项经费")));
			
			proTypeMap3.put("立项数", 0);
			proTypeMap3.put("立项比例", getGrantedRate((Integer)proTypeMap3.get("申请数"), (Integer)proTypeMap3.get("立项数")));
			proTypeMap3.put("立项经费", 0.0);//立项经费清零
			
			proTypeMap.put(proType, proTypeMap3);
			tproTypeMap.put(proType, tproTypeMap3);
		}
		
		//再解析新数据
		for(int i = 0; i < projectList.size(); i++){
			Object[] oo = (Object[])projectList.get(i);
			double applyFee = Double.parseDouble((String)oo[0]);
			String provinceName = (String)oo[2];
			String proId = (String)oo[3];
			applyFee = getRevisedFee(proType, applyFee, planFee, youthFee);
			//int voteNumber = (Integer)oo[1];//----------------手动调整暂时放弃了硬性条件，待调整
			
			if(isSpeciaclArea(provinceName) || area.equals("西部")){
				area = ("新疆维吾尔自治区".equals(area)) ? "新疆维吾尔自治区" : (("西藏自治区".equals(area)) ? "西藏自治区" : "西部");
				Map tproTypeMap2 = (Map)tproTypeMap.get(area);
				Map tproMap = (Map)tproTypeMap2.get(proType);
				Map proTypeMap2 = (Map) proTypeMap.get(area);
				Map proMap = (Map)proTypeMap2.get(proType);//西部-规划/青年
				
				// 把新数据的ids信息更新到特殊地区项目idsMap里
				Map idsProTypeMap = (Map)idsDisMap.get(area);
				Map idsProMap = (Map)idsProTypeMap.get(proType);//特殊地区项目
				if(idsList1.contains(proId)){
					idsProMap.put(proId, 1);
				} else if(idsList2.contains(proId)){
					idsProMap.put(proId, 2);
				}
				idsProTypeMap.put(proType, idsProMap);
				idsDisMap.put(area, idsProTypeMap);
				idsDisTypeMap.put(disType, idsDisMap);
				idsMap.put("学科门类", idsDisTypeMap);
				
//				//更新当前学科总立项数,仅一次更新
//				if(i == 0){
//					tcountMap.put("立项合计", (Integer)tcountMap.get("立项合计") - (Integer)proMap.get("立项数") + grantNum);
//					tcountMap.put("立项总经费", formatFee((Double)tcountMap.get("立项总经费") - (Double)proMap.get("立项经费")));
//					
//					countMap.put("立项合计", (Integer)countMap.get("立项合计") - (Integer)proMap.get("立项数") + grantNum);
//					countMap.put("立项总经费", formatFee((Double)countMap.get("立项总经费") - (Double)proMap.get("立项经费")));
//					
//					tproTypeMap3.put("立项数", (Integer)tproTypeMap3.get("立项数")- (Integer)proMap.get("立项数") + grantNum);
//					tproTypeMap3.put("立项比例", getGrantedRate((Integer)tproTypeMap3.get("申请数"), (Integer)tproTypeMap3.get("立项数")));
//					tproTypeMap3.put("立项经费", formatFee((Double)tproTypeMap3.get("立项经费") - (Double)proMap.get("立项经费")));
//					
//					//一般项目学科立项数&立项经费
//					proTypeMap3.put("立项数", (Integer)proTypeMap3.get("立项数")- (Integer)proMap.get("立项数") + grantNum);
//					proTypeMap3.put("立项比例", getGrantedRate((Integer)proTypeMap3.get("申请数"), (Integer)proTypeMap3.get("立项数")));
//					proTypeMap3.put("立项经费", formatFee((Double)proTypeMap3.get("立项经费") - (Double)proMap.get("立项经费")));
//					
//					tproMap.put("立项数", (Integer)tproMap.get("立项数") - (Integer)proMap.get("立项数") + grantNum);
//					tproMap.put("立项比例", getGrantedRate((Integer)tproMap.get("申请数"), (Integer)tproMap.get("立项数")));
//					tproMap.put("立项经费", formatFee((Double)tproMap.get("立项经费") - (Double)proMap.get("立项经费")));
//					
//					proMap.put("立项数", grantNum);
//					proMap.put("立项比例", getGrantedRate((Integer)proMap.get("申请数"), (Integer)proMap.get("立项数")));
//					proMap.put("立项经费", 0.0);//立项经费清零
//					
//				}
				
				tcountMap.put("立项合计", (Integer)tcountMap.get("立项合计") + 1);
				tcountMap.put("立项总经费", formatFee((Double)tcountMap.get("立项总经费") + applyFee));
				
				countMap.put("立项合计", (Integer)countMap.get("立项合计") + 1);
				countMap.put("立项总经费", formatFee((Double)countMap.get("立项总经费") + applyFee));
				
				tproTypeMap3.put("立项数", (Integer)tproTypeMap3.get("立项数")+ 1);
				tproTypeMap3.put("立项比例", getGrantedRate((Integer)tproTypeMap3.get("申请数"), (Integer)tproTypeMap3.get("立项数")));
				tproTypeMap3.put("立项经费", formatFee((Double)tproTypeMap3.get("立项经费") + applyFee ));
				
				proTypeMap3.put("立项数", (Integer)proTypeMap3.get("立项数") + 1);
				proTypeMap3.put("立项比例", getGrantedRate((Integer)proTypeMap3.get("申请数"), (Integer)proTypeMap3.get("立项数")));
				proTypeMap3.put("立项经费", formatFee((Double)proTypeMap3.get("立项经费") + applyFee ));
				
				tproMap.put("立项数", (Integer)tproMap.get("立项数") + 1);
				tproMap.put("立项比例", getGrantedRate((Integer)tproMap.get("申请数"), (Integer)tproMap.get("立项数")));
				tproMap.put("立项经费", formatFee((Double)tproMap.get("立项经费") + applyFee));
				
				proMap.put("立项数", (Integer)proMap.get("立项数") + 1);
				proMap.put("立项比例", getGrantedRate((Integer)proMap.get("申请数"), (Integer)proMap.get("立项数")));
				proMap.put("立项经费", (Double)proMap.get("立项经费") + applyFee);//立项经费清零//更新容器
				
				//更新容器
				
				proTypeMap2.put(proType, proMap);
				tproTypeMap2.put(proType, tproMap);
				proTypeMap.put(proType, proTypeMap3);
				tproTypeMap.put(proType, tproTypeMap3);
				proTypeMap.put(area, proTypeMap2);
				tproTypeMap.put(area, tproTypeMap2);
			} else {
				//更新一般项目需要重新计算此项目类型、此学科在特殊地区的数值
				
				if(idsScope.contains(proId)){//如果此proId在传到后台的proList中
					ProjectApplication project = (ProjectApplication)this.query(ProjectApplication.class, proId);
					parMap.put("uniCode", project.getUniversityCode());
					String area1, proName;
					List<String> proNameList = this.query("select u.provinceName from University u where u.code = :uniCode", parMap);
					proName = proNameList.get(0);
					if(isSpeciaclArea(proName)){
						area1 = ("新疆维吾尔自治区".equals(proName)) ? "新疆维吾尔自治区" : (("西藏自治区".equals(proName)) ? "西藏自治区" : "西部");
						Map tproTypeMap2 = (Map)tproTypeMap.get(area1);
						Map tproMap = (Map)tproTypeMap2.get(proType);
						Map proTypeMap2 = (Map) proTypeMap.get(area1);
						Map proMap = (Map)proTypeMap2.get(proType);//特殊地区学科map
						
						tcountMap.put("立项合计", (Integer)tcountMap.get("立项合计") + 1);
						tcountMap.put("立项总经费", formatFee((Double)tcountMap.get("立项总经费") + applyFee));
						
						countMap.put("立项合计", (Integer)countMap.get("立项合计") + 1);
						countMap.put("立项总经费", formatFee((Double)countMap.get("立项总经费") + applyFee));
						
						tproTypeMap3.put("立项数", (Integer)tproTypeMap3.get("立项数")+ 1);
						tproTypeMap3.put("立项比例", getGrantedRate((Integer)tproTypeMap3.get("申请数"), (Integer)tproTypeMap3.get("立项数")));
						tproTypeMap3.put("立项经费", formatFee((Double)tproTypeMap3.get("立项经费") + applyFee ));
						
						proTypeMap3.put("立项数", (Integer)proTypeMap3.get("立项数") + 1);
						proTypeMap3.put("立项比例", getGrantedRate((Integer)proTypeMap3.get("申请数"), (Integer)proTypeMap3.get("立项数")));
						proTypeMap3.put("立项经费", formatFee((Double)proTypeMap3.get("立项经费") + applyFee ));
						
						tproMap.put("立项数", (Integer)tproMap.get("立项数") + 1);
						tproMap.put("立项比例", getGrantedRate((Integer)tproMap.get("申请数"), (Integer)tproMap.get("立项数")));
						tproMap.put("立项经费", formatFee((Double)tproMap.get("立项经费") + applyFee));
						
						proMap.put("立项数", (Integer)proMap.get("立项数") + 1);
						proMap.put("立项比例", getGrantedRate((Integer)proMap.get("申请数"), (Integer)proMap.get("立项数")));
						proMap.put("立项经费", (Double)proMap.get("立项经费") + applyFee);//立项经费清零//更新容器
						
						//更新容器
						
						proTypeMap2.put(proType, proMap);
						tproTypeMap2.put(proType, tproMap);
						proTypeMap.put(proType, proTypeMap3);
						tproTypeMap.put(proType, tproTypeMap3);
						proTypeMap.put(area, proTypeMap2);
						tproTypeMap.put(area, tproTypeMap2);
					} else {
						countMap.put("立项合计", (Integer)countMap.get("立项合计") + 1);
						countMap.put("立项总经费", formatFee((Double)countMap.get("立项总经费") + applyFee));
						
						tcountMap.put("立项合计", (Integer)tcountMap.get("立项合计") + 1);
						tcountMap.put("立项总经费", formatFee((Double)tcountMap.get("立项总经费") - (Double)proTypeMap3.get("立项经费")));
						
						tproTypeMap3.put("立项数", (Integer)tproTypeMap3.get("立项数") + 1);
						tproTypeMap3.put("立项比例", getGrantedRate((Integer)tproTypeMap3.get("申请数"), (Integer)tproTypeMap3.get("立项数")));
						tproTypeMap3.put("立项经费", formatFee((Double)tproTypeMap3.get("立项经费") + applyFee));
						
						proTypeMap3.put("立项数", (Integer)proTypeMap3.get("立项数") + 1);
						proTypeMap3.put("立项比例", getGrantedRate((Integer)proTypeMap3.get("申请数"), (Integer)proTypeMap3.get("立项数")));
						proTypeMap3.put("立项经费", formatFee((Double)proTypeMap3.get("立项经费") + applyFee));
						
						proTypeMap.put(proType, proTypeMap3);
						tproTypeMap.put(proType, tproTypeMap3);
					}
				}
				
				//				总和			一般				地区
				// 总计		tcountMap	tproTypeMap3	tproMap
				// 学科		countMap	proTypeMap3		proMap
				
//				//更新当前学科总立项数,仅一次更新
//				if(i == 0){
//					countMap.put("立项合计", (Integer)countMap.get("立项合计") - (Integer)proTypeMap3.get("立项数") + grantNum);
//					countMap.put("立项总经费", formatFee((Double)countMap.get("立项总经费") - (Double)proTypeMap3.get("立项经费")));
//					
//					tcountMap.put("立项合计", (Integer)tcountMap.get("立项合计") - (Integer)proTypeMap3.get("立项数") + grantNum);
//					tcountMap.put("立项总经费", formatFee((Double)tcountMap.get("立项总经费") - (Double)proTypeMap3.get("立项经费")));
//					
//					tproTypeMap3.put("立项数", (Integer)tproTypeMap3.get("立项数") - (Integer)proTypeMap3.get("立项数") + grantNum);
//					tproTypeMap3.put("立项比例", getGrantedRate((Integer)tproTypeMap3.get("申请数"), (Integer)tproTypeMap3.get("立项数")));
//					tproTypeMap3.put("立项经费", formatFee((Double)tproTypeMap3.get("立项经费") - (Double)proTypeMap3.get("立项经费")));
//					
//					proTypeMap3.put("立项数", grantNum);
//					proTypeMap3.put("立项比例", getGrantedRate((Integer)proTypeMap3.get("申请数"), (Integer)proTypeMap3.get("立项数")));
//					proTypeMap3.put("立项经费", 0.0);//立项经费清零
//				}
				//更新经费信息
				tcountMap.put("立项总经费", formatFee((Double)tcountMap.get("立项总经费") + applyFee));
				countMap.put("立项总经费", formatFee((Double)countMap.get("立项总经费") + applyFee));
				tproTypeMap3.put("立项经费", formatFee((Double)tproTypeMap3.get("立项经费") + applyFee));
				proTypeMap3.put("立项经费", formatFee((Double)proTypeMap3.get("立项经费") + applyFee));
				
				proTypeMap.put(proType, proTypeMap3);
				tproTypeMap.put(proType, tproTypeMap3);
			}
		}
		
		disMap.put("项目类型", proTypeMap);
		disMap.put("合计数", countMap);
		disTypeMap.put(disType, disMap);
		
		totalMap.put("合计数", tcountMap);
		totalMap.put("项目类型", tproTypeMap);
		
		
		resultMap.put("学科门类", disTypeMap);
		resultMap.put("总计", totalMap);
		
		//*************************************************************************
//		
//		if(isSpeciaclArea(area) || area.equals("西部")){
//			area = ("新疆维吾尔自治区".equals(area)) ? "新疆维吾尔自治区" : (("西藏自治区".equals(area)) ? "西藏自治区" : "西部");
//			Map idsProTypeMap = (Map)idsDisMap.get(area);
//			Map idsProMap = (Map)idsProTypeMap.get(proType);//特殊地区项目
//			idsProMap.clear();
//			for(String proId : idsList1){
//				idsProMap.put(proId, 1);
//			}
//			for(String proId : idsList2){
//				idsProMap.put(proId, 2);
//			}
//			
//			idsProTypeMap.put(proType, idsProMap);
//			idsDisMap.put(area, idsProTypeMap);
//			
//		} else {
//			Map idsProTypeMap2 = (Map)idsDisMap.get(proType);//一般项目
//			idsProTypeMap2.clear();
//			for(String proId : idsList1){
//				idsProTypeMap2.put(proId, 1);
//			}
//			for(String proId : idsList2){
//				idsProTypeMap2.put(proId, 2);
//			}
//			
//			idsDisMap.put(proType, idsProTypeMap2);
//		}
//		
//		idsDisTypeMap.put(disType, idsDisMap);
//		
//		idsMap.put("学科门类", idsDisTypeMap);
		
		//更新最终容器
		dataMap.put(FinanceService.CLIENTDATA, resultMap);
		dataMap.put(FinanceService.IDSDATA, idsMap);
		
		return dataMap;
		
	}
	
//	/**
//	 * 初始化自筹项目微观调整页面
//	 * @param dataMap 数据容器
//	 * @param disType 学科门类
//	 */
//	@SuppressWarnings("unchecked")
//	public List initCheckZcById(Map dataMap, String disType){
//		//设置量
//		//int year = 2010;
//		
//		//为减少访问数据库消耗，采用一次访问数据库
//		Map parMap = new HashMap();
//		parMap.put("year", year);
//		parMap.put("disType", disType);
////		String conHql = "";
////		if(isSpeciaclArea(area) || area.equals("西部")){//如果是特殊地区
////			if("新疆维吾尔自治区".equals(area) || "西藏自治区".equals(area)){
////				conHql = " = :area ";
////				parMap.put("area", area);
////			} else {
////				conHql = " in (" + join(lib) + ") and u.provinceName <> '新疆' and u.provinceName <> '西藏' " ;
////			}
////		} else {
////			conHql = " not in (" + join(lib) + ")";
////		}
//		
//		List projectList = this.query("select p.id, p.projectName, p.director, u.name, '是否立项', p.voteNumber, p.score, u.provinceName from General p, University u " +
//			"where p.year = :year and p.projectType = :proType and p.universityCode = u.code and p.disciplineType = :disType and p.isReviewable <> 0 order by p.voteNumber desc, p.score desc", parMap);
//		
//		
//		//重构项目列表
//		Map idsMap = (Map)dataMap.get(FinanceService.IDSDATA);
//		Map idsDisTypeMap = (Map)idsMap.get("学科门类");
//		
//		Map idsDisMap = (Map)idsDisTypeMap.get(disType);
//		
//		Map idsProMap = new HashMap();
//		if(isSpeciaclArea(area) || area.equals("西部")){//如果是特殊地区
//			area = ("新疆维吾尔自治区".equals(area)) ? "新疆维吾尔自治区" : (("西藏自治区".equals(area)) ? "西藏自治区" : "西部");
//			Map idsProTypeMap = (Map)idsDisMap.get(area);
//			idsProMap = (Map)idsProTypeMap.get(proType);
//		} else {
//			idsProMap = (Map)idsDisMap.get(proType);
//		}
//		
//		String proId = "";
//		for(int i = 0; i < projectList.size(); i++){
//			Object[] oo = (Object[])projectList.get(i);
//			proId = (String)oo[0];
//			
//			//在对应缓存ids中搜索
//			if(null != idsProMap.get(proId) && (Boolean)idsProMap.get(proId)){
//				oo[4] = true;
//			} else {
//				oo[4] = false;
//			}
//			
//			projectList.set(i, oo);
//			
//		}
//		
//		return projectList;
//	}
//	
//	/**
//	 * 自筹项目微观核算
//	 */
//	@SuppressWarnings("unchecked")
//	public Map checkZcById(Map dataMap, List<String> idsList, String disType){
//		//设置量
//		//int year = 2010;
//		
//		//为减少访问数据库消耗，采用一次访问数据库
//		Map parMap = new HashMap();
//		parMap.put("year", year);
//		parMap.put("disType", disType);
////		String conHql = "";
////		if(isSpeciaclArea(area) || area.equals("西部")){//如果是特殊地区
////			if("新疆维吾尔自治区".equals(area) || "西藏自治区".equals(area)){
////				conHql = " = :area ";
////				parMap.put("area", area);
////			} else {
////				conHql = " in (" + join(lib) + ") and u.provinceName <> '新疆' and u.provinceName <> '西藏' " ;
////			}
////		} else {
////			conHql = " not in (" + join(lib) + ")";
////		}
//		
//		String[] idsArr = castToArray(idsList);//可能包含不存在id,以核对数据库为准
//		String idsScope = "(" + join(idsArr) + ")";
//		List projectList = this.query("select p.applyFee, p.voteNumber from General p, University u " +
//			"where p.year = :year and p.projectType = :proType and p.universityCode = u.code and " +
//			"p.disciplineType = :disType and p.id in " + idsScope + " and p.isReviewable <> 0 order by p.voteNumber desc, p.score desc", parMap);
//		
//		//更新resultMap
//		Map resultMap = (Map)dataMap.get(FinanceService.CLIENTDATA);
//		
//		Map totalMap = (Map)resultMap.get("总计");
//		Map disTypeMap = (Map)resultMap.get("学科门类");
//		
//		Map tcountMap = (Map)totalMap.get("合计数");
//		Map tproTypeMap = (Map)totalMap.get("项目类型");
//		
//		Map disMap = (Map)disTypeMap.get(disType);
//		Map countMap = (Map)disMap.get("合计数");
//		Map proTypeMap = (Map)disMap.get("项目类型");
//		
//		Map tproTypeMap3 = (Map)tproTypeMap.get(proType);
//		Map proTypeMap3 = (Map) proTypeMap.get(proType);
//		
//		// 			总和			一般				地区
//		// 总计		tcountMap	tproTypeMap3	tproMap
//		// 学科		countMap	proTypeMap3		proMap
//		
//		int grantNum = projectList.size();//对应项目立项数目
//		
//		//解析所需数据
//		for(int i = 0; i < projectList.size(); i++){
//			Object[] oo = (Object[])projectList.get(i);
//			double applyFee = Double.parseDouble((String)oo[0]);
//			applyFee = getRevisedFee(proType, applyFee, planFee, youthFee);
//			//int voteNumber = (Integer)oo[1];//----------------手动调整暂时放弃了硬性条件，待调整
//			
//			if(isSpeciaclArea(area) || area.equals("西部")){
//				area = ("新疆维吾尔自治区".equals(area)) ? "新疆维吾尔自治区" : (("西藏自治区".equals(area)) ? "西藏自治区" : "西部");
//				Map tproTypeMap2 = (Map)tproTypeMap.get(area);
//				Map tproMap = (Map)tproTypeMap2.get(proType);
//				Map proTypeMap2 = (Map) proTypeMap.get(area);
//				Map proMap = (Map)proTypeMap2.get(proType);//西部-规划/青年
//				//更新当前学科总立项数,仅一次更新
//				if(i == 0){
//					tcountMap.put("立项合计", (Integer)tcountMap.get("立项合计") - (Integer)proMap.get("立项数") + grantNum);
//					tcountMap.put("立项总经费", formatFee((Double)tcountMap.get("立项总经费") - (Double)proMap.get("立项经费")));
//					
//					countMap.put("立项合计", (Integer)countMap.get("立项合计") - (Integer)proMap.get("立项数") + grantNum);
//					countMap.put("立项总经费", formatFee((Double)countMap.get("立项总经费") - (Double)proMap.get("立项经费")));
//					
//					tproTypeMap3.put("立项数", (Integer)tproTypeMap3.get("立项数")- (Integer)proMap.get("立项数") + grantNum);
//					tproTypeMap3.put("立项比例", getGrantedRate((Integer)tproTypeMap3.get("申请数"), (Integer)tproTypeMap3.get("立项数")));
//					tproTypeMap3.put("立项经费", formatFee((Double)tproTypeMap3.get("立项经费") - (Double)proMap.get("立项经费")));
//					
//					//一般项目学科立项数&立项经费
//					proTypeMap3.put("立项数", (Integer)proTypeMap3.get("立项数")- (Integer)proMap.get("立项数") + grantNum);
//					proTypeMap3.put("立项比例", getGrantedRate((Integer)proTypeMap3.get("申请数"), (Integer)proTypeMap3.get("立项数")));
//					proTypeMap3.put("立项经费", formatFee((Double)proTypeMap3.get("立项经费") - (Double)proMap.get("立项经费")));
//					
//					tproMap.put("立项数", (Integer)tproMap.get("立项数") - (Integer)proMap.get("立项数") + grantNum);
//					tproMap.put("立项比例", getGrantedRate((Integer)tproMap.get("申请数"), (Integer)tproMap.get("立项数")));
//					tproMap.put("立项经费", formatFee((Double)tproMap.get("立项经费") - (Double)proMap.get("立项经费")));
//					
//					proMap.put("立项数", grantNum);
//					proMap.put("立项比例", getGrantedRate((Integer)proMap.get("申请数"), (Integer)proMap.get("立项数")));
//					proMap.put("立项经费", 0.0);//立项经费清零
//					
//				}
//				//更新经费信息
//				tcountMap.put("立项总经费", formatFee((Double)tcountMap.get("立项总经费") + applyFee));
//				countMap.put("立项总经费", formatFee((Double)countMap.get("立项总经费") + applyFee));
//				tproTypeMap3.put("立项经费", formatFee((Double)tproTypeMap3.get("立项经费") + applyFee ));
//				proTypeMap3.put("立项经费", formatFee((Double)proTypeMap3.get("立项经费") + applyFee ));
//				tproMap.put("立项经费", formatFee((Double)tproMap.get("立项经费") + applyFee));
//				proMap.put("立项经费", formatFee((Double)proMap.get("立项经费") + applyFee));
//				
//				//更新容器
//				
//				proTypeMap2.put(proType, proMap);
//				tproTypeMap2.put(proType, tproMap);
//				proTypeMap.put(proType, proTypeMap3);
//				tproTypeMap.put(proType, tproTypeMap3);
//				proTypeMap.put(area, proTypeMap2);
//				tproTypeMap.put(area, tproTypeMap2);
//			} else {
//				//更新当前学科总立项数,仅一次更新
//				if(i == 0){
//					countMap.put("立项合计", (Integer)countMap.get("立项合计") - (Integer)proTypeMap3.get("立项数") + grantNum);
//					countMap.put("立项总经费", formatFee((Double)countMap.get("立项总经费") - (Double)proTypeMap3.get("立项经费")));
//					
//					tcountMap.put("立项合计", (Integer)tcountMap.get("立项合计") - (Integer)proTypeMap3.get("立项数") + grantNum);
//					tcountMap.put("立项总经费", formatFee((Double)tcountMap.get("立项总经费") - (Double)proTypeMap3.get("立项经费")));
//					
//					tproTypeMap3.put("立项数", (Integer)tproTypeMap3.get("立项数") - (Integer)proTypeMap3.get("立项数") + grantNum);
//					tproTypeMap3.put("立项比例", getGrantedRate((Integer)tproTypeMap3.get("申请数"), (Integer)tproTypeMap3.get("立项数")));
//					tproTypeMap3.put("立项经费", formatFee((Double)tproTypeMap3.get("立项经费") - (Double)proTypeMap3.get("立项经费")));
//					
//					proTypeMap3.put("立项数", grantNum);
//					proTypeMap3.put("立项比例", getGrantedRate((Integer)proTypeMap3.get("申请数"), (Integer)proTypeMap3.get("立项数")));
//					proTypeMap3.put("立项经费", 0.0);//立项经费清零
//				}
//				//更新经费信息
//				tcountMap.put("立项总经费", formatFee((Double)tcountMap.get("立项总经费") + applyFee));
//				countMap.put("立项总经费", formatFee((Double)countMap.get("立项总经费") + applyFee));
//				tproTypeMap3.put("立项经费", formatFee((Double)tproTypeMap3.get("立项经费") + applyFee));
//				proTypeMap3.put("立项经费", formatFee((Double)proTypeMap3.get("立项经费") + applyFee));
//				
//				proTypeMap.put(proType, proTypeMap3);
//				tproTypeMap.put(proType, tproTypeMap3);
//			}
//		}
//		
//		disMap.put("项目类型", proTypeMap);
//		disMap.put("合计数", countMap);
//		disTypeMap.put(disType, disMap);
//		
//		totalMap.put("合计数", tcountMap);
//		totalMap.put("项目类型", tproTypeMap);
//		
//		
//		resultMap.put("学科门类", disTypeMap);
//		resultMap.put("总计", totalMap);
//		
//		//更新缓存idsMap
//		Map idsMap = (Map)dataMap.get(FinanceService.IDSDATA);
//		Map idsDisTypeMap = (Map)idsMap.get("学科门类");
//		
//		Map idsDisMap = (Map)idsDisTypeMap.get(disType);
//		
//		if(isSpeciaclArea(area) || area.equals("西部")){
//			area = ("新疆维吾尔自治区".equals(area)) ? "新疆维吾尔自治区" : (("西藏自治区".equals(area)) ? "西藏自治区" : "西部");
//			Map idsProTypeMap = (Map)idsDisMap.get(area);
//			Map idsProMap = (Map)idsProTypeMap.get(proType);
//			
//			idsProMap.clear();
//			for(String proId : idsList){
//				idsProMap.put(proId, true);
//			}
//			
//			idsProTypeMap.put(proType, idsProMap);
//			idsDisMap.put(area, idsProTypeMap);
//			
//		} else {
//			Map idsProMap = (Map)idsDisMap.get(proType);
//			
//			idsProMap.clear();
//			for(String proId : idsList){
//				idsProMap.put(proId, true);
//			}
//			
//			idsDisMap.put(proType, idsProMap);
//		}
//		
//		idsDisTypeMap.put(disType, idsDisMap);
//		
//		idsMap.put("学科门类", idsDisTypeMap);
//		
//		//更新最终容器
//		dataMap.put(FinanceService.CLIENTDATA, resultMap);
//		dataMap.put(FinanceService.IDSDATA, idsMap);
//		
//		return dataMap;
//		
//	}
	
	/**
	 * 完成核算
	 */
	@Transactional
	@SuppressWarnings("unchecked")
	public Boolean finish(Map dataMap){
		//设置量
		//int year = 2010;
		
		Map idsMap = (Map)dataMap.get(FinanceService.IDSDATA);
		Map idsDisTypeMap = (Map)idsMap.get("学科门类");
		Map zcIdsMap = (Map)idsMap.get("自筹项目");
		
		Map logicMap = (Map)idsDisTypeMap.get("逻辑学");
		Map logicMap2 = (Map)logicMap.get("规划基金项目");
		System.out.println(logicMap2.get("52427"));
		
		//先将该批核算数据所有拟立项标志位置为0
//		session.createQuery("update General p set p.isGranting = 0 where p.isReviewable <> 0 and p.year = " + year).executeUpdate();
		this.execute("update ProjectApplication p set p.isGranting = 0 where p.type = 'general' and p.isReviewable <> 0 and p.year = ? ", year);
		
		Iterator<String> it = idsDisTypeMap.keySet().iterator();
		String proIds = "";
		while(it.hasNext()){
			Map idsDisMap = (Map)idsDisTypeMap.get(it.next());
			
			Iterator<String> it2 = idsDisMap.keySet().iterator();
			while(it2.hasNext()){
				String proType = it2.next();
				
				Map idsProMap = new HashMap();
				Map idsProMap1 = new HashMap();//存放拟立项的项目
				Map idsProMap2 = new HashMap();//存放确定立项的项目
				String[] idsArr,idsArr1,idsArr2;
				//如果是特殊地区
				if(proType.equals("西部") || proType.equals("新疆维吾尔自治区") || proType.equals("西藏自治区")){
					Map idsProTypeMap = (Map)idsDisMap.get(proType);
					
					Iterator<String> it3 = idsProTypeMap.keySet().iterator();
					while(it3.hasNext()){
						String proType2 = it3.next();
						idsProMap = (Map)idsProTypeMap.get(proType2);
						
						if(!idsProMap.containsValue(2)){
							idsArr = castToArray(idsProMap.keySet().toArray());
							proIds = "(" + join(idsArr) + ")";
//							session.createQuery("update General p set p.isGranting = 1 where p.id in " + proIds).executeUpdate();
							this.execute("update ProjectApplication p set p.isGranting = 1 where p.type = 'general' and p.id in " + proIds);
						} else {
							Set<String> keySet = idsProMap.keySet();
							for(String key : keySet){
								if(idsProMap.get(key).equals(1)){
									idsProMap1.put(key, 1);
								}else if(idsProMap.get(key).equals(2)){
									idsProMap2.put(key, 2);
								}
							}
							idsArr1 = castToArray(idsProMap1.keySet().toArray());
							proIds = "(" + join(idsArr1) + ")";
//							session.createQuery("update General p set p.isGranting = 1 where p.id in " + proIds).executeUpdate();
							this.execute("update ProjectApplication p set p.isGranting = 1 where p.type = 'general' and p.id in " + proIds);
							idsArr2 = castToArray(idsProMap2.keySet().toArray());
							proIds = "(" + join(idsArr2) + ")";
//							session.createQuery("update General p set p.isGranting = 2 where p.id in " + proIds).executeUpdate();
							this.execute("update ProjectApplication p set p.isGranting = 2 where p.type = 'general' and p.id in " + proIds);
						}
					}
				} else {
					idsProMap = (Map)idsDisMap.get(proType);
					
					if(!idsProMap.containsValue(2)){
						idsArr = castToArray(idsProMap.keySet().toArray());
						proIds = "(" + join(idsArr) + ")";
//						session.createQuery("update General p set p.isGranting = 1 where p.id in " + proIds).executeUpdate();
						this.execute("update ProjectApplication p set p.isGranting = 1 where p.type = 'general' and p.id in " + proIds);
					} else {
						Set<String> keySet = idsProMap.keySet();
						for(String key : keySet){
							if(idsProMap.get(key).equals(1)){
								idsProMap1.put(key, 1);
							}else if(idsProMap.get(key).equals(2)){
								idsProMap2.put(key, 2);
							}
						}
						idsArr1 = castToArray(idsProMap1.keySet().toArray());
						proIds = "(" + join(idsArr1) + ")";
//						session.createQuery("update General p set p.isGranting = 1 where p.id in " + proIds).executeUpdate();
						this.execute("update ProjectApplication p set p.isGranting = 1 where p.type = 'general' and p.id in " + proIds);
						idsArr2 = castToArray(idsProMap2.keySet().toArray());
						proIds = "(" + join(idsArr2) + ")";
//						session.createQuery("update General p set p.isGranting = 2 where p.id in " + proIds).executeUpdate();
						this.execute("update ProjectApplication p set p.isGranting = 2 where p.type = 'general' and p.id in " + proIds);
					}
				}
			}
		}
		
		Iterator<String> zcIt = zcIdsMap.keySet().iterator();
		String zcProIds = "";
		while(zcIt.hasNext()){
			Map zcDisIdsMap = (Map)zcIdsMap.get(zcIt.next());
			
			String[] zcIdsArr = castToArray(zcDisIdsMap.keySet().toArray());
			zcProIds = "(" + join(zcIdsArr) + ")";
			
//			session.createQuery("update General p set p.isGranting = 1 where p.id in " + zcProIds).executeUpdate();
			this.execute("update ProjectApplication p set p.isGranting = 1 where p.type = 'general' and p.id in " + zcProIds);
		}
			
		return true;
	}

	@SuppressWarnings("unchecked")
	public Map checkZcById(Map dataMap, List<String> idsList, String disType) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	public List initCheckZcById(Map dataMap, String disType) {
		// TODO Auto-generated method stub
		return null;
	}
	
}