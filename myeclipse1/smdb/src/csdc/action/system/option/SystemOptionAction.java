package csdc.action.system.option;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.util.finder.Test;

import csdc.action.BaseAction;
import csdc.bean.SystemOption;
import csdc.dao.HibernateBaseDao;
import csdc.service.IBaseService;

/**
 * 查看、维护SystemOption
 * @author xuhan
 *
 */
@SuppressWarnings("unchecked")
public class SystemOptionAction extends BaseAction{

	private static final long serialVersionUID = -7963279482767351049L;
	private static final String HQL = "select so.name, so.description, so.code, so.isAvailable, so.standard, so.abbr, so.id from SystemOption so where 1=1 " ;
	private List nodesInfo;
	private static final String PAGE_NAME = "systemOptionPage";// 列表页面名称
	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";// 列表时间格式
	private String id;
	private static final String PAGE_BUFFER_ID = "so.id";// 上下条查看时用于查找缓存的字段
	private static final String[] COLUMN = {
		"so.name",
		"so.description",
		"so.code",
		"so.isAvailable",
		"so.standard",
		"so.abbr"
	};// 用于拼接的排序列	
	@Autowired
	private IBaseService baseService;
	
	@Autowired
	private HibernateBaseDao dao;
	
	@Transactional
	public String toView() {
		return SUCCESS;
	}
	
	/**
	 * ztree使用
	 * 用于生成系统选项表的树形机构目录
	 * @return
	 */
	@Transactional
	public String queryNodesTitleByParentId() {
		nodesInfo = new ArrayList();
		String hql = "select so.id, so.name, min(children.id) from SystemOption so left join so.systemOptions children where " +
				(id == null || id.isEmpty() ? " so.systemOption.id is null " : " so.systemOption.id = ? ") +
				" group by so.id, so.name, so.code order by so.name ";
		dao.setCacheRegion("queryNodesTitleByParentId");
		List<Object[]> soList = (id == null || id.isEmpty()) ? dao.query(hql) : dao.query(hql, id);
		for (Object[] systemOption : soList) {
			Map node = new HashMap();
			node.put("id", systemOption[0]);
			node.put("name", systemOption[1]);
			if (systemOption[2] != null) {
				node.put("isParent", true);//是否父节点
			}
			nodesInfo.add(node);
		}
		dao.setCacheRegion(null);
		return SUCCESS;
	}
	/**
	 * 根据每一个节点的ID获取节点的详细信息
	 * @param 节点的ID
	 * @return jsonMap的数据为：系统选项表的名称、描述、CODE、是否可用（1：可用； 0：不可用）、代码标准、缩写
	 */
	@Override
	public Object[] simpleSearchCondition() {
		searchType = -1;
		keyword = "";
		StringBuffer hql = new StringBuffer();
		hql.append(HQL);
		Map map = new HashMap();
		dao.setCacheRegion("queryNodesDetailByParentId");
		if (null == id || id.isEmpty()) {
			id =  (String) dao.queryUnique("select so.id from SystemOption so where so.systemOption.id is null");
		} else {
			SystemOption curSystemOption = (SystemOption) dao.query(SystemOption.class, id);
			dao.setCacheRegion("queryNodesDetailByParentId");
			List children = dao.query("select so from SystemOption so where so.systemOption.id = ? order by so.code", id);//根据当前ID查找子节点
			if (children.isEmpty() && curSystemOption.getSystemOption() != null) {//没有子节点的话就先找出父选项的ID；
				id = curSystemOption.getSystemOption().getId();//父选项的ID；
				dao.setCacheRegion("queryNodesDetailByParentId");
			} 
		}
		hql.append(" and so.systemOption.id =:id order by so.name ");
		map.put("id", id);
		return new Object[] {
				hql.toString(),
				map,
				0,
				null
			};
	}
	/**
	 * 根据每一个节点的ID获取节点的详细信息
	 * @param 节点的ID
	 * @return List形式的nodesInfo包含系统选项表的ID、名称、描述、CODE、是否可用（1：可用； 0：不可用）、代码标准、缩写
	 */
	@Transactional
	public String queryNodesDetailByParentId() {
		dao.setCacheRegion("queryNodesDetailByParentId");
		SystemOption curSystemOption = (SystemOption) dao.query(SystemOption.class, id);
		
		List<SystemOption> soList = new ArrayList();
		dao.setCacheRegion("queryNodesDetailByParentId");
		List children = dao.query("select so from SystemOption so where so.systemOption.id = ? order by so.code", id);
		if (children.isEmpty() && curSystemOption.getSystemOption() != null) {
			SystemOption Test = curSystemOption.getSystemOption();
			id = curSystemOption.getSystemOption().getId();//父选项的ID；
			dao.setCacheRegion("queryNodesDetailByParentId");
			soList.add((SystemOption) dao.query(SystemOption.class, id));
			dao.setCacheRegion("queryNodesDetailByParentId");
			soList.addAll(dao.query("select so from SystemOption so where so.systemOption.id = ? order by so.code", id));
			System.out.println(soList);
		} else {
			soList.add(curSystemOption);
			soList.addAll(children);
		}

		nodesInfo = new ArrayList();
		for (SystemOption systemOption : soList) {
			nodesInfo.add(new String[]{
				systemOption.getId(),//系统选项表的ID
				systemOption.getName(),//系统选项表的名称
				systemOption.getDescription(),//系统选项表的描述
				systemOption.getCode(),//系统选项表的CODE
				systemOption.getIsAvailable() + "",//是否可用
				systemOption.getStandard(),//代码标准
				systemOption.getAbbr()//缩写
			});
		}
//		jsonMap.put("nodesInfo", nodesInfo);
		return SUCCESS;
	}
	
	public List getNodesInfo() {
		return nodesInfo;
	}

	public void setNodesInfo(List nodesInfo) {
		this.nodesInfo = nodesInfo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public IBaseService getBaseService() {
		return baseService;
	}

	public void setBaseService(IBaseService baseService) {
		this.baseService = baseService;
	}

	public Map getJsonMap() {
		return jsonMap;
	}

	public void setJsonMap(Map jsonMap) {
		this.jsonMap = jsonMap;
	}

	@Override
	public String pageName() {
		return PAGE_NAME;
	}

	@Override
	public String[] column() {
		return COLUMN;
	}

	@Override
	public String dateFormat() {
		return SystemOptionAction.DATE_FORMAT;
	}


	@Override
	public Object[] advSearchCondition() {
		return null;
	}

	@Override
	public String pageBufferId() {
		return SystemOptionAction.PAGE_BUFFER_ID;
	}
}
