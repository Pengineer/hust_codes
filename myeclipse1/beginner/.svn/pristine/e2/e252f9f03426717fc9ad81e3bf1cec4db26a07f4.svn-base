package csdc.tool;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * 分页
 * @author 龚凡
 */
public class Pager {
	private long totalRows;// 总记录数
	private int totalPages;// 总页数
	private int pageSize;// 页面大小
	private int firstPage;// 首页
	private int previousPage;// 上一页
	private int currentPage;// 当前页
	private int nextPage;// 下一页
	private int lastPage;// 末页
	private int startRow;// 起始数
	private String hql;// 查询语句
	private String[] idsBuffer;// 页面上下条缓存
	private int bufferIndex;// 缓存数组中心元素在查询列表的位置
	private HashMap<Integer, String> pagelist =  new  LinkedHashMap<Integer, String>();// 生成页码列表

	/**
	 * 构造函数，初始化列表总记录数等信息
	 * @param totalRows
	 * @param pageSize
	 * @param hql
	 */
	public Pager(long totalRows, int pageSize, String hql) {
		this.totalRows = totalRows;
		this.pageSize = pageSize;
		this.hql = hql;
		this.totalPages = (int) (totalRows / pageSize);
		int mod = (int) (totalRows % pageSize);
		if(mod > 0)
			totalPages++;
		this.currentPage = 1;
		this.startRow = 0;
		for(int i = 1; i <= totalPages; i++) {
			pagelist.put(i, String.valueOf(i) + "/" + String.valueOf(totalPages));
		}
		this.setPages();
	}

	/**
	 * 设置pager类的首页、上页、下页、末页
	 */
	public void setPages() {
		this.firstPage = 1;
		if (this.totalPages > 1) {
			this.lastPage = this.totalPages;
		} else {
			this.lastPage = 1;
		}
		if (this.currentPage-1 > 0 ) {
			this.previousPage = this.currentPage-1;
		} else {
			this.previousPage = 1;
		}
		if (this.currentPage+1 < this.totalPages) {
			this.nextPage = this.currentPage+1;
		} else {
			this.nextPage = this.totalPages;
		}
	}

	/**
	 * 刷新pager类中的相关信息，主要用于删除记录后
	 */
	public void refresh() {
		this.totalPages = (int) (totalRows / pageSize);
		int mod = (int) (totalRows % pageSize);
		if(mod > 0)
			totalPages++;
		if (currentPage > totalPages) {
			currentPage -= 1;
		}
		startRow = (currentPage - 1) * pageSize;
		this.setPages();
		pagelist.clear();
		for(int i = 1; i <= totalPages; i++) {
			pagelist.put(i, String.valueOf(i) + "/" + String.valueOf(totalPages));
		}
	}
	
	public long getTotalRows() {
		return totalRows;
	}
	public void setTotalRows(long totalRows) {
		this.totalRows = totalRows;
	}
	public String getHql() {
		return hql;
	}
	public void setHql(String hql) {
		this.hql = hql;
	}
	public String[] getIdsBuffer() {
		return idsBuffer;
	}
	public void setIdsBuffer(String[] idsBuffer) {
		this.idsBuffer = idsBuffer;
	}
	public int getBufferIndex() {
		return bufferIndex;
	}
	public void setBufferIndex(int bufferIndex) {
		this.bufferIndex = bufferIndex;
	}
	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getFirstPage() {
		return firstPage;
	}
	public void setFirstPage(int firstPage) {
		this.firstPage = firstPage;
	}
	public int getPreviousPage() {
		return previousPage;
	}
	public void setPreviousPage(int previousPage) {
		this.previousPage = previousPage;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		if (currentPage > 0 && currentPage <= this.totalPages) {
			this.currentPage = currentPage;
		} else {
			this.currentPage = 1;
		}
		this.startRow = (this.currentPage - 1) * this.pageSize;
		this.setPages();
	}
	public int getNextPage() {
		return nextPage;
	}
	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}
	public int getLastPage() {
		return lastPage;
	}
	public void setLastPage(int lastPage) {
		this.lastPage = lastPage;
	}
	public int getStartRow() {
		return startRow;
	}
	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}
	public HashMap<Integer, String> getPagelist() {
		return pagelist;
	}
	public void setPagelist(HashMap<Integer, String> pagelist) {
		this.pagelist = pagelist;
	}
}