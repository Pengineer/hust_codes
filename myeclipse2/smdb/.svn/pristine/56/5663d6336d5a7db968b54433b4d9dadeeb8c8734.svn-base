package csdc.action.dataMining;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.json.JSONObject;

import org.springframework.transaction.annotation.Transactional;


import csdc.action.BaseAction;
import csdc.bean.DMResult;
import csdc.tool.info.DataMiningInfo;
import csdc.tool.info.GlobalInfo;

/**
 * 数据挖掘：挖掘结果管理类
 * @author fengcl
 *
 */
public class DataMiningResultAction extends BaseAction{

	private static final long serialVersionUID = 1L;
	
	private static final String HQL = "select dm.id, dm.title, dm.type, dm.date from DMResult dm where 1 = 1";
	private static final String pageName = "dataMiningResultPage";
	private static final String pageBufferId = "dm.id";// 缓存id
	private static final String dateFormat = "yyyy-MM-dd";// 列表时间格式
	private static final String[] column = new String[] { "dm.title", "dm.type", "dm.date desc" };// 排序列

	/**
	 * 初级检索条件
	 */
	public Object[] simpleSearchCondition() {
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		hql.append(HQL);
		keyword = (keyword == null) ? "" : keyword.toLowerCase();
		if (!keyword.isEmpty()) {
			hql.append(" and ");
			if (searchType == 1) {
				hql.append(" LOWER(dm.title) like :keyword ");
			} else if (searchType == 2) {
				hql.append(" LOWER(dm.type) like :keyword ");
			} else if (searchType == 3) {
				hql.append(" To_CHAR(dm.date,'YYYY-MM-DD') like :keyword ");
			} else {
				hql.append(" (LOWER(dm.title) like :keyword or LOWER(dm.type) like :keyword or To_CHAR(dm.date,'YYYY-MM-DD') like :keyword) ");
			}
			map.put("keyword", "%" + keyword + "%");
		}
		return new Object[]{
			hql.toString(),
			map,
			2,
			null
		};
	}
	
	/**
	 * 进入查看页面
	 * @return
	 */
	public String toView(){
		DMResult dmResult = dao.query(DMResult.class, entityId);
		List<String> dataMining_parm = new ArrayList<String>();
		Map<String, Object> map = JSONObject.fromObject(dmResult.getConfig());
		for (Entry<String, Object> entry : map.entrySet()) {
			dataMining_parm.add(entry.getKey() + "：" + entry.getValue());
		}
		session.put("dataMining_parm", dataMining_parm);
		return SUCCESS;
	}

	/**
	 * 获取查看页面数据
	 * @return
	 */
	public String view(){
		DMResult dmResult = dao.query(DMResult.class, entityId);
		if(dmResult == null){
			jsonMap.put(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_VIEW_NULL);
			return INPUT;
		}
		JSONObject jsonConfig = JSONObject.fromObject(dmResult.getConfig());
		String graphType = jsonConfig.getString(DataMiningInfo.GRAPH_TYPE);
		jsonMap = JSONObject.fromObject(dmResult.getResultJson());
		jsonMap.put("graphType", graphType);
		return SUCCESS;
	}
	
	/**
	 * 删除挖掘结果
	 */
	@Transactional
	public String delete() {
		for (String entityId : entityIds) {
			dao.delete(DMResult.class, entityId);
		}
		return SUCCESS;
	}

	/**
	 * 删除校验
	 */
	public void validateDelete() {
		if (null == entityIds || entityIds.isEmpty()) {// id不得为空
			this.addFieldError(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
			jsonMap.put(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
		}
	}
	
	public Object[] advSearchCondition() {
		return null;
	}
	public String pageName() {
		return DataMiningResultAction.pageName;
	}
	public String[] column() {
		return DataMiningResultAction.column;
	}
	public String dateFormat() {
		return DataMiningResultAction.dateFormat;
	}
	public String pageBufferId() {
		return DataMiningResultAction.pageBufferId;
	}
}
