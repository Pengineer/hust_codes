package csdc.tool.bean;

import java.util.List;
import java.util.Map;

/**
 * 分页
 * @author 龚凡
 * @author xuhan
 */
@SuppressWarnings("unchecked")
public class Pager {
	private String hql;			//查询列表数据语句
	private String hql4Count;	//查询总条数语句
	private Map paraMap;		//hql参数对象
	
	private int sortColumn;			//排序列编号
	private int sortDirection;		//排序方向	0:降序    1:升序
	private int pageSize;			//一页的行数

	private int searchType;		//检索条件
	private String keyword;		//检索关键字
	

	
	
	
	
	
	private Long totalRows;			//总条数
	private int startPageNumber;	//缓存中第一页的页码
	private List laData;			//列表数据缓存
	private List<String> idsBuffer;	//查看页面上下条id缓存


	
	
	
	
	private String targetEntityId;		//返回列表时的目标行id，用于定位页码
	private Integer targetPageNumber;	//返回列表时需要到达的页码
	private boolean needUpdate;			//返回列表时是否需要更新列表和上下条缓存

	private String errorInfo;	//错误信息
	
	
	public List<String> getIdsBuffer() {
		return idsBuffer;
	}

	public void setIdsBuffer(List<String> idsBuffer) {
		this.idsBuffer = idsBuffer;
	}
	
//	public Pager(){};
//
//	/**
//	 * 构造函数，初始化列表总记录数等信息
//	 * @param totalRows
//	 * @param pageSize
//	 * @param hql
//	 */
//	public Pager(String hql, Map paraMap) {
//		this.hql = hql;
//		this.paraMap = paraMap;
//	}

	public String getHql() {
		return hql;
	}
	public void setHql(String hql) {
		this.hql = hql;
	}
	public Map getParaMap() {
		return paraMap;
	}
	public void setParaMap(Map paraMap) {
		this.paraMap = paraMap;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
//	public int getPageNumber() {
//		return pageNumber;
//	}
//	public void setPageNumber(int pageNumber) {
//		this.pageNumber = pageNumber;
//	}
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
	public int getSearchType() {
		return searchType;
	}
	public void setSearchType(int searchType) {
		this.searchType = searchType;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public Long getTotalRows() {
		return totalRows;
	}
	public void setTotalRows(Long totalRows) {
		this.totalRows = totalRows;
	}
	public List getLaData() {
		return laData;
	}
	public void setLaData(List laData) {
		this.laData = laData;
	}
	public String getErrorInfo() {
		return errorInfo;
	}
	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}
	public boolean isNeedUpdate() {
		return needUpdate;
	}
	public void setNeedUpdate(boolean needUpdate) {
		this.needUpdate = needUpdate;
	}
	public int getStartPageNumber() {
		return startPageNumber;
	}
	public void setStartPageNumber(int startPageNumber) {
		this.startPageNumber = startPageNumber;
	}
	public String getTargetEntityId() {
		return targetEntityId;
	}
	public void setTargetEntityId(String targetEntityId) {
		this.targetEntityId = targetEntityId;
	}
	public Integer getTargetPageNumber() {
		return targetPageNumber;
	}
	public void setTargetPageNumber(Integer targetPageNumber) {
		this.targetPageNumber = targetPageNumber;
	}
	public String getHql4Count() {
		return hql4Count;
	}
	public void setHql4Count(String hql4Count) {
		this.hql4Count = hql4Count;
	}
}
