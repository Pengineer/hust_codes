package csdc.service.imp;

import java.io.IOException;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import org.apache.mahout.common.Pair;
import org.apache.mahout.fpm.pfpgrowth.convertors.string.TopKStringPatterns;
import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.DMResult;
import csdc.dao.SqlBaseDao;
import csdc.service.IAssociationService;
import csdc.tool.JsonUtil;
import csdc.tool.dataMining.association.AssociationRulesAnalyzer;
import csdc.tool.dataMining.association.FPGrowthAnalyzer;
import csdc.tool.info.DataMiningInfo;

/**
 * 数据挖掘：关联规则挖掘的业务逻辑层
 * @author fengcl
 */
@SuppressWarnings("deprecation")
public class AssociationService extends DataMiningService implements IAssociationService{
	
	@Autowired
	protected SqlBaseDao sqlDao;
	
	public Map handleAnalyze(String analysisType, int analyzeAngle, int topK, Long minSupport, int toDataBase, int startYear, int endYear){
		Map map = new HashMap();
		
		//定义关联规则挖掘器的输入数据
		List<Object[]> dataItems = new ArrayList<Object[]>();
		//分析年度
		if(startYear<1999) startYear=1999;//测试库数据不规范，因此作此限定
		if(endYear>2099) endYear=2099;
		Map yearMap = new HashMap();
		List yearList = new ArrayList();
		int m = endYear - startYear;
		for(int j=0; j<m+1; j++){
			yearList.add(startYear + j);
		}
		yearMap.put("yearList", yearList);
		
		String title = null;
		// 第一步：数据准备
		if (analysisType == null) {
			return map;
		}else if ("discipline".equals(analysisType)) {
			//学科与学科之间的关联性（minSupport默认取5）
			//查询所有申报或立项项目中有学科交叉研究的情况，并转换为关联规则挖掘器的输入数据格式
			List<String> disciplines = null;
			if (analyzeAngle == 0) {//项目申报数
				title = "项目学科关联性分析（申报数据）";
//				disciplines = dao.query("select pa.discipline from ProjectApplication pa where pa.discipline like '%;%' and pa.year in (:yearList)");
				String hql = "select pa.discipline from ProjectApplication pa where pa.discipline like '%;%' and pa.year in (:yearList)";
				disciplines = dao.query(hql, yearMap);
			}else if (analyzeAngle == 1) {//项目立项数
				title = "项目学科关联性分析（立项数据）";
//				disciplines = dao.query("select pa.discipline from ProjectGranted pg, ProjectApplication pa where pg.applicationId = pa.id and pa.discipline like '%;%' ");
				String hql = "select pa.discipline from ProjectGranted pg, ProjectApplication pa where pg.applicationId = pa.id and pa.discipline like '%;%' and pa.year in (:yearList)";
				disciplines = dao.query(hql, yearMap);
			}
			// 获取2009和1992两种标准的学科代码的映射[name -> code/name]
			List<Object[]> sos2009 = dao.query("select so.code, so.name from SystemOption so where so.standard = ? and so.code is not null", "GBT13745-2009");
			List<Object[]> sos1992 = dao.query("select so.code, so.name from SystemOption so where so.standard = ? and so.code is not null", "GBT13745-1992");
			Map<Object, String> disc2009Map = new HashMap<Object, String>();
			Map<Object, String> disc1992Map = new HashMap<Object, String>();
			for (Object[] so : sos2009) {
				disc2009Map.put(so[1], so[0] + "/" + so[1]);
			}
			for (Object[] so : sos1992) {
				disc1992Map.put(so[1], so[0] + "/" + so[1]);
			}
			// 组装dataItems：1、按学科的名称进行去重；2、给名称添加学科代码，构建成code/name的格式
			for (String discipline : disciplines) {
				String[] oldDiscs = discipline.split("; ");	//按"; "隔开
				String[] newDiscs = discipline.replaceAll("[0-9/]", "").split("; ");//先通过正则表达式去掉字符串中的数字和"/"符号，然后按"; "隔开
				//1、按学科的名称进行去重
				Object[] uniqueDiscs = arrayUnique(newDiscs);//根据学科名称去重
				//2、将 [名称] 转换为 [代码/名称] 的形式
				for (int i = 0, len = uniqueDiscs.length; i < len; i++) {
					if (disc2009Map.containsKey(uniqueDiscs[i])) {//1、首先从2009版标准中获取
						uniqueDiscs[i] = disc2009Map.get(uniqueDiscs[i]);
					}else if (disc1992Map.containsKey(uniqueDiscs[i])) {//2、然后从1992版标准中获取
						uniqueDiscs[i] = disc1992Map.get(uniqueDiscs[i]);
					}else {//3、如果两个标准中都不存在，则从原始数据中获取
						for (String oldDisc : oldDiscs) {
							if (oldDisc.endsWith(uniqueDiscs[i].toString())) {
								uniqueDiscs[i] = oldDisc + "[非国标]";
								break;
							}
						}
					}
				}
				dataItems.add(uniqueDiscs);
			}
		}else if ("university".equals(analysisType)) {
			//高校与高校之间的关联性（minSupport默认取2）
			//查询项目中有高校合作研究的情况，并转换为关联规则挖掘器的输入数据格式
			//所有申报或立项项目所属高校
			List<Object[]> univs4Project = null;
			//所有申报或立项项目项目成员所在高校
			List<Object[]> univs4Member = null;
			if (analyzeAngle == 0) {//项目申报数
				title = "项目高校关联性分析（申报数据）";
				//所有申报项目所属高校
//				univs4Project = dao.query("select pa.id, ag.name from ProjectApplication pa join pa.university ag ");
				String projectHql = "select pa.id, ag.name from ProjectApplication pa join pa.university ag where pa.year in (:yearList)";
				univs4Project = dao.query(projectHql, yearMap);
				//所有申报项目项目成员所在高校
//				univs4Member = dao.query("select pa.id, ag.name from ProjectApplication pa, ProjectMember pm join pm.university ag where pa.id = pm.applicationId and pm.groupNumber = 1");
				String memberHql = "select pa.id, ag.name from ProjectApplication pa, ProjectMember pm join pm.university ag where pa.id = pm.applicationId and pm.groupNumber = 1 and pa.year in (:yearList)";
				univs4Member = dao.query(memberHql, yearMap);
			}else if (analyzeAngle == 1) {//项目立项数
				title = "项目高校关联性分析（立项数据）";
				//所有立项项目所属高校
//				univs4Project = dao.query("select pg.id, ag.name from ProjectGranted pg join pg.university ag ");
				String projectHql = "select pg.id, ag.name from ProjectGranted pg, ProjectApplication pa join pg.university ag where pa.id = pg.applicationId and pa.year in (:yearList)";
				univs4Project = dao.query(projectHql, yearMap);
				//所有立项项目项目成员所在高校
//				univs4Member = dao.query("select pg.id, ag.name from ProjectGranted pg, ProjectMember pm join pm.university ag where pg.applicationId = pm.applicationId and pg.memberGroupNumber = pm.groupNumber ");
				String memberHql = "select pg.id, ag.name from ProjectGranted pg, ProjectMember pm, ProjectApplication pa join pm.university ag where pg.applicationId = pm.applicationId and pg.memberGroupNumber = pm.groupNumber and pa.id = pg.applicationId and pa.year in (:yearList)";
				univs4Member = dao.query(memberHql, yearMap);
			}
			//项目id -> 高校集合的映射（set去重）
			Map<String, Set<String>> univMap = new HashMap<String, Set<String>>();
			//根据项目id，遍历项目所有成员的高校信息
			for (Object[] objs : univs4Member) {
				Set<String> univs = univMap.get(objs[0]);
				if (univs == null) {
					univs = new HashSet<String>();
					univMap.put((String)objs[0], univs);
				}
				univs.add((String)objs[1]);
			}
			//根据项目id，遍历项目的高校信息
			for (Object[] objs : univs4Project) {
				Set<String> univs = univMap.get(objs[0]);
				if (univs != null) {
					univs.add((String)objs[1]);
				}
			}
			//准备关联规则挖掘器的输入数据
			for (Entry<String, Set<String>> entry : univMap.entrySet()) {
				Set<String> univs = entry.getValue();
				dataItems.add(univs.toArray());
			}
		}else if ("member".equals(analysisType)) {
			//研究人员与研究人员之间的关联性（minSupport默认取2）
			//查询项目中有研究人员合作研究的情况，并转换为关联规则挖掘器的输入数据格式
			List<Object[]> projectMembers = null;
			if (analyzeAngle == 0) {//项目申报数据
				//所有申报项目项目成员
				title = "项目研究人员关联性分析（申报数据）";
//				projectMembers = dao.query("select pa.id, pm.memberName, ag.name from ProjectApplication pa, ProjectMember pm join pm.university ag where pa.id = pm.applicationId and pm.groupNumber = 1");
				String memberHql = "select pa.id, pm.memberName, ag.name from ProjectApplication pa, ProjectMember pm join pm.university ag where pa.id = pm.applicationId and pm.groupNumber = 1 and pa.year in (:yearList)";
				projectMembers = dao.query(memberHql, yearMap);
			}else if (analyzeAngle == 1) {//项目立项数据
				//所有立项项目项目成员
				title = "项目研究人员关联性分析（立项数据）";
//				projectMembers = dao.query("select pg.id, pm.memberName, ag.name from ProjectGranted pg, ProjectMember pm join pm.university ag where pg.applicationId = pm.applicationId and pg.memberGroupNumber = pm.groupNumber");
				String memberHql = "select pg.id, pm.memberName, ag.name from ProjectGranted pg, ProjectMember pm, ProjectApplication pa join pm.university ag where pg.applicationId = pm.applicationId and pg.memberGroupNumber = pm.groupNumber and pa.id = pg.applicationId and pa.year in (:yearList)";
				projectMembers = dao.query(memberHql, yearMap);
			}
			//项目id -> 高校集合的映射（set去重）
			Map<String, Set<String>> memberMap = new HashMap<String, Set<String>>();
			//根据项目id，遍历项目所有成员的高校信息
			for (Object[] objs : projectMembers) {
				Set<String> members = memberMap.get(objs[0]);
				if (members == null) {
					members = new HashSet<String>();
					memberMap.put((String)objs[0], members);
				}
				members.add(objs[1] + "（" + objs[2] + "）");
			}
			//准备关联规则挖掘器的输入数据
			for (Entry<String, Set<String>> entry : memberMap.entrySet()) {
				Set<String> members = entry.getValue();
				if (members.size() > 2) {
					dataItems.add(members.toArray());
				}
			}
		}
		
		// 第二步：数据挖掘
		// FP关联规则挖掘分析器
		FPGrowthAnalyzer fpgAnalyzer = new FPGrowthAnalyzer(dataItems, minSupport, topK, new HashSet<String>());
		List<Pair<String, TopKStringPatterns>> frequentPatterns = null;
		try {
			frequentPatterns = fpgAnalyzer.work();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println(frequentPatterns.toString());
		
		// 第三步：数据分析
		// 获取关联信息，结果为Map类型，其map格式样例：{华中科技大学, [[武汉大学, 10], [湖北大学, 6]]}
		Map<String, List<Object[]>> assoMap = AssociationRulesAnalyzer.getAssociation(frequentPatterns);
		
		// 第四步：可视化数据组装
		// 定义D3需要的Json数据格式
		List<Map<String, Object>> nodes = new ArrayList<Map<String, Object>>();	//节点信息
		List<Map<String, Object>> links = new ArrayList<Map<String, Object>>();	//连接线信息
		// 定义列表数据格式
		List<Object[]> listDatas = new ArrayList<Object[]>();	//TOP K 列表数据
		Set<String> outSet = new HashSet<String>();	//TOP K 列表数据过滤器
		// 记录node与link的索引对应关系,对每个node节点名进行编号
		Map<Object, Integer> indexMap = new HashMap<Object, Integer>();
		int i = 0;
		// 1、构建节点信息
		for (String key : assoMap.keySet()) {
			//格式：{"name":"86060/传播学"},
			Map<String,Object> nodeMap =  new HashMap<String,Object>();
			nodeMap.put("name", key);
			nodeMap.put("value", assoMap.get(key).size());// 当前节点关联的其他节点数
			nodes.add(nodeMap);
			indexMap.put(key, i++);// 例如：{华中科技大学, 1} 或  {武汉大学, 2} 或  {湖北大学, 3}
		}
		// 2、构建连接线信息
		for (String key : assoMap.keySet()) {
			List<Object[]> datas = assoMap.get(key);
			for (Object[] data : datas) {
				//格式：{"source":1,"target":0,"value":8},
				Map<String,Object> linkMap =  new HashMap<String,Object>();
				linkMap.put("source", indexMap.get(data[0]));//根据节点名获取编号
				linkMap.put("target", indexMap.get(key));//根据节点名获取编号
				linkMap.put("value", data[1]);	// 连接线的粗细，表示source、target两者之间的合作数量
				links.add(linkMap);
				
				// 添加 TOP K 列表数据，过滤 key + data[0] = data[0] + key的数据，只留下其中之一即可，例如 "华中科技大学<->武汉大学" 与 "武汉大学<->华中科技大学" 
				if(!outSet.contains(data[0] + key)) {// data[0] + key = "武汉大学华中科技大学"
					listDatas.add(new Object[]{0, data[0], key, data[1]});// 添加的数据的格式：[0, 武汉大学, 华中科技大学, 10]
               	 	outSet.add(key + data[0]);// key + data[0] = "华中科技大学武汉大学"
				}
			}
		}
		// 3、构建TOP K表数据
		List<List> dataList = new ArrayList();
		List titleList = new ArrayList();
		titleList.add("序号");
		titleList.add("条目一");
		titleList.add("条目二");
		titleList.add("权值（项）");
		dataList.add(titleList);
//		Object[] listTitle = new Object[]{"序号", "条目一", "条目二", "权值（项）"};
//		dataList.add(listTitle);
		// 对列表数据listDatas进行从大到小进行排序
		Collections.sort(listDatas, new Comparator<Object[]>() {
			public int compare(Object[] o1, Object[] o2) {
				Integer cnt1 = Integer.parseInt(String.valueOf(o1[3]));
				Integer cnt2 = Integer.parseInt(String.valueOf(o2[3]));
				return cnt2.compareTo(cnt1);
			}
		});
		listDatas = listSort(listDatas, analysisType, analyzeAngle);//固定排序

		List<Object[]> objectList = new ArrayList<Object[]>();
		if (topK > 10) {
			if(listDatas.size() > 10){
				objectList.addAll(listDatas.subList(0, 10));
			}else objectList.addAll(listDatas);
		}else {
			if(listDatas.size() > topK){
				objectList.addAll(listDatas.subList(0, topK));
			}else objectList.addAll(listDatas);
		}
		
		int k = 0;
		for(Object[] objects : objectList){
			k++;
			objects[0] = k;
			List newList = new ArrayList();
			for(Object o:objects){
				newList.add(o);
			}
			dataList.add(newList);
		}
		// 返回json数据
//		jsonMap.put("nodes", nodes);
//		jsonMap.put("links", links);
//		jsonMap.put("dataList", dataList);
		JsonUtil util = new JsonUtil();
		util.put("nodes", nodes);
		util.put("links", links);
		util.put("dataList", dataList);
//		util.put("topK", topK > 10 ? 10 : topK);
		map = util.getMap();
		
		//如果需要入库，则更新或添加记录到数据库
		if(toDataBase == 1){
			//根据挖掘主题获取挖掘结果
			DMResult dmResult = (DMResult) dao.queryUnique("from DMResult dm where dm.title = ?", title);
			//如果挖掘结果不存在，则开始预测，并存库；否则直接将resultJson转为jsonMap返回
			if (dmResult == null) {
				//新增结果
				dmResult = new DMResult();
			}
			dmResult.setTitle(title);
			JsonUtil configUtil = new JsonUtil();
			configUtil.put(DataMiningInfo.GRAPH_TYPE, DataMiningInfo.D3_FORCE);
			configUtil.put("分析类型", title);
			configUtil.put("分析角度", (analyzeAngle == 0) ? "申报数据" : "立项数据");
			configUtil.put("最小支持度", minSupport);
			dmResult.setConfig(configUtil.toString());
			dmResult.setResultJson(util.toString());
			dmResult.setType(DataMiningInfo.ASSOCATION);
			dmResult.setDate(new Date());
			dao.addOrModify(dmResult);
		}
		return map;
	}
	
	public Map universityAssociation(int topK, int toDataBase, int startYear, int endYear){
		Map map = new HashMap();
		
		//分析年度
		if(startYear<1999) startYear=1999;//测试库数据不规范，因此作此限定
		if(endYear>2099) endYear=2099;
		Map yearMap = new HashMap();
		List yearList = new ArrayList();
		int m = endYear - startYear;
		for(int j=0; j<m+1; j++){
			yearList.add(startYear + j);
		}
		yearMap.put("yearList", yearList);
		
		//第一步：查询 高校代码 <-> 高校代码/高校名称 的映射
		Map code2NameMap = new HashMap();
		List<Object[]> univList = dao.query("select ag.code, ag.name from Agency ag where ag.type = 3 or ag.type = 4");
		for (Object[] objs : univList) {
			code2NameMap.put(objs[0], objs[0] + "/" + objs[1]);
		}
		
		//第二步：查询项目中有高校合作研究的情况(只有立项项目才有经费)
		String title = "立项项目高校关联性分析-批准经费";
		//所有立项项目所属高校
//		List<Object[]> univs4Project = dao.query("select pg.id, ag.code, pg.approveFee from ProjectGranted pg join pg.university ag where pg.approveFee > 0 ");
		String projectHql = "select pg.id, ag.code, pg.approveFee from ProjectGranted pg, ProjectApplication pa join pg.university ag where pg.approveFee > 0 and pa.id = pg.applicationId and pa.year in (:yearList)";
		List<Object[]> univs4Project = dao.query(projectHql, yearMap);
		//所有立项项目项目成员所在高校
//		List<Object[]> univs4Member = dao.query("select pg.id, ag.code from ProjectGranted pg, ProjectMember pm join pm.university ag where pg.applicationId = pm.applicationId and pg.memberGroupNumber = pm.groupNumber and pg.approveFee > 0");
		String memberHql = "select pg.id, ag.code from ProjectGranted pg, ProjectApplication pa, ProjectMember pm join pm.university ag where pg.applicationId = pm.applicationId and pg.memberGroupNumber = pm.groupNumber and pg.approveFee > 0 and pa.id = pg.applicationId and pa.year in (:yearList)";
		List<Object[]> univs4Member = dao.query(memberHql, yearMap);
		
		//第三步：准备 (1)项目ID 到 对应高校集合的映射、(2)项目ID 到 对应批准经费的映射
		//项目id -> 高校code集合的映射（这里有两点需要注意：1、用set的目的是去重；2、用treeset的目的是为了实现排序，按照学科代码排序）
		Map<String, TreeSet<String>> univMap = new HashMap<String, TreeSet<String>>();
		//项目id -> 批准经费
		Map<String, Double> approveFeeMap = new HashMap<String, Double>();
		//根据项目id，遍历项目所有成员的高校code信息
		for (Object[] objs : univs4Member) {
			TreeSet<String> univs = univMap.get(objs[0]);
			if (univs == null) {
				univs = new TreeSet<String>();
				univMap.put((String)objs[0], univs);//记录每个项目的高校集合
			}
			univs.add((String)objs[1]);
		}
		//根据项目id，遍历项目的高校code信息
		for (Object[] objs : univs4Project) {
			TreeSet<String> univs = univMap.get(objs[0]);
			if (univs != null) {
				univs.add((String)objs[1]);
				approveFeeMap.put((String)objs[0], (Double)objs[2]);//记录每个项目的批准经费
			}
		}
		
		//第四步：高校集合两两组合，准备高校代码对 到 批准经费的映射
		//高校代码对：10001-10487 <-> 经费 的映射
		Map<String, Double> codes2FeeMap = new HashMap<String, Double>();
		//准备高校代码对：10001-10487 <-> 经费 的映射
		for (Entry<String, TreeSet<String>> entry : univMap.entrySet()) {
			String pId = entry.getKey();//项目ID
			Object[] univs = entry.getValue().toArray();//当前项目的高校集合
			int size = univs.length;
			if (size > 1) {//如果当前项目只有一个高校，则不考虑
				//遍历高校集合，进行两两组合（作为key），经费作为value
				for(int i = 0; i < size - 1; i++){
					String key1 = univs[i] + "-";	//如：10001-
					for(int j = i + 1; j < size; j++){
						String key2 = key1 + univs[j];	//如：10001-10487
						//对key2进行经费的更新：如果codes2FeeMap中已经存在key2，则将原来的经费 + 当前合作项目的经费；如果不存在，则将当前合作项目的经费加入
						codes2FeeMap.put(key2, (codes2FeeMap.containsKey(key2) ? codes2FeeMap.get(key2) + approveFeeMap.get(pId) : approveFeeMap.get(pId)));
					}
				}
			}
		}

		//第五步：对codes2FeeMap的value（经费）从高到低排序，为了后面的前topK条过滤
		List<Entry<String,Double>> codes2FeeList = new ArrayList<Entry<String,Double>>(codes2FeeMap.entrySet());
		Collections.sort(codes2FeeList, new Comparator<Entry<String, Double>>() {
			public int compare(Entry<String, Double> entry1, Entry<String, Double> entry2) {
				return (entry1.getValue() < entry2.getValue()) ? 1 : -1;
			}
		});
		//从排序后的结果中去前topK条
		List<Entry<String,Double>> topKCodes2FeeList = null;
		if (topK < codes2FeeList.size()) {
			topKCodes2FeeList = codes2FeeList.subList(0, topK);
		}else {
			topKCodes2FeeList = codes2FeeList;
		}
		
		// 第六步：可视化数据组装
		// 定义D3需要的Json数据格式
		List<Map<String, Object>> nodes = new ArrayList<Map<String, Object>>();	//节点信息
		List<Map<String, Object>> links = new ArrayList<Map<String, Object>>();	//连接线信息
		// 定义列表数据格式
		List<Object[]> listDatas = new ArrayList<Object[]>();	//TOP K 列表数据
		// 记录node与link的索引对应关系
		Map<Object, Integer> indexMap = new HashMap<Object, Integer>();
		int i = 0;
		// 1、构建节点信息
		Set<String> codeSet = new HashSet<String>();
		Map<String, Integer> nodeValue = new HashMap<String, Integer>();
		for (Entry<String, Double> entry : topKCodes2FeeList) {
			String[] codes = entry.getKey().split("-");
			for (String code : codes) {
				nodeValue.put(code, codeSet.contains(code) ? nodeValue.get(code) + 1 : 1);
				codeSet.add(code);
			}
		}
		
		for (String code : codeSet) {
			//格式：{"name":"10487/华中科技大学", "value":"5"},
			Map<String,Object> nodeMap =  new HashMap<String,Object>();
			nodeMap.put("name", code2NameMap.get(code));
			nodeMap.put("value", nodeValue.get(code));// 当前节点关联的其他节点数
			nodes.add(nodeMap);
			indexMap.put(code, i++);
		}
		// 2、构建连接线信息
		for (Entry<String, Double> entry : topKCodes2FeeList) {
			String[] codes = entry.getKey().split("-");
			//格式：{"source":1,"target":0,"value":288.5},
			Map<String,Object> linkMap =  new HashMap<String,Object>();
			linkMap.put("source", indexMap.get(codes[0]));
			linkMap.put("target", indexMap.get(codes[1]));
			linkMap.put("value", entry.getValue());	// 连接线的粗细，表示source、target两者之间的合作数量
			links.add(linkMap);
			//组装列表记录
			listDatas.add(new Object[]{0, code2NameMap.get(codes[0]), code2NameMap.get(codes[1]), entry.getValue()});
		}
		// 3、构建便于excel导出的List<List>类型的TOP K表数据
		List<List> dataList = new ArrayList();
		List titleList = new ArrayList();
		titleList.add("序号");
		titleList.add("条目一");
		titleList.add("条目二");
		titleList.add("权值（万元）");
		dataList.add(titleList);
		Collections.sort(listDatas, new Comparator<Object[]>() {
			public int compare(Object[] o1, Object[] o2) {
				Double cnt1 = Double.valueOf(String.valueOf(o1[3]));
				Double cnt2 = Double.valueOf(String.valueOf(o2[3]));
				return cnt2.compareTo(cnt1);
			}
		});
		listDatas = listSort(listDatas, "university", 2);//固定排序
		List<Object[]> objectList = new ArrayList<Object[]>();
		if (listDatas.size() > topK) {
			objectList.addAll(listDatas.subList(0, topK));
		}else {
			objectList.addAll(listDatas);
		}
		int k = 0;
		for(Object[] objects : objectList){
			k++;
			objects[0] = k;
			List newList = new ArrayList();
			for(Object o:objects){
				newList.add(o);
			}
			dataList.add(newList);
		}
		JsonUtil util = new JsonUtil();
		util.put("nodes", nodes);
		util.put("links", links);
		util.put("dataList", dataList);
		map = util.getMap();
		
		//如果需要入库，则更新或添加记录到数据库
		if(toDataBase == 1){
			//根据挖掘主题获取挖掘结果
			DMResult dmResult = (DMResult) dao.queryUnique("from DMResult dm where dm.title = ?", title);
			//如果挖掘结果不存在，则开始预测，并存库；否则直接将resultJson转为jsonMap返回
			if (dmResult == null) {
				//新增结果
				dmResult = new DMResult();
			}
			dmResult.setTitle(title);
			JsonUtil configUtil = new JsonUtil();
			configUtil.put(DataMiningInfo.GRAPH_TYPE, DataMiningInfo.D3_FORCE);
			configUtil.put("分析类型", title);
			configUtil.put("分析角度", "批准经费");
			configUtil.put("挖掘条数", topK);
			dmResult.setConfig(configUtil.toString());
			dmResult.setResultJson(util.toString());
			dmResult.setType(DataMiningInfo.ASSOCATION);
			dmResult.setDate(new Date());
			dao.addOrModify(dmResult);
		}
		return map;
	}
	
	public Map<Object,String> getProjectYear(){
		Map<Object,String> map = null;
		List years = sqlDao.query("select distinct c_year from S_D_PROJECT where c_year is not null order by c_year");
		if(years.size() > 0){
			map = new LinkedHashMap<Object, String>();
			for (Object year : years) {
				map.put(year, year + "年");
			}
		}
		return map;
	}
	
	public int getLastYear() {
		int lastYear = 0;
		List years = sqlDao.query("select distinct c_year from S_D_PROJECT where c_year is not null order by c_year");
		if(years.size() > 0){
			lastYear = Integer.parseInt(years.get(years.size()-1) + "");
		}
		return lastYear;
	}
	
	/**
	 * 去除数组中重复的记录
	 * @param array
	 * @return
	 */
	public static Object[] arrayUnique(Object[] array) {  
	    List<Object> list = new ArrayList<Object>();  
	    for (Object str : array) {
			 if(!list.contains(str)) {  
	            list.add(str);  
	        }  
		}
	    return list.toArray(new Object[0]);  //list的转成数组
	}
	
	/**
	 * 为了保证多次分析的结果固定，对表格中的数据进行排序操作
	 * 1、同一行的条目一和条目二之间进行排序；
	 * 2、上下条之间以条目一排序；
	 * 【学科和高校经费以“/”隔开，以前面的数字排序，其他的以文字排序】
	 * @param list
	 * @return newList
	 */
	public List<Object[]> listSort(List<Object[]> list, String analysisType, int analyzeAngle){
		List<Object[]> newList = new ArrayList<Object[]>();
		if(analysisType.equals("discipline") || analyzeAngle == 2){
			for(Object[] o : list){
				Object a;
				//同一行的条目一和条目二排序
				if(o[1].toString().compareTo(o[2].toString()) > 0){
					a = o[2];
					o[2] = o[1];
					o[1] = a;
				}
				newList.add(o);
			}
			//上下条之间如果权值相同以条目一排序
			Comparator<Object[]> compare = new Comparator<Object[]>(){
				@Override
				public int compare(Object[] o1, Object[] o2) {
					if(o1[3].equals(o2[3])){
						return o1[1].toString().compareTo(o2[1].toString());
					}else return 0;
				}};
			Collections.sort(newList, compare);			
		}else {//中文排序方式（有缺陷：http://j2ee-yohn.iteye.com/blog/272006）
			for(Object[] o : list){
				Object a;
				//同一行的条目一和条目二排序
				if(Collator.getInstance(Locale.CHINESE).compare(o[1], o[2]) > 0){
					a = o[2];
					o[2] = o[1];
					o[1] = a;
				}
				newList.add(o);
			}
			//上下条之间如果权值相同以条目一排序
			Comparator<Object[]> compare = new Comparator<Object[]>(){
				@Override
				public int compare(Object[] o1, Object[] o2) {
					if(o1[3].equals(o2[3])){
						return Collator.getInstance(Locale.CHINESE).compare(o1[1], o2[1]); 
					}else return 0;
				}};
			Collections.sort(newList, compare);				
		}
		return newList;
	}
}
