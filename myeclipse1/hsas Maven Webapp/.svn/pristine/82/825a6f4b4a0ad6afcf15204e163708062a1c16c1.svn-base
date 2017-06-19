package csdc.tool.bean;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 分页
 * @author 龚凡
 * @author xuhan
 */
public class Pager {
	private String hql;			    //查询列表数据语句
	private HashMap paraMap;		//hql参数对象
	
	private int currentRowIndex;	//当前位置在列表中的大致行号(0-indexed)，用于借助二级缓存加速列表定位
	private String targetEntityId;	//目标行id，用于定位页码
	private int targetPageNumber;	//目标页码

	private int sortColumn;			//排序列编号
	private int sortDirection;		//排序方向	0:降序    1:升序
	private int pageSize;			//一页的行数
	
	private Map searchQuery = new ConcurrentHashMap<String, Object>();
	
	public String getHql() {
		return hql;
	}
	public void setHql(String hql) {
		this.hql = hql;
	}
	public HashMap getParaMap() {
		return paraMap;
	}
	public void setParaMap(HashMap paraMap) {
		this.paraMap = paraMap;
	}
	public int getCurrentRowIndex() {
		return currentRowIndex;
	}
	public void setCurrentRowIndex(int currentRowIndex) {
		this.currentRowIndex = currentRowIndex;
	}
	public String getTargetEntityId() {
		return targetEntityId;
	}
	public void setTargetEntityId(String targetEntityId) {
		this.targetEntityId = targetEntityId;
	}
	public int getTargetPageNumber() {
		return targetPageNumber;
	}
	public void setTargetPageNumber(int targetPageNumber) {
		this.targetPageNumber = targetPageNumber;
	}
	public int getSortColumn() {
		return sortColumn;
	}
	public void setSortColumn(int sortColumn) {
		this.sortColumn = sortColumn;
	}
	public int getSortDirection() {
		return sortDirection;
	}
	public void setSortDirection(int sortDirection) {
		this.sortDirection = sortDirection;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public Map getSearchQuery() {
		return searchQuery;
	}
	public void setSearchQuery(Map searchQuery) {
		this.searchQuery = searchQuery;
	}
}
