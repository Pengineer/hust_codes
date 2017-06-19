package csdc.action.dataMining.hotspot;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.action.dataMining.DadaMiningBaseAction;
import csdc.service.IHotspotService;
import csdc.tool.info.GlobalInfo;

/**
 * 领域热点分析
 * @author fengcl
 */
public class HotspotAction extends DadaMiningBaseAction{
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private IHotspotService hotspotService;
	
	private String type;	//研究热点之项目类型
	private int analyzeAngle;	//分析角度（申报数据、立项数据）
	private int topKnum;	//top k，前K条（topKnum默认取100）
	
	/**
	 * 进入项目研究热点配置页面
	 * @return
	 */
	public String toConfig(){
		return SUCCESS;
	}
	
	/**
	 * 更新索引：项目研究热点
	 * @return
	 */
	public String updateIndex(){
		if (hotspotService.updataIndex(type, analyzeAngle)) {
			jsonMap.put("hintInfo", "索引更新成功！");
		} else {
			jsonMap.put(GlobalInfo.ERROR_INFO, "索引更新失败！");
		}
		return SUCCESS;
	}
	
	/**
	 * 检测索引是否存在
	 * @return
	 */
	public String isExistIndex(){
		if (!hotspotService.isExistIndexFile(type, analyzeAngle)) {
			jsonMap.put(GlobalInfo.ERROR_INFO, "索引不存在，请先更新索引！");
		} 
		return SUCCESS;
	}
	
	/**
	 * 进入一般项目研究热点
	 * @return
	 */
	public String toHotspot(){
		session.put("hotstopType", type);
		session.put("analyzeAngle", analyzeAngle);	//分析角度
		session.put("topKnum", topKnum);
		session.put("toDataBase", toDataBase);
		return SUCCESS;
	}
	
	/**
	 * 各类项目研究热点
	 * @return
	 */
	public String hotspot(){
		// 参数初始化
		type = (String) session.get("hotstopType");
		analyzeAngle = (Integer) session.get("analyzeAngle");
		topKnum = (Integer) session.get("topKnum");
		toDataBase = (Integer) session.get("toDataBase");
		// 获取图表数据
		jsonMap = hotspotService.handleHotspot(type, analyzeAngle, topKnum, toDataBase);
		return SUCCESS;
	}
	/**
	 * 用于点击热点项时检索并获取相关列表数据，直接由JS调用
	 */
	protected List<Object[]> listData() {
		//参数初始化
		type = (String) session.get("hotstopType");
		analyzeAngle = (Integer) session.get("analyzeAngle");
		List<Object[]> listData = hotspotService.getListData(type, analyzeAngle, keyword);
		return listData;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getAnalyzeAngle() {
		return analyzeAngle;
	}
	public void setAnalyzeAngle(int analyzeAngle) {
		this.analyzeAngle = analyzeAngle;
	}
	public int getTopKnum() {
		return topKnum;
	}
	public void setTopKnum(int topKnum) {
		this.topKnum = topKnum;
	}
	
	@Override
	public String pageName() {
		return "hotspotPage";
	}
	@Override
	public String[] column() {
		return null;
	}
	@Override
	public String dateFormat() {
		return null;
	}
	@Override
	public Object[] simpleSearchCondition() {
		return null;
	}
	@Override
	public Object[] advSearchCondition() {
		return null;
	}
	@Override
	public String pageBufferId() {
		return null;
	}

}
