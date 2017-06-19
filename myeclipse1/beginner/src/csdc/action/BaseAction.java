package csdc.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import csdc.service.IBaseService;
import csdc.tool.Pager;

/**
 * 排序、上一条、下一条、检索、高级检索
 * 基本功能
 * @author 龚凡
 * @author 雷达 更新上下条的功能 2010.05.21
 */
public class BaseAction extends ActionSupport implements ServletRequestAware {

	private static final long serialVersionUID = 191333696402808803L;
	protected IBaseService baseservice;// 基本功能接口
	protected List<Object> pageList;// 列表数据
	protected int pageNumber;// 分页页码
	protected int columnLabel;// 排序列标志
	protected int search_type;// 初级检索类别
	protected String keyword;// 初级检索关键字
	protected int listLabel;// 由左侧菜单进入列表的标志，用于还原列表。1表示由左侧进入列表
	protected boolean lable;// 用于ajax功能标识
	public HttpServletRequest request;
	
	/**
	 * 查看上一条
	 * @param pageName pager类的名字
	 * @param id 当前条的id
	 * @return 上一条的id
	 */
	@SuppressWarnings("unchecked")
	protected String prevRecord(String pageName, String id) {
		Map session = ActionContext.getContext().getSession();
		Pager pager = (Pager) session.get(pageName);
		String[] ids = pager.getIdsBuffer();//id的缓存
		//如果缓存为空，则根据当前id初始化缓存
		if(ids.length == 0 || ids == null) {
			initPageBuffer(pageName, id);
			prevRecord(pageName, id);
		}
		//如果当前条为缓存的第0条，而且缓存长度不等于列表总长度则重新生成缓存，将原来缓存的相对总列表的位置向左偏移一段
		//当缓存长度不等于列表总长度，则所有id都在其中，不用重新建立缓存
		if(ids[0].equals(id)) {
			if(ids.length == pager.getTotalRows()) {
				return ids[ids.length - 1];
			}
			//BufferIndex为缓存的相对总列表的位置
			pager.setBufferIndex((int)( ((pager.getBufferIndex() - ids.length / 2) % pager.getTotalRows() + pager.getTotalRows() ) % pager.getTotalRows()));
			session.put(pageName, pager);
			//"shiftBuffer"用作特殊关键字，初始函数识别后不用从总列表读取，直接读取偏移位置即可
			initPageBuffer(pageName, "shiftBuffer");
			pager = (Pager) session.get(pageName);
			ids = pager.getIdsBuffer();
		}
		int i;
		//在缓存中找到匹配后返回上一条
		for(i = 1; i < ids.length; i++) {
			if(ids[i].equals(id))
				return ids[i - 1];
		}
		//没有匹配的，则根据当前id生成缓存，再查找
		if(i == ids.length) {
			initPageBuffer(pageName, id);
			pager = (Pager) session.get(pageName);
			ids = pager.getIdsBuffer();
			for(i = 1; i < ids.length; i++) {
				if(ids[i].equals(id))
					return ids[i - 1];
			}
		}
		return null;
	}

	/**
	 * 查看下一条
	 * @param pageName pager类的名称
	 * @param id 当前的id
	 * @return 下一条的id
	 */
	@SuppressWarnings("unchecked")
	protected String nextRecord(String pageName, String id) {
		Map session = ActionContext.getContext().getSession();
		Pager pager = (Pager) session.get(pageName);
		String[] ids = pager.getIdsBuffer();
		if(ids.length == 0 || ids == null) {
			initPageBuffer(pageName, id);
			nextRecord(pageName, id);
		}
		if(ids[ids.length - 1].equals(id)) {
			if(ids.length == pager.getTotalRows()) {
				return ids[0];
			}
			pager.setBufferIndex((int)((pager.getBufferIndex() + ids.length / 2) % pager.getTotalRows()));
			session.put(pageName, pager);
			initPageBuffer(pageName, "shiftBuffer");
			pager = (Pager) session.get(pageName);
			ids = pager.getIdsBuffer();
		}
		int i;
		for(i = 0; i < ids.length - 1; i++) {
			if(ids[i].equals(id))
				return ids[i + 1];
		}
		if(i == ids.length - 1) {
			initPageBuffer(pageName, id);
			pager = (Pager) session.get(pageName);
			ids = pager.getIdsBuffer();
			for(i = 0; i < ids.length - 1; i++) {
				if(ids[i].equals(id))
					return ids[i + 1];
			}
		}
		return null;
	}

	
	/**
	 * 初始化页面条目id缓存
	 * @param pageName pager类的名称
	 * @param id 当前条的id 如果通过列表页面初始化缓存，此处传null即可
	 */
	@SuppressWarnings("unchecked")
	public void initPageBuffer(String pageName, String id) {
		int oneSideSize = 10;//缓存的单边长度，如果设为N，则缓存长度为2N+1
		Map session = ActionContext.getContext().getSession();
		Pager pager = (Pager) session.get(pageName);
		String hql = pager.getHql();
		//为了节省空间，只查询出id
		hql = hql.substring(0, hql.indexOf(".id")) + ".id" + hql.substring(hql.indexOf(" from "));
		//获取id总列表
		//System.out.println("hql = " + hql);
		List idsList = baseservice.query(hql);
		int total = (int) pager.getTotalRows();
		//如果总列表长度小于默认缓存长度，则将缓存长度设为列表总长度
		if(pager.getTotalRows()  <= oneSideSize * 2 + 1) {
			String[] ids = new String[(int) pager.getTotalRows()];
			for(int i = 0; i < total; i++) {
				ids[i] = idsList.get(i).toString();
			}
			pager.setIdsBuffer(ids);
			pager.setBufferIndex(idsList.size() / 2);
			session.put(pageName, pager);
			return ;
		}
		String[] ids = new String[2 * oneSideSize + 1];
		int offset = 0;
		if(id == null) {//通过列表初始化，读取列表的偏移量设为缓存偏移量
			offset = pager.getStartRow();
		} else {//通过id初始化或者通过关键字"shiftBuffer"初始化
			if(id.equals("shiftBuffer")) {
				offset = pager.getBufferIndex();
			} else {//通过id初始化，在总列表中找到id位子，设为缓存偏移量
				for(int i = 0; i < idsList.size(); i++) {
					if(idsList.get(i).toString().equals(id)) {
						offset = i;
						break;
					}
				}
			}
		}
		//生成缓存
		for(int i = 1; i <= oneSideSize; i++) {
			ids[oneSideSize + i] = idsList.get((offset + i) % total).toString();//右边的缓存
			ids[i - 1] = idsList.get((((offset + i - oneSideSize - 1) % total) + total) % total).toString();//左边的缓存
		}
		ids[oneSideSize] = idsList.get(offset).toString();//中间位置的缓存
		pager.setIdsBuffer(ids);
		pager.setBufferIndex(offset);
		session.put(pageName, pager);
	}
	
	/**
	 * 删除或添加记录后，刷新pager类的总记录数
	 * @param pageName
	 */
	@SuppressWarnings("unchecked")
	public void refreshPager(String pageName) {
		Map session = ActionContext.getContext().getSession();
		// 更新总记录数
		Pager pager = (Pager) session.get(pageName);
		if (pager != null) {
			pager.setTotalRows(baseservice.count(pager.getHql()));
			pager.refresh();
			this.initPageBuffer(pageName, null);
		}
	}

	/**
	 * 按指定列排序
	 * @return 跳转成功
	 */
	@SuppressWarnings("unchecked")
	public void sort(String pageName, String columnName) {
		Map session = ActionContext.getContext().getSession();
		Pager pager = (Pager) session.get(pageName);// 根据页面名称获得页面对象
		String hql = pager.getHql();
		String hql1 = hql.substring(0, hql.indexOf(" order by "));
		String hql2 = hql.substring(hql.indexOf(" order by "));

		if (hql2.indexOf(',') >= 0) {
			String hql3 = hql2.substring(0, hql2.indexOf(','));
			String hql4 = hql2.substring(hql2.indexOf(','));
			if (hql3.indexOf(columnName+" desc") >= 0 || hql3.indexOf(columnName+" asc")>=0) {
				if (hql3.indexOf(" desc") >= 0) {
					hql3 = hql3.substring(0, hql3.indexOf(" desc"))
							+ " asc";
				} else {
					hql3 = hql3.substring(0, hql3.indexOf(" asc"))
							+ " desc";
				}
				hql2 = hql3 + hql4;
			} else {
				hql2 = " order by " + columnName + " desc" + hql4;
			}
			hql = hql1 + hql2;
		}
		//System.out.println(hql);
		pager = new Pager(pager.getTotalRows(), pager.getPageSize(), hql);
		session.put(pageName, pager);
		pageList = baseservice.list(pager.getHql(), pager.getStartRow(), pager.getPageSize());
	}

	
	public void setBaseservice(IBaseService baseservice) {
		this.baseservice = baseservice;
	}
	public List<Object> getPageList() {
		return pageList;
	}
	public void setPageList(List<Object> pageList) {
		this.pageList = pageList;
	}
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public boolean isLable() {
		return lable;
	}
	public void setLable(boolean lable) {
		this.lable = lable;
	}
	public int getColumnLabel() {
		return columnLabel;
	}
	public void setColumnLabel(int columnLabel) {
		this.columnLabel = columnLabel;
	}
	public int getSearch_type() {
		return search_type;
	}
	public void setSearch_type(int search_type) {
		this.search_type = search_type;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public void setListLabel(int listLabel) {
		this.listLabel = listLabel;
	}
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
}