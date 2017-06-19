package csdc.service.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import csdc.bean.DMResult;
import csdc.service.IHotspotService;
import csdc.tool.JsonUtil;
import csdc.tool.dataMining.hotspot.ProjectHotspotAnalyzer;
import csdc.tool.info.DataMiningInfo;

/**
 * 数据挖掘：领域热点分析的业务逻辑层
 * @author fengcl
 */
public class HotspotService extends DataMiningService implements IHotspotService{
	
	public boolean updataIndex(String hotstopType, int analyzeAngle){
		//查询数据：数据准备
		String hql = null;
		if (analyzeAngle == 0) {//项目申报数据
			hql = "select pa.id, pa.name, pa.agencyName, pa.applicantName, pa.year from ProjectApplication pa where pa.name is not null and pa.type = :projectType";
		}else if (analyzeAngle == 1) {//项目立项数据
			hql = "select pg.id, pg.name, pa.agencyName, pg.applicantName, pa.year from ProjectGranted pg, ProjectApplication pa where pg.applicationId = pa.id and pg.name is not null and pg.projectType = :projectType";
		}
		// 处理检索参数：项目类型
		Map map = new HashMap();
		if (hotstopType != null && !hotstopType.isEmpty()) {
			map.put("projectType", hotstopType);
		}
		
		List<Object[]> projects = dao.query(hql, map);
		
		// 构造项目热点分析器：参数为 热点类型 + "_" + 分析角度
		ProjectHotspotAnalyzer projectHotspotAnalyzer = new ProjectHotspotAnalyzer(hotstopType + "_" + analyzeAngle);
		return projectHotspotAnalyzer.createIndex(projects);
	}
	
	public boolean isExistIndexFile(String hotstopType, int analyzeAngle){
		// 构造项目热点分析器：参数为 热点类型 + "_" + 分析角度
		ProjectHotspotAnalyzer projectHotspotAnalyzer = new ProjectHotspotAnalyzer(hotstopType + "_" + analyzeAngle);
		return projectHotspotAnalyzer.isExistIndexFile();
	}
	
	public Map handleHotspot(String hotstopType, int analyzeAngle, int topK, int toDataBase){
		Map map = new HashMap();
		
		// 构造项目热点分析器：参数为 热点类型 + "_" + 分析角度
		ProjectHotspotAnalyzer projectHotspotAnalyzer = new ProjectHotspotAnalyzer(hotstopType + "_" + analyzeAngle);
		// 调用项目热点分析器获取研究热点（top k）：参数为 topK，热点条数
		Map<String, Integer> termFrequency = projectHotspotAnalyzer.fetchTopKTermFreq(topK);
		if (termFrequency == null) {
			return map;
		}
		//组装表格数据
		List<List> list = new ArrayList();
		List titleList = new ArrayList();
		titleList.add("序号");
		titleList.add("热点");
		titleList.add("热度");
		list.add(titleList);
		int k = 0;
		// 组装 D3.js 图表数据格式
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		for (Entry<String, Integer> entry : termFrequency.entrySet()) {
			Map<String, Object> data =  new HashMap<String, Object>();
			data.put("name", entry.getKey());	//节点名称
			data.put("size", entry.getValue());	//节点大小
			dataList.add(data);
			List currentList = new ArrayList();
			k++;
			currentList.add(k);
			currentList.add(entry.getKey());
			currentList.add(entry.getValue());
			list.add(currentList);
		}
		// 对数据进行json格式化
		JsonUtil util = new JsonUtil();
		util.put("name", "root");
		util.put("children", dataList);
		util.put("list", list);
		map = util.getMap();
		
		// 如果需要入库，则更新或添加记录到数据库
		if(toDataBase == 1){
			String title = null;
			if ("general".equals(hotstopType)) {
				title = "一般项目";
			} else if ("key".equals(hotstopType)) {
				title = "重大攻关项目";
			} else if ("instp".equals(hotstopType)) {
				title = "基地项目";
			} else if ("post".equals(hotstopType)) {
				title = "后期资助项目";
			} else if ("entrust".equals(hotstopType)) {
				title = "委托应急课题";
			}
			if (analyzeAngle == 0) {
				title += "申报数据研究热点";
			}else if (analyzeAngle == 1) {
				title += "立项数据研究热点";
			}
			// 根据挖掘主题获取挖掘结果
			DMResult dmResult = (DMResult) dao.queryUnique("from DMResult dm where dm.title = ?", title);
			// 如果挖掘结果不存在，则开始预测，并存库；否则直接将resultJson转为jsonMap返回
			if (dmResult == null) {
				// 新增结果
				dmResult = new DMResult();
			}
			dmResult.setTitle(title);
			// 组装图表配置字段的内容，也是JSON格式
			JsonUtil configUtil = new JsonUtil();
			configUtil.put(DataMiningInfo.GRAPH_TYPE, DataMiningInfo.D3_PACK);
			configUtil.put("分析类型", title);
			configUtil.put("分析角度", (analyzeAngle == 0) ? "申报数据" : "立项数据");
			configUtil.put("挖掘条数", topK);
			dmResult.setConfig(configUtil.toString());
			dmResult.setResultJson(util.toString());
			dmResult.setType(DataMiningInfo.HOTSPOT);
			dmResult.setDate(new Date());
			dao.addOrModify(dmResult);
		}
		return map;
	}

	public List<Object[]> getListData(String hotstopType, int analyzeAngle, String keyword) {
		// 构造项目热点分析器：参数为 热点类型 + "_" + 分析角度
		ProjectHotspotAnalyzer projectHotspotLuceneAnalyzer = new ProjectHotspotAnalyzer(hotstopType + "_" + analyzeAngle);
		List<Object[]> listData = null;
		if(keyword != null && !keyword.isEmpty()){
			// 调用项目热点分析器进行检索：参数为 关键词
			listData = projectHotspotLuceneAnalyzer.search(keyword);
		}
		return listData;
	}

}
